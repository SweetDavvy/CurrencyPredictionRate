package ru.liga.Output;

/**
 * Вывод в консоль
 */
public class Console_Output implements Output {
    @Override
    public void print(String message) {
        System.out.println(message);
    }
}
