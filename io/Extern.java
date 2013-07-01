
package io;


import io.Exceptions.ExternVariableNotFound;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Maneja variables de archivo .set, este es un archivo que Metatrader usa para
 * introducir valores a variables marcadas como extern.
 * @author omar
 */
public class Extern {
    Properties properties;
    /**
     * @param file Direccion del archivo .set
     */
    public Extern(String file){
        this.properties = new Properties();
        try {
            this.properties.load(new FileInputStream(file));
        } catch (IOException ex) {
            Logger.getLogger(Extern.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Intenta obtener un Double del archivo de configuración .set.
     * @param n Nombre del Double
     * @return
     * @throws ExternVariableNotFound Si no es ncontrado.
     */
    public Double getDouble(String n) throws ExternVariableNotFound{
        String s = this.getProperty(n);
        if (!s.equals("!")) {
            return new Double(s);
        } else {
            throw new ExternVariableNotFound(new Double(0.0).getClass().getName(), n);
        }
    }
    
    /**
     * Intenta obtener un String del archivo de configuración .set.
     * @param n Nombre de la String
     * @return
     * @throws ExternVariableNotFound Si no es ncontrado.
     */
    public String getString(String n) throws ExternVariableNotFound{
        String s = this.getProperty(n);
        if (!s.equals("!")) {
            return s;
        } else {
            throw new ExternVariableNotFound(s.getClass().getName(), n);
        }
    }
    /**
     * Intenta obtener un Integer del archivo de configuración .set.
     * @param n Nombre del Entero.
     * @return
     * @throws ExternVariableNotFound Si no es ncontrado.
     */
    public Integer getInteger(String n) throws ExternVariableNotFound{
        String s = this.getProperty(n);
        if (!s.equals("!")) {
            return new Integer(s);
        } else {
            throw new ExternVariableNotFound(new Integer(0).getClass().getName(), n);
        }
    }
    /**
     * Intenta obtener un Boolean del archivo de configuración .set.
     * @param n Nombre del boolean.
     * @return
     * @throws ExternVariableNotFound Si no es ncontrado.
     */
    public Boolean getBoolean(String n) throws ExternVariableNotFound{
        String s = this.getProperty(n);
        if (!s.equals("!")) {
            return Boolean.getBoolean(s);
        } else {
            throw new ExternVariableNotFound(Boolean.TRUE.getClass().getName(),n);
        }
    }
    /**
     * Intenta obtener un char del archivo de configuración .set.
     * @param n Nombre del Char.
     * @return
     * @throws ExternVariableNotFound Si no es ncontrado.
     */
    public Character getChar(String n) throws ExternVariableNotFound{
        String s = this.getProperty(n);
        if (!s.equals("!")) {
            //Obtenemos el primer char, por que en teoría es solo un character
            return new Character(s.charAt(0));
        } else {
            throw new ExternVariableNotFound(new Character('0').getClass().getName(),n);
        }
    }
    /**
     * Obtiene un String apartir del nombre de una variable, si no la encuentra
     * devuelve !
     * @param s
     * @return 
     */
    private String getProperty(String s){
        return this.properties.getProperty(s, "!");
    }
}
