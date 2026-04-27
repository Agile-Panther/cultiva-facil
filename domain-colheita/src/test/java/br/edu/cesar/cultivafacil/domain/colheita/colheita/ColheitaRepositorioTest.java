package br.edu.cesar.cultivafacil.domain.colheita.colheita;

import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class ColheitaRepositorioTest {
    @Test
    void devePermitirSalvarEBuscarColheita() {
        ColheitaRepositorio repositorio = new ColheitaRepositorioEmMemoria();
        Colheita colheita = ColheitaTestFactory.criarColheitaValida();
        repositorio.salvar(colheita);
        Optional<Colheita> encontrada = repositorio.buscarPorId(colheita.getId());
        assertTrue(encontrada.isPresent());
        assertEquals(colheita.getId(), encontrada.get().getId());
    }
}
