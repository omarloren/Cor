
package trade;

/**
 * Clase base para un expert, es la API para que un expert controle la apertura
 * y cierre de operaciones, proporcionando helpers que interactuan con el 
 * sistema,
 * @author omar
 */
public abstract class Expertator {
    private Brokeable broker;
    private Double first
    public Expertator(Brokeable broker){
        this.broker = broker;
    }
    /**
     * Abrimos una posición, la clase que implemente esto, tiene que crear un 
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
