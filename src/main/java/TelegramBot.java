import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class TelegramBot extends TelegramLongPollingBot {
    public static final String BOT_TOKEN = getConfig("token.txt");
    public static final String BOT_USERNAME = getConfig("username.txt");
    public static long chatID;
    public static final String URI = getConfig("nasaURI.txt");
    public static final String HELP = """
            Привет, я чат-бот API NASA.
            Высылаю фото по запросу.
            Фото обновляется раз в сутки.
            Команды:
            /give - получить фото.
            /giveHD - получить HD фото.
            /exp - получить описание к фото.
            /help - помощь.""";
    public static Map<String, Sender> commandMap = new HashMap<>();

    {
        commandMap.put("/give", () -> sendMessage(Utils.getUrl(URI)));
        commandMap.put("/giveHD", () -> sendMessage(Utils.getHDUrl(URI)));
        commandMap.put("/exp", () -> sendMessage(Utils.getExplanation(URI)));
        commandMap.put("/help", () -> sendMessage(HELP));
    }

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
                commandMap.getOrDefault(
                                update.getMessage().getText(),
                                () -> sendMessage("Неизвестная команда.\n/help - помощь."))
                        .send();
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

    private static String getConfig(String filename) {
        String result;
        try {
            result = Files.readString(Path.of(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
