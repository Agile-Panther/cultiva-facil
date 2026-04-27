package br.edu.cesar.cultivafacil.domain.colheita.colheita;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import br.edu.cesar.cultivafacil.domain.shared.ZonaId;
import br.edu.cesar.cultivafacil.domain.shared.CicloAgricolaId;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ColheitaServiceTest {
    private ColheitaRepositorio repositorio;
    private ColheitaService service;

    @BeforeEach
    void setUp() {
        repositorio = mock(ColheitaRepositorio.class);
        service = new ColheitaService(repositorio);
    }

    @Test
    void deveSalvarColheitaValida() {
        ZonaId zonaId = new ZonaId(UUID.randomUUID());
        CicloAgricolaId cicloId = new CicloAgricolaId(UUID.randomUUID());
        Colheita colheita = new Colheita(zonaId, cicloId, BigDecimal.valueOf(10), BigDecimal.valueOf(100), UnidadeMedida.QUILOGRAMA, UnidadeMedida.QUILOGRAMA, DestinoColheita.VENDA, true);
        service.registrarColheita(colheita);
        verify(repositorio, times(1)).salvar(colheita);
    }

    @Test
    void deveBuscarColheitaPorId() {
        ColheitaId id = ColheitaId.novo();
        Colheita colheita = mock(Colheita.class);
        when(repositorio.buscarPorId(id)).thenReturn(Optional.of(colheita));
        Optional<Colheita> resultado = service.buscarColheita(id);
        assertTrue(resultado.isPresent());
        assertEquals(colheita, resultado.get());
    }

    @Test
    void deveListarColheitasPorZonaECiclo() {
        when(repositorio.listarPorZonaECiclo(anyString(), anyString())).thenReturn(java.util.Collections.emptyList());
        assertNotNull(service.listarColheitasPorZonaECiclo("zona", "ciclo"));
    }
}
