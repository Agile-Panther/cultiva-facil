# language: pt
Funcionalidade: Gerenciamento de Focos Fitossanitários
  Como um usuário do sistema (Engenheiro Agrônomo, Técnico Agrícola)
  Eu quero registrar e consultar focos fitossanitários
  Para monitorar a saúde da lavoura e tomar ações de manejo

  Contexto:
    Dado que o usuário está autenticado no sistema
    E possui permissão para gerenciar focos fitossanitários

  @US-20
  Cenário: Registro de um novo foco fitossanitário com sucesso
    Dado que o usuário informa os seguintes dados para um novo foco:
      | campo           | valor                               |
      | Zona            | "Zona de Irrigação 1"               |
      | Ciclo Agrícola  | "Ciclo 2025/2 - Soja"               |
      | Tipo            | "PRAGA"                             |
      | Nível           | "BAIXO"                             |
      | Severidade      | "BAIXA"                             |
      | Descrição       | "Presença de pulgões nas folhas..." |
    Quando o usuário solicita o registro do novo foco
    Então o sistema deve criar um novo foco fitossanitário com um ID único
    E o sistema deve registrar um evento de domínio "FocoRegistrado"
    E o sistema deve exibir a mensagem "Foco fitossanitário registrado com sucesso!"

  @US-21
  Cenário: Tentativa de registro de foco com descrição inválida (curta)
    Dado que o usuário informa os seguintes dados para um novo foco:
      | campo           | valor                 |
      | Zona            | "Zona de Irrigação 2" |
      | Ciclo Agrícola  | "Ciclo 2025/2 - Soja" |
      | Tipo            | "DOENCA"              |
      | Nível           | "MEDIO"               |
      | Severidade      | "MEDIA"               |
      | Descrição       | "Curta"               |
    Quando o usuário solicita o registro do novo foco
    Então o sistema deve rejeitar a operação
    E deve exibir a mensagem de erro "A descrição deve ter entre 10 e 500 caracteres."

  @US-21
  Cenário: Tentativa de registro de foco com campo obrigatório nulo
    Dado que o usuário informa os seguintes dados para um novo foco, deixando o tipo em branco:
      | campo           | valor                 |
      | Zona            | "Zona de Irrigação 3" |
      | Ciclo Agrícola  | "Ciclo 2025/2 - Soja" |
      | Tipo            | null                  |
      | Nível           | "ALTO"                |
      | Severidade      | "ALTA"                |
      | Descrição       | "Descrição válida."   |
    Quando o usuário solicita o registro do novo foco
    Então o sistema deve rejeitar a operação
    E deve exibir a mensagem de erro "O campo 'Tipo' é obrigatório."

