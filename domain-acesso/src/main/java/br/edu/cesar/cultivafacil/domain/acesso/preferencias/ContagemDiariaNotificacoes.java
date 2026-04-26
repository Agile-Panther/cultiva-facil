package br.edu.cesar.cultivafacil.domain.acesso.preferencias;

import java.util.EnumMap;
import java.util.Map;

public final class ContagemDiariaNotificacoes {

    private final Map<TipoNotificacao, Integer> contagem;

    public ContagemDiariaNotificacoes(Map<TipoNotificacao, Integer> contagem) {
        Map<TipoNotificacao, Integer> copia = new EnumMap<>(TipoNotificacao.class);
        for (TipoNotificacao tipo : TipoNotificacao.values()) {
            copia.put(tipo, 0);
        }
        if (contagem != null) {
            copia.putAll(contagem);
        }
        this.contagem = copia;
    }

    public static ContagemDiariaNotificacoes zerada() {
        return new ContagemDiariaNotificacoes(null);
    }

    public int obter(TipoNotificacao tipo) {
        return contagem.getOrDefault(tipo, 0);
    }
}
