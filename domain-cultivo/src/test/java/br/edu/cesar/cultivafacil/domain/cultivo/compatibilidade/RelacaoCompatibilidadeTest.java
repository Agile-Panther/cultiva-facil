package br.edu.cesar.cultivafacil.domain.cultivo.compatibilidade;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.NomeCultura;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RelacaoCompatibilidadeTest {

    @Test
    void deveCriarRelacaoComDoisNomesEClassificacao() {
        NomeCultura base = new NomeCultura("Tomate");
        NomeCultura relacionada = new NomeCultura("Manjericão");

        RelacaoCompatibilidade relacao = new RelacaoCompatibilidade(base, relacionada, ClassificacaoConsorcio.COMPANHEIRA);

        assertNotNull(relacao.getId());
        assertEquals(base, relacao.getCulturaBase());
        assertEquals(relacionada, relacao.getCulturaRelacionada());
        assertEquals(ClassificacaoConsorcio.COMPANHEIRA, relacao.getClassificacao());
    }

    @Test
    void deveCriarRelacaoInimigaEntreTomate_e_Funcho() {
        NomeCultura tomate = new NomeCultura("Tomate");
        NomeCultura funcho = new NomeCultura("Funcho");

        RelacaoCompatibilidade relacao = new RelacaoCompatibilidade(tomate, funcho, ClassificacaoConsorcio.INIMIGA);

        assertEquals(ClassificacaoConsorcio.INIMIGA, relacao.getClassificacao());
    }

    @Test
    void deveCriarRelacaoNeutraParaCulturasSemlRelacaoDef() {
        NomeCultura tomate = new NomeCultura("Tomate");
        NomeCultura cenoura = new NomeCultura("Cenoura");

        RelacaoCompatibilidade relacao = new RelacaoCompatibilidade(tomate, cenoura, ClassificacaoConsorcio.NEUTRA);

        assertEquals(ClassificacaoConsorcio.NEUTRA, relacao.getClassificacao());
    }

    @Test
    void deveRejeitarCulturaBaseNula() {
        NomeCultura relacionada = new NomeCultura("Manjericão");

        assertThrows(NullPointerException.class,
            () -> new RelacaoCompatibilidade(null, relacionada, ClassificacaoConsorcio.COMPANHEIRA));
    }

    @Test
    void deveRejeitarCulturaRelacionadaNula() {
        NomeCultura base = new NomeCultura("Tomate");

        assertThrows(NullPointerException.class,
            () -> new RelacaoCompatibilidade(base, null, ClassificacaoConsorcio.COMPANHEIRA));
    }

    @Test
    void deveRejeitarClassificacaoNula() {
        NomeCultura base = new NomeCultura("Tomate");
        NomeCultura relacionada = new NomeCultura("Manjericão");

        assertThrows(NullPointerException.class,
            () -> new RelacaoCompatibilidade(base, relacionada, null));
    }

    @Test
    void deveReconstituirComIdExistente() {
        RelacaoCompatibilidadeId id = new RelacaoCompatibilidadeId(UUID.randomUUID());
        NomeCultura base = new NomeCultura("Tomate");
        NomeCultura relacionada = new NomeCultura("Funcho");

        RelacaoCompatibilidade relacao = new RelacaoCompatibilidade(id, base, relacionada, ClassificacaoConsorcio.INIMIGA);

        assertEquals(id, relacao.getId());
        assertEquals(ClassificacaoConsorcio.INIMIGA, relacao.getClassificacao());
    }

    @Test
    void deveRejeitarIdNuloNaReconstituicao() {
        NomeCultura base = new NomeCultura("Tomate");
        NomeCultura relacionada = new NomeCultura("Funcho");

        assertThrows(NullPointerException.class,
            () -> new RelacaoCompatibilidade(null, base, relacionada, ClassificacaoConsorcio.INIMIGA));
    }
}
