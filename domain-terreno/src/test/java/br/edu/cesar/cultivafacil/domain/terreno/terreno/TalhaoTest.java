package br.edu.cesar.cultivafacil.domain.terreno.terreno;

import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class TalhaoTest {

    private NomeTalhao nome() { return new NomeTalhao("Canteiro Norte"); }
    private AreaTalhao area() { return new AreaTalhao(new BigDecimal("500")); }

    @Test
    void deveCriarTalhaoComSituacaoDisponivel() {
        var talhao = new Talhao(nome(), area());
        assertEquals(SituacaoTalhao.DISPONIVEL, talhao.getSituacao());
    }

    @Test
    void deveCriarTalhaoComIdGeradoAutomaticamente() {
        var talhao = new Talhao(nome(), area());
        assertNotNull(talhao.getId());
    }

    @Test
    void doisTalhoesNovosDevemTerIdsDiferentes() {
        assertNotEquals(new Talhao(nome(), area()).getId(), new Talhao(nome(), area()).getId());
    }

    @Test
    void deveReconstituirTalhaoComEstadoExistente() {
        var id = TalhaoId.novo();
        var talhao = new Talhao(id, nome(), area(), SituacaoTalhao.EM_USO);
        assertEquals(id, talhao.getId());
        assertEquals(SituacaoTalhao.EM_USO, talhao.getSituacao());
    }

    @Test
    void deveRejeitarNomeNuloNaCriacao() {
        assertThrows(NullPointerException.class, () -> new Talhao(null, area()));
    }

    @Test
    void deveRejeitarAreaNulaNaCriacao() {
        assertThrows(NullPointerException.class, () -> new Talhao(nome(), null));
    }

    @Test
    void deveRejeitarIdNuloNaReconstituicao() {
        assertThrows(NullPointerException.class,
            () -> new Talhao(null, nome(), area(), SituacaoTalhao.DISPONIVEL));
    }

    // F-05 RN-041: verificacao de cultivo ativo
    @Test
    void deveIndicarEmUsoAposIniciarCultivo() {
        var talhao = new Talhao(nome(), area());
        assertFalse(talhao.isEmUso());
        talhao.iniciarCultivo();
        assertTrue(talhao.isEmUso());
    }

    // Maquina de estados: DISPONIVEL -> EM_USO
    @Test
    void iniciarCultivoDeveRetornarEventoComTransicaoCorreta() {
        var talhao = new Talhao(nome(), area());
        var evento = talhao.iniciarCultivo();
        assertEquals(SituacaoTalhao.EM_USO, talhao.getSituacao());
        assertEquals(SituacaoTalhao.DISPONIVEL, evento.anterior);
        assertEquals(SituacaoTalhao.EM_USO, evento.nova);
        assertEquals(talhao.getId(), evento.talhaoId);
    }

    @Test
    void iniciarCultivoDeveRejeitarQuandoJaEmUso() {
        var talhao = new Talhao(nome(), area());
        talhao.iniciarCultivo();
        assertThrows(IllegalArgumentException.class, () -> talhao.iniciarCultivo());
    }

    @Test
    void iniciarCultivoDeveRejeitarQuandoEmDescanso() {
        var talhao = new Talhao(TalhaoId.novo(), nome(), area(), SituacaoTalhao.EM_DESCANSO);
        assertThrows(IllegalArgumentException.class, () -> talhao.iniciarCultivo());
    }

    // Maquina de estados: EM_USO -> EM_DESCANSO
    @Test
    void colocarEmDescansoDeveTransicionarDeEmUso() {
        var talhao = new Talhao(nome(), area());
        talhao.iniciarCultivo();
        var evento = talhao.colocarEmDescanso();
        assertEquals(SituacaoTalhao.EM_DESCANSO, talhao.getSituacao());
        assertEquals(SituacaoTalhao.EM_USO, evento.anterior);
        assertEquals(SituacaoTalhao.EM_DESCANSO, evento.nova);
    }

    @Test
    void colocarEmDescansoDeveRejeitarQuandoDisponivel() {
        var talhao = new Talhao(nome(), area());
        assertThrows(IllegalArgumentException.class, () -> talhao.colocarEmDescanso());
    }

    // Maquina de estados: EM_DESCANSO -> DISPONIVEL
    @Test
    void liberarParaCultivoDeveTransicionarDeEmDescanso() {
        var talhao = new Talhao(TalhaoId.novo(), nome(), area(), SituacaoTalhao.EM_DESCANSO);
        var evento = talhao.liberarParaCultivo();
        assertEquals(SituacaoTalhao.DISPONIVEL, talhao.getSituacao());
        assertEquals(SituacaoTalhao.EM_DESCANSO, evento.anterior);
        assertEquals(SituacaoTalhao.DISPONIVEL, evento.nova);
    }

    @Test
    void liberarParaCultivoDeveRejeitarQuandoEmUso() {
        var talhao = new Talhao(nome(), area());
        talhao.iniciarCultivo();
        assertThrows(IllegalArgumentException.class, () -> talhao.liberarParaCultivo());
    }

    // F-05 RN-042: area editada deve respeitar o limite minimo
    @Test
    void deveAlterarAreaComSucesso() {
        var talhao = new Talhao(nome(), area());
        var novaArea = new AreaTalhao(new BigDecimal("800"));
        talhao.alterarArea(novaArea);
        assertEquals(novaArea, talhao.getArea());
    }

    @Test
    void deveRejeitarAreaNulaAoAlterar() {
        var talhao = new Talhao(nome(), area());
        assertThrows(NullPointerException.class, () -> talhao.alterarArea(null));
    }
}
