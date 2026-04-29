package br.edu.cesar.cultivafacil.domain.acesso.preferencias;

import br.edu.cesar.cultivafacil.domain.acesso.conta.ContaId;
import br.edu.cesar.cultivafacil.domain.acesso.preferencias.events.NotificacoesConfiguradas;
import br.edu.cesar.cultivafacil.domain.acesso.preferencias.events.PreferenciasAtualizadas;
import br.edu.cesar.cultivafacil.domain.evento.EventoBarramento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PreferenciasTest {

    private EventoBarramento barramento;
    private ContaId contaId;
    private PreferenciasNotificacao preferencias;

    @BeforeEach
    void setUp() {
        barramento = new EventoBarramento();
        contaId = ContaId.novo();
        preferencias = new PreferenciasNotificacao(contaId, barramento);
    }

    // =========================================================
    // Construtor
    // =========================================================

    @Test
    void construtorCriacao_rejeitaContaIdNulo() {
        assertThrows(NullPointerException.class,
            () -> new PreferenciasNotificacao(null, barramento));
    }

    @Test
    void construtorCriacao_referenciaContaId() {
        assertEquals(contaId, preferencias.getContaId());
    }

    @Test
    void construtorCriacao_geraIdUnico() {
        PreferenciasNotificacao outra = new PreferenciasNotificacao(ContaId.novo(), barramento);
        assertNotEquals(preferencias.getId(), outra.getId());
    }

    // =========================================================
    // definirPreferencias — US-07 (RN-025, RN-026)
    // =========================================================

    @Test
    void definirPreferencias_atualizaValores() {
        HorarioResumoDiario horario = new HorarioResumoDiario(LocalTime.of(7, 0));
        preferencias.definirPreferencias(horario, UnidadeArea.HECTARES);

        assertEquals(horario, preferencias.getPolitica().getHorario());
        assertEquals(UnidadeArea.HECTARES, preferencias.getPolitica().getUnidade());
    }

    @Test
    void definirPreferencias_publicaEvento() {
        List<PreferenciasAtualizadas> eventos = new ArrayList<>();
        barramento.inscrever((PreferenciasAtualizadas e) -> eventos.add(e));

        preferencias.definirPreferencias(
            new HorarioResumoDiario(LocalTime.of(7, 0)), UnidadeArea.HECTARES);

        assertEquals(1, eventos.size());
    }

    @Test
    void definirPreferencias_eventoContemContaIdEPreferenciasId() {
        List<PreferenciasAtualizadas> eventos = new ArrayList<>();
        barramento.inscrever((PreferenciasAtualizadas e) -> eventos.add(e));

        preferencias.definirPreferencias(
            new HorarioResumoDiario(LocalTime.of(7, 0)), UnidadeArea.HECTARES);

        PreferenciasAtualizadas evento = eventos.get(0);
        assertEquals(contaId, evento.getContaId());
        assertEquals(preferencias.getId(), evento.getPreferenciasId());
    }

    @Test
    void definirPreferencias_RN025_rejeitaHorarioForaDoIntervalo() {
        assertThrows(IllegalArgumentException.class,
            () -> preferencias.definirPreferencias(
                new HorarioResumoDiario(LocalTime.of(4, 0)), UnidadeArea.HECTARES));
    }

    @Test
    void definirPreferencias_RN026_aceitaUnidadeComDuasCasasDecimais() {
        assertDoesNotThrow(() -> preferencias.definirPreferencias(
            new HorarioResumoDiario(LocalTime.of(7, 0)), UnidadeArea.METROS_QUADRADOS));
    }

    // =========================================================
    // configurarNotificacoes — US-08 (RN-027, RN-028)
    // =========================================================

    @Test
    void configurarNotificacoes_publicaEvento() {
        List<NotificacoesConfiguradas> eventos = new ArrayList<>();
        barramento.inscrever((NotificacoesConfiguradas e) -> eventos.add(e));

        preferencias.configurarNotificacoes(EnumSet.of(TipoNotificacao.RESUMO_DIARIO), 0);

        assertEquals(1, eventos.size());
    }

    @Test
    void configurarNotificacoes_eventoContemContaIdETipos() {
        List<NotificacoesConfiguradas> eventos = new ArrayList<>();
        barramento.inscrever((NotificacoesConfiguradas e) -> eventos.add(e));
        Set<TipoNotificacao> tipos = EnumSet.of(TipoNotificacao.RESUMO_DIARIO, TipoNotificacao.ALERTA_CRITICO);

        preferencias.configurarNotificacoes(tipos, 0);

        NotificacoesConfiguradas evento = eventos.get(0);
        assertEquals(contaId, evento.getContaId());
        assertTrue(evento.getTipos().containsAll(tipos));
    }

    @Test
    void configurarNotificacoes_persisteTipos() {
        Set<TipoNotificacao> tipos = EnumSet.of(TipoNotificacao.TAREFA_ATRASADA);
        preferencias.configurarNotificacoes(tipos, 0);

        assertTrue(preferencias.getPolitica().getTipos().contains(TipoNotificacao.TAREFA_ATRASADA));
    }

    @Test
    void configurarNotificacoes_RN028_rejeitaLimiteDiarioExcedido() {
        assertThrows(IllegalStateException.class,
            () -> preferencias.configurarNotificacoes(
                EnumSet.of(TipoNotificacao.RESUMO_DIARIO), 5));
    }

    @Test
    void configurarNotificacoes_RN028_rejeitaTarefaAtrasadaComLimiteExcedido() {
        assertThrows(IllegalStateException.class,
            () -> preferencias.configurarNotificacoes(
                EnumSet.of(TipoNotificacao.TAREFA_ATRASADA), 5));
    }

    @Test
    void configurarNotificacoes_RN028_alertaCriticoNaoTemLimite() {
        assertDoesNotThrow(() ->
            preferencias.configurarNotificacoes(
                EnumSet.of(TipoNotificacao.ALERTA_CRITICO), 10));
    }

    @Test
    void configurarNotificacoes_RN028_permiteCom4TotalNosDiasLimitados() {
        assertDoesNotThrow(() ->
            preferencias.configurarNotificacoes(
                EnumSet.of(TipoNotificacao.RESUMO_DIARIO, TipoNotificacao.TAREFA_ATRASADA), 4));
    }
}
