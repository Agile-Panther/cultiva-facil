package br.edu.cesar.cultivafacil.domain.terreno.terreno;

import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;
import java.util.Objects;

public class Talhao {

    private final TalhaoId id;
    private NomeTalhao nome;
    private AreaTalhao area;
    private SituacaoTalhao situacao;

    public Talhao(NomeTalhao nome, AreaTalhao area) {
        Objects.requireNonNull(nome, "NomeTalhao nao pode ser nulo");
        Objects.requireNonNull(area, "AreaTalhao nao pode ser nula");
        this.id = TalhaoId.novo();
        this.nome = nome;
        this.area = area;
        this.situacao = SituacaoTalhao.DISPONIVEL;
    }

    public Talhao(TalhaoId id, NomeTalhao nome, AreaTalhao area, SituacaoTalhao situacao) {
        Objects.requireNonNull(id, "TalhaoId nao pode ser nulo");
        Objects.requireNonNull(nome, "NomeTalhao nao pode ser nulo");
        Objects.requireNonNull(area, "AreaTalhao nao pode ser nula");
        Objects.requireNonNull(situacao, "SituacaoTalhao nao pode ser nula");
        this.id = id;
        this.nome = nome;
        this.area = area;
        this.situacao = situacao;
    }

    // DISPONIVEL -> EM_USO: chamado pelo subdominio cultivo ao iniciar CicloAgricola (F-06)
    public SituacaoTalhaoAlterada iniciarCultivo() {
        if (!SituacaoTalhao.DISPONIVEL.equals(situacao)) {
            throw new IllegalArgumentException("TALHAO_NAO_DISPONIVEL");
        }
        return transicionarPara(SituacaoTalhao.EM_USO);
    }

    // EM_USO -> EM_DESCANSO: chamado pelo subdominio colheita ao registrar Colheita (F-12)
    public SituacaoTalhaoAlterada colocarEmDescanso() {
        if (!SituacaoTalhao.EM_USO.equals(situacao)) {
            throw new IllegalArgumentException("TALHAO_NAO_EM_USO");
        }
        return transicionarPara(SituacaoTalhao.EM_DESCANSO);
    }

    // EM_DESCANSO -> DISPONIVEL: chamado apos cumprimento do intervalo de descanso (F-08)
    public SituacaoTalhaoAlterada liberarParaCultivo() {
        if (!SituacaoTalhao.EM_DESCANSO.equals(situacao)) {
            throw new IllegalArgumentException("TALHAO_NAO_EM_DESCANSO");
        }
        return transicionarPara(SituacaoTalhao.DISPONIVEL);
    }

    public void alterarArea(AreaTalhao nova) {
        Objects.requireNonNull(nova, "AreaTalhao nao pode ser nula");
        this.area = nova;
    }

    public boolean isEmUso() {
        return SituacaoTalhao.EM_USO.equals(this.situacao);
    }

    public TalhaoId getId() { return id; }
    public NomeTalhao getNome() { return nome; }
    public AreaTalhao getArea() { return area; }
    public SituacaoTalhao getSituacao() { return situacao; }

    private SituacaoTalhaoAlterada transicionarPara(SituacaoTalhao nova) {
        SituacaoTalhao anterior = this.situacao;
        this.situacao = nova;
        return new SituacaoTalhaoAlterada(this.id, anterior, nova);
    }
}
