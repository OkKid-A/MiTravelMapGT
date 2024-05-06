package edu.cunoc.Estructuras.ArbolB;

import edu.cunoc.Estructuras.Graph.Ruta;

import java.util.ArrayList;

public class NodoArbol {

    private String llave;
    private Ruta valor;

    public NodoArbol() {
    }

    public NodoArbol(String llave, Ruta valor) {
        this.llave = llave;
        this.valor = valor;
    }

    public String getLlave() {
        return llave;
    }

    public void setLlave(String llave) {
        this.llave = llave;
    }

    public String getValor() {
        if (valor.getRuta().isEmpty()){
            return "";
        } else {
            String res = "";
            for (int i = 0; i < valor.getRuta().size()-1; i++) {
                res += valor.getRuta().get(i) + " => ";
            }
            res += valor.getRuta().get(valor.getRuta().size()-1);
            res += "\nResultado = " + valor.getDistancia();
            return res;
        }
    }

    public ArrayList<String> getValorArray(){
        return valor.getRuta();
    }

    public void setValor(ArrayList<String> valor) {
        this.valor.setRuta(valor);
    }
}
