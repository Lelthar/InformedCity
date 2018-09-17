package com.example.gerald.informedcity.modelo;

/**
 * Created by gerald on 16/09/18.
 */

public class Reporte {
    private int idReporte;
    private String motivo;
    private String descripcion;

    public Reporte() {
    }

    public int getId_reporte() {
        return idReporte;
    }

    public void setId_reporte(int idReporte) {
        this.idReporte = idReporte;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
