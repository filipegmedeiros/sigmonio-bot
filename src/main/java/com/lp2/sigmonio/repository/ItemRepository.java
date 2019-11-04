package com.lp2.sigmonio.repository;

import com.lp2.sigmonio.model.Category;
import com.lp2.sigmonio.model.Item;
import com.lp2.sigmonio.model.Localization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByLocalizationOrderByNameAsc(Localization Localization);
    List<Item> findAllByCategory(Category Category);
    List<Item> findAllByDescriptionContains(String someDescription);
}