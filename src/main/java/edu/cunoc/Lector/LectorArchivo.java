package edu.cunoc.Lector;

import edu.cunoc.Estructuras.Graph.NodoGrafo;
import edu.cunoc.Estructuras.Graph.Trafico;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class LectorArchivo {

    private Formateador formateador;

    public LectorArchivo() {
        this.formateador = new Formateador();
    }

    public HashMap<String, NodoGrafo> parsearGrafo(String path) throws IOException {
        File archivo = new File(path);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(archivo));
        String linea;
        HashMap<String, NodoGrafo> grafo = new HashMap<>();
        while ((linea = bufferedReader.readLine())!=null&& !linea.isEmpty()){
            formateador.formatearEdge(linea,grafo);
        }
        return grafo;
    }

    public HashMap<String, HashMap<String, ArrayList<Trafico> >> parsearTrafico(Path path) throws IOException {
        File archivo = new File(path.toString());
        BufferedReader bufferedReader = new BufferedReader(new FileReader(archivo));
        String linea;
        HashMap<String, HashMap<String, ArrayList<Trafico>>> trafico = new HashMap<>();
        while ((linea = bufferedReader.readLine())!=null){
            formateador.formatearTrafico(linea,trafico);
        }
        return trafico;
    }



}
