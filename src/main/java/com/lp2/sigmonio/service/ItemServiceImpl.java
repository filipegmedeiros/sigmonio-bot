package com.lp2.sigmonio.service;

import com.lp2.sigmonio.exception.ResourceNotFoundException;
import com.lp2.sigmonio.model.Category;
import com.lp2.sigmonio.model.Item;
import com.lp2.sigmonio.model.Localization;
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

    @Autowired
    private LocalizationService localizationService;

    @Autowired
    private CategoryService categoryService;


    @Override
    public Item save(Item item) {
        Localization localization = localizationService
                .findById(item.getLocalization_id());

        Category category = categoryService
                .findById(item.getCategory_id());
        item.setCategory(category);
        item.setLocalization(localization);
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
        Localization localization = localizationService
                .findById(itemDetails.getLocalization_id());
        Category category = categoryService
                .findById(itemDetails.getCategory_id());

        Item item = findById(itemId);
        item.setName(itemDetails.getName());
        item.setDescription(itemDetails.getDescription());
        item.setLocalization_id(itemDetails.getLocalization_id());
        item.setLocalization(localization);
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
