package ru.liga.Validator;

import ru.liga.Algorithm.CurrencyForecaster;
import ru.liga.Input.Period;
import ru.liga.Type.CommandType;
import ru.liga.Type.CurrencyTypes;

import java.util.List;
import java.util.Map;

public class Command_Validator_Service {

    private final Map<String, String> args;

    public Command_Validator_Service(Map<String, String> args) {
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
