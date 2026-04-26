package br.edu.cesar.cultivafacil.domain.clima.limite;

import br.edu.cesar.cultivafacil.domain.clima.limite.LimiteClimatico;
import br.edu.cesar.cultivafacil.domain.clima.limite.LimiteClimaticoId;

import java.util.Optional;

public interface LimiteClimaticoRepositorio {
    void salvar(LimiteClimatico limiteClimatico);
    Optional<LimiteClimatico> buscarPorId(LimiteClimaticoId id);
}

