package seedu.duke.parser;

import seedu.duke.commands.AddBudgetCommand;
import seedu.duke.commands.AddExpenditureCommand;
import seedu.duke.commands.Command;
import seedu.duke.commands.DeleteBudgetCommand;
import seedu.duke.commands.DeleteExpenditureCommand;
import seedu.duke.commands.ExitCommand;
import seedu.duke.commands.InvalidCommand;
import seedu.duke.commands.ListRecordsCommand;

import java.time.LocalDate;


public class Parser {
    private static String[] splitCommandWordAndArgs(String userInput) {
        final String[] split = userInput.trim().split(" ", 2);
        if (split.length == 2) {
            return new String[]{split[0].toLowerCase(), split[1]};
        }
        return new String[]{split[0].toLowerCase(), ""};
    }

    public static String[] splitExpenditureParams(String expenditureParams) {
        return expenditureParams.split(" ", 3);
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        String[] commandTypeAndParams = splitCommandWordAndArgs(userInput);
        String commandType = commandTypeAndParams[0];
        String commandParams = commandTypeAndParams[1].trim();
        Command command;
        switch (commandType) {
        case AddBudgetCommand.COMMAND_WORD:
            command = new AddBudgetCommand();
            break;
        case AddExpenditureCommand.COMMAND_WORD:
            command = decodeAddBudgetCommand(commandParams);
            break;
        case DeleteExpenditureCommand.COMMAND_WORD:
            command = new DeleteExpenditureCommand(commandParams);
            break;
        case DeleteBudgetCommand.COMMAND_WORD:
            command = new DeleteBudgetCommand(commandParams);
            break;
        case ListRecordsCommand.COMMAND_WORD:
            command = new ListRecordsCommand();
            break;
        case ExitCommand.COMMAND_WORD:
            command = new ExitCommand();
            break;
        default:
            command = new InvalidCommand();
            break;
        }
        return command;
    }

    private Command decodeAddBudgetCommand(String commandParams) {
        String[] splitExpenditureParams = Parser.splitExpenditureParams(commandParams);
        String expenditureDescription = splitExpenditureParams[0].trim();
        double expenditureAmount = Double.parseDouble(splitExpenditureParams[1].trim());
        LocalDate expenditureDate = LocalDate.parse(splitExpenditureParams[2].trim());
        return new AddExpenditureCommand(expenditureDescription, expenditureAmount, expenditureDate);
    }


}
