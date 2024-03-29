package ru.liga.Executor;

import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import ru.liga.Сurrency.CurrencyRate;
import ru.liga.Input.UserCommand;
import ru.liga.Output.CommandResult;
import ru.liga.Repository.CurrencyRepository;
import ru.liga.Type.CurrencyTypes;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GraphCommandExecutor implements CommandExecutor {

    private final CurrencyRepository repository;
    private final UserCommand command;
    private final String fileName;

    public GraphCommandExecutor(CurrencyRepository repository, UserCommand command) {
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
    public CommandResult execute() {
        Map<CurrencyTypes, List<CurrencyRate>> data = new EnumMap<>(CurrencyTypes.class);
        for (CurrencyTypes type: command.getCurrencyTypes()) {
            data.put(type, getForecast(type));
        }
        try {
            generateGraph(data);
        } catch (PythonExecutionException | IOException e) {
            e.printStackTrace();
        }
        return new CommandResult(new File(fileName));
    }

    private List<CurrencyRate> getForecast(CurrencyTypes type) {
        return command.getAlgorithm().getForecast(repository, type, command.getPeriod());
    }


    private void generateGraph(Map<CurrencyTypes, List<CurrencyRate>> data) throws PythonExecutionException, IOException {
        if (new File(fileName).exists()) {
            return;
        }
        Plot plt = Plot.create();
        plt.title("Exchange rate");
        for (Map.Entry<CurrencyTypes, List<CurrencyRate>> entry : data.entrySet()) {
            List<Double> rates = entry.getValue().stream()
                    .map(CurrencyRate::getRate)
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
