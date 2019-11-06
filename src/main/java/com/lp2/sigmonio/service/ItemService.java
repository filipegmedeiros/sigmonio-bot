package com.lp2.sigmonio.service;

import com.lp2.sigmonio.model.Item;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ItemService {
    Item save(Item item);

    String deleteItem(String id);

    ResponseEntity<Item> updateById(Item item, String id);

    void updateItemLocalization(String itemId, String newLocalizationName);
    void updateItemCategory(String itemId, String newCategoryName);


    List<Item> findAll();

    Item findById(long id);

    List<Item> findItemsByLocalization(String name);

    List<Item> findItemsByCategory(String name);

    List<Item> findItemsByDescription(String someDescription);

    ResponseEntity<Item> findOneById(String id);
}
