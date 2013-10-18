package trade;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Clase que proporciona herramientas para hacer operaciones aritméticas básicas
 * entre otras curiosidades con Doubles usando BigDecimals.s
 *
 * @author omar
 */
public class Arithmetic {

    /**
     * Multiplica N cantidad de Doubles.
     *
     * @param values
     * @return
     */
    public static double multiplicar(Double... values) {
        BigDecimal b1 = new BigDecimal(values[0]);

        if(values.length > 0){
            double res = 0.0;
            MathContext mc = new MathContext(5);
            for (int i = 1; i < values.length; i++) {
                res += b1.multiply(new BigDecimal(values[i]), mc).setScale(6, RoundingMode.HALF_UP).doubleValue();
            }
            return res;
        } else {
            return b1.doubleValue();
        }
    }

    /**
     * Divide N cantidad de Doubles.
     *
     * @param values
     * @return
     */
    public static double dividir(Double... values) {
        //Dividimos la suma de los values entre el tamaño
        double res = new BigDecimal(sumar(values)).divide(new BigDecimal(values.length), 5, RoundingMode.HALF_UP).doubleValue();
        return res;
    }
    /**
     * Divide N cantidad de Doubles entre un numero i
     *
     * @param values
     * @return
     */
    public static double dividir(double i, Double... values) {
        //Divide la suma de los values entre el tamaño
        double suma = sumar(values);
        double res = new BigDecimal(suma).divide(new BigDecimal((Double)i), 7, RoundingMode.HALF_EVEN).doubleValue();
        return res;
    }
    /**
     * Resta N cantidad de doubles.
     *
     * @param values
     * @return
     */
    public static double restar(Double... values) {
        BigDecimal b1 = new BigDecimal(values[0]);
        if(values.length > 0){
            for (int i = 1; i < values.length; i++) {
                BigDecimal b2 = new BigDecimal(values[i]);
                /**
                 * BigDecimals son inmutables, cada operacion que se les aplique
                 * retornará una nueva instancia con la operación realizada.
                 */
                b1 = b1.subtract(b2);
            }
            return b1.setScale(4, RoundingMode.HALF_EVEN).doubleValue();
        } else {
            return b1.doubleValue();
        }
    }

    /**
     * Suma N cantidad de doubles.
     *
     * @param values
     * @return
     */
    public static double sumar(Double... values) {
        BigDecimal b1 = new BigDecimal(values[0]);
        if(values.length > 0){
            for (int i = 1; i < values.length; i++) {
                BigDecimal b2 = new BigDecimal(values[i]);
                //Explicación de esto en @restar ;)
                b1 = b1.add(b2);
            }
            return b1.setScale(4, RoundingMode.HALF_EVEN).doubleValue();
        } else {
            return b1.doubleValue();
        }
    }

    /**
     * Redondea un Double a 4 digitos después del punto.
     *
     * @param v
     * @return
     */
    public static double redondear(Double v) {
        BigDecimal a = new BigDecimal(v);
        return a.setScale(4, RoundingMode.HALF_EVEN).doubleValue();
    }

    /**
     * Redondea un Double a cualquier i después del punto.
     *
     * @param v
     * @param i
     * @return
     */
    public static double redondear(Double v, int i) {
        BigDecimal a = new BigDecimal(v);
        return a.setScale(i, RoundingMode.HALF_EVEN).doubleValue();
    }
    
    public static double redondearUp(Double v, int i){
        BigDecimal a = new BigDecimal(v);
        return a.setScale(i, RoundingMode.HALF_UP).doubleValue();
    }
    /*public static double redondearUp6(double v){
        BigDecimal a = new BigDecimal(v);
        //return a.setScale(i, RoundingMode.).doubleValue();
    }*/
    public static double redondearDn(Double v, int i){
        BigDecimal a = new BigDecimal(v);
        return a.setScale(i, RoundingMode.HALF_DOWN).doubleValue();
    }
    public static Double doubleCut(Double v, int i){
        BigDecimal a = new BigDecimal(v);
        return a.setScale(i,RoundingMode.FLOOR).doubleValue();
    }
    
    public static Boolean equals(Double a, double b){
        BigDecimal big = new BigDecimal(a);
        BigDecimal comp = new BigDecimal(b);
        return big.compareTo(comp) == 0 ? true : false;
    }
}
