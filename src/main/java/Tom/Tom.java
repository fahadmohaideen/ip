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

/**
 * The Tom class represents the main application controller that orchestrates
 * the task management system. It initializes and coordinates all major components
 * including the user interface, task list, storage, and command parser.
 * This class contains the main application loop and handles the overall program flow.
 */
public class Tom {
    public Ui ui;
    public TaskList task_list;
    public Storage storage;

    /**
     * Constructs a Tom application instance with the specified components.
     * This constructor follows dependency injection principles to allow for
     * flexible component configuration and testing.
     *
     * @param ui the user interface handler for input/output operations
     * @param task_list the task list manager for storing and manipulating tasks
     * @param storage the storage handler for persisting task data to file system
     */
    public Tom(Ui ui, TaskList task_list, Storage storage){
        this.ui = ui;
        this.task_list = task_list;
        this.storage = storage;
    }

    /**
     * Starts and runs the main application loop. This method:
     * 1. Displays the application greeting
     * 2. Loads existing tasks from storage
     * 3. Enters a continuous command processing loop
     * 4. Processes user commands until exit is requested
     * 5. Handles various exceptions with appropriate error messages

     * The main loop follows this sequence:
     * - Read user input
     * - Parse input into Command objects
     * - Execute commands
     * - Handle exit condition
     * - Manage errors and exceptions
     *
     * @throws IOException if file operations fail during command execution
     * @throws IncompleteTaskException when commands lack required parameters
     * @throws TooManyArgumentsException when commands contain excessive parameters
     * @throws IndexOutOfBoundsException when task indices are out of valid range
     * @throws NumberFormatException when numeric parameters are invalid
     */
    public void run(){
        ui.greeting();
        this.storage.load(this.task_list);
        boolean is_running = true;
        while(is_running){
            try {
                String line_read = this.ui.readCommand();
                Parser cmd_parser = new Parser();
                Command command = cmd_parser.parser(line_read, this.ui);
                if (command != null) {
                    command.execute(this.task_list, this.ui, this.storage);
                    this.ui.showLine();
                }
                if (command instanceof ExitCommand) {
                    is_running = false;
                }
            } catch (IndexOutOfBoundsException | IncompleteTaskException |
                     TooManyArgumentsException | NumberFormatException | IOException e){
                ui.showError(e.getMessage());
                this.ui.showLine();
            }
        }
    }
}
