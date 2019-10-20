package com.lp2.sigmonio.bot;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;

import java.sql.*;

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

    public Ability list() {
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
                        case "items": //Not working
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

    private boolean insert(String local, String[] data)
    {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:15432/postgres",
                            "postgres", "passwd");

            statement = connection.createStatement();
            String content = "";
            if (local.equals("localization"))
            {
                content = "INSERT INTO LOCALIZATION (NAME,DESCRIPTION) " +
                        String.format("VALUES ('%s', '%s');", data[0], data[1]);
            }
            else if (local.equals("category"))
            {
                content = "INSERT INTO CATEGORY (NAME,DESCRIPTION) " +
                        String.format("VALUES ('%s', '%s');", data[0], data[1]);
            }
            else if (local.equals("item"))
            {
                content = "INSERT INTO ITEM (NAME,DESCRIPTION,LOCALIZATION_ID,CATEGORY_ID) " +
                        String.format("VALUES ('%s', '%s', '%d', '%d');", data[0], data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]));
            }
            statement.executeUpdate(content);
            statement.close();
            connection.close();
        } catch ( Exception e ) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private String getList(String local) {
        String list = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
            try {
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:15432/postgres",
                            "postgres", "passwd");

            if (local.equals("localization"))
            {
                preparedStatement = connection.prepareStatement("SELECT * FROM LOCALIZATION");
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    list += resultSet.getString(2) + "\n";
                }
            }
            else if (local.equals("categories"))
            {
                preparedStatement = connection.prepareStatement("SELECT * FROM CATEGORY");
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    list += resultSet.getString(2) + "\n";
                }
            }
            else if (local.equals("items"))
            {
                preparedStatement = connection.prepareStatement("SELECT * FROM ITEM");
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    list += resultSet.getString(2) + "\n";
                }
            }
            connection.close();
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        return list;
    }
}
