package br.edu.ifpb.sonoryextends.util;

import br.edu.ifpb.sonoryextends.model.User;

public class Session {
    private static User usuarioAtual;

    public static User getUsuarioAtual() {
        return usuarioAtual;
    }

    public static void setUsuarioAtual(User usuario) {
        usuarioAtual = usuario;
    }
}
