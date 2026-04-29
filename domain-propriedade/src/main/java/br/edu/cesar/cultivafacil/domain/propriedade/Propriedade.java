package br.edu.cesar.cultivafacil.domain.propriedade.propriedade;

import br.edu.cesar.cultivafacil.shared.ContaId;
import br.edu.cesar.cultivafacil.shared.TalhaoId;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Propriedade {

    private final PropriedadeId id;
    private final ContaId contaId;
    private Localizacao localizacao;
    private TipoSolo tipoSolo;
    private ClimaRegiao climaRegiao;
    private StatusPerfil statusPerfil;
    private final List<Membro> membros;
    private final List<Convite> convites;
    private final List<Object> eventos;

    public Propriedade(ContaId contaId) {
        Validate.notNull(contaId, "PROPRIEDADE_INVALIDO");
        this.id = PropriedadeId.novo();
        this.contaId = contaId;
        this.statusPerfil = StatusPerfil.INCOMPLETO;
        this.membros = new ArrayList<>();
        this.convites = new ArrayList<>();
        this.eventos = new ArrayList<>();
        this.membros.add(Membro.proprietario(contaId));
    }

    public PropriedadeId getId() {
        return id;
    }

    public ContaId getContaId() {
        return contaId;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public TipoSolo getTipoSolo() {
        return tipoSolo;
    }

    public ClimaRegiao getClimaRegiao() {
        return climaRegiao;
    }

    public StatusPerfil getStatusPerfil() {
        return statusPerfil;
    }

    public List<Membro> getMembros() {
        return Collections.unmodifiableList(membros);
    }

    public List<Convite> getConvites() {
        return Collections.unmodifiableList(convites);
    }

    public List<Object> getEventos() {
        return Collections.unmodifiableList(eventos);
    }

    public void limparEventos() {
        eventos.clear();
    }

    public boolean isCompleto() {
        return statusPerfil == StatusPerfil.COMPLETO;
    }

    public void completarPerfil(Localizacao localizacao, TipoSolo tipoSolo, ClimaRegiao climaRegiao) {
        Validate.notNull(localizacao, "PROPRIEDADE_INVALIDO");
        Validate.notNull(tipoSolo, "PROPRIEDADE_INVALIDO");
        Validate.notNull(climaRegiao, "PROPRIEDADE_INVALIDO");
        this.localizacao = localizacao;
        this.tipoSolo = tipoSolo;
        this.climaRegiao = climaRegiao;
        this.statusPerfil = StatusPerfil.COMPLETO;
        eventos.add(new PropriedadeCompletaDiagnosticoAgroclimaticoAtualizado(id));
    }

    public void verificarPerfilCompleto() {
        Validate.isTrue(statusPerfil == StatusPerfil.COMPLETO, "PROPRIEDADE_INVALIDO");
    }

    public void atualizarTipoSolo(TipoSolo novoTipoSolo, JustificativaDiagnostico justificativa) {
        Validate.notNull(novoTipoSolo, "PROPRIEDADE_INVALIDO");
        Validate.notNull(justificativa, "PROPRIEDADE_INVALIDO");
        this.tipoSolo = novoTipoSolo;
        eventos.add(new DiagnosticoAgroclimaticoAtualizado(id, justificativa));
    }

    public void atualizarClima(ClimaRegiao novoClima, JustificativaDiagnostico justificativa, ValidadorCompatibilidadeAgroclimaticaServico validadorCompatibilidade) {
        Validate.notNull(novoClima, "PROPRIEDADE_INVALIDO");
        Validate.notNull(justificativa, "PROPRIEDADE_INVALIDO");
        Validate.notNull(validadorCompatibilidade, "PROPRIEDADE_INVALIDO");
        Validate.notNull(localizacao, "PROPRIEDADE_INVALIDO");
        Validate.isTrue(validadorCompatibilidade.ehCompativel(localizacao, novoClima), "PROPRIEDADE_INVALIDO");
        this.climaRegiao = novoClima;
        eventos.add(new DiagnosticoAgroclimaticoAtualizado(id, justificativa));
    }

    public void convidarMembro(Convite convite) {
        Validate.notNull(convite, "PROPRIEDADE_INVALIDO");
        convites.add(convite);
        eventos.add(new MembroConvidado(id, convite.getEmail(), convite.getPerfilAcesso()));
    }

    public void ativarMembro(ContaId contaId) {
        Validate.notNull(contaId, "PROPRIEDADE_INVALIDO");
        Membro membro = localizarMembro(contaId)
                .orElseThrow(() -> new IllegalArgumentException("PROPRIEDADE_INVALIDO"));
        membro.ativar();
        eventos.add(new MembroAtivado(id, contaId));
    }

    public void alterarPerfilMembro(ContaId contaId, PerfilAcesso novoPerfil) {
        Validate.notNull(contaId, "PROPRIEDADE_INVALIDO");
        Validate.notNull(novoPerfil, "PROPRIEDADE_INVALIDO");
        Membro membro = localizarMembro(contaId)
                .orElseThrow(() -> new IllegalArgumentException("PROPRIEDADE_INVALIDO"));
        membro.alterarPerfil(novoPerfil);
        eventos.add(new PerfilMembroAlterado(id, contaId, novoPerfil));
    }

    public void revogarAcesso(ContaId contaId) {
        Validate.notNull(contaId, "PROPRIEDADE_INVALIDO");
        Membro membro = localizarMembro(contaId)
                .orElseThrow(() -> new IllegalArgumentException("PROPRIEDADE_INVALIDO"));
        membro.revogar();
        eventos.add(new AcessoRevogado(id, contaId));
    }

    public void atribuirTalhaoAoMembro(ContaId contaId, List<TalhaoId> talhoes) {
        Validate.notNull(contaId, "PROPRIEDADE_INVALIDO");
        Validate.notNull(talhoes, "PROPRIEDADE_INVALIDO");
        Membro membro = localizarMembro(contaId)
                .orElseThrow(() -> new IllegalArgumentException("PROPRIEDADE_INVALIDO"));
        membro.atribuirTalhoes(talhoes);
        eventos.add(new TalhaoAtribuidoAoMembro(id, contaId, talhoes));
    }

    private Optional<Membro> localizarMembro(ContaId contaId) {
        return membros.stream().filter(membro -> membro.getContaId().equals(contaId)).findFirst();
    }
}
