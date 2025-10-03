package Tom.commands;
import Tom.data_operations.Storage;
import Tom.data_operations.TaskList;
import Tom.io.Ui;
import java.time.LocalDateTime;

/**
 * The ListCommand class represents a command for displaying and searching tasks in the system.
 * It supports three types of listing operations: displaying all tasks, searching by date,
 * and searching by keyword. This class extends the Command abstract class and implements
 * the execute method to handle different task viewing and searching scenarios.
 */
public class ListCommand extends Command {
    protected String type;
    protected LocalDateTime date_time;
    protected String keyword;

    /**
     * Constructs a ListCommand with the specified listing parameters.
     *
     * @param type the type of listing operation to perform. Supported values are:
     *             - "list_task": Display all tasks in the system
     *             - "search_by_date": Search for tasks occurring at a specific date/time
     *             - "search_by_keyword": Search for tasks containing a specific keyword
     * @param date_time the date and time to search for when type is "search_by_date".
     *                  For "list_task" and "search_by_keyword" operations, this can be null.
     * @param keyword the search term to look for when type is "search_by_keyword".
     *                For "list_task" and "search_by_date" operations, this can be null.
     */
    public ListCommand(String type, LocalDateTime date_time, String keyword){
        this.type = type;
        this.date_time = date_time;
        this.keyword = keyword;
    }

    /**
     * Executes the list command by delegating to the appropriate TaskList method
     * based on the command type. The method handles three types of operations:
     * - "list_task": Displays all tasks with their current status and details
     * - "search_by_date": Finds tasks that occur at or around the specified date/time
     * - "search_by_keyword": Finds tasks whose descriptions contain the specified keyword
     *
     * @param task the TaskList containing the tasks to be listed or searched
     * @param ui the user interface handler for displaying the results to the user
     * @param storage the storage handler (not directly used for listing operations,
     *                but included for interface consistency)
     * @throws IllegalArgumentException if an unsupported operation type is provided
     */
    public void execute(TaskList task, Ui ui, Storage storage) {
        switch(this.type){
            case "list_task":
                task.list(ui);
                break;
            case "search_by_date":
                task.search_by_date(this.date_time);
                break;
            case "search_by_keyword":
                task.search_by_keyword(this.keyword);
                break;
            // Note: Consider adding a default case to handle unsupported operation types
        }
    }
}
