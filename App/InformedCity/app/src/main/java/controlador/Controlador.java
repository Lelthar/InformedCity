package controlador;

import com.example.gerald.informedcity.modelo.Evento;
import com.example.gerald.informedcity.modelo.Usuario;

import java.util.ArrayList;

/**
 * Created by gerald on 13/09/18.
 */

public class Controlador {
    private Usuario usuario;
    private Evento evento;
    private ArrayList<Evento> listaEventos;

    public Controlador() {
       setUsuario(new Usuario());
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public ArrayList<Evento> getListaEventos() {
        return listaEventos;
    }

    public void setListaEventos(ArrayList<Evento> listaEventos) {
        this.listaEventos = listaEventos;
    }
}
