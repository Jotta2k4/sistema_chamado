package com.example.sistema_chamado.repositories;

import com.example.sistema_chamado.models.Technical;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface TechnicalRepository extends JpaRepository<Technical, Integer> {
    List<Technical> findAllByName(String name);

    Optional<Technical> findByName(String name);
}
