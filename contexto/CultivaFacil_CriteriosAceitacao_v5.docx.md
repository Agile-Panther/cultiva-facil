# **CULTIVA FÁCIL**

**Critérios de Aceitação**

BDD  ·  Dado que / Quando / Então  ·   Abril 2026  ·  v5

| Este documento especifica os critérios de aceitação de todas as User Stories do MVP do Cultiva Fácil no formato BDD (Behavior-Driven Development). Para cada Regra de Negócio existe ao menos um cenário negativo que valida o comportamento de rejeição do sistema. *OBS: os cenários positivos (✓) documentam o fluxo feliz da User Story e não precisam ser implementados como testes automatizados isolados — seu propósito é registrar o contexto de sucesso esperado.* |
| :---- |

**Convenção de identificação dos cenários:**

✓  Cenários positivos: fluxo feliz da User Story.

✗  RN-NNN — Cenários negativos: um cenário por Regra de Negócio, identificado pela RN correspondente.

Sub-letras (a/b/c): quando uma RN possui múltiplas condições de rejeição distintas.

| Épico 1 — Autenticação e Acesso |
| :---- |

| F-01  ·  Cadastro de Conta |
| :---- |
| *Para acessar a plataforma, o Agricultor cria uma conta com e-mail, senha e consentimento com os termos de uso.* |

| US-01  ·  4 SP     Como Agricultor, eu quero criar minha conta com e-mail e senha para ter acesso seguro e personalizado à plataforma. |
| :---- |

| ✓  Conta criada com sucesso |
| :---- |

| Dado que | o Agricultor informa um e-mail não cadastrado, senha "Senha123" e marca o consentimento como verdadeiro |
| :---- | :---- |
| **Quando** | submete o formulário de cadastro |
| **Então** | a conta é criada com status ativo e o Agricultor é direcionado para o onboarding da Propriedade |

| ✗  RN-001   E-mail duplicado rejeitado |
| :---- |

| Dado que | o e-mail "joao@email.com" já existe na base de dados |
| :---- | :---- |
| **Quando** | um novo Agricultor tenta se cadastrar com o mesmo e-mail |
| **Então** | o sistema rejeita o cadastro com erro EMAIL\_JA\_CADASTRADO |

| ✗  RN-002a   Senha com menos de 8 caracteres rejeitada |
| :---- |

| Dado que | o Agricultor informa a senha "abc12" (5 caracteres, abaixo do mínimo de 8\) |
| :---- | :---- |
| **Quando** | submete o formulário de cadastro |
| **Então** | o sistema rejeita com erro SENHA\_INVALIDA |

| ✗  RN-002b   Senha sem número rejeitada |
| :---- |

| Dado que | o Agricultor informa a senha "abcdefgh" (8 caracteres, sem nenhum número) |
| :---- | :---- |
| **Quando** | submete o formulário de cadastro |
| **Então** | o sistema rejeita com erro SENHA\_INVALIDA |

| ✗  RN-003   Senha idêntica ao e-mail rejeitada |
| :---- |

| Dado que | o Agricultor informa e-mail "abc@abc.com" e senha "abc@abc.com" |
| :---- | :---- |
| **Quando** | submete o formulário de cadastro |
| **Então** | o sistema rejeita com erro SENHA\_IGUAL\_EMAIL |

| ✗  RN-004   Cadastro sem consentimento rejeitado |
| :---- |

| Dado que | o Agricultor preenche e-mail e senha válidos, mas não marca o consentimento com os termos |
| :---- | :---- |
| **Quando** | submete o formulário de cadastro |
| **Então** | a conta não é ativada e o sistema rejeita com erro CONSENTIMENTO\_AUSENTE |

| F-02  ·  Perfil da Propriedade |
| :---- |
| *No primeiro acesso, o Agricultor configura os três dados essenciais que habilitam todas as funcionalidades do sistema.* |

| US-49  ·  3 SP     Como Proprietário ou Gestor, eu quero convidar um usuário por e-mail atribuindo um perfil para que ele acesse a Propriedade com as permissões corretas desde o primeiro acesso. |
| :---- |

| ✓  Convite enviado e usuário ativado com sucesso |
| :---- |

| Dado que | o Gestor informa o e-mail "novo@email.com", seleciona o perfil "Operador" e atribui a Zona "Canteiro Norte" |
| :---- | :---- |
| **Quando** | o Gestor confirma o convite |
| **Então** | o sistema envia link de ativação para "novo@email.com" válido por 72 horas para que o usuário defina sua própria senha |

| ✗  RN-005   Link de ativação expirado rejeitado |
| :---- |

| Dado que | o usuário convidado recebeu o link de ativação há mais de 72 horas sem ter ativado a conta |
| :---- | :---- |
| **Quando** | o usuário tenta acessar o link de ativação |
| **Então** | o sistema rejeita com erro LINK\_CONVITE\_EXPIRADO e orienta o usuário a solicitar novo convite |

| ✗  RN-006   Gestor tenta convidar com perfil Gestor rejeitado |
| :---- |

| Dado que | um Gestor está logado e tenta convidar um novo usuário com o perfil "Gestor" |
| :---- | :---- |
| **Quando** | o Gestor confirma o convite |
| **Então** | o sistema rejeita com erro PERFIL\_NAO\_PERMITIDO — Gestores só podem convidar Operadores e Financeiros |

| ✗  RN-007   E-mail já cadastrado vinculado sem criar nova conta |
| :---- |

| Dado que | o e-mail "existente@email.com" já possui conta ativa na plataforma |
| :---- | :---- |
| **Quando** | o Proprietário convida "existente@email.com" com perfil "Financeiro" |
| **Então** | o sistema vincula a conta existente à Propriedade com o perfil "Financeiro" sem criar nova conta |

| ✗  RN-008   Perfil inválido no convite rejeitado |
| :---- |

| Dado que | o Proprietário informa o perfil "Administrador" (fora do conjunto aceito: Gestor, Operador, Financeiro) |
| :---- | :---- |
| **Quando** | tenta confirmar o convite |
| **Então** | o sistema rejeita com erro PERFIL\_INVALIDO |

| ✗  RN-009   Tentativa de atribuir perfil Proprietário por convite rejeitada |
| :---- |

| Dado que | o Proprietário tenta convidar um usuário com o perfil "Proprietário" |
| :---- | :---- |
| **Quando** | tenta confirmar o convite |
| **Então** | o sistema rejeita com erro PERFIL\_NAO\_PERMITIDO — o perfil Proprietário é concedido exclusivamente ao criador da Propriedade |

| ✗  RN-010   Convite de Operador sem Zona atribuída rejeitado |
| :---- |

| Dado que | o Gestor informa e-mail e seleciona perfil "Operador" mas não atribui nenhuma Zona |
| :---- | :---- |
| **Quando** | tenta confirmar o convite |
| **Então** | o sistema rejeita com erro ZONA\_OBRIGATORIA\_PARA\_OPERADOR |

| US-50  ·  3 SP     Como Proprietário ou Gestor, eu quero editar o perfil ou revogar o acesso de um usuário da Propriedade para manter o controle de quem opera o sistema. |
| :---- |

| ✓  Perfil de usuário editado com sucesso |
| :---- |

| Dado que | o Proprietário seleciona o usuário "Maria" com perfil atual "Financeiro" |
| :---- | :---- |
| **Quando** | o Proprietário altera o perfil para "Gestor" e confirma |
| **Então** | o sistema salva a alteração e o novo perfil entra em vigor na próxima sessão de Maria |

| ✗  RN-011   Gestor tenta editar outro Gestor rejeitado |
| :---- |

| Dado que | um Gestor tenta alterar o perfil de outro usuário com perfil "Gestor" |
| :---- | :---- |
| **Quando** | o Gestor confirma a alteração |
| **Então** | o sistema rejeita com erro ACAO\_NAO\_PERMITIDA — Gestores só podem editar Operadores e Financeiros |

| ✗  RN-012   Revogação de acesso do Proprietário rejeitada |
| :---- |

| Dado que | qualquer usuário tenta revogar o acesso do Proprietário da Propriedade |
| :---- | :---- |
| **Quando** | confirma a revogação |
| **Então** | o sistema rejeita com erro PROPRIETARIO\_INAMOVIVEL |

| ✗  RN-013   Alteração de perfil entra em vigor apenas na próxima sessão |
| :---- |

| Dado que | o Proprietário altera o perfil do usuário "João" de "Gestor" para "Operador" enquanto João está com sessão ativa |
| :---- | :---- |
| **Quando** | João realiza uma operação restrita ao perfil Gestor na sessão atual |
| **Então** | o sistema permite a operação na sessão ativa; o novo perfil será aplicado somente após João encerrar e reiniciar a sessão |

| ✗  RN-014   Realocação de Zona de Operador entra em vigor imediatamente |
| :---- |

| Dado que | o Gestor altera a Zona atribuída do Operador "Carlos" de "Canteiro Norte" para "Canteiro Sul" enquanto Carlos está com sessão ativa |
| :---- | :---- |
| **Quando** | Carlos tenta registrar um Foco em "Canteiro Norte" |
| **Então** | o sistema rejeita o acesso de Carlos a "Canteiro Norte" imediatamente após a realocação |

| US-02 / US-03  ·  7 SP     Como Agricultor, eu quero configurar o Perfil da Propriedade para desbloquear todas as funcionalidades do sistema. |
| :---- |

| ✓  Perfil configurado e transicionado para Completo |
| :---- |

| Dado que | o Agricultor possui conta ativa sem Propriedade cadastrada |
| :---- | :---- |
| **Quando** | informa município "Recife", estado "PE", tipo de solo "Latossolo" e clima "Semiárido" e confirma |
| **Então** | o Perfil da Propriedade transiciona para o estado Completo e todas as funcionalidades são desbloqueadas |

| ✗  RN-015   Segunda Propriedade rejeitada |
| :---- |

| Dado que | o Agricultor já possui uma Propriedade ativa cadastrada |
| :---- | :---- |
| **Quando** | tenta criar uma segunda Propriedade |
| **Então** | o sistema rejeita com erro PROPRIEDADE\_JA\_EXISTE |

| ✗  RN-016a   Localização com apenas município rejeitada |
| :---- |

| Dado que | o Agricultor informa apenas o município, sem o estado |
| :---- | :---- |
| **Quando** | tenta salvar o Perfil da Propriedade |
| **Então** | o sistema rejeita com erro LOCALIZACAO\_INCOMPLETA |

| ✗  RN-016b   Localização com apenas estado rejeitada |
| :---- |

| Dado que | o Agricultor informa apenas o estado, sem o município |
| :---- | :---- |
| **Quando** | tenta salvar o Perfil da Propriedade |
| **Então** | o sistema rejeita com erro LOCALIZACAO\_INCOMPLETA |

| ✗  RN-017   Tipo de solo inválido rejeitado |
| :---- |

| Dado que | o Agricultor informa o tipo de solo "Pedregoso" (fora do conjunto aceito pelo SiBCS/EMBRAPA) |
| :---- | :---- |
| **Quando** | tenta salvar o Perfil |
| **Então** | o sistema rejeita com erro TIPO\_SOLO\_INVALIDO |

| ✗  RN-018   Clima inválido rejeitado |
| :---- |

| Dado que | o Agricultor informa o clima "Árido" (fora dos subtipos Köppen-Geiger aceitos para o Brasil) |
| :---- | :---- |
| **Quando** | tenta salvar o Perfil |
| **Então** | o sistema rejeita com erro CLIMA\_INVALIDO |

| ✗  RN-019   Transição para Completo com campo ausente rejeitada |
| :---- |

| Dado que | o Agricultor informou município, estado e tipo de solo, mas não informou o clima predominante |
| :---- | :---- |
| **Quando** | tenta confirmar o Perfil da Propriedade |
| **Então** | o sistema rejeita a transição com erro PERFIL\_INCOMPLETO e mantém o estado Incompleto |

| ✗  RN-020   Funcionalidade bloqueada com Perfil Incompleto |
| :---- |

| Dado que | o Agricultor possui conta ativa com Perfil da Propriedade no estado Incompleto |
| :---- | :---- |
| **Quando** | tenta acessar o calendário de tarefas |
| **Então** | o sistema bloqueia o acesso e informa que o Perfil da Propriedade precisa ser completado |

| ✗  RN-021   Edição do Perfil com campo ausente rejeitada |
| :---- |

| Dado que | o Agricultor possui Perfil da Propriedade no estado Incompleto com município e tipo de solo preenchidos, mas sem clima predominante |
| :---- | :---- |
| **Quando** | tenta confirmar a atualização do Perfil |
| **Então** | o sistema rejeita a transição com erro PERFIL\_INCOMPLETO e mantém o estado Incompleto |

| ✗  RN-022   Acesso a funcionalidade bloqueado enquanto Perfil estiver Incompleto |
| :---- |

| Dado que | o Agricultor possui conta ativa com Perfil da Propriedade no estado Incompleto |
| :---- | :---- |
| **Quando** | tenta acessar o módulo de alertas climáticos |
| **Então** | o sistema bloqueia o acesso e informa que o Perfil da Propriedade precisa ser completado |

| F-03  ·  Perfil do Agricultor e Notificações |
| :---- |
| *⚙  FEATURE COMPLEXA — Incorpora: F-16 · Configuração de Notificações* |
| *O Agricultor personaliza seus dados, preferências de exibição e configurações de notificação que moldam a experiência individual na plataforma.* |

| US-04  ·  2 SP     Como Agricultor, eu quero editar meus dados pessoais de perfil para mantê-los atualizados na plataforma. |
| :---- |

| ✓  Nome e foto atualizados com sucesso |
| :---- |

| Dado que | o Agricultor acessa a edição do perfil pessoal |
| :---- | :---- |
| **Quando** | informa o nome "Maria da Silva" e envia uma foto PNG de 2 MB |
| **Então** | o perfil é atualizado com o novo nome e foto |

| ✗  RN-023a   Nome com menos de 2 caracteres rejeitado |
| :---- |

| Dado que | o Agricultor tenta salvar o nome "M" (1 caractere) |
| :---- | :---- |
| **Quando** | submete a edição do perfil |
| **Então** | o sistema rejeita com erro NOME\_INVALIDO |

| ✗  RN-024a   Foto em formato inválido rejeitada |
| :---- |

| Dado que | o Agricultor envia um arquivo ".gif" como foto de perfil |
| :---- | :---- |
| **Quando** | submete a edição do perfil |
| **Então** | o sistema rejeita com erro FOTO\_FORMATO\_INVALIDO |

| ✗  RN-024b   Foto acima de 5 MB rejeitada |
| :---- |

| Dado que | o Agricultor envia um arquivo PNG de 6 MB como foto de perfil |
| :---- | :---- |
| **Quando** | submete a edição do perfil |
| **Então** | o sistema rejeita com erro FOTO\_TAMANHO\_EXCEDIDO |

| US-05  ·  2 SP     Como Agricultor, eu quero definir minhas preferências de exibição e agendamento de resumo para que o sistema se adapte à minha rotina. |
| :---- |

| ✓  Preferências de exibição salvas com sucesso |
| :---- |

| Dado que | o Agricultor define área em "ha", valor 1,50 e horário de resumo diário "07:00" |
| :---- | :---- |
| **Quando** | salva as preferências |
| **Então** | as preferências são persistidas e aplicadas na interface |

| ✗  RN-025a   Valor de área negativo rejeitado |
| :---- |

| Dado que | o Agricultor tenta salvar o valor de área −1,5 (negativo) |
| :---- | :---- |
| **Quando** | submete as preferências |
| **Então** | o sistema rejeita com erro VALOR\_AREA\_INVALIDO |

| ✗  RN-025b   Valor de área com mais de 2 casas decimais rejeitado |
| :---- |

| Dado que | o Agricultor tenta salvar o valor de área 1,567 (3 casas decimais) |
| :---- | :---- |
| **Quando** | submete as preferências |
| **Então** | o sistema rejeita com erro VALOR\_AREA\_INVALIDO |

| ✗  RN-026   Horário de resumo fora do intervalo rejeitado |
| :---- |

| Dado que | o Agricultor tenta salvar horário de resumo "04:00" (abaixo do mínimo de 05:00) |
| :---- | :---- |
| **Quando** | submete as preferências |
| **Então** | o sistema rejeita com erro HORARIO\_RESUMO\_INVALIDO |

| US-06  ·  2 SP     Como Agricultor, eu quero configurar meus canais e tipos de notificação para receber os avisos certos no momento adequado. |
| :---- |

| ✓  Configuração de notificação salva com sucesso |
| :---- |

| Dado que | o Agricultor seleciona tipo "TarefaAtrasada" e canal "Push" e confirma |
| :---- | :---- |
| **Quando** | salva a configuração de notificação |
| **Então** | a configuração é persistida e aplicada a partir da próxima verificação do sistema |

| ✗  RN-027   Tipo de notificação inválido rejeitado |
| :---- |

| Dado que | o Agricultor tenta configurar tipo "Notícias" (fora do conjunto aceito: ResumoDiario, TarefaAtrasada, AlertaCritico) |
| :---- | :---- |
| **Quando** | submete a configuração |
| **Então** | o sistema rejeita com erro TIPO\_NOTIFICACAO\_INVALIDO |

| ✗  RN-028   Mais de 5 notificações do mesmo tipo na mesma data rejeitadas |
| :---- |

| Dado que | o Agricultor já possui 5 notificações do tipo "ResumoDiario" registradas para hoje |
| :---- | :---- |
| **Quando** | o sistema tenta registrar mais uma notificação do tipo "ResumoDiario" para o mesmo Agricultor |
| **Então** | o sistema rejeita com erro LIMITE\_NOTIFICACOES\_EXCEDIDO |

| Épico 2 — Gestão de Terrenos e Zonas |
| :---- |

| F-04  ·  Terrenos |
| :---- |
| *O Agricultor cadastra e gerencia os Terrenos da Propriedade, informando área, tipo de solo, clima e dados opcionais de pH e luminosidade que orientam recomendações agronômicas.* |

| US-07  ·  3 SP     Como Agricultor, eu quero cadastrar um novo Terreno com nome, área, tipo de solo, clima predominante, pH e índice de luminosidade para que o sistema possa oferecer recomendações baseadas nas características físicas do espaço. |
| :---- |

| ✓  Terreno cadastrado com sucesso |
| :---- |

| Dado que | o Agricultor informa nome "Lote Principal", área 5.000 m², tipo de solo "Latossolo", clima "Semiárido", pH 6,5 e luminosidade 10 h |
| :---- | :---- |
| **Quando** | cadastra o Terreno |
| **Então** | o Terreno é criado com sucesso e associado à Propriedade do Agricultor |

| ✗  RN-029   Nome do Terreno inválido rejeitado |
| :---- |

| Dado que | o Agricultor tenta cadastrar um Terreno com nome de apenas 1 caractere |
| :---- | :---- |
| **Quando** | submete o cadastro |
| **Então** | o sistema rejeita com erro NOME\_TERRENO\_INVALIDO |

| ✗  RN-030   Área do Terreno fora do intervalo rejeitada |
| :---- |

| Dado que | o Agricultor informa área de 10 m² (abaixo do mínimo de 50 m²) |
| :---- | :---- |
| **Quando** | submete o cadastro |
| **Então** | o sistema rejeita com erro AREA\_TERRENO\_INVALIDA |

| ✗  RN-031   Tipo de solo inválido no Terreno rejeitado |
| :---- |

| Dado que | o Agricultor informa tipo de solo "Arenoso" (fora do conjunto aceito pelo SiBCS/EMBRAPA) |
| :---- | :---- |
| **Quando** | submete o cadastro do Terreno |
| **Então** | o sistema rejeita com erro TIPO\_SOLO\_INVALIDO |

| ✗  RN-032   Clima inválido no Terreno rejeitado |
| :---- |

| Dado que | o Agricultor informa clima "Tropical Seco" (fora dos subtipos Köppen-Geiger aceitos) |
| :---- | :---- |
| **Quando** | submete o cadastro do Terreno |
| **Então** | o sistema rejeita com erro CLIMA\_INVALIDO |

| ✗  RN-033   pH fora do intervalo aceito rejeitado |
| :---- |

| Dado que | o Agricultor informa pH 9,5 (acima do limite máximo de 9,0) ao cadastrar um Terreno |
| :---- | :---- |
| **Quando** | tenta salvar o Terreno |
| **Então** | o sistema rejeita com erro PH\_INVALIDO |

| ✗  RN-034   Índice de luminosidade fora do intervalo aceito rejeitado |
| :---- |

| Dado que | o Agricultor informa índice de luminosidade 18 horas/dia (acima do máximo de 16\) ao cadastrar um Terreno |
| :---- | :---- |
| **Quando** | tenta salvar o Terreno |
| **Então** | o sistema rejeita com erro LUMINOSIDADE\_INVALIDA |

| US-08  ·  2 SP     Como Agricultor, eu quero editar os dados de um Terreno para corrigir ou atualizar suas informações. |
| :---- |

| ✓  Terreno editado com sucesso |
| :---- |

| Dado que | o Agricultor seleciona o Terreno "Lote Principal" e atualiza o clima para "Subtropical Úmido" |
| :---- | :---- |
| **Quando** | salva as alterações |
| **Então** | os dados do Terreno são atualizados com sucesso |

| ✗  RN-035   Validações do cadastro aplicadas na edição |
| :---- |

| Dado que | o Agricultor tenta editar o tipo de solo do Terreno para "Pedregoso" (fora do conjunto aceito) |
| :---- | :---- |
| **Quando** | submete a edição |
| **Então** | o sistema rejeita com erro TIPO\_SOLO\_INVALIDO — as mesmas validações do cadastro se aplicam à edição |

| ✗  RN-036   Redução de área abaixo da soma das Zonas rejeitada |
| :---- |

| Dado que | o Terreno possui Zonas com área total de 3.000 m² e o Agricultor tenta reduzir a área do Terreno para 2.500 m² |
| :---- | :---- |
| **Quando** | submete a edição da área |
| **Então** | o sistema rejeita com erro AREA\_INSUFICIENTE\_PARA\_ZONAS |

| US-09  ·  2 SP     Como Agricultor, eu quero excluir um Terreno que não utilizo mais para manter minha propriedade organizada. |
| :---- |

| ✓  Terreno excluído com sucesso |
| :---- |

| Dado que | o Terreno não possui nenhuma Zona com Cultivo ativo |
| :---- | :---- |
| **Quando** | o Agricultor confirma a exclusão do Terreno |
| **Então** | o Terreno é removido da Propriedade |

| ✗  RN-037   Exclusão de Terreno com Cultivo ativo rejeitada |
| :---- |

| Dado que | o Terreno possui ao menos uma Zona com Cultivo com status ativo |
| :---- | :---- |
| **Quando** | o Agricultor tenta excluir o Terreno |
| **Então** | o sistema rejeita com erro TERRENO\_COM\_CULTIVO\_ATIVO |

| F-05  ·  Zonas de Plantio |
| :---- |
| *O Agricultor divide cada Terreno em Zonas de Plantio, delimitando espaços independentes para diferentes culturas.* |

| US-10  ·  3 SP     Como Agricultor, eu quero criar Zonas de Plantio em um Terreno para organizar o uso do espaço disponível. |
| :---- |

| ✓  Zona criada com sucesso |
| :---- |

| Dado que | o Terreno possui menos de 20 Zonas, área disponível suficiente, e o Agricultor informa nome "Canteiro Norte" e área 500 m² |
| :---- | :---- |
| **Quando** | o Agricultor cria a Zona |
| **Então** | a Zona é criada com sucesso e associada ao Terreno |

| ✗  RN-038   Criação de 21ª Zona rejeitada |
| :---- |

| Dado que | o Terreno já possui 20 Zonas cadastradas |
| :---- | :---- |
| **Quando** | o Agricultor tenta criar uma nova Zona no mesmo Terreno |
| **Então** | o sistema rejeita com erro LIMITE\_ZONAS\_EXCEDIDO |

| ✗  RN-039   Área da Zona superior ao Terreno rejeitada |
| :---- |

| Dado que | o Terreno possui área total de 1.000 m² e o Agricultor tenta criar uma Zona de 1.200 m² |
| :---- | :---- |
| **Quando** | submete o cadastro da Zona |
| **Então** | o sistema rejeita com erro AREA\_ZONA\_INVALIDA |

| ✗  RN-040a   Nome da Zona inválido rejeitado |
| :---- |

| Dado que | o Agricultor tenta criar uma Zona com nome de 1 caractere |
| :---- | :---- |
| **Quando** | submete o cadastro |
| **Então** | o sistema rejeita com erro NOME\_ZONA\_INVALIDO |

| ✗  RN-040b   Nome de Zona duplicado no Terreno rejeitado |
| :---- |

| Dado que | o Terreno já possui uma Zona chamada "Canteiro A" |
| :---- | :---- |
| **Quando** | o Agricultor tenta criar outra Zona com o mesmo nome no mesmo Terreno |
| **Então** | o sistema rejeita com erro NOME\_ZONA\_DUPLICADO |

| US-11  ·  2 SP     Como Agricultor, eu quero editar ou remover Zonas para ajustar a organização espacial do Terreno conforme meu planejamento evolui. |
| :---- |

| ✓  Zona removida com sucesso |
| :---- |

| Dado que | a Zona não possui nenhum Cultivo com status ativo |
| :---- | :---- |
| **Quando** | o Agricultor confirma a remoção da Zona |
| **Então** | a Zona é removida do Terreno |

| ✗  RN-041   Remoção de Zona com Cultivo ativo rejeitada |
| :---- |

| Dado que | a Zona possui Cultivo com status ativo |
| :---- | :---- |
| **Quando** | o Agricultor tenta remover a Zona |
| **Então** | o sistema rejeita com erro ZONA\_COM\_CULTIVO\_ATIVO |

| ✗  RN-042   Área editada da Zona excede o Terreno |
| :---- |

| Dado que | o Terreno possui área total de 800 m² e o Agricultor tenta editar a Zona para 900 m² |
| :---- | :---- |
| **Quando** | submete a edição da área da Zona |
| **Então** | o sistema rejeita com erro AREA\_ZONA\_INVALIDA |

| Épico 3 — Gestão de Cultivos |
| :---- |

| F-06  ·  Vínculo e Ciclo de Cultivo |
| :---- |
| *Ao vincular uma cultura a uma Zona inicia-se um Ciclo Agrícola. A quantidade plantada declarada neste momento inicializa a projeção do Celeiro e é imutável após a confirmação.* |

| US-12  ·  3 SP     Como Agricultor, eu quero vincular uma cultura a uma Zona declarando a quantidade plantada para iniciar um novo Ciclo Agrícola e permitir que o sistema acompanhe a evolução produtiva daquele espaço. |
| :---- |

| ✓  Ciclo Agrícola iniciado com sucesso |
| :---- |

| Dado que | a Zona está Vazia (sem Cultivo ativo), o Intervalo de Descanso configurado já foi cumprido, e o Agricultor informa quantidade 200 e unidade "kg" |
| :---- | :---- |
| **Quando** | o Agricultor confirma o vínculo da cultura "Tomate" à Zona |
| **Então** | o Ciclo Agrícola é iniciado, a Situação da Zona muda para Ativo e o Celeiro registra 200 kg como projeção inicial |

| ✗  RN-043   Vínculo em Zona já ocupada rejeitado |
| :---- |

| Dado que | a Zona já possui Cultivo ativo de "Tomate" |
| :---- | :---- |
| **Quando** | o Agricultor tenta vincular a cultura "Milho" à mesma Zona |
| **Então** | o sistema rejeita com erro ZONA\_OCUPADA |

| ✗  RN-044   Quantidade plantada ausente ou zero rejeitada |
| :---- |

| Dado que | o Agricultor tenta confirmar o vínculo sem informar a quantidade plantada |
| :---- | :---- |
| **Quando** | submete o formulário de vínculo |
| **Então** | o sistema rejeita com erro QUANTIDADE\_PLANTADA\_OBRIGATORIA |

| ✗  RN-045   Unidade de medida inválida rejeitada |
| :---- |

| Dado que | o Agricultor informa a unidade "tonelada" (fora do conjunto aceito: kg, g, unidades) |
| :---- | :---- |
| **Quando** | tenta confirmar o vínculo de cultura |
| **Então** | o sistema rejeita com erro UNIDADE\_INVALIDA |

| ✗  RN-046   Alteração de quantidade plantada após confirmação rejeitada |
| :---- |

| Dado que | o Ciclo Agrícola da Zona já foi confirmado com quantidade plantada 150 kg |
| :---- | :---- |
| **Quando** | o Agricultor tenta alterar diretamente a quantidade plantada declarada |
| **Então** | o sistema rejeita com erro QUANTIDADE\_PLANTADA\_IMUTAVEL |

| F-07  ·  Compatibilidade de Culturas |
| :---- |
| *O sistema conhece as relações de Companheiras e Inimigas entre culturas e orienta o Agricultor antes que uma combinação inadequada cause dano à produção.* |

| US-13  ·  3 SP     Como Agricultor, eu quero registrar um consórcio de culturas na mesma Zona e ser alertado quando a combinação for prejudicial. |
| :---- |

| ✓  Consórcio Companheiro registrado com sucesso |
| :---- |

| Dado que | a Zona possui Cultivo ativo de "Tomate" e "Manjericão" é classificado como Companheiro |
| :---- | :---- |
| **Quando** | o Agricultor vincula "Manjericão" à Zona |
| **Então** | o vínculo é registrado com status Companheira na relação de consórcio |

| ✗  RN-047   Vínculo de cultura Inimiga sem consentimento rejeitado |
| :---- |

| Dado que | "Funcho" é classificado como Inimigo de "Tomate" e o Agricultor não confirma ciência do risco |
| :---- | :---- |
| **Quando** | tenta vincular "Funcho" à Zona com "Tomate" ativo |
| **Então** | o sistema rejeita com erro INIMIGA\_BLOQUEADA |

| ✗  RN-048   Vínculo de Inimiga com consentimento registrado |
| :---- |

| Dado que | "Funcho" é Inimigo de "Tomate" e o Agricultor confirma explicitamente ciência do risco |
| :---- | :---- |
| **Quando** | submete o consórcio com consentimento registrado |
| **Então** | o vínculo é aceito e registrado com indicador de ciência do Agricultor ativo |

| ✗  RN-049   Cultura sem relação definida não recebe status Companheira |
| :---- |

| Dado que | a Zona possui "Tomate" ativo e o Agricultor vincula "Cenoura" (sem relação de consórcio definida) |
| :---- | :---- |
| **Quando** | registra o consórcio |
| **Então** | o vínculo é aceito sem marcação de Companheira — somente relações explicitamente cadastradas recebem esse status |

| US-14  ·  2 SP     Como Agricultor, eu quero consultar o histórico de consórcios de uma Zona para embasar decisões de plantio conjunto em ciclos futuros. |
| :---- |

| ✓  Histórico de consórcios exibido com sucesso |
| :---- |

| Dado que | a Zona possui ao menos um vínculo de cultura registrado (ativo ou encerrado) |
| :---- | :---- |
| **Quando** | o Agricultor consulta o histórico de consórcios |
| **Então** | todos os consórcios registrados — ativos e encerrados — da Zona são exibidos |

| ✗  RN-050   Histórico indisponível para Zona sem vínculos |
| :---- |

| Dado que | a Zona nunca recebeu nenhum vínculo de cultura |
| :---- | :---- |
| **Quando** | o Agricultor tenta consultar o histórico de consórcios |
| **Então** | o sistema informa que não há histórico disponível com erro HISTORICO\_INEXISTENTE |

| F-08  ·  Rotação de Culturas |
| :---- |
| *O Agricultor define Intervalos de Descanso mínimos por Zona e cultura. O sistema bloqueia novos vínculos enquanto o intervalo não tiver sido cumprido.* |

| US-15  ·  2 SP     Como Agricultor, eu quero definir um Intervalo de Descanso mínimo entre cultivos da mesma cultura em cada Zona. |
| :---- |

| ✓  Intervalo de Descanso cadastrado com sucesso |
| :---- |

| Dado que | a Zona possui ao menos um ciclo de "Tomate" encerrado |
| :---- | :---- |
| **Quando** | o Agricultor informa Intervalo de Descanso de 30 dias para Tomate nessa Zona |
| **Então** | o Intervalo é registrado com sucesso para a combinação Zona \+ cultura |

| ✗  RN-051   Intervalo cadastrado sem histórico encerrado rejeitado |
| :---- |

| Dado que | a Zona nunca teve nenhum ciclo de "Milho" encerrado |
| :---- | :---- |
| **Quando** | o Agricultor tenta cadastrar Intervalo de Descanso para Milho nessa Zona |
| **Então** | o sistema rejeita com erro INTERVALO\_SEM\_HISTORICO |

| ✗  RN-052a   Intervalo de Descanso de 0 dias rejeitado |
| :---- |

| Dado que | o Agricultor informa Intervalo de Descanso de 0 dias (abaixo do mínimo de 1\) |
| :---- | :---- |
| **Quando** | submete o cadastro |
| **Então** | o sistema rejeita com erro INTERVALO\_INVALIDO |

| ✗  RN-052b   Intervalo de Descanso acima de 365 dias rejeitado |
| :---- |

| Dado que | o Agricultor informa Intervalo de Descanso de 400 dias (acima do máximo de 365\) |
| :---- | :---- |
| **Quando** | submete o cadastro |
| **Então** | o sistema rejeita com erro INTERVALO\_INVALIDO |

| US-16  ·  3 SP     Como Agricultor, eu quero ser bloqueado de vincular uma cultura a uma Zona antes que o Intervalo de Descanso definido tenha sido cumprido. |
| :---- |

| ✓  Vínculo aceito após cumprimento do Intervalo |
| :---- |

| Dado que | o Intervalo de Descanso de 30 dias para "Tomate" na Zona foi cumprido (colheita há 35 dias) |
| :---- | :---- |
| **Quando** | o Agricultor vincula "Tomate" novamente à Zona |
| **Então** | o vínculo é aceito e o novo Ciclo Agrícola é iniciado |

| ✗  RN-053   Vínculo rejeitado dentro do Intervalo de Descanso |
| :---- |

| Dado que | o Intervalo de Descanso de 30 dias para "Tomate" na Zona ainda não foi cumprido (colheita há 20 dias) |
| :---- | :---- |
| **Quando** | o Agricultor tenta vincular "Tomate" à Zona |
| **Então** | o sistema rejeita com erro INTERVALO\_NAO\_CUMPRIDO |

| Épico 4 — Calendário e Planejamento |
| :---- |

| F-09  ·  Calendário Agrícola |
| :---- |
| *O sistema gera automaticamente as Tarefas base do ciclo. O Agricultor pode editar, criar e excluir Tarefas dentro dos limites do ciclo ativo.* |

| US-17  ·  3 SP     Como Agricultor, eu quero editar as Tarefas geradas automaticamente para o ciclo ativo para ajustá-las à realidade do meu campo. |
| :---- |

| ✓  Tarefa editada com sucesso |
| :---- |

| Dado que | o ciclo ativo da Zona vai de 01/03 a 30/06 |
| :---- | :---- |
| **Quando** | o Agricultor edita a Tarefa "Irrigação" com novo nome "Irrigação Matinal" e data 15/04 |
| **Então** | a Tarefa é atualizada com o novo nome e a nova data dentro do intervalo do ciclo |

| ✗  RN-054   Nome da Tarefa inválido rejeitado |
| :---- |

| Dado que | o Agricultor tenta editar o nome da Tarefa para uma string de 1 caractere |
| :---- | :---- |
| **Quando** | submete a edição |
| **Então** | o sistema rejeita com erro NOME\_TAREFA\_INVALIDO |

| ✗  RN-055   Data da Tarefa fora do ciclo ativo rejeitada |
| :---- |

| Dado que | o ciclo ativo encerra em 30/06 e o Agricultor tenta mover a Tarefa para 15/07 |
| :---- | :---- |
| **Quando** | submete a edição da data |
| **Então** | o sistema rejeita com erro DATA\_TAREFA\_FORA\_DO\_CICLO |

| US-18  ·  3 SP     Como Agricultor, eu quero criar Tarefas manuais no ciclo ativo para registrar atividades específicas que não foram geradas automaticamente. |
| :---- |

| ✓  Tarefa manual criada com sucesso |
| :---- |

| Dado que | o ciclo ativo vai até 30/06 e hoje é 01/04 |
| :---- | :---- |
| **Quando** | o Agricultor cria a Tarefa "Adubação Extra" com data 20/04 e nome válido |
| **Então** | a Tarefa manual é registrada no calendário do ciclo ativo |

| ✗  RN-056a   Data passada para Tarefa manual rejeitada |
| :---- |

| Dado que | hoje é 01/04 e o Agricultor informa data 15/03 (data passada) para a Tarefa manual |
| :---- | :---- |
| **Quando** | submete a criação |
| **Então** | o sistema rejeita com erro DATA\_TAREFA\_PASSADA |

| ✗  RN-056b   Data posterior ao fim do ciclo rejeitada |
| :---- |

| Dado que | o ciclo ativo encerra em 30/06 e o Agricultor informa data 15/07 para a Tarefa manual |
| :---- | :---- |
| **Quando** | submete a criação |
| **Então** | o sistema rejeita com erro DATA\_TAREFA\_FORA\_DO\_CICLO |

| ✗  RN-057   Nome da Tarefa manual inválido rejeitado |
| :---- |

| Dado que | o Agricultor tenta criar uma Tarefa manual com nome vazio |
| :---- | :---- |
| **Quando** | submete a criação |
| **Então** | o sistema rejeita com erro NOME\_TAREFA\_INVALIDO |

| US-19  ·  1 SP     Como Agricultor, eu quero excluir Tarefas do ciclo ativo que não são mais necessárias para manter o calendário limpo e relevante. |
| :---- |

| ✓  Tarefa do ciclo ativo excluída com sucesso |
| :---- |

| Dado que | a Tarefa "Irrigação" pertence ao ciclo ativo da Zona |
| :---- | :---- |
| **Quando** | o Agricultor solicita a exclusão da Tarefa |
| **Então** | a Tarefa é removida do calendário do ciclo ativo |

| ✗  RN-058   Exclusão de Tarefa de ciclo encerrado rejeitada |
| :---- |

| Dado que | a Tarefa "Adubação" pertence a um ciclo já encerrado da Zona |
| :---- | :---- |
| **Quando** | o Agricultor tenta excluir a Tarefa |
| **Então** | o sistema rejeita com erro TAREFA\_CICLO\_ENCERRADO pois Tarefas de ciclos encerrados são imutáveis |

| F-19  ·  Janela de Plantio e Prévia de Ciclo |
| :---- |
| *Antes de iniciar um ciclo, o Agricultor consulta as Janelas de Plantio recomendadas para uma cultura em uma Zona e visualiza a prévia do ciclo antes de confirmar o plantio.* |

| US-44  ·  3 SP     Como Agricultor, eu quero consultar as Janelas de Plantio recomendadas para uma cultura em uma Zona para decidir o melhor momento de iniciar um novo ciclo. |
| :---- |

| ✓  Janelas de Plantio consultadas com sucesso |
| :---- |

| Dado que | a Zona está Vazia, possui tipo de solo e subtipo climático definidos, e a cultura "Tomate" possui requisitos agronômicos cadastrados |
| :---- | :---- |
| **Quando** | o Agricultor consulta as Janelas de Plantio para "Tomate" na Zona |
| **Então** | o sistema retorna até 3 Janelas ordenadas por adequação: Ideal, Adequado ou Marginal |

| ✗  RN-059   Consulta de Janela de Plantio em Zona com ciclo ativo rejeitada |
| :---- |

| Dado que | a Zona possui Ciclo Agrícola ativo |
| :---- | :---- |
| **Quando** | o Agricultor tenta consultar as Janelas de Plantio |
| **Então** | o sistema rejeita com erro ZONA\_CICLO\_ATIVO — Zonas com ciclo ativo não apresentam recomendações de novo plantio |

| ✗  RN-060   Consulta indisponível por dado ausente na Zona |
| :---- |

| Dado que | a Zona está Vazia mas não possui tipo de solo definido |
| :---- | :---- |
| **Quando** | o Agricultor tenta consultar as Janelas de Plantio |
| **Então** | o sistema informa que a consulta está indisponível com erro DADOS\_ZONA\_INSUFICIENTES |

| ✗  RN-061   Sistema retorna no máximo 3 janelas por cultura por Zona |
| :---- |

| Dado que | existem potencialmente 5 períodos de adequação para "Tomate" na Zona no ano corrente |
| :---- | :---- |
| **Quando** | o Agricultor consulta as Janelas de Plantio |
| **Então** | o sistema retorna somente as 3 Janelas mais adequadas, ordenadas por nível de adequação |

| US-45  ·  2 SP     Como Agricultor, eu quero visualizar a prévia de um ciclo ao selecionar uma Janela de Plantio para entender o cronograma estimado e as Tarefas que serão geradas antes de confirmar o plantio. |
| :---- |

| ✓  Prévia de ciclo exibida com sucesso |
| :---- |

| Dado que | o Agricultor selecionou a Janela de Plantio "Ideal" para "Tomate" na Zona |
| :---- | :---- |
| **Quando** | o Agricultor solicita a prévia do ciclo |
| **Então** | o sistema exibe data estimada de início, data estimada de colheita e lista das Tarefas principais que serão geradas automaticamente |

| ✗  RN-062   Prévia sem Janela selecionada rejeitada |
| :---- |

| Dado que | o Agricultor tenta solicitar a prévia de ciclo sem ter selecionado uma Janela de Plantio |
| :---- | :---- |
| **Quando** | submete a solicitação de prévia |
| **Então** | o sistema rejeita com erro JANELA\_NAO\_SELECIONADA |

| ✗  RN-063   Confirmação do vínculo a partir de prévia respeita regras de F-06 |
| :---- |

| Dado que | o Agricultor selecionou a Janela "Ideal" mas não informa a quantidade plantada ao confirmar |
| :---- | :---- |
| **Quando** | tenta confirmar o vínculo de cultura a partir da prévia |
| **Então** | o sistema rejeita com erro QUANTIDADE\_PLANTADA\_OBRIGATORIA — as regras de F-06 se aplicam integralmente |

| ✗  RN-064   Confirmação de vínculo via Janela de Plantio sem quantidade plantada rejeitada |
| :---- |

| Dado que | o Agricultor selecionou a Janela de Plantio "Ideal" para a cultura Tomate na Zona "Canteiro Norte" e avançou para a confirmação do vínculo |
| :---- | :---- |
| **Quando** | tenta confirmar o vínculo sem informar a quantidade plantada |
| **Então** | o sistema rejeita com erro QUANTIDADE\_OBRIGATORIA, respeitando as regras de F-06 |

| Épico 5 — Manejo Integrado de Pragas |
| :---- |

| F-10  ·  Monitoramento de Focos Fitossanitários |
| :---- |
| *O Agricultor registra os Focos Fitossanitários identificados em campo — pragas, doenças ou danos físicos — classificados por tipo agronômico, nível de infestação e severidade.* |

| US-20  ·  3 SP     Como Agricultor, eu quero registrar um Foco Fitossanitário em uma Zona informando o tipo agronômico, o nível de infestação e a severidade, para documentar a pressão fitossanitária sobre o ciclo ativo. |
| :---- |

| ✓  Foco Fitossanitário registrado com sucesso |
| :---- |

| Dado que | a Zona possui ciclo ativo e ainda não há Foco do tipo "Praga" registrado nela na data de hoje |
| :---- | :---- |
| **Quando** | o Agricultor informa tipo "Praga", infestação "Alta", severidade "Alta" e descrição com 80 caracteres |
| **Então** | o Foco Fitossanitário é salvo com sucesso na Zona |

| ✗  RN-065   Foco em Zona sem ciclo ativo rejeitado |
| :---- |

| Dado que | a Zona está Vazia (sem ciclo ativo) |
| :---- | :---- |
| **Quando** | o Agricultor tenta registrar um Foco Fitossanitário |
| **Então** | o sistema rejeita com erro ZONA\_SEM\_CICLO\_ATIVO |

| ✗  RN-066   Tipo agronômico inválido rejeitado |
| :---- |

| Dado que | o Agricultor informa tipo "Infestação" (fora do conjunto aceito: Praga, Doença, DanoFísico) |
| :---- | :---- |
| **Quando** | submete o registro do Foco |
| **Então** | o sistema rejeita com erro TIPO\_AGRONOMICO\_INVALIDO |

| ✗  RN-067   Nível de infestação inválido rejeitado |
| :---- |

| Dado que | o Agricultor informa nível de infestação "Crítico" (fora do conjunto: Baixo, Médio, Alto) |
| :---- | :---- |
| **Quando** | submete o registro do Foco |
| **Então** | o sistema rejeita com erro NIVEL\_INFESTACAO\_INVALIDO |

| ✗  RN-068   Severidade inválida rejeitada |
| :---- |

| Dado que | o Agricultor informa severidade "Altíssima" (fora do conjunto: Baixa, Média, Alta) |
| :---- | :---- |
| **Quando** | submete o registro do Foco |
| **Então** | o sistema rejeita com erro SEVERIDADE\_INVALIDA |

| ✗  RN-069a   Descrição abaixo do mínimo rejeitada |
| :---- |

| Dado que | o Agricultor informa descrição com apenas 5 caracteres (abaixo do mínimo de 10\) |
| :---- | :---- |
| **Quando** | submete o registro do Foco |
| **Então** | o sistema rejeita com erro DESCRICAO\_FOCO\_INVALIDA |

| ✗  RN-069b   Descrição acima do máximo rejeitada |
| :---- |

| Dado que | o Agricultor informa descrição com 520 caracteres (acima do máximo de 500\) |
| :---- | :---- |
| **Quando** | submete o registro do Foco |
| **Então** | o sistema rejeita com erro DESCRICAO\_FOCO\_INVALIDA |

| ✗  RN-070   Foco duplicado do mesmo tipo na mesma Zona na mesma data rejeitado |
| :---- |

| Dado que | a Zona já possui um Foco do tipo "Doença" registrado na data de hoje |
| :---- | :---- |
| **Quando** | o Agricultor tenta registrar outro Foco do tipo "Doença" na mesma Zona na mesma data |
| **Então** | o sistema rejeita com erro FOCO\_DUPLICADO |

| US-21  ·  2 SP     Como Agricultor, eu quero atualizar ou remover um Foco Fitossanitário registrado para corrigir informações equivocadas antes que o ciclo seja encerrado. |
| :---- |

| ✓  Foco Fitossanitário atualizado com sucesso |
| :---- |

| Dado que | o Foco pertence ao ciclo ativo da Zona e o ciclo ainda está em andamento |
| :---- | :---- |
| **Quando** | o Agricultor atualiza o nível de infestação para "Médio" |
| **Então** | o Foco é atualizado com sucesso |

| ✗  RN-071   Atualização de Foco após encerramento do ciclo rejeitada |
| :---- |

| Dado que | o ciclo da Zona foi encerrado e o Foco Fitossanitário pertence a esse ciclo |
| :---- | :---- |
| **Quando** | o Agricultor tenta atualizar o Foco |
| **Então** | o sistema rejeita com erro FOCO\_IMUTAVEL |

| ✗  RN-072   Validações do registro original aplicadas na atualização |
| :---- |

| Dado que | o Agricultor tenta atualizar o tipo agronômico do Foco para "Contaminação" (fora do conjunto aceito) |
| :---- | :---- |
| **Quando** | submete a atualização |
| **Então** | o sistema rejeita aplicando as mesmas validações do registro original — erro TIPO\_AGRONOMICO\_INVALIDO |

| ✗  RN-073   Remoção de Foco com Registros de Perda vinculados rejeitada |
| :---- |

| Dado que | o Foco Fitossanitário possui ao menos um Registro de Perda vinculado |
| :---- | :---- |
| **Quando** | o Agricultor tenta remover o Foco |
| **Então** | o sistema rejeita com erro FOCO\_COM\_PERDAS\_VINCULADAS — o Agricultor deve remover os Registros de Perda associados antes de excluir o Foco |

| F-11  ·  Registro de Perda e Intervenção Fitossanitária |
| :---- |
| *Cada Registro de Perda é vinculado a um Foco Fitossanitário da Zona e reduz progressivamente a quantidade esperada no Celeiro. Ao encerrar o ciclo, a soma das perdas compõe o Relatório de Perdas.* |

| US-34  ·  3 SP     Como Agricultor, eu quero registrar a perda de produção associada a um Foco Fitossanitário informando a quantidade estimada perdida, a unidade de medida e a intervenção realizada, para que o Celeiro desconte essa perda da quantidade esperada do ciclo. |
| :---- |

| ✓  Registro de Perda criado com sucesso |
| :---- |

| Dado que | a Zona possui ciclo ativo, há um Foco do tipo "Praga" registrado, e a soma de perdas existentes é inferior à quantidade plantada |
| :---- | :---- |
| **Quando** | o Agricultor informa quantidade perdida 30, unidade "kg" e intervenção "AplicaçãoDeDefensivo" |
| **Então** | o Registro de Perda é salvo e o Celeiro atualiza a projeção descontando 30 kg da quantidade esperada |

| ✗  RN-074   Registro de Perda sem Foco vinculado rejeitado |
| :---- |

| Dado que | não existe nenhum Foco Fitossanitário registrado na Zona para o ciclo ativo |
| :---- | :---- |
| **Quando** | o Agricultor tenta criar um Registro de Perda |
| **Então** | o sistema rejeita com erro FOCO\_NAO\_ENCONTRADO — o Registro de Perda deve estar vinculado a um Foco existente |

| ✗  RN-075   Quantidade estimada zero ou negativa rejeitada |
| :---- |

| Dado que | o Agricultor informa quantidade perdida 0 |
| :---- | :---- |
| **Quando** | submete o Registro de Perda |
| **Então** | o sistema rejeita com erro QUANTIDADE\_PERDA\_INVALIDA |

| ✗  RN-076   Unidade de medida incompatível com o cultivo rejeitada |
| :---- |

| Dado que | o cultivo ativo da Zona utiliza unidade "kg" e o Agricultor informa unidade "unidades" no Registro de Perda |
| :---- | :---- |
| **Quando** | submete o Registro de Perda |
| **Então** | o sistema rejeita com erro UNIDADE\_INCOMPATIVEL |

| ✗  RN-077   Tipo de intervenção inválido rejeitado |
| :---- |

| Dado que | o Agricultor informa intervenção "Fumigação" (fora do conjunto aceito: AplicaçãoDeDefensivo, PodaFitossanitária, ControleBiológico, SemIntervenção) |
| :---- | :---- |
| **Quando** | submete o Registro de Perda |
| **Então** | o sistema rejeita com erro TIPO\_INTERVENCAO\_INVALIDO |

| ✗  RN-078   Soma de perdas superior à quantidade plantada rejeitada |
| :---- |

| Dado que | a quantidade plantada do ciclo é 100 kg, as perdas já registradas totalizam 90 kg, e o Agricultor tenta registrar mais 20 kg de perda |
| :---- | :---- |
| **Quando** | submete o Registro de Perda |
| **Então** | o sistema rejeita com erro PERDA\_EXCEDE\_QUANTIDADE\_PLANTADA |

| US-35  ·  2 SP     Como Agricultor, eu quero consultar o histórico de perdas registradas em uma Zona para acompanhar o impacto fitossanitário acumulado do ciclo ativo. |
| :---- |

| ✓  Histórico de perdas exibido com sucesso |
| :---- |

| Dado que | a Zona possui ciclo ativo com 3 Registros de Perda vinculados a Focos distintos |
| :---- | :---- |
| **Quando** | o Agricultor consulta o histórico de perdas do ciclo ativo |
| **Então** | os 3 Registros de Perda são exibidos com quantidade, unidade, tipo de intervenção e Foco vinculado |

| ✗  RN-079   Consulta de ciclo encerrado exibida sem possibilidade de edição |
| :---- |

| Dado que | a Zona possui um ciclo encerrado com Registros de Perda registrados |
| :---- | :---- |
| **Quando** | o Agricultor consulta o histórico de perdas desse ciclo encerrado |
| **Então** | os registros são exibidos corretamente mas sem opções de edição ou remoção — ciclos encerrados são imutáveis |

| ✗  RN-080   Alteração em histórico de ciclo encerrado rejeitada |
| :---- |

| Dado que | o Agricultor consulta o histórico de perdas da Zona "Canteiro Sul" referente a um ciclo já encerrado |
| :---- | :---- |
| **Quando** | tenta editar um Registro de Perda exibido no histórico |
| **Então** | o sistema rejeita a operação — registros de ciclos encerrados são imutáveis |

| Épico 6 — Colheita e Estoque |
| :---- |

| F-12  ·  Registro de Colheita |
| :---- |
| *A Colheita encerra o Ciclo Agrícola de uma Zona e consolida no Celeiro a quantidade efetivamente produzida. A quantidade colhida não pode exceder a projeção atual do Celeiro — calculada como quantidade plantada menos perdas acumuladas.* |

| US-22  ·  3 SP     Como Agricultor, eu quero registrar a colheita de uma Zona informando quantidade, unidade de medida e destino para encerrar o ciclo e atualizar o Celeiro com o resultado real da safra. |
| :---- |

| ✓  Colheita registrada e Ciclo encerrado com sucesso |
| :---- |

| Dado que | a Zona está com Situação "Pronta para Colheita" e a projeção atual do Celeiro é 480 kg |
| :---- | :---- |
| **Quando** | o Agricultor informa quantidade 450 kg e destino "Venda" e confirma |
| **Então** | a Colheita é registrada, o Ciclo Agrícola é encerrado, o Celeiro é atualizado e o Relatório de Perdas é gerado automaticamente |

| ✗  RN-081   Colheita em Zona não pronta rejeitada |
| :---- |

| Dado que | a Zona está com Situação "Vazia" (sem Cultivo ativo) |
| :---- | :---- |
| **Quando** | o Agricultor tenta registrar Colheita |
| **Então** | o sistema rejeita com erro COLHEITA\_NAO\_PERMITIDA |

| ✗  RN-082a   Quantidade zero ou negativa rejeitada |
| :---- |

| Dado que | o Agricultor informa quantidade 0 |
| :---- | :---- |
| **Quando** | submete o registro de Colheita |
| **Então** | o sistema rejeita com erro QUANTIDADE\_COLHIDA\_INVALIDA |

| ✗  RN-082b   Quantidade acima da projeção atual do Celeiro rejeitada |
| :---- |

| Dado que | a projeção atual do Celeiro para a Zona é 300 kg (quantidade plantada 350 kg menos 50 kg de perdas acumuladas) e o Agricultor informa quantidade 320 kg |
| :---- | :---- |
| **Quando** | submete o registro de Colheita |
| **Então** | o sistema rejeita com erro QUANTIDADE\_EXCEDE\_PROJECAO — a quantidade colhida não pode superar a projeção atual do Celeiro |

| ✗  RN-083   Destino inválido rejeitado |
| :---- |

| Dado que | o Agricultor informa destino "Exportação" (fora do conjunto: ConsumoPróprio, Venda, Cooperativa, Descarte) |
| :---- | :---- |
| **Quando** | submete o registro |
| **Então** | o sistema rejeita com erro DESTINO\_INVALIDO |

| ✗  RN-084   Unidade de medida incompatível com o cultivo rejeitada |
| :---- |

| Dado que | o cultivo ativo usa unidade "kg" e o Agricultor informa unidade "unidades" na Colheita |
| :---- | :---- |
| **Quando** | submete o registro |
| **Então** | o sistema rejeita com erro UNIDADE\_INVALIDA |

| ✗  RN-085   Encerramento de ciclo sem quantidade colhida rejeitado |
| :---- |

| Dado que | o Agricultor tenta encerrar o Ciclo Agrícola sem informar a quantidade colhida |
| :---- | :---- |
| **Quando** | submete o encerramento |
| **Então** | o sistema rejeita com erro CICLO\_SEM\_COLHEITA |

| US-23  ·  2 SP     Como Agricultor, eu quero corrigir os dados de uma Colheita registrada para garantir a acurácia do inventário do Celeiro. |
| :---- |

| ✓  Colheita corrigida com sucesso |
| :---- |

| Dado que | a Colheita foi registrada e o Ciclo Agrícola ainda não foi encerrado definitivamente |
| :---- | :---- |
| **Quando** | o Agricultor corrige a quantidade para 420 kg |
| **Então** | a Colheita e a projeção do Celeiro são atualizadas corretamente |

| ✗  RN-086   Correção após encerramento definitivo rejeitada |
| :---- |

| Dado que | o Ciclo Agrícola da Zona foi encerrado definitivamente |
| :---- | :---- |
| **Quando** | o Agricultor tenta corrigir os dados da Colheita |
| **Então** | o sistema rejeita com erro CORRECAO\_CICLO\_ENCERRADO |

| ✗  RN-087   Validações do registro original aplicadas na correção |
| :---- |

| Dado que | o Agricultor tenta corrigir o destino para "Importação" (fora do conjunto aceito) |
| :---- | :---- |
| **Quando** | submete a correção |
| **Então** | o sistema rejeita aplicando as mesmas validações — erro DESTINO\_INVALIDO |

| F-13  ·  Controle do Celeiro com Projeção e Alertas |
| :---- |
| *⚙  FEATURE COMPLEXA — Incorpora: F-17 · Relatório de Produtividade* |
| *O Celeiro registra a quantidade plantada e atualiza a projeção à medida que Registros de Perda são lançados. Incorpora: Meta Comercializável, Alerta de Projeção Abaixo do Esperado, Relatório de Perdas e Relatórios de Produtividade.* |

| US-24  ·  2 SP     Como Agricultor, eu quero visualizar o estado atual do Celeiro de uma Zona para acompanhar a quantidade plantada, as perdas acumuladas e a projeção real de produção do ciclo ativo. |
| :---- |

| ✓  Estado do Celeiro exibido corretamente |
| :---- |

| Dado que | a Zona possui ciclo ativo com 200 kg plantados e 30 kg de perdas registradas |
| :---- | :---- |
| **Quando** | o Agricultor acessa a visualização do Celeiro da Zona |
| **Então** | o sistema exibe: quantidade plantada 200 kg, perdas acumuladas 30 kg e projeção atual 170 kg |

| ✗  RN-088   Ajuste manual direto na projeção do Celeiro rejeitado |
| :---- |

| Dado que | o Agricultor tenta alterar manualmente a quantidade plantada ou a projeção do Celeiro da Zona |
| :---- | :---- |
| **Quando** | submete o ajuste |
| **Então** | o sistema rejeita com erro AJUSTE\_MANUAL\_NAO\_PERMITIDO — esses valores só se alteram via registros originados no ciclo ou nos Registros de Perda |

| ✗  RN-089   Edição direta do campo de projeção no Celeiro rejeitada |
| :---- |

| Dado que | o Agricultor visualiza o Celeiro da Zona "Canteiro Norte" com projeção atual de 80 kg e tenta editar diretamente o campo de projeção para 90 kg |
| :---- | :---- |
| **Quando** | submete a alteração direta na projeção |
| **Então** | o sistema rejeita a operação — a projeção só se altera por meio de registros originados no ciclo de cultivo ou nos Registros de Perda |

| US-36  ·  3 SP     Como Agricultor, eu quero definir uma Meta Comercializável para o ciclo ativo de uma Zona para que o sistema me avise quando as perdas acumuladas indicarem que essa meta está em risco. |
| :---- |

| ✓  Meta Comercializável definida e Alerta de Projeção gerado |
| :---- |

| Dado que | a Zona possui ciclo ativo com 200 kg plantados; o Agricultor define Meta Comercializável de 150 kg; em seguida registra 60 kg de perdas, tornando a projeção 140 kg |
| :---- | :---- |
| **Quando** | o sistema verifica que a projeção (140 kg) é inferior à Meta (150 kg) |
| **Então** | um Alerta de Projeção Abaixo do Esperado é emitido para a Zona |

| ✗  RN-090a   Meta Comercializável zero rejeitada |
| :---- |

| Dado que | o Agricultor tenta definir Meta Comercializável de 0 kg |
| :---- | :---- |
| **Quando** | submete a definição da Meta |
| **Então** | o sistema rejeita com erro META\_COMERCIALIZAVEL\_INVALIDA |

| ✗  RN-090b   Meta Comercializável superior à quantidade plantada rejeitada |
| :---- |

| Dado que | a quantidade plantada do ciclo é 100 kg e o Agricultor tenta definir Meta Comercializável de 120 kg |
| :---- | :---- |
| **Quando** | submete a definição da Meta |
| **Então** | o sistema rejeita com erro META\_COMERCIALIZAVEL\_INVALIDA |

| ✗  RN-091   Meta definida com ciclo encerrado rejeitada |
| :---- |

| Dado que | o ciclo da Zona foi encerrado |
| :---- | :---- |
| **Quando** | o Agricultor tenta definir ou atualizar a Meta Comercializável |
| **Então** | o sistema rejeita com erro CICLO\_ENCERRADO |

| ✗  RN-092   Segundo Alerta de Projeção em 24h bloqueado |
| :---- |

| Dado que | um Alerta de Projeção Abaixo do Esperado já foi emitido para a Zona há 6 horas |
| :---- | :---- |
| **Quando** | as perdas acumuladas continuam abaixo da Meta e o sistema verificaria emitir novo alerta |
| **Então** | o sistema bloqueia a geração do segundo alerta com erro FREQUENCIA\_ALERTA\_EXCEDIDA (intervalo mínimo de 24 horas) |

| ✗  RN-093   Segundo Alerta de Projeção Abaixo do Esperado em 24 horas não emitido |
| :---- |

| Dado que | o sistema emitiu um Alerta de Projeção Abaixo do Esperado para a Zona "Canteiro Norte" há 3 horas |
| :---- | :---- |
| **Quando** | a projeção da mesma Zona volta a cair abaixo da Meta Comercializável |
| **Então** | o sistema não emite novo alerta — o intervalo mínimo de 24 horas entre alertas da mesma Zona ainda não decorreu |

| US-25  ·  3 SP     Como Agricultor, eu quero registrar a saída de produtos do Celeiro para manter o estoque sempre fiel ao que realmente tenho disponível após a colheita. |
| :---- |

| ✓  Saída de produto registrada com sucesso |
| :---- |

| Dado que | o Celeiro possui saldo de 300 kg de "Tomate" |
| :---- | :---- |
| **Quando** | o Agricultor registra saída de 100 kg com motivo "Venda" |
| **Então** | o saldo do Celeiro é reduzido para 200 kg e a saída é registrada com o motivo informado |

| ✗  RN-094   Saída maior que saldo disponível rejeitada |
| :---- |

| Dado que | o Celeiro possui apenas 50 kg de "Milho" |
| :---- | :---- |
| **Quando** | o Agricultor tenta registrar saída de 80 kg |
| **Então** | o sistema rejeita com erro SALDO\_INSUFICIENTE |

| ✗  RN-095   Motivo de saída manual de Perda rejeitado |
| :---- |

| Dado que | o Agricultor tenta registrar uma saída com motivo "Perda" diretamente no Celeiro |
| :---- | :---- |
| **Quando** | submete o registro |
| **Então** | o sistema rejeita com erro MOTIVO\_SAIDA\_INVALIDO — saídas classificadas como Perda devem ser originadas exclusivamente por Registros de Perda do Manejo Integrado de Pragas |

| US-37  ·  2 SP     Como Agricultor, eu quero consultar o Relatório de Perdas de um ciclo encerrado para entender o volume total perdido, os tipos fitossanitários responsáveis e as intervenções realizadas. |
| :---- |

| ✓  Relatório de Perdas exibido com sucesso |
| :---- |

| Dado que | a Zona possui um ciclo encerrado com 3 Registros de Perda de tipos agronômicos distintos |
| :---- | :---- |
| **Quando** | o Agricultor acessa o Relatório de Perdas do ciclo |
| **Então** | o sistema exibe: quantidade plantada, soma total de perdas, projeção original, quantidade colhida e detalhamento por tipo agronômico de foco |

| ✗  RN-096   Relatório de Perdas em ciclo ativo rejeitado |
| :---- |

| Dado que | a Zona possui ciclo ativo (não encerrado) |
| :---- | :---- |
| **Quando** | o Agricultor tenta consultar o Relatório de Perdas |
| **Então** | o sistema rejeita com erro RELATORIO\_CICLO\_ATIVO — o Relatório só está disponível para ciclos encerrados |

| ✓  Relatório de Perdas exibe dados consolidados do ciclo (RN-097) |
| :---- |

| Dado que | o Agricultor acessa o Relatório de Perdas da Zona "Canteiro Norte" referente a um ciclo encerrado com 100 kg plantados, 15 kg de perdas registradas e 85 kg colhidos |
| :---- | :---- |
| **Quando** | o relatório é gerado |
| **Então** | o sistema exibe: quantidade plantada (100 kg), soma total de perdas (15 kg), projeção original (100 kg), quantidade efetivamente colhida (85 kg) e detalhamento por tipo agronômico de foco |

| US-26  ·  2 SP     Como Agricultor, eu quero salvar configurações de relatório de produtividade com nome e período para consultar minha produção rapidamente no formato que prefiro. |
| :---- |

| ✓  Configuração de relatório salva com sucesso |
| :---- |

| Dado que | o Agricultor possui menos de 5 configurações salvas |
| :---- | :---- |
| **Quando** | informa nome "Safra Verão 2026" e período "Semestre" e confirma |
| **Então** | a configuração de relatório é persistida com sucesso |

| ✗  RN-098a   Nome de configuração inválido rejeitado |
| :---- |

| Dado que | o Agricultor informa nome de configuração com apenas 1 caractere |
| :---- | :---- |
| **Quando** | submete o cadastro |
| **Então** | o sistema rejeita com erro NOME\_CONFIG\_INVALIDO |

| ✗  RN-098b   Nome de configuração duplicado rejeitado |
| :---- |

| Dado que | o Agricultor já possui uma configuração chamada "Safra Verão 2026" |
| :---- | :---- |
| **Quando** | tenta criar outra com o mesmo nome |
| **Então** | o sistema rejeita com erro NOME\_CONFIG\_DUPLICADO |

| ✗  RN-099   Período de filtro inválido rejeitado |
| :---- |

| Dado que | o Agricultor informa o período "Bimestre" (fora do conjunto: UltimoMes, Trimestre, Semestre, Ano) |
| :---- | :---- |
| **Quando** | submete o cadastro |
| **Então** | o sistema rejeita com erro PERIODO\_INVALIDO |

| ✗  RN-100   6ª configuração de relatório rejeitada |
| :---- |

| Dado que | o Agricultor já possui 5 configurações de relatório salvas |
| :---- | :---- |
| **Quando** | tenta salvar uma 6ª configuração |
| **Então** | o sistema rejeita com erro LIMITE\_CONFIGURACOES\_EXCEDIDO |

| US-27  ·  3 SP     Como Agricultor, eu quero consultar a produção consolidada com base em uma configuração salva para entender minha produtividade por período e cultura. |
| :---- |

| ✓  Relatório de produtividade gerado com sucesso |
| :---- |

| Dado que | o Agricultor possui a configuração "Safra Verão 2026" com período "Semestre" salva e válida |
| :---- | :---- |
| **Quando** | executa a consulta de relatório |
| **Então** | o sistema retorna a produção consolidada do período com os dados das Colheitas registradas |

| ✗  RN-101   Consulta sem configuração persistida rejeitada |
| :---- |

| Dado que | o Agricultor tenta executar uma consulta de produtividade sem selecionar uma configuração salva |
| :---- | :---- |
| **Quando** | submete a consulta |
| **Então** | o sistema rejeita com erro CONFIGURACAO\_INEXISTENTE |

| Épico 7 — Finanças Fácil |
| :---- |

| F-14  ·  Plano de Insumos |
| :---- |
| *O Agricultor antecipa necessidades de sementes, fertilizantes e defensivos mês a mês por Zona. O registro de aquisição com custo real torna-se permanente.* |

| US-28  ·  3 SP     Como Agricultor, eu quero criar um Plano de Insumos mensal para uma Zona para antecipar o que preciso adquirir antes do próximo ciclo. |
| :---- |

| ✓  Plano de Insumos criado com sucesso |
| :---- |

| Dado que | o Agricultor informa mês de referência futuro, tipo "Semente", quantidade 10 e vincula à Zona |
| :---- | :---- |
| **Quando** | cadastra o Plano de Insumos |
| **Então** | o plano mensal é criado com sucesso para a Zona e mês informados |

| ✗  RN-102   Plano para mês passado rejeitado |
| :---- |

| Dado que | o Agricultor informa o mês de referência "Janeiro/2026" (passado em relação à data atual de Abril/2026) |
| :---- | :---- |
| **Quando** | tenta criar o plano |
| **Então** | o sistema rejeita com erro MES\_REFERENCIA\_PASSADO |

| ✗  RN-103   Plano duplicado para mesma Zona e mês rejeitado |
| :---- |

| Dado que | já existe um Plano de Insumos para a Zona no mês "Junho/2026" |
| :---- | :---- |
| **Quando** | o Agricultor tenta criar outro plano para a mesma Zona e mesmo mês |
| **Então** | o sistema rejeita com erro PLANO\_DUPLICADO |

| ✗  RN-104   Tipo de insumo inválido rejeitado |
| :---- |

| Dado que | o Agricultor informa tipo "Herbicida" (fora do conjunto: Semente, Fertilizante, Defensivo) |
| :---- | :---- |
| **Quando** | submete o cadastro |
| **Então** | o sistema rejeita com erro TIPO\_INSUMO\_INVALIDO |

| ✗  RN-105   Quantidade de insumo zero ou negativa rejeitada |
| :---- |

| Dado que | o Agricultor informa quantidade 0 |
| :---- | :---- |
| **Quando** | submete o cadastro |
| **Então** | o sistema rejeita com erro QUANTIDADE\_INSUMO\_INVALIDA |

| US-29  ·  3 SP     Como Agricultor, eu quero registrar a aquisição de um item de insumo com o custo real pago para manter o controle financeiro da safra. |
| :---- |

| ✓  Item de insumo transitado para Adquirido com sucesso |
| :---- |

| Dado que | o item "Semente de Tomate" está com status Planejado e o Agricultor informa Preço Unitário R$ 15,00 |
| :---- | :---- |
| **Quando** | confirma a mudança de status para Adquirido |
| **Então** | o item transita para status Adquirido com data e custo registrados de forma permanente |

| ✗  RN-106   Transição para Adquirido sem Preço Unitário rejeitada |
| :---- |

| Dado que | o Agricultor tenta mudar o status do item para Adquirido sem informar o Preço Unitário |
| :---- | :---- |
| **Quando** | submete a mudança de status |
| **Então** | o sistema rejeita com erro PRECO\_UNITARIO\_OBRIGATORIO |

| ✗  RN-107   Reversão de status Adquirido para Planejado rejeitada |
| :---- |

| Dado que | o item "Fertilizante NPK" já possui status Adquirido com data de registro confirmada |
| :---- | :---- |
| **Quando** | o Agricultor tenta reverter o status para Planejado |
| **Então** | o sistema rejeita com erro REVERSAO\_STATUS\_INVALIDA para preservar a integridade do histórico financeiro |

| US-46  ·  2 SP     Como Operador, eu quero registrar o consumo de um insumo adquirido em uma Zona para manter o saldo de estoque atualizado. |
| :---- |

| ✓  Consumo de insumo registrado com sucesso |
| :---- |

| Dado que | a Zona possui 50 kg de Fertilizante adquirido e saldo disponível de 50 kg |
| :---- | :---- |
| **Quando** | o Operador registra o consumo de 20 kg de Fertilizante na Zona |
| **Então** | o saldo do Fertilizante na Zona é atualizado para 30 kg |

| ✗  RN-109   Consumo de insumo sem aquisição prévia rejeitado |
| :---- |

| Dado que | a Zona não possui nenhuma aquisição registrada para o tipo "Semente" |
| :---- | :---- |
| **Quando** | o Operador tenta registrar consumo de Semente na Zona |
| **Então** | o sistema rejeita com erro INSUMO\_SEM\_AQUISICAO |

| ✗  RN-110   Consumo acima do saldo disponível rejeitado |
| :---- |

| Dado que | o saldo disponível de Fertilizante na Zona é 30 kg |
| :---- | :---- |
| **Quando** | o Operador tenta registrar consumo de 50 kg de Fertilizante |
| **Então** | o sistema rejeita com erro SALDO\_INSUFICIENTE |

| ✗  RN-111   Consumo com unidade de medida divergente rejeitado |
| :---- |

| Dado que | o insumo Fertilizante foi adquirido com unidade "kg" |
| :---- | :---- |
| **Quando** | o Operador tenta registrar consumo de 5 "litros" de Fertilizante |
| **Então** | o sistema rejeita com erro UNIDADE\_INCOMPATIVEL |

| US-47  ·  2 SP     Como Gestor, eu quero configurar um limite mínimo de estoque por tipo de insumo em uma Zona para que o sistema acione automaticamente um pedido de reposição ao atingir esse ponto. |
| :---- |

| ✓  Limite mínimo de estoque configurado com sucesso |
| :---- |

| Dado que | a Zona possui Fertilizante adquirido com unidade "kg" e o Gestor informa limite de 20 kg e e-mail "fornecedor@email.com" |
| :---- | :---- |
| **Quando** | o Gestor confirma a configuração do limite mínimo |
| **Então** | o sistema salva a configuração e passa a monitorar o saldo do Fertilizante na Zona |

| ✗  RN-112   Limite com valor zero ou negativo rejeitado |
| :---- |

| Dado que | o Gestor informa valor de limite igual a 0 kg para Fertilizante na Zona |
| :---- | :---- |
| **Quando** | tenta salvar a configuração do limite mínimo |
| **Então** | o sistema rejeita com erro LIMITE\_INVALIDO |

| ✗  RN-113   Configuração sem e-mail de destinatário rejeitada |
| :---- |

| Dado que | o Gestor informa limite de 20 kg para Fertilizante mas não informa e-mail de destinatário |
| :---- | :---- |
| **Quando** | tenta salvar a configuração do limite mínimo |
| **Então** | o sistema rejeita com erro EMAIL\_DESTINATARIO\_OBRIGATORIO |

| ✗  RN-114   Segunda configuração de limite para mesmo insumo na mesma Zona rejeitada |
| :---- |

| Dado que | já existe uma configuração de limite ativa para Fertilizante na Zona |
| :---- | :---- |
| **Quando** | o Gestor tenta criar uma segunda configuração de limite para Fertilizante na mesma Zona |
| **Então** | o sistema rejeita com erro LIMITE\_JA\_CONFIGURADO |

| ✗  RN-115   Configuração de limite para insumo sem aquisição rejeitada |
| :---- |

| Dado que | a Zona não possui nenhuma aquisição registrada para o tipo "Defensivo" |
| :---- | :---- |
| **Quando** | o Gestor tenta configurar limite mínimo para Defensivo na Zona |
| **Então** | o sistema rejeita com erro INSUMO\_SEM\_AQUISICAO |

| US-48  ·  2 SP     Como Gestor, eu quero que o sistema envie automaticamente um pedido de reposição por e-mail quando o saldo de um insumo atingir o limite mínimo configurado. |
| :---- |

| ✓  Pedido de reposição enviado automaticamente |
| :---- |

| Dado que | o saldo de Fertilizante na Zona é 18 kg, o limite mínimo configurado é 20 kg e o e-mail do destinatário é "fornecedor@email.com" |
| :---- | :---- |
| **Quando** | o sistema verifica o saldo após registro de consumo que reduziu o saldo abaixo do limite |
| **Então** | o sistema envia e-mail de pedido de reposição ao destinatário e registra o PedidoReposição no histórico da Zona |

| ✗  RN-116   Saldo acima do limite não gera pedido |
| :---- |

| Dado que | o saldo de Fertilizante na Zona é 35 kg e o limite mínimo configurado é 20 kg |
| :---- | :---- |
| **Quando** | o sistema verifica o saldo após registro de consumo |
| **Então** | nenhum pedido de reposição é gerado pois o saldo permanece acima do limite |

| ✗  RN-117   Segundo pedido dentro de 24 horas bloqueado |
| :---- |

| Dado que | um pedido automático de Fertilizante já foi emitido para a Zona nas últimas 12 horas |
| :---- | :---- |
| **Quando** | o saldo cai novamente abaixo do limite mínimo |
| **Então** | o sistema não emite novo pedido e registra a tentativa bloqueada por intervalo de 24 horas |

| ✗  RN-118   Pedido contém todos os campos obrigatórios |
| :---- |

| Dado que | o saldo de Fertilizante na Zona atinge o limite mínimo e o pedido é disparado |
| :---- | :---- |
| **Quando** | o sistema gera o pedido automático de reposição |
| **Então** | o e-mail enviado contém: tipo de insumo, Zona de origem, saldo atual, quantidade sugerida de reposição e e-mail do destinatário |

| ✗  RN-119   Registro de PedidoReposição gerado no histórico |
| :---- |

| Dado que | o sistema aciona e envia um pedido automático de reposição de Fertilizante |
| :---- | :---- |
| **Quando** | o envio é concluído |
| **Então** | o sistema registra o PedidoReposição no histórico da Zona com data, hora, saldo no momento do disparo, destinatário e status "Enviado" |

| ✗  RN-120   Zona sem configuração de limite não gera pedido |
| :---- |

| Dado que | a Zona possui saldo de Semente mas não possui configuração de limite mínimo ativa para esse insumo |
| :---- | :---- |
| **Quando** | o saldo de Semente é reduzido por registro de consumo |
| **Então** | nenhum pedido de reposição é gerado para a Zona |

| US-30  ·  2 SP     Como Agricultor, eu quero consultar o histórico de insumos adquiridos por Zona para entender os custos reais de cada safra encerrada. |
| :---- |

| ✓  Histórico de insumos adquiridos exibido corretamente |
| :---- |

| Dado que | a Zona possui ciclo encerrado com itens de insumo no status Adquirido |
| :---- | :---- |
| **Quando** | o Agricultor consulta o histórico de insumos |
| **Então** | somente os itens Adquiridos vinculados a ciclos encerrados são exibidos no histórico |

| ✗  RN-108   Itens Planejados ou de ciclo ativo excluídos do histórico |
| :---- |

| Dado que | a Zona possui apenas itens Planejados em ciclo ativo, sem itens Adquiridos em ciclo encerrado |
| :---- | :---- |
| **Quando** | o Agricultor consulta o histórico de insumos |
| **Então** | o histórico retorna vazio — itens Planejados e de ciclos ativos não compõem o histórico financeiro |

| F-15  ·  Escrituração Financeira e Fluxo de Caixa |
| :---- |
| *O Agricultor registra receitas e despesas do ciclo produtivo. O sistema compõe o Fluxo de Caixa do período com entradas, saídas e saldo acumulado. Despesas de insumos adquiridos pelo Plano de Insumos alimentam automaticamente o fluxo.* |

| US-38  ·  3 SP     Como Agricultor, eu quero registrar lançamentos de receita e despesa na minha escrituração financeira para manter o controle do fluxo de caixa da safra. |
| :---- |

| ✓  Lançamento financeiro registrado com sucesso |
| :---- |

| Dado que | o Agricultor informa tipo "Receita", categoria "VendaDireta", valor R$ 1.500,00 e data de hoje |
| :---- | :---- |
| **Quando** | confirma o lançamento |
| **Então** | o lançamento é persistido e incorporado ao Fluxo de Caixa do período |

| ✗  RN-121   Tipo de lançamento inválido rejeitado |
| :---- |

| Dado que | o Agricultor informa tipo "Neutro" (fora do conjunto aceito: Receita, Despesa) |
| :---- | :---- |
| **Quando** | submete o lançamento |
| **Então** | o sistema rejeita com erro TIPO\_LANCAMENTO\_INVALIDO |

| ✗  RN-122   Categoria de Receita inválida rejeitada |
| :---- |

| Dado que | o Agricultor informa tipo "Receita" com categoria "Subsídio Estadual" (fora do conjunto: VendaDireta, Cooperativa, Subsídio, OutraReceita) |
| :---- | :---- |
| **Quando** | submete o lançamento |
| **Então** | o sistema rejeita com erro CATEGORIA\_RECEITA\_INVALIDA |

| ✗  RN-123a   Categoria de Despesa inválida rejeitada |
| :---- |

| Dado que | o Agricultor informa tipo "Despesa" com categoria "Combustível" (fora do conjunto aceito) |
| :---- | :---- |
| **Quando** | submete o lançamento |
| **Então** | o sistema rejeita com erro CATEGORIA\_DESPESA\_INVALIDA |

| ✗  RN-123b   Lançamento manual de despesa de insumo rejeitado |
| :---- |

| Dado que | o Agricultor tenta registrar manualmente uma despesa de insumo na escrituração |
| :---- | :---- |
| **Quando** | submete o lançamento com categoria relacionada a insumo |
| **Então** | o sistema rejeita com erro LANCAMENTO\_INSUMO\_DIRETO\_INVALIDO — despesas de insumos são originadas exclusivamente pelo Plano de Insumos |

| ✗  RN-124   Valor zero ou negativo rejeitado |
| :---- |

| Dado que | o Agricultor informa valor R$ 0,00 no lançamento |
| :---- | :---- |
| **Quando** | submete o lançamento |
| **Então** | o sistema rejeita com erro VALOR\_LANCAMENTO\_INVALIDO |

| ✗  RN-125   Data futura rejeitada |
| :---- |

| Dado que | o Agricultor informa data amanhã para o lançamento |
| :---- | :---- |
| **Quando** | submete o lançamento |
| **Então** | o sistema rejeita com erro DATA\_LANCAMENTO\_INVALIDA — a data do lançamento não pode ser posterior à data atual |

| US-39  ·  3 SP     Como Agricultor, eu quero visualizar o Fluxo de Caixa por período para acompanhar o saldo acumulado de entradas e saídas da minha operação. |
| :---- |

| ✓  Fluxo de Caixa exibido com sucesso |
| :---- |

| Dado que | o Agricultor possui lançamentos de receita e despesa registrados no trimestre corrente, incluindo despesas automáticas de insumos do Plano de Insumos |
| :---- | :---- |
| **Quando** | seleciona o período "Trimestre" e consulta o Fluxo de Caixa |
| **Então** | o sistema exibe total de receitas, total de despesas e saldo do período; se o saldo for negativo, o período é destacado como Deficitário |

| ✗  RN-126   Período de filtro inválido rejeitado |
| :---- |

| Dado que | o Agricultor informa período "Bimestre" (fora do conjunto: MêsAtual, Trimestre, Semestre, Ano) |
| :---- | :---- |
| **Quando** | tenta consultar o Fluxo de Caixa |
| **Então** | o sistema rejeita com erro PERIODO\_INVALIDO |

| ✗  RN-127   Filtro de período inválido no Fluxo de Caixa rejeitado |
| :---- |

| Dado que | o Agricultor tenta filtrar o Fluxo de Caixa pelo período "Bimestre" (fora dos valores aceitos) |
| :---- | :---- |
| **Quando** | aplica o filtro |
| **Então** | o sistema rejeita com erro PERIODO\_INVALIDO — o filtro aceita apenas: MêsAtual, Trimestre, Semestre ou Ano |

| ✓  Fluxo de Caixa exibe saldo deficitário destacado (RN-128) |
| :---- |

| Dado que | o Agricultor filtra o Fluxo de Caixa pelo período "Trimestre" com R$ 3.000 em receitas e R$ 4.500 em despesas registradas |
| :---- | :---- |
| **Quando** | o Fluxo de Caixa é exibido |
| **Então** | o sistema apresenta: total de receitas (R$ 3.000), total de despesas (R$ 4.500), saldo do período (− R$ 1.500) com o período destacado como Deficitário |

| F-16  ·  Demonstrações Financeiras e Indicadores |
| :---- |
| *⚙  FEATURE COMPLEXA — Feature que incorporou escopo de outra feature para reduzir o total do backlog. Story points e regras de negócio foram reorganizados.* |
| *Com a escrituração completa, o Agricultor acessa a DRE, o Balanço Patrimonial simplificado e os Indicadores Financeiros (margem bruta, custo por hectare e ROI da safra), todos gerados a partir dos registros existentes.* |

| US-40  ·  3 SP     Como Agricultor, eu quero consultar a Demonstração do Resultado do Exercício (DRE) de um período para saber se minha safra gerou lucro ou prejuízo. |
| :---- |

| ✓  DRE gerada com sucesso |
| :---- |

| Dado que | o período "Semestre" possui ao menos um lançamento de Receita e um de Despesa registrados |
| :---- | :---- |
| **Quando** | o Agricultor solicita a DRE do período |
| **Então** | o sistema retorna Receita Bruta, total de Despesas Operacionais e Resultado Líquido (Receita menos Despesas) |

| ✗  RN-129   DRE indisponível sem movimentação completa |
| :---- |

| Dado que | o período selecionado possui lançamentos de Receita mas nenhum de Despesa |
| :---- | :---- |
| **Quando** | o Agricultor solicita a DRE |
| **Então** | o sistema informa que a DRE está indisponível com erro DRE\_MOVIMENTACAO\_INCOMPLETA |

| ✗  RN-130   Período de filtro inválido para DRE rejeitado |
| :---- |

| Dado que | o Agricultor informa período "MêsAtual" (fora do conjunto aceito para DRE: Trimestre, Semestre, Ano) |
| :---- | :---- |
| **Quando** | solicita a DRE |
| **Então** | o sistema rejeita com erro PERIODO\_INVALIDO |

| ✗  RN-131   Filtro de período inválido na DRE rejeitado |
| :---- |

| Dado que | o Agricultor tenta gerar a DRE pelo período "MêsAtual" (fora dos valores aceitos para DRE) |
| :---- | :---- |
| **Quando** | solicita a geração |
| **Então** | o sistema rejeita com erro PERIODO\_INVALIDO — a DRE aceita apenas: Trimestre, Semestre ou Ano |

| US-41  ·  3 SP     Como Agricultor, eu quero consultar o Balanço Patrimonial simplificado e os Indicadores Financeiros para avaliar a saúde econômica da minha atividade. |
| :---- |

| ✓  Balanço e Indicadores exibidos com sucesso |
| :---- |

| Dado que | o Agricultor possui escrituração com ao menos uma colheita encerrada e lançamentos financeiros no mesmo período |
| :---- | :---- |
| **Quando** | consulta o Balanço Patrimonial e os Indicadores Financeiros |
| **Então** | o sistema exibe Ativo Circulante, Ativo Imobilizado, Passivo Circulante; e os indicadores: Margem Bruta Agrícola, Custo por Hectare e Retorno sobre Investimento da Safra |

| ✗  RN-132   Indicadores sem base de cálculo exibidos como Indisponíveis |
| :---- |

| Dado que | o Agricultor não possui nenhuma colheita encerrada no período ou não há lançamentos financeiros vinculados |
| :---- | :---- |
| **Quando** | consulta os Indicadores Financeiros |
| **Então** | o sistema exibe os indicadores com status Indisponível — não são calculados sem base suficiente |

| ✓  Indicadores Financeiros calculados e exibidos com base suficiente (RN-133) |
| :---- |

| Dado que | o Agricultor acessa os Indicadores Financeiros com ao menos uma colheita encerrada e lançamentos financeiros no período — Receita Bruta R$ 10.000, Custo dos Insumos R$ 3.000, total de despesas R$ 4.000, área ativa 2 ha, Resultado Líquido R$ 6.000 |
| :---- | :---- |
| **Quando** | consulta os indicadores |
| **Então** | o sistema exibe: Margem Bruta Agrícola 70%, Custo por Hectare R$ 2.000/ha e Retorno sobre Investimento da Safra 150% |

| ✗  RN-134   Indicadores Financeiros indisponíveis sem base de cálculo |
| :---- |

| Dado que | o Agricultor acessa os Indicadores Financeiros em um período sem nenhuma colheita encerrada |
| :---- | :---- |
| **Quando** | consulta os indicadores |
| **Então** | o sistema sinaliza todos os indicadores como Indisponíveis |

| Épico 8 — Clima e Alertas |
| :---- |

| F-17  ·  Limites Climáticos, Irrigação e Alertas |
| :---- |
| *⚙  FEATURE COMPLEXA — Incorpora: F-15 · Alertas Climáticos* |
| *O Agricultor define Limites Climáticos de temperatura e precipitação e a Necessidade Hídrica mínima da cultura ativa por Zona. O sistema emite Alertas Climáticos e Alertas de Necessidade de Irrigação quando os limites ou a necessidade hídrica são atingidos.* |

| US-31  ·  3 SP     Como Agricultor, eu quero cadastrar os limites climáticos toleráveis e a necessidade hídrica mínima para a cultura ativa em cada Zona para que o sistema identifique riscos com base na previsão e na precipitação acumulada. |
| :---- |

| ✓  Limites climáticos e Necessidade Hídrica cadastrados com sucesso |
| :---- |

| Dado que | a Zona possui vínculo de cultura ativo de "Tomate" |
| :---- | :---- |
| **Quando** | o Agricultor informa temperatura máxima 35°C, precipitação máxima 80 mm/24h e Necessidade Hídrica 15 mm para janela de 5Dias e confirma |
| **Então** | os limites climáticos e a Necessidade Hídrica são salvos com sucesso para a Zona e cultura ativa |

| ✗  RN-135   Limites em Zona sem cultura ativa rejeitados |
| :---- |

| Dado que | a Zona está Vazia (sem cultura ativa) |
| :---- | :---- |
| **Quando** | o Agricultor tenta cadastrar limites climáticos ou Necessidade Hídrica para essa Zona |
| **Então** | o sistema rejeita com erro ZONA\_SEM\_CULTURA\_ATIVA |

| ✗  RN-136a   Temperatura abaixo de \-5°C rejeitada |
| :---- |

| Dado que | o Agricultor informa temperatura de \-10°C (abaixo do mínimo de \-5°C) |
| :---- | :---- |
| **Quando** | submete o cadastro |
| **Então** | o sistema rejeita com erro TEMPERATURA\_INVALIDA |

| ✗  RN-136b   Temperatura acima de 50°C rejeitada |
| :---- |

| Dado que | o Agricultor informa temperatura de 60°C (acima do máximo de 50°C) |
| :---- | :---- |
| **Quando** | submete o cadastro |
| **Então** | o sistema rejeita com erro TEMPERATURA\_INVALIDA |

| ✗  RN-137   Precipitação acima de 300 mm/24h rejeitada |
| :---- |

| Dado que | o Agricultor informa precipitação de 350 mm/24h (acima do máximo de 300 mm/24h) |
| :---- | :---- |
| **Quando** | submete o cadastro |
| **Então** | o sistema rejeita com erro PRECIPITACAO\_INVALIDA |

| ✗  RN-138a   Necessidade Hídrica fora do intervalo rejeitada |
| :---- |

| Dado que | o Agricultor informa Necessidade Hídrica de 200 mm (acima do máximo de 150 mm) para a janela |
| :---- | :---- |
| **Quando** | submete o cadastro |
| **Então** | o sistema rejeita com erro NECESSIDADE\_HIDRICA\_INVALIDA |

| ✗  RN-138b   Janela de observação inválida rejeitada |
| :---- |

| Dado que | o Agricultor informa janela de observação "10Dias" (fora do conjunto aceito: 3Dias, 5Dias, 7Dias) |
| :---- | :---- |
| **Quando** | submete o cadastro |
| **Então** | o sistema rejeita com erro JANELA\_OBSERVACAO\_INVALIDA |

| US-32  ·  2 SP     Como Agricultor, eu quero editar ou remover os limites climáticos e a necessidade hídrica de uma Zona para ajustá-los conforme o cultivo evolui. |
| :---- |

| ✓  Limite climático e Necessidade Hídrica editados com sucesso |
| :---- |

| Dado que | a Zona possui cultura ativa e limites climáticos e Necessidade Hídrica cadastrados |
| :---- | :---- |
| **Quando** | o Agricultor edita o limite de temperatura para 38°C e a Necessidade Hídrica para 20 mm |
| **Então** | os dados são atualizados com sucesso |

| ✗  RN-139   Edição de limites sem cultura ativa rejeitada |
| :---- |

| Dado que | o ciclo da Zona foi encerrado — não há mais cultura ativa — e os limites foram cadastrados para aquele ciclo |
| :---- | :---- |
| **Quando** | o Agricultor tenta editar os limites climáticos ou a Necessidade Hídrica da Zona |
| **Então** | o sistema rejeita com erro LIMITE\_CLIMATICO\_IMUTAVEL |

| US-33  ·  3 SP     Como Agricultor, eu quero receber Alertas Climáticos quando a previsão ultrapassar os limites que defini, e Alertas de Irrigação quando a precipitação acumulada cair abaixo da necessidade hídrica da cultura, para agir antes que o dano aconteça. |
| :---- |

| ✓  Alerta Climático e Alerta de Irrigação gerados com sucesso |
| :---- |

| Dado que | a Zona possui cultura ativa, limites climáticos (precipitação máx. 80 mm/24h) e Necessidade Hídrica 15 mm/5Dias cadastrados; a previsão indica 120 mm/24h e a precipitação acumulada dos últimos 5 dias é 10 mm |
| :---- | :---- |
| **Quando** | o sistema processa a previsão e a precipitação acumulada |
| **Então** | um Alerta Climático de precipitação e um Alerta de Necessidade de Irrigação são gerados para a Zona e enviados ao Agricultor |

| ✗  RN-140   Alerta Climático não gerado para Zona sem limites cadastrados |
| :---- |

| Dado que | a Zona possui cultura ativa mas não possui limites climáticos cadastrados |
| :---- | :---- |
| **Quando** | a previsão indica condições adversas |
| **Então** | nenhum Alerta Climático é gerado — o sistema requer limites cadastrados para disparar alertas (erro ALERTA\_SEM\_LIMITE) |

| ✗  RN-141   Segundo Alerta Climático do mesmo tipo em 24h bloqueado |
| :---- |

| Dado que | um Alerta Climático de precipitação já foi gerado para a Zona há 6 horas |
| :---- | :---- |
| **Quando** | a previsão volta a ultrapassar o limite de precipitação |
| **Então** | o sistema bloqueia a geração de um segundo alerta do mesmo tipo dentro do intervalo de 24h (erro FREQUENCIA\_ALERTA\_EXCEDIDA) |

| ✗  RN-142   Alerta de Irrigação não gerado sem Necessidade Hídrica cadastrada |
| :---- |

| Dado que | a Zona possui cultura ativa mas não possui Necessidade Hídrica cadastrada |
| :---- | :---- |
| **Quando** | a precipitação acumulada cai abaixo de qualquer limiar |
| **Então** | nenhum Alerta de Irrigação é gerado — o sistema requer Necessidade Hídrica cadastrada (erro NECESSIDADE\_HIDRICA\_NAO\_CADASTRADA) |

| ✗  RN-143   Segundo Alerta de Irrigação em 24h bloqueado |
| :---- |

| Dado que | um Alerta de Necessidade de Irrigação já foi gerado para a Zona há 10 horas |
| :---- | :---- |
| **Quando** | a precipitação acumulada continua abaixo da Necessidade Hídrica definida |
| **Então** | o sistema bloqueia a geração de um segundo Alerta de Irrigação dentro do intervalo de 24 horas (erro FREQUENCIA\_ALERTA\_EXCEDIDA) |

| ✗  RN-144   Segundo Alerta de Necessidade de Irrigação em 24 horas não gerado |
| :---- |

| Dado que | o sistema gerou um Alerta de Necessidade de Irrigação para a Zona "Canteiro Norte" há 10 horas |
| :---- | :---- |
| **Quando** | a precipitação acumulada volta a cair abaixo da Necessidade Hídrica mínima da mesma Zona |
| **Então** | o sistema não emite novo alerta — o intervalo mínimo de 24 horas entre alertas da mesma Zona ainda não decorreu |

| F-18  ·  Consulta de Previsão do Tempo e Histórico Climático |
| :---- |
| *O Agricultor consulta a previsão dos próximos 7 dias por Zona e o Histórico Climático acumulado de ciclos encerrados para embasar decisões de plantio e manejo com dados reais da Propriedade.* |

| US-42  ·  3 SP     Como Agricultor, eu quero consultar a previsão do tempo dos próximos 7 dias para a região das minhas Zonas para planejar atividades de campo com antecedência. |
| :---- |

| ✓  Previsão do tempo exibida com sucesso |
| :---- |

| Dado que | a Zona pertence a uma Propriedade com Perfil Completo e localização — município "Recife" e estado "PE" — devidamente preenchidos |
| :---- | :---- |
| **Quando** | o Agricultor consulta a previsão para a Zona |
| **Então** | o sistema exibe os dados dos próximos 7 dias: temperatura mínima e máxima, precipitação estimada em mm e condição geral do dia; dias com previsão que ultrapasse os Limites Climáticos cadastrados são destacados como dias de risco |

| ✗  RN-145   Previsão indisponível com Perfil da Propriedade Incompleto |
| :---- |

| Dado que | a Propriedade do Agricultor possui Perfil Incompleto — sem município ou estado preenchido |
| :---- | :---- |
| **Quando** | o Agricultor tenta consultar a previsão do tempo para a Zona |
| **Então** | o sistema rejeita com erro PERFIL\_INCOMPLETO — a previsão requer localização completa |

| ✗  RN-146   Condição climática inválida no retorno da previsão |
| :---- |

| Dado que | a integração meteorológica retorna condição "Granizo" (fora do conjunto aceito: Ensolarado, Parcialmente Nublado, Nublado, Chuvoso, Tempestuoso) |
| :---- | :---- |
| **Quando** | o sistema tenta exibir a previsão com essa condição |
| **Então** | o sistema rejeita o dado da previsão com erro CONDICAO\_CLIMATICA\_INVALIDA e não exibe o dia afetado |

| ✓  Dia de risco destacado quando previsão ultrapassa Limites Climáticos (RN-147) |
| :---- |

| Dado que | a Zona "Canteiro Norte" possui Limite Climático de temperatura máxima de 35°C cadastrado e a previsão para quinta-feira indica temperatura máxima de 38°C |
| :---- | :---- |
| **Quando** | o Agricultor consulta a previsão dos próximos 7 dias |
| **Então** | o sistema destaca quinta-feira como dia de risco na consulta |

| US-43  ·  2 SP     Como Agricultor, eu quero consultar o Histórico Climático de uma Zona filtrado por período para identificar padrões sazonais e comparar as condições de ciclos anteriores. |
| :---- |

| ✓  Histórico Climático exibido com sucesso |
| :---- |

| Dado que | a Zona possui ao menos um ciclo de cultivo encerrado |
| :---- | :---- |
| **Quando** | o Agricultor filtra o Histórico Climático pelo período "Semestre" |
| **Então** | o sistema exibe temperatura média registrada, precipitação total acumulada e ocorrência de alertas climáticos gerados no período |

| ✗  RN-148   Histórico Climático indisponível para Zona sem ciclo encerrado |
| :---- |

| Dado que | a Zona nunca teve um ciclo de cultivo encerrado |
| :---- | :---- |
| **Quando** | o Agricultor tenta consultar o Histórico Climático |
| **Então** | o sistema rejeita com erro HISTORICO\_CLIMATICO\_INDISPONIVEL |

| ✗  RN-149   Período de filtro inválido rejeitado |
| :---- |

| Dado que | o Agricultor informa período "Quinzena" (fora do conjunto aceito: UltimaSafra, Trimestre, Semestre, Ano) |
| :---- | :---- |
| **Quando** | tenta consultar o Histórico Climático |
| **Então** | o sistema rejeita com erro PERIODO\_INVALIDO |

| ✓  Histórico Climático exibe dados consolidados do período (RN-150) |
| :---- |

| Dado que | o Agricultor filtra o Histórico Climático da Zona "Canteiro Norte" pelo período "UltimaSafra" e há dados registrados |
| :---- | :---- |
| **Quando** | o histórico é exibido |
| **Então** | o sistema apresenta: temperatura média registrada, precipitação total acumulada e ocorrência de alertas climáticos gerados pelo sistema naquele período |

