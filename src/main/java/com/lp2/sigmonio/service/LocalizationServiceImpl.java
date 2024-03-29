package com.lp2.sigmonio.service;

import com.lp2.sigmonio.exception.ResourceNotFoundException;
import com.lp2.sigmonio.model.Localization;
import com.lp2.sigmonio.repository.LocalizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalizationServiceImpl implements LocalizationService {

    private LocalizationRepository localizationRepository;

    @Autowired
    public LocalizationServiceImpl(LocalizationRepository localizationRepository) {
        this.localizationRepository = localizationRepository;
    }

    @Override
    public Localization save(Localization localization) {
        return localizationRepository.save(localization);
    }

    @Override
    public String deleteLocalization(String localizationName)
            throws ResourceNotFoundException {
        Localization localization = findByName(localizationName);
        localizationRepository.delete(localization);
        return "Deleted Successfully";
    }

    @Override
    public ResponseEntity<Localization> updateByName(Localization localizationDetails, String localizationName) {
        Localization localization = findByName(localizationName);
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
    public List<Localization> findByNameContains(String name) {
        return localizationRepository.findAllByNameContains(name);
    }

    @Override
    public Localization findByName(String name) {
        return localizationRepository.findByName(name).orElseThrow(()
                -> new ResourceNotFoundException("Localization not found for this name : "
                + name));
    }

    @Override
    public boolean exists(String name) {
        return localizationRepository.existsByName(name);
    }
}
