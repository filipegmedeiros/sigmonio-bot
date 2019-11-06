package com.lp2.sigmonio.bot.service;

import java.util.ArrayList;

public interface SigmonioService {

    /**
     *
     * @param arguments arguments passed by telegram user
     * @param size size needed by function
     * @return verify if arguments is of correctly size.
     */
    boolean verifyArguments(ArrayList<String> arguments, int size);

    /**
     *
     * @param arguments arguments passed by telegram user
     * @param cutFirst first string to cut
     * @return sanitized arguments
     */
    ArrayList<String> sanitizeArguments(String[] arguments, boolean cutFirst);

    /**
     *
     * @param name localization name
     * @return return String with details about localization
     */
    String showLocalization(String name);

    /**
     *
     * @param name category name
     * @return return String with details about category
     */
    String showCategory(String name);

    /**
     *
     * @param id item id
     * @return return String with details about item
     */
    String showItem(String id);

    /**
     *
     * @return a String with list of items organized by localization, category and name asc.
     */
    String showReview();

    /**
     *
     * @return a String with full localizations
     */
    String showLocalizations();

    /**
     *
     * @return a String with full categories
     */
    String showCategories();

    /**
     *
     * @return a String with full items
     */
    String showItems();

    /**
     *
     * @param name localization Name
     * @return return a list of items of that localization
     */
    String showItemsByLocalizationName(String name);

    /**
     *
     * @param name category name
     * @return return a list of items of that category
     */
    String showItemsByCategoryName(String name);

    /**
     *
     * @param someDescription a partial description
     * @return return a list of items who contains that partial description
     */
    String showItemsBySomeDescription(String someDescription);

    /**
     *
     * @param name some partial localization name
     * @return return a list of localizations with partial name
     */
    String listOfLocalizationsByNameContains(String name);

    /**
     *
     * @param name some partial category name
     * @return return a list of categories with partial name
     */
    String listOfCategoriesByNameContains(String name);

    /**
     *
     * @param name localization name
     * @return a boolean value if exists or not.
     */
    boolean verifyLocalization(String name);

    /**
     *
     * @param name category name
     * @return a boolean value if exists or not.
     */
    boolean verifyCategory(String name);

    /**
     *
     * @param name name of localization
     * @param description description of localization
     * @return save that localization in database
     */
    String saveLocalization(String name, String description);

    /**
     *
     * @param name name of category
     * @param description category of localization
     * @return save that category in database
     */
    String saveCategory(String name, String description);

    /**
     *
     * @param name name of item
     * @param description name of description
     * @param LocalizationName name of localization
     * @param categoryName name of category
     * @return save item in database
     */
    String saveItem(String name, String description, String LocalizationName, String categoryName);

    /**
     *
     * @param itemId item ID
     * @param newLocalizationName a new localization name
     */
    void moveItemForNewLocalization(String itemId, String newLocalizationName);

    /**
     *
     * @param itemId item ID
     * @param newCategoryName a new category name
     */
    void moveItemForNewCategory(String itemId, String newCategoryName);

    /**
     *
     * @param name name of localization how be deleted.
     * @return if is deleted or not.
     */
    String deleteLocalization(String name);

    /**
     *
     * @param name name of category how be deleted.
     * @return if is deleted or not.
     */

    String deleteCategory(String name);
    /**
     *
     * @param name name of item how be deleted.
     * @return if is deleted or not.
     */
    String deleteItem(String name);

}
