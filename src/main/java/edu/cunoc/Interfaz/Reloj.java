package edu.cunoc.Interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class Reloj{

    private JPanel jPanel;
    private int width;
    private JLabel tiempo;
    private LocalTime localTime;

    public Reloj() {
        this.jPanel = new JPanel();
        this.jPanel.setPreferredSize(new Dimension(100, 25));
        this.jPanel.setLayout(new BorderLayout());
        tiempo = new JLabel();
    }

    public Reloj(JPanel jPanel, JLabel label) {
        this.jPanel = jPanel;
        this.tiempo = label;
        correrTiempo(jPanel,label);
    }

    public void correrTiempo(JPanel panel, JLabel label){
        DateTimeFormatterBuilder dtf = new DateTimeFormatterBuilder();
        dtf.appendPattern("HH:mm:ss");
        Timer timer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText(LocalTime.now().format(dtf.toFormatter()));
                panel.repaint();
                panel.setVisible(true);
            }
        });
        timer.start();

    }

    public void correrTiempo(LocalTime time, JPanel panel, JLabel label){
        localTime = time;
        DateTimeFormatterBuilder dtf = new DateTimeFormatterBuilder();
        dtf.appendPattern("HH:mm:ss");
        Timer timer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText(localTime.format(dtf.toFormatter()));
                localTime = localTime.plusSeconds(1);
            }
        });
        timer.start();
    }

    public JLabel getTiempo() {
        return tiempo;
    }

    public LocalTime getLocalTime(){
        return LocalTime.parse(tiempo.getText());
    }

    public JPanel getjPanel() {
        return jPanel;
    }
}
