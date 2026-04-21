package br.edu.ifs.cultivafacil.domain.colheita;

import br.edu.ifs.cultivafacil.shared.CicloAgricolaId;
import br.edu.ifs.cultivafacil.shared.ZonaId;
import org.apache.commons.lang3.Validate;
import java.math.BigDecimal;
import java.util.UUID;

public class ItemCeleiro {

    private final UUID id;
    private final CicloAgricolaId cicloAgricolaId;
    private final ZonaId zonaId;
    private BigDecimal quantidadePlantada;
    private BigDecimal perdasAcumuladas;
    private boolean cicloAtivo;

    public ItemCeleiro(CicloAgricolaId cicloAgricolaId, ZonaId zonaId, BigDecimal quantidadePlantada) {
        Validate.notNull(cicloAgricolaId, "CicloAgricolaId não pode ser nulo");
        Validate.notNull(zonaId, "ZonaId não pode ser nulo");
        Validate.notNull(quantidadePlantada, "QuantidadePlantada não pode ser nula");
        Validate.isTrue(quantidadePlantada.compareTo(BigDecimal.ZERO) > 0, "QuantidadePlantada deve ser positiva");
        this.id = UUID.randomUUID();
        this.cicloAgricolaId = cicloAgricolaId;
        this.zonaId = zonaId;
        this.quantidadePlantada = quantidadePlantada;
        this.perdasAcumuladas = BigDecimal.ZERO;
        this.cicloAtivo = true;
    }

    // RN-01: alteração via evento de domínio, nunca diretamente
    public void decrementarPorPerda(BigDecimal quantidadePerda) {
        Validate.notNull(quantidadePerda, "QuantidadePerda não pode ser nula");
        Validate.isTrue(quantidadePerda.compareTo(BigDecimal.ZERO) > 0, "QuantidadePerda deve ser positiva");
        this.perdasAcumuladas = this.perdasAcumuladas.add(quantidadePerda);
    }

    public BigDecimal getProjecaoAtual() {
        return quantidadePlantada.subtract(perdasAcumuladas);
    }

    public UUID getId() { return id; }
    public CicloAgricolaId getCicloAgricolaId() { return cicloAgricolaId; }
    public ZonaId getZonaId() { return zonaId; }
    public BigDecimal getQuantidadePlantada() { return quantidadePlantada; }
    public BigDecimal getPerdasAcumuladas() { return perdasAcumuladas; }
    public boolean isCicloAtivo() { return cicloAtivo; }
    public void encerrarCiclo() { this.cicloAtivo = false; }
}
