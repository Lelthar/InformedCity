package vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gerald.informedcity.R;
import com.example.gerald.informedcity.modelo.Evento;

import controlador.Singleton;

public class DatosEventos extends AppCompatActivity {

    private EditText editTextNombre;
    private EditText editTextCategoria;
    private EditText editTextFechaPubli;
    private EditText editTextDescripcion;
    private EditText editTextFechaProgra;
    private TextView textViewEstado;
    private TextView textViewFechaProgra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_eventos);

        editTextNombre = findViewById(R.id.editTextDEName);
        editTextCategoria = findViewById(R.id.editTextDECategory);
        editTextDescripcion = findViewById(R.id.editTextDEDescription);
        editTextFechaPubli = findViewById(R.id.editTextDEPubDate);
        editTextFechaProgra = findViewById(R.id.editTextDESchDate);
        textViewEstado = findViewById(R.id.textViewDEestado);
        textViewFechaProgra = findViewById(R.id.textViewScDate);


        Evento evento = Singleton.getInstance().getControlador().getGestorEventos().getEventoSeleccionado();

        editTextNombre.setText(evento.getNombre());
        editTextCategoria.setText(evento.getCategoria());
        editTextFechaPubli.setText(evento.getFechaPublicacion());
        editTextDescripcion.setText(evento.getDescripcion());

        if(evento.getDisponible()){
            textViewEstado.setText("Verificado");
        }else{
            textViewEstado.setText("No verificado");
        }

        if(evento.getTipo().equals("ACTUAL")){
            editTextFechaProgra.setVisibility(View.INVISIBLE);
            textViewFechaProgra.setVisibility(View.INVISIBLE);
        }else{
            editTextFechaProgra.setText(evento.getFechaProgramada());
        }


    }
}
