package edu.cunoc.Estructuras.Graph;

public class Trafico {

    private int horaInicio;
    private int horaFin;
    private int probTrafico;

    public Trafico(int horaInicio, int horaFin, int probTrafico) {
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.probTrafico = probTrafico;
    }

    public int getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(int horaInicio) {
        this.horaInicio = horaInicio;
    }

    public int getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(int horaFin) {
        this.horaFin = horaFin;
    }

    public int getProbTrafico() {
        return probTrafico;
    }

    public void setProbTrafico(int probTrafico) {
        this.probTrafico = probTrafico;
    }
}
