package br.edu.cesar.cultivafacil.domain.agenda.tarefa;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.CicloAgricolaId;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class F09Steps {

    private Tarefa tarefa;
    private LocalDate hoje;
    private LocalDate inicioCiclo;
    private LocalDate fimCiclo;
    private boolean cicloAtivo;
    private Exception exceptionCapturada;

    // ─── US-17: Editar ───────────────────────────────────────────────────────

    @Dado("que existe uma Tarefa {string} no ciclo ativo que inicia em {string} e encerra em {string}")
    public void que_existe_tarefa_nomeada_no_ciclo(String nome, String inicio, String fim) {
        inicioCiclo = LocalDate.parse(inicio);
        fimCiclo = LocalDate.parse(fim);
        tarefa = new Tarefa(TarefaId.novo(), new NomeTarefa(nome),
                new DataTarefa(inicioCiclo), TalhaoId.novo(), CicloAgricolaId.novo());
    }

    @Dado("que existe uma Tarefa no ciclo ativo que inicia em {string} e encerra em {string}")
    public void que_existe_tarefa_no_ciclo(String inicio, String fim) {
        inicioCiclo = LocalDate.parse(inicio);
        fimCiclo = LocalDate.parse(fim);
        tarefa = new Tarefa(TarefaId.novo(), new NomeTarefa("Irrigação"),
                new DataTarefa(inicioCiclo), TalhaoId.novo(), CicloAgricolaId.novo());
    }

    @Quando("o Agricultor edita o nome para {string} e a data para {string}")
    public void o_agricultor_edita_nome_e_data(String novoNome, String novaData) {
        tarefa.editar(new NomeTarefa(novoNome), new DataTarefa(LocalDate.parse(novaData)), inicioCiclo, fimCiclo);
    }

    @Então("a Tarefa é atualizada com o novo nome e a nova data dentro do intervalo do ciclo")
    public void a_tarefa_e_atualizada() {
        assertNull(exceptionCapturada);
        assertNotNull(tarefa.getNome());
        assertNotNull(tarefa.getData());
    }

    @Quando("o Agricultor tenta editar o nome da Tarefa para {string} \\({int} caractere, abaixo do mínimo de {int})")
    public void o_agricultor_tenta_editar_nome_curto(String nome, Integer chars, Integer minimo) {
        exceptionCapturada = null;
        try {
            tarefa.editar(new NomeTarefa(nome), tarefa.getData(), inicioCiclo, fimCiclo);
        } catch (Exception e) {
            exceptionCapturada = e;
        }
    }

    @Quando("o Agricultor tenta editar o nome para {string}")
    public void o_agricultor_tenta_editar_nome(String nome) {
        exceptionCapturada = null;
        try {
            tarefa.editar(new NomeTarefa(nome), tarefa.getData(), inicioCiclo, fimCiclo);
        } catch (Exception e) {
            exceptionCapturada = e;
        }
    }

    @Quando("o Agricultor tenta mover a Tarefa para a data {string} \\(após o encerramento do ciclo)")
    public void o_agricultor_tenta_mover_data_apos_ciclo(String data) {
        exceptionCapturada = null;
        try {
            tarefa.editar(tarefa.getNome(), new DataTarefa(LocalDate.parse(data)), inicioCiclo, fimCiclo);
        } catch (Exception e) {
            exceptionCapturada = e;
        }
    }

    // ─── US-18: Criar manual ─────────────────────────────────────────────────

    @Dado("que hoje é {string} e o ciclo ativo encerra em {string}")
    public void que_hoje_e_ciclo_encerra(String dataHoje, String dataFim) {
        hoje = LocalDate.parse(dataHoje);
        fimCiclo = LocalDate.parse(dataFim);
    }

    @Quando("o Agricultor cria a Tarefa manual {string} com data {string}")
    public void o_agricultor_cria_tarefa_manual(String nome, String data) {
        tarefa = new Tarefa(new NomeTarefa(nome), new DataTarefa(LocalDate.parse(data)),
                TalhaoId.novo(), CicloAgricolaId.novo(), hoje, fimCiclo);
    }

    @Então("a Tarefa manual é registrada no calendário do ciclo ativo")
    public void a_tarefa_manual_e_registrada() {
        assertNull(exceptionCapturada);
        assertNotNull(tarefa);
        assertNotNull(tarefa.getId());
        assertNotNull(tarefa.getEventoCriacao());
    }

    @Quando("o Agricultor informa a data {string} \\(anterior à data atual) para a Tarefa manual")
    public void o_agricultor_informa_data_passada(String data) {
        exceptionCapturada = null;
        try {
            tarefa = new Tarefa(new NomeTarefa("Tarefa Teste"), new DataTarefa(LocalDate.parse(data)),
                    TalhaoId.novo(), CicloAgricolaId.novo(), hoje, fimCiclo);
        } catch (Exception e) {
            exceptionCapturada = e;
        }
    }

    @Quando("o Agricultor informa a data {string} \\(após o encerramento do ciclo) para a Tarefa manual")
    public void o_agricultor_informa_data_apos_ciclo(String data) {
        exceptionCapturada = null;
        try {
            tarefa = new Tarefa(new NomeTarefa("Tarefa Teste"), new DataTarefa(LocalDate.parse(data)),
                    TalhaoId.novo(), CicloAgricolaId.novo(), hoje, fimCiclo);
        } catch (Exception e) {
            exceptionCapturada = e;
        }
    }

    @Quando("o Agricultor tenta criar uma Tarefa manual com nome vazio")
    public void o_agricultor_tenta_criar_com_nome_vazio() {
        exceptionCapturada = null;
        try {
            tarefa = new Tarefa(new NomeTarefa(""), new DataTarefa(hoje.plusDays(1)),
                    TalhaoId.novo(), CicloAgricolaId.novo(), hoje, fimCiclo);
        } catch (Exception e) {
            exceptionCapturada = e;
        }
    }

    // ─── US-19: Excluir ──────────────────────────────────────────────────────

    @Dado("que a Tarefa {string} pertence ao ciclo ativo da Zona")
    public void que_tarefa_pertence_ao_ciclo_ativo(String nome) {
        cicloAtivo = true;
        tarefa = new Tarefa(TarefaId.novo(), new NomeTarefa(nome),
                new DataTarefa(LocalDate.of(2024, 4, 1)), TalhaoId.novo(), CicloAgricolaId.novo());
    }

    @Quando("o Agricultor solicita a exclusão da Tarefa")
    public void o_agricultor_solicita_exclusao() {
        exceptionCapturada = null;
        try {
            tarefa.validarExclusao(cicloAtivo);
        } catch (Exception e) {
            exceptionCapturada = e;
        }
    }

    @Então("a Tarefa é removida do calendário do ciclo ativo")
    public void a_tarefa_e_removida() {
        assertNull(exceptionCapturada);
    }

    @Dado("que a Tarefa {string} pertence a um ciclo já encerrado da Zona")
    public void que_tarefa_pertence_a_ciclo_encerrado(String nome) {
        cicloAtivo = false;
        tarefa = new Tarefa(TarefaId.novo(), new NomeTarefa(nome),
                new DataTarefa(LocalDate.of(2024, 4, 1)), TalhaoId.novo(), CicloAgricolaId.novo());
    }

    @Quando("o Agricultor tenta excluir a Tarefa")
    public void o_agricultor_tenta_excluir() {
        exceptionCapturada = null;
        try {
            tarefa.validarExclusao(cicloAtivo);
        } catch (Exception e) {
            exceptionCapturada = e;
        }
    }

    // ─── Shared ──────────────────────────────────────────────────────────────

    @Então("o sistema rejeita com erro {string}")
    public void o_sistema_rejeita_com_erro(String erro) {
        assertNotNull(exceptionCapturada, "Esperava uma exceção com mensagem: " + erro);
        assertEquals(erro, exceptionCapturada.getMessage());
    }

    @Então("o sistema rejeita com erro {string} pois Tarefas de ciclos encerrados são imutáveis")
    public void o_sistema_rejeita_ciclo_encerrado(String erro) {
        assertNotNull(exceptionCapturada, "Esperava uma exceção com mensagem: " + erro);
        assertEquals(erro, exceptionCapturada.getMessage());
    }
}
