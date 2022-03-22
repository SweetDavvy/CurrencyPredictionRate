package ru.liga.CSVReader;

import ru.liga.Сurrency.CurrencyRate;
import ru.liga.Type.CurrencyTypes;

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
public class CSVParser implements CurrencyParser {

    private final Map<String, CurrencyTypes> localizedCurrencyNames = new HashMap<>();
    private static final String FILENAME_SUFFIX = "_F01_02_2005_T05_03_2022.csv";

    public CSVParser() {
        localizedCurrencyNames.put("Евро", CurrencyTypes.EUR);
        localizedCurrencyNames.put("Турецкая лира", CurrencyTypes.TRY);
        localizedCurrencyNames.put("Доллар США", CurrencyTypes.USD);
        localizedCurrencyNames.put("Армянский Драм", CurrencyTypes.AMD);
        localizedCurrencyNames.put("Болгарский лев", CurrencyTypes.BGN);
    }

    /**
     * Чтение CSV фалйа с путем получение данных о валютах
     * @param type тип валюты
     * @return List Currency_Rate
     */
    @Override
    public List<CurrencyRate> getCurrencyRates(CurrencyTypes type) {
        List<CurrencyRate> rates = new ArrayList<>();
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
    private CurrencyRate parseCurrencyRate(String line) {
        Scanner scanner = new Scanner(line);
        scanner.useDelimiter(";");
        scanner.next();
        LocalDate date = LocalDate.parse(scanner.next(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        Double rate = Double.parseDouble(scanner.next().replace("\"", "").replace(",", "."));
        CurrencyTypes type = localizedCurrencyNames.get(scanner.next());
        scanner.close();
        return new CurrencyRate(date, type, rate);
    }
}
