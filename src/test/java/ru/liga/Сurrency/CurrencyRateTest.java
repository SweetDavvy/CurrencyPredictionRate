package ru.liga.Сurrency;
import org.junit.jupiter.api.Test;
import ru.liga.Type.Currency_Types;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

class CurrencyRateTest {

    @Test
    void testToString() {
        Currency_Rate rate = new Currency_Rate(LocalDate.parse("21.02.2022", DateTimeFormatter.ofPattern("dd.MM.yyyy")), Currency_Types.EUR, 87.5);
        assertThat(rate.toString()).hasToString("Пн 21.02.2022 - 87,50");
    }
}