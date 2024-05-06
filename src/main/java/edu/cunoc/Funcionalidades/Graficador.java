package edu.cunoc.Funcionalidades;

import edu.cunoc.Estructuras.ArbolB.Arbol;
import edu.cunoc.Estructuras.ArbolB.NodoArbol;
import edu.cunoc.Estructuras.ArbolB.PaginaArbol;
import edu.cunoc.Estructuras.Graph.Edge;
import edu.cunoc.Estructuras.Graph.Grafo;
import edu.cunoc.Estructuras.Graph.NodoGrafo;
import edu.cunoc.Estructuras.Graph.Ruta;
import guru.nidi.graphviz.attribute.*;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Compass;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import guru.nidi.graphviz.model.Node;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static guru.nidi.graphviz.attribute.Records.rec;
import static guru.nidi.graphviz.attribute.Records.turn;
import static guru.nidi.graphviz.model.Factory.*;

public class Graficador {

    public Graficador() {
        generarGrafoUndirectedTrial();
    }

    public void generarGrafoUndirectedTrial(){
        MutableGraph graph =
        mutGraph("grafo").setDirected(false);
        MutableNode nodo0 = mutNode("0").add(Records.of(rec("1","1a"), rec("2","2a"))),
        nodo1 = mutNode("1").add(Records.of(rec("1","1b"), rec("2","2b")));
        graph.add(mutNode("nodo").add(Records.of(turn(rec("f0","")))));
        graph.add(nodo0.addLink(between(port("1"),nodo1.port("2",Compass.NORTH))));
        graph.add(mutNode("a"));

        graph.add(mutNode("a").addLink(mutNode("b")));
        graph.add(mutNode("a").addLink(to(mutNode("b")).add(Color.RED)));
        graph.add(mutNode("b").addLink("a"));
        try {
            Graphviz.fromGraph(graph).width(200).render(Format.PNG).toFile(new File("/home/okkid-a/Documents/EDD/MiTravelMapGT/src/main/resources0"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage generarGrafoUndirected(Grafo grafo){
        MutableGraph graph = mutGraph("grafo").setDirected(false);
        HashMap<String, NodoGrafo> mapa =  grafo.getGrafo();
        ArrayList<String> keysPassed = new ArrayList<>();
        for (Map.Entry<String, NodoGrafo> entry : mapa.entrySet()) {
            graph.add(mutNode(entry.getKey()).add(Font.size(10)));
            for (Map.Entry<String, Edge> edge : entry.getValue().getWeights().entrySet()){
                if (!keysPassed.contains(edge.getKey())) {
                    graph.add(mutNode(entry.getKey()).addLink(mutNode(edge.getKey())));
                }
            }
            keysPassed.add(entry.getKey());
        }
        try {
            Graphviz.fromGraph(graph).width(1500).height(800).render(Format.PNG).toFile(new File("/home/okkid-a/Documents/EDD/MiTravelMapGT/src/main/resources0"));
        } catch (IOException e) {
            e.printStackTrace();
        }
            return Graphviz.fromGraph(graph).width(1500).height(800).render(Format.PNG).toImage();
    }

    public BufferedImage generarGrafoDirected(Grafo grafo){
        MutableGraph graph = mutGraph("grafo").setDirected(true);
        HashMap<String, NodoGrafo> mapa =  grafo.getGrafo();
        for (Map.Entry<String, NodoGrafo> entry : mapa.entrySet()) {
            graph.add(mutNode(entry.getKey()));
            for (Map.Entry<String, Edge> edge : entry.getValue().getWeightsVehiculo().entrySet()){
                    graph.add(mutNode(entry.getKey()).addLink(mutNode(edge.getKey())));
            }
        }
        try {
            Graphviz.fromGraph(graph).width(1500).height(800).render(Format.PNG).toFile(new File("/home/okkid-a/Documents/EDD/MiTravelMapGT/src/main/resources0"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Graphviz.fromGraph(graph).width(1500).height(800).render(Format.PNG).toImage();
    }

    public BufferedImage generarRutaDirected(Grafo grafo, Ruta ruta){
        MutableGraph graph = mutGraph("grafo").setDirected(true);
        HashMap<String, NodoGrafo> mapa =  grafo.getGrafo();
        int contador = 0;
        for (Map.Entry<String, NodoGrafo> entry : mapa.entrySet()) {
            dibujarRuta(ruta, graph, entry);
            for (Map.Entry<String, Edge> edge : entry.getValue().getWeightsVehiculo().entrySet()){
                contador = dibujarRutaDirEdges(ruta, graph, contador, entry, edge);
            }
        }
        return Graphviz.fromGraph(graph).width(1500).height(800).render(Format.PNG).toImage();
    }

    private int dibujarRutaEdges(Ruta ruta, MutableGraph graph, int contador, Map.Entry<String, NodoGrafo> entry, Map.Entry<String, Edge> edge) {
        if (contador< ruta.getRuta().size()-1 && compRutaEdges(ruta,entry.getKey(),edge.getKey())){
            graph.add(mutNode(entry.getKey()).addLink(to(mutNode(edge.getKey())).add(Color.RED)));
            contador++;
        }else {
            graph.add(mutNode(entry.getKey()).addLink(mutNode(edge.getKey())));
        }
        return contador;
    }

    private int dibujarRutaDirEdges(Ruta ruta, MutableGraph graph, int contador, Map.Entry<String, NodoGrafo> entry, Map.Entry<String, Edge> edge) {
        if (contador< ruta.getRuta().size()-1 && compDirRUtaEdges(ruta,entry.getKey(),edge.getKey())){
            graph.add(mutNode(entry.getKey()).addLink(to(mutNode(edge.getKey())).add(Color.RED)));
            contador++;
        }else {
            graph.add(mutNode(entry.getKey()).addLink(mutNode(edge.getKey())));
        }
        return contador;
    }

    private boolean compRutaEdges(Ruta ruta, String origen, String destino){
        boolean esRuta = false;
        for (int i = 0;i < ruta.getRuta().size()-1; i++){
            if ((ruta.getRuta().get(i).equals(origen) && ruta.getRuta().get(i+1).equals(destino) )||
                    (ruta.getRuta().get(i).equals(destino) && ruta.getRuta().get(i+1).equals(origen) )){
                esRuta = true;
                break;
            }
        }
        return esRuta;
    }

    private boolean compDirRUtaEdges(Ruta ruta, String origen, String destino){
        boolean esRuta = false;
        for (int i = 0;i < ruta.getRuta().size()-1; i++){
            if ((ruta.getRuta().get(i).equals(origen) && ruta.getRuta().get(i+1).equals(destino))){
                esRuta = true;
                break;
            }
        }
        return esRuta;
    }

    public BufferedImage generarRutaUndirected(Grafo grafo, Ruta ruta){
        MutableGraph graph = mutGraph("grafo").setDirected(false);
        HashMap<String, NodoGrafo> mapa =  grafo.getGrafo();
        int contador = 0;
        ArrayList<String> keysPassed = new ArrayList<>();
        for (Map.Entry<String, NodoGrafo> entry : mapa.entrySet()) {
            dibujarRuta(ruta, graph, entry);
            for (Map.Entry<String, Edge> edge : entry.getValue().getWeights().entrySet()){
                if (!keysPassed.contains(edge.getKey())) {
                    contador = dibujarRutaEdges(ruta, graph, contador, entry, edge);
                }
            }
            keysPassed.add(entry.getKey());
        }
        return Graphviz.fromGraph(graph).width(1500).height(800).render(Format.PNG).toImage();
    }

    private void dibujarRuta(Ruta ruta, MutableGraph graph, Map.Entry<String, NodoGrafo> entry) {
        if (ruta.getRuta().contains(entry.getKey())){
            if (ruta.getRuta().get(0).equals(entry.getKey())){
                graph.add(mutNode(entry.getKey()).add( Color.RED));
            } else if (ruta.getRuta().get(ruta.getRuta().size()-1).equals(entry.getKey())){
                graph.add(mutNode(entry.getKey()).add(Color.RED));
            } else {
                graph.add(mutNode(entry.getKey()).add(Color.RED));
            }
        } else {
            graph.add(mutNode(entry.getKey()));
        }
    }

    public BufferedImage generarArbol(Arbol arbol){
        if (arbol!=null) {
            PaginaArbol paginaArbol = arbol.getRaiz();
            HashMap<String, MutableNode> nodos = new HashMap<>();
            int niveles = dibujarPagina(nodos, paginaArbol, "0", 0);
            MutableGraph graph =
                    mutGraph("grafo").setDirected(true);
            for (int i = 0; i < niveles-1; i++) {
                if (i==0){
                    MutableNode node0 = nodos.get("0");
                    for (int j = 0; j < 5; j++) {
                        if (nodos.containsKey("0"+(j+1))){
                            graph.add(node0.addLink(between(port(String.valueOf(j+1)), nodos.get("0"+(j+1)).port("2",Compass.NORTH))));
                        }
                    }
                } else {
                    int cont = 5 ^ (i+1);
                    for (int j = 0; j < cont; j++) {
                        String nodeos = getNombreNodo(j+1,i+1);
                        if(nodos.containsKey("0"+nodeos)){
                            MutableNode node = nodos.get("0"+nodeos);
                            String nodeParent = nodeos.substring(0, nodeos.length() -1);
                            MutableNode mutNodePar = nodos.get("0"+nodeParent);
                            graph.add(mutNodePar.addLink(between(port(String.valueOf(buscarIndex(j+1))),
                                    node.port("2",Compass.NORTH))));
                        }
                    }
                }
            }
           /* MutableNode
                    mutnod0 = mutNode("nodo").add(Records.of(turn(rec("f0", ""))));

            graph.add(mutNode("a"));
            graph.add(mutNode("a").addLink(to(mutNode("b")).add(Style.DASHED)));
            graph.add(mutNode("b").addLink(between(port("f0"), mutnod0.port(Compass.NORTH))));*/
            return Graphviz.fromGraph(graph).width(1500).height(800).render(Format.PNG).toImage();
        }
        return null;
    }

    public int dibujarPagina(HashMap<String, MutableNode> nodos, PaginaArbol paginaArbol,String parent, int contador){

        NodoArbol[] keys = paginaArbol.getLlaves();
        MutableNode mutableNode = mutNode(parent).add(Records.of(turn(rec("1",keys[0]==null?"":keys[0].getValor()),
                        rec("2",keys[1]==null?"":keys[1].getValor())
        , rec("3",keys[2]==null?"":keys[2].getValor()), rec("4",keys[3]==null?"":keys[3].getValor()))));
        nodos.put(parent,mutableNode);
        int temp = contador;
        if (!paginaArbol.isHoja()) {
            for (int i = 0; i < paginaArbol.getChildrenUsed(); i++) {
              temp = dibujarPagina(nodos, paginaArbol.getHijos()[i], parent + (i + 1),contador);
            }
        }
        return temp+1;
    }

    public String getNombreNodo(int contador, int nivel){
        StringBuilder tituloNodo = new StringBuilder();
        for (int i = 0; i < nivel; i++) {
            tituloNodo.append("1");
        }
        String titulo = tituloNodo.toString();
        int temp = 1;
        for (int i = 1; i < contador; i++) {
            titulo= sumar5(titulo,i);
        }
        return titulo;
    }

    public String sumar5(String base, int contador){
        char pointed = base.charAt(base.length() - contador);
        if (pointed == '5'){
            base = base.substring(0, base.length()-contador) + '1' + base.substring(base.length()-contador+1);
            base = sumar5(base,contador+1);
        } else {
            int calc = pointed;
            calc++;
            base = base.substring(0, base.length()-contador) + calc + base.substring(base.length()-contador+1);
        }
        return base;
    }

    public int buscarIndex(int contador){
        if (contador%5 == 0){
            return 4;
        } else {
            return contador%5;
        }
    }
}
