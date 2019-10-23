package com.lp2.sigmonio.bot.service;

import java.util.ArrayList;

public interface SigmonioService {

    boolean verifyArguments(ArrayList<String> arguments , int size);
    ArrayList<String> sanitizeArguments(String[] arguments);

    boolean verifyLocalization(String name);
    boolean verifyCategory(String name);

    void saveLocalization(String name, String description);
    void saveCategory(String name, String description);
    void saveItem(String name, String description, String LocalizationName, String categoryName);

}