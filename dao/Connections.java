
package dao;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Control en la creación de instancias de DB's, soportadas:
 *  - MongoDB.
 *  - Y ya...
 * @author omar
 */
public class Connections {
    private static com.mongodb.MongoClient mongo;
    /**
     * Obtiene conexión con Mongo.
     * @return 
     */
    static synchronized com.mongodb.MongoClient getMongoConnection() {
        if (mongo == null) {
            try {
                mongo = new MongoClient("localhost", 27017);
            } catch (UnknownHostException ex) {
                Logger.getLogger(Mongo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return mongo;
    }
}
