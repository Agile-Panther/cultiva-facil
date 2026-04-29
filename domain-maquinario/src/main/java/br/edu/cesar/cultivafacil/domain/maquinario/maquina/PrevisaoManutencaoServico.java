package br.edu.cesar.cultivafacil.domain.maquinario.maquina;

import br.edu.cesar.cultivafacil.domain.maquinario.maquina.events.AlertaManutencaoIminente;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PrevisaoManutencaoServico {

    private static final BigDecimal LIMITE_ALERTA_HORAS = new BigDecimal("50");
    private static final long LIMITE_ALERTA_DIAS = 15L;

    private final MatrizDesgasteRepositorio matrizRepositorio;

    public PrevisaoManutencaoServico(MatrizDesgasteRepositorio matrizRepositorio) {
        Objects.requireNonNull(matrizRepositorio, "MatrizDesgasteRepositorio nao pode ser nulo");
        this.matrizRepositorio = matrizRepositorio;
    }

    // RN-049
    public Optional<DataEstimadaManutencao> calcularDataEstimada(Maquina maquina, LocalDate hoje) {
        Optional<MatrizDesgasteFabricante> matrizOpt = matrizRepositorio.buscarPorModelo(maquina.getModelo());
        if (matrizOpt.isEmpty()) return Optional.empty();
        BigDecimal horasRestantes = matrizOpt.get().getLimiteHoras().horasRestantes(maquina.getHorimetro());
        return projetarData(maquina, horasRestantes, hoje);
    }

    // RN-050
    public Optional<AlertaManutencaoIminente> avaliar(Maquina maquina) {
        return avaliar(maquina, LocalDate.now());
    }

    public Optional<AlertaManutencaoIminente> avaliar(Maquina maquina, LocalDate hoje) {
        Optional<MatrizDesgasteFabricante> matrizOpt = matrizRepositorio.buscarPorModelo(maquina.getModelo());
        if (matrizOpt.isEmpty()) return Optional.empty();

        MatrizDesgasteFabricante matriz = matrizOpt.get();
        BigDecimal horasRestantes = matriz.getLimiteHoras().horasRestantes(maquina.getHorimetro());

        boolean alertaPorHoras = horasRestantes.compareTo(LIMITE_ALERTA_HORAS) < 0;

        Optional<DataEstimadaManutencao> dataEstimadaOpt = projetarData(maquina, horasRestantes, hoje);
        boolean alertaPorData = dataEstimadaOpt
                .map(d -> ChronoUnit.DAYS.between(hoje, d.getValor()) < LIMITE_ALERTA_DIAS)
                .orElse(false);

        if (alertaPorHoras || alertaPorData) {
            return Optional.of(new AlertaManutencaoIminente(
                    maquina.getId(),
                    horasRestantes,
                    dataEstimadaOpt.orElse(null)));
        }

        return Optional.empty();
    }

    private Optional<DataEstimadaManutencao> projetarData(Maquina maquina, BigDecimal horasRestantes, LocalDate hoje) {
        if (horasRestantes.compareTo(BigDecimal.ZERO) <= 0) {
            return Optional.of(new DataEstimadaManutencao(hoje));
        }
        BigDecimal media = calcularMediaDiaria(maquina, hoje);
        if (media.compareTo(BigDecimal.ZERO) <= 0) return Optional.empty();
        long dias = horasRestantes.divide(media, 0, RoundingMode.CEILING).longValue();
        return Optional.of(new DataEstimadaManutencao(hoje.plusDays(dias)));
    }

    BigDecimal calcularMediaDiaria(Maquina maquina, LocalDate hoje) {
        List<ApontamentoUso> historico = maquina.getHistorico();
        if (historico.isEmpty()) return BigDecimal.ZERO;

        ApontamentoUso ultimoApontamento = historico.get(historico.size() - 1);
        LocalDate dataInicial = maquina.getDataRegistro();
        BigDecimal horimetroInicial = maquina.getHorimetroInicial().getValor();

        long diasDecorridos = ChronoUnit.DAYS.between(dataInicial, ultimoApontamento.getData());
        if (diasDecorridos <= 0) return BigDecimal.ZERO;

        BigDecimal totalHoras = ultimoApontamento.getHorimetro().getValor().subtract(horimetroInicial);
        if (totalHoras.compareTo(BigDecimal.ZERO) <= 0) return BigDecimal.ZERO;

        return totalHoras.divide(BigDecimal.valueOf(diasDecorridos), 4, RoundingMode.HALF_UP);
    }
}
