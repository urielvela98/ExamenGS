package com.example.examengs;

class NPersona {
    private  String id;
    private  String nombreP;
    private  String nombreR;
    private  String descripcion;
    private  String foto;
    String especie, planeta, afiliaciones ;

    public NPersona(String id, String nombreP, String nombreR, String descripcion, String foto, String especie, String planeta, String afiliaciones) {
        this.id = id;
        this.nombreP = nombreP;
        this.nombreR = nombreR;
        this.descripcion = descripcion;
        this.foto = foto;
        this.especie = especie;
        this.planeta = planeta;
        this.afiliaciones = afiliaciones;
    }
    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getPlaneta() {
        return planeta;
    }

    public void setPlaneta(String planeta) {
        this.planeta = planeta;
    }

    public String getAfiliaciones() {
        return afiliaciones;
    }

    public void setAfiliaciones(String afiliaciones) {
        this.afiliaciones = afiliaciones;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreP() {
        return nombreP;
    }

    public void setNombreP(String nombreP) {
        this.nombreP = nombreP;
    }

    public String getNombreR() {
        return nombreR;
    }

    public void setNombreR(String nombreR) {
        this.nombreR = nombreR;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

}

