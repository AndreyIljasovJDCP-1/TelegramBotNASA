import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class TelegramBot extends TelegramLongPollingBot {


    public static final String BOT_TOKEN = "5893732280:AAHQx2A67vtf6ZCgctzJWqhscWjI4tB60c0";

    public static final String BOT_USERNAME = "NasaJavaTelegram_bot";
    //    public static final String CHAT_ID = "5214817492";
    public static long chatID;
    public static final String URI =
            "https://api.nasa.gov/planetary/apod?api_key=nz8GILqM7wjvZRatpl9BE4T9IW74nT0OH0Deaq41";

    public TelegramBot() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            botsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage()) {
                chatID = update.getMessage().getChatId();
                switch (update.getMessage().getText()) {
                    case "/help":
                        sendMessage("Привет, я чат-бот NASA! " +
                                "\nВысылаю фото с сайта NASA по запросу. " +
                                "\nФото обновляется раз в сутки. " +
                                "\nКоманды: " +
                                "\n/help - помощь." +
                                "\n/give - получить фото." +
                                "\n/giveHD - получить HD фото." +
                                "\n/exp - получить описание к фото.");
                        break;
                    case "/give":
                        sendMessage(Utils.getUrl(URI));
                        break;
                    case "/giveHD":
                        sendMessage(Utils.getHDUrl(URI));
                        break;
                    case "/exp":
                        sendMessage(Utils.getExplanation(URI));
                        break;
                    default:
                        sendMessage("Неизвестная команда." +
                                " Вызов справки: /help");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMessage(String messageText) {
        SendMessage message = new SendMessage();
        message.setChatId(chatID);
        message.setText(messageText);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
