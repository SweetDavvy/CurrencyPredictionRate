package ru.liga.Repository;

import ru.liga.Сurrency.Currency_Rate;
import ru.liga.Type.Currency_Types;
import ru.liga.CSV_Reader.Currency_Parser;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class In_Memory_Currency_Repository implements Currency_Repository {

    private final Map<Currency_Types, List<Currency_Rate>> data = new EnumMap<>(Currency_Types.class);
    private final Currency_Parser parser;

    public In_Memory_Currency_Repository(Currency_Parser parser) {
        this.parser = parser;
    }

    @Override
    public void addAll(Currency_Types type) {
        if (data.containsKey(type)) {
            data.get(type).addAll(parser.getCurrencyRates(type));
        } else {
            data.put(type, parser.getCurrencyRates(type));
        }
    }

    @Override
    public List<Currency_Rate> getRates(Currency_Types type, int amount) {
        if (!data.containsKey(type)) {
            addAll(type);
        }
        data.get(type).sort((currencyRate1, currencyRate2) -> currencyRate2.getDate().compareTo(currencyRate1.getDate()));
        return data.get(type).stream()
                .limit(amount)
                .collect(Collectors.toList());
    }

    @Override
    public List<Currency_Rate> getAllRates(Currency_Types type) {
        if (!data.containsKey(type)) {
            addAll(type);
        }
        data.get(type).sort((currencyRate1, currencyRate2) -> currencyRate2.getDate().compareTo(currencyRate1.getDate()));
        return data.get(type);
    }

    @Override
    public Currency_Rate getRateForDate(Currency_Types type, LocalDate date) {
        if (!data.containsKey(type)) {
            addAll(type);
        }
        return data.get(type).stream()
                .filter(currencyRate -> isDateEqualOrBefore(currencyRate.getDate(), date))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    private boolean isDateEqualOrBefore(LocalDate date, LocalDate targetDate) {
        return date.isEqual(targetDate) || date.isBefore(targetDate);
    }
}
