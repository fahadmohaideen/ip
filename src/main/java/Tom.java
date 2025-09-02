import java.util.Arrays;
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
            String[] tokens = output.toLowerCase().split(" ");
            System.out.println("____________________________________");
            switch (tokens[0]){
                case "bye":
                    goodbye();
                    return;
                case "list":
                    for(int x=0; x<ptr; x++){
                        System.out.print(x+1);
                        list[x].print();
                    }
                    System.out.println("____________________________________");
                    break;
                case "mark":
                    int index = Integer.parseInt(tokens[1]) - 1;
                    list[index].mark();
                    System.out.println("____________________________________");
                    break;
                case "unmark":
                    int idx = Integer.parseInt(tokens[1]) - 1;
                    list[idx].unmark();
                    System.out.println("____________________________________");
                    break;
                case "todo":
                    System.out.println("Got it. I've added this task:");
                    String[] task = Arrays.copyOfRange(tokens, 1, tokens.length);
                    System.out.println("  [T][] " + String.join(" ", task));
                    list[ptr] = new Task(false, String.join(" ", task));
                    ptr++;
                    System.out.println("Now you have " + ptr + " tasks in the list.");
                    System.out.println("____________________________________");
                    break;
                case "event":
                    System.out.println("Got it. I've added this task:");
                    String[] event_tokens = output.split("/from | /to");
                    String time_start = event_tokens[1];
                    String time_end = event_tokens[2];
                    String[] event = Arrays.copyOfRange(event_tokens[0].split(" "), 1, event_tokens[0].split(" ").length);
                    System.out.println("  [E][] " + String.join(" ", event) + " (from: " + time_start + " to: " + time_end + ")");
                    list[ptr] = new Event(false, String.join(" ", event));
                    ptr++;
                    System.out.println("Now you have " + ptr + " tasks in the list.");
                    System.out.println("____________________________________");
                    break;
                case "deadline":
                    System.out.println("Got it. I've added this task:");
                    String[] deadline_tokens = output.split("/by");
                    String end_date = deadline_tokens[1];
                    String[] deadline = Arrays.copyOfRange(deadline_tokens[0].split(" "), 1, deadline_tokens[0].split(" ").length);
                    System.out.println("  [D][] " + String.join(" ", deadline) + " (by: " + end_date + ")");
                    list[ptr] = new Task(false, String.join(" ", deadline));
                    ptr++;
                    System.out.println("Now you have " + ptr + " tasks in the list.");
                    System.out.println("____________________________________");
                    break;

            }
            /*if(!output.equalsIgnoreCase("Bye")){
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
            }*/
        }
    }
}
