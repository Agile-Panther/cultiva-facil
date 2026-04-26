package br.edu.cesar.cultivafacil.domain.colheita.celeiro;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.CicloAgricolaId;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;

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
    private final List<Object> eventos;

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

    public void definirMeta(double valor) {
        if (!cicloAtivo) throw new IllegalArgumentException("CICLO_ENCERRADO");
        if (valor <= 0 || valor > item.getQuantidadePlantada())
            throw new IllegalArgumentException("META_COMERCIALIZAVEL_INVALIDA");
        this.meta = new MetaComerciavel(valor);
    }

    public void registrarPerda(double quantidade) {
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade de perda deve ser positiva");
        item.adicionarPerda(quantidade);
        verificarEDispararAlerta();
    }

    public void registrarSaida(double quantidade, MotivoSaida motivo) {
        Objects.requireNonNull(motivo, "Motivo não pode ser nulo");
        if (motivo == MotivoSaida.PERDA)
            throw new IllegalArgumentException("MOTIVO_SAIDA_INVALIDO");
        if (quantidade > item.getSaldoDisponivel())
            throw new IllegalArgumentException("SALDO_INSUFICIENTE");
        item.reduzirSaldo(quantidade);
        saidas.add(new SaidaCeleiro(quantidade, motivo));
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

    public RelatorioPerda gerarRelatorioPerda() {
        if (cicloAtivo) throw new IllegalArgumentException("RELATORIO_CICLO_ATIVO");
        return new RelatorioPerda(
                item.getQuantidadePlantada(),
                item.getPerdasAcumuladas(),
                item.getQuantidadePlantada(),
                item.getSaldoDisponivel()
        );
    }

    public double calcularProjecao() {
        return item.getQuantidadePlantada() - item.getPerdasAcumuladas();
    }

    private void verificarEDispararAlerta() {
        if (meta == null) return;
        if (calcularProjecao() >= meta.getValor()) return;
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
    public List<Object> getEventos() { return Collections.unmodifiableList(eventos); }

    public static class AlertaProjecao {
        public final CeleiroId celeiroId;
        public final TalhaoId talhaoId;
        public final double projecaoAtual;
        public final double metaComercializavel;
        public final LocalDateTime emitidoEm;

        public AlertaProjecao(CeleiroId celeiroId, TalhaoId talhaoId,
                              double projecaoAtual, double metaComercializavel,
                              LocalDateTime emitidoEm) {
            this.celeiroId = celeiroId;
            this.talhaoId = talhaoId;
            this.projecaoAtual = projecaoAtual;
            this.metaComercializavel = metaComercializavel;
            this.emitidoEm = emitidoEm;
        }
    }
}
