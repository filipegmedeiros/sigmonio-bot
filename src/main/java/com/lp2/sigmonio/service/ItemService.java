package com.lp2.sigmonio.service;

import com.lp2.sigmonio.model.Item;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ItemService {
    Item save(Item item);
    Map<String, Boolean> deleteItem(String localizationId);
    ResponseEntity<Item> updateById(Item item, String id);

    Item findById(long id);
    List<Item> findAll();
    List<Item> findItemsByLocalization(String name);
    List<Item> findItemsByCategory(String name);

    List<Item> findItemsByDescription(String someDescription);


    ResponseEntity<Item> findOneById(String id);
    ResponseEntity<Item> findLocalizationByItem(Item item);


}
