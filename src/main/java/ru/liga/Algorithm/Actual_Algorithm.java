package ru.liga.Algorithm;


import ru.liga.Сurrency.Currency_Rate;
import ru.liga.Input.Period;
import ru.liga.Type.Currency_Types;
import ru.liga.Type.Error_Messages;
import ru.liga.Exception.Invalid_Range_Exception;
import ru.liga.Repository.Currency_Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Actual_Algorithm implements Currency_Forecaster {

    private static final int TWO_YEARS = 2;
    private static final int THREE_YEARS = 3;

    @Override
    public List<Currency_Rate> getForecast(Currency_Repository repository, Currency_Types type, Period period) {
        LocalDate lastDate = repository.getRates(type, 1).get(0).getDate();
        if (period.getTargetDate().minusYears(TWO_YEARS).isAfter(lastDate)) {
            throw new Invalid_Range_Exception(Error_Messages.INVALID_DATE_IS_BEYOND.getText());
        }
        List<Currency_Rate> result = new ArrayList<>();
        LocalDate nextDay = LocalDate.now().plusDays(1);
        do {
            LocalDate twoYearsBefore = nextDay.minusYears(TWO_YEARS);
            LocalDate threeYearsBefore = nextDay.minusYears(THREE_YEARS);
            Currency_Rate rateTwoYearsBefore = repository.getRateForDate(type, twoYearsBefore);
            Currency_Rate rateThreeYearsBefore = repository.getRateForDate(type, threeYearsBefore);
            result.add(new Currency_Rate(nextDay, type, rateTwoYearsBefore.getRate() + rateThreeYearsBefore.getRate()));
            nextDay = nextDay.plusDays(1);
        } while (!nextDay.isAfter(period.getTargetDate()));

        if (!period.isRange()) {
            Collections.reverse(result);
            return result.stream()
                    .limit(1)
                    .collect(Collectors.toList());
        }
        return result;
    }
}
