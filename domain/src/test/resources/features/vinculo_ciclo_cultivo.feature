# language: pt
Funcionalidade: Vínculo e Ciclo de Cultivo
  Como Proprietário ou Gestor, eu quero vincular uma cultura a uma Zona
  declarando a quantidade plantada para iniciar um novo Ciclo Agrícola
  e permitir que o sistema acompanhe a evolução produtiva daquele espaço.

  Contexto:
    Dado que existe uma Zona de plantio sem ciclo ativo

  # ─── US-12 — Vínculo de Cultura ──────────────────────────────────────

  Cenário: Ciclo Agrícola iniciado com sucesso
    Quando o Agricultor vincula a cultura "Milho" com 50.00 kg plantados
    Então o Ciclo Agrícola é criado com status ATIVO
    E a quantidade plantada registrada é 50.00 kg

  Cenário: RN-043 — Rejeitar vínculo em Zona com Ciclo Agrícola ativo
    Dado que a Zona já possui um ciclo ativo da cultura "Milho"
    Quando o Agricultor tenta vincular a cultura "Feijão" com 30.00 kg plantados
    Então o sistema rejeita com erro "ZONA_COM_CULTIVO_ATIVO"

  Cenário: RN-044a — Rejeitar quantidade plantada zero
    Quando o Agricultor tenta vincular a cultura "Tomate" com 0 kg plantados
    Então o sistema rejeita a criação da quantidade

  Cenário: RN-044b — Rejeitar quantidade plantada negativa
    Quando o Agricultor tenta vincular a cultura "Tomate" com -5.00 kg plantados
    Então o sistema rejeita a criação da quantidade

  Cenário: RN-044c — Rejeitar quantidade com mais de 2 casas decimais
    Quando o Agricultor tenta vincular a cultura "Tomate" com 10.567 kg plantados
    Então o sistema rejeita a criação da quantidade

  Cenário: RN-045 — Aceitar unidade de medida kg
    Quando o Agricultor vincula a cultura "Milho" com 100.00 kg plantados
    Então a quantidade plantada registrada é 100.00 kg

  Cenário: RN-045 — Aceitar unidade de medida g
    Quando o Agricultor vincula a cultura "Alface" com 500.00 g plantados
    Então a quantidade plantada registrada é 500.00 g

  Cenário: RN-045 — Aceitar unidade de medida unidades
    Quando o Agricultor vincula a cultura "Tomate" com 200.00 unidades plantados
    Então a quantidade plantada registrada é 200.00 unidades

  Cenário: RN-046 — Quantidade plantada imutável após confirmação
    Quando o Agricultor vincula a cultura "Milho" com 50.00 kg plantados
    Então a quantidade plantada registrada é 50.00 kg
    E a quantidade plantada não pode ser alterada

  Cenário: Encerramento do Ciclo
    Dado que a Zona já possui um ciclo ativo da cultura "Milho"
    Quando o Agricultor encerra o ciclo com data de colheita de hoje
    Então o Ciclo Agrícola muda para status ENCERRADO
