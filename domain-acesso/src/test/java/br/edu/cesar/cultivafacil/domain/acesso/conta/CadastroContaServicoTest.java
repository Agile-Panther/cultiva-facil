package br.edu.cesar.cultivafacil.domain.acesso.conta;

import br.edu.cesar.cultivafacil.domain.acesso.conta.servico.CadastroContaServico;
import br.edu.cesar.cultivafacil.domain.evento.EventoBarramento;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadastroContaServicoTest {

    @Mock
    private ContaRepositorio repositorio;

    @Mock
    private EventoBarramento barramento;

    @InjectMocks
    private CadastroContaServico servico;

    @Test
    void deveCadastrarContaComSucesso() {
        when(repositorio.buscarPorEmail(any(Email.class))).thenReturn(Optional.empty());

        Conta conta = servico.cadastrar("novo@email.com", "Senha123", true);

        assertNotNull(conta);
        assertTrue(conta.isAtiva());
        verify(repositorio, times(1)).salvar(any(Conta.class));
        verify(barramento, times(1)).publicar(any(Conta.ContaCriada.class));
    }

    @Test
    void deveRejeitarEmailDuplicado() {
        Email emailExistente = new Email("joao@email.com");
        Conta contaExistente = new Conta(emailExistente, new Senha("Senha123"), true);
        when(repositorio.buscarPorEmail(any(Email.class))).thenReturn(Optional.of(contaExistente));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> servico.cadastrar("joao@email.com", "Senha123", true));

        assertEquals("EMAIL_JA_CADASTRADO", ex.getMessage());
        verify(repositorio, never()).salvar(any(Conta.class));
        verify(barramento, never()).publicar(any());
    }

    @Test
    void naoDeveSalvarQuandoSenhaInvalida() {
        when(repositorio.buscarPorEmail(any(Email.class))).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> servico.cadastrar("novo@email.com", "abc12", true));

        verify(repositorio, never()).salvar(any(Conta.class));
    }

    @Test
    void naoDeveSalvarQuandoConsentimentoFalso() {
        when(repositorio.buscarPorEmail(any(Email.class))).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> servico.cadastrar("novo@email.com", "Senha123", false));

        verify(repositorio, never()).salvar(any(Conta.class));
    }

    @Test
    void naoDeveSalvarQuandoSenhaIgualAoEmail() {
        when(repositorio.buscarPorEmail(any(Email.class))).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> servico.cadastrar("abc@abc.com", "abc@abc.com", true));

        assertEquals("SENHA_IGUAL_EMAIL", ex.getMessage());
        verify(repositorio, never()).salvar(any(Conta.class));
    }

    @Test
    void deveVerificarEmailAntesDeValidarSenha() {
        Email emailExistente = new Email("dup@email.com");
        Conta contaExistente = new Conta(emailExistente, new Senha("Senha999"), true);
        when(repositorio.buscarPorEmail(any(Email.class))).thenReturn(Optional.of(contaExistente));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> servico.cadastrar("dup@email.com", "Senha123", true));

        assertEquals("EMAIL_JA_CADASTRADO", ex.getMessage());
        verify(repositorio, times(1)).buscarPorEmail(any(Email.class));
    }
}
