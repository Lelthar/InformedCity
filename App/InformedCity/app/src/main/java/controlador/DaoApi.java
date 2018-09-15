package controlador;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by gerald on 13/09/18.
 */

public class DaoApi {
    private Conexion conexion;

    public DaoApi() {
    }

    public String consultaApi(String direccion, String tipo , JSONObject datos) throws ExecutionException, InterruptedException {
        this.conexion = new Conexion();
        return this.conexion.execute(direccion,tipo,datos.toString()).get();
    }

}
