package Tom.io;

import java.util.Scanner;

public class Ui {
    public String output;
    protected Scanner input;

    public Ui(){
        this.input = new Scanner(System.in);
    }

    public void greeting(){
            System.out.println(" Hello! I'm Tom.Tom");
            System.out.println(" What can I do for you?");
            String name = """
                 _____                     \s
                |_   _| ______   _________ \s
                  | |  |  __  | |  _   _  |\s
                  | |  | |__| | | | | | | |\s
                  |_|  |______| |_| |_| |_|\s
                """;
            System.out.println("Hello from\n" + name);
            System.out.println("____________________________________");
            System.out.println("____________________________________");
    }

    public String readCommand(){
        this.output = input.nextLine();
        return this.output;
    }

    public void showError(String message){
        System.out.println(message);
    }

    public void showLine(){
        System.out.println("____________________________________");
    }

    public void goodbye(){
        System.out.println(" Bye. Hope to see you again soon!");
    }
}
