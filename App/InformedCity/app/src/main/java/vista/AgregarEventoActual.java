package vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gerald.informedcity.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import controlador.DtoEventos;
import controlador.Singleton;

public class AgregarEventoActual extends AppCompatActivity {

    private int PLACE_PICKER_REQUEST = 1;
    private LatLng latLng;

    private Spinner spinner;
    private EditText nombre;
    private EditText descripcion;
    private Button btnUbicacion;
    private Button btnCrear;
    private TextView textView;

    public String[] datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_evento_actual);

        spinner = findViewById(R.id.spinner_evento_actual);
        nombre = findViewById(R.id.nombre_edit_text_evento_a);
        descripcion = findViewById(R.id.descripcion_agregar_evento_actual);
        btnUbicacion = findViewById(R.id.btn_ubicacion_evento_actual);
        btnCrear = findViewById(R.id.create_evento_actual);
        textView = findViewById(R.id.ubicacion_actual_evento_actual);

        datos = new String[] {"Robo o Asalto", "Accidente de Tránsito", "Congestión Vial", "Descarrilamiento de Tren", "Incendio", "Personas Misteriosas en la Zona", "Pleitos o Peleas",
                "Derrumbes", "Inundaciones", "Caída de Objeto", "Persona Desaparecida", "Mascota Desaparecida", "Apagón", "Sin Agua Potable", "Espectáculo en Vía Pública", "Bloqueo de Vía", "Otros"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);
        spinner.setAdapter(adapter);

    }

    public void goPlacePicker(View view){

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(AgregarEventoActual.this),PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if(requestCode == PLACE_PICKER_REQUEST){
            if(resultCode == RESULT_OK ){
                Place place = PlacePicker.getPlace(AgregarEventoActual.this,data);

                //agregar coordenadas
                latLng = place.getLatLng();
                textView.setText(latLng.toString());
            }

        }
    }

    public void guardarEvento(View view) throws JSONException, ExecutionException, InterruptedException {
        Toast.makeText(this,"Se creó el evento",Toast.LENGTH_SHORT).show();
        if (latLng != null) {
            String titulo_evento = nombre.getText().toString();
            Spinner spinnerCategoria = findViewById(R.id.spinner_evento_actual);
            int ind = spinnerCategoria.getSelectedItemPosition();
            String categoria_evento = datos[ind];
            String descripcion_evento = descripcion.getText().toString();
            Float lat = convertToFloat(latLng.latitude);
            Float lon = convertToFloat(latLng.longitude);

            DtoEventos dtoEventos = new DtoEventos();
            dtoEventos.setNombre(titulo_evento);
            dtoEventos.setCategoria(categoria_evento);
            dtoEventos.setDescripcion(descripcion_evento);
            dtoEventos.setPosicionX(lat.toString());
            dtoEventos.setPosicionY(lon.toString());
            dtoEventos.setReportes(0);
            dtoEventos.setVerificaciones(0);
            dtoEventos.setTipo("ACTUAL");

            if (Singleton.getInstance().getControlador().getGestorEventos().agregarEvento(dtoEventos)){
                Toast.makeText(this,"Se creó el evento",Toast.LENGTH_SHORT).show();
                onBackPressed();
            } else {
                Toast.makeText(this,"No se creó el evento",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this,"Necesita seleccionar una ubicación",Toast.LENGTH_SHORT).show();
        }



    }

    public static Float convertToFloat(Double doubleValue) {
        return doubleValue == null ? null : doubleValue.floatValue();
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
