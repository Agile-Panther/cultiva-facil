# language: pt

Funcionalidade: Zonas de Plantio
  Como Proprietario ou Gestor
  Eu quero criar e gerenciar Talhoes dentro de um Terreno
  Para organizar o uso do espaco disponivel para cultivo

  @F05-US10-RN038
  Cenario: Criacao de 21a Zona rejeitada
    Dado que o Terreno ja possui 20 Talhoes cadastrados
    Quando o Agricultor tenta criar um novo Talhao no mesmo Terreno
    Entao o sistema deve rejeitar com erro "LIMITE_ZONAS_EXCEDIDO"

  @F05-US10-RN039
  Cenario: Area da Zona superior ao Terreno rejeitada
    Dado que o Terreno possui area total de 1000 m2
    Quando o Agricultor tenta criar um Talhao com area de 1200 m2
    Entao o sistema deve rejeitar com erro "AREA_ZONA_INVALIDA"

  @F05-US10-RN039b
  Esquema do Cenario: Area de Zona invalida rejeitada no cadastro
    Dado que o Agricultor tenta criar um Talhao com area "<area>" m2
    Quando submete o cadastro de area
    Entao o sistema deve rejeitar com erro "AREA_ZONA_INVALIDA"

    Exemplos:
      | area    |
      | 0.00    |
      | -10.00  |
      | 100.123 |

  @F05-US10-RN040a
  Cenario: Nome da Zona invalido rejeitado por ser muito curto
    Dado que o Agricultor tenta criar um Talhao com nome de 1 caractere
    Quando submete o cadastro
    Entao o sistema deve rejeitar com erro "NOME_ZONA_INVALIDO"

  @F05-US10-RN040a
  Cenario: Nome da Zona invalido rejeitado por ser muito longo
    Dado que o Agricultor tenta criar um Talhao com nome de 101 caracteres
    Quando submete o cadastro
    Entao o sistema deve rejeitar com erro "NOME_ZONA_INVALIDO"

  @F05-US10-RN040b
  Cenario: Nome de Zona duplicado no Terreno rejeitado
    Dado que o Terreno ja possui um Talhao chamado "Canteiro A"
    Quando o Agricultor tenta criar outro Talhao com o mesmo nome no mesmo Terreno
    Entao o sistema deve rejeitar com erro "NOME_ZONA_DUPLICADO"

  @F05-US10
  Cenario: Talhao criado com sucesso
    Dado que o Terreno possui menos de 20 Talhoes e area disponivel suficiente
    Quando o Agricultor cria um Talhao com nome "Canteiro Norte" e area 500 m2
    Entao o Talhao e criado com situacao "DISPONIVEL" e associado ao Terreno

  @F05-US11-RN041
  Cenario: Remocao de Talhao com Cultivo ativo rejeitada
    Dado que o Talhao possui situacao "EM_USO"
    Quando o Agricultor tenta remover o Talhao
    Entao o sistema deve rejeitar com erro "ZONA_COM_CULTIVO_ATIVO"

  @F05-US11
  Cenario: Talhao sem cultivo ativo removido com sucesso
    Dado que o Talhao possui situacao "DISPONIVEL"
    Quando o Agricultor confirma a remocao do Talhao
    Entao o Talhao e removido do Terreno

  @F05-US11-edit
  Cenario: Edicao de dados da Zona realizada com sucesso
    Dado que o Talhao existe com nome "Canteiro Norte" e area 500 m2
    Quando o Agricultor edita a area do Talhao para 750 m2
    Entao o Talhao deve ter area de 750 m2

  @F05-US11-RN042
  Cenario: Area editada da Zona excede o Terreno
    Dado que o Terreno possui area total de 800 m2
    Quando o Agricultor tenta editar o Talhao para 900 m2
    Entao o sistema deve rejeitar com erro "AREA_ZONA_INVALIDA"

  @F05-US11-RN042
  Esquema do Cenario: Multiplas combinacoes de area invalida na edicao
    Dado que o Terreno possui area total de <areaTerrenoM2> m2
    Quando o Agricultor tenta editar o Talhao para <areaTalhaoM2> m2
    Entao o sistema deve rejeitar com erro "AREA_ZONA_INVALIDA"

    Exemplos:
      | areaTerrenoM2 | areaTalhaoM2 |
      | 500           | 600          |
      | 200           | 350          |
