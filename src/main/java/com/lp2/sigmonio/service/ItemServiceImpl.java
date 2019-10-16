package com.lp2.sigmonio.service;

import com.lp2.sigmonio.exception.ResourceNotFoundException;
import com.lp2.sigmonio.model.Item;
import com.lp2.sigmonio.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService{

    @Autowired
    private ItemRepository itemRepository;


    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Map<String, Boolean> deleteItem(Long itemId)
            throws ResourceNotFoundException {
        Item item = findById(itemId);
        itemRepository.delete(item);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }


    public ResponseEntity<Item> updateById(Item itemDetails, long itemId) {
        Item item = findById(itemId);
        item.setName(itemDetails.getName());
        item.setDescription(itemDetails.getDescription());
        ;
        final Item updatedItem = itemRepository.save(item);
        return ResponseEntity.ok(updatedItem);
    }


    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public ResponseEntity<Item> findOneById(long itemId) {
        Item item = findById(itemId);
        return ResponseEntity.ok().body(item);
    }

    @Override
    public ResponseEntity<Item> findLocalizationByItem(Item item) {
        return null;
    }

    @Override
    public Item findById(long itemId) {
        return itemRepository.findById(itemId).orElseThrow(()
                -> new ResourceNotFoundException("Localization not found for this id :: "
                + itemId));
    }

}
