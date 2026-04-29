package br.edu.cesar.cultivafacil.domain.propriedade.propriedade;

import br.edu.cesar.cultivafacil.shared.ContaId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AgroclimaticaServicoTest {

    @Test
    void criarSegundaPropriedade_deveRejeitarComPROPRIEDADE_INVALIDO() {
        InMemoryPropriedadeRepositorio repositorio = new InMemoryPropriedadeRepositorio();
        ValidadorCompatibilidadeAgroclimaticaServico servico = new ValidadorCompatibilidadeAgroclimaticaServico(repositorio);
        ContaId contaId = ContaId.novo();
        servico.criarPropriedade(contaId);

        assertThatThrownBy(() -> servico.criarPropriedade(contaId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("PROPRIEDADE_INVALIDO");
    }

    @Test
    void localizacaoComApenasMunicipio_deveRejeitarComPROPRIEDADE_INVALIDO() {
        assertThatThrownBy(() -> new Localizacao("Recife", ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("PROPRIEDADE_INVALIDO");
    }

    @Test
    void localizacaoComApenasEstado_deveRejeitarComPROPRIEDADE_INVALIDO() {
        assertThatThrownBy(() -> new Localizacao("", "PE"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("PROPRIEDADE_INVALIDO");
    }

    @Test
    void tipoSoloInvalido_deveRejeitarComPROPRIEDADE_INVALIDO() {
        assertThatThrownBy(() -> TipoSolo.fromString("Pedregoso"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("PROPRIEDADE_INVALIDO");
    }

    @Test
    void climaInvalido_deveRejeitarComPROPRIEDADE_INVALIDO() {
        assertThatThrownBy(() -> ClimaRegiao.fromString("Arido"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("PROPRIEDADE_INVALIDO");
    }

    @Test
    void atualizarClimaIncompativelComRegiao_deveRejeitarComPROPRIEDADE_INVALIDO() {
        InMemoryPropriedadeRepositorio repositorio = new InMemoryPropriedadeRepositorio();
        ValidadorCompatibilidadeAgroclimaticaServico servico = new ValidadorCompatibilidadeAgroclimaticaServico(repositorio) {
            @Override
            public boolean ehCompativel(Localizacao localizacao, ClimaRegiao climaRegiao) {
                return false;
            }
        };
        Propriedade propriedade = servico.criarPropriedade(ContaId.novo());
        servico.completarPerfil(propriedade.getId(), "Recife", "PE", "Latossolo", "Semiarido");

        assertThatThrownBy(() -> servico.atualizarClima(
                propriedade.getId(),
                "Tropical Umido",
                new JustificativaDiagnostico("Revisao")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("PROPRIEDADE_INVALIDO");
    }

    @Test
    void atualizarTipoSoloInvalido_deveRejeitarComPROPRIEDADE_INVALIDO() {
        InMemoryPropriedadeRepositorio repositorio = new InMemoryPropriedadeRepositorio();
        ValidadorCompatibilidadeAgroclimaticaServico servico = new ValidadorCompatibilidadeAgroclimaticaServico(repositorio);
        Propriedade propriedade = servico.criarPropriedade(ContaId.novo());
        servico.completarPerfil(propriedade.getId(), "Recife", "PE", "Latossolo", "Semiarido");

        assertThatThrownBy(() -> servico.atualizarTipoSolo(
                propriedade.getId(),
                "Pedregoso",
                new JustificativaDiagnostico("Ajuste")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("PROPRIEDADE_INVALIDO");
    }

    @Test
    void atualizarClimaForaDaClassificacao_deveRejeitarComPROPRIEDADE_INVALIDO() {
        InMemoryPropriedadeRepositorio repositorio = new InMemoryPropriedadeRepositorio();
        ValidadorCompatibilidadeAgroclimaticaServico servico = new ValidadorCompatibilidadeAgroclimaticaServico(repositorio);
        Propriedade propriedade = servico.criarPropriedade(ContaId.novo());
        servico.completarPerfil(propriedade.getId(), "Recife", "PE", "Latossolo", "Semiarido");

        assertThatThrownBy(() -> servico.atualizarClima(
                propriedade.getId(),
                "Arido",
                new JustificativaDiagnostico("Ajuste")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("PROPRIEDADE_INVALIDO");
    }

    @Test
    void validarPerfilIncompleto_deveRejeitarComPROPRIEDADE_INVALIDO() {
        InMemoryPropriedadeRepositorio repositorio = new InMemoryPropriedadeRepositorio();
        ValidadorCompatibilidadeAgroclimaticaServico servico = new ValidadorCompatibilidadeAgroclimaticaServico(repositorio);
        Propriedade propriedade = servico.criarPropriedade(ContaId.novo());

        assertThatThrownBy(() -> servico.validarPerfilCompleto(propriedade.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("PROPRIEDADE_INVALIDO");
    }

    @Test
    void completarPerfilComClimaAusente_deveRejeitarComPROPRIEDADE_INVALIDO() {
        InMemoryPropriedadeRepositorio repositorio = new InMemoryPropriedadeRepositorio();
        ValidadorCompatibilidadeAgroclimaticaServico servico = new ValidadorCompatibilidadeAgroclimaticaServico(repositorio);
        Propriedade propriedade = servico.criarPropriedade(ContaId.novo());

        assertThatThrownBy(() -> servico.completarPerfil(
                propriedade.getId(),
                "Recife",
                "PE",
                "Latossolo",
                null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("PROPRIEDADE_INVALIDO");
    }
}
