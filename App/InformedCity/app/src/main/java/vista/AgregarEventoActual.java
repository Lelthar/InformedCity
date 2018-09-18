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

import com.example.gerald.informedcity.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

public class AgregarEventoActual extends AppCompatActivity {

    private int PLACE_PICKER_REQUEST = 1;
    private LatLng latLng;

    private Spinner spinner;
    private EditText nombre;
    private EditText descripcion;
    private Button btnUbicacion;
    private Button btnCrear;

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
            }

        }
    }

    public void GuardarEvento(View view) throws JSONException {

        String titulo_evento = nombre.getText().toString();
        Spinner spinnerCategoria = findViewById(R.id.spinner_evento_actual);
        int ind = spinnerCategoria.getSelectedItemPosition();
        String categoria_evento = datos[ind];
        String descripcion_evento = descripcion.getText().toString();
        Float lat = convertToFloat(latLng.latitude);
        Float lon = convertToFloat(latLng.longitude);

        //:nombre_evento, :categoria, :descripcion, :posicion_x, :posicion_y, :disponible, :reportes, :verificacion
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nombre_evento",titulo_evento);//str
        jsonObject.put("descripcion",txtDescripcion.getText().toString());//str
        jsonObject.put("latitud",lat);//float
        jsonObject.put("longitud",lon);//float
        jsonObject.put("user_id",Integer.parseInt(datosJson.getString("id")));
        jsonObject.put("title",titulito);

        String result="";
        //
        try {
            result = conexion.execute("https://informedcityapp.herokuapp.com/events","POST",jsonObject.toString()).get();
            Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
            onBackPressed();
        } catch (InterruptedException e) {
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        } catch (ExecutionException e) {
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
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
