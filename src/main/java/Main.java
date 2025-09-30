import Tom.Tom;
import Tom.exceptions.TooManyArgumentsException;
import Tom.tasks.Task;
import Tom.exceptions.IncompleteTaskException;

import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        ArrayList<Task> task = new ArrayList<>();
        Tom tom = new Tom(task, " ");
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
                    for(int x=0; x<tom.list.size(); x++){
                        System.out.print(x+1);
                        tom.list.get(x).print();
                    }
                    System.out.println("____________________________________");
                    break;
                case "mark":
                    int index = Integer.parseInt(tokens[1]) - 1;
                    tom.list.get(index).mark();
                    System.out.println("____________________________________");
                    break;
                case "unmark":
                    int idx = Integer.parseInt(tokens[1]) - 1;
                    tom.list.get(idx).unmark();
                    System.out.println("____________________________________");
                    break;
                case "delete":
                    try {
                        tom.delete();
                    }
                    catch(IndexOutOfBoundsException | IncompleteTaskException |
                          TooManyArgumentsException | NumberFormatException e){
                        System.out.println(e.getMessage());
                        System.out.println("____________________________________");
                    }
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
