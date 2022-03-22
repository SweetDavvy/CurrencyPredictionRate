package ru.liga.Validator;

import ru.liga.Exception.InvalidArgumentException;
import ru.liga.Type.CommandOptions;
import ru.liga.Type.CommandType;
import ru.liga.Type.ErrorMessages;

import java.util.Map;

public class CommandTypeValidator implements Validator<CommandType> {

    @Override
    public CommandType validateAndGet(Map<String, String> args) {
        try {
            return CommandType.valueOf(args.get(CommandOptions.COMMAND.getKey()).toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException(ErrorMessages.INVALID_COMMAND.getText());
        }
    }
}
