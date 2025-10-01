package Tom.io;

import Tom.commands.*;

public class Parser {

    public static Command parser(String cmd_text, Ui ui){
        String[] tokens = cmd_text.toLowerCase().split(" ");
        switch (tokens[0]){
            case "bye":
                return new ExitCommand();
            case "list":
                return new ListCommand();
            case "mark":
                return new MarkCommand(true);
            case "unmark":
                return new MarkCommand(false);
            case "delete":
                return new DeleteCommand();
            case "todo":
                return new AddCommand("task");
            case "event":
                return new AddCommand("event");
            case "deadline":
                return new AddCommand("deadline");
            default:
                System.out.println("Unknown command!");
                ui.showLine();
                return null;
        }
    }
}
