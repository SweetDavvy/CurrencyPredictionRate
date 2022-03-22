package ru.liga.Validator;

import ru.liga.Algorithm.Currency_Forecaster;
import ru.liga.Input.Period;
import ru.liga.Type.Command_Type;
import ru.liga.Type.Currency_Types;

import java.util.List;
import java.util.Map;

public class Command_Validator_Service {

    private final Map<String, String> args;

    public Command_Validator_Service(Map<String, String> args) {
        this.args = args;
    }

    public Period validateAndGetPeriod() {
        return new Period_Validator().validateAndGet(args);
    }

    public Command_Type validateAndGetCommandType() {
        return new Command_Type_Validator().validateAndGet(args);
    }

    public List<Currency_Types> validateAndGetCurrency() {
        return new Currency_Validator().validateAndGet(args);
    }

    public Currency_Forecaster validateAndGetAlgorithm() {
        return new Algorithm_Validator().validateAndGet(args);
    }

    public Boolean validateAndGetOutput() {
        return new Output_Validator().validateAndGet(args);
    }
}
