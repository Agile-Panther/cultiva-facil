package br.edu.cesar.cultivafacil.domain.clima.limite;

import br.edu.cesar.cultivafacil.domain.clima.limite.LimiteClimaticoDefinido;
import br.edu.cesar.cultivafacil.domain.clima.limite.JanelaObservacao;
import br.edu.cesar.cultivafacil.domain.clima.limite.LimiteClimaticoId;
import br.edu.cesar.cultivafacil.domain.clima.limite.PrecipitacaoLimite;
import br.edu.cesar.cultivafacil.domain.clima.limite.TemperaturaLimite;

import java.util.ArrayList;
import java.util.List;

public class LimiteClimatico {

    private LimiteClimaticoId id;
    private TemperaturaLimite temperatura;
    private PrecipitacaoLimite precipitacao;
    private JanelaObservacao janelaObservacao;
    private List<Object> domainEvents = new ArrayList<>();


    // Construtor para criação
    public LimiteClimatico(TemperaturaLimite temperatura, PrecipitacaoLimite precipitacao, JanelaObservacao janelaObservacao) {
        this.id = LimiteClimaticoId.novo();
        this.temperatura = temperatura;
        this.precipitacao = precipitacao;
        this.janelaObservacao = janelaObservacao;
        this.addDomainEvent(new LimiteClimaticoDefinido(this.id));
    }

    // Construtor para reconstituição
    public LimiteClimatico(LimiteClimaticoId id, TemperaturaLimite temperatura, PrecipitacaoLimite precipitacao, JanelaObservacao janelaObservacao) {
        this.id = id;
        this.temperatura = temperatura;
        this.precipitacao = precipitacao;
        this.janelaObservacao = janelaObservacao;
    }

    public LimiteClimaticoId getId() {
        return id;
    }

    public TemperaturaLimite getTemperatura() {
        return temperatura;
    }

    public PrecipitacaoLimite getPrecipitacao() {
        return precipitacao;
    }

    public JanelaObservacao getJanelaObservacao() {
        return janelaObservacao;
    }

    public List<Object> getDomainEvents() {
        return domainEvents;
    }

    private void addDomainEvent(Object event) {
        this.domainEvents.add(event);
    }
}

