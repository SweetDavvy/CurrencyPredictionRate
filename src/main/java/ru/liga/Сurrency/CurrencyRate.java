package ru.liga.Сurrency;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import ru.liga.Type.CurrencyTypes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

@Getter
@EqualsAndHashCode
public class CurrencyRate {

    private final LocalDate date;
    private final CurrencyTypes type;
    private final Double rate;

    public CurrencyRate(LocalDate date, CurrencyTypes type, Double rate) {
        this.date = date;
        this.type = type;
        this.rate = rate;
    }

    /**
     * Возведение в степень.
     *
     * @return возведенное в степень значение
     */
    private Double getRoundedRate() {
        return Math.round(rate * 100) / 100d;
    }

    @Override
    public String toString() {
        String dayName = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("ru"));
        return String.format("%s %s - %.2f", dayName, date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), getRoundedRate());
    }
}
