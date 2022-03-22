package ru.liga.Input;

import java.util.Scanner;

/**
 * Ввод
 */

public class Console_Input implements Input {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public User_Command getUserCommand() {
        return User_Command.createFromString(scanner.nextLine());
    }
}
