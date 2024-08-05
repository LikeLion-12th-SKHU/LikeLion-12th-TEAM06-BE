package com.likelion.plantication.plantGuide.domain.repository;

import com.likelion.plantication.plantGuide.domain.PlantGuide;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GuideRepository extends JpaRepository<PlantGuide, Long> {
    Optional<List<PlantGuide>> findByUser_UserId(Long userId);
}
