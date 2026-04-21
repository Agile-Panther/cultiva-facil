package br.edu.ifs.cultivafacil.domain.colheita;

import br.edu.ifs.cultivafacil.shared.CicloAgricolaId;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class MetaComerciavelTest {

    private final CicloAgricolaId cicloId = new CicloAgricolaId(UUID.randomUUID());
    private final BigDecimal plantado = new BigDecimal("100.00");

    @Test
    void criar_comValorValidoMenorOuIgualAoPlantado_devePersistir() {
        MetaComerciavel meta = new MetaComerciavel(cicloId, new BigDecimal("80.00"), plantado, true);

        assertThat(meta.getId()).isNotNull();
        assertThat(meta.getValor()).isEqualByComparingTo("80.00");
        assertThat(meta.isCicloAtivo()).isTrue();
    }

    @Test
    void criar_comValorZero_deveRejeitarComMETA_COMERCIALIZAVEL_INVALIDA() {
        assertThatThrownBy(() -> new MetaComerciavel(cicloId, BigDecimal.ZERO, plantado, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("META_COMERCIALIZAVEL_INVALIDA");
    }

    @Test
    void criar_comValorSuperiorAoPlantado_deveRejeitarComMETA_COMERCIALIZAVEL_INVALIDA() {
        assertThatThrownBy(() -> new MetaComerciavel(cicloId, new BigDecimal("120.00"), plantado, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("META_COMERCIALIZAVEL_INVALIDA");
    }

    @Test
    void criar_comCicloEncerrado_deveRejeitarComCICLO_INATIVO() {
        assertThatThrownBy(() -> new MetaComerciavel(cicloId, new BigDecimal("50.00"), plantado, false))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("CICLO_INATIVO");
    }
}
