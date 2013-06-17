
package dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import io.Inputs;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Manejo de Objectos básicos para mantener conexión con MongoDB.
 * @author omar
 */
public class Mongo {
    
    private com.mongodb.Mongo mongoConn;
    private DB db;
    private DBCollection coll;
    private Map<String,ArrayList> currencies;
    public Mongo(){
        this.mongoConn = Connections.getMongoConnection();
        this.db = this.mongoConn.getDB("local"); //Le damos la db default
        String[] c = Inputs.getCurrencies();
        
        for (int i=0; i <= c.length; i++){
            this.currencies.put(c[i], new ArrayList());
        }
    }
    
    /**
     * Añade una DB.
     * @param db Nombre de la db.
     * @return Self => Por si quieres Method Chaining.
     */
    public Mongo setDB(String db){
        this.db = this.mongoConn.getDB(db);
        return this;
    }
    
    /**
     * Añade una Collection.
     * @param coll Nombre de la collección.
     * @return Self => Por si quieres Method Chaining.
     */
    public Mongo setCollection(String coll){
        this.coll = this.db.getCollection(coll);
        return this;
    }
    
    /**
     * @return Objecto de la DB.
     */
    public DB getDB(){
        return this.db;
    }
    
    /**
     * @return Collección en uso.
     */
    public DBCollection getCollection(){
        return this.coll;
    }
    
    /**
     * Retorna el N de valores(Aperturas de minuto) obtenidos de la BD, además
     * guarda un buffer de datos, si hacemos un query de 100 datos estos se 
     * guardarán en un buffer y seran repornados para peticiones posteriores.
     * @param s
     * @param n
     * @return 
     */
    public ArrayList getBufferData(String s, int n){
        ArrayList base = this.currencies.get(n);
        ArrayList t = new ArrayList();
        DBCursor cursor ;
        for (int i = 0; i < n; i++) {
            t.add(base.get(i));
        }
        /**
         * Si los datos requeridos exeden los existentes hacemos un query para
         * obtenerlos
         */
        if (n > base.size()) {    
            int dif = n - base.size();
            cursor = this.coll.find().sort(new BasicDBObject("$natural", -1)).limit(dif);
            while (cursor.hasNext()) {
                DBObject curTemp = cursor.next();
                t.add(curTemp.get("open"));
            }
        }
        
        return t;
    }
    
    @Override
    public String toString() {
        return ("Db => " + this.db.getName()
                + "=> Coll => " + this.coll.getName() + " =>" + this.coll.count() + " records.");
    }
}
