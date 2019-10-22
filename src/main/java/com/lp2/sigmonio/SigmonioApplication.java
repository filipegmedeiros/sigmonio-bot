package com.lp2.sigmonio;

import com.lp2.sigmonio.bot.SigmonioBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@SpringBootApplication
@EnableJpaAuditing
public class SigmonioApplication {

	public static void main(String[] args) {
		SpringApplication.run(SigmonioApplication.class, args);
		ApiContextInitializer.init();

	/*	ApiContextInitializer.init();

		TelegramBotsApi api = new TelegramBotsApi();
		try {
			api.registerBot(new SigmonioBot());
		} catch (TelegramApiRequestException e) {
			e.printStackTrace();*/
		}
	}
