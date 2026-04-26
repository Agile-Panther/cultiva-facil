package br.edu.cesar.cultivafacil.domain.cultivo.compatibilidade;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.NomeCultura;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;

import java.util.List;
import java.util.Optional;

public interface RelacaoCompatibilidadeRepositorio {

    void salvar(RelacaoCompatibilidade relacao);

    Optional<RelacaoCompatibilidade> buscarPorCulturas(NomeCultura culturaBase, NomeCultura culturaRelacionada);

    void salvarConsorcio(ConsorcioCultura consorcio);

    List<ConsorcioCultura> listarConsorcioPorTalhao(TalhaoId talhaoId);
}
