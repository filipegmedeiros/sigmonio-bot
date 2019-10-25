package com.lp2.sigmonio.service;

import com.lp2.sigmonio.model.Localization;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Map;

public interface LocalizationService {
    Localization save(Localization localization);
    Map<String, Boolean> deleteLocalization(String localizationId);
    ResponseEntity<Localization> updateByName(Localization localization, String name);
    List<Localization> findAll();
    List<Localization> findByNameContains(String name);

    boolean exists(String name);

    Localization findByName(String name);
}
