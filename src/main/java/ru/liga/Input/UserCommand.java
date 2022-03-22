package ru.liga.Input;

import lombok.Getter;
import ru.liga.Algorithm.CurrencyForecaster;
import ru.liga.Exception.BaseException;
import ru.liga.Type.CommandType;
import ru.liga.Type.CurrencyTypes;
import ru.liga.Validator.*;

import java.util.List;
import java.util.Map;

@Getter
public class UserCommand {

    private final String inputString;
    private final CommandType commandType;
    private final List<CurrencyTypes> currencyTypes;
    private final Period period;
    private final CurrencyForecaster algorithm;
    private final boolean isGraph;

    public UserCommand(String inputString, CommandType commandType,
                       List<CurrencyTypes> currencyTypes, Period period,
                       CurrencyForecaster algorithm, boolean isGraph) {
        this.inputString = inputString;
        this.commandType = commandType;
        this.currencyTypes = currencyTypes;
        this.period = period;
        this.algorithm = algorithm;
        this.isGraph = isGraph;
    }

    /**
     * Создает корректный User_Command объект
     * Если не работает во время проверки:
     * @throws BaseException - с сообщением об ошибке
     * @return UserCommand
     */
    public static UserCommand createFromString(String inputString) {
        Map<String, String> args = InputStringParser.parse(inputString);
        Command_Validator_Service validator = new Command_Validator_Service(args);
        CommandType commandType = validator.validateAndGetCommandType();
        List<CurrencyTypes> currencyTypes = validator.validateAndGetCurrency();
        Period period = validator.validateAndGetPeriod();
        CurrencyForecaster algorithm = validator.validateAndGetAlgorithm();
        Boolean isGraph = validator.validateAndGetOutput();
        return new UserCommand(inputString, commandType, currencyTypes, period, algorithm, isGraph);
    }
}
