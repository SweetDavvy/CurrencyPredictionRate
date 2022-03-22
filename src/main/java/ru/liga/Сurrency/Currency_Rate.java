package ru.liga.Сurrency;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import ru.liga.Type.Currency_Types;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

@Getter
@EqualsAndHashCode
public class Currency_Rate {

    private final LocalDate date;
    private final Currency_Types type;
    private final Double rate;

    public Currency_Rate(LocalDate date, Currency_Types type, Double rate) {
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
