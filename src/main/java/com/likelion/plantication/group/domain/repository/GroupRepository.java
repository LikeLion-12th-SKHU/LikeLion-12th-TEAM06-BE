package com.likelion.plantication.group.domain.repository;


import com.likelion.plantication.group.domain.PlantGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<PlantGroup, Long> {
}
