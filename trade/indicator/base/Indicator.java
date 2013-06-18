
package trade.indicator.base;

import dao.Mongo;
import java.util.ArrayList;

/**
 *
 * @author omar
 */
public abstract class Indicator {
    /**
     * Values - Son los datos con los que el indicador trabajará. 
     */
    public ArrayList<Double> values = new ArrayList();
    /**
     * Variable para verificar que se tiene la cantidad correcta de valores.
     */
    public boolean go = false;
    /**
     * Tamaño del indicador.
     */
    private int n;
    private int periodo;
    private String symbol;
    
    private Mongo mongo;
    
    /**
     * Al construir hacemos un query para obtener los datos iniciales.
     * @param periodo 
     */
    public Indicator(String s,int p, int n) {
        
        this.mongo = new Mongo().setDB("history").setCollection(s);
        this.periodo = p;
        this.n = n;
        this.symbol = s;
        this.mongo.setCollection(this.symbol);
        ArrayList<Double> temp = this.mongo.getBufferData(this.symbol, p, n);
        System.out.println(temp.size());
        
        for (int i = 0; i < temp.size(); i++) {
            //TODO - Verificar la integridad de esto.
            if (i % p == 0) {
                this.values.add(temp.get(i));
            }
        }
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
     * @return Tamaño del indicador.
     */
    public int getN() {
        return this.n;
    }
        
    /**
     * @return Periodo del indicador
     */    
    public int getPeriodo(){
        return this.periodo;
    }
    /**
     * @return Moneda del indicador
     */
    public String getSymbol(){
        return this.symbol;
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
