package br.edu.cesar.cultivafacil.domain.terreno.terreno;

import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;
import java.util.Objects;

public class SituacaoTalhaoAlterada {

    public final TalhaoId talhaoId;
    public final SituacaoTalhao anterior;
    public final SituacaoTalhao nova;

    public SituacaoTalhaoAlterada(TalhaoId talhaoId, SituacaoTalhao anterior, SituacaoTalhao nova) {
        this.talhaoId = Objects.requireNonNull(talhaoId, "TalhaoId nao pode ser nulo");
        this.anterior = Objects.requireNonNull(anterior, "SituacaoTalhao anterior nao pode ser nula");
        this.nova = Objects.requireNonNull(nova, "SituacaoTalhao nova nao pode ser nula");
    }
}
