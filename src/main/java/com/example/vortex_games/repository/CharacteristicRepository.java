package com.example.vortex_games.repository;

import com.example.vortex_games.entity.Characteristic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CharacteristicRepository extends JpaRepository<Characteristic,Long> {

    Optional<Characteristic> findByName(String name);
}