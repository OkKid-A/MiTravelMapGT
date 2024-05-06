package edu.cunoc.Estructuras.ArbolB;

public class Arbol {

    private PaginaArbol raiz;

    public Arbol() {
        raiz = new PaginaArbol();
        raiz.setHoja(false);
    }

    public NodoArbol buscar(String key, PaginaArbol buscando){
        boolean encontrado = false;
        NodoArbol nodo = null;
        if (buscando != null){
            while (!encontrado){
                for (int i = 0; i < buscando.getKeysUsed()+1; i++) {
                    if (i == buscando.getKeysUsed()){
                        nodo = null;
                        encontrado = true;
                    break;}
                    else if(buscando.getLlaves()[i].getLlave().equals(key)){
                        nodo = buscando.getLlaves()[i];
                        encontrado = true;
                        break;
                    } else if (buscando.getLlaves()[i].getLlave().compareToIgnoreCase(key) > 0&&!buscando.isHoja()){
                        nodo = buscar(key,buscando.getHijos()[i]);
                        encontrado = true;
                        break;
                    } else if (i == buscando.getKeysUsed()-1&&!buscando.isHoja()) {
                        nodo = buscar(key,buscando.getHijos()[i+1]);
                        encontrado = true;
                        break;
                    }
                }
            }
        }
        return nodo;
    }

    public void insertar(NodoArbol nodoArbol) {
        if (raiz.getKeysUsed() == 4 && raiz.getChildrenUsed()==0){
            dividirRaiz(nodoArbol);
        } else if (raiz.getKeysUsed() <4&& raiz.getChildrenUsed()==0){
            int i = insertarEnPaginaIncompleta(nodoArbol,raiz);
            raiz.setKeysUsed(raiz.getKeysUsed()+1);
        } else {
            int i = encontrarIndice(nodoArbol,raiz);
            if (raiz.getHijos()[i]==null){
                PaginaArbol paginaArbol = new PaginaArbol();
                paginaArbol.setPadre(raiz);
                paginaArbol.setHoja(true);
                raiz.insertarHijoEnIndex(paginaArbol,i);
                insertarEnPaginaIncompleta(nodoArbol,paginaArbol);
            } else {
                insertarEnPagina(nodoArbol,raiz.getHijos()[i]);
            }
        }
    }

    public int insertarEnPaginaIncompleta(NodoArbol nodoArbol, PaginaArbol paginaArbol) {
        int i = paginaArbol.getKeysUsed()-1;
        while (i>=0 && nodoArbol.getLlave().compareToIgnoreCase(paginaArbol.getLlaves()[i].getLlave()) < 0){
            paginaArbol.insertarLlaveEnIndex(paginaArbol.getLlaves()[i],i+1);
            i--;
        }
        paginaArbol.insertarLlaveEnIndex(nodoArbol,i+1);
        return i+1;
    }

    public int encontrarIndice(NodoArbol nodoArbol, PaginaArbol paginaArbol) {
        int i = paginaArbol.getKeysUsed()-1;
        while (i>=0 && nodoArbol.getLlave().compareToIgnoreCase(raiz.getLlaves()[i].getLlave()) < 0){
            i--;
        }
        return i+1;
    }

    public void insertarEnPagina(NodoArbol nodoArbol, PaginaArbol paginaArbol) {
        if (paginaArbol.isHoja()){
            if (paginaArbol.getKeysUsed()==4){
                dividirHoja(paginaArbol,nodoArbol);
            } else{
                insertarEnPaginaIncompleta(nodoArbol,paginaArbol);
                paginaArbol.setKeysUsed(paginaArbol.getKeysUsed()+1);
            }
        }
    }

    public void insertarEnPadre(PaginaArbol paginaArbol, NodoArbol insertado, PaginaArbol hijoInsertado){
        PaginaArbol padre = paginaArbol.getPadre();
        repartirHijosEnPadre(insertado, hijoInsertado, padre);
        if (padre.getKeysUsed()==5){
            PaginaArbol der = new PaginaArbol();
            der.setHoja(padre.isHoja());
            der.setPadre(padre.getPadre());
            NodoArbol ascendido = padre.getLlaves()[2];
            divisorNodo(padre,der,padre);
            escalarCambios(padre,ascendido,der);
        }
    }

    public void dividirHoja(PaginaArbol paginaArbol, NodoArbol nodoArbol) {
       insertarEnPaginaIncompleta(nodoArbol,paginaArbol);
       PaginaArbol izq  = paginaArbol;
       PaginaArbol der = new PaginaArbol();
       der.setHoja(izq.isHoja());
       der.setPadre(izq.getPadre());
       NodoArbol ascendido = izq.getLlaves()[2];
       divisorNodo(izq,der,paginaArbol);
       escalarCambios(paginaArbol,ascendido,der);
    }

    public void escalarCambios(PaginaArbol paginaArbol, NodoArbol ascendido, PaginaArbol der){
        if (paginaArbol.getPadre().getPadre()==null){
            insertarEnRaiz(ascendido,der);
        } else {
            insertarEnPadre(paginaArbol,ascendido,der);
        }
    }

    private void insertarEnRaiz( NodoArbol insertado, PaginaArbol hijoInsertado) {
        repartirHijosEnPadre(insertado, hijoInsertado, raiz);
        if (raiz.getKeysUsed()==5){
            PaginaArbol nuevaRaiz = new PaginaArbol();
            nuevaRaiz.insertarLlaveSinOrden(raiz.getLlaves()[2]);
            PaginaArbol der = new PaginaArbol();
            der.setHoja(false);
            der.setPadre(nuevaRaiz);
            raiz.setPadre(nuevaRaiz);
            divisorNodo(raiz,der,raiz);
            nuevaRaiz.insertarHijoSinOrden(raiz);
            nuevaRaiz.insertarHijoSinOrden(der);
            raiz = nuevaRaiz;
        }
    }

    private void repartirHijosEnPadre(NodoArbol insertado, PaginaArbol hijoInsertado, PaginaArbol raiz) {
        int i = insertarEnPaginaIncompleta(insertado, raiz);
        raiz.setKeysUsed(raiz.getKeysUsed()+1);
        int hijos = raiz.getChildrenUsed();
        int k = hijos;
        if (i==0){
            for (int j = hijos-1; j > 0;j--){
                raiz.insertarHijoEnIndex(raiz.getHijos()[i],i+1);
            }
            raiz.insertarHijoEnIndex(raiz.getHijos()[1],0);
            raiz.insertarHijoEnIndex(hijoInsertado,1);
        } else {
            for (int j = hijos-1; j > i;j--){
                raiz.insertarHijoEnIndex(raiz.getHijos()[i],i+1);
                k--;
            }
            raiz.insertarHijoEnIndex(hijoInsertado,k);
        }
    }

    public void divisorNodo(PaginaArbol izq, PaginaArbol der, PaginaArbol paginaArbol){
        der.insertarLlaveSinOrden(paginaArbol.getLlaves()[3]);
        der.insertarLlaveSinOrden(paginaArbol.getLlaves()[4]);
        izq.insertarLlaveEnIndex(null,2);
        izq.insertarLlaveEnIndex(null,3);
        izq.insertarLlaveEnIndex(null,4);
        izq.setKeysUsed(3);
        if (!izq.isHoja()){
            copiarHijos(izq,der);
            izq.setChildrenUsed(3);
        }
    }

    public void dividirRaiz(NodoArbol nodoArbol){
        insertarEnPaginaIncompleta(nodoArbol,raiz);
        PaginaArbol nuevaRaiz = new PaginaArbol();
        nuevaRaiz.insertarLlaveSinOrden(raiz.getLlaves()[2]);
        PaginaArbol hijoIzquierdo = new PaginaArbol();
        hijoIzquierdo.setHoja(true);
        hijoIzquierdo.insertarLlaveSinOrden(raiz.getLlaves()[0]);
        hijoIzquierdo.insertarLlaveSinOrden(raiz.getLlaves()[1]);
        PaginaArbol hijoDerecho = new PaginaArbol();
        hijoDerecho.setHoja(true);
        hijoDerecho.insertarLlaveSinOrden(raiz.getLlaves()[3]);
        hijoDerecho.insertarLlaveSinOrden(raiz.getLlaves()[4]);
        nuevaRaiz.insertarHijoSinOrden(hijoIzquierdo);
        nuevaRaiz.insertarHijoSinOrden(hijoDerecho);
        hijoDerecho.setPadre(nuevaRaiz);
        hijoIzquierdo.setPadre(nuevaRaiz);
        raiz = nuevaRaiz;
    }

    public void copiarHijos(PaginaArbol origen, PaginaArbol destino){
        if (!origen.isHoja()){
            for (int j = 0; j < 3;j++){
                destino.insertarHijoEnIndex(origen.getHijos()[j+3],j);
                destino.getHijos()[j].setPadre(destino);
                origen.insertarHijoEnIndex(null,j);
            }
        }
    }

    public PaginaArbol getRaiz() {
        return raiz;
    }

    public void setRaiz(PaginaArbol raiz) {
        this.raiz = raiz;
    }
}
