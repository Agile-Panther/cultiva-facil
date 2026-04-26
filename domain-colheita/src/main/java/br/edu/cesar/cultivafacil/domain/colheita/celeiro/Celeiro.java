package br.edu.cesar.cultivafacil.domain.colheita.celeiro;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.CicloAgricolaId;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Celeiro {

    private static final int LIMITE_CONFIGURACOES = 5;
    private static final int INTERVALO_ALERTA_HORAS = 24;

    private final CeleiroId id;
    private final TalhaoId talhaoId;
    private final CicloAgricolaId cicloAgricolaId;
    private boolean cicloAtivo;
    private final ItemCeleiro item;
    private MetaComerciavel meta;
    private final List<SaidaCeleiro> saidas;
    private final List<ConfiguracaoRelatorio> configuracoes;
    private LocalDateTime ultimoAlertaEmitidoEm;
    private final List<EventoDominio> eventos;

    public Celeiro(TalhaoId talhaoId, CicloAgricolaId cicloAgricolaId, ItemCeleiro item) {
        Objects.requireNonNull(talhaoId, "TalhaoId não pode ser nulo");
        Objects.requireNonNull(cicloAgricolaId, "CicloAgricolaId não pode ser nulo");
        Objects.requireNonNull(item, "ItemCeleiro não pode ser nulo");
        this.id = CeleiroId.novo();
        this.talhaoId = talhaoId;
        this.cicloAgricolaId = cicloAgricolaId;
        this.cicloAtivo = true;
        this.item = item;
        this.meta = null;
        this.saidas = new ArrayList<>();
        this.configuracoes = new ArrayList<>();
        this.ultimoAlertaEmitidoEm = null;
        this.eventos = new ArrayList<>();
    }

    public Celeiro(CeleiroId id, TalhaoId talhaoId, CicloAgricolaId cicloAgricolaId,
                   boolean cicloAtivo, ItemCeleiro item, MetaComerciavel meta,
                   List<SaidaCeleiro> saidas, List<ConfiguracaoRelatorio> configuracoes,
                   LocalDateTime ultimoAlertaEmitidoEm) {
        this.id = id;
        this.talhaoId = talhaoId;
        this.cicloAgricolaId = cicloAgricolaId;
        this.cicloAtivo = cicloAtivo;
        this.item = item;
        this.meta = meta;
        this.saidas = new ArrayList<>(saidas);
        this.configuracoes = new ArrayList<>(configuracoes);
        this.ultimoAlertaEmitidoEm = ultimoAlertaEmitidoEm;
        this.eventos = new ArrayList<>();
    }

    public void definirMeta(BigDecimal valor) {
        Objects.requireNonNull(valor, "Valor não pode ser nulo");
        if (!cicloAtivo) throw new IllegalArgumentException("CICLO_ENCERRADO");
        if (valor.compareTo(BigDecimal.ZERO) <= 0 || valor.compareTo(item.getQuantidadePlantada()) > 0)
            throw new IllegalArgumentException("META_COMERCIALIZAVEL_INVALIDA");
        this.meta = new MetaComerciavel(valor);
    }

    public void registrarPerda(BigDecimal quantidade) {
        Objects.requireNonNull(quantidade, "Quantidade não pode ser nula");
        if (quantidade.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Quantidade de perda deve ser positiva");
        item.adicionarPerda(quantidade);
        verificarEDispararAlerta();
    }

    public void registrarSaida(BigDecimal quantidade, MotivoSaida motivo) {
        Objects.requireNonNull(motivo, "Motivo não pode ser nulo");
        if (motivo == MotivoSaida.PERDA)
            throw new IllegalArgumentException("MOTIVO_SAIDA_INVALIDO");
        item.registrarSaida(quantidade);
        saidas.add(new SaidaCeleiro(quantidade, motivo, LocalDateTime.now()));
    }

    public void encerrarCiclo() {
        this.cicloAtivo = false;
    }

    public void adicionarConfiguracao(String nome, FiltroPeriodo periodo) {
        Objects.requireNonNull(periodo, "Período não pode ser nulo");
        if (nome == null || nome.trim().length() < 2)
            throw new IllegalArgumentException("NOME_CONFIG_INVALIDO");
        if (configuracoes.stream().anyMatch(c -> c.getNome().equals(nome.trim())))
            throw new IllegalArgumentException("NOME_CONFIG_DUPLICADO");
        if (configuracoes.size() >= LIMITE_CONFIGURACOES)
            throw new IllegalArgumentException("LIMITE_CONFIGURACOES_EXCEDIDO");
        configuracoes.add(new ConfiguracaoRelatorio(nome.trim(), periodo));
    }

    public ConfiguracaoRelatorio buscarConfiguracao(String nome) {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("CONFIGURACAO_INEXISTENTE");
        return configuracoes.stream()
                .filter(c -> c.getNome().equals(nome.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("CONFIGURACAO_INEXISTENTE"));
    }

    public RelatorioPerda gerarRelatorioPerda() {
        if (cicloAtivo) throw new IllegalArgumentException("RELATORIO_CICLO_ATIVO");
        return new RelatorioPerda(
                item.getQuantidadePlantada(),
                item.getPerdasAcumuladas(),
                item.getQuantidadePlantada(),
                item.getSaldoDisponivel()
        );
    }

    public BigDecimal calcularProjecao() {
        return item.calcularProjecao();
    }

    private void verificarEDispararAlerta() {
        if (meta == null) return;
        if (!meta.projecaoAbaixoDaMeta(item.calcularProjecao())) return;
        if (ultimoAlertaEmitidoEm != null &&
                LocalDateTime.now().isBefore(ultimoAlertaEmitidoEm.plusHours(INTERVALO_ALERTA_HORAS))) {
            throw new IllegalArgumentException("FREQUENCIA_ALERTA_EXCEDIDA");
        }
        ultimoAlertaEmitidoEm = LocalDateTime.now();
        eventos.add(new AlertaProjecao(id, talhaoId, calcularProjecao(), meta.getValor(), ultimoAlertaEmitidoEm));
    }

    public CeleiroId getId() { return id; }
    public TalhaoId getTalhaoId() { return talhaoId; }
    public CicloAgricolaId getCicloAgricolaId() { return cicloAgricolaId; }
    public boolean isCicloAtivo() { return cicloAtivo; }
    public ItemCeleiro getItem() { return item; }
    public MetaComerciavel getMeta() { return meta; }
    public List<SaidaCeleiro> getSaidas() { return Collections.unmodifiableList(saidas); }
    public List<ConfiguracaoRelatorio> getConfiguracoes() { return Collections.unmodifiableList(configuracoes); }
    public LocalDateTime getUltimoAlertaEmitidoEm() { return ultimoAlertaEmitidoEm; }
    public List<EventoDominio> getEventos() { return Collections.unmodifiableList(eventos); }

    public static class AlertaProjecao implements EventoDominio {
        private final CeleiroId celeiroId;
        private final TalhaoId talhaoId;
        private final BigDecimal projecaoAtual;
        private final BigDecimal metaComercializavel;
        private final LocalDateTime emitidoEm;

        public AlertaProjecao(CeleiroId celeiroId, TalhaoId talhaoId,
                              BigDecimal projecaoAtual, BigDecimal metaComercializavel,
                              LocalDateTime emitidoEm) {
            this.celeiroId = celeiroId;
            this.talhaoId = talhaoId;
            this.projecaoAtual = projecaoAtual;
            this.metaComercializavel = metaComercializavel;
            this.emitidoEm = emitidoEm;
        }

        public CeleiroId getCeleiroId() { return celeiroId; }
        public TalhaoId getTalhaoId() { return talhaoId; }
        public BigDecimal getProjecaoAtual() { return projecaoAtual; }
        public BigDecimal getMetaComercializavel() { return metaComercializavel; }
        public LocalDateTime getEmitidoEm() { return emitidoEm; }
    }
}
