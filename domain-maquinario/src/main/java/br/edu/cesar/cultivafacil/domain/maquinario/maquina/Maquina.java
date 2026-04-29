package br.edu.cesar.cultivafacil.domain.maquinario.maquina;

import br.edu.cesar.cultivafacil.domain.evento.EventoBarramento;
import br.edu.cesar.cultivafacil.domain.maquinario.maquina.events.MaquinaCadastrada;
import br.edu.cesar.cultivafacil.domain.maquinario.maquina.events.ManutencaoPrevista;
import br.edu.cesar.cultivafacil.domain.maquinario.maquina.events.UsoMaquinaApontado;
import br.edu.cesar.cultivafacil.domain.propriedade.propriedade.PropriedadeId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Maquina {

    private final MaquinaId id;
    private final PropriedadeId propriedadeId;
    private final TipoMaquinario tipo;
    private final ModeloMaquina modelo;
    private final IdentificadorFrota identificador;
    private final Horimetro horimetroInicial;
    private final LocalDate dataRegistro;
    private Horimetro horimetro;
    private StatusIntegridade status;
    private PlanoManutencao plano;
    private final List<ApontamentoUso> historico;
    private final EventoBarramento barramento;

    public Maquina(PropriedadeId propriedadeId, TipoMaquinario tipo, ModeloMaquina modelo,
                   IdentificadorFrota identificador, Horimetro horimetro,
                   LocalDate dataRegistro, EventoBarramento barramento) {
        Objects.requireNonNull(propriedadeId, "PropriedadeId nao pode ser nulo");
        Objects.requireNonNull(tipo, "TipoMaquinario nao pode ser nulo");
        Objects.requireNonNull(modelo, "ModeloMaquina nao pode ser nulo");
        Objects.requireNonNull(identificador, "IdentificadorFrota nao pode ser nulo");
        Objects.requireNonNull(horimetro, "Horimetro nao pode ser nulo");
        Objects.requireNonNull(dataRegistro, "DataRegistro nao pode ser nulo");
        Objects.requireNonNull(barramento, "EventoBarramento nao pode ser nulo");

        this.id = MaquinaId.novo();
        this.propriedadeId = propriedadeId;
        this.tipo = tipo;
        this.modelo = modelo;
        this.identificador = identificador;
        this.horimetroInicial = horimetro;
        this.horimetro = horimetro;
        this.dataRegistro = dataRegistro;
        this.status = StatusIntegridade.DISPONIVEL;
        this.historico = new ArrayList<>();
        this.plano = null;
        this.barramento = barramento;

        barramento.publicar(new MaquinaCadastrada(this.id, tipo, identificador));
    }

    // RN-047, RN-048
    public void apontarUso(Horimetro novoHorimetro, LocalDate data) {
        Objects.requireNonNull(novoHorimetro, "NovoHorimetro nao pode ser nulo");
        Objects.requireNonNull(data, "Data nao pode ser nula");

        if (status != StatusIntegridade.DISPONIVEL) {
            throw new IllegalStateException("MAQUINA_NAO_DISPONIVEL: status atual e " + status.name());
        }
        if (!novoHorimetro.isMaiorQue(this.horimetro)) {
            throw new IllegalArgumentException("HORIMETRO_INVALIDO: novo valor deve ser maior que o atual");
        }

        historico.add(new ApontamentoUso(novoHorimetro, data));
        this.horimetro = novoHorimetro;

        barramento.publicar(new UsoMaquinaApontado(this.id, novoHorimetro, data));
    }

    public void atualizarPlanoManutencao(DataEstimadaManutencao dataEstimada) {
        Objects.requireNonNull(dataEstimada, "DataEstimadaManutencao nao pode ser nula");
        if (this.plano == null) {
            this.plano = new PlanoManutencao(dataEstimada);
        } else {
            this.plano.atualizarData(dataEstimada);
        }
        barramento.publicar(new ManutencaoPrevista(this.id, dataEstimada));
    }

    public void marcarAlertaEmitido() {
        if (this.plano != null) {
            this.plano.marcarAlertaEmitido();
        }
    }

    public MaquinaId getId() {
        return id;
    }

    public PropriedadeId getPropriedadeId() {
        return propriedadeId;
    }

    public TipoMaquinario getTipo() {
        return tipo;
    }

    public ModeloMaquina getModelo() {
        return modelo;
    }

    public IdentificadorFrota getIdentificador() {
        return identificador;
    }

    public Horimetro getHorimetro() {
        return horimetro;
    }

    public Horimetro getHorimetroInicial() {
        return horimetroInicial;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public StatusIntegridade getStatus() {
        return status;
    }

    public PlanoManutencao getPlano() {
        return plano;
    }

    public List<ApontamentoUso> getHistorico() {
        return Collections.unmodifiableList(historico);
    }

    public EventoBarramento getBarramento() {
        return barramento;
    }
}
