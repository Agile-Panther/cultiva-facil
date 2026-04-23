package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CicloAgricolaRepositorio {

    void salvar(CicloAgricola ciclo);

    Optional<CicloAgricola> buscarPorId(UUID id);

    List<CicloAgricola> buscarPorZonaId(UUID zonaId);

    Optional<CicloAgricola> buscarCicloAtivoPorZonaId(UUID zonaId);
}
