package com.example.gerald.informedcity.modelo;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gerald.informedcity.R;
import com.squareup.picasso.Picasso;

/**
 * Created by gerald on 16/09/18.
 */

public class CustomListInstituciones extends ArrayAdapter {
    private final Activity context;
    private String[] Nombre;
    private String[] Descripcion;
    private String[] Imagenes;


    public CustomListInstituciones(Activity context, String[] Descripcion, String[] Nick, String[] Imagenes) {
        super(context, R.layout.item_lista_comentarios, Nick);
        this.context = context;
        this.Descripcion = Descripcion;
        this.Nombre = Nick;
        this.Imagenes = Imagenes;
    }


    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_lista_instituciones, null, true);
        TextView lbNick = rowView.findViewById(R.id.nombre_usuario);
        TextView lbComentario = rowView.findViewById(R.id.comentario_usuario);
        ImageView imageView = rowView.findViewById(R.id.imageViewFoto);

        lbNick.setText(Descripcion[position]);
        lbComentario.setText(Nombre[position]);

        if (!TextUtils.isEmpty(this.Imagenes[position])) {
            Picasso.with(this.getContext()).load(this.Imagenes[position]).into(imageView);
        }

        return rowView;
    }
}
