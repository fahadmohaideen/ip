package Tom.tasks;

public class Deadlines extends Task {

    public Deadlines(boolean marked_as_done, String event){
        super(marked_as_done, event);
    }

    public void print(){
        if(this.marked){
            System.out.println(". [D][X] " + this.task);
        }
        else {
            System.out.println(". [D][] " + this.task);
        }
    }
}
