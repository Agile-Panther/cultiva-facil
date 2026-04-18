# language: pt
# Rastreabilidade: F-17

Funcionalidade: F-17 - Definição de Limites Climáticos e Geração de Alertas

  Contexto:
    Dado que o sistema está operacional

  Cenário: Definição de um novo limite climático com sucesso
    Quando um agricultor define um novo limite climático com:
      | temperatura_min | temperatura_max | precipitacao | janela_dias |
      | 15.0            | 30.0            | 20.0         | 7           |
    Então um novo LimiteClimatico deve ser criado com os valores fornecidos
    E um evento de "LimiteClimaticoDefinido" deve ser emitido

  Cenário: Tentativa de definir limite climático com temperatura inválida (min > max)
    Quando um agricultor tenta definir um novo limite climático com temperatura mínima maior que a máxima:
      | temperatura_min | temperatura_max | precipitacao | janela_dias |
      | 35.0            | 25.0            | 20.0         | 7           |
    Então o sistema deve lançar uma "TemperaturaLimiteInvalidaException"
    E nenhum limite climático deve ser criado

  Cenário: Tentativa de definir limite climático com precipitação negativa
    Quando um agricultor tenta definir um novo limite climático com precipitação negativa:
      | temperatura_min | temperatura_max | precipitacao | janela_dias |
      | 15.0            | 30.0            | -5.0         | 7           |
    Então o sistema deve lançar uma "PrecipitacaoLimiteInvalidaException"
    E nenhum limite climático deve ser criado

  Cenário: Geração de um alerta de temperatura
    Dado que existe um LimiteClimatico para temperatura entre 10°C e 25°C
    Quando o sistema recebe uma medição de temperatura de 28°C
    Então um AlertaClimatico de "TEMPERATURA" deve ser gerado
    E um evento de "AlertaClimaticoGerado" deve ser emitido

  Cenário: Geração de um alerta de irrigação
    Dado que a necessidade hídrica definida é de 30mm
    Quando o sistema calcula que a irrigação necessária é de 32mm
    Então um AlertaIrrigacao deve ser gerado
    E um evento de "AlertaIrrigacaoGerado" deve ser emitido

