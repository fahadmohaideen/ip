package Tom.tasks;

import java.time.LocalDateTime;

public class Deadlines extends Task {

    public Deadlines(boolean marked_as_done, String deadline, String description, LocalDateTime start, LocalDateTime end){
        super(marked_as_done, deadline, description, start, end);
    }

    public void print(){
        if(this.marked){
            System.out.println(". [D][X] " + this.task + this.task_description);
        }
        else {
            System.out.println(". [D][] " + this.task + this.task_description);
        }
    }

    public String getDescription(){
        String description;
        if(this.marked){
            description = "[D][X] " + this.task + this.task_description;
        }
        else{
            description = "[D][] " + this.task + this.task_description;
        }
        return description;
    }
}
