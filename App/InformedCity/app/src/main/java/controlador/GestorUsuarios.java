package controlador;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.gerald.informedcity.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by gerald on 13/09/18.
 */

public class GestorUsuarios {

    public GestorUsuarios() {

    }

    public boolean registroUsuario(DtoRegistro dtoRegistro) {
        return false;
    }

    public boolean inicioSesion(DtoInicioSesion dtoInicioSesion) throws JSONException, ExecutionException, InterruptedException {
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("email", dtoInicioSesion.getCorreo());
        jsonParam.put("password", dtoInicioSesion.getPassword());
        String direccion = Singleton.getInstance().getControlador().getContext().getString(R.string.user_login);
        String tipo = "POST";
        String resultado = Singleton.getInstance().getControlador().getDaoApi().consultaApi(direccion,tipo,jsonParam);

        if(resultado.equals("OK")) {
            DownLoadTask downLoadTask = new DownLoadTask();
            JSONObject json_user = new JSONObject(downLoadTask.execute(dtoInicioSesion.getCorreo(),dtoInicioSesion.getPassword(),direccion).get());
            json_user = json_user.getJSONObject("data");
            Singleton.getInstance().getControlador().getUsuario().setCorreo(json_user.getString("email"));
            Singleton.getInstance().getControlador().getUsuario().setNombre(json_user.getString("name"));
            Singleton.getInstance().getControlador().getUsuario().setApellidos(json_user.getString("lastname"));
            Singleton.getInstance().getControlador().getUsuario().setUserName(json_user.getString("nickname"));
            Singleton.getInstance().getControlador().getUsuario().setIdUsuario(json_user.getInt("id"));
            return true;
        }
        return false;
    }

    public class DownLoadTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... strings) {
            String xmlString;
            HttpURLConnection urlConnection = null;
            URL url = null;

            try {
                url = new URL(strings[2]);
                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestProperty("email",strings[0]);
                urlConnection.setRequestProperty("password",strings[1]);
                urlConnection.setRequestProperty("Content-Type","application/json");
                urlConnection.setRequestMethod("POST");
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    StringBuilder xmlResponse = new StringBuilder();
                    BufferedReader input = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String strLine = null;
                    while ((strLine = input.readLine()) != null) {
                        xmlResponse.append(strLine);
                    }
                    xmlString = xmlResponse.toString();
                    //xmlString += urlConnection.getHeaderField("access-token");
                    input.close();
                    return xmlString;

                }else{
                    return "Usuario Incorrecto";
                }
            }
            catch (Exception e) {
                return e.toString();
            }
            finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }
    }
}
