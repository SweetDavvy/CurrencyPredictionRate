package ru.liga.Validator;

import ru.liga.Exception.InvalidCurrencyException;
import ru.liga.Type.CommandOptions;
import ru.liga.Type.CurrencyTypes;
import ru.liga.Type.ErrorMessages;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CurrencyValidator implements Validator<List<CurrencyTypes>> {

    private static final int CURRENCIES_LIMIT = 5;

    @Override
    public List<CurrencyTypes> validateAndGet(Map<String, String> args) {
        if (!args.containsKey(CommandOptions.CURRENCY.getKey())) {
            throw new InvalidCurrencyException(ErrorMessages.INVALID_CURRENCY.getText());
        } else {
            List<String> currencyStrings = Arrays.asList(args.get(CommandOptions.CURRENCY.getKey()).toUpperCase().split(","));
            if (currencyStrings.size() > CURRENCIES_LIMIT) {
                throw new InvalidCurrencyException(ErrorMessages.INVALID_CURRENCY_AMOUNT.getText());
            }
            try {
                List<CurrencyTypes> currencyTypes = currencyStrings.stream()
                        .map(CurrencyTypes::valueOf)
                        .distinct()
                        .collect(Collectors.toList());
                if (currencyStrings.size() != currencyTypes.size()) {
                    throw new InvalidCurrencyException(ErrorMessages.INVALID_SAME_CURRENCY.getText());
                }
                if (currencyTypes.size() > 1 && !args.containsKey(CommandOptions.OUTPUT.getKey())) {
                    throw new InvalidCurrencyException(ErrorMessages.INVALID_NO_OUTPUT.getText());
                }
                return currencyTypes;
            } catch (IllegalArgumentException e) {
                throw new InvalidCurrencyException(ErrorMessages.INVALID_CURRENCY.getText());
            }
        }
    }
}
