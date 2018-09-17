package vista;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gerald.informedcity.R;
import com.example.gerald.informedcity.modelo.CustomListComentarios;
import com.example.gerald.informedcity.modelo.Evento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import controlador.Singleton;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Comentarios.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Comentarios#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Comentarios extends Fragment {


    // TODO: Rename and change types of parameters

    private View rootView;
    private String id_usuario, id_pelicula;
    private EditText comentario;
    private TextView labelNoComentarios;
    private ListView listaComentarios;
    private JSONArray todosComentarios;
    private JSONArray todosUsuarios;
    private Button btnComentar;

    public Comentarios() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Comentarios.
     */
    // TODO: Rename and change types and number of parameters
    public static Comentarios newInstance(String param1, String param2) {
        Comentarios fragment = new Comentarios();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_comentarios, container, false);
        labelNoComentarios = rootView.findViewById(R.id.labelNoComentarios);
        listaComentarios = rootView.findViewById(R.id.listComentarios);
        comentario = rootView.findViewById(R.id.commentario_entry);
        btnComentar = rootView.findViewById(R.id.btnAgregarComentario);

        btnComentar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    AgregarComentario();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            actualizarComentarios();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rootView;
    }

    private void actualizarDatos() {
        try {
            Evento evento = Singleton.getInstance().getControlador().getGestorEventos().getEventoSeleccionado();
            String direccion;
            if (evento.getTipo().equals("ACTUAL")) {
                direccion = getResources().getString(R.string.comentario_evento_actual);
            } else {
                direccion = getResources().getString(R.string.comentario_evento_futuro);
            }
            JSONObject json_parametros = new JSONObject();
            String resultado = Singleton.getInstance().getControlador().getDaoApi().consultaApi(direccion,"GET",json_parametros);

            this.todosComentarios = new JSONArray(resultado);

            direccion = getResources().getString(R.string.users);

            resultado = Singleton.getInstance().getControlador().getDaoApi().consultaApi(direccion,"GET",json_parametros);
            this.todosUsuarios = new JSONArray(resultado);
        } catch (InterruptedException e) {
            Toast.makeText(this.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        } catch (ExecutionException e) {
            Toast.makeText(this.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            Toast.makeText(this.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void actualizarComentarios() throws JSONException {

        actualizarDatos();

        JSONArray datosComentarios = this.todosComentarios;
        JSONArray datosUsuarios = this.todosUsuarios;

        JSONArray comentariosFiltrados = new JSONArray();

        if (datosComentarios !=null) {
            if (datosComentarios.length() > 0) {
                Evento evento = Singleton.getInstance().getControlador().getGestorEventos().getEventoSeleccionado();
                String idTipo;
                if (evento.getTipo().equals("ACTUAL")) {
                    idTipo = "current_event_id";
                } else {
                    idTipo = "future_event_id";
                }
                for (int i = 0; i < datosComentarios.length(); i++) {
                    JSONObject elemento = datosComentarios.getJSONObject(i);
                    if (elemento.getString(idTipo).equals(evento.getIdEvento())) {
                        comentariosFiltrados.put(elemento);
                    }
                }

                if (comentariosFiltrados != null) {
                    if (comentariosFiltrados.length() > 0) {
                        labelNoComentarios.setVisibility(View.INVISIBLE);

                        List<String> nicks = new ArrayList<>();
                        List<String> comentarios = new ArrayList<>();
                        List<String> imagenes = new ArrayList<>();

                        JSONObject elemento;

                        for (int i = 0; i < comentariosFiltrados.length(); i++) {
                            elemento = comentariosFiltrados.getJSONObject(i);

                            JSONObject usuario;
                            for (int k = 0; k < datosUsuarios.length(); k++) {
                                usuario = datosUsuarios.getJSONObject(k);
                                if (elemento.get("user_id").equals(usuario.get("id"))){
                                    nicks.add(usuario.getString("nickname"));
                                    imagenes.add(usuario.getString("image"));
                                    break;
                                }
                            }

                            comentarios.add(elemento.getString("texto_comentario"));
                        }


                        String[] Nicks = nicks.toArray(new String[0]);
                        String[] Comentarios = comentarios.toArray(new String[0]);
                        String[] Imagenes = imagenes.toArray(new String[0]);


                        CustomListComentarios customListComentarios = new CustomListComentarios(getActivity(), Comentarios, Nicks,Imagenes);

                        listaComentarios.setAdapter(customListComentarios);
                    } else
                        labelNoComentarios.setVisibility(View.VISIBLE);
                } else
                    labelNoComentarios.setVisibility(View.VISIBLE);
            } else
                labelNoComentarios.setVisibility(View.VISIBLE);
        } else
            labelNoComentarios.setVisibility(View.VISIBLE);
    }

    private void AgregarComentario() throws JSONException, ExecutionException, InterruptedException {
        String strComentario = comentario.getText().toString();

        if (!strComentario.isEmpty()) {
            Evento evento = Singleton.getInstance().getControlador().getGestorEventos().getEventoSeleccionado();
            if (evento.getTipo().equals("ACTUAL")) {
                JSONObject json_parametros = new JSONObject();
                json_parametros.put("texto_comentario", strComentario);
                json_parametros.put("bloqueado", false);
                json_parametros.put("current_event_id", evento.getIdEvento());
                json_parametros.put("user_id", Singleton.getInstance().getControlador().getUsuario().getIdUsuario());
                String direccion = getResources().getString(R.string.comentario_evento_actual);
                String resultado = Singleton.getInstance().getControlador().getDaoApi().consultaApi(direccion,"POST",json_parametros);

                if (resultado.equals("Created")) {
                    Toast.makeText(rootView.getContext(), "Se publicó exitosamente el comentario.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(rootView.getContext(), "Ocurrió un error inesperado." + resultado.toString(), Toast.LENGTH_LONG).show();
                    //Toast.makeText(rootView.getContext(), json_parametros.toString(), Toast.LENGTH_LONG).show();
                }
            } else {
                JSONObject json_parametros = new JSONObject();
                json_parametros.put("texto_comentario", strComentario);
                json_parametros.put("bloqueado", false);
                json_parametros.put("future_event_id", evento.getIdEvento());
                json_parametros.put("user_id", Singleton.getInstance().getControlador().getUsuario().getIdUsuario());
                String direccion = getResources().getString(R.string.comentario_evento_futuro);
                String resultado = Singleton.getInstance().getControlador().getDaoApi().consultaApi(direccion,"POST",json_parametros);

                if (resultado.equals("Created")) {
                    Toast.makeText(rootView.getContext(), "Se publicó exitosamente el comentario.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(rootView.getContext(), "Ocurrió un error inesperado." + resultado.toString(), Toast.LENGTH_LONG).show();
                    //Toast.makeText(rootView.getContext(), json_parametros.toString(), Toast.LENGTH_LONG).show();
                }
            }


        } else {
            Toast.makeText(rootView.getContext(), "El comentario no puede estar vacío", Toast.LENGTH_LONG).show();
        }
    }

}
