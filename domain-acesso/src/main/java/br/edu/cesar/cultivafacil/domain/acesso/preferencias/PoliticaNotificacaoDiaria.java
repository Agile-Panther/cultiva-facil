package br.edu.cesar.cultivafacil.domain.acesso.preferencias;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public class PoliticaNotificacaoDiaria {

    private HorarioResumoDiario horario;
    private UnidadeArea unidade;
    private Set<TipoNotificacao> tipos;

    PoliticaNotificacaoDiaria() {
        this.tipos = EnumSet.noneOf(TipoNotificacao.class);
    }

    void definir(HorarioResumoDiario horario, UnidadeArea unidade) {
        this.horario = horario;
        this.unidade = unidade;
    }

    void configurarTipos(Set<TipoNotificacao> tipos) {
        this.tipos = tipos.isEmpty()
            ? EnumSet.noneOf(TipoNotificacao.class)
            : EnumSet.copyOf(tipos);
    }

    public HorarioResumoDiario getHorario() {
        return horario;
    }

    public UnidadeArea getUnidade() {
        return unidade;
    }

    public Set<TipoNotificacao> getTipos() {
        return Collections.unmodifiableSet(tipos);
    }
}
