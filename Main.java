
import dao.Mongo;


/**
 * Clase para probar los objectos.
 * @author omar
 */
public class Main extends Mongo {
    
    public void run(){
        this.setDB("history").setCollection("EURUSD");
        System.out.println(this);
    }
    
    public static void main(String[] args) {
        new Main().run();
    }
    
}
