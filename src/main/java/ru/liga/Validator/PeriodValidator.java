package ru.liga.Validator;

import ru.liga.Exception.InvalidRangeException;
import ru.liga.Input.Period;
import ru.liga.Type.CommandOptions;
import ru.liga.Type.ErrorMessages;
import ru.liga.Type.RangeTypes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

public class PeriodValidator implements Validator<Period> {

    private static final String DATE_FORMAT_PATTER = "dd.MM.yyyy";

    @Override
    public Period validateAndGet(Map<String, String> args) {
        if (!args.containsKey(CommandOptions.DATE.getKey()) && !args.containsKey(CommandOptions.PERIOD.getKey())) {
            throw new InvalidRangeException(ErrorMessages.INVALID_NO_DATE_OR_PERIOD.getText());
        } else if (args.containsKey(CommandOptions.DATE.getKey()) && args.containsKey(CommandOptions.PERIOD.getKey())) {
            throw new InvalidRangeException(ErrorMessages.INVALID_DATE_AND_PERIOD.getText());
        } else if (args.containsKey(CommandOptions.DATE.getKey())) {
            String dateValue = args.get(CommandOptions.DATE.getKey());
            if (args.containsKey(CommandOptions.OUTPUT.getKey())) {
                throw new InvalidRangeException(ErrorMessages.INVALID_NO_PERIOD_WHEN_OUTPUT_GRAPH.getText());
            }
            return new Period(getDateForDate(dateValue), false);
        } else {
            String periodValue = args.get(CommandOptions.PERIOD.getKey());
            return new Period(getDateForPeriod(periodValue), true);
        }
    }

    private LocalDate getDateForDate(String dateValue) {
        try {
            LocalDate targetDate = LocalDate.parse(dateValue, DateTimeFormatter.ofPattern(DATE_FORMAT_PATTER));
            if (targetDate.isAfter(LocalDate.now())) {
                return targetDate;
            } else {
                throw new InvalidRangeException(ErrorMessages.INVALID_DATE_IN_PAST.getText());
            }
        } catch (DateTimeParseException e) {
            if (dateValue.equals("tomorrow")) {
                return LocalDate.now().plusDays(1);
            }
            throw new InvalidRangeException(ErrorMessages.INVALID_DATE.getText());
        }
    }

    private LocalDate getDateForPeriod(String periodValue) {
        try {
            RangeTypes rangeType = RangeTypes.valueOf(periodValue.toUpperCase());
            return LocalDate.now().plusDays(rangeType.getDays());
        } catch (IllegalArgumentException e) {
            throw new InvalidRangeException(ErrorMessages.INVALID_PERIOD.getText());
        }
    }
}
