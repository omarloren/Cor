
package dao;

import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Control en la creación de instancias de DB's, soportadas:
 *  -MongoDB.
 * @author omar
 */
public class Connections {
    private static com.mongodb.Mongo mongo;
    
    static synchronized com.mongodb.Mongo getMongoConnection() {
        if (mongo == null) {
            try {
                mongo = new com.mongodb.Mongo("localhost",27017);
            } catch (UnknownHostException ex) {
                Logger.getLogger(Mongo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return mongo;
    }
}
