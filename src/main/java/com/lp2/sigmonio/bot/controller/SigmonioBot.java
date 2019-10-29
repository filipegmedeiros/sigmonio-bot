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

    private static String hello = "`I'm a heritage robot! With me you can add locations, categories and items!\n" +
            "Also, search, list and more!`\n" +
            "*To see how I work, use /commands  ^^*";

    private static String commandsList = "`————————————————————————`\n" +
            "*Summary:* word `type` means it can be *localization* or *category*" +
            "`————————————————————————`\n" +
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
            "/list *items by localization*, `where`\n" +
            "/list *items by category*, `where`\n" +
            "/list *items by description*, `partial description`\n" +
            "`——————`\n" +
            "*Example of inputs acceptable:*\n" +
            "/list *localizations*\n" +
            "/list *categories*\n" +
            "/list *items*\n" +
            "/list *items by localization,* `Room A309`\n" +
            "/list *items by category,* `Computers`\n" +
            "/list *items by description,* `XPS 2015`\n" +
            "`————————————————————————`\n" +
            "/search *is used to show a UNIQUE localization, category or item*\n" +
            "/search *type* `uniqueName`\n" +
            "/search *item* `uniqueId`\n" +
            "`——————`\n" +
            "*Example of inputs acceptable:*\n" +
            "/search *localization* `Room A309`\n" +
            "/search *category* `Computers`\n" +
            "/search *item* `359`\n" +
            "`————————————————————————`\n" +
            "/Review *will generate an report of database*\n" +
            "`————————————————————————`\n" +
            "\n" +
            "*OBS: arguments its separate by comma (,) attention to that!*";

    private SigmonioService sigmonioService;

    @Autowired
    protected SigmonioBot(SigmonioService sigmonioService, Environment env) {
        super(env.getProperty("bot.token"), env.getProperty("bot.username"));
        this.sigmonioService = sigmonioService;
    }

    @Override
    public int creatorId() {
        return 432232268;
    }

    public Ability start() {
        return Ability.builder()
                .name("start") // Command
                .info("Start") // info of command
                .privacy(PUBLIC)  // Who can use this command
                .locality(ALL) // Where this command can be use
                .input(0) // Number of required arguments
                .action(messageContext -> silent.sendMd("*Hello friend " + messageContext.user().getFirstName() + " !* \n" + hello, messageContext.chatId())) // Action of the command
                .build();
    }

    public Ability commands() {
        return Ability.builder()
                .name("commands") // Command
                .info("commands") // info of command
                .privacy(PUBLIC)  // Who can use this command
                .locality(ALL) // Where this command can be use
                .input(0) // Number of required arguments
                .action(messageContext -> silent.sendMd("*Hello, these are my commands* \n" + commandsList, messageContext.chatId())) // Action of the command
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
                    String name;
                    String description;
                    String localizationName;
                    String categoryName;

                    switch (messageContext.firstArg()) {
                        case "localization":
                            if (!sigmonioService.verifyArguments(argument, 2)) {
                                silent.sendMd("Verify your arguments, look this example:\n " +
                                        "/register *localization*  `Room A309`*,* `It's a study room`", messageContext.chatId());
                            }
                            name = argument.get(0);
                            description = argument.get(1);
                            if (sigmonioService.verifyLocalization(name)) {
                                silent.send("Localization already exists", messageContext.chatId());
                            } else {
                                silent.sendMd("*Localization* `has created successfully with name: `" + sigmonioService.saveLocalization(name, description), messageContext.chatId());
                                silent.sendMd("*NOTE:* `This` *name* `is` *unique* , `if you forgotten use` /list", messageContext.chatId());
                            }
                            break;

                        case "category":
                            if (!sigmonioService.verifyArguments(argument, 2)) {
                                silent.sendMd("Verify your arguments, look this example:\n " +
                                        "/register *category* `Computers`*,* `Category of all computers`", messageContext.chatId());
                            }
                            name = argument.get(0);
                            description = argument.get(1);
                            if (sigmonioService.verifyCategory(name)) {
                                silent.send("Category already exists", messageContext.chatId());
                            } else {

                                silent.sendMd("*Category* `has created successfully with name: `" + sigmonioService.saveCategory(name, description), messageContext.chatId());
                                silent.sendMd("*NOTE:* `This` *name* `is` *unique* , `if you forgotten use` /list", messageContext.chatId());
                            }
                            break;
                        case "item":
                            if (!sigmonioService.verifyArguments(argument, 4)) {
                                silent.sendMd("Verify your arguments, look this example:\n " +
                                        "/register *category* `Computers`*,* `Category of all computers`", messageContext.chatId());
                            }
                            name = argument.get(0);
                            description = argument.get(1);
                            localizationName = argument.get(2);
                            categoryName = argument.get(3);

                            if (!sigmonioService.verifyLocalization(localizationName)) {
                                silent.sendMd("*Localization not found :(*", messageContext.chatId());
                                String listOfContainsLocalizationName = sigmonioService.listOfLocalizationsByNameContains(localizationName);
                                if (!listOfContainsLocalizationName.isEmpty() || !listOfContainsLocalizationName.equals(""))
                                    silent.sendMd("Didn't you mean one of these? `" + listOfContainsLocalizationName + "`", messageContext.chatId());
                            }
                            if (!sigmonioService.verifyCategory(categoryName)) {
                                silent.sendMd("*Category not found :(*", messageContext.chatId());
                                String listOfContainsCategoryName = sigmonioService.listOfCategoriesByNameContains(categoryName);
                                if (!listOfContainsCategoryName.isEmpty() || !listOfContainsCategoryName.equals(""))
                                    silent.sendMd("Didn't you mean one of these? `" + listOfContainsCategoryName + "`", messageContext.chatId());
                            } else {
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
                    String localizationName;
                    String categoryName;
                    String partialDescription;
                    switch (messageContext.firstArg()) {
                        case "localizations":
                            silent.sendMd(sigmonioService.showLocalizations(), messageContext.chatId());
                            break;
                        case "categories":
                            silent.sendMd(sigmonioService.showCategories(), messageContext.chatId());
                            break;
                        case "items":
                            if (messageContext.arguments().length > 1) {
                                ArrayList<String> argument = sigmonioService.sanitizeArguments(messageContext.arguments());
                                if (argument.get(0).equals("by description")) {
                                    partialDescription = argument.get(1);
                                    silent.sendMd(sigmonioService.showItemsBySomeDescription(partialDescription), messageContext.chatId());
                                }
                                if (argument.get(0).equals("by localization")) {
                                    localizationName = argument.get(1);
                                    silent.sendMd(sigmonioService.showItemsByLocalizationName(localizationName), messageContext.chatId());
                                }
                                if (argument.get(0).equals("by category")) {
                                    categoryName = argument.get(1);
                                    silent.sendMd(sigmonioService.showItemsByCategoryName(categoryName), messageContext.chatId());
                                }
                            } else
                                silent.sendMd(sigmonioService.showItems(), messageContext.chatId());
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
                            silent.sendMd(sigmonioService.showLocalization(name), messageContext.chatId());
                            break;
                        case "category":
                            silent.sendMd(sigmonioService.showCategory(name), messageContext.chatId());
                            break;
                        case "item":
                            silent.sendMd(sigmonioService.showItem(name), messageContext.chatId());
                            break;
                        default:
                            silent.send("Invalid option, use localization, category or item", messageContext.chatId());
                            break;
                    }
                })
                .build();
    }

    public Ability review() {
        return Ability.builder()
                .name("review") // Command
                .info("review") // info of command
                .privacy(PUBLIC)  // Who can use this command
                .locality(ALL) // Where this command can be use
                .input(0) // Number of required arguments
                .action(messageContext -> silent.sendMd( sigmonioService.showReview(), messageContext.chatId()))
                .build();
    }
}
