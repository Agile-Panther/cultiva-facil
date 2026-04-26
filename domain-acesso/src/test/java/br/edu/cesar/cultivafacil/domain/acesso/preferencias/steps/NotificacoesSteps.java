package br.edu.cesar.cultivafacil.domain.acesso.preferencias.steps;

import br.edu.cesar.cultivafacil.domain.acesso.preferencias.ContagemDiariaNotificacoes;
import br.edu.cesar.cultivafacil.domain.acesso.preferencias.Preferencias;
import br.edu.cesar.cultivafacil.domain.acesso.preferencias.TipoNotificacao;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class NotificacoesSteps {

    private final MundoTeste mundo = MundoTeste.get();

    @E("a contagem de hoje e {int} para {string} e {int} para {string}")
    public void contagemDeHoje(int cont1, String tipo1, int cont2, String tipo2) {
        Map<TipoNotificacao, Integer> mapa = new EnumMap<>(TipoNotificacao.class);
        for (TipoNotificacao t : TipoNotificacao.values()) {
            mapa.put(t, 0);
        }
        mapa.put(TipoNotificacao.deString(tipo1), cont1);
        mapa.put(TipoNotificacao.deString(tipo2), cont2);
        mundo.contagemHoje = new ContagemDiariaNotificacoes(mapa);
    }

    @Quando("ele configura os tipos de notificacao {string}")
    public void configuraTiposNotificacao(String tiposStr) {
        try {
            Set<TipoNotificacao> tipos = parseTipos(tiposStr);
            mundo.preferencias.configurarNotificacoes(tipos, mundo.contagemHoje);
            mundo.eventosPublicados.addAll(mundo.preferencias.eventosNaoPublicados());
        } catch (Exception e) {
            mundo.erroCapturado = e;
        }
    }

    @Quando("ele tenta configurar o tipo de notificacao {string}")
    public void tentaConfigurarTipoNotificacao(String tipo) {
        try {
            Set<TipoNotificacao> tipos = Set.of(TipoNotificacao.deString(tipo));
            mundo.preferencias.configurarNotificacoes(tipos, mundo.contagemHoje);
        } catch (Exception e) {
            mundo.erroCapturado = e;
        }
    }

    @Quando("ele tenta configurar os tipos de notificacao {string}")
    public void tentaConfigurarTiposNotificacao(String tiposStr) {
        try {
            Set<TipoNotificacao> tipos = parseTipos(tiposStr);
            mundo.preferencias.configurarNotificacoes(tipos, mundo.contagemHoje);
        } catch (Exception e) {
            mundo.erroCapturado = e;
        }
    }

    @Entao("a configuracao e persistida no agregado")
    public void configuracaoPersistida() {
        assertNull(mundo.erroCapturado, "Nenhuma excecao deveria ter sido lancada");
        assertFalse(mundo.preferencias.getTiposNotificacao().isEmpty(),
            "Tipos de notificacao devem estar configurados");
    }

    @E("o evento NotificacoesConfiguradas e publicado")
    public void eventoNotificacoesConfiguradasPublicado() {
        boolean encontrado = mundo.eventosPublicados.stream()
            .anyMatch(e -> e instanceof Preferencias.NotificacoesConfiguradas);
        assertTrue(encontrado, "Evento NotificacoesConfiguradas nao foi publicado");
    }

    private Set<TipoNotificacao> parseTipos(String tiposStr) {
        return Arrays.stream(tiposStr.split(","))
            .map(String::trim)
            .map(TipoNotificacao::deString)
            .collect(Collectors.toSet());
    }
}
