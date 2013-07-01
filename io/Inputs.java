
package io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * Manejo de opciones para la aplicación, a esta clase se le debe de entregar 
 * un archivo JSON, con las opciones generales del sistema.
 * @author omar
 */
public class Inputs {
    private static Properties inputs;
    private static String[] currencies;
    private static Inputs self;
    
    /**
     * Manejo de opciones en archivo de configuración.
     */
    private Inputs() { /* EMPTYNESS */ }
    
    /**
     * Objectos que quieran saber algún input, deberán obtener esta instancia.
     * @return 
     */
    public static Inputs getInstance() {
        if (self == null) {
            self = new Inputs();
            try {
                inputs = new Properties();
                inputs.load(new FileInputStream("./inputs.cnf"));
                //this.extern = new Extern(inputs.getProperty("extern_file"));
            } catch (FileNotFoundException ex) {
                System.err.println(ex);
            } catch (IOException ex) {
                System.err.println(ex);
            }
            currencies = inputs.getProperty("currencies").split(",");
        } 
        return self;
    }
    
    /**
     * Devuelve las monedas señaladas en el archivo de configuración, en forma
     * de HashMap de String y Double[].
     * @return 
     */
    public static Map<String,ArrayList<Object[]>> getCurrencies() {
        Map<String,ArrayList<Object[]>> r = new HashMap();
        //Para poder devolver un ArrayList de Object.
        ArrayList<Object[]> a = new ArrayList();
        
        for (int i=0; i < currencies.length; i++){
            r.put(currencies[i], a);
        }
        return r;
    }
    
    /**
     * Obtiene el valor de los Pips (Point) para una moneda determinada. 
     * 0.0001 para todas las monedas 0.001 para el USD/JPY.
     * @param symbol
     * @return 
     */
    public static Double getPoint(String symbol) {
        
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
         if (symbol.equals("USDJPY")) {
             return 0.001;
         } else {
             return 0.00001;
         }
    }
}
