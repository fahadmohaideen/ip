package Tom.tasks;

public class Deadlines extends Task {

    public Deadlines(boolean marked_as_done, String event, String description){
        super(marked_as_done, event, description);
    }

    public void print(){
        if(this.marked){
            System.out.println(". [D][X] " + this.task_description);
        }
        else {
            System.out.println(". [D][] " + this.task_description);
        }
    }
}
