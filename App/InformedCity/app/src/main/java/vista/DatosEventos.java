package vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

        editTextNombre = findViewById(R.id.editTextREName);
        editTextCategoria = findViewById(R.id.editTextDECategory);
        editTextDescripcion = findViewById(R.id.editTextREdescription);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dato_eventos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_event_date_check) {
            return true;
        }else if(id == R.id.action_event_date_report){
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
