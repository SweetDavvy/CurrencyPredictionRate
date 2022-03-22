package ru.liga.Executor;

import ru.liga.Сurrency.CurrencyRate;
import ru.liga.Type.CurrencyTypes;
import ru.liga.Input.UserCommand;
import ru.liga.Output.CommandResult;
import ru.liga.Repository.CurrencyRepository;

import java.util.List;

public class DataCommandExecutor implements CommandExecutor {

    private final CurrencyRepository repository;
    private final UserCommand command;

    public DataCommandExecutor(CurrencyRepository repository, UserCommand userCommand) {
        this.repository = repository;
        this.command = userCommand;
    }

    @Override
    public CommandResult execute() {
        return new CommandResult(getForecast(command.getCurrencyTypes().get(0)));

    }

    private List<CurrencyRate> getForecast(CurrencyTypes type) {
        return command.getAlgorithm().getForecast(repository, type, command.getPeriod());
    }

}
