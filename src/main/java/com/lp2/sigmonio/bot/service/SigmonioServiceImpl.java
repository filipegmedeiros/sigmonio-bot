package com.lp2.sigmonio.bot.service;

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

@Service
public class SigmonioServiceImpl implements SigmonioService {

    @Autowired
    private LocalizationService localizationService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ItemService itemService;


    @Override
    public boolean verifyArguments(ArrayList<String> arguments, int size) {
        return arguments.size() == size;
    }

    @Override
    public ArrayList<String> sanitizeArguments(String[] arguments) {
        arguments = Arrays.copyOfRange(arguments, 1, arguments.length);

        ArrayList<String> sanitizedArguments = new ArrayList<String>();

        StringBuilder temporaryContent = new StringBuilder();

        for (String argument : arguments) {
            if (!argument.contains(","))
                temporaryContent.append(argument).append(" ");
            else {
                temporaryContent.append(argument);
                temporaryContent.deleteCharAt(temporaryContent.length() - 1);
                sanitizedArguments.add(temporaryContent.toString());
                temporaryContent = new StringBuilder();
            }

        }
        temporaryContent.deleteCharAt(temporaryContent.length() - 1);
        sanitizedArguments.add(temporaryContent.toString());

        return sanitizedArguments;
    }

    @Override
    public boolean verifyLocalization(String name) {
        return localizationService.exists(name);
    }

    @Override
    public boolean verifyCategory(String name) {
        return false;
    }

    @Override
    public void saveLocalization(String name, String description) {
        Localization localization = new Localization();
        localization.setName(name);
        localization.setDescription(description);
        localizationService.save(localization);
    }

    @Override
    public void saveCategory(String name, String description) {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        categoryService.save(category);
    }

    @Override
    public void saveItem(String name, String description,
                         String LocalizationName, String categoryName){
        Item item = new Item();
        item.setName(name);
        item.setDescription(description);
        item.setLocalization(localizationService.findByName(LocalizationName));
        item.setCategory(categoryService.findByName(categoryName));
        itemService.save(item);
    };

}

