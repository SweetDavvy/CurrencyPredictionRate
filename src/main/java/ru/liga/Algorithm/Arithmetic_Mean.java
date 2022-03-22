package ru.liga.Algorithm;


import ru.liga.Input.Period;
import ru.liga.Type.Currency_Types;
import ru.liga.Repository.Currency_Repository;
import ru.liga.Сurrency.Currency_Rate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Среднее арифметическое последних 7 дней курса
 */

public class Arithmetic_Mean implements Currency_Forecaster {

    private static final int AVERAGE_FOR_DAYS = 7;

    @Override
    public List<Currency_Rate> getForecast(Currency_Repository repository, Currency_Types type, Period period) {
        List<Currency_Rate> result = new LinkedList<>();
        List<Currency_Rate> lastWeekRates = repository.getRates(type, AVERAGE_FOR_DAYS);
        while (!lastWeekRates.get(0).getDate().isEqual(period.getTargetDate())) {
            LocalDate nextDay = lastWeekRates.get(0).getDate().plusDays(1);
            Double averageRate = lastWeekRates.stream()
                    .limit(AVERAGE_FOR_DAYS)
                    .mapToDouble(Currency_Rate::getRate)
                    .average()
                    .orElse(Double.NaN);
            lastWeekRates.add(0, new Currency_Rate(nextDay, type, averageRate));
        }
        long daysToShow = period.isRange() ? ChronoUnit.DAYS.between(LocalDate.now(), period.getTargetDate()) : 1L;
        lastWeekRates.stream()
                .limit(daysToShow)
                .forEach(result::add);
        Collections.reverse(result);
        return result;
    }

}

