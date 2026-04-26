# language: pt

Funcionalidade: Controle do Celeiro com Projeção e Alertas
  Como Agricultor
  Eu quero controlar o Celeiro de uma Zona com projeção e alertas
  Para acompanhar a produção, gerenciar metas e registrar saídas

  @F13-US24-positivo
  Cenario: Estado do Celeiro exibido corretamente
    Dado que a Zona possui ciclo ativo com 200 kg de "Tomate" plantados
    E foram registradas 30 kg de perdas
    Quando o Agricultor acessa a visualização do Celeiro da Zona
    Entao o sistema exibe quantidade plantada de 200 kg perdas acumuladas de 30 kg e projecao de 170 kg

  @F13-US36-positivo
  Cenario: Meta Comercializavel definida e Alerta de Projecao gerado
    Dado que a Zona possui ciclo ativo com 200 kg de "Milho" plantados
    E o Agricultor define Meta Comercializavel de 150 kg
    Quando sao registradas 60 kg de perdas tornando a projecao 140 kg
    Entao um Alerta de Projecao Abaixo do Esperado e emitido para a Zona

  @F13-US36-RN090a
  Cenario: Meta Comercializavel zero rejeitada
    Dado que a Zona possui ciclo ativo com 100 kg de "Feijao" plantados
    Quando o Agricultor tenta definir Meta Comercializavel de 0 kg
    Entao o sistema rejeita com erro "META_COMERCIALIZAVEL_INVALIDA"

  @F13-US36-RN090b
  Cenario: Meta Comercializavel superior a quantidade plantada rejeitada
    Dado que a Zona possui ciclo ativo com 100 kg de "Feijao" plantados
    Quando o Agricultor tenta definir Meta Comercializavel de 120 kg
    Entao o sistema rejeita com erro "META_COMERCIALIZAVEL_INVALIDA"

  @F13-US36-RN091
  Cenario: Meta definida com ciclo encerrado rejeitada
    Dado que o ciclo da Zona foi encerrado com 100 kg plantados de "Soja"
    Quando o Agricultor tenta definir Meta Comercializavel de 80 kg
    Entao o sistema rejeita com erro "CICLO_ENCERRADO"

  @F13-US36-RN092
  Cenario: Segundo Alerta de Projecao em 24 horas bloqueado
    Dado que a Zona possui ciclo ativo com 200 kg de "Milho" plantados
    E o Agricultor define Meta Comercializavel de 150 kg
    E um Alerta de Projecao ja foi emitido ha menos de 24 horas
    Quando as perdas acumuladas continuam abaixo da Meta e o sistema tenta emitir novo alerta
    Entao o sistema rejeita com erro "FREQUENCIA_ALERTA_EXCEDIDA"

  @F13-US25-positivo
  Cenario: Saida de produto registrada com sucesso
    Dado que o Celeiro possui saldo de 300 kg de "Tomate"
    Quando o Agricultor registra saida de 100 kg com motivo "VENDA"
    Entao o saldo do Celeiro e reduzido para 200 kg

  @F13-US25-RN094
  Cenario: Saida maior que saldo disponivel rejeitada
    Dado que o Celeiro possui saldo de 50 kg de "Milho"
    Quando o Agricultor tenta registrar saida de 80 kg com motivo "VENDA"
    Entao o sistema rejeita com erro "SALDO_INSUFICIENTE"

  @F13-US25-RN095
  Cenario: Motivo de saida manual de Perda rejeitado
    Dado que o Celeiro possui saldo de 100 kg de "Soja"
    Quando o Agricultor tenta registrar uma saida com motivo "PERDA"
    Entao o sistema rejeita com erro "MOTIVO_SAIDA_INVALIDO"

  @F13-US37-RN096
  Cenario: Relatorio de Perdas em ciclo ativo rejeitado
    Dado que a Zona possui ciclo ativo com 100 kg de "Tomate" plantados
    Quando o Agricultor tenta consultar o Relatorio de Perdas
    Entao o sistema rejeita com erro "RELATORIO_CICLO_ATIVO"

  @F13-US37-positivo
  Cenario: Relatorio de Perdas exibe dados consolidados do ciclo
    Dado que o ciclo da Zona foi encerrado com 100 kg plantados de "Tomate"
    E foram registradas 15 kg de perdas no ciclo
    Quando o Agricultor acessa o Relatorio de Perdas do ciclo
    Entao o sistema exibe quantidade plantada de 100 kg e perdas totais de 15 kg

  @F13-US26-positivo
  Cenario: Configuracao de relatorio salva com sucesso
    Dado que o Agricultor possui menos de 5 configuracoes salvas no Celeiro
    Quando informa nome "Safra Verao 2026" e periodo "SEMESTRE"
    Entao a configuracao de relatorio e persistida com sucesso

  @F13-US26-RN098a
  Cenario: Nome de configuracao invalido rejeitado
    Dado que o Agricultor possui menos de 5 configuracoes salvas no Celeiro
    Quando informa nome "A" e periodo "SEMESTRE"
    Entao o sistema rejeita com erro "NOME_CONFIG_INVALIDO"

  @F13-US26-RN098b
  Cenario: Nome de configuracao duplicado rejeitado
    Dado que o Agricultor ja possui a configuracao "Safra Verao 2026" salva
    Quando tenta criar outra configuracao com o nome "Safra Verao 2026" e periodo "SEMESTRE"
    Entao o sistema rejeita com erro "NOME_CONFIG_DUPLICADO"

  @F13-US26-RN100
  Cenario: Sexta configuracao de relatorio rejeitada
    Dado que o Agricultor ja possui 5 configuracoes de relatorio salvas
    Quando tenta salvar uma sexta configuracao com nome "Config Extra" e periodo "ANO"
    Entao o sistema rejeita com erro "LIMITE_CONFIGURACOES_EXCEDIDO"
