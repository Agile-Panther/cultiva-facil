package br.edu.cesar.cultivafacil.domain.cultivo.compatibilidade;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.NomeCultura;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConsorcioCulturaTest {

    @Test
    void deveCriarConsorcioCulturaComCamposObrigatorios() {
        TalhaoId talhaoId = TalhaoId.novo();
        NomeCultura cultura = new NomeCultura("Manjericão");

        ConsorcioCultura consorcio = new ConsorcioCultura(talhaoId, cultura, ClassificacaoConsorcio.COMPANHEIRA, false);

        assertNotNull(consorcio.getId());
        assertEquals(talhaoId, consorcio.getTalhaoId());
        assertEquals(cultura, consorcio.getCultura());
        assertEquals(ClassificacaoConsorcio.COMPANHEIRA, consorcio.getClassificacao());
    }

    @Test
    void deveRegistrarCienciaDoAgricultorQuandoInimigaComConsentimento() {
        TalhaoId talhaoId = TalhaoId.novo();
        NomeCultura cultura = new NomeCultura("Funcho");

        ConsorcioCultura consorcio = new ConsorcioCultura(talhaoId, cultura, ClassificacaoConsorcio.INIMIGA, true);

        assertTrue(consorcio.isCienciaDoAgricultor());
        assertEquals(ClassificacaoConsorcio.INIMIGA, consorcio.getClassificacao());
    }

    @Test
    void deveIniciarSemEncerramento() {
        TalhaoId talhaoId = TalhaoId.novo();
        NomeCultura cultura = new NomeCultura("Cenoura");

        ConsorcioCultura consorcio = new ConsorcioCultura(talhaoId, cultura, ClassificacaoConsorcio.NEUTRA, false);

        assertFalse(consorcio.isEncerrado());
    }

    @Test
    void deveEncerrarConsorcio() {
        TalhaoId talhaoId = TalhaoId.novo();
        NomeCultura cultura = new NomeCultura("Cenoura");
        ConsorcioCultura consorcio = new ConsorcioCultura(talhaoId, cultura, ClassificacaoConsorcio.NEUTRA, false);

        consorcio.encerrar();

        assertTrue(consorcio.isEncerrado());
    }

    @Test
    void deveCriarConsorcioCienciaFalsaParaCulturaCompanheira() {
        TalhaoId talhaoId = TalhaoId.novo();
        NomeCultura cultura = new NomeCultura("Manjericão");

        ConsorcioCultura consorcio = new ConsorcioCultura(talhaoId, cultura, ClassificacaoConsorcio.COMPANHEIRA, false);

        assertFalse(consorcio.isCienciaDoAgricultor());
    }

    @Test
    void deveRejeitarTalhaoIdNulo() {
        NomeCultura cultura = new NomeCultura("Cenoura");

        assertThrows(NullPointerException.class,
            () -> new ConsorcioCultura(null, cultura, ClassificacaoConsorcio.NEUTRA, false));
    }

    @Test
    void deveRejeitarCulturaNula() {
        TalhaoId talhaoId = TalhaoId.novo();

        assertThrows(NullPointerException.class,
            () -> new ConsorcioCultura(talhaoId, null, ClassificacaoConsorcio.NEUTRA, false));
    }

    @Test
    void deveRejeitarClassificacaoNula() {
        TalhaoId talhaoId = TalhaoId.novo();
        NomeCultura cultura = new NomeCultura("Cenoura");

        assertThrows(NullPointerException.class,
            () -> new ConsorcioCultura(talhaoId, cultura, null, false));
    }

    @Test
    void deveReconstituirConsorcioPersistido() {
        ConsorcioCulturaId id = ConsorcioCulturaId.novo();
        TalhaoId talhaoId = TalhaoId.novo();
        NomeCultura cultura = new NomeCultura("Funcho");

        ConsorcioCultura consorcio = new ConsorcioCultura(
            id, talhaoId, cultura, ClassificacaoConsorcio.INIMIGA, true, true);

        assertEquals(id, consorcio.getId());
        assertTrue(consorcio.isCienciaDoAgricultor());
        assertTrue(consorcio.isEncerrado());
    }
}
