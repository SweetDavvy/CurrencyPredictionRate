package ru.liga.Input;

import org.junit.jupiter.api.Test;
import ru.liga.Exception.*;
import ru.liga.Type.Algorithm_Types;
import ru.liga.Type.Currency_Types;
import ru.liga.Type.Error_Messages;

import java.time.LocalDate;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserCommandBuilderTest {


    @Test
    void whenInputValid() {
        String input = "rate USD -date tomorrow -alg linear";
        User_Command actual = User_Command.createFromString(input);
        Period expectedPeriod = new Period(LocalDate.now().plusDays(1), false);

        assertThat(actual.getPeriod()).isEqualTo(expectedPeriod);
        assertThat(actual.getAlgorithm()).isEqualTo(Algorithm_Types.LINEAR.getAlgorithm());
        assertThat(actual.isGraph()).isFalse();
        assertThat(actual.getCurrencyTypes()).isEqualTo(Collections.singletonList(Currency_Types.USD));
    }


    @Test
    void whenInputIsNotRateThenInvalid() {
        assertThatThrownBy(() -> User_Command.createFromString("asd USD -date tomorrow -alg linear"))
                .isInstanceOf(Invalid_Argument_Exception.class)
                .hasMessageContaining(Error_Messages.INVALID_COMMAND.getText());
    }

    @Test
    void whenInputInvalidFormatThenError() {
        assertThatThrownBy(() -> User_Command.createFromString("rate USD -date tomorrow dssad -alg linear"))
                .isInstanceOf(Invalid_Argument_Exception.class)
                .hasMessageContaining(Error_Messages.INVALID_INPUT_FORMAT.getText());
    }

    @Test
    void whenInputInvalidCurrencyThenError() {
        assertThatThrownBy(() -> User_Command.createFromString("rate RUB -period month -alg linear -output graph"))
                .isInstanceOf(Invalid_Currency_Exception.class)
                .hasMessageContaining(Error_Messages.INVALID_CURRENCY.getText());
    }

    @Test
    void whenInputInvalidDateThenError() {
        assertThatThrownBy(() -> User_Command.createFromString("rate USD -date 25032022 -alg linear"))
                .isInstanceOf(Invalid_Range_Exception.class)
                .hasMessageContaining(Error_Messages.INVALID_DATE.getText());
    }

    @Test
    void whenInputInvalidRangeThenError() {
        assertThatThrownBy(() -> User_Command.createFromString("rate USD -period year -alg linear"))
                .isInstanceOf(Invalid_Range_Exception.class)
                .hasMessageContaining(Error_Messages.INVALID_PERIOD.getText());
    }

    @Test
    void whenInputBothDateAndPeriodThenError() {
        assertThatThrownBy(() -> User_Command.createFromString("rate USD -date tomorrow -period month -alg linear"))
                .isInstanceOf(Invalid_Range_Exception.class)
                .hasMessageContaining(Error_Messages.INVALID_DATE_AND_PERIOD.getText());
    }

    @Test
    void whenInputNoDateOrPeriodThenError() {
        assertThatThrownBy(() -> User_Command.createFromString("rate USD,EUR -alg linear -output graph"))
                .isInstanceOf(Invalid_Range_Exception.class)
                .hasMessageContaining(Error_Messages.INVALID_NO_DATE_OR_PERIOD.getText());
    }

    @Test
    void whenInputNoAlgThenError() {
        assertThatThrownBy(() -> User_Command.createFromString("rate USD,EUR -period week -output graph"))
                .isInstanceOf(Invalid_Algorithm_Exception.class)
                .hasMessageContaining(Error_Messages.INVALID_NO_ALG.getText());
    }

    @Test
    void whenInputWrongAlgThenError() {
        assertThatThrownBy(() -> User_Command.createFromString("rate USD -date tomorrow -alg wrong"))
                .isInstanceOf(Invalid_Algorithm_Exception.class)
                .hasMessageContaining(Error_Messages.INVALID_ALGORITHM.getText());
    }

    @Test
    void whenInputMultipleCurrenciesAndNoOutputThenError() {
        assertThatThrownBy(() -> User_Command.createFromString("rate USD,EUR -date tomorrow -alg linear"))
                .isInstanceOf(Invalid_Currency_Exception.class)
                .hasMessageContaining(Error_Messages.INVALID_NO_OUTPUT.getText());
    }

    @Test
    void whenInputMultipleCurrenciesAndPeriodAndNoOutputThenError() {
        assertThatThrownBy(() -> User_Command.createFromString("rate USD,EUR -period month -alg linear"))
                .isInstanceOf(Invalid_Currency_Exception.class)
                .hasMessageContaining(Error_Messages.INVALID_NO_OUTPUT.getText());
    }

    @Test
    void whenInputMultipleCurrenciesAndPeriodAndWrongOutputThenError() {
        assertThatThrownBy(() -> User_Command.createFromString("rate USD,EUR -period month -alg linear -output wrong"))
                .isInstanceOf(Invalid_Output_Exception.class)
                .hasMessageContaining(Error_Messages.INVALID_OUTPUT.getText());
    }

    @Test
    void whenInputMoreThan5CurrenciesThenError() {
        assertThatThrownBy(() -> User_Command.createFromString("rate USD,EUR,TRY,AMD,BGN,RUB -period month -alg linear -output graph"))
                .isInstanceOf(Invalid_Currency_Exception.class)
                .hasMessageContaining(Error_Messages.INVALID_CURRENCY_AMOUNT.getText());
    }

    @Test
    void whenInputNoPeriodWhenOutputGraphThenError() {
        assertThatThrownBy(() -> User_Command.createFromString("rate EUR -date tomorrow -alg linear -output graph"))
                .isInstanceOf(Invalid_Range_Exception.class)
                .hasMessageContaining(Error_Messages.INVALID_NO_PERIOD_WHEN_OUTPUT_GRAPH.getText());
    }

    @Test
    void whenInputSameCurrencyMoreThan1TimeThenError() {
        assertThatThrownBy(() -> User_Command.createFromString("rate USD,EUR,USD -period month -alg linear -output graph"))
                .isInstanceOf(Invalid_Currency_Exception.class)
                .hasMessageContaining(Error_Messages.INVALID_SAME_CURRENCY.getText());
    }
}