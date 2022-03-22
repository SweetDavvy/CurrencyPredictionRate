package ru.liga;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.liga.CSV_Reader.CSV_Parser;
import ru.liga.Input.*;
import ru.liga.Repository.In_Memory_Currency_Repository;

@Slf4j
public class App {

    private static final String BOT_NAME = "CurrencyRatePredictor_bot";
    private static final String BOT_TOKEN = "5258665415:AAHvyIIxwGKCMN_1_8-oxwmEvGwX2igJ9uU";

    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            Bot bot = new Bot(BOT_NAME, BOT_TOKEN, new In_Memory_Currency_Repository(new CSV_Parser()));
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            log.error("Error in psvm: ", e);
        }
    }
}
