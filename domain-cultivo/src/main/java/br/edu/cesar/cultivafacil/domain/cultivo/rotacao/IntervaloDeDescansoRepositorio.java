package br.edu.cesar.cultivafacil.domain.cultivo.rotacao;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.NomeCultura;

import java.util.Optional;
import java.util.UUID;

public interface IntervaloDeDescansoRepositorio {

    void salvar(IntervaloDeDescanso intervalo);

    Optional<IntervaloDeDescanso> buscarPorZonaIdENomeCultura(UUID zonaId, NomeCultura nomeCultura);
}
