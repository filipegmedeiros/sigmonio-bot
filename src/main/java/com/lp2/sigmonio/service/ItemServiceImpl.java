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

    private ItemRepository itemRepository;
    private LocalizationService localizationService;
    private CategoryService categoryService;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository,
                           LocalizationService localizationService,
                           CategoryService categoryService) {
        this.itemRepository = itemRepository;
        this.localizationService = localizationService;
        this.categoryService = categoryService;
    }


    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Map<String, Boolean> deleteItem(String itemId)
            throws ResourceNotFoundException {
        Item item = findById(Integer.parseInt(itemId));
        itemRepository.delete(item);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    public ResponseEntity<Item> updateById(Item itemDetails, String itemId) {

        Item item = findById(Integer.parseInt(itemId));
        item.setName(itemDetails.getName());
        item.setDescription(itemDetails.getDescription());
        final Item updatedItem = itemRepository.save(item);
        return ResponseEntity.ok(updatedItem);
    }

    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public ResponseEntity<Item> findOneById(String itemId) {
        Item item = findById(Integer.parseInt(itemId));
        return ResponseEntity.ok().body(item);
    }

    @Override
    public ResponseEntity<Item> findLocalizationByItem(Item item) {
        return null;
    }

    @Override
    public Item findById(long itemId) {
        return itemRepository.findById(itemId).orElseThrow(()
                -> new ResourceNotFoundException("Localization not found for this id: "
                + itemId));
    }

    @Override
    public List<Item> findItemsByDescription(String someDescription) {
        return itemRepository.findAllByDescriptionContains(someDescription);
    }

    @Override
    public List<Item> findItemsByLocalization(String name) {
        return itemRepository.findAllByLocalization(localizationService.findByName(name));
    }

    @Override
    public List<Item> findItemsByCategory(String name) {
        return itemRepository.findAllByCategory(categoryService.findByName(name));
    }

}
