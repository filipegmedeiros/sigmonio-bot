package com.lp2.sigmonio.bot.service;

import com.lp2.sigmonio.exception.ResourceNotFoundException;
import com.lp2.sigmonio.model.Category;
import com.lp2.sigmonio.model.Item;
import com.lp2.sigmonio.model.Localization;
import com.lp2.sigmonio.service.CategoryService;
import com.lp2.sigmonio.service.ItemService;
import com.lp2.sigmonio.service.LocalizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SigmonioServiceImpl implements SigmonioService {

    private LocalizationService localizationService;
    private CategoryService categoryService;
    private ItemService itemService;

    @Autowired
    public SigmonioServiceImpl(ItemService itemService,
                               LocalizationService localizationService,
                               CategoryService categoryService) {
        this.itemService = itemService;
        this.localizationService = localizationService;
        this.categoryService = categoryService;
    }

    @Override
    public ArrayList<String> sanitizeArguments(String[] arguments, boolean cutFirst) {
        if (cutFirst) {
            arguments = Arrays.copyOfRange(arguments, 1, arguments.length);
        }

        ArrayList<String> sanitizedArguments = new ArrayList<String>();

        StringBuilder temporaryContent = new StringBuilder();

        for (String argument : arguments) {
            if (!argument.contains(","))
                temporaryContent.append(argument).append(" ");
            else {
                temporaryContent.append(argument);
                temporaryContent.deleteCharAt(temporaryContent.length() - 1);
                sanitizedArguments.add(temporaryContent.toString().trim());
                temporaryContent = new StringBuilder();
            }

        }
        temporaryContent.deleteCharAt(temporaryContent.length() - 1);
        sanitizedArguments.add(temporaryContent.toString());

        return sanitizedArguments;
    }

    @Override
    public String showLocalization(String name) {
        Localization localization = localizationService.findByName(name);
        return "`—————LOCALIZATION—————`\n" +
                "*ID:* `" + localization.getId() + "`\n" +
                "*NAME:* `" + localization.getName() + "`\n" +
                "*DESCRIPTION:* `"+ localization.getDescription() + "`\n" +
                "`——————————————————————`";
    }

    @Override
    public String showCategory(String name) {
        Category category = categoryService.findByName(name);
        return "`———————CATEGORY———————`\n" +
                "*ID:* `" + category.getId() + "`\n" +
                "*NAME:*` " + category.getName() + "`\n" +
                "*DESCRIPTION:* `"+ category.getDescription() + "`\n" +
                "`—————————————————————`";
    }

    @Override
    public String showItem(String id) {
        Item item = itemService.findById(Integer.parseInt(id));
        return "`—————————ITEM———————`\n" +
                "*ID:* `" + item.getId() + "`\n" +
                "*NAME:* `" + item.getName() + "`\n" +
                "*DESCRIPTION:* `"+ item.getDescription() + "`\n" +
                "*CATEGORY:* `" + item.getCategory().getName() + "`\n" +
                "*LOCALIZATION: *`" + item.getLocalization().getName() + "`\n" +
                "`————————————————————`";
    }

    @Override
    public String showReview() {
        StringBuilder content = new StringBuilder();
        List<Localization> localizationList = new ArrayList<Localization>();
        List<Category> categoryList = new ArrayList<Category>();
        List<Item> allItems = itemService.findAll();

        allItems.forEach(item -> {
            if (!localizationList.contains(item.getLocalization()))
                localizationList.add(item.getLocalization());
        });

        localizationList.forEach(localization -> {
            content.append("\n`—————LOCALIZATION—————`\n")
                    .append(localization.getName())
                    .append("\n`——————————————————————`\n");

            List<Item> itemsOfThatLocalization = itemService.findItemsByLocalization(localization.getName());

            itemsOfThatLocalization.forEach(item -> {
                if (!categoryList.contains(item.getCategory()))
                    categoryList.add(item.getCategory());
            });

            categoryList.forEach(category -> {
                content.append("`CATEGORY`\n")
                        .append(category.getName())
                        .append("\n`—————————`\n");

                itemsOfThatLocalization.forEach(item -> {
                    if(item.getCategory().getName().equals(category.getName())) {
                        content.append("*ID:* ")
                                .append(item.getId())
                                .append(", *Name:* ")
                                .append(item.getName()).append("\n");
                    }
                });
            });
            itemsOfThatLocalization.clear();
            categoryList.clear();
        });

        return content.toString();
    }

    @Override
    public String showLocalizations(){
        List<Localization> allItems = localizationService.findAll();
        StringBuilder content = new StringBuilder();
        allItems.forEach(localization -> {
            content.append(showLocalization(localization.getName())).append("\n");
        });
        return content.toString();
    }

    @Override
    public String showCategories() {
        List<Category> allItems = categoryService.findAll();
        StringBuilder content = new StringBuilder();
        allItems.forEach(category -> {
            content.append(showCategory(category.getName())).append("\n");
        });
        return content.toString();
    }

    @Override
    public String showItems() {
        List<Item> allItems = itemService.findAll();
        StringBuilder content = new StringBuilder();
        allItems.forEach(item -> {
            content.append(showItem(String.valueOf(item.getId()))).append("\n");
        });
        return content.toString();
    }

    @Override
    public String showItemsByLocalizationName(String name) {
        List<Item> allItems = itemService.findItemsByLocalization(name);
        StringBuilder content = new StringBuilder();
        allItems.forEach(item -> {
            content.append(showItem(String.valueOf(item.getId()))).append("\n");
        });
        return content.toString();
    }

    @Override
    public String showItemsByCategoryName(String name) {
        List<Item> allItems = itemService.findItemsByCategory(name);
        StringBuilder content = new StringBuilder();
        allItems.forEach(item -> {
            content.append(showItem(String.valueOf(item.getId()))).append("\n");
        });
        return content.toString();
    }

    @Override
    public String showItemsBySomeDescription(String someDescription) {
        List<Item> allItems = itemService.findItemsByDescription(someDescription);
        StringBuilder content = new StringBuilder();
        allItems.forEach(item -> {
            content.append(showItem(String.valueOf(item.getId()))).append("\n");
        });
        return content.toString();
    }

    @Override
    public String listOfLocalizationsByNameContains(String name){
        List<Localization> allLocalizations = localizationService.findByNameContains(name);
        StringBuilder content = new StringBuilder();
        allLocalizations.forEach(localization -> {
            content.append(localization.getName()).append(", ");
        });
        return content.toString();
    }

    @Override
    public String listOfCategoriesByNameContains(String name){
        List<Category> allCategories = categoryService.findByNameContains(name);
        StringBuilder content = new StringBuilder();
        allCategories.forEach(category -> {
            content.append(category.getName()).append(", ");
        });
        return content.toString();
    }

    @Override
    public boolean verifyArguments(ArrayList<String> arguments, int size) {
        return arguments.size() == size;
    }

    @Override
    public boolean verifyLocalization(String name) {
        return localizationService.exists(name);
    }

    @Override
    public boolean verifyCategory(String name) {
        return categoryService.exists(name);
    }

    @Override
    public String saveLocalization(String name, String description) {
        Localization localization = new Localization();
        localization.setName(name);
        localization.setDescription(description);
        localizationService.save(localization);
        return localization.getName();
    }

    @Override
    public String saveCategory(String name, String description) {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        categoryService.save(category);
        return category.getName();
    }

    @Override
    public String saveItem(String name, String description,
                           String LocalizationName, String categoryName){
        Item item = new Item();
        item.setName(name);
        item.setDescription(description);
        item.setLocalization(localizationService.findByName(LocalizationName));
        item.setCategory(categoryService.findByName(categoryName));
        itemService.save(item);
        return String.valueOf(item.getId());
    }

    @Override
    public void moveItem(String itemId, String newLocalizationName) throws ResourceNotFoundException{
        try {
            itemService.updateItemLocalization(itemId, newLocalizationName);
        }
        catch (ResourceNotFoundException resourceNotFoundException) {
            throw resourceNotFoundException;
        }

    }

    @Override
    public String deleteLocalization(String name) {
        List<Item> itemList = itemService.findItemsByLocalization(name);

        if(!itemList.isEmpty()) {
            return "*Can't Delete That Localization!!!!* \n\n" +
                    "*Needed delete or move that items first:* \n" +
                    showItemsByLocalizationName(name);
        }else
        return localizationService.deleteLocalization(name);
    }

    @Override
    public String deleteCategory(String name) {
        List<Item> itemList = itemService.findItemsByCategory(name);

        if(!itemList.isEmpty()) {
            return "*Can't Delete That Category!!!!* \n\n" +
                    "*Needed delete or move that items first:* \n" +
                    showItemsByCategoryName(name);
        }else
            return categoryService.deleteCategory(name);
    }

    @Override
    public String deleteItem(String name) {
        return itemService.deleteItem(name);
    }
}

