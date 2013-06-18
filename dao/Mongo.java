
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
    //Conexión con Mongo
    private static com.mongodb.Mongo mongoConn ;
    //Al inicio es la DB por default.
    private static DB db ;
    private DBCollection coll;
    //Buffer de precios para las monedas, tradicionalmente usado por indicadores.
    private static Map<String,ArrayList> currencies;
    /**
     * Inicializa la conexión con mongo, y la db. Debe de ser llamado antes de 
     * usar cualquier método de esta clase.
     */
    public static void build(){
        mongoConn = Connections.getMongoConnection();
        db = mongoConn.getDB("local");
        currencies = Inputs.getCurrencies();
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
    public ArrayList getBufferData(String s, int p, int n){
        ArrayList base = this.currencies.get(s);
        ArrayList t = new ArrayList();
        this.setCollection(s);
        int cant = n * p;
        DBCursor cursor ;
        /**
         * Obtenemos la última hora para saber qué tanto extenderemos el query.
         * Ej. Si la ultima hora es 161700,p = 5 y n es 3 entonces debemos obtener 
         *     los 15 datos apartir de las 161500. 
         * en este caso devolveremos 17 valores.
         */
        //int dif = this.getMinutos((Integer)this.coll.find()
        //        .sort(new BasicDBObject("$natural", -1)).limit(1).next().get("hour"));
        
        if (base.size() > 0) {
            for (int i = 0; i < cant; i++) {
                t.add(base.get(i));
            }
        }
        /**
         * Si los datos requeridos excedén los existentes hacemos un query para
         * obtenerlos
         */
        if (cant > base.size()) {    
            //Obtenemos 
            cursor = this.coll.find().sort(new BasicDBObject("$natural", -1)).limit((cant-base.size()));
            
            while (cursor.hasNext()) {
                DBObject curTemp = cursor.next();
                t.add(curTemp.get("Open"));
                this.currencies.get(s).add(curTemp.get("Open"));
            }
        }
        return t;
    }
    /**
     * No se si usare esto, estupida loss precision.
     * @param i
     * @return 
     */
    private int getMinutos(Integer i){
        
        Double d = (i /100) * 0.01;
        Integer n = d.intValue();
        System.out.println(d +" "+n);
        return  (int)((d - n) * 100);
    }
    
    @Override
    public String toString() {
        return ("Db => " + this.db.getName()
                + "=> Coll => " + this.coll.getName() + " =>" + this.coll.count() + " records.");
    }
}
