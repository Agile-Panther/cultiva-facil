# language: pt
Funcionalidade: Configuracao de notificacoes do Agricultor
  Como Agricultor
  Eu quero escolher os tipos de notificacao e configurar limites de volume
  Para nao ser sobrecarregado por alertas nao criticos

  @F03-US06 @positivo
  Cenario: Configuracao de notificacao salva com sucesso
    Dado que existe uma Preferencia associada a uma Conta
    E a contagem de hoje e 0 para "RESUMO_DIARIO" e 0 para "TAREFA_ATRASADA"
    Quando ele configura os tipos de notificacao "TAREFA_ATRASADA"
    Entao a configuracao e persistida no agregado
    E o evento NotificacoesConfiguradas e publicado

  @F03-US06-RN027
  Cenario: Tipo de notificacao invalido rejeitado
    Dado que existe uma Preferencia associada a uma Conta
    Quando ele tenta configurar o tipo de notificacao "Noticias"
    Entao o sistema rejeita com erro "TIPO_NOTIFICACAO_INVALIDO"

  @F03-US06-RN028
  Cenario: Limite diario de notificacoes excedido rejeitado
    Dado que existe uma Preferencia associada a uma Conta
    E a contagem de hoje e 5 para "RESUMO_DIARIO" e 0 para "TAREFA_ATRASADA"
    Quando ele tenta configurar os tipos de notificacao "RESUMO_DIARIO"
    Entao o sistema rejeita com erro "LIMITE_NOTIFICACOES_EXCEDIDO"
