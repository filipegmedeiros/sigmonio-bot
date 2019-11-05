package com.lp2.sigmonio.service;

import com.lp2.sigmonio.exception.ResourceNotFoundException;
import com.lp2.sigmonio.model.Item;
import com.lp2.sigmonio.model.Localization;
import com.lp2.sigmonio.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

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
    public String deleteItem(String itemId)
            throws ResourceNotFoundException {
        Item item = findById(Integer.parseInt(itemId));
        itemRepository.delete(item);
        return "Deleted Successfully!";
    }

    @Override
    public ResponseEntity<Item> updateById(Item itemDetails, String itemId) {
        Item item = findById(Integer.parseInt(itemId));
        item.setName(itemDetails.getName());
        item.setDescription(itemDetails.getDescription());
        final Item updatedItem = itemRepository.save(item);
        return ResponseEntity.ok(updatedItem);
    }

    @Override
    public ResponseEntity<Item> updateItemLocalization(String itemId, String newLocalizationName) throws ResourceNotFoundException {
        Item item = findById(Integer.parseInt(itemId));
        Localization localization = localizationService.findByName(newLocalizationName);
        item.setLocalization(localization);
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
    public Item findById(long itemId) {
        return itemRepository.findById(itemId).orElseThrow(()
                -> new ResourceNotFoundException("Localization not found for this id: "
                + itemId));
    }

    @Override
    public List<Item> findItemsByLocalization(String name) {
        return itemRepository.findAllByLocalizationOrderByNameAsc(localizationService.findByName(name));
    }

    @Override
    public List<Item> findItemsByCategory(String name) {
        return itemRepository.findAllByCategory(categoryService.findByName(name));
    }

    @Override
    public List<Item> findItemsByDescription(String someDescription) {
        return itemRepository.findAllByDescriptionContains(someDescription);
    }
}
