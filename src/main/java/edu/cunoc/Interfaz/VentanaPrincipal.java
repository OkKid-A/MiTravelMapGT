package edu.cunoc.Interfaz;

import edu.cunoc.Estructuras.ArbolB.Arbol;
import edu.cunoc.Estructuras.Graph.Grafo;
import edu.cunoc.Estructuras.Graph.NodoGrafo;
import edu.cunoc.Estructuras.Graph.Ruta;
import edu.cunoc.Funcionalidades.Graficador;
import edu.cunoc.Funcionalidades.Reportador;
import edu.cunoc.Lector.LectorArchivo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;

public class VentanaPrincipal extends JFrame{
    private JPanel globalPanel;
    private JPanel funcPanel;
    private JPanel graphPanel;
    private JPanel archivosPanel;
    private JPanel funcioPanel;
    private JComboBox<String> origenBox;
    private JComboBox<String> destinoBox;
    private JButton iniciarGPSButton;
    private JComboBox recalcularBox;
    private JTextField mejorRutaField;
    private JTextField peorRutaField;
    private JButton cargarRutasButton;
    private JButton mostrarArbolButton;
    private JButton cargarTraficoButton;
    private JPanel relojPanel;
    private JLabel timeLabel;
    private JButton cambiarHoraButton;
    private JButton recalcularButton;
    private JPanel tiempoPanel;
    private JLabel iconLabel;
    private JPanel iconPanel;
    private JFrame frame;
    private Grafo grafo;
    private Reloj reloj;
    private Arbol rutas;
    private boolean escarro;

    public VentanaPrincipal(String rutas, String trafico) {
        fixComponents();
        fixButtons();
        setComboRutas(rutas,trafico);
        generarGrafo();

    }

    public void fixButtons(){
        iniciarGPSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VentanaPickRoute ventanaPickRoute = new VentanaPickRoute(getVentanaPrincipal());
            }
        });
        recalcularButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setGrafoConRuta(mejorRutaField, recalcularBox, escarro, recalcularBox);
            }
        });
        mostrarArbolButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Graficador graficador = new Graficador();
                iconLabel.setIcon(new ImageIcon(graficador.generarArbol(rutas)));
                graphPanel.repaint();
                graphPanel.revalidate();
                getFrame().pack();
            }
        });
    }

    private void setGrafoConRuta(JTextField mejorRutaField, JComboBox recalcularBox, boolean escarro, JComboBox recalcularBox2) {
        mejorRutaField.setText(rutas.buscar((String) recalcularBox.getSelectedItem(),rutas.getRaiz()).getValor());
        Graficador graficador = new Graficador();
        ImageIcon grafoDibujado;
        if (escarro){
            grafoDibujado = new ImageIcon(graficador.generarRutaDirected(grafo,new Ruta(rutas.buscar((String) recalcularBox2.getSelectedItem(),rutas.getRaiz()).getValorArray(),0)));
        } else {
            grafoDibujado = new ImageIcon(graficador.generarRutaUndirected(grafo,new Ruta(rutas.buscar((String) recalcularBox2.getSelectedItem(),rutas.getRaiz()).getValorArray(),0)));
        }
        iconLabel.setIcon(grafoDibujado);
        graphPanel.repaint();
        graphPanel.revalidate();
        getFrame().pack();
    }

    public void fixComponents(){
        this.frame=  new JFrame("MiTravelMapGT");
        frame.setContentPane(globalPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        reloj = new Reloj(relojPanel,timeLabel);
        graphPanel.repaint();
    }

    public void setComboRutas(String rutas, String trafico){
        LectorArchivo lectorArchivo = new LectorArchivo();
        grafo = new Grafo();
        try {
            grafo.setGrafo(lectorArchivo.parsearGrafo(rutas));
            grafo.setTrafico(lectorArchivo.parsearTrafico(Path.of(trafico)));
            for (Map.Entry<String, NodoGrafo> entry : grafo.getGrafo().entrySet()){
                this.origenBox.addItem(entry.getKey());
                this.destinoBox.addItem(entry.getKey());
            }
            this.origenBox.setSelectedIndex(0);
            frame.pack();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void generarGrafo(){
        Graficador graficador = new Graficador();
        ImageIcon grafoDibujado = new ImageIcon(graficador.generarGrafoDirected(grafo));
        iconLabel.setIcon(grafoDibujado);
        graphPanel.repaint();
        graphPanel.revalidate();
        getFrame().pack();
    }

    public void dibujarRuta(boolean escarro, String tipoRuta, JFrame pickFram){

        Reportador reportador = new Reportador(grafo);
        this.escarro = escarro;
        if (escarro){
            switch (tipoRuta){
                case"Menor Gasolina Usada":
                    rutas = reportador.armarMejoresGasolina((String) origenBox.getSelectedItem(), (String) destinoBox.getSelectedItem());
                    break;
                case "Menor Distancia":
                    rutas = reportador.armarMejorDistancia((String) origenBox.getSelectedItem(), (String) destinoBox.getSelectedItem(),true);
                    break;
                case "Menor Distancia y Gasolina Usada":
                    rutas = reportador.armarMejorDistanciaGasolina((String) origenBox.getSelectedItem(), (String) destinoBox.getSelectedItem());
                    break;
                case "Ruta mas Rapida":
                    rutas = reportador.armarMejoresRapidezVeh((String) origenBox.getSelectedItem(), (String) destinoBox.getSelectedItem(), reloj.getLocalTime());
            }
        } else {
            switch (tipoRuta){
                case"Menor Desgaste Fisico":
                    rutas = reportador.armarMejoresDesgasteFisico((String) origenBox.getSelectedItem(), (String) destinoBox.getSelectedItem());
                    break;
                case "Menor Distancia":
                    rutas = reportador.armarMejorDistancia((String) origenBox.getSelectedItem(), (String) destinoBox.getSelectedItem(),false);
                    break;
                case "Menor Distancia y Desgaste Fisico":
                    rutas = reportador.armarMejorDisFisico((String) origenBox.getSelectedItem(), (String) destinoBox.getSelectedItem());
                    break;
                case "Ruta mas Rapida":
                    rutas = reportador.armarMejoresRapidezCam((String) origenBox.getSelectedItem(), (String) destinoBox.getSelectedItem());
            }
        }
        updateRecalcular(rutas.buscar((String) origenBox.getSelectedItem(),rutas.getRaiz()).getValorArray());
        mejorRutaField.setText(rutas.buscar((String) origenBox.getSelectedItem(),rutas.getRaiz()).getValor());
        setGrafoConRuta(peorRutaField, destinoBox, escarro, origenBox);
        pickFram.dispose();
    }

    public void updateRecalcular(ArrayList<String> nodos){
        recalcularBox.removeAllItems();
        for (String nodo : nodos){
            recalcularBox.addItem(nodo);
        }
    }

    public JFrame getFrame(){
        return frame;
    }

    public VentanaPrincipal getVentanaPrincipal(){
        return this;
    }
}
