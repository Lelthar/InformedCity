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
    private DaoApi daoApi;
    private GestorEventos gestorEventos;
    private GestorMapa gestorMapa;
    private GestorUsuarios gestorUsuarios;

    public Controlador() {
       setUsuario(new Usuario());
       setDaoApi(new DaoApi());
       setEvento(new Evento());
       setListaEventos(new ArrayList<Evento>());
       setGestorEventos(new GestorEventos());
       setGestorMapa(new GestorMapa());
       setGestorUsuarios(new GestorUsuarios());
    }

    public GestorEventos getGestorEventos() {
        return gestorEventos;
    }

    public void setGestorEventos(GestorEventos gestorEventos) {
        this.gestorEventos = gestorEventos;
    }

    public GestorMapa getGestorMapa() {
        return gestorMapa;
    }

    public void setGestorMapa(GestorMapa gestorMapa) {
        this.gestorMapa = gestorMapa;
    }

    public GestorUsuarios getGestorUsuarios() {
        return gestorUsuarios;
    }

    public void setGestorUsuarios(GestorUsuarios gestorUsuarios) {
        this.gestorUsuarios = gestorUsuarios;
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

    public DaoApi getDaoApi() {
        return daoApi;
    }

    public void setDaoApi(DaoApi daoApi) {
        this.daoApi = daoApi;
    }
}
