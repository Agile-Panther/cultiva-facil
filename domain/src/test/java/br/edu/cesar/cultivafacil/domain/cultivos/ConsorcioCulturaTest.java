package br.edu.cesar.cultivafacil.domain.cultivos;

import br.edu.cesar.cultivafacil.shared.ZonaId;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class ConsorcioCulturaTest {

    @Test
    void deveCriarConsorcioCulturaComCamposObrigatorios() {
        ZonaId zonaId = ZonaId.novo();
        NomeCultura cultura = new NomeCultura("Manjericão");

        ConsorcioCultura consorcio = new ConsorcioCultura(zonaId, cultura, ClassificacaoConsorcio.COMPANHEIRA, false);

        assertThat(consorcio.getId()).isNotNull();
        assertThat(consorcio.getZonaId()).isEqualTo(zonaId);
        assertThat(consorcio.getCultura()).isEqualTo(cultura);
        assertThat(consorcio.getClassificacao()).isEqualTo(ClassificacaoConsorcio.COMPANHEIRA);
    }

    @Test
    void deveRegistrarCienciaDoAgricultorQuandoInimigaComConsentimento() {
        ZonaId zonaId = ZonaId.novo();
        NomeCultura cultura = new NomeCultura("Funcho");

        ConsorcioCultura consorcio = new ConsorcioCultura(zonaId, cultura, ClassificacaoConsorcio.INIMIGA, true);

        assertThat(consorcio.isCienciaDoAgricultor()).isTrue();
        assertThat(consorcio.getClassificacao()).isEqualTo(ClassificacaoConsorcio.INIMIGA);
    }

    @Test
    void deveIniciarSemEncerramento() {
        ZonaId zonaId = ZonaId.novo();
        NomeCultura cultura = new NomeCultura("Cenoura");

        ConsorcioCultura consorcio = new ConsorcioCultura(zonaId, cultura, ClassificacaoConsorcio.NEUTRA, false);

        assertThat(consorcio.isEncerrado()).isFalse();
    }

    @Test
    void deveEncerrarConsorcio() {
        ZonaId zonaId = ZonaId.novo();
        NomeCultura cultura = new NomeCultura("Cenoura");
        ConsorcioCultura consorcio = new ConsorcioCultura(zonaId, cultura, ClassificacaoConsorcio.NEUTRA, false);

        consorcio.encerrar();

        assertThat(consorcio.isEncerrado()).isTrue();
    }

    @Test
    void deveRejeitarZonaIdNula() {
        NomeCultura cultura = new NomeCultura("Cenoura");

        assertThatThrownBy(() -> new ConsorcioCultura(null, cultura, ClassificacaoConsorcio.NEUTRA, false))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void deveRejeitarCulturaNula() {
        ZonaId zonaId = ZonaId.novo();

        assertThatThrownBy(() -> new ConsorcioCultura(zonaId, null, ClassificacaoConsorcio.NEUTRA, false))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void deveRejeitarClassificacaoNula() {
        ZonaId zonaId = ZonaId.novo();
        NomeCultura cultura = new NomeCultura("Cenoura");

        assertThatThrownBy(() -> new ConsorcioCultura(zonaId, cultura, null, false))
                .isInstanceOf(NullPointerException.class);
    }
}
