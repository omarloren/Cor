package trade.indicator;

import trade.indicator.base.BollingerBands;
import trade.indicator.base.Indicator;

/**
 *
 * @author omar
 */
public class Prueba {
    public static void main(String[] args) {
        /*IndicatorController ic = new IndicatorController();
        Indicator.setFrom(20080103);
        BollingerBands b1 = new BollingerBands("EURUSD", 5, 5);
        System.out.println(b1.values);
        System.out.println(b1);*/
        Factory f = new Factory(20080103);
        f.newBollinger("EURUSD", 5, 10);
    }
}