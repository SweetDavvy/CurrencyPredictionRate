package ru.liga.Validator;

import java.util.Map;

public interface Validator<T> {

    T validateAndGet(Map<String, String> args);
}
