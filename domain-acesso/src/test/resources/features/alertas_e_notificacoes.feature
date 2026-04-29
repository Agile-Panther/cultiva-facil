# language: pt
Funcionalidade: Geracao Inteligente de Alertas e Politicas de Notificacao
  Como Membro da Propriedade
  Quero personalizar como recebo notificacoes
  Para receber apenas insights relevantes dentro da minha rotina

  Contexto:
    Dado que estou autenticado como Membro da Propriedade

  # US-08 - RN-027
  Cenario: Configuracao de notificacao salva com sucesso
    Quando configuro o tipo de notificacao "ResumoDiario" com 0 notificacoes hoje
    Entao o sistema aceita a configuracao de notificacao

  # US-08 - RN-027
  Cenario: Tipo de notificacao invalido rejeitado
    Quando configuro o tipo de notificacao "Promocional" com 0 notificacoes hoje
    Entao o sistema rejeita a configuracao de notificacao

  # US-08 - RN-028
  Cenario: Limite diario de notificacoes excedido rejeitado
    Quando configuro o tipo de notificacao "ResumoDiario" com 5 notificacoes hoje
    Entao o sistema rejeita a configuracao de notificacao

  # US-07 - RN-025, RN-026
  Cenario: Preferencias de exibicao salvas com sucesso
    Quando configuro o horario "07:00" e unidade de area "HECTARES"
    Entao o sistema aceita as preferencias de exibicao

  # US-07 - RN-026 (ValorArea)
  Cenario: Valor de area negativo rejeitado
    Quando tento registrar um valor de area de "-1.0"
    Entao o sistema rejeita o valor de area

  # US-07 - RN-026 (ValorArea)
  Cenario: Valor de area com mais de 2 casas decimais rejeitado
    Quando tento registrar um valor de area de "1.234"
    Entao o sistema rejeita o valor de area

  # US-07 - RN-025
  Cenario: Horario de resumo abaixo do minimo rejeitado
    Quando configuro o horario do resumo diario para "04:00"
    Entao o sistema rejeita o horario de resumo
