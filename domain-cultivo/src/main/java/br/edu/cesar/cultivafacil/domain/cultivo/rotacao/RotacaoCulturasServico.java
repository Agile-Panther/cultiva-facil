package br.edu.cesar.cultivafacil.domain.cultivo.rotacao;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.NomeCultura;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;

import java.time.LocalDate;

public class RotacaoCulturasServico {

    private final PoliticaDescansoSoloRepositorio repositorio;

    public RotacaoCulturasServico(PoliticaDescansoSoloRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    // F-08 RN-065 e RN-066
    public void cadastrarIntervalo(TalhaoId talhaoId, NomeCultura cultura, DiasDescanso dias) {
        if (!repositorio.existeCicloEncerrado(talhaoId, cultura)) {
            throw new IllegalArgumentException(
                    "TALHAO_INVALIDO: o Talhao nao possui ciclo encerrado para " + cultura);
        }
        var politica = new PoliticaDescansoSolo(talhaoId, cultura, dias, LocalDate.now());
        repositorio.salvar(politica);
    }

    // F-08 RN-067 e RN-068
    public void concederDispensa(TalhaoId talhaoId, NomeCultura cultura,
                                  JustificativaDispensa justificativa, LocalDate dataAtual) {
        var politica = repositorio.buscarPorTalhaoECultura(talhaoId, cultura)
                .orElseThrow(() -> new IllegalArgumentException(
                        "TALHAO_INVALIDO: nao existe Intervalo de Descanso vigente para dispensar"));
        politica.concederDispensa(justificativa, dataAtual);
        repositorio.salvar(politica);
    }
}
