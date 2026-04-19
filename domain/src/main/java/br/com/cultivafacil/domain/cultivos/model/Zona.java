package br.com.cultivafacil.domain.cultivos.model;

import br.com.cultivafacil.domain.cultivos.exception.IntervaloNaoCumpridoException;
import br.com.cultivafacil.domain.cultivos.exception.IntervaloSemHistoricoException;
import br.com.cultivafacil.domain.cultivos.exception.ZonaComCultivoAtivoException;
import br.com.cultivafacil.domain.cultivos.vo.DiasDescanso;
import br.com.cultivafacil.domain.cultivos.vo.StatusZona;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Zona {

    private StatusZona situacao;
    private final List<CicloAgricola> ciclos;
    private final Map<String, DiasDescanso> intervalosDescanso;

    public Zona() {
        this.situacao = StatusZona.VAZIA;
        this.ciclos = new ArrayList<>();
        this.intervalosDescanso = new HashMap<>();
    }

    public void vincularCultura(String nomeCultura) {
        if (situacao == StatusZona.ATIVA) {
            throw new ZonaComCultivoAtivoException();
        }
        validarIntervaloDescanso(nomeCultura);
        ciclos.add(new CicloAgricola(nomeCultura));
        situacao = StatusZona.ATIVA;
    }

    public void encerrarCiclo(String nomeCultura, LocalDate dataColheita) {
        cicloAtivoParaCultura(nomeCultura).ifPresent(ciclo -> {
            ciclo.encerrar(dataColheita);
            situacao = StatusZona.VAZIA;
        });
    }

    public void definirIntervaloDescanso(String nomeCultura, DiasDescanso diasDescanso) {
        boolean temHistoricoEncerrado = ciclos.stream()
                .anyMatch(c -> c.getNomeCultura().equals(nomeCultura)
                        && c.getStatus() == CicloAgricola.StatusCiclo.ENCERRADO);

        if (!temHistoricoEncerrado) {
            throw new IntervaloSemHistoricoException();
        }
        intervalosDescanso.put(nomeCultura, diasDescanso);
    }

    public StatusZona getSituacao() {
        return situacao;
    }

    public List<CicloAgricola> getCiclos() {
        return Collections.unmodifiableList(ciclos);
    }

    private void validarIntervaloDescanso(String nomeCultura) {
        DiasDescanso intervalo = intervalosDescanso.get(nomeCultura);
        if (intervalo == null) {
            return;
        }

        dataUltimaColheitaParaCultura(nomeCultura).ifPresent(dataColheita -> {
            LocalDate dataLiberacao = dataColheita.plusDays(intervalo.getDias());
            if (LocalDate.now().isBefore(dataLiberacao)) {
                throw new IntervaloNaoCumpridoException();
            }
        });
    }

    private Optional<CicloAgricola> cicloAtivoParaCultura(String nomeCultura) {
        return ciclos.stream()
                .filter(c -> c.getNomeCultura().equals(nomeCultura)
                        && c.getStatus() == CicloAgricola.StatusCiclo.ATIVO)
                .findFirst();
    }

    private Optional<LocalDate> dataUltimaColheitaParaCultura(String nomeCultura) {
        return ciclos.stream()
                .filter(c -> c.getNomeCultura().equals(nomeCultura)
                        && c.getStatus() == CicloAgricola.StatusCiclo.ENCERRADO)
                .map(CicloAgricola::getDataColheita)
                .max(LocalDate::compareTo);
    }
}