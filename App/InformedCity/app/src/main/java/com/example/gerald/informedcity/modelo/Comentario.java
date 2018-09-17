package com.example.gerald.informedcity.modelo;

/**
 * Created by gerald on 13/09/18.
 */

public class Comentario {
    private int idComentario;
    private String comentario;
    private Usuario usuario;

    public Comentario() {
        setUsuario(new Usuario());
    }

    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
