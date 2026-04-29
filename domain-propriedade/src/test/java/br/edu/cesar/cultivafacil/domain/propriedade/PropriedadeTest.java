package br.edu.cesar.cultivafacil.domain.propriedade.propriedade;

import br.edu.cesar.cultivafacil.shared.ContaId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PropriedadeTest {

    @Test
    void completarPerfil_comCampoAusente_deveRejeitarComPROPRIEDADE_INVALIDO() {
        Propriedade propriedade = new Propriedade(ContaId.novo());
        Localizacao localizacao = new Localizacao("Recife", "PE");

        assertThatThrownBy(() -> propriedade.completarPerfil(localizacao, TipoSolo.LATOSSOLO, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("PROPRIEDADE_INVALIDO");
    }
}
