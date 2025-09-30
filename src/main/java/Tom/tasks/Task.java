package Tom.tasks;

public class Task{
    protected boolean marked;
    protected String task;
    protected String task_description;

    public Task(boolean marked_as_done, String task_name, String description){
        marked = marked_as_done;
        task = task_name;
        task_description = description;
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
            System.out.println(". [T][X] " + this.task_description);
        }
        else {
            System.out.println(". [T][] " + this.task_description);
        }
    }

    public String getDescription(){
        return this.task_description;
    }
}
