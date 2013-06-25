
import dao.Mongo;
import io.Inputs;
import trade.indicator.IndicatorController;
import trade.indicator.base.BollingerBands;


/**
 * Clase para probar los objectos.
 * @author omar
 */
public class Main{
    
    public void run(){
        Inputs.build();
        Mongo.build();
        IndicatorController c = new IndicatorController();
        c.newBollingerBand("EURUSD", 15, 10);
        
    }
    
    public static void main(String[] args) {
        new Main().run();
    }
    
}
