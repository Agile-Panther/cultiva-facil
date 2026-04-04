package br.com.cultivafacil.domain.cultivos.model;

import java.time.LocalDate;

public class CicloAgricola {

    private final String nomeCultura;
    private final LocalDate dataInicio;
    private StatusCiclo status;
    private LocalDate dataColheita;

    public CicloAgricola(String nomeCultura) {
        this.nomeCultura = nomeCultura;
        this.dataInicio = LocalDate.now();
        this.status = StatusCiclo.ATIVO;
    }

    public void encerrar(LocalDate dataColheita) {
        this.dataColheita = dataColheita;
        this.status = StatusCiclo.ENCERRADO;
    }

    public String getNomeCultura() {
        return nomeCultura;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataColheita() {
        return dataColheita;
    }

    public StatusCiclo getStatus() {
        return status;
    }

    public enum StatusCiclo {
        ATIVO,
        ENCERRADO
    }
}