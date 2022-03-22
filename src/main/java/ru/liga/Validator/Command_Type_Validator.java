package ru.liga.Validator;

import ru.liga.Exception.Invalid_Argument_Exception;
import ru.liga.Type.Command_Options;
import ru.liga.Type.Command_Type;
import ru.liga.Type.Error_Messages;

import java.util.Map;

public class Command_Type_Validator implements Validator<Command_Type> {

    @Override
    public Command_Type validateAndGet(Map<String, String> args) {
        try {
            return Command_Type.valueOf(args.get(Command_Options.COMMAND.getKey()).toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new Invalid_Argument_Exception(Error_Messages.INVALID_COMMAND.getText());
        }
    }
}
