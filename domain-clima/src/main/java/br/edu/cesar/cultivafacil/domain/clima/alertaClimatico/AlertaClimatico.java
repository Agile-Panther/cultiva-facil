package br.edu.cesar.cultivafacil.domain.clima.alertaClimatico;

import br.edu.cesar.cultivafacil.domain.clima.alertaClimatico.AlertaClimaticoGerado;
import br.edu.cesar.cultivafacil.domain.clima.alertaClimatico.AlertaClimaticoId;
import br.edu.cesar.cultivafacil.domain.clima.limite.LimiteClimaticoId;
import br.edu.cesar.cultivafacil.domain.clima.alertaClimatico.TipoAlerta;

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

