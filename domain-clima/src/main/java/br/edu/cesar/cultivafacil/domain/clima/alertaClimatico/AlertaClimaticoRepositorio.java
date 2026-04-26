package br.edu.cesar.cultivafacil.domain.clima.alertaClimatico;

import br.edu.cesar.cultivafacil.domain.clima.alertaClimatico.AlertaClimatico;
import br.edu.cesar.cultivafacil.domain.clima.alertaClimatico.AlertaClimaticoId;

import java.util.Optional;

public interface AlertaClimaticoRepositorio {
    void salvar(AlertaClimatico alertaClimatico);
    Optional<AlertaClimatico> buscarPorId(AlertaClimaticoId id);
}

