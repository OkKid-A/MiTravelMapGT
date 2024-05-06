package edu.cunoc.Funcionalidades;

import edu.cunoc.Estructuras.ArbolB.Arbol;
import edu.cunoc.Estructuras.ArbolB.NodoArbol;
import edu.cunoc.Estructuras.ArbolB.PaginaArbol;
import edu.cunoc.Estructuras.Graph.Grafo;
import edu.cunoc.Estructuras.Graph.Ruta;
import edu.cunoc.Estructuras.Graph.Trafico;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

public class Reportador {

    private Grafo grafo;

    public Reportador(Grafo grafo) {
        this.grafo = grafo;
    }

    public Arbol armarMejoresRapidezVeh(String origen, String destino, LocalTime horaActual){
        Arbol arbol = new Arbol();
        ArrayList<Ruta> calculadas = mejoresRapVehiculo(origen, destino, horaActual);
        Ruta optima = calculadas.get(calculadas.size()-1);
        ArrayList<Ruta> optimas = new ArrayList<>();
        optimas.add(optima);
        LocalTime hora = horaActual;
        for (int i = 1; i < optima.getRuta().size()-1; i++){
            hora.plusMinutes(grafo.getGrafo().get(optima.getRuta().get(i-1)).getWeightsVehiculo().get(optima.getRuta().get(i)).getTiempoPromVehiculo());
            ArrayList<Ruta> rutas = mejoresRapVehiculo(optima.getRuta().get(i), destino,hora);
            optimas.add(rutas.get(rutas.size()-1));
        }
        for (Ruta ruta : optimas){
            arbol.insertar(new NodoArbol(ruta.getRuta().get(0),ruta));
        }
        arbol.insertar(new NodoArbol(destino,calculadas.get(0)));
        return arbol;
    }

    public ArrayList<Ruta> mejoresRapVehiculo(String origen, String destino, LocalTime horaActual){
        ArrayList<ArrayList<String>> rutas = grafo.encontrarRutasVehiculos(origen, destino);
        ArrayList<Ruta> rapidez = new ArrayList<>();
        LocalTime hora;
        for (ArrayList<String> ruta : rutas) {
            hora = horaActual;
            float distancia = 0;
            float tiempo = 0;
            for (int i = 0; i < ruta.size()-1; i++){
                float trafico = 0;
                if (grafo.getTrafico().containsKey(ruta.get(i))&&grafo.getTrafico().get(ruta.get(i)).containsKey(ruta.get(i+1))){
                    ArrayList<Trafico> traficoOB = grafo.getTrafico().get(ruta.get(i)).get(ruta.get(i+1));
                    for (Trafico t : traficoOB){
                        if (hora.isAfter(LocalTime.of(t.getHoraInicio(), 0)) && hora.isBefore(LocalTime.of(t.getHoraFin(), 0))) {
                            trafico = (float) t.getProbTrafico() /100;
                        }
                    }
                }
                distancia += grafo.getGrafo().get(ruta.get(i)).getWeightsVehiculo().get(ruta.get(i+1)).getDistancia();
                        tiempo += (grafo.getGrafo().get(ruta.get(i)).getWeightsVehiculo().get(ruta.get(i+1)).getTiempoPromVehiculo()
                        * (1 + trafico));
                hora = hora.plusMinutes(grafo.getGrafo().get(ruta.get(i)).getWeightsVehiculo().get(ruta.get(i+1)).getTiempoPromVehiculo());
            }
            distancia = (distancia/tiempo);
            rapidez.add(new Ruta(ruta, distancia));
        }
        Collections.sort(rapidez);
        return rapidez;
    }

    public Arbol armarMejoresRapidezCam(String origen, String destino){
        Arbol arbol = new Arbol();
        ArrayList<Ruta> calculadas = mejoresRapidezCam(origen, destino);
        Ruta optima = calculadas.get(calculadas.size()-1);
        ArrayList<Ruta> optimas = new ArrayList<>();
        optimas.add(optima);
        for (int i = 1; i < optima.getRuta().size()-1; i++){
            ArrayList<Ruta> rutas = mejoresRapidezCam(optima.getRuta().get(i), destino);
            optimas.add(rutas.get(rutas.size()-1));
        }
        for (Ruta ruta : optimas){
            arbol.insertar(new NodoArbol(ruta.getRuta().get(0),ruta));
        }
        arbol.insertar(new NodoArbol(destino,calculadas.get(0)));
        return arbol;
    }

    public ArrayList<Ruta> mejoresRapidezCam(String origen, String destino){
        ArrayList<ArrayList<String>> rutas = grafo.encontrarRutas(origen, destino);
        ArrayList<Ruta> rapidez = new ArrayList<>();
        for (ArrayList<String> ruta : rutas) {
            float distancia = 0;
            int tiempo = 0;
            for (int i = 0; i < ruta.size()-1; i++){
                distancia += grafo.getGrafo().get(ruta.get(i)).getWeights().get(ruta.get(i+1)).getDistancia();
                        tiempo += grafo.getGrafo().get(ruta.get(i)).getWeights().get(ruta.get(i+1)).getTiempoPromCaminando();
            }
            distancia = (distancia/tiempo);
            rapidez.add(new Ruta(ruta, distancia));
        }
        Collections.sort(rapidez);
        return rapidez;
    }

    public ArrayList<Ruta> mejoresGasolina(String origen, String destino){
        ArrayList<ArrayList<String>> rutas = grafo.encontrarRutasVehiculos(origen, destino);
        ArrayList<Ruta> gasolina = new ArrayList<>();
        for (ArrayList<String> ruta : rutas) {
            int distancia = 0;
            for (int i = 0; i < ruta.size()-1; i++){
                distancia += grafo.getGrafo().get(ruta.get(i)).getWeightsVehiculo().get(ruta.get(i+1)).getGastoCombustible();
            }
            gasolina.add(new Ruta(ruta, distancia));
        }
        Collections.sort(gasolina);
        return gasolina;
    }

    public Arbol armarMejoresGasolina(String origen, String destino){
        Arbol arbol = new Arbol();
        ArrayList<Ruta> calculadas = mejoresGasolina(origen, destino);
        Ruta optima = calculadas.get(0);
        ArrayList<Ruta> optimas = new ArrayList<>();
        optimas.add(optima);
        for (int i = 1; i < optima.getRuta().size()-1; i++){
            optimas.add(mejoresGasolina(optima.getRuta().get(i), destino).get(0));
        }
        for (Ruta ruta : optimas){
            arbol.insertar(new NodoArbol(ruta.getRuta().get(0),ruta));
        }
        arbol.insertar(new NodoArbol(destino,calculadas.get(calculadas.size()-1)));
        return arbol;
    }

    public Arbol armarMejoresDesgasteFisico(String origen, String destino){
        Arbol arbol = new Arbol();
        ArrayList<Ruta> calculadas = mejoresDesgasteFisico(origen, destino);
        Ruta optima = calculadas.get(0);
        ArrayList<Ruta> optimas = new ArrayList<>();
        optimas.add(optima);
        for (int i = 1; i < optima.getRuta().size()-1; i++){
            optimas.add(mejoresDesgasteFisico(optima.getRuta().get(i), destino).get(0));
        }
        for (Ruta ruta : optimas){
            arbol.insertar(new NodoArbol(ruta.getRuta().get(0),ruta));
        }
        arbol.insertar(new NodoArbol(destino,calculadas.get(calculadas.size()-1)));
        return arbol;
    }

    public ArrayList<Ruta> mejoresDesgasteFisico(String origen, String destino){
        ArrayList<ArrayList<String>> rutas = grafo.encontrarRutas(origen, destino);
        ArrayList<Ruta> desgaste = new ArrayList<>();
        for (ArrayList<String> ruta : rutas) {
            int distancia = 0;
            for (int i = 0; i < ruta.size()-1; i++){
                distancia += grafo.getGrafo().get(ruta.get(i)).getWeights().get(ruta.get(i+1)).getGastoFisico();
            }
            desgaste.add(new Ruta(ruta, distancia));
        }
        Collections.sort(desgaste);
        return desgaste;
    }

    public Arbol armarMejorDistanciaGasolina(String origen, String destino){
        Arbol arbol = new Arbol();
        ArrayList<Ruta> calculadas = mejoresDistanciaGasolina(origen, destino);
        Ruta optima = calculadas.get(0);
        ArrayList<Ruta> optimas = new ArrayList<>();
        optimas.add(optima);
        for (int i = 1; i < optima.getRuta().size()-1; i++){
            optimas.add(mejoresDistanciaGasolina(optima.getRuta().get(i), destino).get(0));
        }
        for (Ruta ruta : optimas){
            arbol.insertar(new NodoArbol(ruta.getRuta().get(0),ruta));
        }
        arbol.insertar(new NodoArbol(destino,calculadas.get(calculadas.size()-1)));
        return arbol;
    }

    public ArrayList<Ruta> mejoresDistanciaGasolina(String origen, String destino){
        ArrayList<ArrayList<String>> rutas = grafo.encontrarRutasVehiculos(origen, destino);
        ArrayList<Ruta> disGas = new ArrayList<>();
        for (ArrayList<String> ruta : rutas) {
            float distancia = 0;
            int gasolina = 0;
            for (int i = 0; i < ruta.size()-1; i++){
                distancia += grafo.getGrafo().get(ruta.get(i)).getWeightsVehiculo().get(ruta.get(i+1)).getGastoCombustible();
                gasolina += grafo.getGrafo().get(ruta.get(i)).getWeightsVehiculo().get(ruta.get(i+1)).getDistancia();
            }
            distancia = distancia / gasolina;
            disGas.add(new Ruta(ruta, distancia));
        }
        Collections.sort(disGas);
        return disGas;
    }

    public Arbol armarMejorDisFisico(String origen, String destino){
        Arbol arbol = new Arbol();
        ArrayList<Ruta> calculadas = mejoresDisFisico(origen, destino);
        Ruta optima = calculadas.get(0);
        ArrayList<Ruta> optimas = new ArrayList<>();
        optimas.add(optima);
        for (int i = 1; i < optima.getRuta().size()-1; i++){
            optimas.add(mejoresDisFisico(optima.getRuta().get(i), destino).get(0));
        }
        for (Ruta ruta : optimas){
            arbol.insertar(new NodoArbol(ruta.getRuta().get(0),ruta));
        }
        arbol.insertar(new NodoArbol(destino,calculadas.get(calculadas.size()-1)));
        return arbol;
    }

    public ArrayList<Ruta> mejoresDisFisico(String origen, String destino){
        ArrayList<ArrayList<String>> rutas = grafo.encontrarRutas(origen, destino);
        ArrayList<Ruta> disGas = new ArrayList<>();
        for (ArrayList<String> ruta : rutas) {
            float distancia = 0;
            int fisico = 0;
            for (int i = 0; i < ruta.size()-1; i++){
                distancia += grafo.getGrafo().get(ruta.get(i)).getWeights().get(ruta.get(i+1)).getGastoFisico();
                fisico += grafo.getGrafo().get(ruta.get(i)).getWeights().get(ruta.get(i+1)).getDistancia();
            }
            distancia = distancia / fisico;
            disGas.add(new Ruta(ruta, distancia));
        }
        Collections.sort(disGas);
        return disGas;
    }

    public Arbol armarMejorDistancia(String origen, String destino, boolean enCarro) throws NullPointerException{
        Arbol arbol = new Arbol();
        if (enCarro){
            ArrayList<Ruta> calculadas = grafo.calcularDistanciasAuto(origen, destino);
            Collections.sort(calculadas);
            Ruta optima = calculadas.get(0);
            ArrayList<Ruta> optimas = new ArrayList<>();
            optimas.add(optima);
            arbol.insertar(new NodoArbol(destino, calculadas.get(calculadas.size()-1)));
            for (int i = 1; i < optima.getRuta().size()-1; i++){
                calculadas = grafo.calcularDistanciasAuto(optima.getRuta().get(i), destino);
                Collections.sort(calculadas);
                optimas.add(calculadas.get(0));
            }
            for (Ruta ruta : optimas){
                arbol.insertar(new NodoArbol(ruta.getRuta().get(0),ruta));
            }
        }else {
            ArrayList<Ruta> calculadas = grafo.calcularDistancias(origen, destino);
            Collections.sort(calculadas);
            Ruta optima = calculadas.get(0);
            ArrayList<Ruta> optimas = new ArrayList<>();
            optimas.add(optima);
            arbol.insertar(new NodoArbol(destino, calculadas.get(calculadas.size()-1)));
            for (int i = 1; i < optima.getRuta().size()-1; i++){
                calculadas = grafo.calcularDistancias(optima.getRuta().get(i), destino);
                Collections.sort(calculadas);
                optimas.add(calculadas.get(0));
            }
            for (Ruta ruta : optimas){
                arbol.insertar(new NodoArbol(ruta.getRuta().get(0),ruta));
            }
        }

        return arbol;
    }


}
