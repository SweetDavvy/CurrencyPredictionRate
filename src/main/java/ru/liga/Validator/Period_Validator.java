package ru.liga.Validator;

import ru.liga.Exception.Invalid_Range_Exception;
import ru.liga.Input.Period;
import ru.liga.Type.Command_Options;
import ru.liga.Type.Error_Messages;
import ru.liga.Type.Range_Types;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

public class Period_Validator implements Validator<Period> {

    private static final String DATE_FORMAT_PATTER = "dd.MM.yyyy";

    @Override
    public Period validateAndGet(Map<String, String> args) {
        if (!args.containsKey(Command_Options.DATE.getKey()) && !args.containsKey(Command_Options.PERIOD.getKey())) {
            throw new Invalid_Range_Exception(Error_Messages.INVALID_NO_DATE_OR_PERIOD.getText());
        } else if (args.containsKey(Command_Options.DATE.getKey()) && args.containsKey(Command_Options.PERIOD.getKey())) {
            throw new Invalid_Range_Exception(Error_Messages.INVALID_DATE_AND_PERIOD.getText());
        } else if (args.containsKey(Command_Options.DATE.getKey())) {
            String dateValue = args.get(Command_Options.DATE.getKey());
            if (args.containsKey(Command_Options.OUTPUT.getKey())) {
                throw new Invalid_Range_Exception(Error_Messages.INVALID_NO_PERIOD_WHEN_OUTPUT_GRAPH.getText());
            }
            return new Period(getDateForDate(dateValue), false);
        } else {
            String periodValue = args.get(Command_Options.PERIOD.getKey());
            return new Period(getDateForPeriod(periodValue), true);
        }
    }

    private LocalDate getDateForDate(String dateValue) {
        try {
            LocalDate targetDate = LocalDate.parse(dateValue, DateTimeFormatter.ofPattern(DATE_FORMAT_PATTER));
            if (targetDate.isAfter(LocalDate.now())) {
                return targetDate;
            } else {
                throw new Invalid_Range_Exception(Error_Messages.INVALID_DATE_IN_PAST.getText());
            }
        } catch (DateTimeParseException e) {
            if (dateValue.equals("tomorrow")) {
                return LocalDate.now().plusDays(1);
            }
            throw new Invalid_Range_Exception(Error_Messages.INVALID_DATE.getText());
        }
    }

    private LocalDate getDateForPeriod(String periodValue) {
        try {
            Range_Types rangeType = Range_Types.valueOf(periodValue.toUpperCase());
            return LocalDate.now().plusDays(rangeType.getDays());
        } catch (IllegalArgumentException e) {
            throw new Invalid_Range_Exception(Error_Messages.INVALID_PERIOD.getText());
        }
    }
}
