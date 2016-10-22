package seedu.task.logic.parser;

import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.task.commons.exceptions.EmptyValueException;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.AddEventCommand;
import seedu.task.logic.commands.AddTaskCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Responsible for validating and preparing the arguments for AddCommand execution
 * @author kian ming
 */

public class AddParser implements Parser {

    public static final Prefix descriptionPrefix = new Prefix("/desc");
    public static final Prefix deadlinePrefix = new Prefix("/by");
    public static final Prefix durationPrefix = new Prefix("/from");
    
    public AddParser() {}
    
    /**
     * Parses arguments in the context of the add task or event command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    @Override
    public Command prepare(String args){
        
        if (args.isEmpty()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(descriptionPrefix, deadlinePrefix, durationPrefix);
        argsTokenizer.tokenize(args);
        
        try {           
            String name = argsTokenizer.getPreamble().get();
            Optional <String> description = argsTokenizer.getValue(descriptionPrefix);
            Optional <String> duration = argsTokenizer.getValue(durationPrefix);
            Optional <String> deadline = argsTokenizer.getValue(deadlinePrefix);
            
            if (duration.isPresent()) { //Only events have duration
                try {
                    return new AddEventCommand(name, description.orElse(""), duration.orElse(""));
                } catch (IllegalValueException ive) {
                    return new IncorrectCommand(ive.getMessage());
                } catch (NoSuchElementException nsee) {
                    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                }
            } else {
                try {
                    return new AddTaskCommand(name, description.orElse(""), deadline.orElse(""));             
                } catch (IllegalValueException ive) {
                    return new IncorrectCommand(ive.getMessage());
                } catch (NoSuchElementException nsee) {
                    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                }
            }
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (EmptyValueException e) {
            return new IncorrectCommand(e.getMessage());
        }
    } 
    
}
