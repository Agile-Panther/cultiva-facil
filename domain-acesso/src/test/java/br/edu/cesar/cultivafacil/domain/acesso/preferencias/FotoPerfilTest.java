package br.edu.cesar.cultivafacil.domain.acesso.preferencias;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FotoPerfilTest {

    private static final int MB_2 = 2 * 1024 * 1024;
    private static final int MB_5 = 5 * 1024 * 1024;
    private static final int MB_6 = 6 * 1024 * 1024;

    // ── Positivos ─────────────────────────────────────────────────────────

    @Test
    void aceitaFotoPNGDe2MB() {
        FotoPerfil foto = new FotoPerfil(new byte[MB_2], FormatoFoto.PNG);
        assertEquals(FormatoFoto.PNG, foto.getFormato());
        assertEquals(MB_2, foto.getBytes().length);
    }

    @Test
    void aceitaFotoJPGDe1MB() {
        FotoPerfil foto = new FotoPerfil(new byte[1024 * 1024], FormatoFoto.JPG);
        assertEquals(FormatoFoto.JPG, foto.getFormato());
    }

    @Test
    void aceitaFotoNoLimiteExatoDe5MB() {
        FotoPerfil foto = new FotoPerfil(new byte[MB_5], FormatoFoto.PNG);
        assertEquals(MB_5, foto.getBytes().length);
    }

    @Test
    void aceitaExtensaoPNG_viaDeExtensao() {
        assertEquals(FormatoFoto.PNG, FormatoFoto.deExtensao("PNG"));
    }

    @Test
    void aceitaExtensaoJPG_viaDeExtensao() {
        assertEquals(FormatoFoto.JPG, FormatoFoto.deExtensao("jpg"));
    }

    // ── RN-024a · Formato inválido ─────────────────────────────────────────

    @Test
    void rejeitaFormatoNulo() {
        // RN-024a
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> new FotoPerfil(new byte[MB_2], null)
        );
        assertEquals("FOTO_FORMATO_INVALIDO", ex.getMessage());
    }

    @Test
    void rejeitaFormatoGIF_viaDeExtensao() {
        // RN-024a
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> FormatoFoto.deExtensao("GIF")
        );
        assertEquals("FOTO_FORMATO_INVALIDO", ex.getMessage());
    }

    @Test
    void rejeitaExtensaoNula_viaDeExtensao() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> FormatoFoto.deExtensao(null)
        );
        assertEquals("FOTO_FORMATO_INVALIDO", ex.getMessage());
    }

    // ── RN-024b · Tamanho excedido ─────────────────────────────────────────

    @Test
    void rejeitaTamanhoAcimaDe5MB() {
        // RN-024b
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> new FotoPerfil(new byte[MB_6], FormatoFoto.PNG)
        );
        assertEquals("FOTO_TAMANHO_EXCEDIDO", ex.getMessage());
    }

    @Test
    void rejeitaBytesNulos() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> new FotoPerfil(null, FormatoFoto.PNG)
        );
        assertEquals("FOTO_TAMANHO_EXCEDIDO", ex.getMessage());
    }
}
