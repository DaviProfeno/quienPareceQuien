/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Quien_elije_quien;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

/**
 *
 * @author DPL
 */
public class Pantalla_principal extends javax.swing.JFrame {

    private final JFrame ventana;

    /**
     * Creates new form Pantalla_principal
     */
    public Pantalla_principal() {

        ventana = new JFrame("¿Quién parece quién?");
        Image imagenDeFondo = new ImageIcon("imagenes/Pantalla_principal.jpg").getImage();
        ImagenDeFondo panelConImagenDeFondo = new ImagenDeFondo(imagenDeFondo);

        // Agrega todos los componentes al panelConImagenDeFondo en lugar de agregarlos al JFrame directamente
        panelConImagenDeFondo.setLayout(new BorderLayout());

        //Panel donde están contenidos los botones de inicio y salida del juego
        JPanel inicio = new JPanel();
        inicio.setLayout(new BoxLayout(inicio, BoxLayout.X_AXIS));
        inicio.setBackground(Color.BLUE);

        //Botón de comienzo del juego
        JButton boton1 = new JButton("");
        boton1.setText("Comenzar el juego");
        boton1.setFont(new Font("Arial", Font.BOLD, 25));
        boton1.setPreferredSize(null);
        boton1.setBackground(Color.GREEN);
        boton1.setForeground(Color.BLACK);
        boton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Pantalla_juego pantallaJuego = new Pantalla_juego();
                    ventana.dispose();
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(Pantalla_principal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Pantalla_principal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(Pantalla_principal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        //Botón de Salida de juego
        JButton boton2 = new JButton("");
        boton2.setText("Salir del juego");
        boton2.setFont(new Font("Arial", Font.BOLD, 25));
        boton2.setPreferredSize(null);
        boton2.setBackground(Color.RED);
        boton2.setForeground(Color.BLACK);
        boton2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        //Panel para los botones de inicio y salida del juego
        inicio.add(Box.createHorizontalGlue());
        inicio.add(boton1, BorderLayout.WEST);
        inicio.add(Box.createHorizontalGlue());
        inicio.add(boton2, BorderLayout.EAST);
        inicio.add(Box.createHorizontalGlue());
        //añado el panel al panel principal
        panelConImagenDeFondo.add(inicio, BorderLayout.SOUTH);

        // Cargar la lista de puntuaciones altas desde el archivo
        ArrayList<Puntuacion> puntuacionesAltas = new ArrayList<>();
        try ( BufferedReader br = new BufferedReader(new FileReader("puntuaciones.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                String nombre = datos[0];
                int puntua = Integer.parseInt(datos[1]);
                puntuacionesAltas.add(new Puntuacion(nombre, puntua));
            }
        } catch (IOException e) {
            System.err.format("Error en la lectura del archivo de puntuaciones", e);
        }

        //Creación del panel derecho donde irán el resto de elementos
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.setPreferredSize(new Dimension(300, panelDerecho.getPreferredSize().height));
        panelDerecho.setBackground(Color.BLUE);

        // Agrega el JLabel con el título "Mejores puntuaciones" arriba del panel derecho
        JLabel tituloPuntuaciones = new JLabel("Mejores puntuaciones", SwingConstants.CENTER);
        tituloPuntuaciones.setFont(new Font("Arial", Font.BOLD, 25));
        tituloPuntuaciones.setOpaque(true);
        tituloPuntuaciones.setBackground(Color.RED);
        tituloPuntuaciones.repaint();
        tituloPuntuaciones.setForeground(Color.WHITE);
        DefaultListModel<Puntuacion> modeloLista = new DefaultListModel<>();
        for (Puntuacion puntuacion : puntuacionesAltas) {
            modeloLista.addElement(puntuacion);
        }

        //Texto superior a las puntuaciones
        JList<Puntuacion> listaPuntuaciones = new JList<>(modeloLista);
        JLabel textoSuperior = new JLabel("<html><h1>Bienvenido al juego<br> ¿Quién parece Quién?</h1><br><br>"
                + "Voy a elegir un personaje de manera aleatoria de los que estás viendo en pantalla y tendrás que adivinar  en quién<br>"
                + " estoy pensando haciéndome preguntas. Por adivinar el personaje conseguirás 100 puntos, "
                + "por realizar una pregunta restarás 10 puntos, y por fallar el personaje restarás 20 puntos.¿Estás listo para jugar? Presiona el botón de comenzar juego.</html> ");
        textoSuperior.setFont(new Font("Arial", Font.BOLD, 15));
        textoSuperior.setForeground(Color.WHITE);

        panelDerecho.add(textoSuperior, BorderLayout.NORTH);
        panelDerecho.add(Box.createVerticalGlue());
        panelDerecho.add(tituloPuntuaciones, BorderLayout.CENTER);
        panelDerecho.add(new JScrollPane(listaPuntuaciones), BorderLayout.SOUTH);
        listaPuntuaciones.setForeground(Color.WHITE);
        listaPuntuaciones.setBackground(Color.blue);

        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH); // Ventana maximizada
        ventana.pack();
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setVisible(true);
        // Agrega el panel derecho al lado derecho del BorderLayout
        ventana.getContentPane().add(panelConImagenDeFondo, BorderLayout.CENTER);
        ventana.getContentPane().add(panelDerecho, BorderLayout.EAST);

    }

    public Pantalla_principal(JFrame ventana) throws HeadlessException {
        this.ventana = ventana;
    }

    public void mostrar() {
        ventana.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jColorChooser1 = new javax.swing.JColorChooser();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 650, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JColorChooser jColorChooser1;
    // End of variables declaration//GEN-END:variables

}
