package com.lp2.sigmonio.service;

import com.lp2.sigmonio.exception.ResourceNotFoundException;
import com.lp2.sigmonio.model.Localization;
import com.lp2.sigmonio.repository.LocalizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LocalizationServiceImpl implements LocalizationService{

    @Autowired
    private LocalizationRepository localizationRepository;

    @Override
    public Localization save(Localization localization) {
        return localizationRepository.save(localization);
    }

    @Override
    public Map<String, Boolean> deleteLocalization(Long localizationId)
            throws ResourceNotFoundException {
        Localization localization = findById(localizationId);
        localizationRepository.delete(localization);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    public ResponseEntity<Localization> updateById(Localization localizationDetails, long localizationId) {
        Localization localization = findById(localizationId);
        localization.setName(localizationDetails.getName());
        localization.setDescription(localizationDetails.getDescription());
        final Localization updatedLocalization = localizationRepository.save(localization);
        return ResponseEntity.ok(updatedLocalization);
    }

    @Override
    public List<Localization> findAll() {
        return localizationRepository.findAll();
    }

    @Override
    public ResponseEntity<Localization> findOneById(long localizationId) {
        Localization localization = findById(localizationId);
        return ResponseEntity.ok().body(localization);
    }

    @Override
    public Localization findById(long localizationId) {
        return localizationRepository.findById(localizationId).orElseThrow(()
                -> new ResourceNotFoundException("Localization not found for this id :: "
                + localizationId));
    }


    @Override
    public Optional<Localization> find(Update update) {
        String text = update.getMessage().getText();
        return localizationRepository.findById(Long.parseLong(text));
    }
}
