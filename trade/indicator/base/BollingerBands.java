
package trade.indicator.base;

import trade.Arithmetic;
import trade.indicator.base.util.SimpleMovingAverage;
import trade.indicator.base.util.StandardDeviation;

/**
 * For Bollinger bands: a middle band being an N-period simple moving average an
 * upper band at K times an N-period standard deviation above the middle band a
 * lower band at K times an N-period standard deviation below the middle band
 *
 * @author Ivan
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
        
    public BollingerBands(String s, int p, int n) {
        super(s, p, n);
        this.simpleMovingAverage = new SimpleMovingAverage(getN(), this.values);
        this.standardDeviation = new StandardDeviation(getN(), this.values);
        // Calculates simple moving average (SMA)
        this.middleBand = Arithmetic.redondear(this.simpleMovingAverage.getSMA() , 4);
        // Calculates the upper band by getting the previously calculated SMA
        this.upperBand = this.middleBand + (this.standardDeviation.calculateStdDev() * 2);
        // Calculates the lower band by getting the previously calculated SMA
        this.lowerBand = this.middleBand - (this.standardDeviation.calculateStdDev() * 2);
    }
    
    /**
     *
     * @return upperBand Banda de arriba.
     */
    public Double getUpperBand() {
        return Arithmetic.redondear(this.upperBand);
    }

    /**
     *
     * @return middleBand regresa el SMA
     */
    public Double getMiddleBand() {
        return Arithmetic.redondear(this.middleBand);

    }

    /**
     *
     * @return lowerBand regresa la banda de abajo.
     */
    public Double getLowerBand() {
        return Arithmetic.redondear(this.lowerBand);
    }
    
    @Override
    public void rollOn() {
        
        this.simpleMovingAverage = new SimpleMovingAverage(getN(), this.values);
        this.standardDeviation = new StandardDeviation(getN(), this.values);
        // Calculates simple moving average (SMA)
        this.middleBand = simpleMovingAverage.getSMA();
        // Calculates the upper band by getting the previously calculated SMA
        this.upperBand =  this.middleBand + (standardDeviation.calculateStdDev() * 2);
        // Calculates the lower band by getting the previously calculated SMA
        this.lowerBand = this.middleBand - (standardDeviation.calculateStdDev() * 2);
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
        return ("#"+this.getN()+" => Up:"+this.getUpperBand() + " Middle:"+this.getMiddleBand() + " Down:"+this.getLowerBand());
    }
}
