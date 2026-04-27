package br.edu.cesar.cultivafacil.domain.insumo.plano;

import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;

import java.util.List;
import java.util.Optional;

public interface PlanoInsumosRepositorio {

    void salvar(PlanoInsumos plano);

    Optional<PlanoInsumos> buscarPorId(PlanoInsumosId id);

    List<PlanoInsumos> listarPorTalhao(TalhaoId talhaoId);

    boolean existePorTalhaoEMes(TalhaoId talhaoId, MesReferencia mes);

    /** Retorna apenas planos encerrados da zona — exclui planos com ciclo ativo. */
    List<PlanoInsumos> listarHistoricoPorTalhao(TalhaoId talhaoId);
}