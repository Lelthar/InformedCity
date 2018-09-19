package vista;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.gerald.informedcity.R;

public class MuroEmpresas extends AppCompatActivity {

    private Button buttonAcept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muro_empresas);
        buttonAcept = findViewById(R.id.buttonWallAcept);
        buttonAcept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
