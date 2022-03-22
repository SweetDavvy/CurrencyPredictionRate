package ru.liga.Input;

import ru.liga.Exception.Invalid_Argument_Exception;
import ru.liga.Type.Error_Messages;
import ru.liga.Type.Command_Options;

import java.util.HashMap;
import java.util.Map;

public class Input_String_Parser {

    private static final int COMMAND_INDEX = 0;
    private static final int CURRENCY_INDEX = 1;

    private Input_String_Parser() {
    }

    public static Map<String, String> parse(String inputString) {
        Map<String, String> args = new HashMap<>();
        String[] words = inputString.split(" ");
        if (words.length % 2 != 0) {
            throw new Invalid_Argument_Exception(Error_Messages.INVALID_INPUT_FORMAT.getText());
        }
        args.put(Command_Options.COMMAND.getKey(), words[COMMAND_INDEX]);
        args.put(Command_Options.CURRENCY.getKey(), words[CURRENCY_INDEX]);
        try {
            for (int i = CURRENCY_INDEX + 1; i < words.length; i++) {
                if (words[i].startsWith("-")) {
                    args.put(words[i], words[i + 1]);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            throw new Invalid_Argument_Exception(Error_Messages.INVALID_INPUT_FORMAT.getText());
        }
        return args;
    }
}
