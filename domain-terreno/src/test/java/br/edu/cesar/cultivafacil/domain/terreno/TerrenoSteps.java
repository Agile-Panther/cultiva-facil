package br.edu.cesar.cultivafacil.domain.terreno;

import br.edu.cesar.cultivafacil.domain.terreno.terreno.*;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Steps Cucumber para o arquivo terreno.feature (F-04).
 * Implementa todos os cenários das RN-029 a RN-037.
 * Entrega 1 — domain layer apenas, sem repositório real.
 */
public class TerrenoSteps {

    // -------------------------------------------------------------------------
    // Estado compartilhado entre steps de um mesmo cenário
    // -------------------------------------------------------------------------

    private String agricultorId;
    private Terreno terreno;
    private Exception excecaoCapturada;

    // -------------------------------------------------------------------------
    // @Dado
    // -------------------------------------------------------------------------

    @Dado("que o agricultor possui id {string}")
    public void que_o_agricultor_possui_id(String id) {
        this.agricultorId = id;
    }

    @Dado("que existe um terreno cadastrado com nome {string}")
    public void que_existe_um_terreno_cadastrado_com_nome(String nome) {
        this.agricultorId = "550e8400-e29b-41d4-a716-446655440000";
        this.terreno = new Terreno(
                agricultorId,
                new NomeTerreno(nome),
                new AreaTerreno(new BigDecimal("10000")),
                TipoSoloTerreno.LATOSSOLO,
                ClimaRegiaoTerreno.TROPICAL_UMIDO,
                null,
                null
        );
        this.terreno.pullEvents(); // limpa eventos de criação
    }

    @Dado("que existe um terreno cadastrado com area {string} m2")
    public void que_existe_um_terreno_cadastrado_com_area_m2(String area) {
        this.agricultorId = "550e8400-e29b-41d4-a716-446655440000";
        this.terreno = new Terreno(
                agricultorId,
                new NomeTerreno("Fazenda Teste"),
                new AreaTerreno(new BigDecimal(area)),
                TipoSoloTerreno.LATOSSOLO,
                ClimaRegiaoTerreno.TROPICAL_UMIDO,
                null,
                null
        );
        this.terreno.pullEvents();
    }

    @Dado("a soma das areas das zonas do terreno e {string} m2")
    public void a_soma_das_areas_das_zonas_do_terreno_e_m2(String totalZonas) {
        // Armazenado como contexto — usado no step @Quando de atualizarArea
        this.totalAreaZonas = new AreaTerreno(new BigDecimal(totalZonas));
    }

    @Dado("que existe um terreno cadastrado sem cultivo ativo")
    public void que_existe_um_terreno_cadastrado_sem_cultivo_ativo() {
        this.agricultorId = "550e8400-e29b-41d4-a716-446655440000";
        this.terreno = new Terreno(
                agricultorId,
                new NomeTerreno("Fazenda Sem Cultivo"),
                new AreaTerreno(new BigDecimal("5000")),
                TipoSoloTerreno.LATOSSOLO,
                ClimaRegiaoTerreno.TROPICAL_UMIDO,
                null,
                null
        );
        this.terreno.pullEvents();
        this.terreno.marcarSemCultivoAtivo();
    }

    @Dado("que existe um terreno cadastrado com cultivo ativo em uma zona")
    public void que_existe_um_terreno_cadastrado_com_cultivo_ativo_em_uma_zona() {
        this.agricultorId = "550e8400-e29b-41d4-a716-446655440000";
        this.terreno = new Terreno(
                agricultorId,
                new NomeTerreno("Fazenda Com Cultivo"),
                new AreaTerreno(new BigDecimal("5000")),
                TipoSoloTerreno.LATOSSOLO,
                ClimaRegiaoTerreno.TROPICAL_UMIDO,
                null,
                null
        );
        this.terreno.pullEvents();
        this.terreno.marcarComCultivoAtivo();
    }

    // -------------------------------------------------------------------------
    // @Quando — cadastro
    // -------------------------------------------------------------------------

    @Quando("o agricultor cadastra um terreno com nome {string} e area {string} m2 solo {string} e clima {string}")
    public void o_agricultor_cadastra_um_terreno_com_nome_e_area_m2_solo_e_clima(
            String nome, String area, String tipoSolo, String clima) {
        try {
            this.terreno = new Terreno(
                    agricultorId,
                    new NomeTerreno(nome),
                    new AreaTerreno(new BigDecimal(area)),
                    TipoSoloTerreno.valueOf(tipoSolo),
                    ClimaRegiaoTerreno.valueOf(clima),
                    null,
                    null
            );
        } catch (Exception e) {
            this.excecaoCapturada = e;
        }
    }

    @Quando("o agricultor tenta cadastrar um terreno com nome {string} e area {string} m2 solo {string} e clima {string}")
    public void o_agricultor_tenta_cadastrar_um_terreno_com_nome_e_area_m2_solo_e_clima(
            String nome, String area, String tipoSolo, String clima) {
        try {
            this.terreno = new Terreno(
                    agricultorId,
                    new NomeTerreno(nome),
                    new AreaTerreno(new BigDecimal(area)),
                    TipoSoloTerreno.valueOf(tipoSolo),
                    ClimaRegiaoTerreno.valueOf(clima),
                    null,
                    null
            );
        } catch (Exception e) {
            this.excecaoCapturada = e;
        }
    }

    @Quando("o agricultor cadastra um terreno sem informar o pH com nome {string} area {string} solo {string} clima {string}")
    public void o_agricultor_cadastra_um_terreno_sem_informar_o_ph(
            String nome, String area, String tipoSolo, String clima) {
        try {
            this.terreno = new Terreno(
                    agricultorId,
                    new NomeTerreno(nome),
                    new AreaTerreno(new BigDecimal(area)),
                    TipoSoloTerreno.valueOf(tipoSolo),
                    ClimaRegiaoTerreno.valueOf(clima),
                    null, // pH nulo → padrão 6.5 (RN-033)
                    null
            );
        } catch (Exception e) {
            this.excecaoCapturada = e;
        }
    }

    @Quando("o agricultor cadastra um terreno com nome {string} area {string} solo {string} clima {string} e pH {string}")
    public void o_agricultor_cadastra_um_terreno_com_ph_explicito(
            String nome, String area, String tipoSolo, String clima, String ph) {
        try {
            this.terreno = new Terreno(
                    agricultorId,
                    new NomeTerreno(nome),
                    new AreaTerreno(new BigDecimal(area)),
                    TipoSoloTerreno.valueOf(tipoSolo),
                    ClimaRegiaoTerreno.valueOf(clima),
                    new Ph(new BigDecimal(ph)),
                    null
            );
        } catch (Exception e) {
            this.excecaoCapturada = e;
        }
    }

    @Quando("o agricultor tenta cadastrar um terreno com pH {string}")
    public void o_agricultor_tenta_cadastrar_um_terreno_com_ph(String ph) {
        try {
            new Ph(new BigDecimal(ph));
        } catch (Exception e) {
            this.excecaoCapturada = e;
        }
    }

    @Quando("o agricultor cadastra um terreno sem informar o indice de iluminosidade com nome {string} area {string} solo {string} clima {string}")
    public void o_agricultor_cadastra_um_terreno_sem_indice_iluminosidade(
            String nome, String area, String tipoSolo, String clima) {
        try {
            this.terreno = new Terreno(
                    agricultorId,
                    new NomeTerreno(nome),
                    new AreaTerreno(new BigDecimal(area)),
                    TipoSoloTerreno.valueOf(tipoSolo),
                    ClimaRegiaoTerreno.valueOf(clima),
                    null,
                    null // sem índice de iluminosidade (RN-034)
            );
        } catch (Exception e) {
            this.excecaoCapturada = e;
        }
    }

    @Quando("o agricultor cadastra um terreno com indice de iluminosidade {string} horas nome {string} area {string} solo {string} clima {string}")
    public void o_agricultor_cadastra_um_terreno_com_indice_iluminosidade(
            String horas, String nome, String area, String tipoSolo, String clima) {
        try {
            this.terreno = new Terreno(
                    agricultorId,
                    new NomeTerreno(nome),
                    new AreaTerreno(new BigDecimal(area)),
                    TipoSoloTerreno.valueOf(tipoSolo),
                    ClimaRegiaoTerreno.valueOf(clima),
                    null,
                    new IndiceIluminosidade(new BigDecimal(horas))
            );
        } catch (Exception e) {
            this.excecaoCapturada = e;
        }
    }

    @Quando("o agricultor tenta cadastrar um terreno com indice de iluminosidade {string} horas")
    public void o_agricultor_tenta_cadastrar_um_terreno_com_indice_iluminosidade(String horas) {
        try {
            new IndiceIluminosidade(new BigDecimal(horas));
        } catch (Exception e) {
            this.excecaoCapturada = e;
        }
    }

    // -------------------------------------------------------------------------
    // @Quando — edição (RN-035)
    // -------------------------------------------------------------------------

    @Quando("o agricultor atualiza o nome para {string}")
    public void o_agricultor_atualiza_o_nome_para(String novoNome) {
        try {
            this.terreno.atualizarNome(new NomeTerreno(novoNome));
        } catch (Exception e) {
            this.excecaoCapturada = e;
        }
    }

    @Quando("o agricultor tenta atualizar o nome para {string}")
    public void o_agricultor_tenta_atualizar_o_nome_para(String novoNome) {
        try {
            this.terreno.atualizarNome(new NomeTerreno(novoNome));
        } catch (Exception e) {
            this.excecaoCapturada = e;
        }
    }

    @Quando("o agricultor atualiza o pH para {string}")
    public void o_agricultor_atualiza_o_ph_para(String ph) {
        try {
            this.terreno.atualizarPH(new Ph(new BigDecimal(ph)));
        } catch (Exception e) {
            this.excecaoCapturada = e;
        }
    }

    @Quando("o agricultor tenta atualizar o pH para {string}")
    public void o_agricultor_tenta_atualizar_o_ph_para(String ph) {
        try {
            this.terreno.atualizarPH(new Ph(new BigDecimal(ph)));
        } catch (Exception e) {
            this.excecaoCapturada = e;
        }
    }

    // -------------------------------------------------------------------------
    // @Quando — área com zonas (RN-036)
    // -------------------------------------------------------------------------

    @Quando("o agricultor tenta reduzir a area do terreno para {string} m2")
    public void o_agricultor_tenta_reduzir_a_area_do_terreno_para(String novaArea) {
        try {
            this.terreno.atualizarArea(
                    new AreaTerreno(new BigDecimal(novaArea)),
                    this.totalAreaZonas
            );
        } catch (Exception e) {
            this.excecaoCapturada = e;
        }
    }

    @Quando("o agricultor atualiza a area do terreno para {string} m2")
    public void o_agricultor_atualiza_a_area_do_terreno_para(String novaArea) {
        try {
            this.terreno.atualizarArea(
                    new AreaTerreno(new BigDecimal(novaArea)),
                    this.totalAreaZonas
            );
        } catch (Exception e) {
            this.excecaoCapturada = e;
        }
    }

    // -------------------------------------------------------------------------
    // @Quando — exclusão (RN-037)
    // -------------------------------------------------------------------------

    @Quando("o agricultor solicita a exclusao do terreno")
    public void o_agricultor_solicita_a_exclusao_do_terreno() {
        try {
            this.terreno.validarPermissaoExclusao();
        } catch (Exception e) {
            this.excecaoCapturada = e;
        }
    }

    // -------------------------------------------------------------------------
    // @Entao — sucesso
    // -------------------------------------------------------------------------

    @Entao("o terreno deve ser salvo com sucesso")
    public void o_terreno_deve_ser_salvo_com_sucesso() {
        assertNull(excecaoCapturada, "Não era esperada nenhuma exceção, mas ocorreu: " +
                (excecaoCapturada != null ? excecaoCapturada.getMessage() : ""));
        assertNotNull(terreno);
        assertNotNull(terreno.getId());
    }

    @Entao("o terreno deve ser salvo com pH igual a {string}")
    public void o_terreno_deve_ser_salvo_com_ph_igual_a(String phEsperado) {
        assertNull(excecaoCapturada);
        assertNotNull(terreno);
        assertEquals(new BigDecimal(phEsperado), terreno.getPh().getValor());
    }

    @Entao("o terreno deve ser salvo sem indice de iluminosidade")
    public void o_terreno_deve_ser_salvo_sem_indice_de_iluminosidade() {
        assertNull(excecaoCapturada);
        assertNotNull(terreno);
        assertNull(terreno.getIndiceIluminosidade());
    }

    @Entao("o terreno deve ser salvo com indice de iluminosidade {string} horas")
    public void o_terreno_deve_ser_salvo_com_indice_de_iluminosidade(String horas) {
        assertNull(excecaoCapturada);
        assertNotNull(terreno);
        assertNotNull(terreno.getIndiceIluminosidade());
        assertEquals(new BigDecimal(horas), terreno.getIndiceIluminosidade().getHoras());
    }

    @Entao("o terreno deve ter o nome {string}")
    public void o_terreno_deve_ter_o_nome(String nomeEsperado) {
        assertNull(excecaoCapturada);
        assertEquals(nomeEsperado, terreno.getNome().getValor());
    }

    @Entao("o terreno deve ter pH {string}")
    public void o_terreno_deve_ter_ph(String phEsperado) {
        assertNull(excecaoCapturada);
        assertEquals(new BigDecimal(phEsperado), terreno.getPh().getValor());
    }

    @Entao("o terreno deve ter area {string} m2")
    public void o_terreno_deve_ter_area_m2(String areaEsperada) {
        assertNull(excecaoCapturada);
        assertEquals(new BigDecimal(areaEsperada), terreno.getArea().getValorM2());
    }

    @Entao("o terreno deve ser excluido com sucesso")
    public void o_terreno_deve_ser_excluido_com_sucesso() {
        assertNull(excecaoCapturada, "Exclusão não deveria lançar exceção");
    }

    // -------------------------------------------------------------------------
    // @Entao — erro
    // -------------------------------------------------------------------------

    @Entao("o sistema deve rejeitar com erro {string}")
    public void o_sistema_deve_rejeitar_com_erro(String codigoErro) {
        assertNotNull(excecaoCapturada,
                "Era esperada uma exceção para o código '" + codigoErro + "', mas nenhuma foi lançada");
    }

    // -------------------------------------------------------------------------
    // Campo auxiliar para RN-036
    // -------------------------------------------------------------------------

    private AreaTerreno totalAreaZonas;
}