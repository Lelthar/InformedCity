package controlador;

import android.widget.Toast;

import com.example.gerald.informedcity.R;
import com.example.gerald.informedcity.modelo.Comentario;
import com.example.gerald.informedcity.modelo.Evento;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by gerald on 13/09/18.
 */

public class GestorEventos {

    private Evento eventoSeleccionado;

    public boolean agregarEvento(DtoEventos dtoEventos) throws JSONException, ExecutionException, InterruptedException {
        String direccion;
        if( dtoEventos.getTipo().equals("ACTUAL") ) {
            direccion = Singleton.getInstance().getControlador().getContext().getString(R.string.evento_actual);
        } else {
            direccion = Singleton.getInstance().getControlador().getContext().getString(R.string.evento_futuro);
        }
        //:nombre_evento, :categoria, :descripcion, :posicion_x, :posicion_y, :disponible, :reportes, :verificacion

        int user_id = Singleton.getInstance().getControlador().getUsuario().getIdUsuario();

        JSONObject jsonParam = new JSONObject();
        jsonParam.put("nombre_evento", dtoEventos.getNombre());
        jsonParam.put("categoria", dtoEventos.getCategoria());
        jsonParam.put("descripcion", dtoEventos.getDescripcion());
        jsonParam.put("posicion_x", dtoEventos.getPosicionX());
        jsonParam.put("posicion_y", dtoEventos.getPosicionY());
        jsonParam.put("disponible", true);
        jsonParam.put("reportes", dtoEventos.getReportes());
        jsonParam.put("verificacion", dtoEventos.getVerificaciones());
        jsonParam.put("user_id", user_id);

        String resultado = Singleton.getInstance().getControlador().getDaoApi().consultaApi(direccion,"POST",jsonParam);

        if (resultado.equals("Created")) {
            Toast.makeText(Singleton.getInstance().getControlador().getContext(), "Se publicó exitosamente el evento.", Toast.LENGTH_LONG).show();
            return true;
        } else {
            Toast.makeText(Singleton.getInstance().getControlador().getContext(), "Ocurrió un error inesperado." + resultado.toString(), Toast.LENGTH_LONG).show();
            return false;
        }
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

    public void setEventoSeleccionado(Evento eventoSeleccionado){
        this.eventoSeleccionado = eventoSeleccionado;
    }

    public Evento getEventoSeleccionado(){
        return this.eventoSeleccionado;
    }
}
