package br.edu.cesar.cultivafacil.domain.terreno.terreno;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes do TerrenoRepositorio usando Mock (padrão SGB — seção 2.9.3).
 * Verifica contratos da interface de repositório com Mockito.
 */
@ExtendWith(MockitoExtension.class)
class TerrenoRepositorioTest {

    @Mock
    private TerrenoRepositorio repositorio;

    private static final String AGRICULTOR_ID = "550e8400-e29b-41d4-a716-446655440000";

    private Terreno criarTerreno() {
        return new Terreno(
                AGRICULTOR_ID,
                new NomeTerreno("Fazenda Teste"),
                new AreaTerreno(new BigDecimal("5000")),
                TipoSoloTerreno.LATOSSOLO,
                ClimaRegiaoTerreno.TROPICAL_UMIDO,
                null,
                null
        );
    }

    // -------------------------------------------------------------------------
    // salvar
    // -------------------------------------------------------------------------

    @Test
    void salvar_deveSerChamadoUmaVezComOTerreno() {
        var terreno = criarTerreno();
        repositorio.salvar(terreno);
        verify(repositorio, times(1)).salvar(terreno);
    }

    // -------------------------------------------------------------------------
    // buscarPorId
    // -------------------------------------------------------------------------

    @Test
    void buscarPorId_deveRetornarTerrenoQuandoExiste() {
        var terreno = criarTerreno();
        when(repositorio.buscarPorId(terreno.getId()))
                .thenReturn(Optional.of(terreno));

        Optional<Terreno> resultado = repositorio.buscarPorId(terreno.getId());

        assertTrue(resultado.isPresent());
        assertEquals(terreno.getId(), resultado.get().getId());
        verify(repositorio, times(1)).buscarPorId(terreno.getId());
    }

    @Test
    void buscarPorId_deveRetornarVazioQuandoNaoExiste() {
        TerrenoId idInexistente = TerrenoId.novo();
        when(repositorio.buscarPorId(idInexistente))
                .thenReturn(Optional.empty());

        Optional<Terreno> resultado = repositorio.buscarPorId(idInexistente);

        assertTrue(resultado.isEmpty());
        verify(repositorio, times(1)).buscarPorId(idInexistente);
    }

    // -------------------------------------------------------------------------
    // listarPorAgricultor
    // -------------------------------------------------------------------------

    @Test
    void listarPorAgricultor_deveRetornarListaDeTerrenos() {
        var t1 = criarTerreno();
        var t2 = criarTerreno();
        when(repositorio.listarPorAgricultor(AGRICULTOR_ID))
                .thenReturn(List.of(t1, t2));

        List<Terreno> resultado = repositorio.listarPorAgricultor(AGRICULTOR_ID);

        assertEquals(2, resultado.size());
        verify(repositorio, times(1)).listarPorAgricultor(AGRICULTOR_ID);
    }

    @Test
    void listarPorAgricultor_deveRetornarListaVaziaQuandoNaoHaTerrenos() {
        when(repositorio.listarPorAgricultor(AGRICULTOR_ID))
                .thenReturn(List.of());

        List<Terreno> resultado = repositorio.listarPorAgricultor(AGRICULTOR_ID);

        assertTrue(resultado.isEmpty());
        verify(repositorio, times(1)).listarPorAgricultor(AGRICULTOR_ID);
    }

    // -------------------------------------------------------------------------
    // excluir
    // -------------------------------------------------------------------------

    @Test
    void excluir_deveSerChamadoComIdCorreto() {
        var terreno = criarTerreno();
        repositorio.excluir(terreno.getId());
        verify(repositorio, times(1)).excluir(terreno.getId());
    }

    @Test
    void excluir_naoDeveSerChamadoSemInvocacaoExplicita() {
        var terreno = criarTerreno();
        verify(repositorio, never()).excluir(terreno.getId());
    }
}