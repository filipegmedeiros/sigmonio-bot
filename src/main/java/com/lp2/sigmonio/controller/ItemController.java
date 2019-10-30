package com.lp2.sigmonio.controller;

import com.lp2.sigmonio.model.Item;
import com.lp2.sigmonio.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class ItemController {

    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/item")
    public List<Item> getAllItems() {
        return itemService.findAll();
    }

    @GetMapping("/item/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable(value = "id") String itemId){
        return itemService.findOneById(itemId);
    }

    @PostMapping("/item")
    public Item createItem(@Valid @RequestBody Item item) {
        return itemService.save(item);
    }

    @PutMapping("/item/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable(value = "id") String itemId,
                                           @Valid @RequestBody Item itemDetails){
        return itemService.updateById(itemDetails, itemId);
    }

    @DeleteMapping("/item/{id}")
    public String deleteItem(@PathVariable(value = "id") String itemId){
        return itemService.deleteItem(itemId);
    }
}