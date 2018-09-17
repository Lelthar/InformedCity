package vista;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gerald.informedcity.R;
import com.example.gerald.informedcity.modelo.Evento;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import controlador.Singleton;

public class TabEventos extends AppCompatActivity {
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_eventos);

        mTabHost = findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.contenido_tab);

        Bundle args1 = new Bundle();
        String tab1 = getResources().getString(R.string.tab1);
        String tab2 = getResources().getString(R.string.tab2);

        mTabHost.addTab(mTabHost.newTabSpec(tab1).setIndicator(tab1),
                fInformacionEvento.class,args1);
        mTabHost.addTab(mTabHost.newTabSpec(tab2).setIndicator(tab2),
                Comentarios.class,args1);

        mTabHost.getTabWidget().setBackgroundColor(this.getResources().getColor(R.color.colorButtons));
        mTabHost.getTabWidget().setDividerDrawable(R.mipmap.divider);

        for (int x = 0; x < mTabHost.getTabWidget().getChildCount(); x++) {
            View v=mTabHost.getTabWidget().getChildAt(x);
            final TextView tv = v.findViewById(android.R.id.title);
            // Look for the title view to ensure this is an indicator and not a divider.(I didn't know, it would return divider too, so I was getting an NPE)
            if (tv == null)
                continue;
            else {
                tv.setTextColor(Color.WHITE);
            }
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
            enviarVerificacion();
            return true;
        }else if(id == R.id.action_event_date_report){
            Intent intent = new Intent(getApplicationContext(),ReportarEvento.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void enviarVerificacion(){
        JSONObject jsonParam = new JSONObject();
        Evento evento = Singleton.getInstance().getControlador().getGestorEventos().getEventoSeleccionado();
        String direccion="";
        try {
            int check = evento.getVerificaciones()+1;
            jsonParam.put("verificacion",check);
            if(evento.getTipo().equals("ACTUAL")){
                direccion = Singleton.getInstance().getControlador().getContext().getString(R.string.evento_actual);
            }else {
                direccion = Singleton.getInstance().getControlador().getContext().getString(R.string.evento_futuro);
            }
            direccion+="/"+String.valueOf(evento.getIdEvento());
            String tipo = "PATCH";
            String resultado = Singleton.getInstance().getControlador().getDaoApi().consultaApi(direccion, tipo, jsonParam);
            Toast.makeText(this,resultado,Toast.LENGTH_LONG) .show();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
