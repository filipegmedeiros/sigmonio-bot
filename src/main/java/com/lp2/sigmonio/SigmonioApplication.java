package com.lp2.sigmonio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.telegram.telegrambots.ApiContextInitializer;


import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaRepositories
public class SigmonioApplication implements Command {

	@Override
	public void execute() {
		ApiContextInitializer.init();
	}

	SigmonioApplication(){
		this.execute();
	}


	@PostConstruct
	void started(){
		TimeZone.setDefault(TimeZone.getTimeZone("TimeZone"));
	}

	public static void main(String[] args) {
		SpringApplication.run(SigmonioApplication.class, args);
	}


}
