package br.edu.cesar.cultivafacil.domain.insumo.plano;

import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;
import java.time.LocalDateTime;
import java.util.UUID;

public class PedidoReposicao {

    private final UUID id;
    private final TipoInsumo tipo;
    private final TalhaoId talhaoId;
    private final double saldoNoMomento;
    private final double quantidadeSugerida;
    private final String emailDestinatario;
    private final LocalDateTime dataHoraDisparo;
    private StatusEnvioPedido statusEnvio;

    PedidoReposicao(TipoInsumo tipo, TalhaoId talhaoId, double saldoNoMomento,
                    double quantidadeSugerida, String emailDestinatario) {
        this.id = UUID.randomUUID();
        this.tipo = tipo;
        this.talhaoId = talhaoId;
        this.saldoNoMomento = saldoNoMomento;
        this.quantidadeSugerida = quantidadeSugerida;
        this.emailDestinatario = emailDestinatario;
        this.dataHoraDisparo = LocalDateTime.now();
        this.statusEnvio = StatusEnvioPedido.PENDENTE;
    }

    PedidoReposicao(UUID id, TipoInsumo tipo, TalhaoId talhaoId, double saldoNoMomento,
                    double quantidadeSugerida, String emailDestinatario,
                    LocalDateTime dataHoraDisparo, StatusEnvioPedido statusEnvio) {
        this.id = id;
        this.tipo = tipo;
        this.talhaoId = talhaoId;
        this.saldoNoMomento = saldoNoMomento;
        this.quantidadeSugerida = quantidadeSugerida;
        this.emailDestinatario = emailDestinatario;
        this.dataHoraDisparo = dataHoraDisparo;
        this.statusEnvio = statusEnvio;
    }

    public void marcarComoEnviado() {
        this.statusEnvio = StatusEnvioPedido.ENVIADO;
    }

    public void marcarComoFalha() {
        this.statusEnvio = StatusEnvioPedido.FALHA;
    }

    public UUID getId() { return id; }
    public TipoInsumo getTipo() { return tipo; }
    public TalhaoId getTalhaoId() { return talhaoId; }
    public double getSaldoNoMomento() { return saldoNoMomento; }
    public double getQuantidadeSugerida() { return quantidadeSugerida; }
    public String getEmailDestinatario() { return emailDestinatario; }
    public LocalDateTime getDataHoraDisparo() { return dataHoraDisparo; }
    public StatusEnvioPedido getStatusEnvio() { return statusEnvio; }
}