import Tom.Tom;
import Tom.io.Ui;
import Tom.data_operations.TaskList;
import Tom.data_operations.Storage;
import Tom.tasks.Task;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        Ui ui = new Ui();
        ArrayList<Task> list_of_tasks = new ArrayList<>();
        TaskList task_list = new TaskList(list_of_tasks);
        Storage storage = new Storage("tom.txt");
        Tom tom = new Tom(ui, task_list, storage);
        tom.run();
    }
}

