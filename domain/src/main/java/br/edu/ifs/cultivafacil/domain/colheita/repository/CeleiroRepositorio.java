package br.edu.ifs.cultivafacil.domain.colheita.repository;


import br.edu.ifs.cultivafacil.domain.colheita.Celeiro;
import br.edu.ifs.cultivafacil.domain.colheita.ZonaId;

import java.util.List;
import java.util.Optional;

public interface CeleiroRepositorio {

    void salvar(Celeiro celeiro);

    Optional<Celeiro> buscarPorZonaId(ZonaId zonaId);

    List<Celeiro> buscarPorPropriedadeId(AgricultorId agricultorId);
}
