package vista;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gerald.informedcity.R;
import com.example.gerald.informedcity.modelo.CustomListInstituciones;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import controlador.Singleton;

public class MuroEmpresas extends AppCompatActivity {

    private Button buttonAcept;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muro_empresas);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView = findViewById(R.id.list_view);
        try {
            mostrarInstituticiones();
        } catch (ExecutionException e) {
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG) .show();
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG) .show();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG) .show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void mostrarInstituticiones() throws ExecutionException, InterruptedException, JSONException {

        String direccion = getResources().getString(R.string.instituciones)+".json";
        JSONObject json_parametros = new JSONObject();

        String resultado = Singleton.getInstance().getControlador().getDaoApi().consultaApi(direccion,"GET",json_parametros);

        JSONArray instituciones = new JSONArray(resultado);

        List<String> nombres = new ArrayList<>();
        List<String> descripciones = new ArrayList<>();
        List<String> imagenes = new ArrayList<>();

        JSONObject elemento;

        for (int i = 0; i < instituciones.length(); i++) {
            elemento = instituciones.getJSONObject(i);

            nombres.add(elemento.getString("name"));
            descripciones.add(elemento.getString("description"));
            imagenes.add(elemento.getString("image_url"));

        }


        String[] Nombres = nombres.toArray(new String[0]);
        String[] Descripciones = descripciones.toArray(new String[0]);
        String[] Imagenes = imagenes.toArray(new String[0]);

        CustomListInstituciones customListaInstituciones = new CustomListInstituciones(this, Nombres, Descripciones,Imagenes);

        listView.setAdapter(customListaInstituciones);
    }
}
