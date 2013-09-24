package trade;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import trade.indicator.IndicatorController;
import trade.indicator.base.BollingerBands;
import trade.indicator.base.Indicator;

/**
 * Clase base para un expert, es la API para que un expert controle la apertura
 * y cierre de operaciones, proporcionando helpers que interactuan con el
 * sistema.
 *
 * @author omar
 */
public abstract class AbstractExpert {
    
    
    private Brokeable broker;
    private String symbol;
    private Integer periodo;
    private Double Ask = null;
    private Double Bid = null;
    private Double openMin = null;
    private Double point;
    private Integer magic;
    

    /**
     * Este es el "contructor" de la clase, favor de llamarlo a continuación de
     * crear este objecto. (NO DEBERÍA PROGRAMAR COMO EN PHP)
     *
     * @param broker
     */
    public AbstractExpert __construct(Brokeable broker, Integer from, String symbol, Double point, Integer magic) {
        Indicator.setFrom(from);
        this.broker = broker;
        this.symbol = symbol;
        this.point = point;
        this.magic = magic;
        return this;
    }
    
    /**
     * Añade precio de bid.
     *
     * @param bid
     * @return
     */
    public AbstractExpert setBid(Double bid) {
        this.Bid = bid;
        return this;
    }

    /**
     * Añade precio Ask.
     *
     * @param ask
     * @return
     */
    public AbstractExpert setAsk(Double ask) {
        this.Ask = ask;
        return this;
    }

    /**
     * Añade precio de apertura de minuto.
     *
     * @param open
     * @return
     */
    public AbstractExpert setOpenMin(Double open) {
        this.openMin = open;
        return this;
    }

    /**
     * Obtiene el total de ordenes de para cierto magic pero del symbol actual,
     * normalmente lo usamos si queremos cerrar las ordenes.
     *
     * @return ArrayList con las ordenes
     */
    public ArrayList<Ordener> ordersTotal(Integer ma) {
        return this.broker.getOrdersByMagic(this.symbol, ma);
    }
    /**
     * Ordenes de la moneda
     * @return 
     */
    public ArrayList<Ordener>  getOrdersBySymbol() {
        return this.broker.getOrdersBySymbol(this.symbol);
    }
    
    public Integer ordersBySymbol() {
        Integer i = this.broker.getOrdersBySymbol(this.symbol).size();
        return i;
    }
    
    public void setPeriodo(Integer periodo) {
         this.periodo = periodo;
    }
    
    /**
     * Para saber si una grafica tiene los datos necesarios para operar.
     *
     * @return True si los tiene, False si no.
     */
    public Boolean isReady() {
        return (this.Ask != null && this.Bid != null && this.openMin != null
                && this.periodo != null && this.symbol != null && this.broker != null);
    }

    public String getSymbol() {
        return this.symbol;
    }

    public int getPeriod() {
        return this.periodo;
    }
    
    public Brokeable getBrokeable() {
        return this.broker;
    }
    
    public Double getAsk() {
        return this.Ask;
    }
    
    public Double getBid() {
        return this.Bid;
    }
    
    public Double getOpenMin() {
        return this.openMin;
    }
    
    public Double getPoint() {
        return this.point;
    }
    
    public Integer getMagic() {
        return this.magic;
    }
    /**
     * Corta un valor a un formato valido de precio, es decir si algún cálculo 
     * es 1.3265666455646 el cortará este valor a 1.32656.
     * @param p
     * @return 
     */
    public Double priceCut(Double p) {
       
        MathContext mc = new MathContext(7, RoundingMode.HALF_UP);
        BigDecimal a = new BigDecimal(p,mc);
        return a.doubleValue();
    }
    
    public double redondear(Double d) {
        return Arithmetic.redondear(d);
    }
    
    /**
     * Interfaz para crear el indicador de Bollinger Bands.
     *
     * @param n
     * @return
     */
    public BollingerBands iBand(int n) {
        IndicatorController ic = this.broker.getIndicatorController();
        return ic.newBollingerBand(this.symbol, this.periodo, n);
    }

    /**
     * Abre una posición, la clase que implemente esto, tiene que crear un
     * objecto Ordener y enviarselo al broker.
     *
     * @param price
     * @param lotes
     * @param magic
     * @param side
     * @param sl
     * @param tp
     */
    //public abstract void orderSend(Double price, Double lotes, Integer magic, Character side, Double sl, Double tp);

    /*
     * Helpersitos para el tiempo.
     */
    public abstract int getSeconds();

    public abstract int getMinutes();

    public abstract int getHora();

    public abstract int getDay();

    public abstract int getMonth();

    public abstract int getYear();
    
    public abstract Boolean isNewCandle();
}
