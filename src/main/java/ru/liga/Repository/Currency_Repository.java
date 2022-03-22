package ru.liga.Repository;

import ru.liga.Сurrency.Currency_Rate;
import ru.liga.Type.Currency_Types;

import java.time.LocalDate;
import java.util.List;

public interface Currency_Repository {

    void addAll(Currency_Types type);
    List<Currency_Rate> getRates(Currency_Types type, int amount);
    List<Currency_Rate> getAllRates(Currency_Types type);
    Currency_Rate getRateForDate(Currency_Types type, LocalDate date);
}
