
package dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import io.Inputs;
import java.util.ArrayList;
import java.util.Map;

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
    /**
     * Buffer de precios para las monedas, tradicionalmente usado por indicadores,
     * tiene un ArrayList<Object[]> por que tenemos que alamacenar el minuto y 
     * el precio que se genero en ese minuto.
     */
    private static Map<String,ArrayList<Object[]>> currencies;
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
     * Inserta en cierta collección.
     * @param coll
     * @param o 
     */
    public void insert(String coll, DBObject o){
        this.setCollection(coll);
        this.coll.insert(o);
    }
    
    /**
     * Añade una DB.
     * @param db Nombre de la db.
     * @return Self => Por si quieres Method Chaining.
     */
    public Mongo setDB(String db){
        this.db = mongoConn.getDB(db);
        return this;
    }
    
    /**
     * Añade una Collection.
     * @param coll Nombre de la collección.
     * @return Self => Por si quieres Method Chaining.
     */
    public Mongo setCollection(String coll){
        this.coll = db.getCollection(coll);
        return this;
    }
    
    /**
     * @return Objecto de la DB.
     */
    public DB getDB(){
        return db;
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
     * guardarán en un buffer y serán retornados para peticiones posteriores.
     * @param s
     * @param n
     * @return 
     */
    public ArrayList getCoinBuffer(String s, int p, int n){
        //Buffer del s.
        ArrayList<Object[]> base = currencies.get(s);
        
        ArrayList<Object[]> t = new ArrayList();
        this.setCollection(s);
        int cant = n * p;
        DBCursor cursor ;
        
        //Si hay datos en el buffer
        if (base.size() > 0) {
            //Si el buffer puede satisfacer el request.
            if (base.size() > cant) {
                for (int i = 0; i < cant; i++) {
                    t.add(base.get(i));
                }    
            } else {
                //Si no hasta donde pueda 
                for (int i = 0; i < base.size(); i++) {
                    t.add(base.get(i));
                }
            }
        }
        /**
         * Si hemos alcanzado la cantidad necesaria, hacemos un query para obtener
         * los datos faltantes.
         */ 
        if (t.size() < cant) {    
            //Query
            cursor = this.coll.find().sort(new BasicDBObject("$natural", -1)).limit((cant-base.size()));
            while (cursor.hasNext()) {
                DBObject curTemp = cursor.next();
                Object d[] = new Object[2];
                d[0] = this.getMinutos((Integer)curTemp.get("hour"));
                d[1] = curTemp.get("Open");
                t.add(d);
                //Alimentamos nuestro buffer con nuevos datos.
                currencies.get(s).add(d);             
            }
        }
        return t;
    }
    /**
     * Extrae los minutos y los convierte en enteros.
     * @param i
     * @return 
     */
    private Integer getMinutos(Integer i){
        
        String s = i.toString().substring(2, 4);
        return  (new Integer(s));
    }
    
    @Override
    public String toString() {
        return ("Db => " + db.getName()
                + "=> Coll => " + this.coll.getName() + " =>" + this.coll.count() + " records.");
    }
}
