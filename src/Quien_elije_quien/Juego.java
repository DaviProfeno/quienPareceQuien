/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Quien_elije_quien;

/**
 *
 * @author DPL
 */
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Juego {
    private HashMap<String, HashMap<String, Boolean>> personajes;

    public Juego() {
        personajes = new HashMap<>();

        try {
            Scanner scanner = new Scanner(new File("personajes.txt"));
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] partes = linea.split(";");
                String nombrePersonaje = partes[0];
                HashMap<String, Boolean> caracteristicas = new HashMap<>();
                for (int i = 1; i < partes.length; i++) {
                    String[] partesCaracteristica = partes[i].split(":");
                    String nombreCaracteristica = partesCaracteristica[0];
                    Boolean valorCaracteristica = Boolean.valueOf(partesCaracteristica[1]);
                    caracteristicas.put(nombreCaracteristica, valorCaracteristica);
                }
                personajes.put(nombrePersonaje, caracteristicas);
            }
        } catch (FileNotFoundException e) {
            System.err.println("No se pudo abrir el archivo personajes.txt.");
        }
        
    }
    
public String hacerPregunta(String pregunta) {
        String respuesta = "";

        // Comprobar si la pregunta se aplica a algún personaje
        for (String nombrePersonaje : personajes.keySet()) {
            HashMap<String, Boolean> caracteristicas = personajes.get(nombrePersonaje);
            Boolean valorCaracteristica = caracteristicas.get(pregunta);
            if (valorCaracteristica != null) {
                // La pregunta se aplica a este personaje
                if (valorCaracteristica) {
                    respuesta += nombrePersonaje + " tiene " + pregunta + ". ";
                } else {
                    respuesta += nombrePersonaje + " no tiene " + pregunta + ". ";
                }
            }
        }

        if (respuesta.equals("")) {
            // No se aplica a ningún personaje
            respuesta = "No sé.";
        }

        return respuesta;
    }
}
 

