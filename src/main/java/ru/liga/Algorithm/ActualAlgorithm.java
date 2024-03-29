package ru.liga.Algorithm;


import ru.liga.Сurrency.CurrencyRate;
import ru.liga.Input.Period;
import ru.liga.Type.CurrencyTypes;
import ru.liga.Type.ErrorMessages;
import ru.liga.Exception.InvalidRangeException;
import ru.liga.Repository.CurrencyRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ActualAlgorithm implements CurrencyForecaster {

    private static final int TWO_YEARS = 2;
    private static final int THREE_YEARS = 3;

    @Override
    public List<CurrencyRate> getForecast(CurrencyRepository repository, CurrencyTypes type, Period period) {
        LocalDate lastDate = repository.getRates(type, 1).get(0).getDate();
        if (period.getTargetDate().minusYears(TWO_YEARS).isAfter(lastDate)) {
            throw new InvalidRangeException(ErrorMessages.INVALID_DATE_IS_BEYOND.getText());
        }
        List<CurrencyRate> result = new ArrayList<>();
        LocalDate nextDay = LocalDate.now().plusDays(1);
        do {
            LocalDate twoYearsBefore = nextDay.minusYears(TWO_YEARS);
            LocalDate threeYearsBefore = nextDay.minusYears(THREE_YEARS);
            CurrencyRate rateTwoYearsBefore = repository.getRateForDate(type, twoYearsBefore);
            CurrencyRate rateThreeYearsBefore = repository.getRateForDate(type, threeYearsBefore);
            result.add(new CurrencyRate(nextDay, type, rateTwoYearsBefore.getRate() + rateThreeYearsBefore.getRate()));
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
