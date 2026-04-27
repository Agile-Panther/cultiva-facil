package br.edu.cesar.cultivafacil.domain.acesso.preferencias;

import br.edu.cesar.cultivafacil.domain.acesso.conta.ContaId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PreferenciasTest {

    private ContaId contaId;
    private Preferencias preferencias;

    @BeforeEach
    void setUp() {
        contaId = new ContaId(UUID.randomUUID());
        // Construtor de criação (seção 2.3 do modelo)
        preferencias = new Preferencias(contaId, new NomeConta("Maria da Silva"));
    }

    // ── US-04 · editarPerfil ───────────────────────────────────────────────

    @Test
    void editarPerfil_atualizaNomeEFoto() {
        // US-04 · positivo
        NomeConta novoNome = new NomeConta("Joao Silva");
        FotoPerfil foto = new FotoPerfil(new byte[2 * 1024 * 1024], FormatoFoto.PNG);

        preferencias.editarPerfil(novoNome, foto);

        assertEquals("Joao Silva", preferencias.getNome().getValor());
        assertNotNull(preferencias.getFotoPerfil());
        assertEquals(FormatoFoto.PNG, preferencias.getFotoPerfil().getFormato());
    }

    @Test
    void editarPerfil_permiteRemoverFoto() {
        FotoPerfil foto = new FotoPerfil(new byte[1024], FormatoFoto.JPG);
        preferencias.editarPerfil(new NomeConta("Com Foto"), foto);

        preferencias.editarPerfil(new NomeConta("Sem Foto"), null);

        assertNull(preferencias.getFotoPerfil());
    }

    @Test
    void editarPerfil_publicaEventoPerfilEditado() {
        preferencias.editarPerfil(new NomeConta("Novo Nome"), null);

        assertEquals(1, preferencias.eventosNaoPublicados().size());
        assertInstanceOf(Preferencias.PerfilEditado.class, preferencias.eventosNaoPublicados().get(0));
    }

    @Test
    void editarPerfil_eventoContemContaId() {
        preferencias.editarPerfil(new NomeConta("Editado"), null);

        Preferencias.PerfilEditado evento =
            (Preferencias.PerfilEditado) preferencias.eventosNaoPublicados().get(0);
        assertEquals(contaId, evento.getContaId());
    }

    @Test
    void editarPerfil_RN023_rejeitaNomeComUmCaracter() {
        // RN-023 protegido pelo VO NomeConta
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> new NomeConta("M")
        );
        assertEquals("NOME_INVALIDO", ex.getMessage());
    }

    @Test
    void editarPerfil_RN024a_rejeitaFormatoInvalido() {
        // RN-024a protegido por FormatoFoto.deExtensao()
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> FormatoFoto.deExtensao("GIF")
        );
        assertEquals("FOTO_FORMATO_INVALIDO", ex.getMessage());
    }

    @Test
    void editarPerfil_RN024b_rejeitaTamanhoAcimaDe5MB() {
        // RN-024b protegido pelo VO FotoPerfil
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> new FotoPerfil(new byte[6 * 1024 * 1024], FormatoFoto.PNG)
        );
        assertEquals("FOTO_TAMANHO_EXCEDIDO", ex.getMessage());
    }

    // ── US-05 · definirPreferencias ────────────────────────────────────────

    @Test
    void definirPreferencias_atualizaValores() {
        ValorArea area = new ValorArea(new BigDecimal("2.50"));
        HorarioResumo horario = new HorarioResumo(LocalTime.of(8, 0));

        preferencias.definirPreferencias(UnidadeArea.ALQUEIRE, area, horario);

        assertEquals(UnidadeArea.ALQUEIRE, preferencias.getUnidadeArea());
        assertEquals(0, new BigDecimal("2.50").compareTo(preferencias.getValorArea().getValor()));
        assertEquals(LocalTime.of(8, 0), preferencias.getHorarioResumo().getHorario());
    }

    @Test
    void definirPreferencias_publicaEvento() {
        preferencias.definirPreferencias(
            UnidadeArea.HECTARE,
            new ValorArea(new BigDecimal("1.00")),
            new HorarioResumo(LocalTime.of(7, 0))
        );

        assertEquals(1, preferencias.eventosNaoPublicados().size());
        assertInstanceOf(Preferencias.PreferenciasDefinidas.class,
            preferencias.eventosNaoPublicados().get(0));
    }

    @Test
    void definirPreferencias_eventoContemContaIdEPreferenciasId() {
        preferencias.definirPreferencias(
            UnidadeArea.METRO_QUADRADO,
            new ValorArea(new BigDecimal("100.00")),
            new HorarioResumo(LocalTime.of(6, 0))
        );

        Preferencias.PreferenciasDefinidas evento =
            (Preferencias.PreferenciasDefinidas) preferencias.eventosNaoPublicados().get(0);
        assertEquals(contaId, evento.getContaId());
        assertEquals(preferencias.getId(), evento.getPreferenciasId());
    }

    // ── US-06 · configurarNotificacoes ─────────────────────────────────────

    @Test
    void configurarNotificacoes_persisteTipos() {
        preferencias.configurarNotificacoes(
            Set.of(TipoNotificacao.TAREFA_ATRASADA),
            ContagemDiariaNotificacoes.zerada()
        );

        assertTrue(preferencias.getTiposNotificacao().contains(TipoNotificacao.TAREFA_ATRASADA));
    }

    @Test
    void configurarNotificacoes_publicaEvento() {
        preferencias.configurarNotificacoes(
            Set.of(TipoNotificacao.ALERTA_CRITICO),
            ContagemDiariaNotificacoes.zerada()
        );

        assertEquals(1, preferencias.eventosNaoPublicados().size());
        assertInstanceOf(Preferencias.NotificacoesConfiguradas.class,
            preferencias.eventosNaoPublicados().get(0));
    }

    @Test
    void configurarNotificacoes_eventoContemContaIdETipos() {
        Set<TipoNotificacao> tipos = Set.of(TipoNotificacao.RESUMO_DIARIO, TipoNotificacao.ALERTA_CRITICO);

        preferencias.configurarNotificacoes(tipos, ContagemDiariaNotificacoes.zerada());

        Preferencias.NotificacoesConfiguradas evento =
            (Preferencias.NotificacoesConfiguradas) preferencias.eventosNaoPublicados().get(0);
        assertEquals(contaId, evento.getContaId());
        assertEquals(tipos, evento.getTipos());
    }

    @Test
    void configurarNotificacoes_RN028_rejeitaLimiteDiarioExcedido() {
        // RN-028 · totalHoje >= 5 → LIMITE_NOTIFICACOES_EXCEDIDO
        Map<TipoNotificacao, Integer> mapa = new EnumMap<>(TipoNotificacao.class);
        mapa.put(TipoNotificacao.RESUMO_DIARIO, 5);
        mapa.put(TipoNotificacao.TAREFA_ATRASADA, 0);
        mapa.put(TipoNotificacao.ALERTA_CRITICO, 0);

        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> preferencias.configurarNotificacoes(
                Set.of(TipoNotificacao.RESUMO_DIARIO),
                new ContagemDiariaNotificacoes(mapa)
            )
        );
        assertEquals("LIMITE_NOTIFICACOES_EXCEDIDO", ex.getMessage());
    }

    @Test
    void configurarNotificacoes_RN028_alertaCriticoNaoTeLimite() {
        // ALERTA_CRITICO não entra na contagem do limite
        Map<TipoNotificacao, Integer> mapa = new EnumMap<>(TipoNotificacao.class);
        mapa.put(TipoNotificacao.RESUMO_DIARIO, 5);
        mapa.put(TipoNotificacao.TAREFA_ATRASADA, 5);
        mapa.put(TipoNotificacao.ALERTA_CRITICO, 0);

        assertDoesNotThrow(() ->
            preferencias.configurarNotificacoes(
                Set.of(TipoNotificacao.ALERTA_CRITICO),
                new ContagemDiariaNotificacoes(mapa)
            )
        );
    }

    @Test
    void configurarNotificacoes_RN028_permiteCom4TotalNosDiasLimitados() {
        Map<TipoNotificacao, Integer> mapa = new EnumMap<>(TipoNotificacao.class);
        mapa.put(TipoNotificacao.RESUMO_DIARIO, 3);
        mapa.put(TipoNotificacao.TAREFA_ATRASADA, 1);
        mapa.put(TipoNotificacao.ALERTA_CRITICO, 0);

        assertDoesNotThrow(() ->
            preferencias.configurarNotificacoes(
                Set.of(TipoNotificacao.RESUMO_DIARIO),
                new ContagemDiariaNotificacoes(mapa)
            )
        );
    }

    // ── Construtor de criação ─────────────────────────────────────────────

    @Test
    void construtorCriacao_referenciaContaId() {
        assertEquals(contaId, preferencias.getContaId());
    }

    @Test
    void construtorCriacao_nomeInicialPreservado() {
        assertEquals("Maria da Silva", preferencias.getNome().getValor());
    }

    @Test
    void construtorCriacao_rejeitaContaIdNulo() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Preferencias(null, new NomeConta("Teste"))
        );
    }

    @Test
    void construtorCriacao_rejeitaNomeNulo() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Preferencias(contaId, null)
        );
    }
}
