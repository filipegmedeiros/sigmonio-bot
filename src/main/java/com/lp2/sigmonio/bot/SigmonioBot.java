package com.lp2.sigmonio.bot;

import com.lp2.sigmonio.model.Localization;
import com.lp2.sigmonio.service.LocalizationService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;

import java.util.ArrayList;
import java.util.Arrays;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

@Component
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SigmonioBot extends AbilityBot {
    private String commands;

    @Autowired
    LocalizationService localizationService;

    public static final String BOT_TOKEN = "981659676:AAEVBrdcM2vAvZz1Auoe0b-cHyCul7wk62I";
    public static final String BOT_USERNAME = "sigmonio_bot ";

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
        this.commands =
                "/register {localization, category or item} {data} - Register one localization, category or item\n" +
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

    public Ability commandNotFound() {
        return Ability.builder()
                .name(DEFAULT)
                .privacy(PUBLIC)
                .locality(ALL)
                .input(0)
                .action(messageContext -> silent.send("\"" + messageContext.update().getMessage().getText() + "\"" +
                        " is not a command, you can control me by sending these commands:\n" + commands, messageContext.chatId()))
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
                            ArrayList<String> argument = this.separateComma(messageContext.arguments());
                            if(!localizationService.existLocalizationByName(messageContext.secondArg())
                                    && argument.size() == 2) {
                                createLocalization(argument.get(0), argument.get(1));
                                silent.send("Localization has created sucessfuly!", messageContext.chatId());
                            }
                            else {
                                silent.send("that " + argument + " already exists", messageContext.chatId());
                            }
                            break;

                        case "category":
                            if(localizationService.existLocalizationByName(messageContext.secondArg()))

                                break;
                        case "item":
                            break;
                        default:
                            silent.send("Invalid option, use localization, category or item", messageContext.chatId());
                            break;
                    }
                })
                .build();
    }

    private ArrayList<String> separateComma(String[] args){
        args = Arrays.copyOfRange(args, 1, args.length);
        ArrayList<String> finalArgument = new ArrayList<String>();
        StringBuilder content = new StringBuilder();
        for (String argument : args) {
            if (!argument.contains(","))
                content.append(argument).append(" ");
            else {
                content.append(argument);
                content.deleteCharAt(content.length() - 1);
                finalArgument.add(content.toString());
                content = new StringBuilder();
            }

        }
        content.deleteCharAt(content.length() - 1);
        finalArgument.add(content.toString());

        return finalArgument;
    }

    private void createLocalization(String name, String description){
        Localization localization = new Localization();
        localization.setName(name);
        localization.setDescription(description);
        localizationService.save(localization);
    }

}