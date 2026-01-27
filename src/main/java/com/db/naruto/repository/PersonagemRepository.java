package com.db.naruto.repository;

import com.db.naruto.model.Personagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonagemRepository extends JpaRepository<Personagem, Integer> {
}