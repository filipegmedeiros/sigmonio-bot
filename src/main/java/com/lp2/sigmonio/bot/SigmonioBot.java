package com.lp2.sigmonio.bot;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;

import java.io.IOException;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

public class SigmonioBot extends AbilityBot {
    public static final String BOT_TOKEN = "981659676:AAGEIJZwAqc9H1hnXdZ3CTSGiHyk98FiD0c";
    public static final String BOT_USERNAME = "sigmonio_bot ";
    private String listaDeComandos;

    public SigmonioBot() {
        super(BOT_TOKEN, BOT_USERNAME);
        setListaDeComandos();
    }

    @Override
    public int creatorId() {
        return 432232268;
    }

    private void setListaDeComandos() {
        this.listaDeComandos =
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
                .action(ctx -> silent.send("Hello, here is the commands of me!\n" + listaDeComandos, ctx.chatId())) // Action of the command
                .build();
    }

    public Ability register() {
        return Ability.builder()
                .name("register")
                .info("Register one location, category or item")
                .privacy(PUBLIC)
                .locality(ALL)
                .input(0)
                .action(ctx -> {
                    String[] data;
                    switch (ctx.firstArg()) {
                        case "localization":
                            data = new String[] {ctx.secondArg(), ctx.thirdArg()};
                            if (ctx.arguments().length == 3 && insert("localization", data))
                                silent.send("successfully registered localization", ctx.chatId());
                            else
                                silent.send("Something is wrong, could not register localization", ctx.chatId());
                            break;
                        case "category":
                            data = new String[] {ctx.secondArg(), ctx.thirdArg()};
                            if (ctx.arguments().length == 3 && insert("category", data))
                                silent.send("successfully registered category", ctx.chatId());
                            else
                                silent.send("Something is wrong, could not register category", ctx.chatId());
                            break;
                        case "item":
                            data = new String[] {ctx.secondArg(), ctx.thirdArg(), ctx.arguments()[3], ctx.arguments()[4]};
                            if (ctx.arguments().length == 5 && insert("item", data))
                                silent.send("successfully registered item", ctx.chatId());
                            else
                                silent.send("Something is wrong, could not register item", ctx.chatId());
                            break;
                        default:
                            silent.send("Invalid option, use localization, category or item", ctx.chatId());
                    }
                })
                .build();
    }

    public Ability list() { //Not working
        return Ability.builder()
                .name("list")
                .info("list localizations, categories or items")
                .privacy(PUBLIC)
                .locality(ALL)
                .input(0)
                .action(ctx -> {
                    String list = "";
                    switch (ctx.firstArg()) {
                        case "localizations":
                            list = getList("localization");
                            if (list.length() != 0)
                                silent.send(list, ctx.chatId());
                            else
                                silent.send("Something is wrong, could not list localizations", ctx.chatId());
                            break;
                        case "categories":
                            list = getList("categories");
                            if (list.length() != 0)
                                silent.send(list, ctx.chatId());
                            else
                                silent.send("Something is wrong, could not list categories", ctx.chatId());
                            break;
                        case "items":
                            list = getList("items");
                            if (list.length() != 0)
                                silent.send(list, ctx.chatId());
                            else
                                silent.send("Something is wrong, could not list items", ctx.chatId());
                            break;
                        default:
                            silent.send("Invalid option, use localizations, categories or items", ctx.chatId());
                    }
                })
                .build();
    }

    public Ability commandNotFound() {
        return Ability.builder()
                .name(DEFAULT)
                .privacy(PUBLIC)
                .locality(ALL)
                .input(0)
                .action(ctx -> silent.send("\"" + ctx.update().getMessage().getText() + "\"" +
                        " is not a command, you can control me by sending these commands:\n" + listaDeComandos, ctx.chatId()))
                .build();
    }

    private boolean insert(String local, String[] data) {
        String content = "";
        StringEntity entity;
        CloseableHttpClient httpClient;
        HttpPost request = null;
        if (local.equals("localization")) {
            content = "{" +
                    "\"name\": \"" + data[0] + "\"," +
                    "\"description\": \"" + data[1] + "\"" +
                    "}";

            request = new HttpPost("http://localhost:8080/localization");
        }
        else if (local.equals("category"))
        {
            content = "{" +
                    "\"name\": \"" + data[0] + "\"," +
                    "\"description\": \"" + data[1] + "\"" +
                    "}";

            request = new HttpPost("http://localhost:8080/category");
        }
        else if (local.equals("item"))
        {
            content = "{" +
                    "\"name\": \"" + data[0] + "\"," +
                    "\"description\": \"" + data[1] + "\"," +
                    "\"localization_id\": " + data[2] + "," +
                    "\"category_id\": " + data[3] +
                    "}";

            request = new HttpPost("http://localhost:8080/item");
        }

        entity = new StringEntity(content,
                ContentType.APPLICATION_JSON);
        request.setEntity(entity);
        httpClient = HttpClientBuilder.create().build();
        HttpResponse response = null;
        try {
            response = httpClient.execute(request);
        } catch (IOException e) {
            return false;
        }
        if (response.getStatusLine().getStatusCode() == 200)
            return true;
        else
            return false;
    }

    private String getList(String local) {
        String content = "";
        CloseableHttpClient httpClient;
        HttpGet request = null;
        if (local.equals("localization")) {
            request = new HttpGet("http://localhost:8080/localization");
        }
        else if (local.equals("category"))
        {
            request = new HttpGet("http://localhost:8080/category");
        }
        else if (local.equals("item"))
        {
            request = new HttpGet("http://localhost:8080/item");
        }

        httpClient = HttpClientBuilder.create().build();
        HttpResponse response = null;
        try {
            response = httpClient.execute(request);

            System.out.println(EntityUtils.toString(response.getEntity()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }
}
