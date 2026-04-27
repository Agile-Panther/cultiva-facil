package br.edu.cesar.cultivafacil.domain.acesso.conta;

import br.edu.cesar.cultivafacil.domain.acesso.conta.servico.CadastroContaServico;
import br.edu.cesar.cultivafacil.domain.evento.EventoBarramento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CadastroContaServicoIntegracaoTest {

    private ContaRepositorioEmMemoria repositorio;
    private EventoBarramento barramento;
    private CadastroContaServico servico;
    private List<Object> eventosCaptados;

    @BeforeEach
    void setUp() {
        repositorio = new ContaRepositorioEmMemoria();
        barramento = new EventoBarramento();
        eventosCaptados = new ArrayList<>();
        barramento.inscrever(eventosCaptados::add);
        servico = new CadastroContaServico(repositorio, barramento);
    }

    @Test
    void fluxoCompleto_ContaCriadaComSucesso() {
        Conta conta = servico.cadastrar("agricultor@fazenda.com", "Senha123", true);

        assertNotNull(conta);
        assertTrue(conta.isAtiva());
        assertNotNull(conta.getId());
        assertEquals("agricultor@fazenda.com", conta.getCredenciais().getEmail().getValor());

        assertTrue(repositorio.buscarPorEmail(new Email("agricultor@fazenda.com")).isPresent());

        assertEquals(1, eventosCaptados.size());
        assertTrue(eventosCaptados.get(0) instanceof Conta.ContaCriada);
        Conta.ContaCriada evento = (Conta.ContaCriada) eventosCaptados.get(0);
        assertEquals(conta.getId(), evento.contaId);
    }

    @Test
    void fluxoNegativo_EmailDuplicadoRejeitado() {
        servico.cadastrar("joao@email.com", "Senha123", true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> servico.cadastrar("joao@email.com", "OutraSenha1", true));

        assertEquals("EMAIL_JA_CADASTRADO", ex.getMessage());
        assertEquals(1, repositorio.totalContas());
    }

    @Test
    void fluxoNegativo_SenhaCurtaRejeitada() {
        assertThrows(IllegalArgumentException.class,
                () -> servico.cadastrar("novo@email.com", "abc12", true));

        assertEquals(0, repositorio.totalContas());
    }

    @Test
    void fluxoNegativo_SenhaSemNumeroRejeitada() {
        assertThrows(IllegalArgumentException.class,
                () -> servico.cadastrar("novo@email.com", "abcdefgh", true));

        assertEquals(0, repositorio.totalContas());
    }

    @Test
    void fluxoNegativo_SenhaIgualEmailRejeitada() {
        assertThrows(IllegalArgumentException.class,
                () -> servico.cadastrar("abc@abc.com", "abc@abc.com", true));

        assertEquals(0, repositorio.totalContas());
    }

    @Test
    void fluxoNegativo_SemConsentimentoRejeitado() {
        assertThrows(IllegalArgumentException.class,
                () -> servico.cadastrar("novo@email.com", "Senha123", false));

        assertEquals(0, repositorio.totalContas());
    }

    @Test
    void multiplosAgricultoresComEmailsDiferentes() {
        servico.cadastrar("agricultor1@fazenda.com", "Senha123", true);
        servico.cadastrar("agricultor2@fazenda.com", "Senha456", true);
        servico.cadastrar("agricultor3@fazenda.com", "Senha789", true);

        assertEquals(3, repositorio.totalContas());
        assertEquals(3, eventosCaptados.size());
    }

    @Test
    void emailCaseInsensitivo_DuplicadoRejeitado() {
        servico.cadastrar("joao@email.com", "Senha123", true);

        assertThrows(IllegalArgumentException.class,
                () -> servico.cadastrar("JOAO@EMAIL.COM", "Senha456", true));

        assertEquals(1, repositorio.totalContas());
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

        public int totalContas() {
            return contas.size();
        }
    }
}
