import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IncompleteTaskException {
        Scanner input = new Scanner(System.in);
        Tom tom = new Tom(0, new Task[100], " ");
        tom.greeting();
        while(true) {
            tom.output = input.nextLine();
            String[] tokens = tom.output.toLowerCase().split(" ");
            System.out.println("____________________________________");
            switch (tokens[0]){
                case "bye":
                    tom.goodbye();
                    return;
                case "list":
                    for(int x=0; x<tom.ptr; x++){
                        System.out.print(x+1);
                        tom.list[x].print();
                    }
                    System.out.println("____________________________________");
                    break;
                case "mark":
                    int index = Integer.parseInt(tokens[1]) - 1;
                    tom.list[index].mark();
                    System.out.println("____________________________________");
                    break;
                case "unmark":
                    int idx = Integer.parseInt(tokens[1]) - 1;
                    tom.list[idx].unmark();
                    System.out.println("____________________________________");
                    break;
                case "todo":
                    try {
                        tom.addTask();
                    } catch (IncompleteTaskException e) {
                        System.out.println(e.getMessage());
                        System.out.println("____________________________________");
                    }
                    break;
                case "event":
                    try {
                        tom.addEvent();
                    }
                    catch(IncompleteTaskException e){
                        System.out.println(e.getMessage());
                        System.out.println("____________________________________");
                    }
                    break;
                case "deadline":
                    try {
                        tom.addDeadline();
                    }
                    catch(IncompleteTaskException e){
                        System.out.println(e.getMessage());
                        System.out.println("____________________________________");
                    }
                    break;

                default:
                    System.out.println("Unknown command!");
                    System.out.println("____________________________________");
            }
        }
    }
}
