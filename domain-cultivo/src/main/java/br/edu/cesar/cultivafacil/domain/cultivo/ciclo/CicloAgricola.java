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
    private CancelamentoCiclo cancelamento;

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

    // F-06 RN-055 e RN-056
    public void cancelar(JustificativaCancelamento justificativa) {
        if (this.status != StatusCiclo.ATIVO) {
            throw new IllegalStateException(
                    "CICLO_INVALIDO: somente Ciclos no estado ATIVO podem ser cancelados");
        }
        this.cancelamento = new CancelamentoCiclo(justificativa);
        this.status = StatusCiclo.CANCELADO;
    }

    public void encerrar() {
        if (this.status != StatusCiclo.ATIVO) {
            throw new IllegalStateException(
                    "CICLO_INVALIDO: somente Ciclos no estado ATIVO podem ser encerrados");
        }
        this.status = StatusCiclo.ENCERRADO;
    }

    public CicloAgricolaId getId() { return id; }
    public TalhaoId getTalhaoId() { return talhaoId; }
    public NomeCultura getCultura() { return cultura; }
    public QuantidadePlantada getQuantidadePlantada() { return quantidadePlantada; }
    public UnidadeMedidaCiclo getUnidade() { return unidade; }
    public StatusCiclo getStatus() { return status; }
    public CancelamentoCiclo getCancelamento() { return cancelamento; }
}
