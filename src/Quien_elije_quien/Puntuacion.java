/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Quien_elije_quien;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author DPL
 */
public class Puntuacion implements Serializable {
 /**
*
 *@param nombreUsuario
* @param puntuacion
 */
    private String nombreUsuario = null;
    private int puntuacion = 0;
/**
     *
     * @param nombreUsuario
     * @param puntuacion
     */
    public Puntuacion(String nombreUsuario, int puntuacion) {
        this.nombreUsuario = nombreUsuario;
        this.puntuacion = puntuacion;
     }
/**
     *
     * @return
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }
/**
     *
     * @return
     */
    public int getPuntuacion() {
        return puntuacion;
    }
    /**
 * @brief Constructor de la clase Puntuacion
 *
 * Crea un objeto Puntuacion y carga las puntuaciones previamente guardadas
 * en el archivo con el nombre especificado. Si el archivo no existe, se crea.
 *@param archivo El nombre del archivo donde se guardarán las puntuaciones
 * @param puntuaciones ArrayList con las mejores puntuaciones
*/
    private File archivo;
    private List<Integer> puntuaciones;
    /**
     * @param nombreArchivo
     * @throws java.io.IOException
     */
    public Puntuacion (String nombreArchivo) throws IOException {
        archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            archivo.createNewFile();
        }
        puntuaciones = new ArrayList<>();
        cargarPuntuaciones();
    }
/**
 * @brief Agrega una nueva puntuación a la lista y actualiza el archivo
 *
 * Agrega la puntuación especificada a la lista de puntuaciones y la ordena de forma descendente.
 * Si la lista supera el límite de 10 puntuaciones, se elimina la puntuación más baja.
 * Finalmente, se guarda la lista actualizada en el archivo.
 *
 * @param puntuacion La puntuación a agregar
 */
    public void agregarPuntuacion(int puntuacion) {
        puntuaciones.add(puntuacion);
        Collections.sort(puntuaciones, Collections.reverseOrder());
        while (puntuaciones.size() > 10) {
            puntuaciones.remove(puntuaciones.size() - 1);
        }
        guardarPuntuaciones();
    }
/**
     *
     * @return
     */
    public List<Integer> getPuntuaciones() {
        return puntuaciones;
    }
    public String toString() {
    return nombreUsuario + ": " + puntuacion + " puntos";
}
/**
 * @brief Carga las puntuaciones guardadas en el archivo
 *
 * Lee las puntuaciones guardadas en el archivo y las agrega a la lista de puntuaciones.
 *
 * @throws java.io.IOException si ocurre un error al leer el archivo
 */
    private void cargarPuntuaciones() throws IOException {
        try (Scanner scanner = new Scanner(archivo)) {
            while (scanner.hasNextInt()) {
                puntuaciones.add(scanner.nextInt());
            }
        } catch (IOException e) {
            System.err.format("Error en la lectura del archivo puntuaciones", e);
        }
    }
/**
 * @brief Guarda las puntuaciones en el archivo
 *
 * Escribe las puntuaciones actuales en el archivo de puntuaciones. Cada puntuación se escribe
 * en una línea separada.
 *
 * * @throws java.io.IOException si ocurre un error al escribir en el archivo
 */
    private void guardarPuntuaciones() {
        try {
            try (FileWriter escritor = new FileWriter(archivo)) {
                for (int p : puntuaciones) {
                    escritor.write(p + "\n");
                }
            }
        } catch (IOException e) {
            System.err.format("Error en la escritura del archivo puntuaciones", e);
        }
    }
}