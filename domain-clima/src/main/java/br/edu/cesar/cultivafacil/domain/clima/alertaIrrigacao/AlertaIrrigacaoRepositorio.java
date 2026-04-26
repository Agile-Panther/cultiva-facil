package br.edu.cesar.cultivafacil.domain.clima.alertaIrrigacao;

import br.edu.cesar.cultivafacil.domain.clima.alertaIrrigacao.AlertaIrrigacao;
import br.edu.cesar.cultivafacil.domain.clima.alertaIrrigacao.AlertaIrrigacaoId;

import java.util.Optional;

public interface AlertaIrrigacaoRepositorio {
    void salvar(AlertaIrrigacao alertaIrrigacao);
    Optional<AlertaIrrigacao> buscarPorId(AlertaIrrigacaoId id);
}

