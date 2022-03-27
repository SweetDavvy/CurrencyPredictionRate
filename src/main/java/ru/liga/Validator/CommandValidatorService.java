package ru.liga.Validator;

import ru.liga.Algorithm.CurrencyForecaster;
import ru.liga.Input.Period;
import ru.liga.Type.CommandType;
import ru.liga.Type.CurrencyTypes;

import java.util.List;
import java.util.Map;

public class CommandValidatorService {

    private final Map<String, String> args;

    public CommandValidatorService(Map<String, String> args) {
        this.args = args;
    }

    public Period validateAndGetPeriod() {
        return new PeriodValidator().validateAndGet(args);
    }

    public CommandType validateAndGetCommandType() {
        return new CommandTypeValidator().validateAndGet(args);
    }

    public List<CurrencyTypes> validateAndGetCurrency() {
        return new CurrencyValidator().validateAndGet(args);
    }

    public CurrencyForecaster validateAndGetAlgorithm() {
        return new AlgorithmValidator().validateAndGet(args);
    }

    public Boolean validateAndGetOutput() {
        return new OutputValidator().validateAndGet(args);
    }
}
