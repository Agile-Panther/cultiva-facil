package br.edu.cesar.cultivafacil.domain.propriedade.propriedade;

import br.edu.cesar.cultivafacil.shared.ContaId;
import br.edu.cesar.cultivafacil.shared.TalhaoId;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Membro {

    private final ContaId contaId;
    private PerfilAcesso perfilAcesso;
    private StatusMembro status;
    private final List<TalhaoId> talhoesAtribuidos;

    public Membro(ContaId contaId, PerfilAcesso perfilAcesso, StatusMembro status, List<TalhaoId> talhoesAtribuidos) {
        Validate.notNull(contaId, "PROPRIEDADE_INVALIDO");
        Validate.notNull(perfilAcesso, "PROPRIEDADE_INVALIDO");
        Validate.notNull(status, "PROPRIEDADE_INVALIDO");
        this.contaId = contaId;
        this.perfilAcesso = perfilAcesso;
        this.status = status;
        this.talhoesAtribuidos = new ArrayList<>(talhoesAtribuidos == null ? List.of() : talhoesAtribuidos);
    }

    public static Membro proprietario(ContaId contaId) {
        return new Membro(contaId, PerfilAcesso.PROPRIETARIO, StatusMembro.ATIVO, List.of());
    }

    public ContaId getContaId() {
        return contaId;
    }

    public PerfilAcesso getPerfilAcesso() {
        return perfilAcesso;
    }

    public StatusMembro getStatus() {
        return status;
    }

    public List<TalhaoId> getTalhoesAtribuidos() {
        return Collections.unmodifiableList(talhoesAtribuidos);
    }

    public void alterarPerfil(PerfilAcesso novoPerfil) {
        Validate.notNull(novoPerfil, "PROPRIEDADE_INVALIDO");
        this.perfilAcesso = novoPerfil;
    }

    public void ativar() {
        this.status = StatusMembro.ATIVO;
    }

    public void revogar() {
        this.status = StatusMembro.INATIVO;
    }

    public void atribuirTalhoes(List<TalhaoId> talhoes) {
        Validate.notNull(talhoes, "PROPRIEDADE_INVALIDO");
        this.talhoesAtribuidos.clear();
        this.talhoesAtribuidos.addAll(talhoes);
    }
}
