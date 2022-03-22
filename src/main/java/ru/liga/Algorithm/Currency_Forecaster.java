package ru.liga.Algorithm;

import ru.liga.Сurrency.Currency_Rate;
import ru.liga.Input.Period;
import ru.liga.Type.Currency_Types;
import ru.liga.Repository.Currency_Repository;

import java.util.List;

public interface Currency_Forecaster {

    List<Currency_Rate> getForecast(Currency_Repository repository, Currency_Types type, Period period);
}
