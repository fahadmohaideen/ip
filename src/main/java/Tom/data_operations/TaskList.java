package Tom.data_operations;
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

/**
 * The TaskList class manages a collection of tasks and provides operations
 * for adding, deleting, modifying, and searching tasks.
 * It also handles persistence by interacting with the Storage class.
 */
public class TaskList {
    protected ArrayList<Task> list;

    /**
     * Constructs a TaskList with the specified list of tasks.
     *
     * @param list the initial list of tasks
     */
    public TaskList(ArrayList<Task> list){
        this.list = list;
    }

    /**
     * Adds a generic task to the task list and persists it to storage.
     *
     * @param task the name/type of the task
     * @param task_description the detailed description of the task
     * @param storage the storage handler for saving the task
     * @param start the start date/time of the task (can be null for tasks without start time)
     * @param end the end date/time of the task (can be null for tasks without end time)
     */
    public void addTask(String task, String task_description, Storage storage, LocalDateTime start, LocalDateTime end){
        this.list.add(new Task(false, task, task_description, start, end));
        System.out.println("Now you have " + this.list.size() + " tasks in the list.");
        String save_line = "T |" + " " + "| " + task + "\n";
        storage.save(save_line);
    }

    /**
     * Adds an event task to the task list and persists it to storage.
     * Events have both start and end times.
     *
     * @param event the name of the event
     * @param event_description the detailed description including time range in format "(from: X to: Y)"
     * @param storage the storage handler for saving the event
     * @param start the start date/time of the event
     * @param end the end date/time of the event
     */
    public void addEvent(String event, String event_description, Storage storage, LocalDateTime start, LocalDateTime end){
        this.list.add(new Event(false, event, event_description, start, end));
        System.out.println("Now you have " + this.list.size() + " tasks in the list.");
        String time_start = event_description.split("from:|to:|[()]")[2];
        String time_end = event_description.split("from:|to:|[()]")[3];
        String save_line = "E |" + " " + "| " + event +
                " | " + time_start + "-" + time_end + "\n";
        storage.save(save_line);
    }

    /**
     * Adds a deadline task to the task list and persists it to storage.
     * Deadlines have only an end time (due date).
     *
     * @param deadline the name of the deadline task
     * @param deadline_description the detailed description including due time in format "(by: X)"
     * @param storage the storage handler for saving the deadline
     * @param start the start date/time (typically null for deadlines)
     * @param end the due date/time of the deadline
     */
    public void addDeadline(String deadline, String deadline_description, Storage storage, LocalDateTime start, LocalDateTime end){
        this.list.add(new Deadlines(false, deadline, deadline_description, start, end));
        System.out.println("Now you have " + this.list.size() + " tasks in the list.");
        String end_date = deadline_description.split("by: |[()]")[2];
        String save_line = "D |" + " " + "| " + deadline +
                " | " + end_date + "\n";
        storage.save(save_line);
    }

    /**
     * Deletes a task from the task list at the specified index and updates the storage file.
     * The method removes both the task from memory and its corresponding line from the storage file.
     *
     * @param index the zero-based index of the task to delete
     * @param storage the storage handler for updating the file
     * @throws ArrayIndexOutOfBoundsException if the index is out of range (index < 0 || index >= list.size())
     */
    public void delete(int index, Storage storage){
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

    /**
     * Marks or unmarks a task as completed based on the specified mark flag.
     *
     * @param mark true to mark the task as completed, false to unmark it
     * @param index the zero-based index of the task to modify
     * @throws ArrayIndexOutOfBoundsException if the index is out of range
     */
    public void mark_task(boolean mark, int index){
        if(mark){
            this.list.get(index).mark();
        }
        else {
            this.list.get(index).unmark();
        }
    }

    /**
     * Modifies the completion status of a task in the storage file.
     * Updates the file representation of the task's completion status.
     *
     * @param index the zero-based index of the task to modify
     * @param storage the storage handler for updating the file
     * @param mark true to mark as completed (replace "| |" with "|X|"),
     *             false to unmark (replace "|X|" with "| |")
     */
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
                    String replaced_line;
                    if(mark) {
                        replaced_line = line.replace("| |", "|X|");
                    }
                    else{
                        replaced_line = line.replace("|X|", "| |");
                    }
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

    /**
     * Displays all tasks in the task list with their numbering and details.
     * Shows a line separator after listing all tasks.
     *
     * @param ui the user interface handler for displaying output
     */
    public void list(Ui ui){
        for(int x=0; x<this.list.size(); x++){
            System.out.print(x+1);
            this.list.get(x).print();
        }
        ui.showLine();
    }

    /**
     * Searches for tasks that occur at or around the specified date/time.
     * For deadlines: matches tasks due on the same date.
     * For events: matches tasks that are active during the specified time.
     *
     * @param date_time the date and time to search for
     */
    public void search_by_date(LocalDateTime date_time){
        for (Task curr_task : this.list) {
            if (curr_task.end != null) {
                if ((curr_task.start == null) && (date_time.toLocalDate().equals(curr_task.end.toLocalDate()))) {
                    System.out.println(curr_task.getDescription());
                } else if ((curr_task.start != null) && (date_time.isAfter(curr_task.start) && date_time.isBefore(curr_task.end))) {
                    System.out.println(curr_task.getDescription());
                } else {
                    System.out.println("There are no events/deadlines at this time!");
                }
            }
        }
    }

    /**
     * Searches for tasks whose description contains the specified keyword.
     * Performs a case-sensitive search and displays all matching tasks.
     * If no matches are found, informs the user.
     *
     * @param keyword the search term to look for in task descriptions
     */
    public void search_by_keyword(String keyword) {
        boolean matched = false;
        for (Task curr_task : this.list) {
            if (curr_task.task.contains(keyword)) {
                System.out.println(curr_task.getDescription());
                matched = true;
            }
        }
        if(!matched){
            System.out.println("There are no matching tasks!");
        }
    }
}
