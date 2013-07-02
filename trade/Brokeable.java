
package trade;

import java.util.ArrayList;
import trade.indicator.IndicatorController;

/**
 * Control de transacciones(ordenés), proporciona una interfaz para abrir o 
 * cerrar operaciones, ademas regula que estas no exedan sus limites Sl/Tp.
 * En un futuro podría proporcionar la habilidad de tener diversos tipos de 
 * ordenés(Juego).
 * @author omar
 */
public abstract class Brokeable {
    /*
     * Todas las ordenes abiertas.
     */
    private ArrayList<Ordener> orders = new ArrayList();
    static IndicatorController indicatorController;
    public Brokeable(){
        indicatorController = new IndicatorController();
    }    
    /**
     * Revisa qué compras excedieron sus limites.
     * @param ask 
     */
    public void asker(Double ask){
        for (int i = 0; i < this.orders.size(); i++) {
            if (this.orders.get(i).getSide() == '1') {
                if (ask <= this.orders.get(i).getSl() || ask >= this.orders.get(i).getTp()) {
                    this.orders.get(i).setClosePrice(ask).setReason("TP ó SL");

                }
            }
        }
    }
    
    /**
     * Revisa qué ventas excedieron sus limites.
     * @param bid 
     */
    public void bider(Double bid){
        for (int i = 0; i <this.orders.size(); i++) {
            Ordener o = this.orders.get(i);
            if (o.getSide() == '2'){
               if (bid >= o.getSl() || bid <= o.getTp()) {
                   o.setClosePrice(bid).setReason("TP ó SL");
                   this.orderClosedCallback(o);
               }
            }
        }
    }
    
    public void remove(String id){
        for (int i = 0; i < this.orders.size(); i++) {
            if (this.orders.get(i).getID().equals(id)){
                this.orders.remove(i);
            }
        }
    }
    
    /**
     * Abre una orden.
     * @param orden
     */
    public void sendOrder(Ordener orden){
         this.orders.add(orden);
         //Se podría validar la orden de alguna forma.
         this.ordenOpenedCallback(orden);
    }
    
    /**
     * Cierra una orden, por su id.
     * @param price 
     */
    public void closeOrder(String id, Double price){
        for (int i = 0; i < this.orders.size(); i++) {
            Ordener o = this.orders.get(i);
            if (o.getID().equals(id)) {
                o.setClosePrice(price);
                //TODO guardar registro en DB.
                this.orders.remove(i).setReason("closed by User");
                this.orderClosedCallback(o);
            }
        }
    }
    
    /**
     * Obtiene el total de ordenes que hay para ese cruce y magic. Un expert
     * que este manejando cierta moneda no podrá ver otros cruces.
     * @param ma MAGIC NUMBER.
     * @param symbol
     * @return 
     */
    public ArrayList<Ordener> getOrdersByMagic(String symbol, Integer ma){
        ArrayList r= new ArrayList();
        for (int i = 0; i < orders.size(); i++) {
            Ordener o = this.orders.get(i); //temporal.
            if (o.getSymbol().equals(symbol) && o.getMagic() == ma) {
                r.add(orders.get(i));
            }            
        }
        return r; 
    }
    
    /**
     * Obtiene las ordenes para un symbol.
     * @param symbol
     * @return 
     */
    public ArrayList<Ordener> getOrdersBySymbol(String symbol){
        ArrayList r= new ArrayList();
        for (int i = 0; i < orders.size(); i++) {
            Ordener o = this.orders.get(i); //temporal.
            if (o.getSymbol().equals(symbol)) {
                r.add(orders.get(i));
            }            
        }
        return r; 
    }
    /**
     * Busca una orden dependiendo de su ID.
     * @param id
     * @return 
     */
    public Ordener getOrdernById(String id){
        Ordener o = null;
        for(int i=0; i < this.orders.size(); i++){
            if (this.orders.get(i).getID().equals(id)) {
                o = this.orders.get(i);
            }
        }
        return o;
    }
    
    ArrayList<Ordener> getOrders(){
        return this.orders;
    }
    
    public Brokeable setOrders(ArrayList<Ordener> orders){
        this.orders = orders;
        return this;
    }
    
    /**
     * @return Controlador de indicadores.
     */
    public IndicatorController getIndicatorController(){
        return indicatorController;
    }
    /**
     * Añadimos precios de minutos para los indicadores.
     * @param open 
     */
    public abstract  void setOpenMin(Double open);
    
    /**
     * Cuando una orden sea abierta exitosamente.
     */
    public abstract void ordenOpenedCallback(Ordener orden); 
    
    /**
     * Cuando una orden es cerrada, ya sea por el usuario o SL/TP.
     */
    public abstract void orderClosedCallback(Ordener orden);
}
