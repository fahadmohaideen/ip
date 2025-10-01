package Tom.commands;

import Tom.data_operations.Storage;
import Tom.data_operations.TaskList;
import Tom.io.Ui;
import Tom.exceptions.IncompleteTaskException;
import Tom.exceptions.TooManyArgumentsException;

import java.io.IOException;

public class ExitCommand extends Command {
    public void execute(TaskList task, Ui ui, Storage storage) throws IncompleteTaskException, IOException, TooManyArgumentsException {
        ui.goodbye();

    }
}
