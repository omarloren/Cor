package trade;

import io.Extern;
import java.util.ArrayList;
import trade.indicator.base.BollingerBands;

/**
 * Clase base para un expert, es la API para que un expert controle la apertura
 * y cierre de operaciones, proporcionando helpers que interactuan con el
 * sistema.
 *
 * @author omar
 */
public abstract class AbstractExpert {
    
    
    Brokeable broker;
    String Symbol;
    Integer Period;
    Double Ask = null;
    Double Bid = null;
    Double openMin = null;
    Double Point = null; //Valor del Pip
    public Extern extern;

    /**
     * Este es el "contructor" de la clase, favor de llamarlo a continuación de
     * crear este objecto, ¡GRACIAS!.
     *
     * @param broker
     */
    public AbstractExpert builder(Brokeable broker, String symbol, Integer period) {
        this.broker = broker;
        this.Symbol = symbol;
        this.Period = period;
        this.Point = this.Symbol.equals("USDJPY") ? 0.001 : 0.0001;
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
     * @return
     */
    ArrayList<Ordener> ordersTotal(Integer ma) {
        return this.broker.getOrdersByMagic(this.Symbol, ma);
    }

    /**
     * Acceso directo al numero de posiciones para un magic.
     *s
     * @param ma
     * @return
     */
    Integer ordersByMagic(Integer ma) {
        return this.broker.getOrdersByMagic(this.Symbol, ma).size();
    }

    /**
     * Para saber si una grafica tiene los datos necesarios para operar.
     *
     * @return True si los tiene, False si no.
     */
    public Boolean isReady() {
        return (this.Ask != null && this.Bid != null && this.openMin != null
                && this.Period != null && this.Symbol != null && this.broker != null);
    }

    public String getSymbol() {
        return this.Symbol;
    }

    public int getPeriod() {
        return this.Period;
    }

    public Brokeable getBrokeable() {
        return this.broker;
    }
    
    public Double getAsk(){
        return this.Ask;
    }
    
    public Double getBid(){
        return this.Bid;
    }
    
    public Double getOpenMin(){
        return this.openMin;
    }
    
    /**
     * Interfaz para crear el indicador de Bollinger Bands.
     *
     * @param n
     * @return
     */
    public BollingerBands iBand(int n) {
        return this.broker.getIndicatorController().newBollingerBand(this.Symbol, this.Period, n);
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
    public abstract void orderSend(Double price, Double lotes, Integer magic, Character side, Double sl, Double tp);

    /*
     * Helpersitos para el tiempo.
     */
    public abstract int getSeconds();

    public abstract int getMinutes();

    public abstract int getHora();

    public abstract int getDay();

    public abstract int getMonth();

    public abstract int getYear();
}
