package com.lp2.sigmonio.bot.service;

import java.util.ArrayList;

public interface SigmonioService {

    boolean verifyArguments(ArrayList<String> arguments, int size);

    ArrayList<String> sanitizeArguments(String[] arguments, boolean cutFirst);

    String showLocalization(String name);

    String showCategory(String name);

    String showItem(String id);

    String showReview();

    String showLocalizations();

    String showCategories();

    String showItems();

    String showItemsByLocalizationName(String name);

    String showItemsByCategoryName(String name);

    String showItemsBySomeDescription(String someDescription);

    String listOfLocalizationsByNameContains(String name);

    String listOfCategoriesByNameContains(String name);

    boolean verifyLocalization(String name);

    boolean verifyCategory(String name);

    String saveLocalization(String name, String description);

    String saveCategory(String name, String description);

    String saveItem(String name, String description, String LocalizationName, String categoryName);

    void moveItem(String itemId, String newLocalizationName);

    String deleteLocalization(String name);

    String deleteCategory(String name);

    String deleteItem(String name);

}
