package br.edu.cesar.cultivafacil.domain.colheita.repository;


import br.edu.cesar.cultivafacil.domain.colheita.Celeiro;
import br.edu.cesar.cultivafacil.shared.AgricultorId;
import br.edu.cesar.cultivafacil.shared.ZonaId;

import java.util.List;
import java.util.Optional;

public interface CeleiroRepositorio {

    void salvar(Celeiro celeiro);

    Optional<Celeiro> buscarPorZonaId(ZonaId zonaId);

    List<Celeiro> buscarPorPropriedadeId(AgricultorId agricultorId);
}
