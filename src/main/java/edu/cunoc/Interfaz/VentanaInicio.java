package edu.cunoc.Interfaz;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaInicio extends JFrame{
    private JPanel globalPanel;
    private JButton seleccionarButton;
    private JButton seleccionarTraficoButton;
    private JLabel rutaTraLabel;
    private JLabel rutasLabel;
    private JButton aceptarButton;
    private JFrame frame;
    private String rutas, trafico;

    public VentanaInicio() {
        fixComponents();
        setButtons();
        rutas = "";
    }

    public void setButtons(){
        seleccionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.showSaveDialog(null);
                rutas = fileChooser.getSelectedFile().getAbsolutePath();
                rutasLabel.setText("Ruta: "+rutas);
                redundar().pack();
            }
        });
        seleccionarTraficoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.showSaveDialog(null);
                trafico = fileChooser.getSelectedFile().getAbsolutePath();
                rutaTraLabel.setText("Ruta: "+trafico);
                redundar().pack();
            }
        });
        aceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(rutas, trafico);
                frame.dispose();
            }
        });
    }

    public void fixComponents(){
        this.frame = new JFrame("Nuevo Proyecto");
        frame.setContentPane(globalPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public JFrame redundar(){
        return frame;
    }

}
