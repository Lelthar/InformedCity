package controlador;

import com.example.gerald.informedcity.modelo.Evento;

import java.util.ArrayList;

/**
 * Created by gerald on 13/09/18.
 */

public class DtoMapa {
    private ArrayList<Evento> listaEventos;

    public DtoMapa() {
    }

    public ArrayList<Evento> getListaEventos() {
        return listaEventos;
    }

    public void setListaEventos(ArrayList<Evento> listaEventos) {
        this.listaEventos = listaEventos;
    }
}
