package com.lp2.sigmonio.bot.controller;

import com.lp2.sigmonio.bot.service.SigmonioService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;

import java.util.ArrayList;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;


@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class SigmonioBot extends AbilityBot {
    String commands;

    @Autowired
    SigmonioService sigmonioService;

    private static final String BOT_TOKEN = "981659676:AAGKqVtYVNp-4TjI1GQg92JuH8kQnegKH0o";
    private static final String BOT_USERNAME = "sigmonio_bot ";

    @Autowired
    public SigmonioBot() {
        super(BOT_TOKEN, BOT_USERNAME);
        setCommands();
    }

    @Override
    public int creatorId() {
        return 432232268;
    }

    private void setCommands() {
        this.commands = "/register {localization, category or item} {data} - Register one localization, category or item\n" +
                "/list {localizations, categories or localization} - List localizations, categories or items from one localization\n" +
                "/search {name or description} - Search item by name or description\n" +
                "/report - Generate a report";
    }

    public Ability start() {
        return Ability.builder()
                .name("start") // Command
                .info("Start") // info of command
                .privacy(PUBLIC)  // Who can use this command
                .locality(ALL) // Where this command can be use
                .input(0) // Number of required arguments
                .action(messageContext -> silent.send("Hello, here is the commands of me!\n" + commands, messageContext.chatId())) // Action of the command
                .build();
    }

    public Ability register() {
        return Ability.builder()
                .name("register")
                .info("Register one location, category or item")
                .privacy(PUBLIC)
                .locality(ALL)
                .input(0)
                .action(messageContext -> {
                    switch (messageContext.firstArg()) {
                        case "localization":
                            ArrayList<String> argument = sigmonioService.sanitizeArguments(messageContext.arguments());
                            String name = argument.get(0);
                            String description = argument.get(1);

                            if(!sigmonioService.verifyLocalization(name) && sigmonioService.verifyArguments(argument,2)) {

                                sigmonioService.saveLocalization(name, description);
                                silent.send("Localization has created sucessfuly!", messageContext.chatId());
                            }
                            else {
                                silent.send("that " + name + " already exists", messageContext.chatId());
                            }
                            break;

                        case "category":
                            break;
                        default:
                            silent.send("Invalid option, use localization, category or item", messageContext.chatId());
                            break;
                    }
                })
                .build();
    }
}
