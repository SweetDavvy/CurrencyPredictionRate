package ru.liga.Validator;

import ru.liga.Exception.Invalid_Currency_Exception;
import ru.liga.Type.Command_Options;
import ru.liga.Type.Currency_Types;
import ru.liga.Type.Error_Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Currency_Validator implements Validator<List<Currency_Types>> {

    private static final int CURRENCIES_LIMIT = 5;

    @Override
    public List<Currency_Types> validateAndGet(Map<String, String> args) {
        if (!args.containsKey(Command_Options.CURRENCY.getKey())) {
            throw new Invalid_Currency_Exception(Error_Messages.INVALID_CURRENCY.getText());
        } else {
            List<String> currencyStrings = Arrays.asList(args.get(Command_Options.CURRENCY.getKey()).toUpperCase().split(","));
            if (currencyStrings.size() > CURRENCIES_LIMIT) {
                throw new Invalid_Currency_Exception(Error_Messages.INVALID_CURRENCY_AMOUNT.getText());
            }
            try {
                List<Currency_Types> currencyTypes = currencyStrings.stream()
                        .map(Currency_Types::valueOf)
                        .distinct()
                        .collect(Collectors.toList());
                if (currencyStrings.size() != currencyTypes.size()) {
                    throw new Invalid_Currency_Exception(Error_Messages.INVALID_SAME_CURRENCY.getText());
                }
                if (currencyTypes.size() > 1 && !args.containsKey(Command_Options.OUTPUT.getKey())) {
                    throw new Invalid_Currency_Exception(Error_Messages.INVALID_NO_OUTPUT.getText());
                }
                return currencyTypes;
            } catch (IllegalArgumentException e) {
                throw new Invalid_Currency_Exception(Error_Messages.INVALID_CURRENCY.getText());
            }
        }
    }
}
