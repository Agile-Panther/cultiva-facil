package br.edu.cesar.cultivafacil.domain.insumo.plano;

import java.time.LocalDateTime;
import java.util.UUID;

public class ConfiguracaoLimite {

    private final UUID id;
    private final TipoInsumo tipo;
    private final QuantidadeInsumo limiteMinimo;
    private final UnidadeMedidaInsumo unidade;
    private final String emailDestinatario;
    private boolean ativa;
    private LocalDateTime ultimoPedido;

    ConfiguracaoLimite(TipoInsumo tipo, QuantidadeInsumo limiteMinimo,
                       UnidadeMedidaInsumo unidade, String emailDestinatario) {
        if (tipo == null) throw new InsumoDomainException(InsumoErroCodigo.TIPO_INSUMO_INVALIDO, "TipoInsumo obrigatorio");
        if (limiteMinimo == null) throw new InsumoDomainException(InsumoErroCodigo.LIMITE_INVALIDO, "LimiteMinimo obrigatorio");
        if (unidade == null) throw new InsumoDomainException(InsumoErroCodigo.LIMITE_INVALIDO, "UnidadeMedidaInsumo obrigatoria");
        if (emailDestinatario == null || emailDestinatario.isBlank()) {
            throw new InsumoDomainException(InsumoErroCodigo.EMAIL_DESTINATARIO_OBRIGATORIO,
                    "Email do destinatario obrigatorio para configurar limite");
        }
        this.id = UUID.randomUUID();
        this.tipo = tipo;
        this.limiteMinimo = limiteMinimo;
        this.unidade = unidade;
        this.emailDestinatario = emailDestinatario;
        this.ativa = true;
    }

    ConfiguracaoLimite(UUID id, TipoInsumo tipo, QuantidadeInsumo limiteMinimo,
                       UnidadeMedidaInsumo unidade, String emailDestinatario,
                       boolean ativa, LocalDateTime ultimoPedido) {
        this.id = id;
        this.tipo = tipo;
        this.limiteMinimo = limiteMinimo;
        this.unidade = unidade;
        this.emailDestinatario = emailDestinatario;
        this.ativa = ativa;
        this.ultimoPedido = ultimoPedido;
    }

    public boolean podeEmitirNovoPedido() {
        return ultimoPedido == null || ultimoPedido.isBefore(LocalDateTime.now().minusHours(24));
    }

    public void registrarPedido() {
        this.ultimoPedido = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public TipoInsumo getTipo() { return tipo; }
    public QuantidadeInsumo getLimiteMinimo() { return limiteMinimo; }
    public UnidadeMedidaInsumo getUnidade() { return unidade; }
    public String getEmailDestinatario() { return emailDestinatario; }
    public boolean isAtiva() { return ativa; }
    public LocalDateTime getUltimoPedido() { return ultimoPedido; }
}