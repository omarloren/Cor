
package io;

import java.util.ArrayList;
import java.util.Properties;

/**
 * Manejo de opciones para la aplicación, a esta clase se le debe de entregar 
 * un archivo JSON, con las opciones generales del sistema.
 * @author omar
 */
public class Inputs {
    private Properties inputs;
    private String[] currencies;
    
    public Inputs(String file){
        this.inputs = new Properties(inputs);
        this.currencies = this.inputs.getProperty("currencies").split(",");
    }
    
    /**
     * Devuelve las monedas señaladas en el archivo de configuración.
     * @return 
     */
    String[] getCurrencies(){
        return this.currencies;
    }
    
}
