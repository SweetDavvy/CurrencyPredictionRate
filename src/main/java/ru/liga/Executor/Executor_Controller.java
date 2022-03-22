package ru.liga.Executor;

import ru.liga.Input.User_Command;
import ru.liga.Repository.Currency_Repository;

public class Executor_Controller {

    private final Currency_Repository repository;

    public Executor_Controller(Currency_Repository repository) {
        this.repository = repository;
    }

    public Command_Executor getExecutor(User_Command command) {
        if (command.isGraph()) {
            return new Graph_Command_Executor(repository, command);
        } else {
            return new Data_Command_Executor(repository, command);
        }
    }
}
