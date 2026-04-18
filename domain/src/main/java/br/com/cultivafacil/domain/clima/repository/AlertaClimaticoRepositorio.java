package br.com.cultivafacil.domain.clima.repository;

import br.com.cultivafacil.domain.clima.model.AlertaClimatico;
import br.com.cultivafacil.domain.clima.vo.AlertaClimaticoId;

import java.util.Optional;

public interface AlertaClimaticoRepositorio {
    void salvar(AlertaClimatico alertaClimatico);
    Optional<AlertaClimatico> buscarPorId(AlertaClimaticoId id);
}

