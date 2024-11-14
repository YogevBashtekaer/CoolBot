package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class  Main {
    public static void main(String[] args) {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new CoolBot());
        }catch (TelegramApiException e){
            System.err.println("Failed to register the bot due to Telegram API issues: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}