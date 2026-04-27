package br.edu.cesar.cultivafacil.domain.terreno.terreno;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para o Aggregate Root Terreno.
 * Cobre invariantes RN-029 a RN-037 conforme documento de modelagem.
 * O AR não chama repositório — nenhum mock é necessário aqui.
 */
class TerrenoTest {

    // -------------------------------------------------------------------------
    // Helpers de fixture
    // -------------------------------------------------------------------------

    private static final String AGRICULTOR_ID = "550e8400-e29b-41d4-a716-446655440000";

    private NomeTerreno nomeValido() {
        return new NomeTerreno("Fazenda Boa Vista");
    }

    private AreaTerreno areaValida() {
        return new AreaTerreno(new BigDecimal("10000"));
    }

    private Terreno terrenoPadrao() {
        return new Terreno(
                AGRICULTOR_ID,
                nomeValido(),
                areaValida(),
                TipoSoloTerreno.LATOSSOLO,
                ClimaRegiaoTerreno.TROPICAL_UMIDO,
                null,  // ph nulo → deve usar padrão 6.5 (RN-033)
                null   // indiceIluminosidade opcional
        );
    }

    // -------------------------------------------------------------------------
    // Criação — campos obrigatórios e defaults
    // -------------------------------------------------------------------------

    @Test
    void deveCriarTerrenoComCamposObrigatorios() {
        var terreno = terrenoPadrao();
        assertNotNull(terreno.getId());
        assertEquals(AGRICULTOR_ID, terreno.getAgricultorId());
        assertEquals("Fazenda Boa Vista", terreno.getNome().getValor());
        assertEquals(new BigDecimal("10000"), terreno.getArea().getValorM2());
        assertEquals(TipoSoloTerreno.LATOSSOLO, terreno.getTipoSolo());
        assertEquals(ClimaRegiaoTerreno.TROPICAL_UMIDO, terreno.getClimaRegiao());
    }

    @Test
    void deveCriarTerrenoComPhExplicito() {
        var ph = new Ph(new BigDecimal("5.5"));
        var terreno = new Terreno(
                AGRICULTOR_ID,
                nomeValido(),
                areaValida(),
                TipoSoloTerreno.ARGISSOLO,
                ClimaRegiaoTerreno.SEMIARIDO,
                ph,
                null
        );
        assertEquals(new BigDecimal("5.5"), terreno.getPh().getValor());
    }

    // -------------------------------------------------------------------------
    // RN-033: pH padrão 6.5 quando null
    // -------------------------------------------------------------------------

    @Test
    void deveUsarPhPadraoQuandoPhNuloForFornecido() {
        var terreno = terrenoPadrao();
        assertEquals(Ph.DEFAULT_VALUE, terreno.getPh().getValor());
    }

    // -------------------------------------------------------------------------
    // RN-034: IndiceIluminosidade opcional
    // -------------------------------------------------------------------------

    @Test
    void deveAceitarIndiceIluminosidadeNulo() {
        var terreno = terrenoPadrao();
        assertNull(terreno.getIndiceIluminosidade());
    }

    @Test
    void deveCriarTerrenoComIndiceIluminosidadePreenchido() {
        var indice = new IndiceIluminosidade(new BigDecimal("8"));
        var terreno = new Terreno(
                AGRICULTOR_ID,
                nomeValido(),
                areaValida(),
                TipoSoloTerreno.LATOSSOLO,
                ClimaRegiaoTerreno.TROPICAL_UMIDO,
                null,
                indice
        );
        assertNotNull(terreno.getIndiceIluminosidade());
        assertEquals(new BigDecimal("8"), terreno.getIndiceIluminosidade().getHoras());
    }

    // -------------------------------------------------------------------------
    // Criação — campos obrigatórios nulos devem lançar exceção
    // -------------------------------------------------------------------------

    @Test
    void deveRejeitarAgricultorIdNulo() {
        assertThrows(NullPointerException.class,
                () -> new Terreno(null, nomeValido(), areaValida(),
                        TipoSoloTerreno.LATOSSOLO, ClimaRegiaoTerreno.TROPICAL_UMIDO, null, null));
    }

    @Test
    void deveRejeitarNomeNulo() {
        assertThrows(NullPointerException.class,
                () -> new Terreno(AGRICULTOR_ID, null, areaValida(),
                        TipoSoloTerreno.LATOSSOLO, ClimaRegiaoTerreno.TROPICAL_UMIDO, null, null));
    }

    @Test
    void deveRejeitarAreaNula() {
        assertThrows(NullPointerException.class,
                () -> new Terreno(AGRICULTOR_ID, nomeValido(), null,
                        TipoSoloTerreno.LATOSSOLO, ClimaRegiaoTerreno.TROPICAL_UMIDO, null, null));
    }

    @Test
    void deveRejeitarTipoSoloNulo() {
        assertThrows(NullPointerException.class,
                () -> new Terreno(AGRICULTOR_ID, nomeValido(), areaValida(),
                        null, ClimaRegiaoTerreno.TROPICAL_UMIDO, null, null));
    }

    @Test
    void deveRejeitarClimaRegiaoNulo() {
        assertThrows(NullPointerException.class,
                () -> new Terreno(AGRICULTOR_ID, nomeValido(), areaValida(),
                        TipoSoloTerreno.LATOSSOLO, null, null, null));
    }

    // -------------------------------------------------------------------------
    // Domain Event — TerrenoCriado publicado na criação
    // -------------------------------------------------------------------------

    @Test
    void devePublicarEventoTerrenoCriadoAoCriar() {
        var terreno = terrenoPadrao();
        List<Object> eventos = terreno.pullEvents();
        assertEquals(1, eventos.size());
        assertInstanceOf(Terreno.TerrenoCriado.class, eventos.get(0));
    }

    @Test
    void eventoTerrenoCriadoDeveConterIdETerrenoId() {
        var terreno = terrenoPadrao();
        List<Object> eventos = terreno.pullEvents();
        var evento = (Terreno.TerrenoCriado) eventos.get(0);
        assertEquals(terreno.getId(), evento.terrenoId);
        assertEquals(AGRICULTOR_ID, evento.agricultorId);
    }

    @Test
    void pullEvents_deveLimparListaDeEventosAposChamada() {
        var terreno = terrenoPadrao();
        terreno.pullEvents();
        assertTrue(terreno.pullEvents().isEmpty());
    }

    // -------------------------------------------------------------------------
    // RN-035 — atualizações: mesmas regras de validação se aplicam
    // -------------------------------------------------------------------------

    @Test
    void deveAtualizarNomeComValorValido() {
        var terreno = terrenoPadrao();
        terreno.atualizarNome(new NomeTerreno("Sítio Recanto"));
        assertEquals("Sítio Recanto", terreno.getNome().getValor());
    }

    @Test
    void deveRejeitarAtualizacaoDeNomeComNulo() {
        var terreno = terrenoPadrao();
        assertThrows(NullPointerException.class,
                () -> terreno.atualizarNome(null));
    }

    @Test
    void deveAtualizarTipoSolo() {
        var terreno = terrenoPadrao();
        terreno.atualizarTipoSolo(TipoSoloTerreno.GLEISSOLO);
        assertEquals(TipoSoloTerreno.GLEISSOLO, terreno.getTipoSolo());
    }

    @Test
    void deveRejeitarAtualizacaoDeTipoSoloComNulo() {
        var terreno = terrenoPadrao();
        assertThrows(NullPointerException.class,
                () -> terreno.atualizarTipoSolo(null));
    }

    @Test
    void deveAtualizarClimaRegiao() {
        var terreno = terrenoPadrao();
        terreno.atualizarClimaRegiao(ClimaRegiaoTerreno.SEMIARIDO);
        assertEquals(ClimaRegiaoTerreno.SEMIARIDO, terreno.getClimaRegiao());
    }

    @Test
    void deveRejeitarAtualizacaoDeClimaRegiaoComNulo() {
        var terreno = terrenoPadrao();
        assertThrows(NullPointerException.class,
                () -> terreno.atualizarClimaRegiao(null));
    }

    @Test
    void deveAtualizarPH() {
        var terreno = terrenoPadrao();
        terreno.atualizarPH(new Ph(new BigDecimal("7.0")));
        assertEquals(new BigDecimal("7.0"), terreno.getPh().getValor());
    }

    @Test
    void deveRejeitarAtualizacaoDePHComNulo() {
        var terreno = terrenoPadrao();
        assertThrows(NullPointerException.class,
                () -> terreno.atualizarPH(null));
    }

    @Test
    void deveAtualizarIndiceIluminosidade() {
        var terreno = terrenoPadrao();
        terreno.atualizarIndiceIluminosidade(new IndiceIluminosidade(new BigDecimal("12")));
        assertEquals(new BigDecimal("12"), terreno.getIndiceIluminosidade().getHoras());
    }

    @Test
    void devePermitirLimparIndiceIluminosidadeComNulo() {
        var terreno = new Terreno(
                AGRICULTOR_ID,
                nomeValido(),
                areaValida(),
                TipoSoloTerreno.LATOSSOLO,
                ClimaRegiaoTerreno.TROPICAL_UMIDO,
                null,
                new IndiceIluminosidade(new BigDecimal("8"))
        );
        terreno.atualizarIndiceIluminosidade(null);
        assertNull(terreno.getIndiceIluminosidade());
    }

    // -------------------------------------------------------------------------
    // RN-036 — área não pode ser reduzida abaixo da soma das zonas
    // -------------------------------------------------------------------------

    @Test
    void deveAtualizarAreaQuandoNovaAreaMaiorQueAreaDasZonas() {
        var terreno = terrenoPadrao();
        var novaArea = new AreaTerreno(new BigDecimal("20000"));
        var totalZonas = new AreaTerreno(new BigDecimal("5000"));
        terreno.atualizarArea(novaArea, totalZonas);
        assertEquals(new BigDecimal("20000"), terreno.getArea().getValorM2());
    }

    @Test
    void deveAtualizarAreaQuandoNovaAreaIgualAreaDasZonas() {
        var terreno = terrenoPadrao();
        var novaArea = new AreaTerreno(new BigDecimal("5000"));
        var totalZonas = new AreaTerreno(new BigDecimal("5000"));
        terreno.atualizarArea(novaArea, totalZonas);
        assertEquals(new BigDecimal("5000"), terreno.getArea().getValorM2());
    }

    @Test
    void deveRejeitarReducaoDeAreaAbaixoDaSomaDasZonas() {
        var terreno = terrenoPadrao();
        var novaAreaMenor = new AreaTerreno(new BigDecimal("500"));
        var totalZonas = new AreaTerreno(new BigDecimal("1000"));
        assertThrows(IllegalArgumentException.class,
                () -> terreno.atualizarArea(novaAreaMenor, totalZonas));
    }

    @Test
    void deveRejeitarAtualizacaoDeAreaComNovaAreaNula() {
        var terreno = terrenoPadrao();
        assertThrows(NullPointerException.class,
                () -> terreno.atualizarArea(null, areaValida()));
    }

    @Test
    void deveRejeitarAtualizacaoDeAreaComTotalZonasNulo() {
        var terreno = terrenoPadrao();
        assertThrows(NullPointerException.class,
                () -> terreno.atualizarArea(areaValida(), null));
    }

    // -------------------------------------------------------------------------
    // RN-037 — exclusão bloqueada se há cultivo ativo
    // -------------------------------------------------------------------------

    @Test
    void devePermitirExclusaoQuandoNaoHaCultivoAtivo() {
        var terreno = terrenoPadrao();
        terreno.pullEvents(); // limpa eventos de criação
        assertDoesNotThrow(() -> terreno.validarPermissaoExclusao());
    }

    @Test
    void deveBloqueiaExclusaoQuandoHaCultivoAtivo() {
        var terreno = terrenoPadrao();
        terreno.marcarComCultivoAtivo();
        assertThrows(IllegalStateException.class,
                () -> terreno.validarPermissaoExclusao());
    }

    @Test
    void devePermitirExclusaoAposDesmarcarCultivoAtivo() {
        var terreno = terrenoPadrao();
        terreno.marcarComCultivoAtivo();
        terreno.marcarSemCultivoAtivo();
        assertDoesNotThrow(() -> terreno.validarPermissaoExclusao());
    }

    @Test
    void marcarComCultivoAtivo_deveAtualizarFlag() {
        var terreno = terrenoPadrao();
        assertFalse(terreno.isPossuiCultivoAtivo());
        terreno.marcarComCultivoAtivo();
        assertTrue(terreno.isPossuiCultivoAtivo());
    }

    @Test
    void marcarSemCultivoAtivo_deveAtualizarFlag() {
        var terreno = terrenoPadrao();
        terreno.marcarComCultivoAtivo();
        terreno.marcarSemCultivoAtivo();
        assertFalse(terreno.isPossuiCultivoAtivo());
    }

    // -------------------------------------------------------------------------
    // Reconstituição (construtor de persistência)
    // -------------------------------------------------------------------------

    @Test
    void deveReconstituirTerrenoComTodosOsCampos() {
        TerrenoId id = TerrenoId.novo();
        var nome = new NomeTerreno("Fazenda Reconstituída");
        var area = new AreaTerreno(new BigDecimal("8000"));
        var ph = new Ph(new BigDecimal("6.0"));
        var indice = new IndiceIluminosidade(new BigDecimal("10"));

        var terreno = new Terreno(
                id, AGRICULTOR_ID, nome, area,
                TipoSoloTerreno.NITOSSOLO, ClimaRegiaoTerreno.SUBTROPICAL_UMIDO,
                ph, indice, true
        );

        assertEquals(id, terreno.getId());
        assertEquals("Fazenda Reconstituída", terreno.getNome().getValor());
        assertEquals(new BigDecimal("8000"), terreno.getArea().getValorM2());
        assertEquals(TipoSoloTerreno.NITOSSOLO, terreno.getTipoSolo());
        assertEquals(ClimaRegiaoTerreno.SUBTROPICAL_UMIDO, terreno.getClimaRegiao());
        assertEquals(new BigDecimal("6.0"), terreno.getPh().getValor());
        assertEquals(new BigDecimal("10"), terreno.getIndiceIluminosidade().getHoras());
        assertTrue(terreno.isPossuiCultivoAtivo());
    }

    @Test
    void reconstituicao_naoDevePublicarEventos() {
        TerrenoId id = TerrenoId.novo();
        var terreno = new Terreno(
                id, AGRICULTOR_ID, nomeValido(), areaValida(),
                TipoSoloTerreno.LATOSSOLO, ClimaRegiaoTerreno.TROPICAL_UMIDO,
                Ph.padrao(), null, false
        );
        assertTrue(terreno.pullEvents().isEmpty());
    }

    @Test
    void reconstituicao_deveRejeitarIdNulo() {
        assertThrows(NullPointerException.class,
                () -> new Terreno(
                        null, AGRICULTOR_ID, nomeValido(), areaValida(),
                        TipoSoloTerreno.LATOSSOLO, ClimaRegiaoTerreno.TROPICAL_UMIDO,
                        Ph.padrao(), null, false
                ));
    }

    @Test
    void reconstituicao_deveRejeitarPhNulo() {
        assertThrows(NullPointerException.class,
                () -> new Terreno(
                        TerrenoId.novo(), AGRICULTOR_ID, nomeValido(), areaValida(),
                        TipoSoloTerreno.LATOSSOLO, ClimaRegiaoTerreno.TROPICAL_UMIDO,
                        null, null, false
                ));
    }
}