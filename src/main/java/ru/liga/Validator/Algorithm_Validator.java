package ru.liga.Validator;

import ru.liga.Algorithm.Currency_Forecaster;
import ru.liga.Exception.Invalid_Algorithm_Exception;
import ru.liga.Type.Algorithm_Types;
import ru.liga.Type.Command_Options;
import ru.liga.Type.Error_Messages;

import java.util.Map;

public class Algorithm_Validator implements Validator<Currency_Forecaster> {

    @Override
    public Currency_Forecaster validateAndGet(Map<String, String> args) {
        if (!args.containsKey(Command_Options.ALGORITHM.getKey())) {
            throw new Invalid_Algorithm_Exception(Error_Messages.INVALID_NO_ALG.getText());
        } else {
            try {
                return Algorithm_Types.valueOf(args.get(Command_Options.ALGORITHM.getKey()).toUpperCase()).getAlgorithm();
            } catch (IllegalArgumentException e) {
                throw new Invalid_Algorithm_Exception(Error_Messages.INVALID_ALGORITHM.getText());
            }
        }
    }
}
