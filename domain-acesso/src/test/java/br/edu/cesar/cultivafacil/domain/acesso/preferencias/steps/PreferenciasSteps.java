package br.edu.cesar.cultivafacil.domain.acesso.preferencias.steps;

import br.edu.cesar.cultivafacil.domain.acesso.conta.ContaId;
import br.edu.cesar.cultivafacil.domain.acesso.preferencias.FotoPerfil;
import br.edu.cesar.cultivafacil.domain.acesso.preferencias.FormatoFoto;
import br.edu.cesar.cultivafacil.domain.acesso.preferencias.HorarioResumo;
import br.edu.cesar.cultivafacil.domain.acesso.preferencias.NomeConta;
import br.edu.cesar.cultivafacil.domain.acesso.preferencias.Preferencias;
import br.edu.cesar.cultivafacil.domain.acesso.preferencias.UnidadeArea;
import br.edu.cesar.cultivafacil.domain.acesso.preferencias.ValorArea;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PreferenciasSteps {

    private final MundoTeste mundo = MundoTeste.get();

    @Before
    public void resetar() {
        mundo.reset();
    }

    // ── Setup compartilhado ────────────────────────────────────────────────

    @Dado("que o Agricultor acessa a edicao do perfil pessoal")
    public void agricultorAcessaEdicaoPerfil() {
        mundo.preferencias = new Preferencias(
            new ContaId(UUID.randomUUID()),
            new NomeConta("Nome Inicial")
        );
    }

    @Dado("que existe uma Preferencia associada a uma Conta")
    public void preferenciaCadastrada() {
        mundo.preferencias = new Preferencias(
            new ContaId(UUID.randomUUID()),
            new NomeConta("Maria da Silva")
        );
    }

    // ── US-04 · Editar perfil ──────────────────────────────────────────────

    @Quando("ele informa o nome {string} e envia uma foto PNG de 2 MB")
    public void informaNomeEFoto(String nome) {
        try {
            FotoPerfil foto = new FotoPerfil(new byte[2 * 1024 * 1024], FormatoFoto.PNG);
            mundo.preferencias.editarPerfil(new NomeConta(nome), foto);
            mundo.eventosPublicados.addAll(mundo.preferencias.eventosNaoPublicados());
        } catch (Exception e) {
            mundo.erroCapturado = e;
        }
    }

    @Quando("ele tenta salvar o nome {string}")
    public void tentaSalvarNome(String nome) {
        try {
            mundo.preferencias.editarPerfil(new NomeConta(nome), null);
        } catch (Exception e) {
            mundo.erroCapturado = e;
        }
    }

    @Quando("ele tenta enviar uma foto no formato {string}")
    public void tentaEnviarFotoNoFormato(String formato) {
        try {
            FormatoFoto f = FormatoFoto.deExtensao(formato);
            mundo.preferencias.editarPerfil(mundo.preferencias.getNome(),
                new FotoPerfil(new byte[1024], f));
        } catch (Exception e) {
            mundo.erroCapturado = e;
        }
    }

    @Quando("ele tenta enviar uma foto PNG de 6 MB")
    public void tentaEnviarFotoGrande() {
        try {
            mundo.preferencias.editarPerfil(mundo.preferencias.getNome(),
                new FotoPerfil(new byte[6 * 1024 * 1024], FormatoFoto.PNG));
        } catch (Exception e) {
            mundo.erroCapturado = e;
        }
    }

    @Entao("o perfil e atualizado com o novo nome e foto")
    public void perfilAtualizadoComNomeEFoto() {
        assertNull(mundo.erroCapturado, "Nenhuma excecao deveria ter sido lancada");
        assertNotNull(mundo.preferencias.getNome());
        assertNotNull(mundo.preferencias.getFotoPerfil());
    }

    @E("o evento PerfilEditado e publicado")
    public void eventoPerfilEditadoPublicado() {
        boolean encontrado = mundo.eventosPublicados.stream()
            .anyMatch(e -> e instanceof Preferencias.PerfilEditado);
        assertTrue(encontrado, "Evento PerfilEditado nao foi publicado");
    }

    // ── US-05 · Preferências ───────────────────────────────────────────────

    @Quando("ele define unidade {string}, area {string} e horario de resumo {string}")
    public void definePreferencias(String unidade, String area, String horario) {
        try {
            UnidadeArea unidadeArea = UnidadeArea.valueOf(unidade);
            ValorArea valorArea = new ValorArea(new BigDecimal(area));
            HorarioResumo horarioResumo = new HorarioResumo(LocalTime.parse(horario));
            mundo.preferencias.definirPreferencias(unidadeArea, valorArea, horarioResumo);
            mundo.eventosPublicados.addAll(mundo.preferencias.eventosNaoPublicados());
        } catch (Exception e) {
            mundo.erroCapturado = e;
        }
    }

    @Quando("ele tenta definir area {string}")
    public void tentaDefinirArea(String area) {
        try {
            new ValorArea(new BigDecimal(area));
        } catch (Exception e) {
            mundo.erroCapturado = e;
        }
    }

    @Quando("ele tenta definir horario de resumo {string}")
    public void tentaDefinirHorario(String horario) {
        try {
            new HorarioResumo(LocalTime.parse(horario));
        } catch (Exception e) {
            mundo.erroCapturado = e;
        }
    }

    @Entao("as preferencias sao persistidas no agregado")
    public void preferenciasPersistidas() {
        assertNull(mundo.erroCapturado, "Nenhuma excecao deveria ter sido lancada");
        assertNotNull(mundo.preferencias);
    }

    @E("o evento PreferenciasDefinidas e publicado")
    public void eventoPreferenciasDefinidasPublicado() {
        boolean encontrado = mundo.eventosPublicados.stream()
            .anyMatch(e -> e instanceof Preferencias.PreferenciasDefinidas);
        assertTrue(encontrado, "Evento PreferenciasDefinidas nao foi publicado");
    }

    // ── Compartilhado ──────────────────────────────────────────────────────

    @Entao("o sistema rejeita com erro {string}")
    public void sistemaRejeitaComErro(String codigoErro) {
        assertNotNull(mundo.erroCapturado, "Esperava uma excecao, mas nenhuma foi lancada");
        assertEquals(codigoErro, mundo.erroCapturado.getMessage());
    }
}
