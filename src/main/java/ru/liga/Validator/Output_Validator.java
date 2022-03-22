package ru.liga.Validator;

import ru.liga.Exception.Invalid_Output_Exception;
import ru.liga.Type.Command_Options;
import ru.liga.Type.Error_Messages;

import java.util.Map;

public class Output_Validator implements Validator<Boolean> {

    @Override
    public Boolean validateAndGet(Map<String, String> args) {
        if (!args.containsKey(Command_Options.OUTPUT.getKey())) {
            return false;
        } else if (!args.get(Command_Options.OUTPUT.getKey()).equals("graph")) {
            throw new Invalid_Output_Exception(Error_Messages.INVALID_OUTPUT.getText());
        } else {
            return true;
        }
    }
}
