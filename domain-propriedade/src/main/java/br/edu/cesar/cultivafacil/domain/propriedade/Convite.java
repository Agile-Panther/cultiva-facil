package br.edu.cesar.cultivafacil.domain.propriedade.propriedade;

import br.edu.cesar.cultivafacil.shared.TalhaoId;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Convite {

    private final String email;
    private final PerfilAcesso perfilAcesso;
    private final List<TalhaoId> talhoesAtribuidos;
    private final PrazoConvite prazoConvite;

    public Convite(String email, PerfilAcesso perfilAcesso, List<TalhaoId> talhoesAtribuidos, PrazoConvite prazoConvite) {
        Validate.notBlank(email, "PROPRIEDADE_INVALIDO");
        Validate.notNull(perfilAcesso, "PROPRIEDADE_INVALIDO");
        Validate.notNull(prazoConvite, "PROPRIEDADE_INVALIDO");
        this.email = email.trim();
        this.perfilAcesso = perfilAcesso;
        this.talhoesAtribuidos = new ArrayList<>(talhoesAtribuidos == null ? List.of() : talhoesAtribuidos);
        this.prazoConvite = prazoConvite;
    }

    public String getEmail() {
        return email;
    }

    public PerfilAcesso getPerfilAcesso() {
        return perfilAcesso;
    }

    public List<TalhaoId> getTalhoesAtribuidos() {
        return Collections.unmodifiableList(talhoesAtribuidos);
    }

    public PrazoConvite getPrazoConvite() {
        return prazoConvite;
    }
}
