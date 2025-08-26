public class Tom {
    public static void greeting(){
        System.out.println(" Hello! I'm Tom");
        System.out.println(" What can I do for you?");
    }

    public static void goodbye(){
        System.out.println(" Bye. Hope to see you again soon!");
    }

    public static void main(String[] args) {
        String name = " _____                      \n"
                    + "|_   _| ______   _________  \n"
                    + "  | |  |  __  | |  _   _  | \n"
                    + "  | |  | |__| | | | | | | | \n"
                    + "  |_|  |______| |_| |_| |_| \n";
        System.out.println("Hello from\n" + name);
        System.out.println("____________________________________");
        greeting();
        System.out.println("____________________________________");
        goodbye();
        System.out.println("____________________________________");
    }
}
