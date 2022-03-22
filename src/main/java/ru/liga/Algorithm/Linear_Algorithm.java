package ru.liga.Algorithm;

import ru.liga.Сurrency.Currency_Rate;
import ru.liga.Input.Period;
import ru.liga.Type.Currency_Types;
import ru.liga.Repository.Currency_Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Linear_Algorithm implements Currency_Forecaster {

    private static final int DAYS_AMOUNT_TO_INTERPOLATE = 30;

    @Override
    public List<Currency_Rate> getForecast(Currency_Repository repository, Currency_Types type, Period period) {
        List<Currency_Rate> data = repository.getRates(type, DAYS_AMOUNT_TO_INTERPOLATE);
        LocalDate nextDay = data.get(0).getDate().plusDays(1);
        data.sort(Comparator.comparing(Currency_Rate::getDate));
        double[] dates = new double[data.size()];
        double[] rates = new double[data.size()];
        for (int i = 0; i < data.size(); i++) {
            dates[i] = i + 1d;
            rates[i] = data.get(i).getRate();
        }
        Linear_Regression linearRegression = new Linear_Regression(dates, rates);
        List<Currency_Rate> result = new ArrayList<>();
        int i = 1;
        do {
            if (nextDay.isAfter(LocalDate.now())) {
                result.add(new Currency_Rate(nextDay, type, linearRegression.predict(dates[data.size() - 1] + i)));
            }
            nextDay = nextDay.plusDays(1);
            i++;
        } while (!nextDay.isAfter(period.getTargetDate()));

        if (period.isRange()) {
            return result;
        } else {
            Collections.reverse(result);
            return result.stream().limit(1).collect(Collectors.toList());
        }
    }
}
