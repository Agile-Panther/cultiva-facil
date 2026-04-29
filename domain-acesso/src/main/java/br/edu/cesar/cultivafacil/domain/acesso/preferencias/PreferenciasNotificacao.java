package br.edu.cesar.cultivafacil.domain.acesso.preferencias;

import br.edu.cesar.cultivafacil.domain.acesso.conta.ContaId;
import br.edu.cesar.cultivafacil.domain.acesso.preferencias.events.NotificacoesConfiguradas;
import br.edu.cesar.cultivafacil.domain.acesso.preferencias.events.PreferenciasAtualizadas;
import br.edu.cesar.cultivafacil.domain.evento.EventoBarramento;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class PreferenciasNotificacao {

    private final PreferenciasId id;
    private final ContaId contaId;
    private final PoliticaNotificacaoDiaria politica;
    private final EventoBarramento barramento;

    public PreferenciasNotificacao(ContaId contaId, EventoBarramento barramento) {
        Objects.requireNonNull(contaId, "ContaId nao pode ser nulo");
        Objects.requireNonNull(barramento, "EventoBarramento nao pode ser nulo");
        this.id = PreferenciasId.novo();
        this.contaId = contaId;
        this.politica = new PoliticaNotificacaoDiaria();
        this.barramento = barramento;
    }

    public void definirPreferencias(HorarioResumoDiario horario, UnidadeArea unidade) {
        Objects.requireNonNull(horario, "HorarioResumoDiario nao pode ser nulo");
        Objects.requireNonNull(unidade, "UnidadeArea nao pode ser nula");
        politica.definir(horario, unidade);
        barramento.publicar(new PreferenciasAtualizadas(contaId, id));
    }

    public void configurarNotificacoes(Set<TipoNotificacao> tipos, int notificacoesHoje) {
        Objects.requireNonNull(tipos, "Tipos nao podem ser nulos");
        boolean temNaoCritico = tipos.stream()
            .anyMatch(t -> t != TipoNotificacao.ALERTA_CRITICO);
        if (temNaoCritico && LimiteNotificacaoDiaria.excedido(notificacoesHoje)) {
            throw new IllegalStateException("LIMITE_DIARIO_EXCEDIDO");
        }
        politica.configurarTipos(new HashSet<>(tipos));
        barramento.publicar(new NotificacoesConfiguradas(contaId, new HashSet<>(tipos)));
    }

    public PreferenciasId getId() {
        return id;
    }

    public ContaId getContaId() {
        return contaId;
    }

    public PoliticaNotificacaoDiaria getPolitica() {
        return politica;
    }
}
