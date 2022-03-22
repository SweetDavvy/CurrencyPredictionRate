package ru.liga.Type;

import lombok.extern.slf4j.Slf4j;
import ru.liga.Algorithm.*;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
public enum Algorithm_Types {
    ACTUAL(new Actual_Algorithm()),
    LINEAR(new Linear_Algorithm()),
    MYSTIC(new Mystic_Algorithm()),
    MEAN(new Arithmetic_Mean());

    private final Currency_Forecaster algorithm;

    Algorithm_Types(Currency_Forecaster algorithm) {
        this.algorithm = algorithm;
    }

    public static String getString() {
        return Arrays.stream(values())
                .map(Enum::name)
                .collect(Collectors.joining(" | ", "<", ">"));
    }

    public Currency_Forecaster getAlgorithm() {
        return algorithm;
    }

}
