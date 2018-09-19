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
import controlador.Singleton;

public class InicioSesion extends AppCompatActivity {

    private EditText correoEditText;
    private EditText passwordEditText;
    private Button btnIniciarSesion;
    private Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        Singleton.getInstance().getControlador().setContext(this);

        correoEditText = findViewById(R.id.email_login);
        passwordEditText = findViewById(R.id.password_login);
        btnIniciarSesion = findViewById(R.id.sign_in_button);
        btnRegistrar = findViewById(R.id.sign_up_button);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    iniciarSesion();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registro();
            }
        });
    }

    public void iniciarSesion() throws InterruptedException, ExecutionException, JSONException {
        if (!correoEditText.getText().toString().equals("") && !passwordEditText.getText().toString().equals("")){
            DtoInicioSesion dtoInicioSesion = new DtoInicioSesion();
            dtoInicioSesion.setCorreo(correoEditText.getText().toString());
            dtoInicioSesion.setPassword(passwordEditText.getText().toString());

            if (Singleton.getInstance().getControlador().getGestorUsuarios().inicioSesion(dtoInicioSesion)) {
                Intent intent= new Intent(InicioSesion.this, MenuPrincipal.class);
                startActivity(intent);
            } else {
                Toast.makeText(this,"Datos incorrectos",Toast.LENGTH_LONG) .show();
            }
        } else {
            Toast.makeText(this,"Datos incorrectos",Toast.LENGTH_LONG) .show();
        }

    }

    public void registro() {
        Intent intent= new Intent(InicioSesion.this, Registro.class);
        //Intent intent= new Intent(InicioSesion.this, AgregarEventoActual.class);
        startActivity(intent);
    }

    /*
    private void errorMessageDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setIcon(R.drawable.ic_launcher_foreground)
                .setMessage(message).setTitle("Error").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) { return; }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }*/


}
