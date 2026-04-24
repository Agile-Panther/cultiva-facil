package br.edu.cesar.cultivafacil.domain.cultivo.rotacao;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.CicloAgricola;
import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.CicloAgricolaRepositorio;
import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.NomeCultura;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RotacaoCulturasServico {

    private final CicloAgricolaRepositorio cicloRepositorio;
    private final IntervaloDeDescansoRepositorio intervaloRepositorio;

    public RotacaoCulturasServico(CicloAgricolaRepositorio cicloRepositorio,
                                  IntervaloDeDescansoRepositorio intervaloRepositorio) {
        this.cicloRepositorio = cicloRepositorio;
        this.intervaloRepositorio = intervaloRepositorio;
    }

    /**
     * RN-051: Define intervalo de descanso para uma cultura em uma Zona.
     * Requer ao menos um ciclo encerrado para a cultura informada naquela Zona.
     */
    public IntervaloDeDescanso definirIntervaloDeDescanso(UUID zonaId, NomeCultura nomeCultura,
                                                          DiasDescanso diasDescanso) {
        List<CicloAgricola> ciclosEncerrados = cicloRepositorio
                .buscarEncerradosPorZonaIdENomeCultura(zonaId, nomeCultura);

        if (ciclosEncerrados.isEmpty()) {
            throw new IntervaloSemHistoricoException();
        }

        IntervaloDeDescanso intervalo = new IntervaloDeDescanso(zonaId, nomeCultura, diasDescanso);
        intervaloRepositorio.salvar(intervalo);
        return intervalo;
    }

    /**
     * RN-053: Valida se o intervalo de descanso foi cumprido antes de permitir novo vínculo.
     * Lança IntervaloNaoCumpridoException se a Zona ainda estiver em período de descanso.
     */
    public void validarIntervaloDeDescanso(UUID zonaId, NomeCultura nomeCultura) {
        Optional<IntervaloDeDescanso> intervaloOpt = intervaloRepositorio
                .buscarPorZonaIdENomeCultura(zonaId, nomeCultura);

        if (intervaloOpt.isEmpty()) {
            return;
        }

        IntervaloDeDescanso intervalo = intervaloOpt.get();

        Optional<LocalDate> ultimaColheita = cicloRepositorio
                .buscarEncerradosPorZonaIdENomeCultura(zonaId, nomeCultura)
                .stream()
                .map(CicloAgricola::getDataColheita)
                .max(LocalDate::compareTo);

        ultimaColheita.ifPresent(dataColheita -> {
            LocalDate dataLiberacao = dataColheita.plusDays(intervalo.getDiasDescanso().getDias());
            if (LocalDate.now().isBefore(dataLiberacao)) {
                throw new IntervaloNaoCumpridoException();
            }
        });
    }
}
