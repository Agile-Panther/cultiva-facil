package br.edu.cesar.cultivafacil.domain.acesso.preferencias;

import org.apache.commons.lang3.Validate;

import java.util.Arrays;

public final class FotoPerfil {

    private static final int TAMANHO_MAXIMO_BYTES = 5 * 1024 * 1024;

    private final byte[] bytes;
    private final FormatoFoto formato;

    public FotoPerfil(byte[] bytes, FormatoFoto formato) {
        Validate.isTrue(formato != null, "FOTO_FORMATO_INVALIDO");
        Validate.isTrue(bytes != null && bytes.length <= TAMANHO_MAXIMO_BYTES, "FOTO_TAMANHO_EXCEDIDO");
        this.bytes = bytes.clone();
        this.formato = formato;
    }

    public byte[] getBytes() {
        return bytes.clone();
    }

    public FormatoFoto getFormato() {
        return formato;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FotoPerfil)) return false;
        FotoPerfil that = (FotoPerfil) o;
        return Arrays.equals(bytes, that.bytes) && formato == that.formato;
    }

    @Override
    public int hashCode() {
        return 31 * Arrays.hashCode(bytes) + formato.hashCode();
    }
}
