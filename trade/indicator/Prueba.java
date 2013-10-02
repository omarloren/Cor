package trade.indicator;

import trade.indicator.base.BollingerBands;
import trade.indicator.base.Indicator;

/**
 *
 * @author omar
 */
public class Prueba {
    public static void main(String[] args) {
        IndicatorController ic = new IndicatorController();
        Indicator.setFrom(20080417);
        BollingerBands b1 = new BollingerBands("EURUSD", 5, 13);
        System.out.println(b1.values);
        System.out.println(b1);
    }
}