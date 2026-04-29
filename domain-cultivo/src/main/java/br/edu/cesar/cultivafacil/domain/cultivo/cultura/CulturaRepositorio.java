package br.edu.cesar.cultivafacil.domain.cultivo.cultura;

import br.edu.cesar.cultivafacil.domain.propriedade.propriedade.PropriedadeId;

import java.util.Optional;

public interface CulturaRepositorio {

    void salvar(Cultura cultura);

    boolean existePorNomeEVariedade(PropriedadeId propriedadeId, NomeComumCultura nomeComum, Variedade variedade);

    Optional<Cultura> buscarPorNome(PropriedadeId propriedadeId, NomeComumCultura nomeComum);
}
