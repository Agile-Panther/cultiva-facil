package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

import org.apache.commons.lang3.Validate;

import java.time.LocalDate;
import java.util.UUID;

public class CicloAgricola {

    private final UUID id;
    private final UUID zonaId;
    private final NomeCultura nomeCultura;
    private final LocalDate dataInicio;
    private StatusCiclo status;
    private LocalDate dataColheita;

    // Construtor de criação
    public CicloAgricola(UUID zonaId, NomeCultura nomeCultura) {
        Validate.notNull(zonaId, "zonaId nao pode ser nulo");
        Validate.notNull(nomeCultura, "nomeCultura nao pode ser nula");
        this.id = UUID.randomUUID();
        this.zonaId = zonaId;
        this.nomeCultura = nomeCultura;
        this.dataInicio = LocalDate.now();
        this.status = StatusCiclo.ATIVO;
    }

    // Construtor de reconstituição
    public CicloAgricola(UUID id, UUID zonaId, NomeCultura nomeCultura,
                         LocalDate dataInicio, StatusCiclo status, LocalDate dataColheita) {
        Validate.notNull(id, "id nao pode ser nulo");
        Validate.notNull(zonaId, "zonaId nao pode ser nulo");
        Validate.notNull(nomeCultura, "nomeCultura nao pode ser nula");
        Validate.notNull(dataInicio, "dataInicio nao pode ser nula");
        Validate.notNull(status, "status nao pode ser nulo");
        this.id = id;
        this.zonaId = zonaId;
        this.nomeCultura = nomeCultura;
        this.dataInicio = dataInicio;
        this.status = status;
        this.dataColheita = dataColheita;
    }

    public void encerrar(LocalDate dataColheita) {
        Validate.validState(this.status == StatusCiclo.ATIVO, "Ciclo ja encerrado");
        Validate.notNull(dataColheita, "Data de colheita obrigatoria");
        this.dataColheita = dataColheita;
        this.status = StatusCiclo.ENCERRADO;
    }

    public UUID getId() { return id; }
    public UUID getZonaId() { return zonaId; }
    public NomeCultura getNomeCultura() { return nomeCultura; }
    public LocalDate getDataInicio() { return dataInicio; }
    public StatusCiclo getStatus() { return status; }
    public LocalDate getDataColheita() { return dataColheita; }

    // Domain Events
    public static class CicloIniciado {
        private final UUID cicloAgricolaId;
        private final UUID zonaId;
        private final NomeCultura nomeCultura;

        public CicloIniciado(UUID cicloAgricolaId, UUID zonaId, NomeCultura nomeCultura) {
            this.cicloAgricolaId = cicloAgricolaId;
            this.zonaId = zonaId;
            this.nomeCultura = nomeCultura;
        }

        public UUID getCicloAgricolaId() { return cicloAgricolaId; }
        public UUID getZonaId() { return zonaId; }
        public NomeCultura getNomeCultura() { return nomeCultura; }
    }

    public static class CicloEncerrado {
        private final UUID cicloAgricolaId;
        private final UUID zonaId;
        private final LocalDate dataColheita;

        public CicloEncerrado(UUID cicloAgricolaId, UUID zonaId, LocalDate dataColheita) {
            this.cicloAgricolaId = cicloAgricolaId;
            this.zonaId = zonaId;
            this.dataColheita = dataColheita;
        }

        public UUID getCicloAgricolaId() { return cicloAgricolaId; }
        public UUID getZonaId() { return zonaId; }
        public LocalDate getDataColheita() { return dataColheita; }
    }
}
