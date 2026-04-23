# language: pt
Funcionalidade: Rotação de Culturas
  Como Proprietário ou Gestor, eu quero definir um Intervalo de Descanso mínimo
  entre cultivos da mesma cultura em cada Zona para que o sistema proteja o solo da exaustão.

  Contexto:
    Dado que existe uma Zona de plantio cadastrada

  # ─── US-15 — Definir Intervalo de Descanso ───────────────────────────

  Cenário: RN-051 — Definir intervalo de descanso com histórico encerrado
    Dado que a Zona possui um ciclo encerrado da cultura "Tomate" colhido há 60 dias
    Quando o Agricultor define um intervalo de descanso de 30 dias para "Tomate"
    Então o intervalo de descanso é registrado com sucesso

  Cenário: RN-051 — Rejeitar intervalo sem histórico de ciclo encerrado
    Dado que a Zona não possui histórico de ciclo encerrado para "Alface"
    Quando o Agricultor tenta definir um intervalo de descanso de 30 dias para "Alface"
    Então o sistema rejeita com erro "INTERVALO_SEM_HISTORICO"

  Cenário: RN-052a — Rejeitar intervalo abaixo do mínimo
    Quando o Agricultor tenta criar um intervalo de descanso de 0 dias
    Então o sistema rejeita a criação do intervalo

  Cenário: RN-052b — Rejeitar intervalo acima do máximo
    Quando o Agricultor tenta criar um intervalo de descanso de 400 dias
    Então o sistema rejeita a criação do intervalo

  # ─── US-16 — Bloqueio por Intervalo de Descanso ──────────────────────

  Cenário: RN-053 — Bloquear vínculo dentro do intervalo de descanso
    Dado que a Zona possui um ciclo encerrado da cultura "Tomate" colhido há 10 dias
    E que existe um intervalo de descanso de 30 dias definido para "Tomate"
    Quando o Agricultor tenta vincular "Tomate" à Zona
    Então o sistema rejeita com erro "INTERVALO_NAO_CUMPRIDO"

  Cenário: RN-053 — Permitir vínculo após intervalo de descanso cumprido
    Dado que a Zona possui um ciclo encerrado da cultura "Tomate" colhido há 40 dias
    E que existe um intervalo de descanso de 30 dias definido para "Tomate"
    Quando o Agricultor tenta vincular "Tomate" à Zona
    Então o vínculo é permitido com sucesso

  Cenário: RN-053 — Permitir vínculo no dia exato de liberação
    Dado que a Zona possui um ciclo encerrado da cultura "Tomate" colhido há 30 dias
    E que existe um intervalo de descanso de 30 dias definido para "Tomate"
    Quando o Agricultor tenta vincular "Tomate" à Zona
    Então o vínculo é permitido com sucesso

  Cenário: RN-053 — Intervalo de descanso não bloqueia cultura diferente
    Dado que a Zona possui um ciclo encerrado da cultura "Tomate" colhido há 10 dias
    E que existe um intervalo de descanso de 30 dias definido para "Tomate"
    Quando o Agricultor tenta vincular "Alface" à Zona
    Então o vínculo é permitido com sucesso
