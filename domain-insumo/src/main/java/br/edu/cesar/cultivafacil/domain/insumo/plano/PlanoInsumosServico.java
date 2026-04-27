package br.edu.cesar.cultivafacil.domain.insumo.plano;

import br.edu.cesar.cultivafacil.domain.acesso.conta.ContaId;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;

import java.util.List;
import java.util.stream.Collectors;

public class PlanoInsumosServico {

    private final PlanoInsumosRepositorio repositorio;

    public PlanoInsumosServico(PlanoInsumosRepositorio repositorio) {
        if (repositorio == null) throw new IllegalArgumentException("Repositorio obrigatorio");
        this.repositorio = repositorio;
    }

    public PlanoInsumos criarPlano(TalhaoId talhaoId, ContaId contaId, MesReferencia mesReferencia) {
        if (repositorio.existePorTalhaoEMes(talhaoId, mesReferencia)) {
            throw new InsumoDomainException(InsumoErroCodigo.PLANO_DUPLICADO,
                    "Ja existe plano de insumos para esta Zona neste mes");
        }
        PlanoInsumos plano = new PlanoInsumos(talhaoId, contaId, mesReferencia);
        repositorio.salvar(plano);
        return plano;
    }

    public List<ItemInsumo> consultarHistorico(TalhaoId talhaoId) {
        return repositorio.listarHistoricoPorTalhao(talhaoId).stream()
                .flatMap(p -> p.getItens().stream())
                .filter(i -> i.getStatus() == StatusItemInsumo.ADQUIRIDO)
                .collect(Collectors.toList());
    }
}