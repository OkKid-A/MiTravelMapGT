package edu.cunoc.Lector;

import edu.cunoc.Estructuras.Graph.Edge;
import edu.cunoc.Estructuras.Graph.NodoGrafo;
import edu.cunoc.Estructuras.Graph.Trafico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Formateador {

    public Formateador() {
    }

    public Edge formatearEdge(String paso, HashMap<String, NodoGrafo> grafoHashMap) {
        String[] tokens = paso.split("(\\|)");
        Edge edge = new Edge(tokens[1],Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3])
                ,Integer.parseInt(tokens[4]),Integer.parseInt(tokens[5]),Integer.parseInt(tokens[6]));
        Edge edgeRetorno = new Edge(tokens[0],Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3])
                ,Integer.parseInt(tokens[4]),Integer.parseInt(tokens[5]),Integer.parseInt(tokens[6]));
        if (grafoHashMap.get(tokens[0]) == null) {
            grafoHashMap.put(tokens[0], new NodoGrafo(tokens[0]));
        }
        if (grafoHashMap.get(tokens[1]) == null) {
            grafoHashMap.put(tokens[1], new NodoGrafo(tokens[1]));
            grafoHashMap.get(tokens[1]).getWeights().put(tokens[0],edgeRetorno);
        }  else if (!grafoHashMap.get(tokens[1]).getWeightsVehiculo().containsKey(tokens[0])) {
            grafoHashMap.get(tokens[1]).getWeights().put(tokens[0],edgeRetorno);
        }
        grafoHashMap.get(tokens[0]).getWeightsVehiculo().put(tokens[1],edge);
        grafoHashMap.get(tokens[0]).getWeights().put(tokens[1],edge);
        return edge;
    }

    public Trafico formatearTrafico(String paso, HashMap<String, HashMap<String, ArrayList<Trafico>>> traficoHashMap) {
        String[] tokens = paso.split("(\\|)");
        Trafico trafico = new Trafico(Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3]),Integer.parseInt(tokens[4]));
        traficoHashMap.computeIfAbsent(tokens[0], k -> new HashMap<String, ArrayList<Trafico>>());
        if(traficoHashMap.get(tokens[0]).get(tokens[1]) == null) {
            ArrayList<Trafico> traficos = new ArrayList<>();
            traficos.add(trafico);
            traficoHashMap.get(tokens[0]).put(tokens[1],traficos);
        } else {
            traficoHashMap.get(tokens[0]).get(tokens[1]).add(trafico);
        }

        return trafico;
    }
}
