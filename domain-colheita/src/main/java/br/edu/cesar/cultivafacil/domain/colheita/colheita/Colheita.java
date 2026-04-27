package br.edu.cesar.cultivafacil.domain.colheita.colheita;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import br.edu.cesar.cultivafacil.domain.shared.ZonaId;
import br.edu.cesar.cultivafacil.domain.shared.CicloAgricolaId;

public class Colheita {
    private final ColheitaId id;
    private final ZonaId zonaId;
    private final CicloAgricolaId cicloAgricolaId;
    private QuantidadeColhida quantidade;
    private UnidadeMedida unidadeMedida;
    private DestinoColheita destino;
    private final LocalDateTime dataRegistro;
    private boolean encerrada;

    // Domain Event — Classe estática interna
    public static class ColheitaRegistrada {
        public final ColheitaId colheitaId;
        public final ZonaId zonaId;
        public final CicloAgricolaId cicloAgricolaId;
        public final BigDecimal quantidade;
        public final UnidadeMedida unidadeMedida;
        public final DestinoColheita destino;

        public ColheitaRegistrada(ColheitaId colheitaId, ZonaId zonaId, CicloAgricolaId cicloAgricolaId, BigDecimal quantidade, UnidadeMedida unidadeMedida, DestinoColheita destino) {
            this.colheitaId = colheitaId;
            this.zonaId = zonaId;
            this.cicloAgricolaId = cicloAgricolaId;
            this.quantidade = quantidade;
            this.unidadeMedida = unidadeMedida;
            this.destino = destino;
        }
    }

    // Construtor de registro de nova colheita
    public Colheita(ZonaId zonaId, CicloAgricolaId cicloAgricolaId, BigDecimal quantidade, BigDecimal maxProjecao, UnidadeMedida unidadeMedida, UnidadeMedida unidadeCultivo, DestinoColheita destino, boolean zonaProntaParaColheita) {
        if (!zonaProntaParaColheita) throw new IllegalStateException("RN-081: Zona não está pronta para colheita");
        if (unidadeMedida != unidadeCultivo) throw new IllegalArgumentException("RN-084: Unidade de medida não coincide com a do cultivo ativo");
        Objects.requireNonNull(destino, "Destino obrigatório");
        this.id = ColheitaId.novo();
        this.zonaId = Objects.requireNonNull(zonaId);
        this.cicloAgricolaId = Objects.requireNonNull(cicloAgricolaId);
        this.quantidade = new QuantidadeColhida(quantidade, maxProjecao); // RN-082
        this.unidadeMedida = unidadeMedida;
        this.destino = destino;
        this.dataRegistro = LocalDateTime.now();
        this.encerrada = false;
    }

    // Construtor de reconstituição
    public Colheita(ColheitaId id, ZonaId zonaId, CicloAgricolaId cicloAgricolaId, QuantidadeColhida quantidade, UnidadeMedida unidadeMedida, DestinoColheita destino, LocalDateTime dataRegistro, boolean encerrada) {
        this.id = id;
        this.zonaId = zonaId;
        this.cicloAgricolaId = cicloAgricolaId;
        this.quantidade = quantidade;
        this.unidadeMedida = unidadeMedida;
        this.destino = destino;
        this.dataRegistro = dataRegistro;
        this.encerrada = encerrada;
    }

    public void corrigir(BigDecimal novaQuantidade, BigDecimal maxProjecao, UnidadeMedida novaUnidade, UnidadeMedida unidadeCultivo, DestinoColheita novoDestino, boolean cicloEncerrado) {
        if (cicloEncerrado) throw new IllegalStateException("RN-086: Não é possível corrigir após encerramento do ciclo");
        if (novaUnidade != unidadeCultivo) throw new IllegalArgumentException("RN-084: Unidade de medida não coincide com a do cultivo ativo");
        this.quantidade = new QuantidadeColhida(novaQuantidade, maxProjecao); // RN-087
        this.unidadeMedida = novaUnidade;
        this.destino = novoDestino;
    }

    public void encerrarCiclo() {
        this.encerrada = true;
        // Aqui acionaria a consolidação do Relatório de Perdas
    }

    public ColheitaId getId() { return id; }
    public ZonaId getZonaId() { return zonaId; }
    public CicloAgricolaId getCicloAgricolaId() { return cicloAgricolaId; }
    public QuantidadeColhida getQuantidade() { return quantidade; }
    public UnidadeMedida getUnidadeMedida() { return unidadeMedida; }
    public DestinoColheita getDestino() { return destino; }
    public LocalDateTime getDataRegistro() { return dataRegistro; }
    public boolean isEncerrada() { return encerrada; }
}
