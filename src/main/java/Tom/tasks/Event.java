package Tom.tasks;

public class Event extends Task {

    public Event(boolean marked_as_done, String event){
        super(marked_as_done, event);
    }

    public void print(){
        if(this.marked){
            System.out.println(". [E][X] " + this.task);
        }
        else {
            System.out.println(". [E][] " + this.task);
        }
    }
}
