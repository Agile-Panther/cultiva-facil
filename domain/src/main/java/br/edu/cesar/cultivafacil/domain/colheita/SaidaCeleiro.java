package br.edu.cesar.cultivafacil.domain.colheita;

import br.edu.ifs.cultivafacil.shared.CicloAgricolaId;
import org.apache.commons.lang3.Validate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class SaidaCeleiro {

    private final UUID id;
    private final CicloAgricolaId cicloAgricolaId;
    private final BigDecimal quantidade;
    private final MotivoSaida motivo;
    private final LocalDateTime dataHora;

    public SaidaCeleiro(CicloAgricolaId cicloAgricolaId, BigDecimal quantidade, MotivoSaida motivo) {
        Validate.notNull(cicloAgricolaId, "CicloAgricolaId não pode ser nulo");
        Validate.notNull(quantidade, "Quantidade não pode ser nula");
        Validate.notNull(motivo, "MotivoSaida não pode ser nulo");
        Validate.isTrue(quantidade.compareTo(BigDecimal.ZERO) > 0, "Quantidade da saída deve ser positiva");
        this.id = UUID.randomUUID();
        this.cicloAgricolaId = cicloAgricolaId;
        this.quantidade = quantidade;
        this.motivo = motivo;
        this.dataHora = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public CicloAgricolaId getCicloAgricolaId() { return cicloAgricolaId; }
    public BigDecimal getQuantidade() { return quantidade; }
    public MotivoSaida getMotivo() { return motivo; }
    public LocalDateTime getDataHora() { return dataHora; }
}
