package com.example.gerald.informedcity.modelo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gerald.informedcity.R;
/**
 * Created by gerald on 16/09/18.
 */

public class CustomListComentarios extends ArrayAdapter {
    private final Activity context;
    private String[] Nick;
    private String[] Comentario;
    private String[] imagenes;
    private String[] Fechas;


    public CustomListComentarios(Activity context, String[] Comentario, String[] Nick, String[] Imagenes,String[] Fechas) {
        super(context, R.layout.item_lista_comentarios, Nick);
        this.context = context;
        this.Comentario = Comentario;
        this.Nick = Nick;
        this.imagenes = Imagenes;
        this.Fechas=Fechas;
    }


    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_lista_comentarios, null, true);
        TextView lbNick = rowView.findViewById(R.id.nombre_usuario);
        TextView lbComentario = rowView.findViewById(R.id.comentario_usuario);
        TextView lbFecha = rowView.findViewById(R.id.fecha_public);

        lbNick.setText(Nick[position]);
        lbComentario.setText(Comentario[position]);
        lbFecha.setText(Fechas[position]);

        return rowView;
    }
}
