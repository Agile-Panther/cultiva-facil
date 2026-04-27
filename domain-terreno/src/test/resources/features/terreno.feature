# language: pt

Funcionalidade: Gestão de Terrenos (F-04)
  Como Proprietario ou Gestor
  Eu quero cadastrar, editar e excluir terrenos
  Para organizar o espaço físico da minha propriedade agrícola

  # ---------------------------------------------------------------------------
  # RN-029 — Nome do Terreno: 2 a 100 caracteres
  # ---------------------------------------------------------------------------

  @F04-US07-RN029
  Cenario: Cadastro de terreno com nome valido
    Dado que o agricultor possui id "550e8400-e29b-41d4-a716-446655440000"
    Quando o agricultor cadastra um terreno com nome "Fazenda Boa Vista" e area "10000" m2 solo "LATOSSOLO" e clima "TROPICAL_UMIDO"
    Entao o terreno deve ser salvo com sucesso

  @F04-US07-RN029
  Cenario: Rejeitar cadastro de terreno com nome em branco
    Dado que o agricultor possui id "550e8400-e29b-41d4-a716-446655440000"
    Quando o agricultor tenta cadastrar um terreno com nome "" e area "10000" m2 solo "LATOSSOLO" e clima "TROPICAL_UMIDO"
    Entao o sistema deve rejeitar com erro "NOME_INVALIDO"

  @F04-US07-RN029
  Cenario: Rejeitar cadastro de terreno com nome de um caractere
    Dado que o agricultor possui id "550e8400-e29b-41d4-a716-446655440000"
    Quando o agricultor tenta cadastrar um terreno com nome "A" e area "10000" m2 solo "LATOSSOLO" e clima "TROPICAL_UMIDO"
    Entao o sistema deve rejeitar com erro "NOME_INVALIDO"

  @F04-US07-RN029
  Esquema do Cenario: Rejeitar nomes fora do tamanho permitido
    Dado que o agricultor possui id "550e8400-e29b-41d4-a716-446655440000"
    Quando o agricultor tenta cadastrar um terreno com nome <nome> e area "10000" m2 solo "LATOSSOLO" e clima "TROPICAL_UMIDO"
    Entao o sistema deve rejeitar com erro "NOME_INVALIDO"

    Exemplos:
      | nome                                                                                                        |
      | "A"                                                                                                         |
      | "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" |

  # ---------------------------------------------------------------------------
  # RN-030 — Área: 50 m² a 1.000.000.000 m²
  # ---------------------------------------------------------------------------

  @F04-US07-RN030
  Cenario: Cadastro de terreno com area no limite minimo
    Dado que o agricultor possui id "550e8400-e29b-41d4-a716-446655440000"
    Quando o agricultor cadastra um terreno com nome "Horta Pequena" e area "50" m2 solo "NEOSSOLO" e clima "TROPICAL_UMIDO"
    Entao o terreno deve ser salvo com sucesso

  @F04-US07-RN030
  Cenario: Rejeitar cadastro de terreno com area abaixo do minimo
    Dado que o agricultor possui id "550e8400-e29b-41d4-a716-446655440000"
    Quando o agricultor tenta cadastrar um terreno com nome "Terreno X" e area "49" m2 solo "LATOSSOLO" e clima "TROPICAL_UMIDO"
    Entao o sistema deve rejeitar com erro "AREA_INVALIDA"

  @F04-US07-RN030
  Cenario: Rejeitar cadastro de terreno com area zero
    Dado que o agricultor possui id "550e8400-e29b-41d4-a716-446655440000"
    Quando o agricultor tenta cadastrar um terreno com nome "Terreno X" e area "0" m2 solo "LATOSSOLO" e clima "TROPICAL_UMIDO"
    Entao o sistema deve rejeitar com erro "AREA_INVALIDA"

  @F04-US07-RN030
  Cenario: Rejeitar cadastro de terreno com area acima do maximo
    Dado que o agricultor possui id "550e8400-e29b-41d4-a716-446655440000"
    Quando o agricultor tenta cadastrar um terreno com nome "Terreno X" e area "1000000001" m2 solo "LATOSSOLO" e clima "TROPICAL_UMIDO"
    Entao o sistema deve rejeitar com erro "AREA_INVALIDA"

  # ---------------------------------------------------------------------------
  # RN-031 — Tipo de Solo: SiBCS/EMBRAPA
  # ---------------------------------------------------------------------------

  @F04-US07-RN031
  Esquema do Cenario: Cadastro de terreno com tipo de solo valido
    Dado que o agricultor possui id "550e8400-e29b-41d4-a716-446655440000"
    Quando o agricultor cadastra um terreno com nome "Fazenda Solo" e area "5000" m2 solo <tipoSolo> e clima "TROPICAL_UMIDO"
    Entao o terreno deve ser salvo com sucesso

    Exemplos:
      | tipoSolo      |
      | "LATOSSOLO"   |
      | "ARGISSOLO"   |
      | "NEOSSOLO"    |
      | "CAMBISSOLO"  |
      | "GLEISSOLO"   |
      | "NITOSSOLO"   |
      | "VERTISSOLO"  |
      | "PLINTOSSOLO" |

  # ---------------------------------------------------------------------------
  # RN-032 — Clima da Região: Köppen-Geiger
  # ---------------------------------------------------------------------------

  @F04-US07-RN032
  Esquema do Cenario: Cadastro de terreno com clima valido
    Dado que o agricultor possui id "550e8400-e29b-41d4-a716-446655440000"
    Quando o agricultor cadastra um terreno com nome "Fazenda Clima" e area "5000" m2 solo "LATOSSOLO" e clima <clima>
    Entao o terreno deve ser salvo com sucesso

    Exemplos:
      | clima                      |
      | "TROPICAL_UMIDO"           |
      | "TROPICAL_SAVANICO"        |
      | "TROPICAL_ESTACAO_SECA"    |
      | "SEMIARIDO"                |
      | "SUBTROPICAL_UMIDO"        |
      | "SUBTROPICAL_ALTITUDE"     |
      | "SUBTROPICAL_INVERNO_SECO" |

  # ---------------------------------------------------------------------------
  # RN-033 — pH: 3.0 a 9.0; padrão 6.5 quando omitido
  # ---------------------------------------------------------------------------

  @F04-US07-RN033
  Cenario: Cadastro de terreno sem pH usa valor padrao 6.5
    Dado que o agricultor possui id "550e8400-e29b-41d4-a716-446655440000"
    Quando o agricultor cadastra um terreno sem informar o pH com nome "Fazenda Ph Padrao" area "5000" solo "LATOSSOLO" clima "TROPICAL_UMIDO"
    Entao o terreno deve ser salvo com pH igual a "6.5"

  @F04-US07-RN033
  Cenario: Cadastro de terreno com pH explicito valido
    Dado que o agricultor possui id "550e8400-e29b-41d4-a716-446655440000"
    Quando o agricultor cadastra um terreno com nome "Fazenda Ph Explicito" area "5000" solo "LATOSSOLO" clima "TROPICAL_UMIDO" e pH "5.5"
    Entao o terreno deve ser salvo com pH igual a "5.5"

  @F04-US07-RN033
  Esquema do Cenario: Rejeitar pH fora do intervalo permitido
    Dado que o agricultor possui id "550e8400-e29b-41d4-a716-446655440000"
    Quando o agricultor tenta cadastrar um terreno com pH <ph>
    Entao o sistema deve rejeitar com erro "PH_INVALIDO"

    Exemplos:
      | ph    |
      | "2.9" |
      | "9.1" |
      | "0.0" |

  # ---------------------------------------------------------------------------
  # RN-034 — Índice de Iluminosidade: 2 a 16 h/dia (opcional)
  # ---------------------------------------------------------------------------

  @F04-US07-RN034
  Cenario: Cadastro de terreno sem indice de iluminosidade
    Dado que o agricultor possui id "550e8400-e29b-41d4-a716-446655440000"
    Quando o agricultor cadastra um terreno sem informar o indice de iluminosidade com nome "Fazenda Sem Luz" area "5000" solo "LATOSSOLO" clima "TROPICAL_UMIDO"
    Entao o terreno deve ser salvo sem indice de iluminosidade

  @F04-US07-RN034
  Cenario: Cadastro de terreno com indice de iluminosidade valido
    Dado que o agricultor possui id "550e8400-e29b-41d4-a716-446655440000"
    Quando o agricultor cadastra um terreno com indice de iluminosidade "8" horas nome "Fazenda Ensolarada" area "5000" solo "LATOSSOLO" clima "TROPICAL_UMIDO"
    Entao o terreno deve ser salvo com indice de iluminosidade "8" horas

  @F04-US07-RN034
  Esquema do Cenario: Rejeitar indice de iluminosidade fora do intervalo
    Dado que o agricultor possui id "550e8400-e29b-41d4-a716-446655440000"
    Quando o agricultor tenta cadastrar um terreno com indice de iluminosidade <indice> horas
    Entao o sistema deve rejeitar com erro "INDICE_ILUMINOSIDADE_INVALIDO"

    Exemplos:
      | indice |
      | "1"    |
      | "17"   |
      | "0"    |

  # ---------------------------------------------------------------------------
  # RN-035 — Edição: mesmas regras de validação do cadastro
  # ---------------------------------------------------------------------------

  @F04-US08-RN035
  Cenario: Editar nome do terreno com valor valido
    Dado que existe um terreno cadastrado com nome "Fazenda Original"
    Quando o agricultor atualiza o nome para "Fazenda Atualizada"
    Entao o terreno deve ter o nome "Fazenda Atualizada"

  @F04-US08-RN035
  Cenario: Rejeitar edicao de nome com valor invalido
    Dado que existe um terreno cadastrado com nome "Fazenda Original"
    Quando o agricultor tenta atualizar o nome para "A"
    Entao o sistema deve rejeitar com erro "NOME_INVALIDO"

  @F04-US08-RN035
  Cenario: Editar pH do terreno com valor valido
    Dado que existe um terreno cadastrado com nome "Fazenda Original"
    Quando o agricultor atualiza o pH para "7.0"
    Entao o terreno deve ter pH "7.0"

  @F04-US08-RN035
  Cenario: Rejeitar edicao de pH com valor fora do intervalo
    Dado que existe um terreno cadastrado com nome "Fazenda Original"
    Quando o agricultor tenta atualizar o pH para "10.0"
    Entao o sistema deve rejeitar com erro "PH_INVALIDO"

  # ---------------------------------------------------------------------------
  # RN-036 — Área não pode ser reduzida abaixo da soma das zonas
  # ---------------------------------------------------------------------------

  @F04-US08-RN036
  Cenario: Rejeitar reducao de area abaixo da soma das zonas
    Dado que existe um terreno cadastrado com area "10000" m2
    E a soma das areas das zonas do terreno e "8000" m2
    Quando o agricultor tenta reduzir a area do terreno para "5000" m2
    Entao o sistema deve rejeitar com erro "AREA_MENOR_QUE_ZONAS"

  @F04-US08-RN036
  Cenario: Permitir atualizacao de area maior que a soma das zonas
    Dado que existe um terreno cadastrado com area "10000" m2
    E a soma das areas das zonas do terreno e "8000" m2
    Quando o agricultor atualiza a area do terreno para "12000" m2
    Entao o terreno deve ter area "12000" m2

  @F04-US08-RN036
  Cenario: Permitir atualizacao de area igual a soma das zonas
    Dado que existe um terreno cadastrado com area "10000" m2
    E a soma das areas das zonas do terreno e "8000" m2
    Quando o agricultor atualiza a area do terreno para "8000" m2
    Entao o terreno deve ter area "8000" m2

  # ---------------------------------------------------------------------------
  # RN-037 — Exclusão: bloqueada se há cultivo ativo em alguma zona
  # ---------------------------------------------------------------------------

  @F04-US09-RN037
  Cenario: Excluir terreno sem cultivo ativo
    Dado que existe um terreno cadastrado sem cultivo ativo
    Quando o agricultor solicita a exclusao do terreno
    Entao o terreno deve ser excluido com sucesso

  @F04-US09-RN037
  Cenario: Rejeitar exclusao de terreno com cultivo ativo
    Dado que existe um terreno cadastrado com cultivo ativo em uma zona
    Quando o agricultor solicita a exclusao do terreno
    Entao o sistema deve rejeitar com erro "TERRENO_COM_CULTIVO_ATIVO"
