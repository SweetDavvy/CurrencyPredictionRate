package ru.liga.Output;

/**
 * Вывод в консоль
 */
public class ConsoleOutput implements Output {
    @Override
    public void print(String message) {
        System.out.println(message);
    }
}
