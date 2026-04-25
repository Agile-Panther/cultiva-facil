package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;
import org.apache.commons.lang3.Validate;

public class CicloAgricola {

    private final CicloAgricolaId id;
    private final TalhaoId talhaoId;
    private final NomeCultura cultura;
    private final QuantidadePlantada quantidadePlantada;
    private final UnidadeMedidaCiclo unidade;
    private StatusCiclo status;

    // Construtor de criação
    public CicloAgricola(TalhaoId talhaoId, NomeCultura cultura,
                         QuantidadePlantada quantidadePlantada, UnidadeMedidaCiclo unidade) {
        Validate.notNull(talhaoId, "talhaoId e obrigatorio");
        Validate.notNull(cultura, "cultura e obrigatoria");
        Validate.notNull(quantidadePlantada, "quantidadePlantada e obrigatoria");
        Validate.notNull(unidade, "unidade e obrigatoria");
        this.id = CicloAgricolaId.novo();
        this.talhaoId = talhaoId;
        this.cultura = cultura;
        this.quantidadePlantada = quantidadePlantada;
        this.unidade = unidade;
        this.status = StatusCiclo.ATIVO;
    }

    // Construtor de reconstituição
    public CicloAgricola(CicloAgricolaId id, TalhaoId talhaoId, NomeCultura cultura,
                         QuantidadePlantada quantidadePlantada, UnidadeMedidaCiclo unidade,
                         StatusCiclo status) {
        Validate.notNull(id, "id nao pode ser nulo");
        Validate.notNull(talhaoId, "talhaoId e obrigatorio");
        Validate.notNull(cultura, "cultura e obrigatoria");
        Validate.notNull(quantidadePlantada, "quantidadePlantada e obrigatoria");
        Validate.notNull(unidade, "unidade e obrigatoria");
        Validate.notNull(status, "status e obrigatorio");
        this.id = id;
        this.talhaoId = talhaoId;
        this.cultura = cultura;
        this.quantidadePlantada = quantidadePlantada;
        this.unidade = unidade;
        this.status = status;
    }

    // F-06 RN-046
    public void alterarQuantidadePlantada(QuantidadePlantada nova) {
        throw new IllegalStateException(
                "QUANTIDADE_PLANTADA_IMUTAVEL: a quantidade plantada nao pode ser alterada");
    }

    // F-06 RN-045
    public void alterarUnidade(UnidadeMedidaCiclo novaUnidade) {
        throw new IllegalStateException(
                "UNIDADE_INVALIDA: a unidade de medida do ciclo nao pode ser alterada");
    }

    public void encerrar() {
        if (this.status == StatusCiclo.ENCERRADO) {
            throw new IllegalStateException("Ciclo ja esta encerrado");
        }
        this.status = StatusCiclo.ENCERRADO;
    }

    public CicloAgricolaId getId() { return id; }
    public TalhaoId getTalhaoId() { return talhaoId; }
    public NomeCultura getCultura() { return cultura; }
    public QuantidadePlantada getQuantidadePlantada() { return quantidadePlantada; }
    public UnidadeMedidaCiclo getUnidade() { return unidade; }
    public StatusCiclo getStatus() { return status; }

    // Domain Events — classes estáticas internas
    public static class CicloIniciado {
        public final CicloAgricolaId cicloId;
        public final TalhaoId talhaoId;
        public final NomeCultura cultura;

        public CicloIniciado(CicloAgricolaId cicloId, TalhaoId talhaoId, NomeCultura cultura) {
            this.cicloId = cicloId;
            this.talhaoId = talhaoId;
            this.cultura = cultura;
        }
    }

    public static class CicloEncerrado {
        public final CicloAgricolaId cicloId;
        public final TalhaoId talhaoId;

        public CicloEncerrado(CicloAgricolaId cicloId, TalhaoId talhaoId) {
            this.cicloId = cicloId;
            this.talhaoId = talhaoId;
        }
    }
}
