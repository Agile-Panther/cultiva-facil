package br.edu.cesar.cultivafacil.domain.cultivos;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class RelacaoCompatibilidadeTest {

    @Test
    void deveCriarRelacaoComDoisNomesEClassificacao() {
        NomeCultura base = new NomeCultura("Tomate");
        NomeCultura relacionada = new NomeCultura("Manjericão");

        RelacaoCompatibilidade relacao = new RelacaoCompatibilidade(base, relacionada, ClassificacaoConsorcio.COMPANHEIRA);

        assertThat(relacao.getId()).isNotNull();
        assertThat(relacao.getCulturaBase()).isEqualTo(base);
        assertThat(relacao.getCulturaRelacionada()).isEqualTo(relacionada);
        assertThat(relacao.getClassificacao()).isEqualTo(ClassificacaoConsorcio.COMPANHEIRA);
    }

    @Test
    void deveRejeitarCulturaBaseNula() {
        NomeCultura relacionada = new NomeCultura("Manjericão");

        assertThatThrownBy(() -> new RelacaoCompatibilidade(null, relacionada, ClassificacaoConsorcio.COMPANHEIRA))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void deveRejeitarCulturaRelacionadaNula() {
        NomeCultura base = new NomeCultura("Tomate");

        assertThatThrownBy(() -> new RelacaoCompatibilidade(base, null, ClassificacaoConsorcio.COMPANHEIRA))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void deveRejeitarClassificacaoNula() {
        NomeCultura base = new NomeCultura("Tomate");
        NomeCultura relacionada = new NomeCultura("Manjericão");

        assertThatThrownBy(() -> new RelacaoCompatibilidade(base, relacionada, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void deveReconstituirComIdExistente() {
        RelacaoCompatibilidadeId id = new RelacaoCompatibilidadeId(UUID.randomUUID());
        NomeCultura base = new NomeCultura("Tomate");
        NomeCultura relacionada = new NomeCultura("Funcho");

        RelacaoCompatibilidade relacao = new RelacaoCompatibilidade(id, base, relacionada, ClassificacaoConsorcio.INIMIGA);

        assertThat(relacao.getId()).isEqualTo(id);
        assertThat(relacao.getClassificacao()).isEqualTo(ClassificacaoConsorcio.INIMIGA);
    }
}
