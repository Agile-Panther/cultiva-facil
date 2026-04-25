package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;

import java.time.LocalDate;

public class RotacaoCulturasServico {

    private final IntervalodeDescansoRepositorio repositorio;

    public RotacaoCulturasServico(IntervalodeDescansoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    // F-08 RN-051 e RN-052
    public void cadastrarIntervalo(TalhaoId talhaoId, NomeCultura cultura, DiasDescanso dias) {
        if (!repositorio.existeCicloEncerrado(talhaoId, cultura)) {
            throw new IllegalArgumentException(
                    "INTERVALO_SEM_HISTORICO: o Talhao nao possui ciclo encerrado para " + cultura);
        }
        repositorio.salvar(new IntervalodeDescanso(talhaoId, cultura, dias, LocalDate.now()));
    }

    // F-08 RN-053
    public void validarDescanso(TalhaoId talhaoId, NomeCultura cultura, LocalDate dataVinculo) {
        repositorio.buscarIntervalo(talhaoId, cultura).ifPresent(intervalo -> {
            if (!intervalo.foiCumprido(dataVinculo)) {
                throw new IllegalArgumentException(
                        "INTERVALO_NAO_CUMPRIDO: o Intervalo de Descanso para "
                                + cultura + " ainda nao foi cumprido");
            }
        });
    }
}
