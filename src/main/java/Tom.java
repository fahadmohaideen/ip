import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Tom {
    public static void greeting(){
        System.out.println(" Hello! I'm Tom");
        System.out.println(" What can I do for you?");
    }

    public static void goodbye(){
        System.out.println(" Bye. Hope to see you again soon!");
    }

    public static void main(String[] args) throws IOException {
        String name = " _____                      \n"
                    + "|_   _| ______   _________  \n"
                    + "  | |  |  __  | |  _   _  | \n"
                    + "  | |  | |__| | | | | | | | \n"
                    + "  |_|  |______| |_| |_| |_| \n";
        System.out.println("Hello from\n" + name);
        System.out.println("____________________________________");
        greeting();
        System.out.println("____________________________________");
        Scanner input = new Scanner(System.in);
        while(true) {
            String output = input.nextLine();
            if(!output.equalsIgnoreCase("Bye")){
                System.out.println("____________________________________");
                System.out.println(output);
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
