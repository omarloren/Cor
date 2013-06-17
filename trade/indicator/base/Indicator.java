
package trade.indicator.base;

import java.util.ArrayList;

/**
 *
 * @author omar
 */
public abstract class Indicator {
    /**
     * Values - Son los datos con los que el indicador trabajará. 
     */
    public ArrayList<Double> values;
    /**
     * Tamaño del indicador.
     */
    private int n;
    
    private int periodo;
    
    /**
     * Variable para verificar que se tiene la cantidad correcta de valores.
     */
    public boolean go = false;
    private Double point = 0.0001;
    
    /**
     * Al construir hacemos un query para obtener los datos iniciales.
     * @param periodo 
     */
    public Indicator(int p, int n) {
        this.periodo = p;
        this.n = n;
        //Llenamos el indicador con los n periodos.
        //this.values = Runner.data.getBufferData(this.n);
        
        /*for (int i = 1; i < dif; i++) {
            temp.remove(0);
        }
        for (int i = temp.size(); i >=0; i--) {
            if (i % n == 0) {
                if (cont < periodo) {
                    str.append(" "+temp.get(i-1)+",");
                    data.add(temp.get(i-1));
                    cont++;
                }
            }
        }*/
    }
    
    /**
     * Update of the data base.
     * @param val new value.
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
     * llamar al método refreshValues si quiere que actualizar los values.
     * @param val 
     */
    abstract public void rollOn(Double val);
    
    @Override
    abstract public boolean equals(Object o);
}
