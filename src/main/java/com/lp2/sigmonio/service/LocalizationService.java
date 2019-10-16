package com.lp2.sigmonio.service;

import com.lp2.sigmonio.model.Localization;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface LocalizationService {
    Localization save(Localization localization);
    public Map<String, Boolean> deleteLocalization(Long localizationId);
    ResponseEntity<Localization> updateById(Localization localization, long id);
    List<Localization> findAll();
    ResponseEntity<Localization> findOneById(long id);
}
