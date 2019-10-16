package com.lp2.sigmonio.service;

import com.lp2.sigmonio.model.Item;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ItemService {
    Item save(Item item);
    Map<String, Boolean> deleteItem(Long localizationId);
    ResponseEntity<Item> updateById(Item item, long id);
    List<Item> findAll();
    ResponseEntity<Item> findOneById(long id);
    ResponseEntity<Item> findLocalizationByItem(Item item);
    Item findById(long id);

}
