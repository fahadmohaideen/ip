package Tom.tasks;

public class Event extends Task {

    public Event(boolean marked_as_done, String event, String description){
        super(marked_as_done, event, description);
    }

    public void print(){
        if(this.marked){
            System.out.println(". [E][X] " + this.task_description);
        }
        else {
            System.out.println(". [E][] " + this.task_description);
        }
    }
}
