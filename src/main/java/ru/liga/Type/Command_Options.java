package ru.liga.Type;

import lombok.Getter;

@Getter
public enum Command_Options {
    COMMAND("command"),
    CURRENCY("currency"),
    DATE("-date"),
    PERIOD("-period"),
    ALGORITHM("-alg"),
    OUTPUT("-output");

    private String key;

    Command_Options(String key) {
        this.key = key;
    }

}
