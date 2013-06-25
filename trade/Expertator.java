
package trade;

import java.util.ArrayList;
import trade.indicator.Controller;
import trade.indicator.base.BollingerBands;

/**
 * Clase base para un expert, es la API para que un expert controle la apertura
 * y cierre de operaciones, proporcionando helpers que interactuan con el 
 * sistema.
 * @author omar
 */
public abstract class Expertator {
    Brokeable broker;
    String Symbol ;
    Integer Period;
    Double Ask = null;
    Double Bid = null;
    Double openMin = null;       
    Double Point = null; //Valor del Pip
    private Controller controller;
    /**
     * Este es el "contructor" de la clase, favor de llamarlo a continuación de 
     * crear este objecto, ¡GRACIAS!.
     * @param broker 
     */
    public Expertator builder(Brokeable broker, String symbol, Integer period){
        this.broker = broker;
        this.Symbol = symbol;
        this.Period = period;
        this.Point = this.Symbol.equals("USDJPY")? 0.001 :0.0001;
        this.controller = new Controller();
        return this;
    }
    /**
     * Añade precio de bid.
     * @param bid
     * @return 
     */
    public Expertator setBid(Double bid){
        this.Bid = bid;
        return this;
    }
    
    /**
     * Añade precio Ask.
     * @param ask
     * @return 
     */
    public Expertator setAsk(Double ask){
        this.Ask = ask;
        return this;
    }
    
    /**
     * Añade precio de apertura de minuto.
     * @param open
     * @return 
     */
    public Expertator setOpenMin(Double open){
        this.openMin = open;
        return this;
    }
    
    public Controller iController(){
        return this.controller;
    }
    /**
     * Obtiene el total de ordenes de para cierto magic pero del symbol 
     * actual, normalmente lo usamos si queremos cerrar las ordenes.
     * @return 
     */
    ArrayList<Ordener> ordersTotal(Integer ma){
        return this.broker.getOrdersByMagic(this.Symbol, ma);
    }
    
    /**
     * Acceso directo al numero de posiciones para un magic.
     * @param ma
     * @return 
     */
    Integer ordersByMagic(Integer ma){
        return this.broker.getOrdersByMagic(this.Symbol, ma).size();
    }
    /**
     * Para saber si una grafica tiene los datos necesarios para operar.
     * @return True si los tiene, False si no.
     */
    public Boolean isReady(){
        return (this.Ask != null && this.Bid != null && this.openMin != null &&
                this.Period != null && this.Symbol != null && this.broker != null);
    }
    
    /**
     * Abre una posición, la clase que implemente esto, tiene que crear un 
     * objecto Ordener y enviarselo al broker.
     * @param price
     * @param lotes
     * @param side
     * @param sl
     * @param tp 
     */
    public abstract void orderSend(Double price,Double lotes, char side,Double sl, Double tp);
    
   /**
     * Se llama una vez al inicializar el expert, usualmente es usado para 
     * inicializar variables locales.
     */
    public abstract void Init();
    /**
     * Evento de tick llamado al recibir un nuevo precio(Bid), es en donde todo
     * pasa.
     */
    public abstract void onTick();
    /**
     * En caso de ser una prueba, se llama al finalizar la misma.
     */
    public abstract void onDone();
}
