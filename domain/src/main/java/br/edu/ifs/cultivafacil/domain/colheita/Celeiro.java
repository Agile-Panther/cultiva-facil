package br.edu.ifs.cultivafacil.domain.colheita;


import org.apache.commons.lang3.Validate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class Celeiro {

    private CeleiroId id;
    private ZonaId zonaId;
    private final List<ItemCeleiro> itens;
    private final List<SaidaCeleiro> saidas;
    private MetaComerciavel metaComerciavel;
    private final List<ConfiguracaoRelatorio> configuracoes;
    // controle de cooldown 24h para AlertaProjecaoEvent (RN-04)
    private LocalDateTime ultimoAlertaProjecao;
    private final List<Object> eventos;

    public Celeiro(ZonaId zonaId) {
        Validate.notNull(zonaId, "ZonaId não pode ser nulo");
        this.id = new CeleiroId(UUID.randomUUID());
        this.zonaId = zonaId;
        this.itens = new ArrayList<>();
        this.saidas = new ArrayList<>();
        this.configuracoes = new ArrayList<>();
        this.eventos = new ArrayList<>();
    }

    public Celeiro(CeleiroId id,
                   ZonaId zonaId,
                   List<ItemCeleiro> itens,
                   List<SaidaCeleiro> saidas,
                   MetaComerciavel metaComerciavel,
                   List<ConfiguracaoRelatorio> configuracoes,
                   LocalDateTime ultimoAlertaProjecao) {
        Validate.notNull(id, "ID não pode ser nulo");
        Validate.notNull(zonaId, "ZonaId não pode ser nulo");
        this.id = id;
        this.zonaId = zonaId;
        this.itens = new ArrayList<>(itens != null ? itens : List.of());
        this.saidas = new ArrayList<>(saidas != null ? saidas : List.of());
        this.metaComerciavel = metaComerciavel;
        this.configuracoes = new ArrayList<>(configuracoes != null ? configuracoes : List.of());
        this.ultimoAlertaProjecao = ultimoAlertaProjecao;
        this.eventos = new ArrayList<>();
    }

    // RN-01: único ponto de entrada para inicialização da projeção
    public void inicializarProjecao(CicloAgricolaId cicloAgricolaId, BigDecimal quantidadePlantada) {
        Validate.notNull(cicloAgricolaId, "CicloAgricolaId não pode ser nulo");
        Validate.notNull(quantidadePlantada, "QuantidadePlantada não pode ser nula");
        Validate.isTrue(quantidadePlantada.compareTo(BigDecimal.ZERO) > 0, "AJUSTE_MANUAL_NAO_PERMITIDO");
        itens.add(new ItemCeleiro(cicloAgricolaId, zonaId, quantidadePlantada));
    }

    // RN-01 e RN-04: decrementa projeção e emite alerta se projeção < meta e cooldown expirou
    public void registrarPerda(CicloAgricolaId cicloAgricolaId, BigDecimal quantidade) {
        Validate.notNull(cicloAgricolaId, "CicloAgricolaId não pode ser nulo");
        Validate.notNull(quantidade, "Quantidade da perda não pode ser nula");
        ItemCeleiro item = encontrarItemAtivo(cicloAgricolaId);
        item.decrementarPorPerda(quantidade);
        verificarAlertaProjecao(item, cicloAgricolaId);
    }

    // RN-05: quantidade não pode superar saldo disponível
    public SaidaCeleiro registrarSaida(CicloAgricolaId cicloAgricolaId, BigDecimal quantidade, MotivoSaida motivo) {
        Validate.notNull(cicloAgricolaId, "CicloAgricolaId não pode ser nulo");
        Validate.notNull(quantidade, "Quantidade não pode ser nula");
        Validate.notNull(motivo, "MotivoSaida não pode ser nulo");
        ItemCeleiro item = encontrarItemAtivo(cicloAgricolaId);
        Validate.isTrue(quantidade.compareTo(item.getProjecaoAtual()) <= 0, "SALDO_INSUFICIENTE");
        SaidaCeleiro saida = new SaidaCeleiro(cicloAgricolaId, quantidade, motivo);
        saidas.add(saida);
        item.decrementarPorPerda(quantidade);
        return saida;
    }

    // RN-02 e RN-03: valor > 0 e <= plantado; ciclo deve estar ativo
    public void definirMeta(CicloAgricolaId cicloAgricolaId, BigDecimal valorMeta, boolean cicloAtivo) {
        ItemCeleiro item = encontrarItemAtivo(cicloAgricolaId);
        this.metaComerciavel = new MetaComerciavel(cicloAgricolaId, valorMeta, item.getQuantidadePlantada(), cicloAtivo);
    }

    // RN-09: máximo 5 configurações por Agricultor; duplicidade verificada no CeleiroServico
    public void salvarConfiguracaoRelatorio(AgricultorId agricultorId, String nome,
                                            FiltroPeriodo filtroPeriodo, long totalConfiguracoesSalvas) {
        Validate.isTrue(totalConfiguracoesSalvas < 5, "LIMITE_CONFIGURACOES_EXCEDIDO");
        configuracoes.add(new ConfiguracaoRelatorio(agricultorId, nome, filtroPeriodo));
    }

    private void verificarAlertaProjecao(ItemCeleiro item, CicloAgricolaId cicloAgricolaId) {
        if (metaComerciavel == null) return;
        if (item.getProjecaoAtual().compareTo(metaComerciavel.getValor()) >= 0) return;
        // RN-04: cooldown de 24h
        if (ultimoAlertaProjecao != null &&
                ultimoAlertaProjecao.isAfter(LocalDateTime.now().minusHours(24))) {
            throw new IllegalStateException("FREQUENCIA_ALERTA_EXCEDIDA");
        }
        ultimoAlertaProjecao = LocalDateTime.now();
        eventos.add(new AlertaProjecaoEvent(zonaId, cicloAgricolaId,
                item.getProjecaoAtual(), metaComerciavel.getValor(), ultimoAlertaProjecao));
    }

    public static class AlertaProjecaoEvent {
        private final ZonaId zonaId;
        private final CicloAgricolaId cicloAgricolaId;
        private final BigDecimal projecaoAtual;
        private final BigDecimal metaComerciavel;
        private final LocalDateTime emitidoEm;

        public AlertaProjecaoEvent(ZonaId zonaId, CicloAgricolaId cicloAgricolaId,
                                   BigDecimal projecaoAtual, BigDecimal metaComerciavel,
                                   LocalDateTime emitidoEm) {
            this.zonaId = zonaId;
            this.cicloAgricolaId = cicloAgricolaId;
            this.projecaoAtual = projecaoAtual;
            this.metaComerciavel = metaComerciavel;
            this.emitidoEm = emitidoEm;
        }

        public ZonaId getZonaId() { return zonaId; }
        public CicloAgricolaId getCicloAgricolaId() { return cicloAgricolaId; }
        public BigDecimal getProjecaoAtual() { return projecaoAtual; }
        public BigDecimal getMetaComerciavel() { return metaComerciavel; }
        public LocalDateTime getEmitidoEm() { return emitidoEm; }
    }

    private ItemCeleiro encontrarItemAtivo(CicloAgricolaId cicloAgricolaId) {
        return itens.stream()
                .filter(i -> i.getCicloAgricolaId().equals(cicloAgricolaId) && i.isCicloAtivo())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Item do ciclo não encontrado"));
    }

    public CeleiroId getId() { return id; }
    public ZonaId getZonaId() { return zonaId; }
    public List<ItemCeleiro> getItens() { return Collections.unmodifiableList(itens); }
    public List<SaidaCeleiro> getSaidas() { return Collections.unmodifiableList(saidas); }
    public MetaComerciavel getMetaComerciavel() { return metaComerciavel; }
    public List<ConfiguracaoRelatorio> getConfiguracoes() { return Collections.unmodifiableList(configuracoes); }
    public List<Object> getEventos() { return Collections.unmodifiableList(eventos); }
    public void limparEventos() { eventos.clear(); }
}
