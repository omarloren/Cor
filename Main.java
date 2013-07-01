
import io.Exceptions.ExternVariableNotFound;
import io.Extern;
import io.Inputs;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase para probar los objectos.
 * @author omar
 */
public class Main{
    
    public void run(){
        try {
            Extern extern = new Extern("./log.chr");
            System.out.println(extern.getInteger("lala"));
        } catch (ExternVariableNotFound ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        new Main().run();
    }
    
}
