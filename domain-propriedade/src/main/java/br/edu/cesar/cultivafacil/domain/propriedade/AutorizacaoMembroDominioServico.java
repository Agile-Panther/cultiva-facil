package br.edu.cesar.cultivafacil.domain.propriedade.propriedade;

import org.apache.commons.lang3.Validate;

public class AutorizacaoMembroDominioServico {

    public boolean podeConvidarMembro(Membro membro) {
        Validate.notNull(membro, "MEMBRO_INVALIDO");
        // Lógica de autorização: apenas proprietários podem convidar
        return membro.getPerfilAcesso() == PerfilAcesso.PROPRIETARIO;
    }

    public boolean podeAtivarMembro(Membro membro) {
        Validate.notNull(membro, "MEMBRO_INVALIDO");
        // Lógica: proprietários e administradores podem ativar
        return membro.getPerfilAcesso() == PerfilAcesso.PROPRIETARIO ||
               membro.getPerfilAcesso() == PerfilAcesso.ADMINISTRADOR;
    }

    public boolean podeAlterarPerfil(Membro membroSolicitante, Membro membroAlvo, PerfilAcesso novoPerfil) {
        Validate.notNull(membroSolicitante, "MEMBRO_INVALIDO");
        Validate.notNull(membroAlvo, "MEMBRO_INVALIDO");
        Validate.notNull(novoPerfil, "PERFIL_INVALIDO");
        // Apenas proprietários podem alterar perfis
        return membroSolicitante.getPerfilAcesso() == PerfilAcesso.PROPRIETARIO;
    }

    public boolean podeRevogarAcesso(Membro membroSolicitante, Membro membroAlvo) {
        Validate.notNull(membroSolicitante, "MEMBRO_INVALIDO");
        Validate.notNull(membroAlvo, "MEMBRO_INVALIDO");
        // Proprietários podem revogar, administradores podem revogar membros comuns
        if (membroSolicitante.getPerfilAcesso() == PerfilAcesso.PROPRIETARIO) {
            return true;
        }
        return membroSolicitante.getPerfilAcesso() == PerfilAcesso.ADMINISTRADOR &&
               membroAlvo.getPerfilAcesso() == PerfilAcesso.COMUM;
    }

    public boolean podeAtribuirTalhao(Membro membro) {
        Validate.notNull(membro, "MEMBRO_INVALIDO");
        // Proprietários e administradores podem atribuir talhões
        return membro.getPerfilAcesso() == PerfilAcesso.PROPRIETARIO ||
               membro.getPerfilAcesso() == PerfilAcesso.ADMINISTRADOR;
    }
}