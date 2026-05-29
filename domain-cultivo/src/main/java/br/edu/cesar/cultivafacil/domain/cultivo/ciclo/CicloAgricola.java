package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

import br.edu.cesar.cultivafacil.domain.terreno.zona.ZonaId;
import org.apache.commons.lang3.Validate;

public class CicloAgricola {

    private final CicloAgricolaId id;
    private final ZonaId zonaId;
    private final NomeCultura cultura;
    private StatusCiclo status;

    // Construtor de criação
    public CicloAgricola(ZonaId zonaId, NomeCultura cultura) {
        Validate.notNull(zonaId, "zonaId e obrigatorio");
        Validate.notNull(cultura, "cultura e obrigatoria");
        this.id = CicloAgricolaId.novo();
        this.zonaId = zonaId;
        this.cultura = cultura;
        this.status = StatusCiclo.ATIVO;
    }

    // Construtor de reconstituição
    public CicloAgricola(CicloAgricolaId id, ZonaId zonaId, NomeCultura cultura,
                         StatusCiclo status) {
        Validate.notNull(id, "id nao pode ser nulo");
        Validate.notNull(zonaId, "zonaId e obrigatorio");
        Validate.notNull(cultura, "cultura e obrigatoria");
        Validate.notNull(status, "status e obrigatorio");
        this.id = id;
        this.zonaId = zonaId;
        this.cultura = cultura;
        this.status = status;
    }

    public void encerrar() {
        if (this.status == StatusCiclo.ENCERRADO) {
            throw new IllegalStateException("Ciclo ja esta encerrado");
        }
        this.status = StatusCiclo.ENCERRADO;
    }

    public CicloAgricolaId getId() { return id; }
    public ZonaId getZonaId() { return zonaId; }
    public NomeCultura getCultura() { return cultura; }
    public StatusCiclo getStatus() { return status; }

    // Domain Events — classes estáticas internas
    public static class CicloIniciado {
        public final CicloAgricolaId cicloId;
        public final ZonaId zonaId;
        public final NomeCultura cultura;

        public CicloIniciado(CicloAgricolaId cicloId, ZonaId zonaId, NomeCultura cultura) {
            this.cicloId = cicloId;
            this.zonaId = zonaId;
            this.cultura = cultura;
        }
    }

    public static class CicloEncerrado {
        public final CicloAgricolaId cicloId;
        public final ZonaId zonaId;

        public CicloEncerrado(CicloAgricolaId cicloId, ZonaId zonaId) {
            this.cicloId = cicloId;
            this.zonaId = zonaId;
        }
    }
}
