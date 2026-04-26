# language: pt
Funcionalidade: Edicao de perfil do Agricultor
  Como Agricultor
  Eu quero editar meus dados pessoais de perfil
  Para mante-los atualizados na plataforma

  @F03-US04 @positivo
  Cenario: Nome e foto atualizados com sucesso
    Dado que o Agricultor acessa a edicao do perfil pessoal
    Quando ele informa o nome "Maria da Silva" e envia uma foto PNG de 2 MB
    Entao o perfil e atualizado com o novo nome e foto
    E o evento PerfilEditado e publicado

  @F03-US04-RN023
  Cenario: Nome com menos de 2 caracteres rejeitado
    Dado que o Agricultor acessa a edicao do perfil pessoal
    Quando ele tenta salvar o nome "M"
    Entao o sistema rejeita com erro "NOME_INVALIDO"

  @F03-US04-RN024a
  Cenario: Foto em formato invalido rejeitada
    Dado que o Agricultor acessa a edicao do perfil pessoal
    Quando ele tenta enviar uma foto no formato "GIF"
    Entao o sistema rejeita com erro "FOTO_FORMATO_INVALIDO"

  @F03-US04-RN024b
  Cenario: Foto acima de 5 MB rejeitada
    Dado que o Agricultor acessa a edicao do perfil pessoal
    Quando ele tenta enviar uma foto PNG de 6 MB
    Entao o sistema rejeita com erro "FOTO_TAMANHO_EXCEDIDO"
