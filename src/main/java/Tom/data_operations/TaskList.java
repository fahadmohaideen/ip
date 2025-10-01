package Tom.data_operations;
import Tom.exceptions.IncompleteTaskException;
import Tom.exceptions.TooManyArgumentsException;
import Tom.io.Ui;
import Tom.tasks.Task;
import Tom.tasks.Event;
import Tom.tasks.Deadlines;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TaskList {
    protected ArrayList<Task> list;

    public TaskList(ArrayList<Task> list){
        this.list = list;
    }

    public void addTask(Ui ui, Storage storage) throws IncompleteTaskException, IOException {
        String[] tokens = ui.output.toLowerCase().split(" ");
        if(tokens.length <= 1){
            throw new IncompleteTaskException("Description of task cannot be empty!");
        }
        System.out.println("Got it. I've added this task:");
        String[] task = Arrays.copyOfRange(tokens, 1, tokens.length);
        String task_description = String.join(" ", task);
        System.out.println("  [T][] " + task_description);
        this.list.add(new Task(false, String.join(" ", task), task_description));
        System.out.println("Now you have " + this.list.size() + " tasks in the list.");
        ui.showLine();

        String save_line = "T |" + " " + "| " + String.join(" ", task) + "\n";
        storage.save(save_line);
    }

    public void addEvent(Ui ui, Storage storage) throws IncompleteTaskException, IOException {
        String[] tokens = ui.output.toLowerCase().split(" ");
        if(tokens.length <= 1){
            throw new IncompleteTaskException("Description of event cannot be empty!");
        }
        String[] from_check_tokens = ui.output.split("/from");
        String[] to_check_tokens = ui.output.split("/to");
        String[] event_tokens = ui.output.split("/from | /to");
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
        ui.showLine();

        String save_line = "E |" + " " + "| " + String.join(" ", event) +
                " | " + time_start + "-" + time_end + "\n";
        storage.save(save_line);
    }

    public void addDeadline(Ui ui, Storage storage) throws IncompleteTaskException, IOException {
        String[] tokens = ui.output.toLowerCase().split(" ");
        if(tokens.length <= 1){
            throw new IncompleteTaskException("Description of deadline cannot be empty!");
        }
        String[] deadline_tokens = ui.output.split("/by");
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
        ui.showLine();

        String save_line = "D |" + " " + "| " + String.join(" ", deadline) +
                " | " + end_date + "\n";
        storage.save(save_line);
    }

    public void delete(Ui ui, Storage storage) throws IncompleteTaskException, TooManyArgumentsException {
        String[] tokens = ui.output.toLowerCase().split(" ");
        if(tokens.length <= 1){
            throw new IncompleteTaskException("Specify the index you wish to delete!");
        }
        if(tokens.length > 2){
            throw new TooManyArgumentsException("Too many arguments provided, maximum 2!");
        }
        int index = Integer.parseInt(tokens[1]) - 1;
        System.out.println("Noted. I've removed this task:");
        System.out.println(this.list.get(index).getDescription());
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(storage.file));
            ArrayList<String> updatedLines = new ArrayList<>();
            String line;
            int line_num = 0;

            while ((line = bufferedReader.readLine()) != null) {
                if(line_num != index){
                    updatedLines.add(line);
                }
                line_num++;
            }
            FileWriter file_overwriter = new FileWriter(storage.file);
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

    public void mark_task(boolean mark, int index){
        if(mark){
            this.list.get(index).mark();
        }
        else {
            this.list.get(index).unmark();
        }
    }

    public void modify(int index, Storage storage, boolean mark){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(storage.file));
            ArrayList<String> updatedLines = new ArrayList<>();
            String line;
            int line_num = 0;

            while ((line = bufferedReader.readLine()) != null) {
                if(line_num != index){
                    updatedLines.add(line);
                }
                else{
                    String replaced_line = line.replace("| |", "|X|");
                    updatedLines.add(replaced_line);
                }
                line_num++;
            }
            FileWriter file_overwriter = new FileWriter(storage.file);
            for (String updated_line : updatedLines) {
                file_overwriter.write(updated_line + System.lineSeparator());
            }
            file_overwriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void list(Ui ui){
        for(int x=0; x<this.list.size(); x++){
            System.out.print(x+1);
            this.list.get(x).print();
        }
        ui.showLine();
    }

}
