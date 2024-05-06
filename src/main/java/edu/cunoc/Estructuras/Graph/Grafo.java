package edu.cunoc.Estructuras.Graph;

import edu.cunoc.Estructuras.ArbolB.NodoArbol;
import edu.cunoc.Main;

import java.util.*;

public class Grafo {

    private HashMap <String, HashMap<String, ArrayList<Trafico>>> trafico;
    private HashMap<String,NodoGrafo> grafo;

    public Grafo() {
        this.grafo = new HashMap<>();
    }

    public ArrayList<Ruta> calcularDistancias(String origen, String destino){
        ArrayList<Ruta> distancias = new ArrayList<>();
        ArrayList<ArrayList<String>> rutas = encontrarRutas(origen,destino);
        for (ArrayList<String> ruta : rutas) {
            int distancia = 0;
            for (int i = 0; i < ruta.size()-1; i++){
                distancia += grafo.get(ruta.get(i)).getWeights().get(ruta.get(i+1)).getDistancia();
            }
            distancias.add(new Ruta(ruta, distancia));
        }
        return distancias;
    }

    public ArrayList<Ruta> calcularDistanciasAuto(String origen, String destino){
        ArrayList<Ruta> distancias = new ArrayList<>();
        ArrayList<ArrayList<String>> rutas = encontrarRutasVehiculos(origen,destino);
        for (ArrayList<String> ruta : rutas) {
            int distancia = 0;
            for (int i = 0; i < ruta.size()-1; i++){
                distancia += grafo.get(ruta.get(i)).getWeightsVehiculo().get(ruta.get(i+1)).getDistancia();
            }
            distancias.add(new Ruta(ruta, distancia));
        }
        return distancias;
    }

    public ArrayList<ArrayList<String>> encontrarRutas(String origen, String destino){
        ArrayList<ArrayList<String>> rutas = new ArrayList<>();
        ArrayList<String> visitados = new ArrayList<>();
        visitados.add(origen);
        for(Map.Entry<String, Edge> edge: grafo.get(origen).getWeights().entrySet()){
            if(edge.getKey().equals(destino)){
                ArrayList<String> rutaInm = new ArrayList<>();
                rutaInm.add(origen);
                rutaInm.add(destino);
                rutas.add(rutaInm);
            } else {
                ArrayList<String> vistaCopia = new ArrayList<>(visitados);
                formarRuta(edge.getKey(),destino,rutas,vistaCopia,false);
            }
        }
        return rutas;
    }

    public ArrayList<ArrayList<String>> encontrarRutasVehiculos(String origen, String destino){
        ArrayList<ArrayList<String>> rutas = new ArrayList<>();
        ArrayList<String> visitados = new ArrayList<>();
        visitados.add(origen);
        for(Map.Entry<String, Edge> edge: grafo.get(origen).getWeightsVehiculo().entrySet()){
            if(edge.getKey().equals(destino)){
                ArrayList<String> rutaInm = new ArrayList<>();
                rutaInm.add(origen);
                rutaInm.add(destino);
                rutas.add(rutaInm);
            } else {
                ArrayList<String> vistaCopia = new ArrayList<>(visitados);
                formarRuta(edge.getKey(), destino, rutas, vistaCopia, true);
            }
        }
        return rutas;
    }

    public void formarRuta(String origen, String destino, ArrayList<ArrayList<String>> rutas, ArrayList<String> visitados, boolean vehiculo){
        visitados.add(origen);
        Set<Map.Entry<String, Edge>> entries;
        if (vehiculo){
             entries = grafo.get(origen).getWeightsVehiculo().entrySet();
        } else {
             entries = grafo.get(origen).getWeights().entrySet();
        }
        for(Map.Entry<String, Edge> edge: entries){
            if (!visitados.contains(edge.getKey())){
                if (edge.getKey().equals(destino)){
                    visitados.add(destino);
                    rutas.add(visitados);
                } else {
                    ArrayList<String> vistaCopia = new ArrayList<>(visitados);
                    formarRuta(edge.getKey(),destino,rutas,vistaCopia,vehiculo);
                }
            }
        }
    }

    public HashMap<String, NodoGrafo> getGrafo() {

        return grafo;
    }

    public void setGrafo(HashMap<String, NodoGrafo> grafo) {
        this.grafo = grafo;
    }

    public HashMap<String, HashMap<String, ArrayList<Trafico>>> getTrafico() {
        return trafico;
    }

    public void setTrafico(HashMap<String, HashMap<String, ArrayList<Trafico>>> trafico) {
        this.trafico = trafico;
    }
}
