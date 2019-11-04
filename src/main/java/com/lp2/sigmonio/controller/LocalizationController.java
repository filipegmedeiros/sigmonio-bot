package com.lp2.sigmonio.controller;

import com.lp2.sigmonio.model.Localization;
import com.lp2.sigmonio.service.LocalizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/")
public class LocalizationController {

    private LocalizationService localizationService;

    @Autowired
    public LocalizationController(LocalizationService localizationService) {
        this.localizationService = localizationService;
    }

    @GetMapping("/localization")
    public List<Localization> getAllLocalizations() {
        return localizationService.findAll();
    }

    @PostMapping("/localization")
    public Localization createLocalization(@Valid @RequestBody Localization localization) {
        return localizationService.save(localization);
    }

    @PutMapping("/localization/{id}")
    public ResponseEntity<Localization> updateLocalization(@PathVariable(value = "id") String localizationId,
                                                           @Valid @RequestBody Localization localizationDetails) {
        return localizationService.updateByName(localizationDetails, localizationId);
    }

    @DeleteMapping("/localization/{name}")
    public String deleteLocalization(@PathVariable(value = "name") String localizationName) {
        return localizationService.deleteLocalization(localizationName);
    }
}