package ru.liga.CSVReader;

import ru.liga.Сurrency.CurrencyRate;
import ru.liga.Type.CurrencyTypes;

import java.util.List;

public interface CurrencyParser {
    /**
     * Чтение данных из файла
     * @param type тип валюты
     * @return list валют из источника данных
     */
    List<CurrencyRate> getCurrencyRates(CurrencyTypes type);
}
