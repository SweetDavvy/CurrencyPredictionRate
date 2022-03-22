package ru.liga.Executor;

import ru.liga.Сurrency.Currency_Rate;
import ru.liga.Type.Currency_Types;
import ru.liga.Input.User_Command;
import ru.liga.Output.Command_Result;
import ru.liga.Repository.Currency_Repository;

import java.util.List;

public class Data_Command_Executor implements Command_Executor {

    private final Currency_Repository repository;
    private final User_Command command;

    public Data_Command_Executor(Currency_Repository repository, User_Command userCommand) {
        this.repository = repository;
        this.command = userCommand;
    }

    @Override
    public Command_Result execute() {
        return new Command_Result(getForecast(command.getCurrencyTypes().get(0)));

    }

    private List<Currency_Rate> getForecast(Currency_Types type) {
        return command.getAlgorithm().getForecast(repository, type, command.getPeriod());
    }

}
