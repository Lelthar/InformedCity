package controlador;

import com.example.gerald.informedcity.modelo.Comentario;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by gerald on 13/09/18.
 */

public class DtoEventos {
    private String nombre;
    private String categoria;
    private String descripcion;
    private Date fechaPublicacion;
    private String posicionX;
    private String getPosicionY;
    private ArrayList<Comentario> comentarios;
    private String  tipo;
    private int reportes;
    private int verificaciones;

    public DtoEventos() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(String posicionX) {
        this.posicionX = posicionX;
    }

    public String getGetPosicionY() {
        return getPosicionY;
    }

    public void setGetPosicionY(String getPosicionY) {
        this.getPosicionY = getPosicionY;
    }

    public ArrayList<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(ArrayList<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getReportes() {
        return reportes;
    }

    public void setReportes(int reportes) {
        this.reportes = reportes;
    }

    public int getVerificaciones() {
        return verificaciones;
    }

    public void setVerificaciones(int verificaciones) {
        this.verificaciones = verificaciones;
    }
}
