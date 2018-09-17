package vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.gerald.informedcity.R;

public class AgregarEventoActual extends AppCompatActivity {
    private int PLACE_PICKER_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_evento_actual);
    }
}
