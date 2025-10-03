package Tom.data_operations;
import Tom.io.Parser;
import Tom.tasks.Deadlines;
import Tom.tasks.Event;
import Tom.tasks.Task;
import java.io.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The Storage class handles file operations for persisting and loading task data.
 * It manages the creation, reading, and writing of task data to a file storage system.
 * The class supports three types of tasks: generic Tasks, Events, and Deadlines.
 */
public class Storage {
    protected File file;

    /**
     * Constructs a Storage object with the specified file path.
     * Attempts to create the file if it doesn't exist.
     *
     * @param filepath the path to the file where task data will be stored
     */
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

    /**
     * Loads task data from the storage file and populates the provided TaskList.
     * Reads the file line by line, parsing each line into appropriate task objects.
     *
     * @param task_list the TaskList to populate with loaded tasks
     * @throws IOException if an I/O error occurs during file reading
     */
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

    /**
     * Parses a single line from the storage file and creates the appropriate task object.
     * The expected line formats are:
     * - Tasks: "T |[X/ ]| [description]"
     * - Events: "E |[X/ ]| [description] | [start]-[end]"
     * - Deadlines: "D |[X/ ]| [description] | [due_date]"
     *
     * @param line the tokenized line split by "|" character
     * @param task_list the TaskList to add the parsed task to
     */
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

    /**
     * Appends a line of task data to the storage file.
     * Uses append mode to add new tasks without overwriting existing content.
     *
     * @param save_line the formatted task data line to save to the file
     * @throws IOException if an I/O error occurs during file writing
     */
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
