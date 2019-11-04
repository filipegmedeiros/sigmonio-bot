package com.lp2.sigmonio.service;

import com.lp2.sigmonio.model.Localization;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LocalizationService {
    Localization save(Localization localization);

    String deleteLocalization(String localizationName);

    ResponseEntity<Localization> updateByName(Localization localization, String name);

    List<Localization> findAll();

    List<Localization> findByNameContains(String name);

    Localization findByName(String name);

    boolean exists(String name);

}
