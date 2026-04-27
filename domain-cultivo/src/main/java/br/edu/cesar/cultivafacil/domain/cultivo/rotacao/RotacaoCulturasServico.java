package br.edu.cesar.cultivafacil.domain.cultivo.rotacao;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.NomeCultura;
import br.edu.cesar.cultivafacil.domain.terreno.zona.ZonaId;

import java.time.LocalDate;

public class RotacaoCulturasServico {

    private final IntervalodeDescansoRepositorio repositorio;

    public RotacaoCulturasServico(IntervalodeDescansoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    // F-08 RN-051 e RN-052
    public void cadastrarIntervalo(ZonaId zonaId, NomeCultura cultura, DiasDescanso dias) {
        if (!repositorio.existeCicloEncerrado(zonaId, cultura)) {
            throw new IllegalArgumentException(
                    "INTERVALO_SEM_HISTORICO: a Zona nao possui ciclo encerrado para " + cultura);
        }
        repositorio.salvar(new IntervalodeDescanso(zonaId, cultura, dias, LocalDate.now()));
    }

    // F-08 RN-053
    public void validarDescanso(ZonaId zonaId, NomeCultura cultura, LocalDate dataVinculo) {
        repositorio.buscarIntervalo(zonaId, cultura).ifPresent(intervalo -> {
            if (!intervalo.foiCumprido(dataVinculo)) {
                throw new IllegalArgumentException(
                        "INTERVALO_NAO_CUMPRIDO: o Intervalo de Descanso para "
                                + cultura + " ainda nao foi cumprido");
            }
        });
    }
}
