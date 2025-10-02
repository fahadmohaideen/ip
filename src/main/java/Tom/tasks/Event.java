package Tom.tasks;

import java.time.LocalDateTime;

public class Event extends Task {

    public Event(boolean marked_as_done, String event, String description, LocalDateTime start, LocalDateTime end){
        super(marked_as_done, event, description, start, end);
    }

    public void print(){
        if(this.marked){
            System.out.println(". [E][X] " + this.task + this.task_description);
        }
        else {
            System.out.println(". [E][] " + this.task + this.task_description);
        }
    }

    public String getDescription(){
        String description;
        if(this.marked){
            description = "[E][X] " + this.task + this.task_description;
        }
        else{
            description = "[E][] " + this.task + this.task_description;
        }
        return description;
    }
}
