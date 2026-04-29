package br.edu.cesar.cultivafacil.domain.estoque.estoque;

import br.edu.cesar.cultivafacil.domain.propriedade.propriedade.PropriedadeId;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Quando;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class EstoqueSteps {

    private EstoquePropriedade estoque;
    private final Map<String, ItemEstoqueId> itensPorNome = new HashMap<>();
    private Exception excecaoCapturada;

    @Dado("que existe um estoque para a propriedade {string}")
    public void existeEstoqueParaPropriedade(String nome) {
        estoque = new EstoquePropriedade(PropriedadeId.novo(), LocalDate.of(2026, 4, 28));
        itensPorNome.clear();
        excecaoCapturada = null;
    }

    @E("que existe o item de estoque {string} com unidade de medida {string}")
    public void existeItemDeEstoque(String nome, String unidade) {
        ItemEstoqueId itemId = estoque.cadastrarItem(nome, TipoItemEstoque.INSUMO, new UnidadeMedidaEstoque(unidade));
        itensPorNome.put(nome, itemId);
    }

    @Dado("que existe entrada para item {string} com quantidade {double}, unidade {string}, origem {string} e referencia {string}")
    public void existeEntradaParaItem(String nome, double quantidade, String unidade, String origem, String referencia) {
        estoque.registrarEntrada(item(nome), texto(quantidade), unidade, origem, referencia);
    }

    @Dado("que a data atual do estoque e {string}")
    public void dataAtualDoEstoque(String data) {
        estoque.alterarDataAtual(LocalDate.parse(data));
    }

    @Dado("que existe configuracao ATIVA de limite minimo para o item {string}")
    public void existeConfiguracaoAtiva(String nome) {
        estoque.configurarLimiteMinimo(item(nome), "50", "kg");
    }

    @Quando("registro entrada do item {string} com quantidade {double}, unidade {string}, origem {string} e referencia {string}")
    public void registroEntradaDouble(String nome, double quantidade, String unidade, String origem, String referencia) {
        capturar(() -> estoque.registrarEntrada(item(nome), texto(quantidade), unidade, origem, referencia));
    }

    @Quando("registro entrada do item {string} com quantidade {string}, unidade {string}, origem {string} e referencia {string}")
    public void registroEntradaString(String nome, String quantidade, String unidade, String origem, String referencia) {
        capturar(() -> estoque.registrarEntrada(item(nome), quantidade, unidade, origem, referencia));
    }

    @Quando("registro saida do item {string} com quantidade {string}, motivo {string} e data {string}")
    public void registroSaidaString(String nome, String quantidade, String motivo, String data) {
        String justificativa = "Descarte".equals(motivo) ? textoComTamanho(20) : null;
        capturar(() -> estoque.registrarSaida(item(nome), quantidade, motivo, LocalDate.parse(data), justificativa));
    }

    @Quando("registro saida do item {string} com quantidade {double}, motivo {string} e data {string}")
    public void registroSaidaDouble(String nome, double quantidade, String motivo, String data) {
        String justificativa = "Descarte".equals(motivo) ? textoComTamanho(20) : null;
        capturar(() -> estoque.registrarSaida(item(nome), texto(quantidade), motivo, LocalDate.parse(data), justificativa));
    }

    @Quando("registro saida do item {string} com quantidade {double}, motivo {string}, data {string} e justificativa de {string} caracteres")
    public void registroSaidaComJustificativa(String nome, double quantidade, String motivo, String data, String tamanho) {
        capturar(() -> estoque.registrarSaida(item(nome), texto(quantidade), motivo,
                LocalDate.parse(data), textoComTamanho(Integer.parseInt(tamanho))));
    }

    @Quando("configuro limite minimo do item {string} com valor {string} e unidade {string}")
    public void configuroLimiteString(String nome, String valor, String unidade) {
        capturar(() -> estoque.configurarLimiteMinimo(item(nome), valor, unidade));
    }

    @Quando("configuro limite minimo do item {string} com valor {double} e unidade {string}")
    public void configuroLimiteDouble(String nome, double valor, String unidade) {
        capturar(() -> estoque.configurarLimiteMinimo(item(nome), texto(valor), unidade));
    }

    @Entao("o sistema {string} o registro")
    public void sistemaResultadoRegistro(String resultado) {
        verificarResultado(resultado);
    }

    @Entao("o sistema {string} a configuracao")
    public void sistemaResultadoConfiguracao(String resultado) {
        verificarResultado(resultado);
    }

    @Entao("o sistema rejeita o registro")
    public void sistemaRejeitaRegistro() {
        assertNotNull(excecaoCapturada, "Esperava rejeicao do registro");
    }

    @Entao("o sistema rejeita a configuracao")
    public void sistemaRejeitaConfiguracao() {
        assertNotNull(excecaoCapturada, "Esperava rejeicao da configuracao");
    }

    @E("a mensagem de erro contem {string}")
    public void mensagemErroContem(String trecho) {
        assertNotNull(excecaoCapturada, "Esperava mensagem de erro");
        assertTrue(excecaoCapturada.getMessage().toLowerCase().contains(trecho.toLowerCase()),
                "Mensagem nao contem '" + trecho + "': " + excecaoCapturada.getMessage());
    }

    private void verificarResultado(String resultado) {
        if ("aceita".equals(resultado)) {
            assertNull(excecaoCapturada, "Esperava aceite, mas houve erro: " +
                    (excecaoCapturada == null ? "" : excecaoCapturada.getMessage()));
        } else if ("rejeita".equals(resultado)) {
            assertNotNull(excecaoCapturada, "Esperava rejeicao");
        } else {
            fail("Resultado desconhecido: " + resultado);
        }
    }

    private void capturar(Runnable operacao) {
        excecaoCapturada = null;
        try {
            operacao.run();
        } catch (Exception ex) {
            excecaoCapturada = ex;
        }
    }

    private ItemEstoqueId item(String nome) {
        ItemEstoqueId itemId = itensPorNome.get(nome);
        assertNotNull(itemId, "Item nao encontrado: " + nome);
        return itemId;
    }

    private String texto(double valor) {
        if (valor == Math.rint(valor)) {
            return String.valueOf((long) valor);
        }
        return String.valueOf(valor);
    }

    private String textoComTamanho(int tamanho) {
        return "x".repeat(Math.max(0, tamanho));
    }
}
