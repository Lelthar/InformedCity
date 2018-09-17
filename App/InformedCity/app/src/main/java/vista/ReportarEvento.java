package vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gerald.informedcity.R;
import com.example.gerald.informedcity.modelo.Evento;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import controlador.Singleton;

public class ReportarEvento extends AppCompatActivity {

    private TextView textViewNombre;
    private EditText editTextMotivo;
    private EditText editTextDescripcion;
    private Button buttonAceptar;
    private Button buttonCancelar;
    private Evento evento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportar_evento);

        textViewNombre = findViewById(R.id.textViewREnombre);
        editTextMotivo = findViewById(R.id.editTextREName);
        editTextDescripcion = findViewById(R.id.editTextREdescription);
        buttonAceptar = findViewById(R.id.buttonREaccept);


        buttonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                generarReporte();
                finish();
            }
        });


        buttonCancelar = findViewById(R.id.buttonREcancel);

        evento = Singleton.getInstance().getControlador().getGestorEventos().getEventoSeleccionado();
        textViewNombre.setText(evento.getNombre());
    }

    public void generarReporte(){
        try {
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("motivo", editTextMotivo.getText().toString());
            jsonParam.put("descripcion", editTextDescripcion.getText().toString());

            jsonParam.put("user_id",Singleton.getInstance().getControlador().getUsuario().getIdUsuario());
            String direccion="";
            if(evento.getTipo().equals("ACTUAL")){
                direccion = Singleton.getInstance().getControlador().getContext().getString(R.string.reportes_evento_actual);
                jsonParam.put("current_event_id",evento.getIdEvento());
            }else {
                direccion = Singleton.getInstance().getControlador().getContext().getString(R.string.reportes_evento_futuro);
                jsonParam.put("future_event_id",evento.getIdEvento());
            }
            String tipo = "POST";
            String resultado = Singleton.getInstance().getControlador().getDaoApi().consultaApi(direccion, tipo, jsonParam);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
