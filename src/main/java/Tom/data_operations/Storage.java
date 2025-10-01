package Tom.data_operations;

import Tom.tasks.Deadlines;
import Tom.tasks.Event;
import Tom.tasks.Task;

import java.io.*;
import java.util.Objects;

public class Storage {
    protected File file;

    public Storage(String filepath) throws IOException {
        this.file = new File(filepath);
        if (this.file.createNewFile()) {           // Try to create the file
            System.out.println("File created: " + this.file.getName());
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
            e.printStackTrace();
        }
    }

    public void parseLines(String[] line, TaskList task_list){
        String task_description;
        switch(line[0]){
            case "T ":
                task_description = line[2];
                if(Objects.equals(line[1], "X")){
                    task_list.list.add(new Task(true, line[2], task_description));
                }
                else {
                    task_list.list.add(new Task(false, line[2], task_description));
                }
                break;
            case "E ":
                task_description = line[2] + " (from: " + line[3].split("-")[0] + " to: " + line[3].split("-")[1] + ")";
                if(Objects.equals(line[1], "X")){
                    task_list.list.add(new Event(true, line[2], task_description));
                }
                else {
                    task_list.list.add(new Event(false, line[2], task_description));
                }
                break;
            case "D ":
                task_description = line[2] + " (by: " + line[3] + ")";
                if(Objects.equals(line[1], "X")){
                    task_list.list.add(new Deadlines(true, line[2], task_description));
                }
                else {
                    task_list.list.add(new Deadlines(false, line[2], task_description));
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
            e.printStackTrace();
        }
    }
}
