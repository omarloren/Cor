
package trade;

import java.util.ArrayList;

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
    
    public Brokeable(){
        //No sé qué :/
    }    
    /**
     * Revisamos qué compras excedieron sus limites.
     * @param ask 
     */
    public void asker(Double ask){
        for(int i=0; i <this.orders.size(); i++){
            if (this.orders.get(i).getSide() == '1'){
               if (ask <= this.orders.get(i).getSl() || ask >= this.orders.get(i).getTp()) {
                   this.orders.get(i).setClosePrice(ask).setReason("TP ó SL");
                   
               }
            }
        }
    }
    
    /**
     * Revisamos qué ventas excedieron sus limites.
     * @param bid 
     */
    public void bider(Double bid){
        for(int i=0; i <this.orders.size(); i++){
            Ordener orden = this.orders.get(i);
            if (orden.getSide() == '2'){
               if (bid >= orden.getSl() || bid <= orden.getTp()) {
                   orden.setClosePrice(bid).setReason("TP ó SL");
                   this.orderClosedCallback(orden);
               }
            }
        }
    }
    
    /**
     * Abrimos una orden.
     * @param orden
     */
    public void sendOrder(Ordener orden){
         this.orders.add(orden);
         //Se podría validar la orden de alguna forma.
         this.ordenOpenedCallback(orden);
    }
    
    /**
     * Cerramos una orden, por su id.
     * @param price 
     */
    public void closeOrder(String id, Double price){
        for (int i = 0; i < this.orders.size(); i++) {
            Ordener orden = this.orders.get(i);
            if (orden.getID().equals(id)) {
                orden.setClosePrice(price);
                //TODO guardar registro en DB.
                this.orders.remove(i).setReason("closed by User");
                this.orderClosedCallback(orden);
            }
        }
    }
    
    /**
     * Cuando una orden sea abierta exitosamente.
     */
    public abstract void ordenOpenedCallback(Ordener orden); 
    
    /**
     * Cuando una orden es cerrada, ya sea por el usuario o SL/TP.
     */
    public abstract void orderClosedCallback(Ordener orden);
}
