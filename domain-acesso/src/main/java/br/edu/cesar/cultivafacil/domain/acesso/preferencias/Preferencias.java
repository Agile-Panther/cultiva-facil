package br.edu.cesar.cultivafacil.domain.acesso.preferencias;

import br.edu.cesar.cultivafacil.domain.acesso.conta.ContaId;
import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Preferencias {

    private final PreferenciasId id;
    private final ContaId contaId;
    private NomeConta nome;
    private FotoPerfil fotoPerfil;
    private UnidadeArea unidadeArea;
    private ValorArea valorArea;
    private HorarioResumo horarioResumo;
    private final ConfiguracaoNotificacao configuracaoNotificacao;
    private final List<Object> eventosNaoPublicados;

    // ── Construtor de criação — ID gerado internamente (seção 2.3 do modelo) ──

    public Preferencias(ContaId contaId, NomeConta nome) {
        Validate.isTrue(contaId != null, "ContaId nao pode ser nulo");
        Validate.isTrue(nome != null, "NomeConta nao pode ser nulo");
        this.id = PreferenciasId.novo();
        this.contaId = contaId;
        this.nome = nome;
        this.fotoPerfil = null;
        this.unidadeArea = UnidadeArea.HECTARE;
        this.valorArea = new ValorArea(new BigDecimal("1.00"));
        this.horarioResumo = new HorarioResumo(LocalTime.of(7, 0));
        this.configuracaoNotificacao = new ConfiguracaoNotificacao();
        this.eventosNaoPublicados = new ArrayList<>();
    }

    // ── Construtor de reconstituição — ID fornecido (persistência) ──────────

    public Preferencias(PreferenciasId id, ContaId contaId, NomeConta nome, FotoPerfil fotoPerfil,
                        UnidadeArea unidadeArea, ValorArea valorArea, HorarioResumo horarioResumo) {
        Validate.isTrue(id != null, "PreferenciasId nao pode ser nulo");
        Validate.isTrue(contaId != null, "ContaId nao pode ser nulo");
        Validate.isTrue(nome != null, "NomeConta nao pode ser nulo");
        Validate.isTrue(unidadeArea != null, "UnidadeArea nao pode ser nula");
        Validate.isTrue(valorArea != null, "ValorArea nao pode ser nula");
        Validate.isTrue(horarioResumo != null, "HorarioResumo nao pode ser nulo");
        this.id = id;
        this.contaId = contaId;
        this.nome = nome;
        this.fotoPerfil = fotoPerfil;
        this.unidadeArea = unidadeArea;
        this.valorArea = valorArea;
        this.horarioResumo = horarioResumo;
        this.configuracaoNotificacao = new ConfiguracaoNotificacao();
        this.eventosNaoPublicados = new ArrayList<>();
    }

    // ── US-04 · Editar perfil ─────────────────────────────────────────────

    public void editarPerfil(NomeConta novoNome, FotoPerfil novaFoto) {
        Validate.isTrue(novoNome != null, "NomeConta nao pode ser nulo");
        this.nome = novoNome;
        this.fotoPerfil = novaFoto;
        eventosNaoPublicados.add(new PerfilEditado(this.contaId));
    }

    // ── US-05 · Preferências de exibição ─────────────────────────────────

    public void definirPreferencias(UnidadeArea unidade, ValorArea area, HorarioResumo horario) {
        Validate.isTrue(unidade != null, "UnidadeArea nao pode ser nula");
        Validate.isTrue(area != null, "ValorArea nao pode ser nula");
        Validate.isTrue(horario != null, "HorarioResumo nao pode ser nulo");
        this.unidadeArea = unidade;
        this.valorArea = area;
        this.horarioResumo = horario;
        eventosNaoPublicados.add(new PreferenciasDefinidas(this.contaId, this.id));
    }

    // ── US-06 · Configurar notificações ──────────────────────────────────

    public void configurarNotificacoes(Set<TipoNotificacao> tipos, ContagemDiariaNotificacoes contagemHoje) {
        Validate.isTrue(tipos != null && !tipos.isEmpty(),
            "Tipos de notificacao nao podem ser nulos ou vazios");
        boolean temTipoLimitado = tipos.stream().anyMatch(
            t -> t == TipoNotificacao.RESUMO_DIARIO || t == TipoNotificacao.TAREFA_ATRASADA
        );
        if (temTipoLimitado) {
            int totalHoje = contagemHoje.obter(TipoNotificacao.RESUMO_DIARIO)
                          + contagemHoje.obter(TipoNotificacao.TAREFA_ATRASADA);
            Validate.isTrue(totalHoje < 5, "LIMITE_NOTIFICACOES_EXCEDIDO");
        }
        configuracaoNotificacao.definir(tipos);
        eventosNaoPublicados.add(new NotificacoesConfiguradas(this.contaId, Set.copyOf(tipos)));
    }

    // ── Consultas ──────────────────────────────────────────────────────────

    public List<Object> eventosNaoPublicados() {
        return Collections.unmodifiableList(eventosNaoPublicados);
    }

    public PreferenciasId getId()          { return id; }
    public ContaId getContaId()            { return contaId; }
    public NomeConta getNome()             { return nome; }
    public FotoPerfil getFotoPerfil()      { return fotoPerfil; }
    public UnidadeArea getUnidadeArea()    { return unidadeArea; }
    public ValorArea getValorArea()        { return valorArea; }
    public HorarioResumo getHorarioResumo() { return horarioResumo; }
    public Set<TipoNotificacao> getTiposNotificacao() {
        return configuracaoNotificacao.getTipos();
    }

    // ── Domain Events ──────────────────────────────────────────────────────

    public static final class PerfilEditado {
        private final ContaId contaId;

        public PerfilEditado(ContaId contaId) {
            this.contaId = contaId;
        }

        public ContaId getContaId() { return contaId; }
    }

    public static final class PreferenciasDefinidas {
        private final ContaId contaId;
        private final PreferenciasId preferenciasId;

        public PreferenciasDefinidas(ContaId contaId, PreferenciasId preferenciasId) {
            this.contaId = contaId;
            this.preferenciasId = preferenciasId;
        }

        public ContaId getContaId()               { return contaId; }
        public PreferenciasId getPreferenciasId() { return preferenciasId; }
    }

    public static final class NotificacoesConfiguradas {
        private final ContaId contaId;
        private final Set<TipoNotificacao> tipos;

        public NotificacoesConfiguradas(ContaId contaId, Set<TipoNotificacao> tipos) {
            this.contaId = contaId;
            this.tipos = Set.copyOf(tipos);
        }

        public ContaId getContaId()          { return contaId; }
        public Set<TipoNotificacao> getTipos() { return tipos; }
    }
}
