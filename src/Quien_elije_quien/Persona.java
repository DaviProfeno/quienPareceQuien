/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Quien_elije_quien;

/**
 *
 * @author DPL
 */
public class Persona {

    /**
     *
     * @param nombre
     * @param real
     * @param sexo
     * @param colorpelo
     * @param volar
     * @param universo_profesion
     */

    private String nombre;
    private Boolean real;
    private String sexo;
    private String colorpelo;
    private Boolean volar;
    private String universo_profesion;

    public Persona(String nombre, Boolean real, String sexo, String colorpelo, Boolean volar, String universo_profesion) {
        this.nombre = nombre;
        this.real = real;
        this.sexo = sexo;
        this.colorpelo = colorpelo;
        this.volar = volar;
        this.universo_profesion = universo_profesion;
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @return
     */
    public Boolean getReal() {
        return real;
    }

    /**
     *
     * @return
     */
    public String getSexo() {
        return sexo;
    }

    /**
     *
     * @return
     */
    public String getColorpelo() {
        return colorpelo;
    }

    /**
     *
     * @return
     */
    public Boolean getVolar() {
        return volar;
    }

    /**
     *
     * @return
     */
    public String getUniverso_profesion() {
        return universo_profesion;
    }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @param real
     */
    public void setReal(Boolean real) {
        this.real = real;
    }

    /**
     *
     * @param sexo
     */
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    /**
     *
     * @param colorpelo
     */
    public void setColorpelo(String colorpelo) {
        this.colorpelo = colorpelo;
    }

    /**
     *
     * @param volar
     */
    public void setVolar(Boolean volar) {
        this.volar = volar;
    }

    /**
     *
     * @param universo_profesion
     */
    public void setUniverso_profesion(String universo_profesion) {
        this.universo_profesion = universo_profesion;
    }

    /**
     *
     * @return
     */
    public String getInfo() {
        // Utiliza el método toString para devolver una cadena con toda la información del personaje
        return toString();
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        // Crea una cadena con toda la información del personaje
        return "Nombre: " + this.nombre + "\nReal: " + this.real + "\nSexo: " + this.sexo + "\nColor de pelo: " + this.colorpelo + "\nVuela: " + this.volar + "\nUniverso o profesión: " + this.universo_profesion;
    }

}
