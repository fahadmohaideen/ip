package Tom.commands;

import Tom.data_operations.Storage;
import Tom.data_operations.TaskList;
import Tom.io.Ui;
import Tom.exceptions.IncompleteTaskException;
import Tom.exceptions.TooManyArgumentsException;

public class DeleteCommand extends Command {

    public void execute(TaskList task, Ui ui, Storage storage) throws IncompleteTaskException, TooManyArgumentsException {
        task.delete(ui, storage);
    }
}
