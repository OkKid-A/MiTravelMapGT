package edu.cunoc.Interfaz;

import com.kitfox.svg.xml.StyleSheetRule;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPickRoute {
    private JPanel globalPanel;
    private JRadioButton vehiculoButton;
    private JRadioButton caminandoButton;
    private JComboBox<String> vehiculoBox;
    private JComboBox<String> caminandoBox;
    private JButton aceptarButton;
    private JFrame frame;
    private VentanaPrincipal ventanaPrincipal;

    public VentanaPickRoute(VentanaPrincipal ventanaPrincipal) {
        fixComponents();
        setButtons();
        this.ventanaPrincipal = ventanaPrincipal;
    }

    public void setButtons(){
        vehiculoButton.setSelected(true);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(vehiculoButton);
        buttonGroup.add(caminandoButton);
        caminandoBox.addItem("Menor Desgaste Fisico");
        caminandoBox.addItem("Menor Distancia");
        caminandoBox.addItem("Menor Distancia y Desgaste Fisico");
        caminandoBox.addItem("Ruta mas Rapida");
        caminandoBox.setSelectedIndex(0);
        vehiculoBox.addItem("Menor Gasolina Usada");
        vehiculoBox.addItem("Menor Distancia");
        vehiculoBox.addItem("Menor Distancia y Gasolina Usada");
        vehiculoBox.addItem("Ruta mas Rapida");
        vehiculoBox.setSelectedIndex(0);
        aceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean enCarro = false;
                String tipoRuta = null;
                if (vehiculoButton.isSelected()) {
                    enCarro =true;
                    tipoRuta = (String) vehiculoBox.getSelectedItem();
                } else {
                    tipoRuta = (String) caminandoBox.getSelectedItem();
                }
                ventanaPrincipal.dibujarRuta(enCarro,tipoRuta,frame);
            }
        });
    }

    public void fixComponents(){
        this.frame=  new JFrame("MiTravelMapGT");
        frame.setContentPane(globalPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public JFrame getFrame(){
        return frame;
    }
}
