package ru.liga.Output;

import lombok.Getter;
import ru.liga.Сurrency.Currency_Rate;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Command_Result {

    private String textResult;
    private List<Currency_Rate> listResult;
    private File graphResult;
    private boolean isText;
    private boolean isFile;

    public Command_Result(List<Currency_Rate> rates) {
        textResult = rates.stream()
                .map(Currency_Rate::toString)
                .collect(Collectors.joining("\n"));
        listResult = rates;
        isText = true;
    }

    public Command_Result(File graph) {
        graphResult = graph;
        isFile = true;
    }

    @Override
    public String toString() {
        return "CommandResult{" +
                "textResult='" + textResult + '\'' +
                ", graphResult=" + graphResult +
                ", isText=" + isText +
                ", isFile=" + isFile +
                '}';
    }
}
