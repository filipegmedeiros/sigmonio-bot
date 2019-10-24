package com.lp2.sigmonio.bot.service;

import com.lp2.sigmonio.model.Category;
import com.lp2.sigmonio.model.Item;
import com.lp2.sigmonio.model.Localization;

import java.util.ArrayList;
import java.util.List;

public interface SigmonioService {

    boolean verifyArguments(ArrayList<String> arguments , int size);
    ArrayList<String> sanitizeArguments(String[] arguments);

    List<Localization> showLocalizations();
    List<Category> showCategorys();
    List<Item> showItems();
    List<Item> showItemsByLocalizationName(String name);


    String showLocalization(String name);
    String showCategory(String name);
    String showItem(String id);

    boolean verifyLocalization(String name);
    boolean verifyCategory(String name);

    String saveLocalization(String name, String description);
    String saveCategory(String name, String description);
    Long saveItem(String name, String description, String LocalizationName, String categoryName);

}
