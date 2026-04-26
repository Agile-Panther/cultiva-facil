# src/test/resources/features/monitoramento_focos.feature
# language: pt

Funcionalidade: Monitoramento de Focos Fitossanitarios

  @F10-US20-Sucesso
  Cenario: Foco Fitossanitario registrado com sucesso
    Dado que a Zona possui ciclo ativo e ainda nao ha Foco do tipo "Praga" registrado nela na data de hoje
    Quando o Peao informa tipo "Praga", infestacao "Alta", severidade "Alta" e descricao com 80 caracteres
    Entao o Foco Fitossanitario e salvo com sucesso na Zona

  @F10-US20-RN065
  Cenario: Foco em Zona sem ciclo ativo rejeitado
    Dado que a Zona esta Vazia (sem ciclo ativo)
    Quando o Peao tenta registrar um Foco Fitossanitario
    Entao o sistema rejeita com erro "ZONA_SEM_CICLO_ATIVO"

  @F10-US20-RN066
  Esquema do Cenário: Tipo agronomico invalido rejeitado
    Dado que o Peao informa o tipo agronomico <tipo>
    Quando submete o registro do Foco
    Entao o sistema rejeita com erro "TIPO_AGRONOMICO_INVALIDO"

    Exemplos:
      | tipo         |
      | "Infestacao" |

  @F10-US20-RN067
  Esquema do Cenario: Nivel de infestacao invalido rejeitado
    Dado que o Peao informa nivel de infestacao <nivel>
    Quando submete o registro do Foco
    Entao o sistema rejeita com erro "NIVEL_INFESTACAO_INVALIDO"

    Exemplos:
      | nivel     |
      | "Critico" |

  @F10-US20-RN068
  Esquema do Cenario: Severidade invalida rejeitada
    Dado que o Peao informa severidade <severidade>
    Quando submete o registro do Foco
    Entao o sistema rejeita com erro "SEVERIDADE_INVALIDA"

    Exemplos:
      | severidade  |
      | "Altissima" |

  @F10-US20-RN069a
  Cenario: Descricao abaixo do minimo rejeitada
    Dado que o Peao informa descricao com apenas 5 caracteres
    Quando submete o registro do Foco
    Entao o sistema rejeita com erro "DESCRICAO_FOCO_INVALIDA"

  @F10-US20-RN069b
  Cenario: Descricao acima do maximo rejeitada
    Dado que o Peao informa descricao com 520 caracteres
    Quando submete o registro do Foco
    Entao o sistema rejeita com erro "DESCRICAO_FOCO_INVALIDA"

  @F10-US20-RN070
  Cenario: Foco duplicado do mesmo tipo na mesma Zona na mesma data rejeitado
    Dado que a Zona ja possui um Foco do tipo "Doenca" registrado na data de hoje
    Quando o Peao tenta registrar outro Foco do tipo "Doenca" na mesma Zona na mesma data
    Entao o sistema rejeita com erro "FOCO_DUPLICADO"

  @F10-US21-Sucesso
  Cenario: Foco Fitossanitario atualizado com sucesso
    Dado que o Foco pertence ao ciclo ativo da Zona e o ciclo ainda esta em andamento
    Quando o Peao atualiza o nível de infestacao para "Medio"
    Entao o Foco e atualizado com sucesso

  @F10-US21-RN071
  Cenario: Atualizacao de Foco apos encerramento do ciclo rejeitada
    Dado que o ciclo da Zona foi encerrado e o Foco Fitossanitario pertence a esse ciclo
    Quando o Peao tenta atualizar o Foco
    Entao o sistema rejeita com erro "FOCO_IMUTAVEL"

  @F10-US21-RN072
  Cenario: Validacoes do registro original aplicadas na atualizacao
    Dado que o Peao tenta atualizar o tipo agronomico do Foco para "Contaminacao"
    Quando submete a atualizacao
    Entao o sistema rejeita aplicando as mesmas validacoes do registro original

  @F10-US21-RN073
  Cenario: Remocao de Foco com Registros de Perda vinculados rejeitada
    Dado que o Foco Fitossanitario possui ao menos um Registro de Perda vinculado
    Quando o Peao tenta remover o Foco
    Entao o sistema rejeita com erro "FOCO_COM_PERDAS_VINCULADAS"