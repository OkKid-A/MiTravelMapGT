package edu.cunoc.Estructuras.ArbolB;

public class PaginaArbol {

    public static final int ORDEN = 5;

    private NodoArbol[] llaves;
    private PaginaArbol[] hijos;
    private int keysUsed;
    private int childrenUsed;
    private boolean hoja;
    private PaginaArbol padre;

    public PaginaArbol() {
        llaves = new NodoArbol[ORDEN];
        hijos = new PaginaArbol[ORDEN+1];
        keysUsed = 0;
        childrenUsed = 0;
        hoja = false;
        padre = null;
    }

    public PaginaArbol getPadre() {
        return padre;
    }

    public void setPadre(PaginaArbol padre) {
        this.padre = padre;
    }

    public boolean isHoja() {
        return hoja;
    }

    public void setHoja(boolean hoja) {
        this.hoja = hoja;
    }

    public int getChildrenUsed() {
        return childrenUsed;
    }

    public void setChildrenUsed(int childrenUsed) {
        this.childrenUsed = childrenUsed;
    }

    public NodoArbol[] getLlaves() {
        return llaves;
    }

    public void setLlaves(NodoArbol[] llaves) {
        this.llaves = llaves;
    }

    public void insertarLlaveSinOrden(NodoArbol llave) {
        llaves[keysUsed] = llave;
        keysUsed++;
    }

    public void insertarLlaveEnIndex(NodoArbol llave, int index) {
        llaves[index] = llave;

    }

    public PaginaArbol[] getHijos() {
        return hijos;
    }

    public void setHijos(PaginaArbol[] hijos) {
        this.hijos = hijos;
    }

    public void insertarHijoSinOrden(PaginaArbol hijo) {
        hijos[childrenUsed] = hijo;
        childrenUsed++;
    }

    public void insertarHijoEnIndex(PaginaArbol hijo, int index) {
        hijos[index] = hijo;
        childrenUsed++;
    }

    public int getKeysUsed() {
        return keysUsed;
    }

    public void setKeysUsed(int keysUsed) {
        this.keysUsed = keysUsed;
    }
}
