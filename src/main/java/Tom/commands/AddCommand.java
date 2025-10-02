package Tom.commands;

import Tom.data_operations.Storage;
import Tom.data_operations.TaskList;
import Tom.io.Ui;
import Tom.exceptions.IncompleteTaskException;

import java.io.IOException;
import java.time.LocalDateTime;

public class AddCommand extends Command {
    protected String type;
    protected String cmd_description;
    protected String cmd;
    protected LocalDateTime start;
    protected LocalDateTime end;

    public AddCommand(String type, String cmd_description, String cmd, LocalDateTime start, LocalDateTime end){
        this.type = type;
        this.cmd_description = cmd_description;
        this.cmd = cmd;
        this.start = start;
        this.end = end;
    }

    public void execute(TaskList task, Ui ui, Storage storage) throws IncompleteTaskException, IOException {
        switch(this.type){
            case "task":
                task.addTask(this.cmd, this.cmd_description, storage, this.start, this.end);
                break;
            case "event":
                task.addEvent(this.cmd, this.cmd_description, storage, this.start, this.end);
                break;
            case "deadline":
                task.addDeadline(this.cmd, this.cmd_description, storage, this.start, this.end);
        }
    }
}
