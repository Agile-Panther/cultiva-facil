package br.edu.cesar.cultivafacil.domain.colheita;

import br.edu.ifs.cultivafacil.shared.CicloAgricolaId;
import org.apache.commons.lang3.Validate;
import java.math.BigDecimal;
import java.util.UUID;

public class MetaComerciavel {

    private final UUID id;
    private final CicloAgricolaId cicloAgricolaId;
    private final BigDecimal valor;
    private final boolean cicloAtivo;

    public MetaComerciavel(CicloAgricolaId cicloAgricolaId, BigDecimal valor,
                           BigDecimal quantidadePlantada, boolean cicloAtivo) {
        // RN-03: ciclo deve estar ativo
        Validate.isTrue(cicloAtivo, "CICLO_INATIVO");
        Validate.notNull(cicloAgricolaId, "CicloAgricolaId não pode ser nulo");
        Validate.notNull(valor, "Valor da Meta não pode ser nulo");
        Validate.notNull(quantidadePlantada, "QuantidadePlantada não pode ser nula");
        // RN-02: valor > 0 e <= quantidadePlantada
        Validate.isTrue(valor.compareTo(BigDecimal.ZERO) > 0, "META_COMERCIALIZAVEL_INVALIDA");
        Validate.isTrue(valor.compareTo(quantidadePlantada) <= 0, "META_COMERCIALIZAVEL_INVALIDA");
        this.id = UUID.randomUUID();
        this.cicloAgricolaId = cicloAgricolaId;
        this.valor = valor;
        this.cicloAtivo = cicloAtivo;
    }

    public UUID getId() { return id; }
    public CicloAgricolaId getCicloAgricolaId() { return cicloAgricolaId; }
    public BigDecimal getValor() { return valor; }
    public boolean isCicloAtivo() { return cicloAtivo; }
}
