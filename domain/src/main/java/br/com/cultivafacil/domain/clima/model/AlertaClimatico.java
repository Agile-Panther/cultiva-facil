package br.com.cultivafacil.domain.clima.model;

import br.com.cultivafacil.domain.clima.event.AlertaClimaticoGerado;
import br.com.cultivafacil.domain.clima.vo.AlertaClimaticoId;
import br.com.cultivafacil.domain.clima.vo.LimiteClimaticoId;
import br.com.cultivafacil.domain.clima.vo.TipoAlerta;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AlertaClimatico {

    private AlertaClimaticoId id;
    private LimiteClimaticoId limiteClimaticoId;
    private TipoAlerta tipoAlerta;
    private String mensagem;
    private LocalDateTime dataHora;
    private List<Object> domainEvents = new ArrayList<>();

    // Construtor para criação
    public AlertaClimatico(LimiteClimaticoId limiteClimaticoId, TipoAlerta tipoAlerta, String mensagem) {
        this.id = AlertaClimaticoId.novo();
        this.limiteClimaticoId = limiteClimaticoId;
        this.tipoAlerta = tipoAlerta;
        this.mensagem = mensagem;
        this.dataHora = LocalDateTime.now();
        this.addDomainEvent(new AlertaClimaticoGerado(this.id));
    }

    // Construtor para reconstituição
    public AlertaClimatico(AlertaClimaticoId id, LimiteClimaticoId limiteClimaticoId, TipoAlerta tipoAlerta, String mensagem, LocalDateTime dataHora) {
        this.id = id;
        this.limiteClimaticoId = limiteClimaticoId;
        this.tipoAlerta = tipoAlerta;
        this.mensagem = mensagem;
        this.dataHora = dataHora;
    }

    public AlertaClimaticoId getId() {
        return id;
    }

    public LimiteClimaticoId getLimiteClimaticoId() {
        return limiteClimaticoId;
    }

    public TipoAlerta getTipoAlerta() {
        return tipoAlerta;
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

