/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Quien_elije_quien;

import java.awt.BorderLayout;
import java.awt.Color;
import static java.awt.Color.blue;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.sound.sampled.*;

/**
 *
 * @author DPL
 */
public class Pantalla_juego extends javax.swing.JFrame {

    private JFrame frame;
    private JButton[] botonesPersonajes;
    private final JComboBox<String> preguntas;
    //puntuacion final
    int puntuacion = 0;
    //puntuacion conseguida por acertar el personaje
    int acertar = 0;
    //puntuacion que resta al no acertar el personaje
    int noAcertar = 0;
    //contador de preguntas multiplicado por 10 para restar la puntuacion de las preguntas utilizadas
    int numeroPreguntas = 0;
    //contador de preguntas utilizadas
    int contador = 0;

    public Pantalla_juego() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        frame = new JFrame("¿Quién parece quién?");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        //Cremos el archivo de música de juego
        File file = new File("musica/pantalla_juego.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        AudioFormat format = audioStream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        Clip clip = (Clip) AudioSystem.getLine(info);
        clip.open(audioStream);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-10.0f);
        clip.loop(Clip.LOOP_CONTINUOUSLY);

        // Crear la barra de herramientas
        JLabel titulo = new JLabel("¿Quién parece quién?");
        titulo.setForeground(Color.WHITE);
        titulo.setBackground(Color.RED);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setPreferredSize(new Dimension(titulo.getPreferredSize().width, 50));
        titulo.setOpaque(true);
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.RED);
        menuBar.add(titulo);
        frame.setJMenuBar(menuBar);

        //Array de personajes del fichero almacenado
        List<Persona> personas;
        personas = new ArrayList<>();
        try ( BufferedReader br = new BufferedReader(new FileReader("personajes.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String nombre = parts[0];
                boolean esFamoso = Boolean.parseBoolean(parts[1]);
                String genero = parts[2];
                String cabello = parts[3];
                boolean esSuperheroe = Boolean.parseBoolean(parts[4]);
                String profesion = parts[5];
                Persona p = new Persona(nombre, esFamoso, genero, cabello, esSuperheroe, profesion);
                personas.add(p);
            }
        } catch (IOException e) {
            System.err.format("Error en el archivo de personajes", e);
        }

        // Elige un elemento aleatorio del arraylist de personas
        Random rand = new Random();
        Persona target = personas.get(rand.nextInt(personas.size()));

        //Pedimos el nombre de usuario para almacenarlo
        String nombreUsuario = JOptionPane.showInputDialog(null, "Introduce tu nombre de usuario");

        // Leer las preguntas desde el archivo "preguntas.txt"
        ArrayList<String> preguntasList = new ArrayList<>();
        try ( BufferedReader br = new BufferedReader(new FileReader("preguntas.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                preguntasList.add(linea);
            }
        } catch (IOException e) {
            System.err.format("Error en el archivo de preguntas", e);
        }

        //Efecto de sonido para los botones de los personajes
        File file2 = new File("musica/efecto_personajes.wav");
        AudioInputStream audioStream2 = AudioSystem.getAudioInputStream(file2);
        AudioFormat format2 = audioStream2.getFormat();
        DataLine.Info info2 = new DataLine.Info(Clip.class, format2);
        Clip clip2 = (Clip) AudioSystem.getLine(info2);
        clip2.open(audioStream2);

        //Creo los botones de las fotografías y sus eventos
        JButton boton1 = new JButton("");
        boton1.setPreferredSize(new Dimension(50, 25));
        boton1.setLayout(new Layout_Botones());
        boton1.setOpaque(false);
        boton1.setBorderPainted(false);
        // Crea un JLabel con el texto del botón y lo agrega al botón
        JLabel label1 = new JLabel("Batman");
        label1.setForeground(Color.RED);
        label1.setFont(new Font("Arial", Font.BOLD, 24));
        boton1.add(label1);
        // Cargar la imagen
        ImageIcon icono1 = new ImageIcon("imagenes/Batman.jpg");
        // Añadir la imagen al botón
        boton1.setIcon(icono1);
        boton1.addActionListener((ActionEvent e) -> {
            clip2.setFramePosition(0);
            clip2.start();
            boton1.setIcon(new ImageIcon("imagenes/Batman_eliminado.jpg"));
            boton1.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        boton1.setIcon(new ImageIcon("imagenes/Batman.jpg"));
                    }
                }
            });
        });

        JButton boton2 = new JButton("");
        boton2.setPreferredSize(new Dimension(50, 50));
        boton2.setLayout(new Layout_Botones());
        boton2.setOpaque(false);
        boton2.setBorderPainted(false);
        // Crea un JLabel con el texto del botón y lo agrega al botón
        JLabel label2 = new JLabel("Oliver");
        label2.setForeground(Color.RED);
        label2.setFont(new Font("Arial", Font.BOLD, 24));
        boton2.add(label2);
        // Cargar la imagen
        ImageIcon icono2 = new ImageIcon("imagenes/Oliver.jpg");
        // Añadir la imagen al botón
        boton2.setIcon(icono2);
        boton2.addActionListener((ActionEvent e) -> {
            clip2.setFramePosition(0);
            clip2.start();
            boton2.setIcon(new ImageIcon("imagenes/Oliver_eliminado.jpg"));

            boton2.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        boton2.setIcon(new ImageIcon("imagenes/Oliver.jpg"));
                    }
                }
            });
        });
        JButton boton3 = new JButton("");
        boton3.setPreferredSize(new Dimension(50, 50));
        boton3.setLayout(new Layout_Botones());
        boton3.setOpaque(false);
        boton3.setBorderPainted(false);
        // Crea un JLabel con el texto del botón y lo agrega al botón
        JLabel label3 = new JLabel("Sakura");
        label3.setForeground(Color.RED);
        label3.setFont(new Font("Arial", Font.BOLD, 24));
        boton3.add(label3);
        //Carga la imagen
        ImageIcon icono3 = new ImageIcon("imagenes/Sakura.png");
        // Añadir la imagen al botón
        boton3.setIcon(icono3);
        boton3.addActionListener((ActionEvent e) -> {
            clip2.setFramePosition(0);
            clip2.start();
            boton3.setIcon(new ImageIcon("imagenes/Sakura_eliminado.jpg"));

            boton3.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        boton3.setIcon(new ImageIcon("imagenes/Sakura.png"));
                    }
                }
            });
        });
        JButton boton4 = new JButton("");
        boton4.setPreferredSize(new Dimension(50, 50));
        boton4.setLayout(new Layout_Botones());
        boton4.setOpaque(false);
        boton4.setBorderPainted(false);
        // Crea un JLabel con el texto del botón y lo agrega al botón
        JLabel label4 = new JLabel("Supergirl");
        label4.setForeground(Color.RED);
        label4.setFont(new Font("Arial", Font.BOLD, 24));
        boton4.add(label4);
        ImageIcon icono4 = new ImageIcon("imagenes/Supergirl.jpg");
        // Añadir la imagen al botón
        boton4.setIcon(icono4);
        boton4.addActionListener((ActionEvent e) -> {
            clip2.setFramePosition(0);
            clip2.start();
            boton4.setIcon(new ImageIcon("imagenes/Supergirl_eliminado.jpg"));
            boton4.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        boton4.setIcon(new ImageIcon("imagenes/Supergirl.jpg"));
                    }
                }
            });
        });
        JButton boton5 = new JButton("");
        boton5.setPreferredSize(new Dimension(50, 50));
        boton5.setLayout(new Layout_Botones());
        boton5.setOpaque(false);
        boton5.setBorderPainted(false);
        // Crea un JLabel con el texto del botón y lo agrega al botón
        JLabel label5 = new JLabel("Goku");
        label5.setForeground(Color.RED);
        label5.setFont(new Font("Arial", Font.BOLD, 24));
        boton5.add(label5);
        ImageIcon icono5 = new ImageIcon("imagenes/Goku.jpg");
        // Añadir la imagen al botón
        boton5.setIcon(icono5);
        boton5.addActionListener((ActionEvent e) -> {
            clip2.setFramePosition(0);
            clip2.start();
            boton5.setIcon(new ImageIcon("imagenes/Goku_eliminado.jpg"));
            boton5.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        boton5.setIcon(new ImageIcon("imagenes/Goku.jpg"));
                    }
                }
            });
        });
        JButton boton6 = new JButton("");
        boton6.setPreferredSize(new Dimension(50, 50));
        boton6.setLayout(new Layout_Botones());
        boton6.setOpaque(false);
        boton6.setBorderPainted(false);
        // Crea un JLabel con el texto del botón y lo agrega al botón
        JLabel label6 = new JLabel("Darth Vader");
        label6.setForeground(Color.RED);
        label6.setFont(new Font("Arial", Font.BOLD, 24));
        boton6.add(label6);
        ImageIcon icono6 = new ImageIcon("imagenes/Darth_Vader.jpg");
        // Añadir la imagen al botón
        boton6.setIcon(icono6);
        boton6.addActionListener((ActionEvent e) -> {
            clip2.setFramePosition(0);
            clip2.start();
            boton6.setIcon(new ImageIcon("imagenes/Darth_Vader_eliminado.jpg"));
            boton6.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        boton6.setIcon(new ImageIcon("imagenes/Darth_Vader.jpg"));
                    }
                }
            });
        });
        JButton boton7 = new JButton("");
        boton7.setPreferredSize(new Dimension(50, 50));
        boton7.setLayout(new Layout_Botones());
        boton7.setOpaque(false);
        boton7.setBorderPainted(false);
        // Crea un JLabel con el texto del botón y lo agrega al botón
        JLabel label7 = new JLabel("Coco Chanel");
        label7.setForeground(Color.RED);
        label7.setFont(new Font("Arial", Font.BOLD, 24));
        boton7.add(label7);
        ImageIcon icono7 = new ImageIcon("imagenes/Coco_Chanel.jpg");
        // Añadir la imagen al botón
        boton7.setIcon(icono7);
        boton7.addActionListener((ActionEvent e) -> {
            clip2.setFramePosition(0);
            clip2.start();
            boton7.setIcon(new ImageIcon("imagenes/Coco_Chanel_eliminado.jpg"));
            boton7.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        boton7.setIcon(new ImageIcon("imagenes/Coco_Chanel.jpg"));
                    }
                }
            });
        });
        JButton boton8 = new JButton("");
        boton8.setPreferredSize(new Dimension(100, 50));
        boton8.setLayout(new Layout_Botones());
        boton8.setOpaque(false);
        boton8.setBorderPainted(false);
        // Crea un JLabel con el texto del botón y lo agrega al botón
        JLabel label8 = new JLabel("Rosalía");
        label8.setForeground(Color.RED);
        label8.setFont(new Font("Arial", Font.BOLD, 24));
        boton8.add(label8);
        ImageIcon icono8 = new ImageIcon("imagenes/Rosalia.jpg");
        // Añadir la imagen al botón
        boton8.setIcon(icono8);
        boton8.addActionListener((ActionEvent e) -> {
            clip2.setFramePosition(0);
            clip2.start();
            boton8.setIcon(new ImageIcon("imagenes/Rosalia_eliminado.jpg"));
            boton8.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        boton8.setIcon(new ImageIcon("imagenes/Rosalia.jpg"));
                    }
                }
            });
        });
        JButton boton9 = new JButton("");
        boton9.setPreferredSize(new Dimension(50, 50));
        boton9.setLayout(new Layout_Botones());
        boton9.setOpaque(false);
        boton9.setBorderPainted(false);
        // Crea un JLabel con el texto del botón y lo agrega al botón
        JLabel label9 = new JLabel("Eminem");
        label9.setForeground(Color.RED);
        label9.setFont(new Font("Arial", Font.BOLD, 24));
        boton9.add(label9);
        ImageIcon icono9 = new ImageIcon("imagenes/Eminem.jpg");
        // Añadir la imagen al botón
        boton9.setIcon(icono9);
        boton9.addActionListener((ActionEvent e) -> {
            clip2.setFramePosition(0);
            clip2.start();
            boton9.setIcon(new ImageIcon("imagenes/Eminem_eliminado.jpg"));
            boton9.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        boton9.setIcon(new ImageIcon("imagenes/Eminem.jpg"));
                    }
                }
            });
        });
        JButton boton10 = new JButton("");
        boton10.setPreferredSize(new Dimension(50, 50));
        boton10.setLayout(new Layout_Botones());
        boton10.setOpaque(false);
        boton10.setBorderPainted(false);
        // Crea un JLabel con el texto del botón y lo agrega al botón
        JLabel label10 = new JLabel("Frida Khalo");
        label10.setForeground(Color.RED);
        label10.setFont(new Font("Arial", Font.BOLD, 24));
        boton10.add(label10);
        ImageIcon icono10 = new ImageIcon("imagenes/Frida_Khalo.jpg");
        // Añadir la imagen al botón
        boton10.setIcon(icono10);
        boton10.addActionListener((ActionEvent e) -> {
            clip2.setFramePosition(0);
            clip2.start();
            boton10.setIcon(new ImageIcon("imagenes/Frida_Khalo_eliminado.jpg"));
            boton10.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        boton10.setIcon(new ImageIcon("imagenes/Frida_Khalo.jpg"));
                    }
                }
            });
        });
        JButton boton11 = new JButton("");
        boton11.setPreferredSize(new Dimension(50, 50));
        boton11.setLayout(new Layout_Botones());
        boton11.setOpaque(false);
        boton11.setBorderPainted(false);
        // Crea un JLabel con el texto del botón y lo agrega al botón
        JLabel label11 = new JLabel("Leo Messi");
        label11.setForeground(Color.RED);
        label11.setFont(new Font("Arial", Font.BOLD, 24));
        boton11.add(label11);
        ImageIcon icono11 = new ImageIcon("imagenes/Messi.jpg");
        // Añadir la imagen al botón
        boton11.setIcon(icono11);
        boton11.addActionListener((ActionEvent e) -> {
            clip2.setFramePosition(0);
            clip2.start();
            boton11.setIcon(new ImageIcon("imagenes/Messi_eliminado.jpg"));
            boton11.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        boton11.setIcon(new ImageIcon("imagenes/Messi.jpg"));
                    }
                }
            });
        });
        JButton boton12 = new JButton("");
        boton12.setPreferredSize(new Dimension(50, 50));
        boton12.setLayout(new Layout_Botones());
        boton12.setOpaque(false);
        boton12.setBorderPainted(false);
        // Crea un JLabel con el texto del botón y lo agrega al botón
        JLabel label12 = new JLabel("Michael Jordan");
        label12.setForeground(Color.RED);
        label12.setFont(new Font("Arial", Font.BOLD, 24));
        boton12.add(label12);
        ImageIcon icono12 = new ImageIcon("imagenes/Michael_Jordan.jpg");
        // Añadir la imagen al botón
        boton12.setIcon(icono12);
        boton12.addActionListener((ActionEvent e) -> {
            clip2.setFramePosition(0);
            clip2.start();
            boton12.setIcon(new ImageIcon("imagenes/Michael_Jordan_eliminado.jpg"));
            boton12.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        boton12.setIcon(new ImageIcon("imagenes/Michael_Jordan.jpg"));
                    }
                }
            });
        });
        botonesPersonajes = new JButton[]{boton1, boton2, boton3, boton4, boton5, boton6, boton7, boton8, boton9, boton10, boton11, boton12};
        //Panel donde están distribuídos los botones y su layout
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(0, 3, 10, 10)); // 3 columnas, espacio horizontal y vertical de 10 píxeles
        for (JButton button : botonesPersonajes) {
            button.setPreferredSize(new Dimension(50, 50)); // Establecer tamaño
            panelBotones.add(button);
        }
        //Añadimos los botones a otro panel para añadir un borde alrededor 
        JPanel panel2 = new JPanel(new BorderLayout());
        panel2.setBackground(blue);
        panel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Añadir un margen alrededor del panel
        panel2.add(panelBotones, BorderLayout.CENTER);

        // Creamos el panel lateral 
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(blue);
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        // Label donde la máquina responderá
        JLabel respuesta = new JLabel("");
        respuesta.setForeground(Color.WHITE);
        respuesta.setFont(new Font("Arial", Font.BOLD, 25));
        respuesta.setPreferredSize(null);

        //boton para realizar respuesta
        JButton boton13 = new JButton("Haz tu pregunta");
        boton13.setFont(new Font("Arial", Font.BOLD, 45));
        boton13.setPreferredSize(null);
        boton13.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtiene el índice de la pregunta seleccionada
                int indicePreguntaSeleccionada = preguntas.getSelectedIndex();
                // Obtiene la pregunta seleccionada
                String preguntaSeleccionada = preguntas.getItemAt(indicePreguntaSeleccionada);

                if ((indicePreguntaSeleccionada < 9) && (preguntaSeleccionada.contains("1") && target.getReal())) {
                    respuesta.setText("Sí, es real.");
                    contador += 1;
                } else if ((indicePreguntaSeleccionada < 9) && (preguntaSeleccionada.contains("2") && target.getSexo().equalsIgnoreCase("hombre"))) {
                    respuesta.setText("Sí, el personaje es hombre");
                    contador += 1;
                } else if ((indicePreguntaSeleccionada < 9) && (preguntaSeleccionada.contains("3") && target.getSexo().equalsIgnoreCase("mujer"))) {
                    respuesta.setText("Sí, el personaje es mujer");
                    contador += 1;
                } else if ((indicePreguntaSeleccionada < 9) && (preguntaSeleccionada.contains("4") && target.getColorpelo().equalsIgnoreCase("castaño"))) {
                    respuesta.setText("Sí, el personaje tiene el pelo castaño.");
                    contador += 1;
                } else if ((indicePreguntaSeleccionada < 9) && (preguntaSeleccionada.contains("5") && target.getColorpelo().equalsIgnoreCase("rubio"))) {
                    respuesta.setText("Sí, el personaje tiene el pelo rubio.");
                    contador += 1;
                } else if ((indicePreguntaSeleccionada < 9) && (preguntaSeleccionada.contains("6") && target.getColorpelo().equalsIgnoreCase("negro"))) {
                    respuesta.setText("Sí, el personaje tiene el pelo negro.");
                    contador += 1;
                } else if ((indicePreguntaSeleccionada < 9) && (preguntaSeleccionada.contains("7") && target.getColorpelo().equalsIgnoreCase("rosa"))) {
                    respuesta.setText("Sí, el personaje tiene el pelo rosa.");
                    contador += 1;
                } else if ((indicePreguntaSeleccionada < 9) && (preguntaSeleccionada.contains("8") && target.getColorpelo().equalsIgnoreCase("calvo"))) {
                    respuesta.setText("Sí, el personaje es calvo.");
                    contador += 1;
                } else if ((indicePreguntaSeleccionada < 9) && (preguntaSeleccionada.contains("9") && target.getVolar())) {
                    respuesta.setText("Sí, el personaje puede volar.");
                    contador += 1;
                } else if ((indicePreguntaSeleccionada > 9) && (preguntaSeleccionada.contains("10") && target.getUniverso_profesion().equalsIgnoreCase("Dragon Ball"))) {
                    respuesta.setText("Sí, el personaje pertenece al universo Dragon Ball.");
                    contador += 1;
                } else if ((indicePreguntaSeleccionada > 9) && (preguntaSeleccionada.contains("11") && target.getUniverso_profesion().equalsIgnoreCase("Star Wars"))) {
                    respuesta.setText("Sí, el personaje pertenece al universo Star Wars.");
                    contador += 1;
                } else if ((indicePreguntaSeleccionada > 9) && (preguntaSeleccionada.contains("12") && target.getUniverso_profesion().equalsIgnoreCase("Naruto"))) {
                    respuesta.setText("Sí, el personaje pertenece al universo Naruto.");
                    contador += 1;
                } else if ((indicePreguntaSeleccionada > 9) && (preguntaSeleccionada.contains("13") && target.getUniverso_profesion().equalsIgnoreCase("Oliver y Benji"))) {
                    respuesta.setText("Sí, el personaje pertenece al universo de Oliver y Benji.");
                    contador += 1;
                } else if ((indicePreguntaSeleccionada > 9) && (preguntaSeleccionada.contains("14") && target.getUniverso_profesion().equalsIgnoreCase("DC"))) {
                    respuesta.setText("Sí, el personaje pertenece al universo DC.");
                    contador += 1;
                } else if ((indicePreguntaSeleccionada > 9) && (preguntaSeleccionada.contains("15") && target.getUniverso_profesion().equalsIgnoreCase("cantante"))) {
                    respuesta.setText("Sí, el personaje es cantante.");
                    contador += 1;
                } else if ((indicePreguntaSeleccionada > 9) && (preguntaSeleccionada.contains("16") && target.getUniverso_profesion().equalsIgnoreCase("Jugador de fútbol"))) {
                    respuesta.setText("Sí, el personaje es jugador de fútbol.");
                    contador += 1;
                } else if ((indicePreguntaSeleccionada > 9) && (preguntaSeleccionada.contains("17") && target.getUniverso_profesion().equalsIgnoreCase("Jugador de baloncesto"))) {
                    respuesta.setText("Sí, el personaje es jugador de baloncesto.");
                    contador += 1;
                } else if ((indicePreguntaSeleccionada > 9) && (preguntaSeleccionada.contains("18") && target.getUniverso_profesion().equalsIgnoreCase("Pintora"))) {
                    respuesta.setText("Sí, el personaje es pintora.");
                    contador += 1;
                } else if ((indicePreguntaSeleccionada > 9) && (preguntaSeleccionada.contains("19") && target.getUniverso_profesion().equalsIgnoreCase("Diseñadora de moda"))) {
                    respuesta.setText("Sí, el personaje es diseñadora de moda.");
                    contador += 1;
                } else {
                    // Si se llega hasta aquí, significa que la pregunta no se ajusta a ninguna de las características de las personas
                    // por lo que la respuesta es "no"
                    respuesta.setText("No, continúa jugando.");
                    contador += 1;
                }
                numeroPreguntas = contador * 10;
            }
        }
        );

        // Crea el panel que contiene el botón de hacer la pregunta
        JPanel panelBoton13 = new JPanel();
        BoxLayout layout2 = new BoxLayout(panelBoton13, BoxLayout.Y_AXIS);
        panelBoton13.setLayout(layout2);
        panelBoton13.setPreferredSize(null);
        panelBoton13.setBackground(Color.WHITE);
        panelBoton13.add(boton13);

        //El usuario introduce el nombre del personaje elegido
        JTextField cuadroTexto = new JTextField("");
        cuadroTexto.setFont(new Font("Arial", Font.PLAIN, 25));
        cuadroTexto.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        //música de aplausos
        File file3 = new File("musica/aplausos.wav");
        AudioInputStream audioStream3 = AudioSystem.getAudioInputStream(file3);
        AudioFormat format3 = audioStream3.getFormat();
        DataLine.Info info3 = new DataLine.Info(Clip.class, format3);
        Clip clip3 = (Clip) AudioSystem.getLine(info3);
        clip3.open(audioStream3);
        FloatControl gainControl3 = (FloatControl) clip3.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl3.setValue(-5.0f);

        //música de fallo de personaje
        File file4 = new File("musica/fallo_personaje.wav");
        AudioInputStream audioStream4 = AudioSystem.getAudioInputStream(file4);
        AudioFormat format4 = audioStream4.getFormat();
        DataLine.Info info4 = new DataLine.Info(Clip.class, format4);
        Clip clip4 = (Clip) AudioSystem.getLine(info4);
        clip4.open(audioStream4);
        FloatControl gainControl2 = (FloatControl) clip4.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl2.setValue(+6.0f);
        //Botón para resolver el personaje
        JButton boton14 = new JButton("Resolver");
        boton14.setFont(new Font("Arial", Font.BOLD, 45));
        boton14.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textoUsuario = cuadroTexto.getText();
                //Si aciertas te saca por pantalla dos mensajes de que has ganado y tu puntuación y después vuelve a la pantalla de inicio
                if (textoUsuario.equalsIgnoreCase(target.getNombre())) {
                    //Cremos el archivo de música
                    clip3.setFramePosition(0);
                    clip3.start();
                    JOptionPane.showMessageDialog(null, " ¡Enhorabuena " + nombreUsuario + ", acertaste! ");
                    acertar = 100;
                    puntuacion = acertar - noAcertar - numeroPreguntas;
                    JOptionPane.showMessageDialog(null, " ¡Tu puntuación ha sido de: " + puntuacion + " !");
                    /**
                     * @throws java.io.IOException
                     */

                    // Crear una nueva puntuación con sus atributos
                    Puntuacion nuevaPuntuacion = new Puntuacion(nombreUsuario, puntuacion);
                    /**
                     * @throws java.io.IOException
                     */
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
                    } catch (IOException e3) {
                        System.err.format("Error en la lectura del archivo de puntuaciones", e3);
                    }

                    // Agregamos la nueva puntuación a la lista de puntuaciones altas
                    puntuacionesAltas.add(nuevaPuntuacion);

                    // Ordenar la lista de puntuaciones altas por orden descendente de puntuación
                    Collections.sort(puntuacionesAltas,
                            (p1, p2) -> p2.getPuntuacion() - p1.getPuntuacion());
                    /**
                     *
                     * @param max_puntuaciones_altas
                     */
                    //Se crea una variable para determinar el número máximo de puntuaciones que  vamos a almacenar
                    int max_puntuaciones_altas = 10;

                    // Si la lista de puntuaciones altas tiene más elementos de los permitidos, eliminar el último elemento (el de menor puntuación)
                    if (puntuacionesAltas.size()
                            > max_puntuaciones_altas) {
                        puntuacionesAltas.remove(puntuacionesAltas.size() - 1);
                    }
                    /**
                     * @throws java.io.IOException
                     */
                    // Escribir la lista de puntuaciones altas en el archivo
                    try ( FileWriter writer = new FileWriter("puntuaciones.txt", false)) {
                        for (Puntuacion pun : puntuacionesAltas) {
                            writer.write(pun.getNombreUsuario() + "," + pun.getPuntuacion() + "\n");
                        }
                    } catch (IOException e2) {
                        System.err.format("Error en la escritura del archivo puntuaciones", e2);
                    }
                    clip.stop();
                    Pantalla_principal pantallaPrincipal = new Pantalla_principal();
                    frame.dispose();

                    //Si fallas te aparece otro mensaje y te resta 20 puntos
                } else {
                    clip4.setFramePosition(0);
                    clip4.start();
                    JOptionPane.showMessageDialog(null, " Fallaste, continua jugando");
                    noAcertar = 20;
                }
            }

        });

        //Panel donde se incluye el botón resolver y el nombre del personaje adivinado
        JPanel panelBoton14 = new JPanel();
        BoxLayout layout3 = new BoxLayout(panelBoton14, BoxLayout.Y_AXIS);
        panelBoton14.setLayout(layout3);
        panelBoton14.setPreferredSize(null);
        panelBoton14.setBackground(Color.BLUE);
        panelBoton14.add(boton14);

        //Cuadro de texto explicando el funcionamiento de las preguntas
        JLabel funcionamientoPregunta = new JLabel("<html><p>Aquí tienes todas las preguntas que me puedes hacer para</p>"
                + "<p>que adivines mi personaje, selecciona una y te responderé.</p> <p>Haz click en los personajes para tacharlos.</p></html>");
        funcionamientoPregunta.setFont(new Font("Arial", Font.BOLD, 25));
        funcionamientoPregunta.setForeground(Color.WHITE);
        funcionamientoPregunta.setHorizontalAlignment(JLabel.LEFT);
        funcionamientoPregunta.setPreferredSize(null);

        // Crear un array de tipo String con las preguntas del ArrayList
        String[] preguntasArray = new String[preguntasList.size()];
        preguntasArray = preguntasList.toArray(preguntasArray);
        preguntas = new JComboBox<>(preguntasArray);
        preguntas.setRenderer(
                new CustomComboBoxRenderer());
        preguntas.setPreferredSize(
                new Dimension(preguntas.getPreferredSize().width, 50));

        // Creamos un panel para las preguntas
        JPanel preguntasPanel = new JPanel();
        BoxLayout layout = new BoxLayout(preguntasPanel, BoxLayout.Y_AXIS);
        preguntasPanel.setLayout(layout);
        preguntasPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        preguntasPanel.add(preguntas);

        //Creo un texto para que inserte el nombre en el que está pensando la máquina
        JLabel texto = new JLabel("Si sabes en quien estoy pensando escríbelo aquí debajo.");
        texto.setFont(new Font("Arial", Font.BOLD, 25));
        texto.setForeground(Color.WHITE);
        texto.setHorizontalAlignment(JLabel.LEFT);
        texto.setPreferredSize(null);

        //Panel donde están contenidos los botones de vuelta a la pantalla de inicio y salida del juego
        JPanel inicio = new JPanel();
        inicio.setLayout(new BoxLayout(inicio, BoxLayout.X_AXIS));
        inicio.setBackground(Color.BLUE);

        //botón para controlar la música
        JButton boton22 = new JButton("");
        boton22.setOpaque(false);
        boton22.setBorderPainted(false);
        // Crea un JLabel con el texto del botón y lo agrega al botón
        JLabel label22 = new JLabel(" ");
        label22.setForeground(Color.RED);
        label22.setBackground(blue);
        label22.setFont(new Font("Arial", Font.BOLD, 24));
        boton22.add(label22);
        // Cargar el icono de sonido activado
        ImageIcon iconoSonidoActivado = new ImageIcon("imagenes/sound_off.jpg");
        // Cargar el icono de sonido desactivado
        ImageIcon iconoSonidoDesactivado = new ImageIcon("imagenes/sound_on.jpg");
        // Establecer el icono de sonido activado como icono del botón
        boton22.setIcon(iconoSonidoActivado);
        // Agregar un ActionListener al botón
        boton22.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clip.isRunning()) {
                    // Si la música se está reproduciendo, detenerla y establecer el icono de sonido desactivado
                    clip.stop();
                    boton22.setIcon(iconoSonidoDesactivado);
                } else {
                    // Si la música no se está reproduciendo, reanudar la reproducción y establecer el icono de sonido activado
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                    boton22.setIcon(iconoSonidoActivado);
                }
            }
        });
        //Botón de comienzo del juego
        JButton boton19 = new JButton("");
        boton19.setText("Volver a la pantalla de inicio");
        boton19.setFont(new Font("Arial", Font.BOLD, 25));
        boton19.setPreferredSize(null);
        boton19.setBackground(Color.GREEN);
        boton19.setForeground(Color.BLACK);
        boton19.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clip.stop();
                Pantalla_principal pantallaPrincipal = new Pantalla_principal();
                frame.dispose();
            }
        });

        //Botón para salir del juego en cualquier momento
        JButton boton20 = new JButton("");
        boton20.setText("Salir del juego");
        boton20.setFont(new Font("Arial", Font.BOLD, 25));
        boton20.setPreferredSize(null);
        boton20.setBackground(Color.RED);
        boton20.setForeground(Color.BLACK);
        boton20.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        //Añado los botones de entrada y salida del juego a un panel
        inicio.add(Box.createHorizontalGlue());
        inicio.add(boton22, BorderLayout.WEST);
        inicio.add(Box.createHorizontalGlue());
        inicio.add(boton19, BorderLayout.WEST);
        inicio.add(boton20, BorderLayout.EAST);
        inicio.add(Box.createHorizontalGlue());

        // Agregamos todos los componentes  al panel lateral
        menuPanel.add(funcionamientoPregunta);
        menuPanel.add(preguntasPanel);
        menuPanel.add(panelBoton13);
        menuPanel.add(respuesta);
        menuPanel.add(Box.createVerticalGlue());
        menuPanel.add(texto);
        menuPanel.add(cuadroTexto, BorderLayout.CENTER);
        menuPanel.add(panelBoton14);
        menuPanel.add(Box.createVerticalGlue());
        menuPanel.add(inicio);
        //Posiciono los elementos en el menuPanel
        preguntasPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelBoton13.setAlignmentX(Component.LEFT_ALIGNMENT);
        respuesta.setAlignmentX(Component.LEFT_ALIGNMENT);
        texto.setAlignmentX(Component.LEFT_ALIGNMENT);
        cuadroTexto.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelBoton14.setAlignmentX(Component.LEFT_ALIGNMENT);
        inicio.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Agregamos el panel lateral al contenedor principal
        frame.add(panel2, BorderLayout.CENTER);
        frame.getContentPane()
                .add(menuPanel, BorderLayout.EAST);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(
                true);
    }

    public void mostrar() {
        frame.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 347, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    private void getSelectedIndex() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
