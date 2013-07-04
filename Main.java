
import io.Exceptions.ExternVariableNotFound;
import io.Extern;
import io.Messenger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 * Clase para probar los objectos.
 * @author omar
 */
public class Main extends Messenger{
    
    public Main(Integer i,int o){
        
    }
    
    public void correr(){
        this.run();
    }
    
    public static void main(String[] args) {
        Main main = new Main(1);
        main.correr();
    }

    @Override
    public void inHandler(JSONObject msj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
