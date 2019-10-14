package com.lp2.sigmonio.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;


import com.lp2.sigmonio.repository.AssetsRepository;
import com.lp2.sigmonio.repository.LocalizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lp2.sigmonio.exception.ResourceNotFoundException;
import com.lp2.sigmonio.model.Localization;

@RestController
@RequestMapping("/")
public class LocalizationController {
    @Autowired
    private LocalizationRepository localizationRepository;

    @GetMapping("/localization")
    public List<Localization> getAllLocalizations() {
        return localizationRepository.findAll();
    }

    @GetMapping("/localization/{id}")
    public ResponseEntity<Localization> getLocalizationById(@PathVariable(value = "id") Long localizationId)
            throws ResourceNotFoundException {
        Localization localization = localizationRepository.findById(localizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Localization not found for this id :: " + localizationId));
        return ResponseEntity.ok().body(localization);
    }

    @PostMapping("/localization")
    public Localization createLocalization(@Valid @RequestBody Localization localization) {
        return localizationRepository.save(localization);
    }

    @PutMapping("/localization/{id}")
    public ResponseEntity<Localization> updateLocalization(@PathVariable(value = "id") Long localizationId,
                                                       @Valid @RequestBody Localization localizationDetails) throws ResourceNotFoundException {
        Localization localization = localizationRepository.findById(localizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Localization not found for this id :: " + localizationId));

        localization.setTitle(localizationDetails.getTitle());
        localization.setDescription(localizationDetails.getDescription());
        final Localization updatedLocalization = localizationRepository.save(localization);
        return ResponseEntity.ok(updatedLocalization);
    }

    @DeleteMapping("/localization/{id}")
    public Map<String, Boolean> deleteLocalization(@PathVariable(value = "id") Long localizationId)
            throws ResourceNotFoundException {
        Localization localization = localizationRepository.findById(localizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Localization not found for this id :: " + localizationId));

        localizationRepository.delete(localization);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}