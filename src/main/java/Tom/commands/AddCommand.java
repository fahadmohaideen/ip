package Tom.commands;
import Tom.data_operations.Storage;
import Tom.data_operations.TaskList;
import Tom.io.Ui;
import java.time.LocalDateTime;

/**
 * The AddCommand class represents a command to add new tasks to the task management system.
 * It supports three types of tasks: generic tasks, events, and deadlines.
 * This class extends the Command abstract class and implements the execute method
 * to handle the actual task creation process.
 */
public class AddCommand extends Command {
    protected String type;
    protected String cmd_description;
    protected String cmd;
    protected LocalDateTime start;
    protected LocalDateTime end;

    /**
     * Constructs an AddCommand with the specified task parameters.
     *
     * @param type the type of task to add ("task", "event", or "deadline")
     * @param cmd_description the detailed description of the task including time information
     * @param cmd the name or short description of the task
     * @param start the start date/time for the task (can be null for tasks without start time)
     * @param end the end date/time for the task (required for events and deadlines)
     */
    public AddCommand(String type, String cmd_description, String cmd, LocalDateTime start, LocalDateTime end){
        this.type = type;
        this.cmd_description = cmd_description;
        this.cmd = cmd;
        this.start = start;
        this.end = end;
    }

    /**
     * Executes the add command by delegating to the appropriate TaskList method
     * based on the task type. The method handles three types of tasks:
     * - "task": Generic tasks without specific time constraints
     * - "event": Tasks with both start and end times
     * - "deadline": Tasks with a specific due date/time
     *
     * @param task the TaskList to which the new task will be added
     * @param ui the user interface handler for displaying output
     * @param storage the storage handler for persisting the task data
     * @throws IllegalArgumentException if an unsupported task type is provided
     */
    public void execute(TaskList task, Ui ui, Storage storage){
        switch(this.type){
            case "task":
                task.addTask(this.cmd, this.cmd_description, storage, this.start, this.end);
                break;
            case "event":
                task.addEvent(this.cmd, this.cmd_description, storage, this.start, this.end);
                break;
            case "deadline":
                task.addDeadline(this.cmd, this.cmd_description, storage, this.start, this.end);
                break;
        }
    }
}
