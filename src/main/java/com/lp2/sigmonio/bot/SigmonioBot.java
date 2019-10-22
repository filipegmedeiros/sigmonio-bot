package com.lp2.sigmonio.bot;

import com.lp2.sigmonio.model.Localization;
import com.lp2.sigmonio.service.LocalizationService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;



@Component
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SigmonioBot extends TelegramLongPollingBot {

    @Autowired
    LocalizationService localizationService;



    @Getter
    @Value("${bot.username}")
    String botUsername;

    @Getter
    @Value("${bot.token}")
    String botToken;


    @Override
    public void onUpdateReceived(Update update) {

        Localization localization = null;
        try {
            localization = localizationService.find(update).get();
            if (localization.getDescription() != null)
                sendTextMessage(update.getMessage().getChatId(), localization.getName());
            else
                sendTextMessage(update.getMessage().getChatId(), "there is no localization ");

        } catch (Exception e) {
            sendTextMessage(update.getMessage().getChatId(), "error");
        }
    }

    public synchronized void sendTextMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);


        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e);
        }
    }


}