package controlador;

/**
 * Created by gerald on 13/09/18.
 */

public class DtoInicioSesion {
    private String correo;
    private String password;

    public DtoInicioSesion() {
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
