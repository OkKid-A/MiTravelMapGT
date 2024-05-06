package edu.cunoc.Estructuras.Graph;

import java.util.ArrayList;

public class Ruta implements Comparable<Ruta>{

    private ArrayList<String> ruta;
    private float distancia;

    public Ruta(ArrayList<String> ruta, float distancia) {
        this.ruta = ruta;
        this.distancia = distancia;
    }

    public ArrayList<String> getRuta() {
        return ruta;
    }

    public void setRuta(ArrayList<String> ruta) {
        this.ruta = ruta;
    }

    public float getDistancia() {
        return distancia;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }

    @Override
    public int compareTo(Ruta o) {
        return Float.compare(this.distancia, o.getDistancia());
    }
}
