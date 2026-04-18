package br.com.cultivafacil.domain.clima.repository;

import br.com.cultivafacil.domain.clima.model.LimiteClimatico;
import br.com.cultivafacil.domain.clima.vo.LimiteClimaticoId;

import java.util.Optional;

public interface LimiteClimaticoRepositorio {
    void salvar(LimiteClimatico limiteClimatico);
    Optional<LimiteClimatico> buscarPorId(LimiteClimaticoId id);
}

