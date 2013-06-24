
package trade.indicator;

import java.util.ArrayList;
import trade.indicator.base.*;

/**
 * Interfaz de los controladores, solo se pueden crear atravez de esta clase,
 * se controla que sean refrescados, etc.
 * @author omar
 */
public class Controller {
    
    /**
     * Actualmente tenemos un array para todos los indicadores, pero podriamos 
     * tener uno para cada moneda y/o periodo.
     */
    private static ArrayList<Indicator> indicatorsPool = new ArrayList();
    public Controller(){
        //
    }
    /**
     * Refresca los indicadores con un nuevo precio.
     * @param r 
     */
    public void update(Double r){
        for(Indicator i : indicatorsPool ){
            i.refreshValues(r);
        }
    }
    /**
     * Crea un nuevo BollingerBand, primero buscamos en la base de los 
     * indicadores si existe ya existe un indicador que sea del mismo tipo y de 
     * los mismos periodos que el deseado. Si existe devuelve la referencia del
     * indicador, si no existe le crea.
     * @param n Periodo de bandas deseado.
     * @return Objecto del Bollinger.
     */
    public BollingerBands newBollingerBand(String s, int p, int n){
        BollingerBands b = null;
        for (Indicator i : indicatorsPool){
            if (i.equals(b)) {
                b = (BollingerBands) i;
                break;
            }
        }
        if (b == null) {
            b  = new BollingerBands(s, p, n);
        }
        return b;
    }
}
