package Tom.commands;

import Tom.data_operations.Storage;
import Tom.data_operations.TaskList;
import Tom.io.Ui;

public class MarkCommand extends Command {
    protected boolean mark;

    public MarkCommand(boolean is_marked){
        this.mark = is_marked;
    }

    public void execute(TaskList task, Ui ui, Storage storage) {
        String[] tokens = ui.output.toLowerCase().split(" ");
        int index = Integer.parseInt(tokens[1]) - 1;
        task.mark_task(this.mark, index);
        task.modify(index, storage, this.mark);
        ui.showLine();
    }
}
