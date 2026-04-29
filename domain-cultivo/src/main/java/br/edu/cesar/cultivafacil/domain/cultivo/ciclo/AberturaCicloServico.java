package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;

public class AberturaCicloServico {

    private final CicloAgricolaRepositorio repositorio;

    public AberturaCicloServico(CicloAgricolaRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    // F-06 RN-051 e RN-054
    public CicloAgricola abrirCiclo(TalhaoId talhaoId, NomeCultura cultura,
                                     QuantidadePlantada quantidade, UnidadeMedidaCiclo unidade) {
        repositorio.buscarAtivoPorTalhao(talhaoId).ifPresent(c -> {
            throw new IllegalArgumentException(
                    "TALHAO_INVALIDO: o Talhao ja possui um Ciclo Agricola ativo");
        });
        var ciclo = new CicloAgricola(talhaoId, cultura, quantidade, unidade);
        repositorio.salvar(ciclo);
        return ciclo;
    }
}
