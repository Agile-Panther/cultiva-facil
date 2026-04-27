package br.edu.cesar.cultivafacil.domain.acesso.conta;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContaTest {

    private final Email emailValido = new Email("agricultor@fazenda.com");
    private final Senha senhaValida = new Senha("Senha123");

    @Test
    void deveCriarContaComDadosValidos() {
        Conta conta = new Conta(emailValido, senhaValida, true);

        assertNotNull(conta.getId());
        assertTrue(conta.isAtiva());
        assertEquals(emailValido, conta.getCredenciais().getEmail());
    }

    @Test
    void deveCriarContaComStatusAtivo() {
        Conta conta = new Conta(emailValido, senhaValida, true);
        assertTrue(conta.isAtiva());
    }

    @Test
    void deveCriarIdUnicoParaCadaConta() {
        Conta conta1 = new Conta(emailValido, senhaValida, true);
        Conta conta2 = new Conta(new Email("outro@email.com"), senhaValida, true);
        assertNotEquals(conta1.getId(), conta2.getId());
    }

    @Test
    void deveRejeitarCriacaoSemConsentimento() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Conta(emailValido, senhaValida, false));
        assertEquals("CONSENTIMENTO_AUSENTE", ex.getMessage());
    }

    @Test
    void deveRejeitarSenhaIgualAoEmail() {
        Email email = new Email("test1@t.com");
        Senha senhaIgualEmail = new Senha("test1@t.com");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Conta(email, senhaIgualEmail, true));
        assertEquals("SENHA_IGUAL_EMAIL", ex.getMessage());
    }

    @Test
    void deveRejeitarEmailNulo() {
        assertThrows(NullPointerException.class,
                () -> new Conta(null, senhaValida, true));
    }

    @Test
    void deveRejeitarSenhaNula() {
        assertThrows(NullPointerException.class,
                () -> new Conta(emailValido, null, true));
    }

    @Test
    void devePermitirReconstituicao() {
        ContaId id = ContaId.novo();
        Credenciais credenciais = new Credenciais(emailValido, senhaValida, true);
        Conta conta = new Conta(id, credenciais, true);

        assertEquals(id, conta.getId());
        assertTrue(conta.isAtiva());
    }

    @Test
    void devePublicarEventoContaCriada() {
        Conta conta = new Conta(emailValido, senhaValida, true);
        Conta.ContaCriada evento = new Conta.ContaCriada(conta.getId(), emailValido);

        assertNotNull(evento.contaId);
        assertEquals(emailValido, evento.email);
    }
}
