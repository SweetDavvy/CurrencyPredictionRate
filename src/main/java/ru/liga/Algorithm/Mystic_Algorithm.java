package ru.liga.Algorithm;

import ru.liga.Сurrency.Currency_Rate;
import ru.liga.Input.Period;
import ru.liga.Type.Currency_Types;
import ru.liga.Repository.Currency_Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mystic_Algorithm implements Currency_Forecaster {

    private final List<LocalDate> fullMoonList = new ArrayList<>();

    public Mystic_Algorithm() {
        fullMoonList.add(LocalDate.of(2022, 2, 16));
        fullMoonList.add(LocalDate.of(2022, 1, 18));
        fullMoonList.add(LocalDate.of(2021, 12, 19));
    }

    @Override
    public List<Currency_Rate> getForecast(Currency_Repository repository, Currency_Types type, Period period) {
        List<Currency_Rate> fullMoonRates = new ArrayList<>();
        fullMoonList.forEach(d -> fullMoonRates.add(repository.getRateForDate(type, d)));

        Double averageRate = fullMoonRates.stream()
                .mapToDouble(Currency_Rate::getRate)
                .average().orElse(0D);

        List<Currency_Rate> result = new ArrayList<>();
        if (period.isRange()) {
            Currency_Rate lastRate = new Currency_Rate(LocalDate.now().plusDays(1), type, averageRate);
            result.add(lastRate);
            while (!lastRate.getDate().isEqual(period.getTargetDate())) {
                Double newRate = getNewRate(lastRate.getRate());
                lastRate = new Currency_Rate(lastRate.getDate().plusDays(1), type, newRate);
                result.add(lastRate);
            }
        } else {
            result.add(new Currency_Rate(period.getTargetDate(), type, averageRate));
        }
        return result;
    }

    private Double getNewRate(Double initialRate) {
        Random random = new Random();
        int min = -10;
        int max = 10;
        int diff = max - min;
        int randValue = min + random.nextInt(diff + 1);

        double percentage = randValue / 100D;
        return (1 + percentage) * initialRate;
    }

}
