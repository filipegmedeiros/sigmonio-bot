package com.lp2.sigmonio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SigmonioApplication {

	public static void main(String[] args) {
		SpringApplication.run(SigmonioApplication.class, args);
	}

}
