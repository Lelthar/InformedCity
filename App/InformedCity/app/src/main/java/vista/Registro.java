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

import controlador.DtoInicioSesion;
import controlador.DtoRegistro;
import controlador.Singleton;


public class Registro extends AppCompatActivity {
    private EditText correoEditText;
    private EditText passwordEditText;
    private EditText repeat_passwordEditText;
    private Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        correoEditText = findViewById(R.id.email_registro);
        passwordEditText = findViewById(R.id.password_registro);
        repeat_passwordEditText = findViewById(R.id.repeat_password_registro);
        btnRegistrar = findViewById(R.id.create_account_button);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    registrarCuenta();
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

    public void registrarCuenta() throws InterruptedException, ExecutionException, JSONException {

        if (passwordEditText.getText().toString().equals(repeat_passwordEditText.getText().toString())) {
            DtoRegistro dtoRegistro = new DtoRegistro();
            dtoRegistro.setCorreo(correoEditText.getText().toString());
            dtoRegistro.setPassword(passwordEditText.getText().toString());

            if (Singleton.getInstance().getControlador().getGestorUsuarios().registroUsuario(dtoRegistro)) {
                Intent intent= new Intent(Registro.this, AjustesCuenta.class);
                intent.putExtra("tipo","registro");
                startActivity(intent);
            } else {
                Toast.makeText(this,"Datos incorrectos",Toast.LENGTH_LONG) .show();
            }
        } else {
            Toast.makeText(this,"Las contraseñas no coinciden.",Toast.LENGTH_LONG) .show();
        }


    }
}
