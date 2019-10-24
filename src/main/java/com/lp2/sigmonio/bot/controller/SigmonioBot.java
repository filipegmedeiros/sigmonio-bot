package com.lp2.sigmonio.bot.controller;

import com.lp2.sigmonio.bot.service.SigmonioService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

    private SigmonioService sigmonioService;

    @Autowired
    protected SigmonioBot(SigmonioService sigmonioService, Environment env) {
        super(env.getProperty("bot.token"), env.getProperty("bot.username"));

        this.sigmonioService = sigmonioService;
        setCommands();
    }

    @Override
    public int creatorId() {
        return 432232268;
    }

    private void setCommands() {
        this.commands = "`————————————————————————`\n" +
                "/register *is used to register one localization, category or item.*\n" +
                "/register *type* `name`*,* `description`\n" +
                "/register *item* `name`*,* `description`*,* `localizationName`*,* `categoryName`\n" +
                "`——————`\n" +
                "*Example of inputs acceptables:*\n" +
                "/register *localization*  `Room A309`*,* `It's a study room`\n" +
                "/register *category* `Computers`*,* `Category of all computers`\n" +
                "/register *item* `Computer`*,* `An Dell XPS 2015`*,* `Room A309`*,* `Computers`\n" +
                "`————————————————————————`\n" +
                "/list *is used to listen all localizations, categorys, items or all items by localization*\n" +
                "/list *type*\n" +
                "/list *type* `where`*,*\n" +
                "`——————`\n" +
                "*Example of inputs acceptables:*\n" +
                "/list *localizations*\n" +
                "/list *categorys*\n" +
                "/list *items*\n" +
                "/list *items* `Room A309`*,*\n" +
                "`————————————————————————`\n" +
                "/search *is used to listen a UNIQUE localization, category or item*\n" +
                "/search *type* `uniqueName`\n" +
                "/search *type* `uniqueId`\n" +
                "`——————`\n" +
                "\n" +
                "*Example of inputs acceptables:*\n" +
                "/search *localization* `Room A309`\n" +
                "/search *category* `Computers`\n" +
                "/search *item* `359`\n" +
                "`————————————————————————`\n" +
                "/relatory *will generate an report of database*\n" +
                "`————————————————————————`\n" +
                "\n" +
                "*OBS: arguments its separate by comma (,) attention to that!*";
    }

    public Ability start() {
        return Ability.builder()
                .name("start") // Command
                .info("Start") // info of command
                .privacy(PUBLIC)  // Who can use this command
                .locality(ALL) // Where this command can be use
                .input(0) // Number of required arguments
                .action(messageContext -> silent.sendMd("*Hello, here are the commands of mine!* \n" + commands, messageContext.chatId())) // Action of the command
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
                    ArrayList<String> argument = sigmonioService.sanitizeArguments(messageContext.arguments());
                    String name = argument.get(0);
                    String description = argument.get(1);

                    switch (messageContext.firstArg()) {
                        case "localization":
                            if(!sigmonioService.verifyLocalization(name)
                                    && sigmonioService.verifyArguments(argument,2)) {
                                silent.sendMd("*Localization* `has created sucessfuly with name: `" + sigmonioService.saveLocalization(name, description), messageContext.chatId());
                                silent.sendMd("*NOTE:* `This` *name* `is` *unique* , `so keep this in mind if you want to edit or view this localization specifically.`", messageContext.chatId());
                            }
                            else {
                                silent.send("that " + name + " already exists", messageContext.chatId());
                            }
                            break;

                        case "category":
                            if(!sigmonioService.verifyCategory(name)
                                    && sigmonioService.verifyArguments(argument,2)) {
                                silent.sendMd("*Category* `has created sucessfuly with name: `" + sigmonioService.saveCategory(name, description), messageContext.chatId());
                                silent.sendMd("*NOTE:* `This` *name* `is` *unique* , `so keep this in mind if you want to edit or view this category specifically.`", messageContext.chatId());
                            }
                            else {
                                silent.send("that " + name + " already exists", messageContext.chatId());
                            }
                            break;
                        case "item":
                            String localizationName = argument.get(2);
                            String categoryName = argument.get(3);
                            if(sigmonioService.verifyLocalization(localizationName)
                                    && sigmonioService.verifyCategory(categoryName)
                                    && sigmonioService.verifyArguments(argument,4)) {
                                silent.sendMd("*Item* `has created sucessfuly with ID: `" + sigmonioService.saveItem(name, description,localizationName,categoryName), messageContext.chatId());
                                silent.sendMd("*NOTE:* `This` *ID* `is` *unique* , `so keep this in mind if you want to edit or view this item specifically.`", messageContext.chatId());
                            }
                            else {
                                silent.send("that " + name + " already exists", messageContext.chatId());
                            }
                            break;

                        default:
                            silent.send("Invalid option, use localization, category or item", messageContext.chatId());
                            break;
                    }
                })
                .build();
    }

    public Ability list() {
        return Ability.builder()
                .name("list") // Command
                .info("list all locations, categorys, items or items of localization") // info of command
                .privacy(PUBLIC)  // Who can use this command
                .locality(ALL) // Where this command can be use
                .input(0) // Number of required arguments
                .action(messageContext -> {
                    switch (messageContext.firstArg()) {
                        case "localizations":
                            silent.send(sigmonioService.showLocalizations().toString(),messageContext.chatId());
                            break;

                        case "categorys":
                            silent.send(sigmonioService.showCategorys().toString(),messageContext.chatId());

                            break;
                        case "items":
                            silent.send(sigmonioService.showItems().toString(),messageContext.chatId());
                            break;


                        default:
                            silent.send("Invalid option, use localization, category or item", messageContext.chatId());
                            break;
                    }
                })
                .build();
    }

    public Ability search() {
        return Ability.builder()
                .name("search") // Command
                .info("search one localization, category, item") // info of command
                .privacy(PUBLIC)  // Who can use this command
                .locality(ALL) // Where this command can be use
                .input(0) // Number of required arguments
                .action(messageContext -> {
                    ArrayList<String> argument = sigmonioService.sanitizeArguments(messageContext.arguments());
                    String name = argument.get(0);
                    switch (messageContext.firstArg()) {
                        case "localization":
                            silent.sendMd(sigmonioService.showLocalization(name),messageContext.chatId());
                            break;

                        case "category":
                            silent.sendMd(sigmonioService.showCategory(name),messageContext.chatId());

                            break;
                        case "item":
                            silent.sendMd(sigmonioService.showItem(name),messageContext.chatId());
                            break;


                        default:
                            silent.send("Invalid option, use localization, category or item", messageContext.chatId());
                            break;
                    }
                })
                .build();
    }

}
