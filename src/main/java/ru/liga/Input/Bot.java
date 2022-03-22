package ru.liga.Input;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.Exception.Base_Exception;
import ru.liga.Executor.Command_Executor;
import ru.liga.Executor.Executor_Controller;
import ru.liga.Output.Command_Result;
import ru.liga.Repository.Currency_Repository;

@Slf4j
public class Bot extends TelegramLongPollingCommandBot {

    private final String BOT_NAME;
    private final String BOT_TOKEN;
    private final Currency_Repository repository;

    public Bot(String botName, String botToken, Currency_Repository repository) {
        super();
        this.repository = repository;
        this.BOT_NAME = botName;
        this.BOT_TOKEN = botToken;
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        Message userInput = update.getMessage();
        log.info("Input: {}; from user: {}", userInput.getText(), userInput.getFrom().getUserName());
        try {
            User_Command userCommand = User_Command.createFromString(userInput.getText());
            Command_Executor executor = new Executor_Controller(repository).getExecutor(userCommand);
            Command_Result result = executor.execute();
            setAnswer(userInput.getChatId(), userInput.getFrom().getUserName(), result);
        } catch (Base_Exception e) {
            log.error(e.getMessage());
            setAnswer(userInput.getChatId(), userInput.getFrom().getUserName(), e.getMessage());
        }
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    private void setAnswer(Long chatId, String userName, String error) {
        SendMessage answer = new SendMessage();
        answer.setChatId(chatId.toString());
        answer.setText(error);
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            log.error("Error: {}; from: {}; result: {}", e.getMessage(), userName, error);
        }
    }

    private void setAnswer(Long chatId, String userName, Command_Result result) {
        try {
            if (result.isText()) {
                SendMessage answer = new SendMessage();
                answer.setChatId(chatId.toString());
                answer.setText(result.getTextResult());
                execute(answer);
            } else {
                SendPhoto answer = new SendPhoto();
                answer.setChatId(chatId.toString());
                answer.setPhoto(new InputFile(result.getGraphResult()));
                execute(answer);
            }
        } catch (TelegramApiException e) {
            log.error("Error: {}; from: {}; result: {}", e.getMessage(), userName, result);
        }
    }

}
