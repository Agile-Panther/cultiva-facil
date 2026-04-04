package br.com.cultivafacil.domain.cultivos.model;

import br.com.cultivafacil.domain.cultivos.exception.IntervaloSemHistoricoException;
import br.com.cultivafacil.domain.cultivos.exception.ZonaComCultivoAtivoException;
import br.com.cultivafacil.domain.cultivos.vo.DiasDescanso;
import br.com.cultivafacil.domain.cultivos.vo.StatusZona;

import java.util.ArrayList;
import java.util.List;

public class Zona {

    private StatusZona situacao;
    private final List<CicloAgricola> ciclos;

    public Zona() {
        this.situacao = StatusZona.VAZIA;
        this.ciclos = new ArrayList<>();
    }

    public void vincularCultura(String nomeCultura) {
        if (situacao == StatusZona.ATIVA) {
            throw new ZonaComCultivoAtivoException();
        }
        CicloAgricola ciclo = new CicloAgricola(nomeCultura);
        this.ciclos.add(ciclo);
        this.situacao = StatusZona.ATIVA;
    }

    public void definirIntervaloDescanso(String nomeCultura, DiasDescanso diasDescanso) {
        boolean temHistoricoEncerrado = ciclos.stream()
                .anyMatch(c -> c.getNomeCultura().equals(nomeCultura)
                        && c.getStatus() == CicloAgricola.StatusCiclo.ENCERRADO);

        if (!temHistoricoEncerrado) {
            throw new IntervaloSemHistoricoException();
        }
    }

    public StatusZona getSituacao() {
        return situacao;
    }

    public List<CicloAgricola> getCiclos() {
        return ciclos;
    }
}