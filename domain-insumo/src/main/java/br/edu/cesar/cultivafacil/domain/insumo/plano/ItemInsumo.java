package br.edu.cesar.cultivafacil.domain.insumo.plano;

import java.time.LocalDateTime;
import java.util.UUID;

public class ItemInsumo {

    private final UUID id;
    private final TipoInsumo tipo;
    private final QuantidadeInsumo quantidade;
    private final UnidadeMedidaInsumo unidade;
    private StatusItemInsumo status;
    private PrecoUnitario precoUnitario;
    private LocalDateTime dataAquisicao;

    ItemInsumo(TipoInsumo tipo, QuantidadeInsumo quantidade, UnidadeMedidaInsumo unidade) {
        if (tipo == null) throw new InsumoDomainException(InsumoErroCodigo.TIPO_INSUMO_INVALIDO, "TipoInsumo obrigatorio");
        if (quantidade == null) throw new InsumoDomainException(InsumoErroCodigo.QUANTIDADE_INSUMO_INVALIDA, "QuantidadeInsumo obrigatoria");
        if (unidade == null) throw new InsumoDomainException(InsumoErroCodigo.QUANTIDADE_INSUMO_INVALIDA, "UnidadeMedidaInsumo obrigatoria");
        this.id = UUID.randomUUID();
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.unidade = unidade;
        this.status = StatusItemInsumo.PLANEJADO;
    }

    ItemInsumo(UUID id, TipoInsumo tipo, QuantidadeInsumo quantidade, UnidadeMedidaInsumo unidade,
               StatusItemInsumo status, PrecoUnitario precoUnitario, LocalDateTime dataAquisicao) {
        this.id = id;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.unidade = unidade;
        this.status = status;
        this.precoUnitario = precoUnitario;
        this.dataAquisicao = dataAquisicao;
    }

    public void registrarAquisicao(PrecoUnitario preco) {
        if (preco == null) {
            throw new InsumoDomainException(InsumoErroCodigo.PRECO_UNITARIO_OBRIGATORIO,
                    "Preco unitario obrigatorio para registrar aquisicao");
        }
        this.precoUnitario = preco;
        this.status = StatusItemInsumo.ADQUIRIDO;
        this.dataAquisicao = LocalDateTime.now();
    }

    public void reverterParaPlanejado() {
        if (status == StatusItemInsumo.ADQUIRIDO && dataAquisicao != null) {
            throw new InsumoDomainException(InsumoErroCodigo.REVERSAO_STATUS_INVALIDA,
                    "Item adquirido nao pode retornar ao status Planejado");
        }
    }

    public UUID getId() { return id; }
    public TipoInsumo getTipo() { return tipo; }
    public QuantidadeInsumo getQuantidade() { return quantidade; }
    public UnidadeMedidaInsumo getUnidade() { return unidade; }
    public StatusItemInsumo getStatus() { return status; }
    public PrecoUnitario getPrecoUnitario() { return precoUnitario; }
    public LocalDateTime getDataAquisicao() { return dataAquisicao; }
}