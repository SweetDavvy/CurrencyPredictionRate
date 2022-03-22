package ru.liga.Input;

import lombok.Getter;
import ru.liga.Algorithm.Currency_Forecaster;
import ru.liga.Exception.Base_Exception;
import ru.liga.Type.Command_Type;
import ru.liga.Type.Currency_Types;
import ru.liga.Validator.*;

import java.util.List;
import java.util.Map;

@Getter
public class User_Command {

    private final String inputString;
    private final Command_Type commandType;
    private final List<Currency_Types> currencyTypes;
    private final Period period;
    private final Currency_Forecaster algorithm;
    private final boolean isGraph;

    public User_Command(String inputString, Command_Type commandType,
                        List<Currency_Types> currencyTypes, Period period,
                        Currency_Forecaster algorithm, boolean isGraph) {
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
     * @throws Base_Exception - с сообщением об ошибке
     * @return UserCommand
     */
    public static User_Command createFromString(String inputString) {
        Map<String, String> args = Input_String_Parser.parse(inputString);
        Command_Validator_Service validator = new Command_Validator_Service(args);
        Command_Type commandType = validator.validateAndGetCommandType();
        List<Currency_Types> currencyTypes = validator.validateAndGetCurrency();
        Period period = validator.validateAndGetPeriod();
        Currency_Forecaster algorithm = validator.validateAndGetAlgorithm();
        Boolean isGraph = validator.validateAndGetOutput();
        return new User_Command(inputString, commandType, currencyTypes, period, algorithm, isGraph);
    }
}
