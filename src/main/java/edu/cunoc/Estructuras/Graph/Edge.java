package edu.cunoc.Estructuras.Graph;

public class Edge {

    private String nodoDest;
    private int tiempoPromVehiculo;
    private int tiempoPromCaminando;
    private int gastoCombustible;
    private int gastoFisico;
    private int distancia;

    public Edge() {
    }

    public Edge(String nodoDest, int tiempoPromVehiculo, int tiempoPromCaminando, int gastoCombustible, int gastoFisico, int distancia) {
        this.nodoDest = nodoDest;
        this.tiempoPromVehiculo = tiempoPromVehiculo;
        this.tiempoPromCaminando = tiempoPromCaminando;
        this.gastoCombustible = gastoCombustible;
        this.gastoFisico = gastoFisico;
        this.distancia = distancia;
    }


    public int getTiempoPromVehiculo() {
        return tiempoPromVehiculo;
    }

    public void setTiempoPromVehiculo(int tiempoPromVehiculo) {
        this.tiempoPromVehiculo = tiempoPromVehiculo;
    }

    public int getTiempoPromCaminando() {
        return tiempoPromCaminando;
    }

    public void setTiempoPromCaminando(int tiempoPromCaminando) {
        this.tiempoPromCaminando = tiempoPromCaminando;
    }

    public int getGastoCombustible() {
        return gastoCombustible;
    }

    public void setGastoCombustible(int gastoCombustible) {
        this.gastoCombustible = gastoCombustible;
    }

    public int getGastoFisico() {
        return gastoFisico;
    }

    public void setGastoFisico(int gastoFisico) {
        this.gastoFisico = gastoFisico;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }
}
