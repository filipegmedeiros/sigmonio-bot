package com.lp2.sigmonio.repository;

import com.lp2.sigmonio.model.Localization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalizationRepository extends JpaRepository<Localization, String> {
}
