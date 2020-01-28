package com.projectviiigps.Data;

public class dataHijos {
    private  String nombre, edad, direccion,tiposangre,enfermedad,alergia;

    public dataHijos(String nombre, String edad, String direccion, String tiposangre, String enfermedad, String alergia) {
        this.nombre = nombre;
        this.edad = edad;
        this.direccion = direccion;
        this.tiposangre = tiposangre;
        this.enfermedad = enfermedad;
        this.alergia = alergia;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTiposangre() {
        return tiposangre;
    }

    public void setTiposangre(String tiposangre) {
        this.tiposangre = tiposangre;
    }

    public String getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(String enfermedad) {
        this.enfermedad = enfermedad;
    }

    public String getAlergia() {
        return alergia;
    }

    public void setAlergia(String alergia) {
        this.alergia = alergia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
