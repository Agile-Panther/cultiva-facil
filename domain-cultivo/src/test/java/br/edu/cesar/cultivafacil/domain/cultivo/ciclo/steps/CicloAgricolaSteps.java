package br.edu.cesar.cultivafacil.domain.cultivo.ciclo.steps;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.*;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CicloAgricolaSteps {

    private CicloAgricolaRepositorio repositorio;
    private AberturaCicloServico servico;
    private CicloAgricola ciclo;
    private TalhaoId talhaoId;
    private Exception erro;

    // ===== Abertura =====

    @Dado("que o Talhao esta livre e sem Ciclo ativo")
    public void talhaoLivre() {
        talhaoId = TalhaoId.novo();
        repositorio = mock(CicloAgricolaRepositorio.class);
        when(repositorio.buscarAtivoPorTalhao(talhaoId)).thenReturn(Optional.empty());
        servico = new AberturaCicloServico(repositorio);
    }

    @Quando("o Proprietario vincula a cultura {string} com quantidade {double} e unidade {string}")
    public void vincularCultura(String cultura, double quantidade, String unidade) {
        try {
            ciclo = servico.abrirCiclo(talhaoId, new NomeCultura(cultura),
                    new QuantidadePlantada(quantidade),
                    UnidadeMedidaCiclo.valueOf(unidade));
        } catch (Exception e) {
            erro = e;
        }
    }

    @Entao("o Ciclo Agricola e criado com status ATIVO")
    public void cicloComStatusAtivo() {
        assertNull(erro);
        assertNotNull(ciclo);
        assertEquals(StatusCiclo.ATIVO, ciclo.getStatus());
    }

    @Dado("que o Talhao ja possui um Ciclo ativo de {string}")
    public void talhaoComCicloAtivo(String cultura) {
        talhaoId = TalhaoId.novo();
        repositorio = mock(CicloAgricolaRepositorio.class);
        var cicloExistente = new CicloAgricola(talhaoId, new NomeCultura(cultura),
                new QuantidadePlantada(100.0), UnidadeMedidaCiclo.KG);
        when(repositorio.buscarAtivoPorTalhao(talhaoId)).thenReturn(Optional.of(cicloExistente));
        servico = new AberturaCicloServico(repositorio);
    }

    @Quando("o Proprietario tenta vincular a cultura {string} ao mesmo Talhao")
    public void tentarVincularMesmoTalhao(String cultura) {
        try {
            servico.abrirCiclo(talhaoId, new NomeCultura(cultura),
                    new QuantidadePlantada(50.0), UnidadeMedidaCiclo.KG);
        } catch (Exception e) {
            erro = e;
        }
    }

    // ===== Quantidade =====

    @Quando("o Proprietario tenta criar quantidade plantada com valor {string}")
    public void tentarCriarQuantidade(String valor) {
        try {
            new QuantidadePlantada(Double.parseDouble(valor));
        } catch (Exception e) {
            erro = e;
        }
    }

    @Entao("o sistema rejeita quantidade com erro {string}")
    public void rejeitaQuantidadeComErro(String codigoErro) {
        assertNotNull(erro, "Esperava erro mas nenhum ocorreu");
        assertTrue(erro.getMessage().contains(codigoErro),
                "Esperava codigo " + codigoErro + " mas obteve: " + erro.getMessage());
    }

    // ===== Cancelamento =====

    @Dado("que existe um Ciclo Agricola ativo de {string}")
    public void cicloAtivo(String cultura) {
        talhaoId = TalhaoId.novo();
        ciclo = new CicloAgricola(talhaoId, new NomeCultura(cultura),
                new QuantidadePlantada(100.0), UnidadeMedidaCiclo.KG);
    }

    @Dado("que existe um Ciclo Agricola encerrado de {string}")
    public void cicloEncerrado(String cultura) {
        talhaoId = TalhaoId.novo();
        ciclo = new CicloAgricola(talhaoId, new NomeCultura(cultura),
                new QuantidadePlantada(100.0), UnidadeMedidaCiclo.KG);
        ciclo.encerrar();
    }

    @Quando("o Proprietario cancela o Ciclo com justificativa {string}")
    public void cancelarCiclo(String justificativa) {
        try {
            ciclo.cancelar(new JustificativaCancelamento(justificativa));
        } catch (Exception e) {
            erro = e;
        }
    }

    @Quando("o Proprietario tenta cancelar o Ciclo com justificativa {string}")
    public void tentarCancelarCiclo(String justificativa) {
        try {
            ciclo.cancelar(new JustificativaCancelamento(justificativa));
        } catch (Exception e) {
            erro = e;
        }
    }

    @Entao("o Ciclo Agricola fica com status CANCELADO")
    public void cicloComStatusCancelado() {
        assertNull(erro);
        assertEquals(StatusCiclo.CANCELADO, ciclo.getStatus());
    }

    // ===== Erro genérico =====

    @Entao("o sistema rejeita com erro {string}")
    public void sistemaRejeitaComErro(String codigoErro) {
        assertNotNull(erro, "Esperava erro mas nenhum ocorreu");
        assertTrue(erro.getMessage().contains(codigoErro),
                "Esperava codigo " + codigoErro + " mas obteve: " + erro.getMessage());
    }
}
