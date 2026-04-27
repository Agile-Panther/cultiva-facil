package br.edu.cesar.cultivafacil.domain.insumo.plano;

import java.time.LocalDateTime;
import java.util.UUID;

public class ItemConsumo {

    private final UUID id;
    private final TipoInsumo tipo;
    private final QuantidadeInsumo quantidade;
    private final UnidadeMedidaInsumo unidade;
    private final LocalDateTime dataConsumo;

    ItemConsumo(TipoInsumo tipo, QuantidadeInsumo quantidade, UnidadeMedidaInsumo unidade) {
        this.id = UUID.randomUUID();
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.unidade = unidade;
        this.dataConsumo = LocalDateTime.now();
    }

    ItemConsumo(UUID id, TipoInsumo tipo, QuantidadeInsumo quantidade,
                UnidadeMedidaInsumo unidade, LocalDateTime dataConsumo) {
        this.id = id;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.unidade = unidade;
        this.dataConsumo = dataConsumo;
    }

    public UUID getId() { return id; }
    public TipoInsumo getTipo() { return tipo; }
    public QuantidadeInsumo getQuantidade() { return quantidade; }
    public UnidadeMedidaInsumo getUnidade() { return unidade; }
    public LocalDateTime getDataConsumo() { return dataConsumo; }
}