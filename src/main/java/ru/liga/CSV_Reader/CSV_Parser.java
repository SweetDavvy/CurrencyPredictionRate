package ru.liga.CSV_Reader;

import ru.liga.Сurrency.Currency_Rate;
import ru.liga.Type.Currency_Types;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Чтение CSV фалйа с помощью BufferedReader;
 */
public class CSV_Parser implements Currency_Parser {

    private final Map<String, Currency_Types> localizedCurrencyNames = new HashMap<>();
    private static final String FILENAME_SUFFIX = "_F01_02_2005_T05_03_2022.csv";

    public CSV_Parser() {
        localizedCurrencyNames.put("Евро", Currency_Types.EUR);
        localizedCurrencyNames.put("Турецкая лира", Currency_Types.TRY);
        localizedCurrencyNames.put("Доллар США", Currency_Types.USD);
        localizedCurrencyNames.put("Армянский Драм", Currency_Types.AMD);
        localizedCurrencyNames.put("Болгарский лев", Currency_Types.BGN);
    }

    /**
     * Чтение CSV фалйа с путем получение данных о валютах
     * @param type тип валюты
     * @return List Currency_Rate
     */
    @Override
    public List<Currency_Rate> getCurrencyRates(Currency_Types type) {
        List<Currency_Rate> rates = new ArrayList<>();
        try (InputStream is = this.getClass().getResourceAsStream("/" + type + FILENAME_SUFFIX);
             BufferedReader br = new BufferedReader(new InputStreamReader(is, "windows-1251"))) {
            br.lines().skip(1).forEach(l -> rates.add(parseCurrencyRate(l)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        rates.sort((currencyRate1, currencyRate2) -> currencyRate2.getDate().compareTo(currencyRate1.getDate()));
        return rates;
    }

    /**
     * Доастает строку для создания Currency_rate
     * @param line строка
     * @return new CurrencyRate объект
     */
    private Currency_Rate parseCurrencyRate(String line) {
        Scanner scanner = new Scanner(line);
        scanner.useDelimiter(";");
        scanner.next();
        LocalDate date = LocalDate.parse(scanner.next(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        Double rate = Double.parseDouble(scanner.next().replace("\"", "").replace(",", "."));
        Currency_Types type = localizedCurrencyNames.get(scanner.next());
        scanner.close();
        return new Currency_Rate(date, type, rate);
    }
}
