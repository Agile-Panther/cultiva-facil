package br.edu.cesar.cultivafacil.domain.terreno.terreno;

import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;
import java.util.Objects;

public class TalhaoCriado {

    public final TalhaoId talhaoId;
    public final NomeTalhao nome;
    public final AreaTalhao area;

    public TalhaoCriado(TalhaoId talhaoId, NomeTalhao nome, AreaTalhao area) {
        this.talhaoId = Objects.requireNonNull(talhaoId, "TalhaoId nao pode ser nulo");
        this.nome = Objects.requireNonNull(nome, "NomeTalhao nao pode ser nulo");
        this.area = Objects.requireNonNull(area, "AreaTalhao nao pode ser nula");
    }
}
