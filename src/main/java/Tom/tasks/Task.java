package Tom.tasks;

import java.time.LocalDateTime;

public class Task{
    protected boolean marked;
    public String task;
    protected String task_description;
    public LocalDateTime start;
    public LocalDateTime end;

    public Task(boolean marked_as_done, String task_name, String description, LocalDateTime start, LocalDateTime end){
        this.marked = marked_as_done;
        this.task = task_name;
        this.task_description = description;
        this.start = start;
        this.end = end;
    }

    public void mark(){
        marked = true;
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("   [X] " + this.task);
    }

    public void unmark(){
        marked = false;
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("   [ ] " + this.task);
    }

    public void print(){
        if(this.marked){
            System.out.println(". [T][X] " + this.task + this.task_description);
        }
        else {
            System.out.println(". [T][] " + this.task + this.task_description);
        }
    }

    public String getDescription(){
        String description;
        if(this.marked){
            description = "[T][X] " + this.task + this.task_description;
        }
        else{
            description = "[T][] " + this.task + this.task_description;
        }
        return description;
    }
}
