package ru.liga.Algorithm;

import ru.liga.Сurrency.CurrencyRate;
import ru.liga.Input.Period;
import ru.liga.Type.CurrencyTypes;
import ru.liga.Repository.CurrencyRepository;

import java.util.List;

public interface CurrencyForecaster {

    List<CurrencyRate> getForecast(CurrencyRepository repository, CurrencyTypes type, Period period);
}
