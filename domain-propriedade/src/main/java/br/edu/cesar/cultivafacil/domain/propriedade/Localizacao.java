package br.edu.cesar.cultivafacil.domain.propriedade.propriedade;

import org.apache.commons.lang3.Validate;

public final class Localizacao {

    private final String municipio;
    private final String estado;

    public Localizacao(String municipio, String estado) {
        Validate.notBlank(municipio, "PROPRIEDADE_INVALIDO");
        Validate.notBlank(estado, "PROPRIEDADE_INVALIDO");
        this.municipio = municipio.trim();
        this.estado = estado.trim();
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getEstado() {
        return estado;
    }
}
