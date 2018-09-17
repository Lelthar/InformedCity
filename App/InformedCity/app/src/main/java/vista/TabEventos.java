package vista;

import android.graphics.Color;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.gerald.informedcity.R;
import com.example.gerald.informedcity.modelo.Evento;

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
}
