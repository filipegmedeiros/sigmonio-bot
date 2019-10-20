package com.lp2.sigmonio.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.lp2.sigmonio.service.LocalizationService;
import com.lp2.sigmonio.service.LocalizationServiceImpl;
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

import com.lp2.sigmonio.model.Localization;

@RestController
@RequestMapping("/")
public class LocalizationController {

    @Autowired
    private LocalizationService localizationService;

    @GetMapping("/localization")
    public List<Localization> getAllLocalizations() {
        return localizationService.findAll();
    }

    @GetMapping("/localization/{id}")
    public ResponseEntity<Localization> getLocalizationById(@PathVariable(value = "id") Long localizationId){
        return localizationService.findOneById(localizationId);
    }

    @PostMapping("/localization")
    public Localization createLocalization(@Valid @RequestBody Localization localization) {
        return localizationService.save(localization);
    }

    @PutMapping("/localization/{id}")
    public ResponseEntity<Localization> updateLocalization(@PathVariable(value = "id") Long localizationId,
                                                           @Valid @RequestBody Localization localizationDetails){

        return localizationService.updateById(localizationDetails, localizationId);
    }

    @DeleteMapping("/localization/{id}")
    public Map<String, Boolean> deleteLocalization(@PathVariable(value = "id") Long localizationId){
        return localizationService.deleteLocalization(localizationId);
    }
}