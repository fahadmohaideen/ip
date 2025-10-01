package Tom.commands;

import Tom.data_operations.Storage;
import Tom.data_operations.TaskList;
import Tom.io.Ui;
import Tom.exceptions.IncompleteTaskException;

import java.io.IOException;

public class AddCommand extends Command {
    protected String type;

    public AddCommand(String type){
        this.type = type;
    }

    public void execute(TaskList task, Ui ui, Storage storage) throws IncompleteTaskException, IOException {
        switch(this.type){
            case "task":
                task.addTask(ui, storage);
                break;
            case "event":
                task.addEvent(ui, storage);
                break;
            case "deadline":
                task.addDeadline(ui, storage);
        }
    }
}
