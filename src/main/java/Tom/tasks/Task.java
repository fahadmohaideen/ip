package Tom.tasks;

public class Task{
    protected boolean marked;
    protected String task;

    public Task(boolean marked_as_done, String task_name){
        marked = marked_as_done;
        task = task_name;
    }

    public void mark(){
        marked = true;
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("   [X] " + task);
    }

    public void unmark(){
        marked = false;
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("   [ ] " + task);
    }

    public void print(){
        if(this.marked){
            System.out.println(". [T][X] " + task);
        }
        else {
            System.out.println(". [T][] " + task);
        }
    }
}
