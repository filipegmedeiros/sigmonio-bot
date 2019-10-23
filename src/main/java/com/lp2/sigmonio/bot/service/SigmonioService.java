package com.lp2.sigmonio.bot.service;

import com.lp2.sigmonio.model.Category;
import com.lp2.sigmonio.model.Item;
import com.lp2.sigmonio.model.Localization;

import java.util.ArrayList;
import java.util.List;

public interface SigmonioService {

    boolean verifyArguments(ArrayList<String> arguments , int size);
    ArrayList<String> sanitizeArguments(String[] arguments);

    List<Localization> showLocalization();
    List<Category> showCategory();
    List<Item> showItem();
    List<Item> showItemByLocalizationName(String name);

    boolean verifyLocalization(String name);
    boolean verifyCategory(String name);

    void saveLocalization(String name, String description);
    void saveCategory(String name, String description);
    void saveItem(String name, String description, String LocalizationName, String categoryName);

}
