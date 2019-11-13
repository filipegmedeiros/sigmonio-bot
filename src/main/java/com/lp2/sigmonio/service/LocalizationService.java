package com.lp2.sigmonio.service;

import com.lp2.sigmonio.model.Localization;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LocalizationService {
    /**
     *
     * @param localization localization object
     * @return saved in bd
     */
    Localization save(Localization localization);

    /**
     *
     * @param localizationName localization name
     * @return returns if deleted or not of database
     */
    String deleteLocalization(String localizationName);

    /**
     *
     * @param localization localization object
     * @param name localization name
     * @return updated object of that name
     */
    ResponseEntity<Localization> updateByName(Localization localization, String name);

    /**
     *
     * @return all localizations of database
     */
    List<Localization> findAll();

    /**
     *
     * @param name partial localization name
     * @return find name with contains partial name
     */
    List<Localization> findByNameContains(String name);

    /**
     *
     * @param name localization name
     * @return find localization by name
     */
    Localization findByName(String name);

    /**
     *
     * @param name localization
     * @return verify if exists
     */
    boolean exists(String name);

}
