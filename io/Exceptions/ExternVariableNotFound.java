
package io.Exceptions;

/**
 * Padre de excepciones de variables que no se encuentran en archivo de externs.
 * @author omar
 */
public class ExternVariableNotFound extends Exception{
    /**
     * @param c clase que no encontramos 
     * @param s Nombre de la variable que no encontramos
     */
    public ExternVariableNotFound(String c, String s){
        super("No se pudo encontrar la variable "+ s + " => " + c);
    }
}
