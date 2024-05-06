package edu.cunoc.Estructuras.Graph;

import java.util.ArrayList;
import java.util.HashMap;

public class NodoGrafo {

    private HashMap<String,Edge> weights;
    private HashMap<String,Edge> weightsVehiculo;
    private String key;

    public NodoGrafo(String key) {
        weights = new HashMap<>();
        weightsVehiculo = new HashMap<>();
        this.key = key;
    }

    public HashMap<String, Edge> getWeightsVehiculo() {
        return weightsVehiculo;
    }

    public void setWeightsVehiculo(HashMap<String, Edge> weightsVehiculo) {
        this.weightsVehiculo = weightsVehiculo;
    }

    public Edge buscarNodo(String nombre) {
        return weights.get(nombre);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ArrayList<Integer> getDistancias(){
        ArrayList<Integer> pesos = new ArrayList<>();
        for(Edge edge : weights.values()){
            pesos.add(edge.getDistancia());
        }
        return pesos;
    }

    public HashMap<String, Edge> getWeights() {
        return weights;
    }

    public void setWeights(HashMap<String, Edge> weights) {
        this.weights = weights;
    }


}
