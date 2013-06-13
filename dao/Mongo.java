
package dao;

import com.mongodb.DB;
import com.mongodb.DBCollection;

/**
 * Manejo de Objectos básicos para mantener conexión con MongoDB.
 * @author omar
 */
public class Mongo {
    
    private com.mongodb.Mongo mongoConn;
    private DB db;
    private DBCollection coll;
    
    public Mongo(){
        this.mongoConn = Connections.getMongoConnection();
        this.db = this.mongoConn.getDB("local"); //Le damos la db default
    }
    
    /**
     * Añadimos una DB.
     * @param db Nombre de la db.
     * @return Self => Por si quieres Method Chaining.
     */
    public Mongo setDB(String db){
        this.db = this.mongoConn.getDB(db);
        return this;
    }
    
    /**
     * Añados Collection.
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
    
    @Override
    public String toString() {
        return ("Db => " + this.db.getName()
                + "=> Coll => " + this.coll.getName() + " =>" + this.coll.count() + " records.");
    }
}
