package br.com.cultivafacil.domain.clima.repository;

import br.com.cultivafacil.domain.clima.model.AlertaIrrigacao;
import br.com.cultivafacil.domain.clima.vo.AlertaIrrigacaoId;

import java.util.Optional;

public interface AlertaIrrigacaoRepositorio {
    void salvar(AlertaIrrigacao alertaIrrigacao);
    Optional<AlertaIrrigacao> buscarPorId(AlertaIrrigacaoId id);
}

