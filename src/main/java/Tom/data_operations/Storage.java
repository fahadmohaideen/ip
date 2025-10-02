package Tom.data_operations;

import Tom.io.Parser;
import Tom.tasks.Deadlines;
import Tom.tasks.Event;
import Tom.tasks.Task;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Objects;

public class Storage {
    protected File file;

    public Storage(String filepath){
        try {
            this.file = new File(filepath);
            if (this.file.createNewFile()) {           // Try to create the file
                System.out.println("File created: " + this.file.getName());
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void load(TaskList task_list){
        try (BufferedReader reader = new BufferedReader(new FileReader(this.file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] line_buffer = line.split("\\|");
                this.parseLines(line_buffer, task_list);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void parseLines(String[] line, TaskList task_list){
        String task_description;
        Parser dateTime_parser = new Parser();
        switch(line[0]){
            case "T ":
                task_description = " ";
                if(Objects.equals(line[1], "X")){
                    task_list.list.add(new Task(true, line[2], task_description, null, null));
                }
                else {
                    task_list.list.add(new Task(false, line[2], task_description, null, null));
                }
                break;
            case "E ":
                String start_str = line[3].split("-")[0];
                String end_str = line[3].split("-")[1];
                LocalDateTime start = dateTime_parser.parse_dateTime(start_str.trim(), "dd/MM/yyyy HHmm");
                LocalDateTime end = dateTime_parser.parse_dateTime(end_str.trim(), "dd/MM/yyyy HHmm");
                task_description = " (from: " + line[3].split("-")[0] + " to: " + line[3].split("-")[1] + ")";
                if(Objects.equals(line[1], "X")){
                    task_list.list.add(new Event(true, line[2], task_description, start, end));
                }
                else {
                    task_list.list.add(new Event(false, line[2], task_description, start, end));
                }
                break;
            case "D ":
                String ending_str = line[3];
                LocalDateTime ending_time = dateTime_parser.parse_dateTime(ending_str.trim(), "dd/MM/yyyy HHmm");
                task_description = " (by: " + line[3] + ")";
                if(Objects.equals(line[1], "X")){
                    task_list.list.add(new Deadlines(true, line[2], task_description, null, ending_time));
                }
                else {
                    task_list.list.add(new Deadlines(false, line[2], task_description, null, ending_time));
                }
                break;
        }
    }

    public void save(String save_line) {
        try {
            FileWriter file_saver = new FileWriter(this.file, true);
            file_saver.write(save_line);
            file_saver.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
