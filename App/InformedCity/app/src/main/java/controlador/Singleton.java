package controlador;

/**
 * Created by gerald on 13/09/18.
 */

public class Singleton {
    private static Singleton INSTANCE = null;
    private Controlador controlador;

    private Singleton () {
        controlador = new Controlador();
    }

    public Controlador getControlador() {
        return controlador;
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    private synchronized  static void createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Singleton();
        }
    }

    public static Singleton getInstance() {
        if (INSTANCE == null) createInstance();
        return INSTANCE;
    }
}
