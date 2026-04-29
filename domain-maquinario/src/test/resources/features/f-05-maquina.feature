# language: pt
Funcionalidade: Gestão de Maquinário e Manutenção Preventiva
  Como Proprietário, Gestor ou Peão
  Quero registrar e operar a frota agrícola da Propriedade
  Para manter o maquinário disponível e antecipar manutenções

  Contexto:
    Dado que estou autenticado como Gestor "Carlos"
    E que existe a Propriedade "Fazenda Boa Vista"

  # ============================================================
  # US-13 · Cadastro de Maquinário
  # ============================================================

  # RN-043 — Tipo de maquinário em conjunto fechado
  Esquema do Cenário: Validação do tipo de maquinário
    Quando cadastro um maquinário do tipo "<tipo>" com identificador "PLC-001" e horímetro 0
    Então o sistema "<resultado>" o cadastro
    Exemplos:
      | tipo            | resultado |
      | Trator          | aceita    |
      | Colheitadeira   | aceita    |
      | Pulverizador    | aceita    |
      | Plantadeira     | aceita    |
      | ImplementoGeral | aceita    |
      | Caminhão        | rejeita   |
      | Carreta         | rejeita   |

  # RN-044 — Status inicial é Disponível
  Cenário: Maquinário recém-cadastrado assume status Disponível
    Quando cadastro um maquinário do tipo "Trator" com identificador "PLC-002" e horímetro 100
    Então o sistema aceita o cadastro
    E o status de integridade do maquinário é "Disponível"

  # RN-045 — Horímetro inicial >= 0
  Esquema do Cenário: Validação do horímetro inicial
    Quando cadastro um maquinário do tipo "Trator" com identificador "PLC-003" e horímetro "<horimetro>"
    Então o sistema "<resultado>" o cadastro
    Exemplos:
      | horimetro | resultado |
      | 0         | aceita    |
      | 100.5     | aceita    |
      | 9999.99   | aceita    |
      | -1        | rejeita   |
      | -0.01     | rejeita   |

  # RN-046 — Placa ou Número de Série único na Propriedade
  Cenário: Cadastro com identificador duplicado é rejeitado
    Dado que já existe um maquinário com Placa "PLC-100" na Propriedade
    Quando cadastro um novo maquinário com Placa "PLC-100"
    Então o sistema rejeita o cadastro
    E a mensagem de erro contém "Placa ou Número de Série já cadastrado"

  # ============================================================
  # US-14 · Apontamento de Uso e Previsão de Manutenção
  # ============================================================

  # RN-047 — Novo horímetro estritamente maior
  Esquema do Cenário: Validação do novo horímetro no apontamento
    Dado que existe o maquinário "PLC-001" com horímetro atual de 500 horas e status "Disponível"
    Quando registro apontamento com horímetro "<novo_horimetro>"
    Então o sistema "<resultado>" o apontamento
    Exemplos:
      | novo_horimetro | resultado |
      | 501            | aceita    |
      | 750            | aceita    |
      | 500            | rejeita   |
      | 499            | rejeita   |
      | 0              | rejeita   |

  # RN-048 — Apontamento só em maquinário Disponível
  Esquema do Cenário: Apontamento bloqueado conforme status do maquinário
    Dado que existe o maquinário "PLC-002" com status "<status>"
    Quando registro apontamento com horímetro 600
    Então o sistema "<resultado>" o apontamento
    Exemplos:
      | status         | resultado |
      | Disponível     | aceita    |
      | Em Manutenção  | rejeita   |
      | Inoperante     | rejeita   |

  # RN-049 — Sistema projeta data de manutenção
  Cenário: Apontamento dispara projeção de data de manutenção
    Dado que existe o maquinário "PLC-001" com Marca "John Deere" e Modelo "5075E"
    E que a matriz de desgaste de fábrica define limite de 250 horas
    E que a média de uso recente é de 5 horas por dia
    Quando registro apontamento com horímetro 100
    Então o sistema aceita o apontamento
    E o sistema calcula a Data Estimada para a próxima manutenção
    E a Data Estimada é coerente com o limite de fábrica e a média de uso

  # RN-050 — Alerta de Manutenção Iminente
  Esquema do Cenário: Disparo do Alerta de Manutenção Iminente
    Dado que existe o maquinário "PLC-001" com limite de fábrica de 250 horas
    E que o horímetro alvo é <horimetro>
    E que a Data Estimada calculada é em 30 dias
    Quando registro um novo apontamento com o horímetro alvo
    Então o sistema <emite> o Alerta de Manutenção Iminente
    Exemplos:
      | horimetro | emite      |
      | 200       | não emite  |
      | 201       | emite      |
