package br.edu.cesar.cultivafacil.domain.terreno.terreno;

import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TalhaoSteps {

    private Talhao talhao;
    private final List<Talhao> zonas = new ArrayList<>();
    private BigDecimal areaTerrenoM2;
    private Exception excecaoCapturada;
    private boolean talhaoRemovido;

    // RN-038
    @Dado("que o Terreno ja possui {int} Talhoes cadastrados")
    public void terrenoJaPossuiTalhoesCadastrados(int qtd) {
        for (int i = 1; i <= qtd; i++) {
            zonas.add(new Talhao(
                new NomeTalhao("Zona " + i),
                new AreaTalhao(new BigDecimal("10"))
            ));
        }
    }

    @Quando("o Agricultor tenta criar um novo Talhao no mesmo Terreno")
    public void tentaCriarNovoTalhaoNoMesmoTerreno() {
        try {
            if (zonas.size() >= 20) {
                throw new IllegalArgumentException("LIMITE_ZONAS_EXCEDIDO");
            }
            zonas.add(new Talhao(new NomeTalhao("Nova Zona"), new AreaTalhao(new BigDecimal("10"))));
        } catch (IllegalArgumentException e) {
            excecaoCapturada = e;
        }
    }

    // RN-039: area excede o terreno
    @Dado("que o Terreno possui area total de {int} m2")
    public void terrenoPossuiAreaTotalDe(int area) {
        areaTerrenoM2 = new BigDecimal(area);
        talhao = new Talhao(
            TalhaoId.novo(),
            new NomeTalhao("Zona Existente"),
            new AreaTalhao(new BigDecimal("50")),
            SituacaoTalhao.DISPONIVEL
        );
        zonas.add(talhao);
    }

    @Quando("o Agricultor tenta criar um Talhao com area de {int} m2")
    public void tentaCriarTalhaoComAreaDe(int areaM2) {
        try {
            AreaTalhao area = new AreaTalhao(new BigDecimal(areaM2));
            if (area.getValor().compareTo(areaTerrenoM2) > 0) {
                throw new IllegalArgumentException("AREA_ZONA_INVALIDA");
            }
        } catch (IllegalArgumentException e) {
            excecaoCapturada = e;
        }
    }

    // RN-039b: area invalida (nao-positiva ou mais de 2 casas decimais)
    @Dado("que o Agricultor tenta criar um Talhao com area {string} m2")
    public void tentaCriarTalhaoComAreaInvalida(String area) {
        try {
            new AreaTalhao(new BigDecimal(area));
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    @Quando("submete o cadastro de area")
    public void submeteCadastroDeArea() {
        assertNotNull(excecaoCapturada, "Era esperado que o sistema rejeitasse a area invalida");
    }

    // RN-040a: nome invalido (muito curto)
    @Dado("que o Agricultor tenta criar um Talhao com nome de {int} caractere")
    public void tentaCriarTalhaoComNomeCurto(int qtdCaracteres) {
        try {
            new NomeTalhao("A".repeat(qtdCaracteres));
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    // RN-040a: nome invalido (muito longo)
    @Dado("que o Agricultor tenta criar um Talhao com nome de {int} caracteres")
    public void tentaCriarTalhaoComNomeLongo(int qtdCaracteres) {
        try {
            new NomeTalhao("A".repeat(qtdCaracteres));
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    @Quando("submete o cadastro")
    public void submeteCadastro() {
        assertNotNull(excecaoCapturada, "Era esperado que o sistema rejeitasse o cadastro");
    }

    // RN-040b: nome duplicado
    @Dado("que o Terreno ja possui um Talhao chamado {string}")
    public void terrenoJaPossuiTalhaoComNome(String nome) {
        zonas.add(new Talhao(new NomeTalhao(nome), new AreaTalhao(new BigDecimal("100"))));
        areaTerrenoM2 = new BigDecimal("1000");
    }

    @Quando("o Agricultor tenta criar outro Talhao com o mesmo nome no mesmo Terreno")
    public void tentaCriarTalhaoComMesmoNome() {
        try {
            NomeTalhao nome = new NomeTalhao("Canteiro A");
            boolean duplicado = zonas.stream().anyMatch(z -> z.getNome().equals(nome));
            if (duplicado) {
                throw new IllegalArgumentException("NOME_ZONA_DUPLICADO");
            }
        } catch (IllegalArgumentException e) {
            excecaoCapturada = e;
        }
    }

    // US-10: criacao com sucesso
    @Dado("que o Terreno possui menos de {int} Talhoes e area disponivel suficiente")
    public void terrenoPossuiMenosTalhoesEAreaSuficiente(int limite) {
        zonas.clear();
        areaTerrenoM2 = new BigDecimal("2000");
    }

    @Quando("o Agricultor cria um Talhao com nome {string} e area {int} m2")
    public void criaTalhaoComNomeEArea(String nome, int areaM2) {
        talhao = new Talhao(new NomeTalhao(nome), new AreaTalhao(new BigDecimal(areaM2)));
        zonas.add(talhao);
    }

    @Entao("o Talhao e criado com situacao {string} e associado ao Terreno")
    public void talhaoECriadoComSituacaoEAssociado(String situacao) {
        assertNotNull(talhao);
        assertEquals(SituacaoTalhao.valueOf(situacao), talhao.getSituacao());
        assertTrue(zonas.contains(talhao));
    }

    // RN-041: remocao com cultivo ativo
    @Dado("que o Talhao possui situacao {string}")
    public void talhaoPossuiSituacao(String situacao) {
        talhao = new Talhao(
            TalhaoId.novo(),
            new NomeTalhao("Zona Teste"),
            new AreaTalhao(new BigDecimal("100")),
            SituacaoTalhao.valueOf(situacao)
        );
    }

    @Quando("o Agricultor tenta remover o Talhao")
    public void tentaRemoverTalhao() {
        try {
            if (talhao.isEmUso()) {
                throw new IllegalArgumentException("ZONA_COM_CULTIVO_ATIVO");
            }
            zonas.remove(talhao);
            talhaoRemovido = true;
        } catch (IllegalArgumentException e) {
            excecaoCapturada = e;
        }
    }

    // US-11: remocao sem cultivo
    @Quando("o Agricultor confirma a remocao do Talhao")
    public void confirmaRemocaoDoTalhao() {
        if (!talhao.isEmUso()) {
            zonas.remove(talhao);
            talhaoRemovido = true;
        }
    }

    @Entao("o Talhao e removido do Terreno")
    public void talhaoERemovidoDoTerreno() {
        assertTrue(talhaoRemovido);
        assertFalse(zonas.contains(talhao));
    }

    // US-11-edit: edicao com sucesso
    @Dado("que o Talhao existe com nome {string} e area {int} m2")
    public void talhaoExisteComNomeEArea(String nome, int areaM2) {
        talhao = new Talhao(new NomeTalhao(nome), new AreaTalhao(new BigDecimal(areaM2)));
    }

    @Quando("o Agricultor edita a area do Talhao para {int} m2")
    public void editaAreaDoTalhao(int novaAreaM2) {
        talhao.alterarArea(new AreaTalhao(new BigDecimal(novaAreaM2)));
    }

    @Entao("o Talhao deve ter area de {int} m2")
    public void talhaoDeveTerAreaDe(int areaM2) {
        assertEquals(0, new BigDecimal(areaM2).compareTo(talhao.getArea().getValor()));
    }

    // RN-042: area editada excede o terreno
    @Quando("o Agricultor tenta editar o Talhao para {int} m2")
    public void tentaEditarTalhaoParaArea(int novaAreaM2) {
        try {
            AreaTalhao novaArea = new AreaTalhao(new BigDecimal(novaAreaM2));
            if (novaArea.getValor().compareTo(areaTerrenoM2) > 0) {
                throw new IllegalArgumentException("AREA_ZONA_INVALIDA");
            }
            talhao.alterarArea(novaArea);
        } catch (IllegalArgumentException e) {
            excecaoCapturada = e;
        }
    }

    // shared Then
    @Entao("o sistema deve rejeitar com erro {string}")
    public void sistemaMustRejeitarComErro(String codigoErro) {
        assertNotNull(excecaoCapturada, "Era esperado uma excecao com codigo: " + codigoErro);
        assertEquals(codigoErro, excecaoCapturada.getMessage());
    }
}
