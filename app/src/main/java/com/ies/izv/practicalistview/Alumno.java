package com.ies.izv.practicalistview;

import java.io.Serializable;

public class Alumno implements Comparable<Alumno>, Serializable{

    private String nombre, grupo;
    private boolean sexoFemenino;

    public Alumno(String nombre, String grupo, boolean sexoFemenino) {
        this.nombre = nombre;
        this.grupo = grupo;
        this.sexoFemenino = sexoFemenino;
    }

    public void set(Alumno a){
        this.nombre=a.nombre;
        this.grupo=a.grupo;
        this.sexoFemenino=a.sexoFemenino;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alumno alumno = (Alumno) o;

        if (!grupo.equals(alumno.grupo)) return false;
        if (!nombre.equals(alumno.nombre)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nombre.hashCode();
        result = 31 * result + grupo.hashCode();
        return result;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public boolean isSexoFemenino() {
        return sexoFemenino;
    }

    public String getSexo(){
        return sexoFemenino ? " femenino " : " masculino ";
    }

    public boolean isSexoMasculino() {
        return !sexoFemenino;
    }

    public void setSexoFemenino() {
        this.sexoFemenino = true;
    }

    public void setSexoMasculino() {
        this.sexoFemenino = false;
    }

    @Override
    public int compareTo(Alumno alumno) {
        int ct1 = this.grupo.compareTo(alumno.grupo);
        if(ct1==0){
            ct1 = this.nombre.compareTo(alumno.nombre);
        }
        return ct1;
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "nombre='" + nombre + '\'' +
                ", grupo='" + grupo + '\'' +
                (sexoFemenino ? " femenino " : " masculino ") +
                ", Femenino=" + sexoFemenino +
                ", Masculino=" + !sexoFemenino +
                '}';
    }
}