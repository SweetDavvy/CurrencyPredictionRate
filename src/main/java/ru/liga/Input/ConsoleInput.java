package ru.liga.Input;

import java.util.Scanner;

/**
 * Ввод
 */

public class ConsoleInput implements Input {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public UserCommand getUserCommand() {
        return UserCommand.createFromString(scanner.nextLine());
    }
}
