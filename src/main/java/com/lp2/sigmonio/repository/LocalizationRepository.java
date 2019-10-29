package com.lp2.sigmonio.repository;

import com.lp2.sigmonio.model.Localization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocalizationRepository extends JpaRepository<Localization, String> {
    Optional<Localization> findByName(String name);
    boolean existsByName(String name);
    List<Localization> findAllByNameContains(String name);
}
