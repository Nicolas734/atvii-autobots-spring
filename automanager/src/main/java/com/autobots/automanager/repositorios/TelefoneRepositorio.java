package com.autobots.automanager.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.entidades.Telefone;

public interface TelefoneRepositorio extends JpaRepository<Telefone, Long> {

}
