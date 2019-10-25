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
                "*Example of inputs acceptable:*\n" +
                "/register *localization*  `Room A309`*,* `It's a study room`\n" +
                "/register *category* `Computers`*,* `Category of all computers`\n" +
                "/register *item* `Computer`*,* `An Dell XPS 2015`*,* `Room A309`*,* `Computers`\n" +
                "`————————————————————————`\n" +
                "/list *is used to list all localizations, categories, items or all items by localization*\n" +
                "/list *type*\n" +
                "/list *item by localization*, `where`\n" +
                "/list *item with description*, `partial description`\n" +
                "`——————`\n" +
                "*Example of inputs acceptable:*\n" +
                "/list *localizations*\n" +
                "/list *categories*\n" +
                "/list *items*\n" +
                "/list *items by localization* `Room A309`*,*\n" +
                "/list *items with description* `XPS 2015`*,*\n" +
                "`————————————————————————`\n" +
                "/search *is used to list a UNIQUE localization, category or item*\n" +
                "/search *type* `uniqueName`\n" +
                "/search *type* `uniqueId`\n" +
                "`——————`\n" +
                "\n" +
                "*Example of inputs acceptable:*\n" +
                "/search *localization* `Room A309`\n" +
                "/search *category* `Computers`\n" +
                "/search *item* `359`\n" +
                "`————————————————————————`\n" +
                "/report *will generate an report of database*\n" +
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
                .action(messageContext -> silent.sendMd("*Hello, these are my commands* \n" + commands, messageContext.chatId())) // Action of the command
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
                    String name = "";
                    String description = "";
                    String localizationName = "";
                    String categoryName = "";

                    switch (messageContext.firstArg()) {
                        case "localization":
                            if (!sigmonioService.verifyArguments(argument,2)) {
                                silent.sendMd("Verify your arguments, look this example:\n " +
                                        "/register *localization*  `Room A309`*,* `It's a study room`", messageContext.chatId());
                            }
                            name = argument.get(0);
                            description = argument.get(1);
                            if (sigmonioService.verifyLocalization(name)) {
                                silent.send("Localization already exists" , messageContext.chatId());
                            }
                            else {
                                silent.sendMd("*Localization* `has created successfully with name: `" + sigmonioService.saveLocalization(name, description), messageContext.chatId());
                                silent.sendMd("*NOTE:* `This` *name* `is` *unique* , `if you forgotten use` /list", messageContext.chatId());
                            }
                            break;

                        case "category":
                            if (!sigmonioService.verifyArguments(argument,2)) {
                                silent.sendMd("Verify your arguments, look this example:\n " +
                                        "/register *category* `Computers`*,* `Category of all computers`", messageContext.chatId());
                            }
                            name = argument.get(0);
                            description = argument.get(1);
                            if (sigmonioService.verifyCategory(name)) {
                                silent.send("Category already exists" , messageContext.chatId());
                            }
                            else {
                                silent.sendMd("*Category* `has created successfully with name: `" + sigmonioService.saveCategory(name, description), messageContext.chatId());
                                silent.sendMd("*NOTE:* `This` *name* `is` *unique* , `if you forgotten use` /list", messageContext.chatId());
                            }
                            break;
                        case "item":
                            if (!sigmonioService.verifyArguments(argument,4)) {
                                silent.sendMd("Verify your arguments, look this example:\n " +
                                        "/register *category* `Computers`*,* `Category of all computers`", messageContext.chatId());
                            }
                            name = argument.get(0);
                            description = argument.get(1);
                            localizationName = argument.get(2);
                            categoryName = argument.get(3);

                            if (!sigmonioService.verifyLocalization(localizationName))
                            {
                                silent.send("Localization not found" , messageContext.chatId());

                            }
                            if (!sigmonioService.verifyCategory(categoryName)) {
                                silent.send("Category not found" , messageContext.chatId());
                            }
                            else {
                                silent.sendMd("*Item* `has created successfully with `*ID: *`" + sigmonioService.saveItem(name, description, localizationName, categoryName) + "`", messageContext.chatId());
                                silent.sendMd("*NOTE:* `This` *ID* `is` *unique* , `if you forgotten use` /list", messageContext.chatId());
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
                .info("list all locations, categories, items or items of localization") // info of command
                .privacy(PUBLIC)  // Who can use this command
                .locality(ALL) // Where this command can be use
                .input(0) // Number of required arguments
                .action(messageContext -> {
                    String localizationName = "";
                    String partialDescription = "";
                    switch (messageContext.firstArg()) {

                        case "localizations":
                            silent.sendMd(sigmonioService.showLocalizations(), messageContext.chatId());
                            break;

                        case "categories":
                            silent.sendMd(sigmonioService.showCategories(), messageContext.chatId());

                            break;
                        case "items":
                            if(messageContext.arguments().length > 1){
                                ArrayList<String> argument = sigmonioService.sanitizeArguments(messageContext.arguments());
                                if( argument.get(0).equals("with description")) {
                                    partialDescription = argument.get(1);
                                    silent.sendMd(sigmonioService.showItemsBySomeDescription(partialDescription), messageContext.chatId());
                                }
                                if( argument.get(0).equals("by localization")){
                                    localizationName = argument.get(1);
                                    silent.sendMd(sigmonioService.showItemsByLocalizationName(localizationName), messageContext.chatId());

                                }
                            }else{
                                silent.sendMd(sigmonioService.showItems(), messageContext.chatId());
                            }

                            break;


                        default:
                            silent.send("Invalid option, use localizations, categories or items", messageContext.chatId());
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
