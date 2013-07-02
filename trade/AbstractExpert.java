package trade;

import io.Exceptions.ExternVariableNotFound;
import io.Extern;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import trade.indicator.base.BollingerBands;

/**
 * Clase base para un expert, es la API para que un expert controle la apertura
 * y cierre de operaciones, proporcionando helpers que interactuan con el
 * sistema.
 *
 * @author omar
 */
public abstract class AbstractExpert {
    
    
    private Brokeable broker;
    private String Symbol;
    private Integer Period;
    private Double Ask = null;
    private Double Bid = null;
    private Double openMin = null;
    private Double Point = null; //Valor del Pip
    private Extern extern;

    /**
     * Este es el "contructor" de la clase, favor de llamarlo a continuación de
     * crear este objecto, ¡GRACIAS!.
     *
     * @param broker
     */
    public AbstractExpert builder(Brokeable broker, Properties file) {
        this.broker = broker;
        this.extern = new Extern(file);
        this.Point = this.Symbol.equals("USDJPY") ? 0.001 : 0.0001;
        try {
            this.Symbol = this.extern.getString("Symbol");
            this.Period = this.extern.getInteger("period");
        } catch (ExternVariableNotFound ex) {
            Logger.getLogger(AbstractExpert.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }

    public Extern getExtern(){
        return this.extern;
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
        return this.broker.getOrdersByMagic(this.Symbol, ma);
    }
    /**
     * Ordenes de la moneda
     * @return 
     */
    public ArrayList<Ordener>  ordersBySymbol(){
        return this.broker.getOrdersBySymbol(this.Symbol);
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
    
    public Double getPoint(){
        return this.Point;
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
