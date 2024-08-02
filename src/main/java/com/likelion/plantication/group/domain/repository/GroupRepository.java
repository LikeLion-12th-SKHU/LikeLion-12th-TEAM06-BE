package com.likelion.plantication.group.domain.repository;


import com.likelion.plantication.group.domain.Groups;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Groups, Long> {
}
