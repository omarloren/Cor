
package trade.indicator.base;


/**
 * "Indicador" que busca que cierto numeros de variables x se encuentren en un 
 * rango de variables y.
 * @author omar
 */
public class Trend extends Indicator{
    private int  n = 0; //Periodos
    private int x1 = 0; //Primer intervalo x
    private int x2 = 0; //Segundo intervalo x
    private int y1 = 0; //Primer  intervalo y
    private int y2 = 0; //Segundo intervalo y
    private int c = 0; //Coincidencias    
    private Double point;
    public Trend(String s, int p, int x1, int x2, int y1, int y2, int c){
        super(s, p, x1);
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.c = c;
        this.point = this.input.getPoint(this.getSymbol());
    }
    /**
     * Buscamos una tendencia alcista, devolviendo el número de coincidencias
     * entre los rango de "y" a partir de "x1" y hasta "x2".
     * @param val
     * @return 
     */
    public Boolean isUp(){
        int cont = 0;
        for (int i = 0; i < (this.x1 - this.x2); i++) {
            double current = this.values.get(this.x1 + i);
            if (current < (this.lastValue + this.y1 * this.point) && 
                    this.values.get(i) > (this.lastValue + this.y2 * this.point)){
                cont++;
            }
        }
        if (cont >= this.c) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Buscamos una tendencia bajista, devolviendo el número de coincidencias
     * entre los rango de "y" a partir de "x1" y hasta "x2".
     * @param val
     * @return 
     */
    public Boolean isDn(){
        int cont = 0;
        for (int i = 0; i < (this.x1 - this.x2); i++) {
            double current = this.values.get(this.x1 + i);
            if (this.values.get(i) > (this.lastValue + this.y2 * this.point) && 
                    this.values.get(i) < (this.lastValue + this.y1 * this.point)){
                cont++;
            }
        }
        if (cont >= this.c) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public void rollOn() {
        //
    }

    @Override
    public boolean equals(Object o) {
        boolean b = false;
        if (o == null) b = false;
        if (o == this) b = true;
        if(o instanceof BollingerBands) b = true;
        return b;
    }

}
