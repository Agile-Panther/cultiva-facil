# language: pt

Funcionalidade: Perfil do Agricultor e Notificacoes
  Como Agricultor
  Eu quero gerenciar meus dados pessoais, preferencias de exibicao e configuracoes de notificacao
  Para manter meus dados atualizados e adaptar o sistema a minha rotina

  # ── US-04 · Edicao de Dados Pessoais ──────────────────────────────────────

  @F03-US04
  Cenario: Nome e foto atualizados com sucesso
    Dado que o Agricultor acessa a edicao do perfil pessoal
    Quando informa o nome "Maria da Silva" e envia uma foto PNG de 2 MB
    Entao o perfil e atualizado com o novo nome e foto

  @F03-US04-RN023a
  Cenario: Nome com menos de 2 caracteres rejeitado
    Dado que o Agricultor acessa a edicao do perfil pessoal
    Quando tenta salvar o nome "M"
    Entao o sistema deve rejeitar com erro "NOME_INVALIDO"

  @F03-US04-RN023b
  Cenario: Nome com mais de 80 caracteres rejeitado
    Dado que o Agricultor acessa a edicao do perfil pessoal
    Quando tenta salvar um nome com 81 caracteres
    Entao o sistema deve rejeitar com erro "NOME_INVALIDO"

  @F03-US04-RN024a
  Cenario: Foto em formato invalido rejeitada
    Dado que o Agricultor acessa a edicao do perfil pessoal
    Quando tenta enviar um arquivo ".gif" como foto de perfil
    Entao o sistema deve rejeitar com erro "FOTO_FORMATO_INVALIDO"

  @F03-US04-RN024b
  Cenario: Foto acima de 5 MB rejeitada
    Dado que o Agricultor acessa a edicao do perfil pessoal
    Quando tenta enviar um arquivo PNG de 6 MB como foto de perfil
    Entao o sistema deve rejeitar com erro "FOTO_TAMANHO_EXCEDIDO"

  @F03-US04-RN024a
  Esquema do Cenario: Multiplos formatos invalidos rejeitados
    Dado que o Agricultor acessa a edicao do perfil pessoal
    Quando tenta enviar um arquivo "<formato>" como foto de perfil
    Entao o sistema deve rejeitar com erro "FOTO_FORMATO_INVALIDO"
    Exemplos:
      | formato |
      | gif     |
      | bmp     |
      | webp    |
      | tiff    |

  # ── US-05 · Preferencias de Exibicao e Agendamento ────────────────────────

  @F03-US05
  Cenario: Preferencias de exibicao salvas com sucesso
    Dado que o Agricultor acessa as preferencias de exibicao
    Quando define area em "ha", valor 1,50 e horario de resumo diario "07:00"
    Entao as preferencias sao persistidas e aplicadas na interface

  @F03-US05-RN025a
  Cenario: Valor de area negativo rejeitado
    Dado que o Agricultor acessa as preferencias de exibicao
    Quando tenta salvar o valor de area "-1.5"
    Entao o sistema deve rejeitar com erro "VALOR_AREA_INVALIDO"

  @F03-US05-RN025b
  Cenario: Valor de area com mais de 2 casas decimais rejeitado
    Dado que o Agricultor acessa as preferencias de exibicao
    Quando tenta salvar o valor de area "1.567"
    Entao o sistema deve rejeitar com erro "VALOR_AREA_INVALIDO"

  @F03-US05-RN026
  Cenario: Horario de resumo fora do intervalo rejeitado
    Dado que o Agricultor acessa as preferencias de exibicao
    Quando tenta salvar horario de resumo "04:00"
    Entao o sistema deve rejeitar com erro "HORARIO_RESUMO_INVALIDO"

  @F03-US05-RN026
  Esquema do Cenario: Multiplos horarios fora do intervalo rejeitados
    Dado que o Agricultor acessa as preferencias de exibicao
    Quando tenta salvar horario de resumo "<horario>"
    Entao o sistema deve rejeitar com erro "HORARIO_RESUMO_INVALIDO"
    Exemplos:
      | horario |
      | 04:59   |
      | 10:01   |
      | 00:00   |
      | 23:00   |

  # ── US-06 · Configuracao de Notificacoes ──────────────────────────────────

  @F03-US06
  Cenario: Configuracao de notificacao salva com sucesso
    Dado que o Agricultor acessa as configuracoes de notificacao
    E o tipo "TAREFA_ATRASADA" ainda nao atingiu o limite diario
    Quando seleciona tipo "TAREFA_ATRASADA" e canal "Push" e confirma
    Entao a configuracao e persistida e aplicada a partir da proxima verificacao do sistema

  @F03-US06-RN027
  Cenario: Tipo de notificacao invalido rejeitado
    Dado que o Agricultor acessa as configuracoes de notificacao
    Quando tenta configurar tipo "Noticias"
    Entao o sistema deve rejeitar com erro "TIPO_NOTIFICACAO_INVALIDO"

  @F03-US06-RN028
  Cenario: Mais de 5 notificacoes do mesmo tipo na mesma data rejeitadas
    Dado que o Agricultor ja possui 5 notificacoes do tipo "RESUMO_DIARIO" registradas para hoje
    Quando o sistema tenta registrar mais uma notificacao do tipo "RESUMO_DIARIO" para o mesmo Agricultor
    Entao o sistema deve rejeitar com erro "LIMITE_NOTIFICACOES_EXCEDIDO"

  @F03-US06-RN027
  Esquema do Cenario: Multiplos tipos invalidos rejeitados
    Dado que o Agricultor acessa as configuracoes de notificacao
    Quando tenta configurar tipo "<tipo>"
    Entao o sistema deve rejeitar com erro "TIPO_NOTIFICACAO_INVALIDO"
    Exemplos:
      | tipo            |
      | Noticias        |
      | Promocoes       |
      | AtualizacaoApp  |
