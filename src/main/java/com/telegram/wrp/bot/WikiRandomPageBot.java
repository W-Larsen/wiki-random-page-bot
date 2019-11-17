package com.telegram.wrp.bot;

import com.telegram.wrp.domain.WikiResponse;
import com.telegram.wrp.service.WikiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

/**
 * Wiki random page bot implementation.
 *
 * @author Valentyn Korniienko
 */
@Component
public class WikiRandomPageBot extends TelegramLongPollingBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikiRandomPageBot.class);

    @Value("${wiki.random.page.bot.token}")
    private String botToken;
    @Value("${wiki.random.page.bot.username}")
    private String botUsername;
    @Value("${wiki.random.page.channel.username}")
    private String channelUsername;
    @Value("${wiki.random.page.locale}")
    private String locale;

    @Autowired
    private WikiService wikiService;

    @PostConstruct
    public void init() {
        LOGGER.info("Username: {}, token: {}", botUsername, botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            SendMessage response = new SendMessage();
            Long chatId = message.getChatId();
            response.setChatId(chatId);

            String text = message.getText();
            response.setText(text);

            try {
                execute(response);
                LOGGER.info("Sent message \"{}\" to {}", text, chatId);
            } catch (TelegramApiException e) {
                LOGGER.error("Failed to send message \"{}\" to {} due to error: {}", text, chatId, e.getMessage());
            }
        }
    }

    @Scheduled(cron = "${wiki.random.page.cron}")
    private void sendRandomWikiPage() throws TelegramApiException {
        WikiResponse response = wikiService.getWikiResponse(locale);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(channelUsername);
        sendMessage.setText(response.getTitle() + System.lineSeparator() + response.getUrl());
        execute(sendMessage);
        LOGGER.info("Random page with title '{}' was sent to the channel.", response.getTitle());
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

}
