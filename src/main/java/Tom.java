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
        String[] list = new String[100];
        Scanner input = new Scanner(System.in);
        int ptr = 0;
        while(true) {
            String output = input.nextLine();
            if(!output.equalsIgnoreCase("Bye")){
                System.out.println("____________________________________");
                if (output.equalsIgnoreCase("list")){
                    for(int x=0; x<ptr; x++){
                        System.out.println(x+1 + ". " + list[x]);
                    }
                }
                else{
                    System.out.println("added:" + output);
                    list[ptr] = output;
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
