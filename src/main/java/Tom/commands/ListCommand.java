package Tom.commands;

import Tom.data_operations.Storage;
import Tom.data_operations.TaskList;
import Tom.io.Ui;

import java.time.LocalDateTime;

public class ListCommand extends Command {
    protected String type;
    protected LocalDateTime date_time;
    protected String keyword;

    public ListCommand(String type, LocalDateTime date_time, String keyword){
        this.type = type;
        this.date_time = date_time;
        this.keyword = keyword;
    }

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
        }
    }
}
