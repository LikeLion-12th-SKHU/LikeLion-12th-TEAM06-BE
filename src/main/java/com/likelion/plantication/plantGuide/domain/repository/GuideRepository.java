package com.likelion.plantication.plantGuide.domain.repository;

import com.likelion.plantication.plantGuide.domain.PlantGuide;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuideRepository extends JpaRepository<PlantGuide, Long> {
}
