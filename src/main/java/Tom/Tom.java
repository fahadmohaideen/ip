package Tom;

import Tom.commands.Command;
import Tom.commands.ExitCommand;
import Tom.data_operations.Storage;
import Tom.exceptions.IncompleteTaskException;
import Tom.exceptions.TooManyArgumentsException;
import Tom.data_operations.TaskList;
import Tom.io.Parser;
import Tom.io.Ui;

import java.io.*;

public class Tom {
    /*public int ptr;
    public ArrayList<Task> list;
    public String output;
    public File file;
    public FileWriter file_writer;
    public FileReader file_reader;*/
    public Ui ui;
    public TaskList task_list;
    public Storage storage;

    public Tom(Ui ui, TaskList task_list, Storage storage) throws IOException {
        //this.ptr = ptr;
        /*this.list = list;
        this.output = output;
        this.file = new File(filepath);
        if (this.file.createNewFile()) {           // Try to create the file
            System.out.println("File created: " + this.file.getName());
        }
        this.file_writer = new FileWriter(filepath, true);
        this.file_reader = new FileReader(filepath);*/
        this.ui = ui;
        this.task_list = task_list;
        this.storage = storage;
    }

    public void run() throws IncompleteTaskException, TooManyArgumentsException, IOException {
        ui.greeting();
        this.storage.load(this.task_list);
        boolean is_running = true;
        while(is_running){
            String line_read = this.ui.readCommand();
            Command command = Parser.parser(line_read, this.ui);
            if (command != null) {
                command.execute(this.task_list, this.ui, this.storage);
            }
            if(command instanceof ExitCommand){
                is_running = false;
            }
        }
    }

    /*public void load(){
        try (BufferedReader reader = new BufferedReader(this.file_reader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] line_buffer = line.split("\\|");
                this.parseLines(line_buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseLines(String[] line){
        String task_description;
        switch(line[0]){
            case "T ":
                System.out.println(Arrays.toString(line));
                task_description = line[2];
                this.list.add(new Task(false, line[2], task_description));
                break;
            case "E ":
                System.out.println(Arrays.toString(line));
                task_description = line[2] + " (from: " + line[3].split("-")[0] + " to: " + line[3].split("-")[1] + ")";
                this.list.add(new Event(false, line[2], task_description));
                break;
            case "D ":
                System.out.println(Arrays.toString(line));
                task_description = line[2] + " (by: " + line[3] + ")";
                this.list.add(new Deadlines(false, line[2], task_description));
                break;
        }
    }

    public void greeting(){
        System.out.println(" Hello! I'm Tom.Tom");
        System.out.println(" What can I do for you?");
        String name = """
                 _____                     \s
                |_   _| ______   _________ \s
                  | |  |  __  | |  _   _  |\s
                  | |  | |__| | | | | | | |\s
                  |_|  |______| |_| |_| |_|\s
                """;
        System.out.println("Hello from\n" + name);
        System.out.println("____________________________________");
        System.out.println("____________________________________");
    }

    public void addTask() throws IncompleteTaskException, IOException {
        String[] tokens = this.output.toLowerCase().split(" ");
        if(tokens.length <= 1){
            throw new IncompleteTaskException("Description of task cannot be empty!");
        }
        System.out.println("Got it. I've added this task:");
        String[] task = Arrays.copyOfRange(tokens, 1, tokens.length);
        String task_description = String.join(" ", task);
        System.out.println("  [T][] " + task_description);
        this.list.add(new Task(false, String.join(" ", task), task_description));
        System.out.println("Now you have " + this.list.size() + " tasks in the list.");
        System.out.println("____________________________________");

        String save_line = "T |" + " " + "| " + String.join(" ", task) + "\n";
        FileWriter file_saver = new FileWriter(this.file);
        file_saver.write(save_line);
        file_saver.close();


    }

    public void addEvent() throws IncompleteTaskException, IOException {
        String[] tokens = this.output.toLowerCase().split(" ");
        if(tokens.length <= 1){
            throw new IncompleteTaskException("Description of event cannot be empty!");
        }
        String[] from_check_tokens = this.output.split("/from");
        String[] to_check_tokens = this.output.split("/to");
        String[] event_tokens = this.output.split("/from | /to");
        if(from_check_tokens.length <= 1){
            if(to_check_tokens.length <= 1){
                throw new IncompleteTaskException("Tom.tasks.Event description missing both /from and /to!");
            }
            else{
                throw new IncompleteTaskException("Tom.tasks.Event description missing /from");
            }
        }
        else{
            if(to_check_tokens.length <= 1){
                throw new IncompleteTaskException("Tom.tasks.Event description missing /to!");
            }
        }
        System.out.println("Got it. I've added this task:");
        String time_start = event_tokens[1];
        String time_end = event_tokens[2];
        String[] event = Arrays.copyOfRange(event_tokens[0].split(" "), 1, event_tokens[0].split(" ").length);
        String event_description = String.join(" ", event) + " (from: " + time_start + " to: " + time_end + ")";
        System.out.println("  [E][] " + event_description);
        this.list.add(new Event(false, String.join(" ", event), event_description));
        System.out.println("Now you have " + this.list.size() + " tasks in the list.");
        System.out.println("____________________________________");

        String save_line = "E |" + " " + "| " + String.join(" ", event) +
                " | " + time_start + "-" + time_end + "\n";
        FileWriter file_saver = new FileWriter(this.file);
        file_saver.write(save_line);
        file_saver.close();
    }

    public void addDeadline() throws IncompleteTaskException, IOException {
        String[] tokens = this.output.toLowerCase().split(" ");
        if(tokens.length <= 1){
            throw new IncompleteTaskException("Description of deadline cannot be empty!");
        }
        String[] deadline_tokens = this.output.split("/by");
        if(deadline_tokens.length <= 1){
            throw new IncompleteTaskException("Deadline description missing /by!");
        }
        System.out.println("Got it. I've added this task:");
        String end_date = deadline_tokens[1];
        String[] deadline = Arrays.copyOfRange(deadline_tokens[0].split(" "), 1, deadline_tokens[0].split(" ").length);
        String deadline_description = String.join(" ", deadline) + " (by: " + end_date + ")";
        System.out.println("  [D][] " + deadline_description);
        this.list.add(new Deadlines(false, String.join(" ", deadline), deadline_description));
        System.out.println("Now you have " + this.list.size() + " tasks in the list.");
        System.out.println("____________________________________");

        String save_line = "D |" + " " + "| " + String.join(" ", deadline) +
                " | " + end_date + "\n";
        FileWriter file_saver = new FileWriter(this.file);
        file_saver.write(save_line);
        file_saver.close();
    }

    public void delete() throws IncompleteTaskException, TooManyArgumentsException {
        String[] tokens = this.output.toLowerCase().split(" ");
        if(tokens.length <= 1){
            throw new IncompleteTaskException("Specify the index you wish to delete!");
        }
        if(tokens.length > 2){
            throw new TooManyArgumentsException("Too many arguments provided, maximum 2!");
        }
        int index = Integer.parseInt(tokens[1]);
        System.out.println("Noted. I've removed this task:");
        System.out.println(this.list.get(index).getDescription());
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(this.file));
            ArrayList<String> updatedLines = new ArrayList<>();
            String line;
            int line_num = 0;

            while ((line = bufferedReader.readLine()) != null) {
                if(line_num != index){
                    updatedLines.add(line);
                }
                line_num++;
            }
            FileWriter file_overwriter = new FileWriter(this.file);
            for (String updated_line : updatedLines) {
                file_overwriter.write(updated_line + System.lineSeparator());
            }
            file_overwriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.list.remove(index);
        System.out.println("Now you have " + this.list.size() + " tasks in the list.");
        System.out.println("____________________________________");
    }

    public void goodbye(){
        System.out.println(" Bye. Hope to see you again soon!");
    }*/

}
