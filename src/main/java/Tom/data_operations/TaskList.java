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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class TaskList {
    protected ArrayList<Task> list;

    public TaskList(ArrayList<Task> list){
        this.list = list;
    }

    public void addTask(String task, String task_description, Storage storage, LocalDateTime start, LocalDateTime end) throws IncompleteTaskException, IOException {
        this.list.add(new Task(false, task, task_description, start, end));
        System.out.println("Now you have " + this.list.size() + " tasks in the list.");
        String save_line = "T |" + " " + "| " + task + "\n";
        storage.save(save_line);
    }

    public void addEvent(String event, String event_description, Storage storage, LocalDateTime start, LocalDateTime end) throws IncompleteTaskException, IOException {
        this.list.add(new Event(false, event, event_description, start, end));
        System.out.println("Now you have " + this.list.size() + " tasks in the list.");
        String time_start = event_description.split("from:|to:|[()]")[2];
        String time_end = event_description.split("from:|to:|[()]")[3];
        String save_line = "E |" + " " + "| " + event +
                " | " + time_start + "-" + time_end + "\n";
        storage.save(save_line);
    }

    public void addDeadline(String deadline, String deadline_description, Storage storage, LocalDateTime start, LocalDateTime end) throws IncompleteTaskException, IOException {
        this.list.add(new Deadlines(false, deadline, deadline_description, start, end));
        System.out.println("Now you have " + this.list.size() + " tasks in the list.");
        String end_date = deadline_description.split("by: |[()]")[2];
        String save_line = "D |" + " " + "| " + deadline +
                " | " + end_date + "\n";
        storage.save(save_line);
    }

    public void delete(int index, Storage storage) throws IncompleteTaskException, TooManyArgumentsException {
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
            System.out.println(e.getMessage());
        }
        this.list.remove(index);
        System.out.println("Now you have " + this.list.size() + " tasks in the list.");
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
            System.out.println(e.getMessage());
        }
    }

    public void list(Ui ui){
        for(int x=0; x<this.list.size(); x++){
            System.out.print(x+1);
            this.list.get(x).print();
        }
        ui.showLine();
    }

    public void search_by_date(LocalDateTime date_time){
        for(int x=0; x<this.list.size(); x++){
            Task curr_task = this.list.get(x);
            if(curr_task.end != null){
                if((curr_task.start == null) && (date_time.toLocalDate().equals(curr_task.end.toLocalDate()))){
                    System.out.println(curr_task.getDescription());
                }
                if((curr_task.start != null) && (date_time.isAfter(curr_task.start) && date_time.isBefore(curr_task.end))){
                    System.out.println(curr_task.getDescription());
                }
            }
        }
    }

}
