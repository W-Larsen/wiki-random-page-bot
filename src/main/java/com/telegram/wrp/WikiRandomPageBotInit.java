package com.telegram.wrp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.ApiContextInitializer;

/**
 * Wiki random page bot initializer.
 *
 * @author Valentyn Korniienko
 */
@SpringBootApplication
@EnableScheduling
public class WikiRandomPageBotInit {

    /**
     * Main class.
     *
     * @param args the args
     */
    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(WikiRandomPageBotInit.class, args);
    }

}
