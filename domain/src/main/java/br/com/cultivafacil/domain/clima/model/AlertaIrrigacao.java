package br.com.cultivafacil.domain.clima.model;

import br.com.cultivafacil.domain.clima.event.AlertaIrrigacaoGerado;
import br.com.cultivafacil.domain.clima.vo.AlertaIrrigacaoId;
import br.com.cultivafacil.domain.clima.vo.NecessidadeHidrica;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AlertaIrrigacao {

    private AlertaIrrigacaoId id;
    private NecessidadeHidrica necessidadeHidrica;
    private String mensagem;
    private LocalDateTime dataHora;
    private List<Object> domainEvents = new ArrayList<>();

    // Construtor para criação
    public AlertaIrrigacao(NecessidadeHidrica necessidadeHidrica, String mensagem) {
        this.id = AlertaIrrigacaoId.novo();
        this.necessidadeHidrica = necessidadeHidrica;
        this.mensagem = mensagem;
        this.dataHora = LocalDateTime.now();
        this.addDomainEvent(new AlertaIrrigacaoGerado(this.id));
    }

    // Construtor para reconstituição
    public AlertaIrrigacao(AlertaIrrigacaoId id, NecessidadeHidrica necessidadeHidrica, String mensagem, LocalDateTime dataHora) {
        this.id = id;
        this.necessidadeHidrica = necessidadeHidrica;
        this.mensagem = mensagem;
        this.dataHora = dataHora;
    }

    public AlertaIrrigacaoId getId() {
        return id;
    }

    public NecessidadeHidrica getNecessidadeHidrica() {
        return necessidadeHidrica;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public List<Object> getDomainEvents() {
        return domainEvents;
    }

    private void addDomainEvent(Object event) {
        this.domainEvents.add(event);
    }
}

