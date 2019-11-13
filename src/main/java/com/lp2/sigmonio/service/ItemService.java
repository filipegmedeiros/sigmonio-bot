package com.lp2.sigmonio.service;

import com.lp2.sigmonio.model.Item;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ItemService {

    /**
     *
     * @param item object item
     * @return save in database
     */
    Item save(Item item);

    /**
     *
     * @param id item id
     * @return delete item with that id
     */
    String deleteItem(String id);

    /**
     *
     * @param item item object
     * @param id item id
     * @return updated item
     */
    ResponseEntity<Item> updateById(Item item, String id);

    /**
     *
     * @param itemId item id
     * @param newLocalizationName a new localization
     */
    void updateItemLocalization(String itemId, String newLocalizationName);

    /**
     *
     * @param itemId item id
     * @param newCategoryName a new category
     */
    void updateItemCategory(String itemId, String newCategoryName);

    /**
     *
     * @return list of all items
     */
    List<Item> findAll();

    /**
     *
     * @param id item id
     * @return return a object item with that id
     */
    ResponseEntity<Item> findOneById(String id);

    /**
     *
     * @param id item id
     * @return return a object item with that id
     */
    Item findById(long id);

    /**
     *
     * @param name localization name
     * @return items with that localization
     */
    List<Item> findItemsByLocalization(String name);

    /**
     *
     * @param name category name
     * @return items with that category
     */
    List<Item> findItemsByCategory(String name);

    /**
     *
     * @param someDescription partial some description
     * @return items with that partial text
     */
    List<Item> findItemsByDescription(String someDescription);


}
