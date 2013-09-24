
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
    private static Integer contOps = 0; 
    public Brokeable(){
        indicatorController = new IndicatorController();
    }    
    
    
    /**
     * Revisa qué compras excedieron sus limites.
     * @param ask 
     */
    public void asker(Double ask) {
        for (int i = 0; i < this.orders.size(); i++) {
            Ordener o = this.orders.get(i);
            if (o.getSide() == '1') {
               if (ask <= o.getSl()) {
                   o.setClosePrice(o.getSl()).setReason("StopLoss");
                   this.closeOrder(o);
                   this.refreshDrowDown(o);
               } else if(ask >= o.getTp()){
                   o.setClosePrice(o.getTp()).setReason("TakeProfit");
                   this.closeOrder(o);
                   this.refreshDrowDown(o);
               }
            }
        }
    }
    
    /**
     * Revisa qué ventas excedieron sus limites.
     * @param bid 
     */
    public void bider(Double bid) {
        for (int i = 0; i < this.orders.size(); i++) {
            Ordener o = this.orders.get(i);
            if (o.getSide() == '2') {
               if (bid >= o.getSl() ) {
                   o.setClosePrice(o.getSl()).setReason("TakeProfit");
                   this.closeOrder(o);
                   this.refreshDrowDown(o);
               }else if(bid <= o.getTp()){
                   o.setClosePrice(o.getTp()).setReason("StopLoss");
                   this.closeOrder(o);
                   this.refreshDrowDown(o);
               }
            }
        }
    }
    
    public void remove(String id){
        for (int i = 0; i < this.orders.size(); i++) {
            if (this.orders.get(i).getID().equals(id)) {
                this.orders.remove(i);
            }
        }
    }
    
    /**
     * Abre una orden.
     * @param orden
     */
    public void sendOrder(Ordener orden) {
         orden.setId(++contOps);
         this.orders.add(orden);         
         //Se podría validar la orden de alguna forma.
         this.ordenOpenCallback(orden);
    }
    
    /**
     * Cierra una orden, por su id.
     * @param price 
     */
    public void closeOrder(Ordener in) {
        this.remove(in.getID());
        this.orderCloseCallback(in);
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
            if (o.getSymbol().equals(symbol) && o.getMagic() == ma && o.isActive()) {
                r.add(o);
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
            if (o.getSymbol().equals(symbol) && o.isActive()) {
                r.add(o);
            }            
        }
        return r; 
    }
    /**
     * Busca una orden dependiendo de su ID.
     * @param id
     * @return 
     */
    public Ordener getOrdernById(String id) {
        Ordener o = null;
        for(int i=0; i < this.orders.size(); i++) {
            if (this.orders.get(i).getID().equals(id)) {
                o = this.orders.get(i);
            }
        }
        return o;
    }
    
    public ArrayList<Ordener> getOrders() {
        return this.orders;
    }
    
    public ArrayList<Ordener> getOrdersActives() {
        ArrayList r= new ArrayList();
        for (int i = 0; i < orders.size(); i++) {
            Ordener o = this.orders.get(i); //temporal.
            if (o.isActive()) {
                r.add(o);
            }            
        }
        return r;
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
    public abstract void ordenOpenCallback(Ordener o); 
    
    /**
     * Cuando una orden es cerrada, ya sea por el usuario o SL/TP.
     */
    public abstract void orderCloseCallback(Ordener o);
    
    /**
     * Refrescamos las perdidas o ganacias flotantes asi como el drow - down.
     * @param orden 
     */
    public abstract void refreshDrowDown(Ordener orden);
}
