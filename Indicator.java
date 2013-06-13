


import java.util.ArrayList;

/**
 * Base de los indicadores.
 * @author omar
 */
abstract class Indicator {
    /**
     * Values - Son los datos con los que el indicador trabajará. 
     */
    public ArrayList<Double> values;
    /**
     * Tamaño del indicador.
     */
    private int n;
    private Double point;
    /**
     * Variable para verificar que se tiene la cantidad correcta de valores.
     */
    public boolean go = false;
    /**
     * Al construir hacemos un query para obtener los datos iniciales.
     * @param periodo 
     */
    public Indicator(Integer n){
        this.n = n;
    }
    
    /**
     * Actualiza el array de valores con un valor nuevo 
     * @param val 
     */
    public void refreshValues(Double val) {
        this.values.remove(values.size()-1);
        this.values.add(0, val);
    }
    
    /**
     * @param n the n to set.
     */
    public void setN(int n) {
        this.n = n;
    }

    /**
     * @return the n.
     */
    public int getN() {
        return this.n;
    }
        
    public Double getPoint() {
        return this.point;
    }
    
    /**
     * Cada indicador debe de implementar este método en donde deberá también
     * llamar al método refreshValues si quiere actualizar los values.
     * @param val 
     */
    abstract public void rollOn(Double val);
}
