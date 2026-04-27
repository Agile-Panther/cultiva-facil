package br.edu.cesar.cultivafacil.domain.acesso.preferencias;

public enum FormatoFoto {
    JPG, PNG;

    public static FormatoFoto deExtensao(String extensao) {
        if (extensao != null) {
            for (FormatoFoto f : values()) {
                if (f.name().equalsIgnoreCase(extensao)) {
                    return f;
                }
            }
        }
        throw new IllegalArgumentException("FOTO_FORMATO_INVALIDO");
    }
}
