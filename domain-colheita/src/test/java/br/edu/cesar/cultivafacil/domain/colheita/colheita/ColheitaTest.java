package br.edu.cesar.cultivafacil.domain.colheita.colheita;

import br.edu.cesar.cultivafacil.domain.shared.ZonaId;
import br.edu.cesar.cultivafacil.domain.shared.CicloAgricolaId;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class ColheitaTest {
    @Test
    void deveRejeitarRegistroEmZonaNaoPronta() {
        ZonaId zonaId = new ZonaId(UUID.randomUUID());
        CicloAgricolaId cicloId = new CicloAgricolaId(UUID.randomUUID());
        BigDecimal quantidade = BigDecimal.valueOf(10);
        BigDecimal maxProjecao = BigDecimal.valueOf(100);
        assertThrows(IllegalStateException.class, () ->
            new Colheita(zonaId, cicloId, quantidade, maxProjecao, UnidadeMedida.QUILOGRAMA, UnidadeMedida.QUILOGRAMA, DestinoColheita.VENDA, false)
        );
    }

    @Test
    void deveRejeitarQuantidadeZeroOuNegativa() {
        ZonaId zonaId = new ZonaId(UUID.randomUUID());
        CicloAgricolaId cicloId = new CicloAgricolaId(UUID.randomUUID());
        assertThrows(IllegalArgumentException.class, () ->
            new Colheita(zonaId, cicloId, BigDecimal.ZERO, BigDecimal.valueOf(100), UnidadeMedida.QUILOGRAMA, UnidadeMedida.QUILOGRAMA, DestinoColheita.VENDA, true)
        );
        assertThrows(IllegalArgumentException.class, () ->
            new Colheita(zonaId, cicloId, BigDecimal.valueOf(-5), BigDecimal.valueOf(100), UnidadeMedida.QUILOGRAMA, UnidadeMedida.QUILOGRAMA, DestinoColheita.VENDA, true)
        );
    }

    @Test
    void deveRejeitarQuantidadeMaiorQueProjecao() {
        ZonaId zonaId = new ZonaId(UUID.randomUUID());
        CicloAgricolaId cicloId = new CicloAgricolaId(UUID.randomUUID());
        assertThrows(IllegalArgumentException.class, () ->
            new Colheita(zonaId, cicloId, BigDecimal.valueOf(120), BigDecimal.valueOf(100), UnidadeMedida.QUILOGRAMA, UnidadeMedida.QUILOGRAMA, DestinoColheita.VENDA, true)
        );
    }

    @Test
    void deveRejeitarDestinoInvalido() {
        // Não é possível instanciar DestinoColheita inválido, mas pode-se simular via null
        ZonaId zonaId = new ZonaId(UUID.randomUUID());
        CicloAgricolaId cicloId = new CicloAgricolaId(UUID.randomUUID());
        assertThrows(NullPointerException.class, () ->
            new Colheita(zonaId, cicloId, BigDecimal.valueOf(10), BigDecimal.valueOf(100), UnidadeMedida.QUILOGRAMA, UnidadeMedida.QUILOGRAMA, null, true)
        );
    }

    @Test
    void deveRejeitarUnidadeDiferenteDoCultivo() {
        ZonaId zonaId = new ZonaId(UUID.randomUUID());
        CicloAgricolaId cicloId = new CicloAgricolaId(UUID.randomUUID());
        assertThrows(IllegalArgumentException.class, () ->
            new Colheita(zonaId, cicloId, BigDecimal.valueOf(10), BigDecimal.valueOf(100), UnidadeMedida.LITRO, UnidadeMedida.QUILOGRAMA, DestinoColheita.VENDA, true)
        );
    }

    @Test
    void deveRejeitarCorrecaoAposEncerramentoCiclo() {
        ZonaId zonaId = new ZonaId(UUID.randomUUID());
        CicloAgricolaId cicloId = new CicloAgricolaId(UUID.randomUUID());
        Colheita colheita = new Colheita(zonaId, cicloId, BigDecimal.valueOf(10), BigDecimal.valueOf(100), UnidadeMedida.QUILOGRAMA, UnidadeMedida.QUILOGRAMA, DestinoColheita.VENDA, true);
        colheita.encerrarCiclo();
        assertThrows(IllegalStateException.class, () ->
            colheita.corrigir(BigDecimal.valueOf(12), BigDecimal.valueOf(100), UnidadeMedida.QUILOGRAMA, UnidadeMedida.QUILOGRAMA, DestinoColheita.VENDA, true)
        );
    }

    @Test
    void deveRejeitarCorrecaoComDadosInvalidos() {
        ZonaId zonaId = new ZonaId(UUID.randomUUID());
        CicloAgricolaId cicloId = new CicloAgricolaId(UUID.randomUUID());
        Colheita colheita = new Colheita(zonaId, cicloId, BigDecimal.valueOf(10), BigDecimal.valueOf(100), UnidadeMedida.QUILOGRAMA, UnidadeMedida.QUILOGRAMA, DestinoColheita.VENDA, true);
        // Quantidade negativa
        assertThrows(IllegalArgumentException.class, () ->
            colheita.corrigir(BigDecimal.valueOf(-1), BigDecimal.valueOf(100), UnidadeMedida.QUILOGRAMA, UnidadeMedida.QUILOGRAMA, DestinoColheita.VENDA, false)
        );
        // Unidade divergente
        assertThrows(IllegalArgumentException.class, () ->
            colheita.corrigir(BigDecimal.valueOf(5), BigDecimal.valueOf(100), UnidadeMedida.LITRO, UnidadeMedida.QUILOGRAMA, DestinoColheita.VENDA, false)
        );
    }
}
