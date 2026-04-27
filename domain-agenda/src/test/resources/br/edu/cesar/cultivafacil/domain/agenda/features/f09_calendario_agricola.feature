# language: pt
Funcionalidade: F-09 Calendário Agrícola
  Como Proprietário ou Gestor
  Eu quero gerenciar as Tarefas do ciclo ativo
  Para organizar as atividades agrícolas no calendário da Zona

  @F09-US17-positivo
  Cenário: Tarefa editada com sucesso
    Dado que existe uma Tarefa "Irrigação" no ciclo ativo que inicia em "2024-04-01" e encerra em "2024-06-30"
    Quando o Agricultor edita o nome para "Irrigação Matinal" e a data para "2024-04-15"
    Então a Tarefa é atualizada com o novo nome e a nova data dentro do intervalo do ciclo

  @F09-US17-RN054
  Cenário: Nome da Tarefa inválido ao editar rejeitado
    Dado que existe uma Tarefa "Irrigação" no ciclo ativo que inicia em "2024-04-01" e encerra em "2024-06-30"
    Quando o Agricultor tenta editar o nome da Tarefa para "X" (1 caractere, abaixo do mínimo de 2)
    Então o sistema rejeita com erro "NOME_TAREFA_INVALIDO"

  @F09-US17-RN055
  Cenário: Data da Tarefa fora do ciclo ao editar rejeitada
    Dado que existe uma Tarefa "Irrigação" no ciclo ativo que inicia em "2024-04-01" e encerra em "2024-06-30"
    Quando o Agricultor tenta mover a Tarefa para a data "2024-07-15" (após o encerramento do ciclo)
    Então o sistema rejeita com erro "DATA_TAREFA_FORA_DO_CICLO"

  @F09-US18-positivo
  Cenário: Tarefa manual criada com sucesso
    Dado que hoje é "2024-04-01" e o ciclo ativo encerra em "2024-06-30"
    Quando o Agricultor cria a Tarefa manual "Adubação Extra" com data "2024-04-20"
    Então a Tarefa manual é registrada no calendário do ciclo ativo

  @F09-US18-RN056a
  Cenário: Data passada para Tarefa manual rejeitada
    Dado que hoje é "2024-04-01" e o ciclo ativo encerra em "2024-06-30"
    Quando o Agricultor informa a data "2024-03-15" (anterior à data atual) para a Tarefa manual
    Então o sistema rejeita com erro "DATA_TAREFA_PASSADA"

  @F09-US18-RN056b
  Cenário: Data posterior ao fim do ciclo para Tarefa manual rejeitada
    Dado que hoje é "2024-04-01" e o ciclo ativo encerra em "2024-06-30"
    Quando o Agricultor informa a data "2024-07-15" (após o encerramento do ciclo) para a Tarefa manual
    Então o sistema rejeita com erro "DATA_TAREFA_FORA_DO_CICLO"

  @F09-US18-RN057
  Cenário: Nome inválido para Tarefa manual rejeitado
    Dado que hoje é "2024-04-01" e o ciclo ativo encerra em "2024-06-30"
    Quando o Agricultor tenta criar uma Tarefa manual com nome vazio
    Então o sistema rejeita com erro "NOME_TAREFA_INVALIDO"

  @F09-US19-positivo
  Cenário: Tarefa do ciclo ativo excluída com sucesso
    Dado que a Tarefa "Adubação" pertence ao ciclo ativo da Zona
    Quando o Agricultor solicita a exclusão da Tarefa
    Então a Tarefa é removida do calendário do ciclo ativo

  @F09-US19-RN058
  Cenário: Exclusão de Tarefa de ciclo encerrado rejeitada
    Dado que a Tarefa "Adubação" pertence a um ciclo já encerrado da Zona
    Quando o Agricultor tenta excluir a Tarefa
    Então o sistema rejeita com erro "TAREFA_CICLO_ENCERRADO" pois Tarefas de ciclos encerrados são imutáveis

  @F09-US17-RN054-limite
  Esquema do Cenário: Multiplos nomes invalidos ao editar rejeitados
    Dado que existe uma Tarefa no ciclo ativo que inicia em "2024-04-01" e encerra em "2024-06-30"
    Quando o Agricultor tenta editar o nome para <nome>
    Então o sistema rejeita com erro "NOME_TAREFA_INVALIDO"
    Exemplos:
      | nome                                                                                                       |
      | "A"                                                                                                        |
      | ""                                                                                                         |
      | "AAAAAAAAAABBBBBBBBBBCCCCCCCCCCDDDDDDDDDDEEEEEEEEEEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" |
