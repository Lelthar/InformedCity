package com.example.gerald.informedcity.modelo;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by gerald on 13/09/18.
 */

public class Evento {
    private int idEvento;
    private String nombre;
    private String categoria;
    private String descripcion;
    private Date fechaPublicacion;
    private String posicionX;
    private String posicionY;
    private ArrayList<Comentario> comentarios;
    private String  tipo;
    private int reportes;
    private int verificaciones;
    private String fechaProgramada;
    private boolean disponible;

    public Evento() {
        idEvento=-1;
        nombre="";
        categoria="";
        descripcion="";
        fechaPublicacion=null;
        posicionX="";
        posicionY="";
        comentarios = new ArrayList<>();
        tipo="";
        reportes=0;
        verificaciones=0;
        fechaProgramada="";
        disponible=true;
    }



    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
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

    public String getPosicionY() {
        return posicionY;
    }

    public void setGetPosicionY(String getPosicionY) {
        this.posicionY = getPosicionY;
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

    public String getFechaProgramada(){
        return this.fechaProgramada;
    }

    public void setFechaProgramada(String fechaProgramada){
        this.fechaProgramada=fechaProgramada;
    }

    public void setDisponible(boolean disponible){
        this.disponible=disponible;
    }

    public boolean getDisponible(){
        return this.disponible;
    }
}
