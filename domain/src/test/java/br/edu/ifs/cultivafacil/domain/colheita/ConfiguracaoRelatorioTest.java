package br.edu.ifs.cultivafacil.domain.colheita;


import br.edu.ifs.cultivafacil.shared.AgricultorId;
import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class ConfiguracaoRelatorioTest {

    private final AgricultorId agricultorId = new AgricultorId(UUID.randomUUID());

    @Test
    void criar_comNomeValido_devePersistir() {
        ConfiguracaoRelatorio config = new ConfiguracaoRelatorio(agricultorId, "Relatório Mensal", FiltroPeriodo.ULTIMO_MES);

        assertThat(config.getNome()).isEqualTo("Relatório Mensal");
        assertThat(config.getFiltroPeriodo()).isEqualTo(FiltroPeriodo.ULTIMO_MES);
        assertThat(config.getId()).isNotNull();
    }

    @Test
    void criar_comNomeMuitoCurto_deveRejeitarComNOME_CONFIG_INVALIDO() {
        assertThatThrownBy(() -> new ConfiguracaoRelatorio(agricultorId, "A", FiltroPeriodo.TRIMESTRE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("NOME_CONFIG_INVALIDO");
    }

    @Test
    void criar_comNomeMuitoLongo_deveRejeitarComNOME_CONFIG_INVALIDO() {
        assertThatThrownBy(() ->
                new ConfiguracaoRelatorio(agricultorId, "A".repeat(101), FiltroPeriodo.SEMESTRE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("NOME_CONFIG_INVALIDO");
    }

    @Test
    void criar_comFiltroPeriodoNulo_deveRejeitarComValidate() {
        assertThatThrownBy(() -> new ConfiguracaoRelatorio(agricultorId, "Config Válida", null))
                .isInstanceOf(NullPointerException.class);
    }
}
