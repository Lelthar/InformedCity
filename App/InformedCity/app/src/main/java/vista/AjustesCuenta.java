package vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gerald.informedcity.R;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

import controlador.DtoRegistro;
import controlador.Singleton;

public class AjustesCuenta extends AppCompatActivity {
    private EditText nombre;
    private EditText apellidos;
    private EditText nickname;
    private Button btnGuardar;
    private String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes_cuenta);

        nombre = findViewById(R.id.nombre_cuenta);
        apellidos = findViewById(R.id.apellidos_usuario);
        nickname = findViewById(R.id.nickname_usuario);
        btnGuardar = findViewById(R.id.guardar_datos_cuenta_button);

        tipo = getIntent().getExtras().getString("tipo");

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    modificarCuenta();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void modificarCuenta() throws InterruptedException, ExecutionException, JSONException {

            DtoRegistro dtoRegistro = new DtoRegistro();
            dtoRegistro.setNombre(nombre.getText().toString());
            dtoRegistro.setUserName(nickname.getText().toString());
            dtoRegistro.setApellidos(apellidos.getText().toString());
            dtoRegistro.setImage("path");

            if (Singleton.getInstance().getControlador().getGestorUsuarios().ajustesCuenta(dtoRegistro)){
                if (tipo.equals("registro")) {
                    Singleton.getInstance().getControlador().getUsuario().setIdUsuario(0);
                    Intent intent= new Intent(AjustesCuenta.this, InicioSesion.class);
                    startActivity(intent);
                } else {
                    Intent intent= new Intent(AjustesCuenta.this, MenuPrincipal.class);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(this,"Datos incorrectos",Toast.LENGTH_LONG) .show();
            }

    }
}
