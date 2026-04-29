package br.edu.cesar.cultivafacil.domain.propriedade.propriedade;

import br.edu.cesar.cultivafacil.shared.ContaId;
import org.apache.commons.lang3.Validate;

public class ValidadorCompatibilidadeAgroclimaticaServico {

    private final PropriedadeRepositorio propriedadeRepositorio;

    public ValidadorCompatibilidadeAgroclimaticaServico(PropriedadeRepositorio propriedadeRepositorio) {
        this.propriedadeRepositorio = propriedadeRepositorio;
    }

    public Propriedade criarPropriedade(ContaId contaId) {
        Validate.notNull(contaId, "PROPRIEDADE_INVALIDO");
        if (propriedadeRepositorio.existePorContaId(contaId)) {
            throw new IllegalArgumentException("PROPRIEDADE_INVALIDO");
        }
        Propriedade propriedade = new Propriedade(contaId);
        propriedadeRepositorio.salvar(propriedade);
        return propriedade;
    }

    public void completarPerfil(PropriedadeId propriedadeId, String municipio, String estado, String tipoSolo, String clima) {
        Propriedade propriedade = buscarPropriedade(propriedadeId);
        Localizacao localizacao = new Localizacao(municipio, estado);
        TipoSolo tipoSoloValidado = TipoSolo.fromString(tipoSolo);
        ClimaRegiao climaValidado = ClimaRegiao.fromString(clima);
        propriedade.completarPerfil(localizacao, tipoSoloValidado, climaValidado);
        propriedadeRepositorio.salvar(propriedade);
    }

    public void atualizarTipoSolo(PropriedadeId propriedadeId, String tipoSolo, JustificativaDiagnostico justificativa) {
        Propriedade propriedade = buscarPropriedade(propriedadeId);
        TipoSolo tipoSoloValidado = TipoSolo.fromString(tipoSolo);
        propriedade.atualizarTipoSolo(tipoSoloValidado, justificativa);
        propriedadeRepositorio.salvar(propriedade);
    }

    public void atualizarClima(PropriedadeId propriedadeId, String clima, JustificativaDiagnostico justificativa) {
        Propriedade propriedade = buscarPropriedade(propriedadeId);
        ClimaRegiao climaValidado = ClimaRegiao.fromString(clima);
        propriedade.atualizarClima(climaValidado, justificativa, this);
        propriedadeRepositorio.salvar(propriedade);
    }

    public void validarPerfilCompleto(PropriedadeId propriedadeId) {
        Propriedade propriedade = buscarPropriedade(propriedadeId);
        propriedade.verificarPerfilCompleto();
    }

    public boolean ehCompativel(Localizacao localizacao, ClimaRegiao climaRegiao) {
        return true;
    }

    private Propriedade buscarPropriedade(PropriedadeId propriedadeId) {
        Validate.notNull(propriedadeId, "PROPRIEDADE_INVALIDO");
        return propriedadeRepositorio.buscarPorId(propriedadeId)
                .orElseThrow(() -> new IllegalArgumentException("PROPRIEDADE_INVALIDO"));
    }
}
