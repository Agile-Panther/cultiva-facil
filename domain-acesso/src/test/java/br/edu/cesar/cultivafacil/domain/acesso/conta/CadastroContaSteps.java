package br.edu.cesar.cultivafacil.domain.acesso.conta;

import br.edu.cesar.cultivafacil.domain.acesso.conta.servico.CadastroContaServico;
import br.edu.cesar.cultivafacil.domain.evento.EventoBarramento;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CadastroContaSteps {

    private ContaRepositorioEmMemoria repositorio;
    private CadastroContaServico servico;

    private String emailPendente;
    private String senhaPendente;
    private boolean consentimentoPendente;

    private Conta contaResultante;
    private IllegalArgumentException erroCapturado;

    @Before
    public void setUp() {
        repositorio = new ContaRepositorioEmMemoria();
        servico = new CadastroContaServico(repositorio, new EventoBarramento());
    }

    @Dado("que o Agricultor informa o email {string} a senha {string} e consentimento verdadeiro")
    public void que_o_agricultor_informa_o_email_a_senha_e_consentimento_verdadeiro(String email, String senha) {
        emailPendente = email;
        senhaPendente = senha;
        consentimentoPendente = true;
    }

    @Dado("que o Agricultor informa o email {string} a senha {string} e consentimento falso")
    public void que_o_agricultor_informa_o_email_a_senha_e_consentimento_falso(String email, String senha) {
        emailPendente = email;
        senhaPendente = senha;
        consentimentoPendente = false;
    }

    @Dado("que o email {string} ja existe na base de dados")
    public void que_o_email_ja_existe_na_base_de_dados(String email) {
        servico.cadastrar(email, "Senha123", true);
    }

    @Quando("submete o formulario de cadastro")
    public void submete_o_formulario_de_cadastro() {
        try {
            contaResultante = servico.cadastrar(emailPendente, senhaPendente, consentimentoPendente);
        } catch (IllegalArgumentException e) {
            erroCapturado = e;
        }
    }

    @Quando("um novo Agricultor tenta se cadastrar com o mesmo email {string} senha {string} e consentimento verdadeiro")
    public void um_novo_agricultor_tenta_se_cadastrar_com_o_mesmo_email_senha_e_consentimento_verdadeiro(String email, String senha) {
        try {
            contaResultante = servico.cadastrar(email, senha, true);
        } catch (IllegalArgumentException e) {
            erroCapturado = e;
        }
    }

    @Entao("a conta e criada com status ativo")
    public void a_conta_e_criada_com_status_ativo() {
        assertNull(erroCapturado, "Nenhum erro era esperado");
        assertNotNull(contaResultante);
        assertTrue(contaResultante.isAtiva());
    }

    @Entao("o sistema rejeita o cadastro com erro {string}")
    public void o_sistema_rejeita_o_cadastro_com_erro(String codigoErro) {
        assertNotNull(erroCapturado, "Era esperado um erro");
        assertEquals(codigoErro, erroCapturado.getMessage());
    }

    @Entao("o sistema rejeita com erro {string}")
    public void o_sistema_rejeita_com_erro(String codigoErro) {
        assertNotNull(erroCapturado, "Era esperado um erro");
        assertEquals(codigoErro, erroCapturado.getMessage());
    }

    private static class ContaRepositorioEmMemoria implements ContaRepositorio {
        private final Map<String, Conta> contas = new HashMap<>();

        @Override
        public void salvar(Conta conta) {
            contas.put(conta.getCredenciais().getEmail().getValor(), conta);
        }

        @Override
        public Optional<Conta> buscarPorEmail(Email email) {
            return Optional.ofNullable(contas.get(email.getValor()));
        }

        @Override
        public Optional<Conta> buscarPorId(ContaId id) {
            return contas.values().stream()
                    .filter(c -> c.getId().equals(id))
                    .findFirst();
        }
    }
}
