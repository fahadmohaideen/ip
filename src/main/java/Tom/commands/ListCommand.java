package Tom.commands;

import Tom.data_operations.Storage;
import Tom.data_operations.TaskList;
import Tom.io.Ui;

public class ListCommand extends Command {

    public void execute(TaskList task, Ui ui, Storage storage) {
        task.list(ui);
    }
}
