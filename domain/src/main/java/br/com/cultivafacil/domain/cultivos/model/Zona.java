package br.com.cultivafacil.domain.cultivos.model;

import br.com.cultivafacil.domain.cultivos.exception.ZonaComCultivoAtivoException;

public class Zona {

    private boolean culticoAtivo;

    public void vincularCultura(String nomeCultura) {
        if (culticoAtivo) {
            throw new ZonaComCultivoAtivoException();
        }
        this.culticoAtivo = true;
    }
}