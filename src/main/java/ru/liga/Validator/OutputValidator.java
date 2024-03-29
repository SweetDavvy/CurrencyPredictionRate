package ru.liga.Validator;

import ru.liga.Exception.InvalidOutputException;
import ru.liga.Type.CommandOptions;
import ru.liga.Type.ErrorMessages;

import java.util.Map;

public class OutputValidator implements Validator<Boolean> {

    @Override
    public Boolean validateAndGet(Map<String, String> args) {
        if (!args.containsKey(CommandOptions.OUTPUT.getKey())) {
            return false;
        } else if (!args.get(CommandOptions.OUTPUT.getKey()).equals("graph")) {
            throw new InvalidOutputException(ErrorMessages.INVALID_OUTPUT.getText());
        } else {
            return true;
        }
    }
}
