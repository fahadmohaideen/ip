package Tom;

import Tom.exceptions.IncompleteTaskException;
import Tom.tasks.Task;
import Tom.tasks.Event;
import Tom.tasks.Deadlines;
import java.util.Arrays;

public class Tom {
    public int ptr;
    public Task[] list;
    public String output;

    public Tom(int ptr, Task[] list, String output){
        this.ptr = ptr;
        this.list = list;
        this.output = output;
    }

    public void greeting(){
        System.out.println(" Hello! I'm Tom.Tom");
        System.out.println(" What can I do for you?");
        String name = """
                 _____                     \s
                |_   _| ______   _________ \s
                  | |  |  __  | |  _   _  |\s
                  | |  | |__| | | | | | | |\s
                  |_|  |______| |_| |_| |_|\s
                """;
        System.out.println("Hello from\n" + name);
        System.out.println("____________________________________");
        System.out.println("____________________________________");
    }

    public void addTask() throws IncompleteTaskException {
        String[] tokens = this.output.toLowerCase().split(" ");
        if(tokens.length <= 1){
            throw new IncompleteTaskException("Description of task cannot be empty!");
        }
        System.out.println("Got it. I've added this task:");
        String[] task = Arrays.copyOfRange(tokens, 1, tokens.length);
        System.out.println("  [T][] " + String.join(" ", task));
        this.list[this.ptr] = new Task(false, String.join(" ", task));
        this.ptr++;
        System.out.println("Now you have " + this.ptr + " tasks in the list.");
        System.out.println("____________________________________");
    }

    public void addEvent() throws IncompleteTaskException {
        String[] tokens = this.output.toLowerCase().split(" ");
        if(tokens.length <= 1){
            throw new IncompleteTaskException("Description of event cannot be empty!");
        }
        String[] from_check_tokens = this.output.split("/from");
        String[] to_check_tokens = this.output.split("/to");
        String[] event_tokens = this.output.split("/from | /to");
        if(from_check_tokens.length <= 1){
            if(to_check_tokens.length <= 1){
                throw new IncompleteTaskException("Tom.tasks.Event description missing both /from and /to!");
            }
            else{
                throw new IncompleteTaskException("Tom.tasks.Event description missing /from");
            }
        }
        else{
            if(to_check_tokens.length <= 1){
                throw new IncompleteTaskException("Tom.tasks.Event description missing /to!");
            }
        }
        System.out.println("Got it. I've added this task:");
        String time_start = event_tokens[1];
        String time_end = event_tokens[2];
        String[] event = Arrays.copyOfRange(event_tokens[0].split(" "), 1, event_tokens[0].split(" ").length);
        System.out.println("  [E][] " + String.join(" ", event) + " (from: " + time_start + " to: " + time_end + ")");
        this.list[this.ptr] = new Event(false, String.join(" ", event));
        this.ptr++;
        System.out.println("Now you have " + this.ptr + " tasks in the list.");
        System.out.println("____________________________________");
    }

    public void addDeadline() throws IncompleteTaskException {
        String[] tokens = this.output.toLowerCase().split(" ");
        if(tokens.length <= 1){
            throw new IncompleteTaskException("Description of deadline cannot be empty!");
        }
        String[] deadline_tokens = this.output.split("/by");
        if(deadline_tokens.length <= 1){
            throw new IncompleteTaskException("Deadline description missing /by!");
        }
        System.out.println("Got it. I've added this task:");
        String end_date = deadline_tokens[1];
        String[] deadline = Arrays.copyOfRange(deadline_tokens[0].split(" "), 1, deadline_tokens[0].split(" ").length);
        System.out.println("  [D][] " + String.join(" ", deadline) + " (by: " + end_date + ")");
        this.list[this.ptr] = new Deadlines(false, String.join(" ", deadline));
        this.ptr++;
        System.out.println("Now you have " + this.ptr + " tasks in the list.");
        System.out.println("____________________________________");
    }

    public void goodbye(){
        System.out.println(" Bye. Hope to see you again soon!");
    }
}
