package br.edu.cesar.cultivafacil.domain.cultivos.repository;

import br.edu.cesar.cultivafacil.domain.cultivos.ConsorcioCultura;
import br.edu.cesar.cultivafacil.domain.cultivos.NomeCultura;
import br.edu.cesar.cultivafacil.domain.cultivos.RelacaoCompatibilidade;
import br.edu.cesar.cultivafacil.shared.ZonaId;

import java.util.List;
import java.util.Optional;

public interface RelacaoCompatibilidadeRepositorio {

    void salvar(RelacaoCompatibilidade relacao);

    Optional<RelacaoCompatibilidade> buscarPorCulturas(NomeCultura culturaBase, NomeCultura culturaRelacionada);

    void salvarConsorcio(ConsorcioCultura consorcio);

    List<ConsorcioCultura> listarConsorcioPorZona(ZonaId zonaId);
}
