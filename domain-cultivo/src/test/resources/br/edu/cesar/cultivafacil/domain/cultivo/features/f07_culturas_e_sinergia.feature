# language: pt
# encoding: UTF-8
@F07
Funcionalidade: Gestão de Culturas e Matriz de Sinergia
  Como Proprietário ou Gestor
  Quero gerenciar o portfólio de Culturas da fazenda
  Para tomar decisões de plantio e consórcio embasadas em compatibilidade

  Contexto:
    Dado que estou autenticado como Gestor "Carlos"
    E que existe a Propriedade "Fazenda Boa Vista"

  @RN058
  Cenário: Cadastro aceito com nome comum e variedade válidos
    Quando cadastro uma Cultura com nome comum "Milho" e variedade "Hibrido AG-30"
    Então o sistema "aceita" o cadastro

  @RN058
  Cenário: Cadastro rejeitado com nome comum inválido
    Quando cadastro uma Cultura com nome comum "A" e variedade "Cereja"
    Então o sistema "rejeita" o cadastro

  @RN058
  Cenário: Cadastro rejeitado com variedade inválida
    Quando cadastro uma Cultura com nome comum "Milho" e variedade "A"
    Então o sistema "rejeita" o cadastro

  @RN059
  Cenário: Cadastro aceito com família botânica válida
    Quando cadastro uma Cultura com nome "Milho", variedade "AG-30" e família botânica "Poaceae"
    Então o sistema "aceita" o cadastro

  @RN059
  Cenário: Cadastro rejeitado com família botânica inválida
    Quando cadastro uma Cultura com nome "Milho", variedade "AG-30" e família botânica "A"
    Então o sistema "rejeita" o cadastro

  @RN060
  Cenário: Cadastro de Cultura customizada duplicada é rejeitado
    Dado que já existe uma Cultura customizada "Milho - AG-30" no catálogo
    Quando cadastro uma nova Cultura customizada com nome "Milho" e variedade "AG-30"
    Então o sistema rejeita o cadastro
    E a mensagem de erro contém "combinação de nome e variedade já existe no catálogo"

  @RN061
  Cenário: Compatibilidade aceita cultivos ativos no catálogo da Propriedade
    Dado que a Cultura "Milho" está "ativa" no catálogo
    E que a Cultura "Feijao" está "ativa" no catálogo
    Quando verifico a compatibilidade entre "Milho" e "Feijao"
    Então o sistema "aceita" a verificação

  @RN061
  Cenário: Compatibilidade rejeita cultivo ausente do catálogo da Propriedade
    Dado que a Cultura "Milho" está "ativa" no catálogo
    E que a Cultura "Feijao" está "ausente" no catálogo
    Quando verifico a compatibilidade entre "Milho" e "Feijao"
    Então o sistema "rejeita" a verificação

  @RN062
  Cenário: Verificação aceita exatamente dois cultivos distintos
    Quando verifico a compatibilidade com os cultivos "Milho,Feijao"
    Então o sistema "aceita" a verificação

  @RN062
  Cenário: Verificação rejeita menos de dois cultivos
    Quando verifico a compatibilidade com os cultivos "Milho"
    Então o sistema "rejeita" a verificação

  @RN062
  Cenário: Verificação rejeita mais de dois cultivos
    Quando verifico a compatibilidade com os cultivos "Milho,Feijao,Soja"
    Então o sistema "rejeita" a verificação

  @RN062
  Cenário: Verificação rejeita cultivo repetido
    Quando verifico a compatibilidade com os cultivos "Milho,Milho"
    Então o sistema "rejeita" a verificação

  @RN063
  Cenário: Compatibilidade rejeita cultivo inativo
    Dado que a Cultura "Milho" está "inativa"
    E que a Cultura "Feijao" está "ativa"
    Quando verifico a compatibilidade entre "Milho" e "Feijao"
    Então o sistema "rejeita" a verificação

  @RN064
  Cenário: Cultivo customizado com família botânica pode ser analisado
    Dado que a Cultura customizada "Milho-Custom" possui família botânica "Poaceae"
    E que a Cultura "Feijao" é nativa do catálogo
    Quando verifico a compatibilidade entre "Milho-Custom" e "Feijao"
    Então o sistema "aceita" a verificação

  @RN064
  Cenário: Cultivo customizado sem família botânica não pode ser analisado
    Dado que a Cultura customizada "Milho-Custom" possui família botânica ""
    E que a Cultura "Feijao" é nativa do catálogo
    Quando verifico a compatibilidade entre "Milho-Custom" e "Feijao"
    Então o sistema "rejeita" a verificação
