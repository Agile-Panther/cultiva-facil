package br.edu.cesar.cultivafacil.domain.cultivo.rotacao;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.NomeCultura;
import org.apache.commons.lang3.Validate;

import java.util.UUID;

public class IntervaloDeDescanso {

    private final UUID zonaId;
    private final NomeCultura nomeCultura;
    private final DiasDescanso diasDescanso;

    public IntervaloDeDescanso(UUID zonaId, NomeCultura nomeCultura, DiasDescanso diasDescanso) {
        Validate.notNull(zonaId, "zonaId nao pode ser nulo");
        Validate.notNull(nomeCultura, "nomeCultura nao pode ser nula");
        Validate.notNull(diasDescanso, "diasDescanso nao pode ser nulo");
        this.zonaId = zonaId;
        this.nomeCultura = nomeCultura;
        this.diasDescanso = diasDescanso;
    }

    public UUID getZonaId() { return zonaId; }
    public NomeCultura getNomeCultura() { return nomeCultura; }
    public DiasDescanso getDiasDescanso() { return diasDescanso; }
}
