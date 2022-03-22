package ru.liga.Executor;

import ru.liga.Input.UserCommand;
import ru.liga.Repository.CurrencyRepository;

public class ExecutorController {

    private final CurrencyRepository repository;

    public ExecutorController(CurrencyRepository repository) {
        this.repository = repository;
    }

    public CommandExecutor getExecutor(UserCommand command) {
        if (command.isGraph()) {
            return new GraphCommandExecutor(repository, command);
        } else {
            return new DataCommandExecutor(repository, command);
        }
    }
}
