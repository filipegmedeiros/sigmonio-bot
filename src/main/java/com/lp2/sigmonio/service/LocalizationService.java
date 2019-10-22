package com.lp2.sigmonio.service;

import com.lp2.sigmonio.model.Localization;
import org.springframework.http.ResponseEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LocalizationService {
    Localization save(Localization localization);
    Map<String, Boolean> deleteLocalization(String localizationId);
    ResponseEntity<Localization> updateById(Localization localization, String id);
    List<Localization> findAll();
    ResponseEntity<Localization> findOneById(String id);
    Localization findById(String id);

    boolean existLocalizationByName(String name);
    Optional<Localization> find(Update update);
}
