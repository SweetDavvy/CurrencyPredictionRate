package ru.liga.CSV_Reader;

import ru.liga.Сurrency.Currency_Rate;
import ru.liga.Type.Currency_Types;

import java.util.List;

public interface Currency_Parser {
    /**
     * Чтение данных из файла
     * @param type тип валюты
     * @return list валют из источника данных
     */
    List<Currency_Rate> getCurrencyRates(Currency_Types type);
}
