# language: pt

Funcionalidade: Rotação de Culturas e Proteção do Ciclo de Descanso do Solo
  Como Proprietário ou Gestor
  Eu quero definir Intervalos de Descanso entre cultivos da mesma cultura em cada Talhão
  Para proteger o solo da exaustão

  # ── US-19 · Cadastro de Intervalo de Descanso ──

  @F08-US19-positivo
  Cenário: Cadastro bem-sucedido — Talhão com histórico encerrado aceita Intervalo válido
    Dado que o Talhão possui ao menos um ciclo de "Tomate" encerrado
    Quando o Proprietário cadastra Intervalo de Descanso de 30 dias para "Tomate"
    Então o Intervalo é registrado com sucesso

  @F08-US19-RN066
  Cenário: Cadastro rejeitado — Talhão sem ciclo encerrado impede criação de Intervalo
    Dado que o Talhão nunca teve nenhum ciclo de "Milho" encerrado
    Quando o Proprietário tenta cadastrar Intervalo de Descanso de 30 dias para "Milho"
    Então o sistema rejeita o cadastro com erro "TALHAO_INVALIDO"

  @F08-US19-RN065
  Esquema do Cenário: Cadastro rejeitado — dias fora da faixa 30-3650 são inválidos
    Dado que o Talhão possui ao menos um ciclo de "Tomate" encerrado
    Quando o Proprietário tenta cadastrar Intervalo de Descanso de <dias> dias para "Tomate"
    Então o sistema rejeita o cadastro com erro "ROTACAO_INVALIDO"
    Exemplos:
      | dias |
      | 29   |
      | 3651 |

  # ── US-20 · Dispensa de Descanso ──

  @F08-US20-positivo
  Cenário: Dispensa bem-sucedida — dentro do Intervalo vigente com justificativa válida
    Dado que o Talhão possui ao menos um ciclo de "Tomate" encerrado
    E que o Intervalo de 30 dias para "Tomate" ainda não foi cumprido
    Quando o Proprietário concede dispensa para "Tomate" com justificativa válida
    Então a Dispensa é concedida com sucesso

  @F08-US20-RN068
  Cenário: Dispensa rejeitada — Intervalo já cumprido não possui restrição a dispensar
    Dado que o Talhão possui ao menos um ciclo de "Tomate" encerrado
    E que o Intervalo de 30 dias para "Tomate" já foi cumprido
    Quando o Proprietário tenta conceder dispensa para "Tomate" com justificativa válida
    Então o sistema rejeita a dispensa com erro "TALHAO_INVALIDO"

  @F08-US20-RN067
  Cenário: Dispensa rejeitada — justificativa com menos de 20 caracteres é inválida
    Dado que o Talhão possui ao menos um ciclo de "Tomate" encerrado
    E que o Intervalo de 30 dias para "Tomate" ainda não foi cumprido
    Quando o Proprietário tenta conceder dispensa para "Tomate" com justificativa "curta"
    Então o sistema rejeita a dispensa com erro "JUSTIFICATIVA_INVALIDA"
