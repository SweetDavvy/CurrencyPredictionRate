package ru.liga.Executor;

import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import ru.liga.Сurrency.Currency_Rate;
import ru.liga.Input.User_Command;
import ru.liga.Output.Command_Result;
import ru.liga.Repository.Currency_Repository;
import ru.liga.Type.Currency_Types;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Graph_Command_Executor implements Command_Executor {

    private final Currency_Repository repository;
    private final User_Command command;
    private final String fileName;

    public Graph_Command_Executor(Currency_Repository repository, User_Command command) {
        this.repository = repository;
        this.command = command;
        String folderName = "png/";
        File folder = new File(folderName);
        if (!folder.exists()) {
            folder.mkdir();
        }
        this.fileName = folderName + LocalDate.now() + " " + command.getInputString() + ".png";
    }

    @Override
    public Command_Result execute() {
        Map<Currency_Types, List<Currency_Rate>> data = new EnumMap<>(Currency_Types.class);
        for (Currency_Types type: command.getCurrencyTypes()) {
            data.put(type, getForecast(type));
        }
        try {
            generateGraph(data);
        } catch (PythonExecutionException | IOException e) {
            e.printStackTrace();
        }
        return new Command_Result(new File(fileName));
    }

    private List<Currency_Rate> getForecast(Currency_Types type) {
        return command.getAlgorithm().getForecast(repository, type, command.getPeriod());
    }


    private void generateGraph(Map<Currency_Types, List<Currency_Rate>> data) throws PythonExecutionException, IOException {
        if (new File(fileName).exists()) {
            return;
        }
        Plot plt = Plot.create();
        plt.title("Exchange rate");
        for (Map.Entry<Currency_Types, List<Currency_Rate>> entry : data.entrySet()) {
            List<Double> rates = entry.getValue().stream()
                    .map(Currency_Rate::getRate)
                    .collect(Collectors.toList());
            plt.plot().add(rates).label(entry.getKey().name());
        }
        plt.legend();

        plt.xlabel("Days");
        plt.ylabel("Rate");
        plt.savefig(fileName).dpi(200);
        plt.executeSilently();
    }
}
