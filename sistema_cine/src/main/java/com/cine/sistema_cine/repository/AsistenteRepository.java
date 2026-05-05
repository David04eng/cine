package com.cine.sistema_cine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cine.sistema_cine.model.Asistente;

@Repository
public interface AsistenteRepository extends JpaRepository<Asistente, Long> {
}