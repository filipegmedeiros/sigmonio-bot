package com.lp2.sigmonio.service;

import com.lp2.sigmonio.model.Item;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ItemService {
    Item save(Item item);
    Map<String, Boolean> deleteItem(String localizationId);
    ResponseEntity<Item> updateById(Item item, String id);
    List<Item> findAll();
    ResponseEntity<Item> findOneById(String id);
    List<Item> findItensByLocalization(String name);
    ResponseEntity<Item> findLocalizationByItem(Item item);
    Item findById(String id);
}
