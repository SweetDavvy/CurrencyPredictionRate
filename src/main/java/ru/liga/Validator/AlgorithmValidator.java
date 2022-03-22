package ru.liga.Validator;

import ru.liga.Algorithm.CurrencyForecaster;
import ru.liga.Exception.InvalidAlgorithmException;
import ru.liga.Type.AlgorithmTypes;
import ru.liga.Type.CommandOptions;
import ru.liga.Type.ErrorMessages;

import java.util.Map;

public class AlgorithmValidator implements Validator<CurrencyForecaster> {

    @Override
    public CurrencyForecaster validateAndGet(Map<String, String> args) {
        if (!args.containsKey(CommandOptions.ALGORITHM.getKey())) {
            throw new InvalidAlgorithmException(ErrorMessages.INVALID_NO_ALG.getText());
        } else {
            try {
                return AlgorithmTypes.valueOf(args.get(CommandOptions.ALGORITHM.getKey()).toUpperCase()).getAlgorithm();
            } catch (IllegalArgumentException e) {
                throw new InvalidAlgorithmException(ErrorMessages.INVALID_ALGORITHM.getText());
            }
        }
    }
}
