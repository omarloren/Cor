
package io;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Manejo de opciones para la aplicación, a esta clase se le debe de entregar 
 * un archivo JSON, con las opciones generales del sistema.
 * @author omar
 */
public class Inputs {
    private static Properties inputs = new Properties();;
    private static String[] currencies;
    
    /**
     * Inicializa el archivo de propiedades y las monedas, debe de ser llamado
     * antes de utilizar cualquier método de esta clase.
     */
    public static void build(){
        try {
            inputs.load(new FileInputStream("inputs.cnf"));
        } catch (IOException ex) {
            Logger.getLogger(Inputs.class.getName()).log(Level.SEVERE, null, ex);
        }
        currencies = inputs.getProperty("currencies").split(",");
    }
    /**
     * Devuelve las monedas señaladas en el archivo de configuración.
     * @return 
     */
    public static Map<String,ArrayList> getCurrencies(){
        Map<String,ArrayList> r = new HashMap();
        for (int i=0; i < currencies.length; i++){
            r.put(currencies[i], new ArrayList());
        }
        return r;
    }
    /**
     * Obtiene el valor de los Pips (Point) para una moneda determinada. 
     * 0.0001 para todas las monedas 0.001 para el USD/JPY.
     * @param symbol
     * @return 
     */
    public static Double getPoint(String symbol){
        
        /*
         * TODO => Agrregar que este patern se creé apartir de las monedas 
         * señaladas en el archivo, esto para que acepte cualquier formato 
         * USD/JPY debe de ser igual a USDJPY.
         */
        Pattern p = Pattern.compile("(USDJPY|EURUSD|GBPUSD|USDCHF|EURGBP)");
        /*Matcher m = p.matcher(config.getProperty("symbol"));
        if(m.find()){
            r = m.group();
        }*/
         if(symbol.equals("USDJPY")){
             return 0.001;
         } else {
             return 0.00001;
         }
    }
    
}
