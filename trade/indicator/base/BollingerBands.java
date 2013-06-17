
package trade.indicator.base;

import trade.indicator.base.util.SimpleMovingAverage;
import trade.indicator.base.util.StandardDeviation;

/**
 *
 * @author Iván
 */
public class BollingerBands extends Indicator{

    /**
     * StandardDeviation object used to calculate the standard deviation used in
     * this BollingerBands object.
	 *
     */
    private StandardDeviation standardDeviation;
    /**
     * SimpleMovingAverage object used to calculate the SMA in this
     * BollingerBands object.
	 *
     */
    private SimpleMovingAverage simpleMovingAverage;
    /**
     * The value of the lower band.
	 *
     */
    private double lowerBand = 0;
    /**
     * The value of the middle band.
	 *
     */
    private double middleBand = 0;
    /**
     * The value of the upper band.
	 *
     */
    private double upperBand = 0;
    
    
    /**
     * For Bollinger bands: a middle band being an N-period simple moving
     * average an upper band at K times an N-period standard deviation above the
     * middle band a lower band at K times an N-period standard deviation below
     * the middle band
     *
     * This is the 'N' value
     */
    private int n;
    
    public BollingerBands(int p, int n) {
        super(p,n);
        
        simpleMovingAverage = new SimpleMovingAverage(getN(), this.values);
        standardDeviation = new StandardDeviation(getN(), this.values);
        // Calculates simple moving average (SMA)
        middleBand = simpleMovingAverage.getSMA();
        // Calculates the upper band by getting the previously calculated SMA
        upperBand = simpleMovingAverage.getMean() + (standardDeviation.calculateStdDev() * 2);
        // Calculates the lower band by getting the previously calculated SMA
        lowerBand = simpleMovingAverage.getMean() - (standardDeviation.calculateStdDev() * 2);
    }
    
    /**
     *
     * @return upperBand Banda de arriba.
     */
    public Double getUpperBand() {
        return this.upperBand;
    }

    /**
     *
     * @return middleBand regresa el SMA
     */
    public Double getMiddleBand() {
        return this.middleBand;

    }

    /**
     *
     * @return lowerBand regresa la banda de abajo.
     */
    public Double getLowerBand() {
        return this.lowerBand;
    }

           
    private double redondear(Double val){
        int temp= (int)(val * 100000000);
        return temp*0.00000001;
    }
    
    @Override
    public void rollOn(Double val) {
        this.refreshValues(val);
        simpleMovingAverage = new SimpleMovingAverage(getN(), this.values);
        standardDeviation = new StandardDeviation(getN(), this.values);
        // Calculates simple moving average (SMA)
        middleBand = Math.rint(simpleMovingAverage.getSMA() * 100000) / 100000;
        // Calculates the upper band by getting the previously calculated SMA
        upperBand = this.redondear(simpleMovingAverage.getMean() + (standardDeviation.calculateStdDev() * 2)) ;
        // Calculates the lower band by getting the previously calculated SMA
        lowerBand = this.redondear(simpleMovingAverage.getMean() - (standardDeviation.calculateStdDev() * 2)) ;
    }
    
    @Override
    public boolean equals(Object o){
        boolean b = false;
        if (o == null) b = false;
        if (o == this) b = true;
        if(o instanceof BollingerBands) b = true;
        return b;
    }
    
    @Override
    public String toString(){
        return (this.n+" => Up:"+this.upperBand + " Middle:"+this.middleBand + " Down:"+this.lowerBand);
    }
}
