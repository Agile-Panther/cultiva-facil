package br.edu.cesar.cultivafacil.domain.maquinario.maquina.events;

import br.edu.cesar.cultivafacil.domain.maquinario.maquina.IdentificadorFrota;
import br.edu.cesar.cultivafacil.domain.maquinario.maquina.MaquinaId;
import br.edu.cesar.cultivafacil.domain.maquinario.maquina.TipoMaquinario;

public class MaquinaCadastrada {

    private final MaquinaId maquinaId;
    private final TipoMaquinario tipo;
    private final IdentificadorFrota identificador;

    public MaquinaCadastrada(MaquinaId maquinaId, TipoMaquinario tipo, IdentificadorFrota identificador) {
        this.maquinaId = maquinaId;
        this.tipo = tipo;
        this.identificador = identificador;
    }

    public MaquinaId getMaquinaId() {
        return maquinaId;
    }

    public TipoMaquinario getTipo() {
        return tipo;
    }

    public IdentificadorFrota getIdentificador() {
        return identificador;
    }
}
