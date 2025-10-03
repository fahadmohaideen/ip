package Tom.io;
import Tom.commands.*;
import Tom.exceptions.IncompleteTaskException;
import Tom.exceptions.TooManyArgumentsException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

/**
 * The Parser class is responsible for interpreting user input and converting it
 * into executable Command objects. It handles the parsing of various command types
 * including task creation, listing, searching, and system commands.
 * This class serves as the main input processing component of the application.
 */
public class Parser {

    /**
     * Main parser method that processes raw command text and returns appropriate Command objects.
     * This method acts as the entry point for all command parsing in the system.
     *
     * @param cmd_text the raw command input from the user
     * @param ui the user interface handler for displaying output and errors
     * @return a Command object corresponding to the parsed input, or null for unknown commands
     * @throws IncompleteTaskException when required command parameters are missing
     * @throws TooManyArgumentsException when excessive parameters are provided
     */
    public Command parser(String cmd_text, Ui ui) throws IncompleteTaskException, TooManyArgumentsException {
        String[] tokens = cmd_text.toLowerCase().split(" ");
        switch (tokens[0]){
            case "bye":
                return new ExitCommand();
            case "list":
                return new ListCommand("list_task", null, null);
            case "mark":
                return new MarkCommand(true);
            case "unmark":
                return new MarkCommand(false);
            case "delete":
                return this.parse_delete(cmd_text);
            case "todo":
                return this.parse_task(cmd_text);
            case "event":
                return this.parse_event(cmd_text);
            case "deadline":
                return this.parse_deadline(cmd_text);
            case "search_by_date":
                return this.parse_search_date(cmd_text);
            case "search_by_keyword":
                return this.parse_search_keyword(cmd_text);
            default:
                System.out.println("Unknown command!");
                ui.showLine();
                return null;
        }
    }

    /**
     * Parses keyword search commands to find tasks containing specific text.
     * Expected format: "search_by_keyword [keyword]"
     *
     * @param line_read the raw input line containing the search command
     * @return a ListCommand configured for keyword search
     * @throws IncompleteTaskException when no search keyword is provided
     */
    private Command parse_search_keyword(String line_read) throws IncompleteTaskException {
        String[] tokens = line_read.toLowerCase().split(" ");
        if(tokens.length == 1){
            throw new IncompleteTaskException("Description of keyword search cannot be empty!");
        }
        System.out.println("Here are the tasks matching your keyword search:");
        String[] keyword_array = Arrays.copyOfRange(tokens, 1, tokens.length);
        String keyword = String.join(" ", keyword_array);
        return new ListCommand("search_by_keyword", null, keyword);
    }

    /**
     * Parses date-based search commands to find tasks occurring on specific dates.
     * Expected format: "search_by_date [dd/MM/yyyy] [HHmm]"
     *
     * @param line_read the raw input line containing the date search command
     * @return a ListCommand configured for date-based search
     * @throws IncompleteTaskException when date parameters are missing or incomplete
     */
    private Command parse_search_date(String line_read) throws IncompleteTaskException {
        String[] tokens = line_read.toLowerCase().split(" ");
        if(tokens.length == 1){
            throw new IncompleteTaskException("Description of date search cannot be empty!");
        }
        System.out.println("Here are the events occurring/deadlines due on this date:");
        String date_time_str = tokens[1] + " " + tokens[2];
        LocalDateTime search_date = this.parse_dateTime(date_time_str, "dd/MM/yyyy HHmm");
        return new ListCommand("search_by_date", search_date, null);
    }

    /**
     * Parses todo task creation commands.
     * Expected format: "todo [task description]"
     *
     * @param line_read the raw input line containing the todo command
     * @return an AddCommand configured for creating a generic task
     * @throws IncompleteTaskException when task description is missing
     */
    public Command parse_task(String line_read) throws IncompleteTaskException {
        String[] tokens = line_read.toLowerCase().split(" ");
        if(tokens.length == 1){
            throw new IncompleteTaskException("Description of task cannot be empty!");
        }
        System.out.println("Got it. I've added this task:");
        String[] task = Arrays.copyOfRange(tokens, 1, tokens.length);
        String task_description = "";
        System.out.println("  [T][] " + String.join(" ", task) + task_description);
        return new AddCommand("task", task_description, String.join(" ", task), null, null);
    }

    /**
     * Parses event task creation commands with start and end times.
     * Expected format: "event [description] /from [dd/MM/yyyy HHmm] /to [dd/MM/yyyy HHmm]"
     *
     * @param line_read the raw input line containing the event command
     * @return an AddCommand configured for creating an event task
     * @throws IncompleteTaskException when required time parameters (/from or /to) are missing
     */
    public Command parse_event(String line_read) throws IncompleteTaskException {
        String[] tokens = line_read.toLowerCase().split(" ");
        if(tokens.length <= 1){
            throw new IncompleteTaskException("Description of event cannot be empty!");
        }
        String[] from_check_tokens = line_read.split("/from");
        String[] to_check_tokens = line_read.split("/to");
        String[] event_tokens = line_read.split("/from | /to");
        if(from_check_tokens.length <= 1){
            if(to_check_tokens.length <= 1){
                throw new IncompleteTaskException("Tom.tasks.Event description missing both /from and /to!");
            }
            else{
                throw new IncompleteTaskException("Tom.tasks.Event description missing /from");
            }
        }
        else{
            if(to_check_tokens.length <= 1){
                throw new IncompleteTaskException("Tom.tasks.Event description missing /to!");
            }
        }
        System.out.println("Got it. I've added this task:");
        LocalDateTime start_dateTime = parse_dateTime(event_tokens[1].trim(), "dd/MM/yyyy HHmm");
        LocalDateTime end_dateTime = parse_dateTime(event_tokens[2].trim(), "dd/MM/yyyy HHmm");
        String time_start = start_dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));
        String time_end = end_dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));
        String[] event = Arrays.copyOfRange(event_tokens[0].split(" "), 1, event_tokens[0].split(" ").length);
        String event_description = " (from: " + time_start + " to: " + time_end + ")";
        System.out.println("  [E][] " + String.join(" ", event) + event_description);
        return new AddCommand("event", event_description, String.join(" ", event), start_dateTime, end_dateTime);
    }

    /**
     * Parses deadline task creation commands with due dates.
     * Expected format: "deadline [description] /by [dd/MM/yyyy HHmm]"
     *
     * @param line_read the raw input line containing the deadline command
     * @return an AddCommand configured for creating a deadline task
     * @throws IncompleteTaskException when the /by parameter is missing
     */
    public Command parse_deadline(String line_read) throws IncompleteTaskException {
        String[] tokens = line_read.toLowerCase().split(" ");
        if(tokens.length <= 1){
            throw new IncompleteTaskException("Description of deadline cannot be empty!");
        }
        String[] deadline_tokens = line_read.split("/by");
        if(deadline_tokens.length <= 1){
            throw new IncompleteTaskException("Deadline description missing /by!");
        }
        System.out.println("Got it. I've added this task:");
        LocalDateTime end_dateTime = parse_dateTime(deadline_tokens[1].trim(), "dd/MM/yyyy HHmm");
        String end_date = end_dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));
        String[] deadline = Arrays.copyOfRange(deadline_tokens[0].split(" "), 1, deadline_tokens[0].split(" ").length);
        String deadline_description = " (by: " + end_date + ")";
        System.out.println("  [D][] " + String.join(" ", deadline) + deadline_description);
        return new AddCommand("deadline", deadline_description, String.join(" ", deadline), null, end_dateTime);
    }

    /**
     * Parses task deletion commands by index.
     * Expected format: "delete [index]"
     *
     * @param line_read the raw input line containing the delete command
     * @return a DeleteCommand configured to remove the specified task
     * @throws IncompleteTaskException when no index is specified
     * @throws TooManyArgumentsException when multiple indices are provided
     * @throws NumberFormatException when the provided index is not a valid integer
     */
    public Command parse_delete(String line_read) throws IncompleteTaskException, TooManyArgumentsException {
        String[] tokens = line_read.toLowerCase().split(" ");
        if(tokens.length <= 1){
            throw new IncompleteTaskException("Specify the index you wish to delete!");
        }
        if(tokens.length > 2){
            throw new TooManyArgumentsException("Too many arguments provided, maximum 2!");
        }
        int index = Integer.parseInt(tokens[1]) - 1;
        return new DeleteCommand(index);
    }

    /**
     * Utility method for parsing date-time strings into LocalDateTime objects.
     * Handles DateTimeParseException and returns null on failure.
     *
     * @param dateTimeStr the date-time string to parse
     * @param pattern the expected format pattern (e.g., "dd/MM/yyyy HHmm")
     * @return a LocalDateTime object representing the parsed date-time, or null if parsing fails
     */
    public LocalDateTime parse_dateTime(String dateTimeStr, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return LocalDateTime.parse(dateTimeStr, formatter);
        } catch (DateTimeParseException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
