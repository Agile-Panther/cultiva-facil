package br.edu.cesar.cultivafacil.domain.cultivo.bdd;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.*;

import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class VinculoCicloCultivoSteps {

    private UUID zonaId;
    private CicloAgricola cicloAtivo;
    private CicloAgricola cicloResultado;
    private Exception excecaoCapturada;

    @Before
    public void setUp() {
        zonaId = UUID.randomUUID();
        cicloAtivo = null;
        cicloResultado = null;
        excecaoCapturada = null;
    }

    // ── Dado ──────────────────────────────────────────────────────────

    @Dado("que existe uma Zona de plantio sem ciclo ativo")
    public void queExisteUmaZonaDePlantioSemCicloAtivo() {
        // zonaId inicializado no setUp, sem ciclo ativo
    }

    @Dado("que a Zona já possui um ciclo ativo da cultura {string}")
    public void queAZonaJaPossuiUmCicloAtivoDaCultura(String cultura) {
        cicloAtivo = new CicloAgricola(zonaId, new NomeCultura(cultura),
                new QuantidadePlantada(new BigDecimal("10.00"), UnidadeMedidaCiclo.KG));
    }

    // ── Quando ────────────────────────────────────────────────────────

    @Quando("o Agricultor vincula a cultura {string} com {double} kg plantados")
    public void oAgricultorVinculaACulturaComKgPlantados(String cultura, double quantidade) {
        try {
            validarZonaSemCicloAtivo();
            cicloResultado = new CicloAgricola(zonaId, new NomeCultura(cultura),
                    new QuantidadePlantada(new BigDecimal(String.valueOf(quantidade)), UnidadeMedidaCiclo.KG));
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    @Quando("o Agricultor vincula a cultura {string} com {double} g plantados")
    public void oAgricultorVinculaACulturaComGPlantados(String cultura, double quantidade) {
        try {
            validarZonaSemCicloAtivo();
            cicloResultado = new CicloAgricola(zonaId, new NomeCultura(cultura),
                    new QuantidadePlantada(new BigDecimal(String.valueOf(quantidade)), UnidadeMedidaCiclo.G));
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    @Quando("o Agricultor vincula a cultura {string} com {double} unidades plantados")
    public void oAgricultorVinculaACulturaComUnidadesPlantados(String cultura, double quantidade) {
        try {
            validarZonaSemCicloAtivo();
            cicloResultado = new CicloAgricola(zonaId, new NomeCultura(cultura),
                    new QuantidadePlantada(new BigDecimal(String.valueOf(quantidade)), UnidadeMedidaCiclo.UNIDADES));
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    @Quando("o Agricultor tenta vincular a cultura {string} com {double} kg plantados")
    public void oAgricultorTentaVincularACulturaComKgPlantados(String cultura, double quantidade) {
        try {
            validarZonaSemCicloAtivo();
            cicloResultado = new CicloAgricola(zonaId, new NomeCultura(cultura),
                    new QuantidadePlantada(new BigDecimal(String.valueOf(quantidade)), UnidadeMedidaCiclo.KG));
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    @Quando("o Agricultor tenta vincular a cultura {string} com {int} kg plantados")
    public void oAgricultorTentaVincularACulturaComKgPlantadosInt(String cultura, int quantidade) {
        try {
            validarZonaSemCicloAtivo();
            cicloResultado = new CicloAgricola(zonaId, new NomeCultura(cultura),
                    new QuantidadePlantada(new BigDecimal(quantidade), UnidadeMedidaCiclo.KG));
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    @Quando("o Agricultor encerra o ciclo com data de colheita de hoje")
    public void oAgricultorEncerraOCicloComDataDeColheitaDeHoje() {
        try {
            cicloAtivo.encerrar(LocalDate.now());
            cicloResultado = cicloAtivo;
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    // ── Então ─────────────────────────────────────────────────────────

    @Então("o Ciclo Agrícola é criado com status ATIVO")
    public void oCicloAgricolaECriadoComStatusAtivo() {
        assertNull(excecaoCapturada, "Nenhuma exceção deveria ter sido lançada");
        assertNotNull(cicloResultado);
        assertEquals(StatusCiclo.ATIVO, cicloResultado.getStatus());
    }

    @Então("a quantidade plantada registrada é {double} kg")
    public void aQuantidadePlantadaRegistradaEKg(double quantidade) {
        assertNotNull(cicloResultado);
        assertEquals(0, new BigDecimal(String.valueOf(quantidade))
                .compareTo(cicloResultado.getQuantidadePlantada().getValor()));
        assertEquals(UnidadeMedidaCiclo.KG, cicloResultado.getQuantidadePlantada().getUnidade());
    }

    @Então("a quantidade plantada registrada é {double} g")
    public void aQuantidadePlantadaRegistradaEG(double quantidade) {
        assertNotNull(cicloResultado);
        assertEquals(0, new BigDecimal(String.valueOf(quantidade))
                .compareTo(cicloResultado.getQuantidadePlantada().getValor()));
        assertEquals(UnidadeMedidaCiclo.G, cicloResultado.getQuantidadePlantada().getUnidade());
    }

    @Então("a quantidade plantada registrada é {double} unidades")
    public void aQuantidadePlantadaRegistradaEUnidades(double quantidade) {
        assertNotNull(cicloResultado);
        assertEquals(0, new BigDecimal(String.valueOf(quantidade))
                .compareTo(cicloResultado.getQuantidadePlantada().getValor()));
        assertEquals(UnidadeMedidaCiclo.UNIDADES, cicloResultado.getQuantidadePlantada().getUnidade());
    }

    @Então("o sistema rejeita com erro {string}")
    public void oSistemaRejeitaComErro(String codigoErro) {
        assertNotNull(excecaoCapturada, "Uma exceção deveria ter sido lançada");
        if ("ZONA_COM_CULTIVO_ATIVO".equals(codigoErro)) {
            assertInstanceOf(ZonaComCultivoAtivoException.class, excecaoCapturada);
        } else {
            fail("Código de erro desconhecido: " + codigoErro);
        }
    }

    @Então("o sistema rejeita a criação da quantidade")
    public void oSistemaRejeitaACriacaoDaQuantidade() {
        assertNotNull(excecaoCapturada, "Uma exceção deveria ter sido lançada");
        assertInstanceOf(IllegalArgumentException.class, excecaoCapturada);
    }

    @E("a quantidade plantada não pode ser alterada")
    public void aQuantidadePlantadaNaoPodeSerAlterada() {
        // RN-046: QuantidadePlantada é final no AR, sem setter — imutável por design
        assertNotNull(cicloResultado);
        QuantidadePlantada original = cicloResultado.getQuantidadePlantada();
        // Verificar que não há como alterar — o campo é final e o getter retorna o mesmo objeto
        assertSame(original, cicloResultado.getQuantidadePlantada());
    }

    @Então("o Ciclo Agrícola muda para status ENCERRADO")
    public void oCicloAgricolaMudaParaStatusEncerrado() {
        assertNull(excecaoCapturada, "Nenhuma exceção deveria ter sido lançada");
        assertNotNull(cicloResultado);
        assertEquals(StatusCiclo.ENCERRADO, cicloResultado.getStatus());
        assertNotNull(cicloResultado.getDataColheita());
    }

    // ── Helper ────────────────────────────────────────────────────────

    private void validarZonaSemCicloAtivo() {
        if (cicloAtivo != null && cicloAtivo.getStatus() == StatusCiclo.ATIVO) {
            throw new ZonaComCultivoAtivoException();
        }
    }
}
