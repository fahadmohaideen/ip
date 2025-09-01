import java.util.Scanner;

public class Tom {
    public static void greeting(){
        System.out.println(" Hello! I'm Tom");
        System.out.println(" What can I do for you?");
    }

    public static void goodbye(){
        System.out.println(" Bye. Hope to see you again soon!");
    }

    public static void main(String[] args){
        String name = """
                 _____                     \s
                |_   _| ______   _________ \s
                  | |  |  __  | |  _   _  |\s
                  | |  | |__| | | | | | | |\s
                  |_|  |______| |_| |_| |_|\s
                """;
        System.out.println("Hello from\n" + name);
        System.out.println("____________________________________");
        greeting();
        System.out.println("____________________________________");
        Task[] list = new Task[100];
        Scanner input = new Scanner(System.in);
        int ptr = 0;
        while(true) {
            String output = input.nextLine();
            if(!output.equalsIgnoreCase("Bye")){
                System.out.println("____________________________________");
                if (output.equalsIgnoreCase("list")){
                    for(int x=0; x<ptr; x++){
                        System.out.println("Here are the tasks in your list:");
                        if(list[x].marked){
                            System.out.println(x+1 + ". [X] " + list[x].task);
                        }
                        else {
                            System.out.println(x + 1 + ". [ ] " + list[x].task);
                        }
                    }
                }
                else if (output.split(" ")[0].equalsIgnoreCase("mark")){
                    int index = Integer.parseInt(output.split(" ")[1]) - 1;
                    list[index].mark();
                }

                else if (output.split(" ")[0].equalsIgnoreCase("unmark")){
                    int index = Integer.parseInt(output.split(" ")[1]) - 1;
                    list[index].unmark();
                }

                else{
                    System.out.println("added:" + output);
                    Task new_task = new Task(false, output);
                    list[ptr] = new_task;
                    ptr++;
                }
                System.out.println("____________________________________");
            }
            else{
                break;
            }
        }

        goodbye();
        System.out.println("____________________________________");
    }
}
