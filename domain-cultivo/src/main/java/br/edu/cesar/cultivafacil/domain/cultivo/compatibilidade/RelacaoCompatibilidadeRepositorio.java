package br.edu.cesar.cultivafacil.domain.cultivo.compatibilidade;

import br.edu.cesar.cultivafacil.domain.cultivo.cultura.CulturaId;

import java.util.Optional;

public interface RelacaoCompatibilidadeRepositorio {

    void salvar(RelacaoCompatibilidade relacao);

    Optional<RelacaoCompatibilidade> buscarPorCulturas(CulturaId primeiraCulturaId, CulturaId segundaCulturaId);
}
