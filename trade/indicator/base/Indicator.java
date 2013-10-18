package trade.indicator.base;

import dao.Mongo;
import io.Inputs;
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
     * Tamaño de el indicador.
     */
    private int n;
    /**
     * Periodo del indicador: 1M, 5M, 30M, 1HR, etc.
     */
    private int periodo;
    /**
     * Moneda del indicador.
     */
    private String symbol;
    private Mongo mongo;
    private static Integer from;
    Inputs input;
    Double lastValue;

    /**
     * Al construir hace un query para obtener los datos iniciales del indicador
     * ademas asegura la fiabilidad de estos.
     * @param periodo 
     */
    public Indicator(String s,int p, int n) {
        
        this.mongo = new Mongo().setDB("data").setCollection(s).setFrom(from);
        this.input = Inputs.getInstance();
        this.periodo = p;
        this.n = n;
        this.symbol = s;
        this.mongo.setCollection(this.symbol);
        ArrayList<Object[]> temp = this.mongo.getCoinBuffer(this.symbol, p, n);
        int lastMin = (int)temp.get(0)[0];
        Double lastOpen = (Double)temp.get(0)[1];
        //this.values.add(lastOpen);
        if(this.n == 1){
            this.values.add(lastOpen);
            return;
        }
        for (int i = 0; i < temp.size(); i++) { 
            int min = (int)temp.get(i)[0];
            int dif = 0;
            Double open = (Double)temp.get(i)[1];
            /**
             * Buscamos si hay un salto de hora, puede que pase lo siguiente:
             * Si lastMin = 2 y min = 59 hubo cambio de hora, entoncés buscamos
             * recorrerlo de -1 a 2 buscando un Mod.
             */
            if (lastMin < min) {
                dif = (min - 60);
            } else {
                dif = lastMin - min;
            }
            //Si min es modulo de p, entonces.          
            if (isMod(p, min)) {
                this.values.add(open);
            } else if (dif < 0) {
                //Si hay cambio de hora y hay gap en este cambio.
                if (dif + lastMin >= 0) {
                    for (int j = lastMin; j > dif; j--) {
                        if (this.isMod(p, j)) {
                            this.values.add(lastOpen);
                        }
                    }
                }
            } else if (dif > 1) {
                for (int j = 0; j < dif; j++) {
                    if (this.isMod(p, (j-lastMin))) {
                        this.values.add(lastOpen);
                    }
                }
            }
            lastMin = min;
            lastOpen = open;
            //Si Ya tenemos los datos necesarios.
            if( this.values.size() == n) {
                return;
            }
        }
    }
    /**
     * 
     * @param p
     * @param m
     * @return 
     */
    private boolean isMod(int p, int m){
        int mod = (m - (p * (m / p)));
        //System.out.println(p +" " + m + " " +mod);
         if( mod == 0 ){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Update of the data base.
     * @param val new value.
     */
    public void refreshValues(Double val) {
        this.lastValue = val;
        this.values.remove(values.size()-1);
        this.values.add(0, val);
        this.rollOn();
    }
    
    
    /**
     * @param n the n to set.
     */
    public void setN(int n) {
        this.n = n;
    }
    
    public static void setFrom(Integer f){
        from = f;
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
    public int getPeriodo() {
        return this.periodo;
    }
    /**
     * @return Moneda del indicador
     */
    public String getSymbol() {
        return this.symbol;
    }
    /**
     * Cada indicador debe de implementar este método en donde deberá también
     * llamar al método refreshValues si quiere que actualizar los values.
     * @param val 
     */
    abstract public void rollOn();
    
    @Override
    abstract public boolean equals(Object o);
}
