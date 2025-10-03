package Tom.commands;
import Tom.data_operations.Storage;
import Tom.data_operations.TaskList;
import Tom.io.Ui;


public class DeleteCommand extends Command {
    protected int index;

    public DeleteCommand(int index){
        this.index = index;
    }

    public void execute(TaskList task, Ui ui, Storage storage){
        task.delete(this.index, storage);
    }
}
