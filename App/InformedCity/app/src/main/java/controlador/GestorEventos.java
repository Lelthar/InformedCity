package controlador;

import com.example.gerald.informedcity.modelo.Comentario;
import com.example.gerald.informedcity.modelo.Evento;

import java.util.ArrayList;

/**
 * Created by gerald on 13/09/18.
 */

public class GestorEventos {

    public boolean agregarEvento(DtoEventos dtoEventos) {
        return false;
    }

    public Evento obtenerInformacionEvento(DtoEventos dtoEventos) {
        return new Evento();
    }

    public boolean agregarComentarioEvento(DtoEventos dtoEventos) {
        return false;
    }

    public ArrayList<Comentario> obtenerComentariosEvento(DtoEventos dtoEventos) {
        return new ArrayList<>();
    }

    public boolean agregarReporte(DtoReporte dtoReporte) {
        return false;
    }
}
