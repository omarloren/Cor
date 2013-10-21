/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trade.indicator;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import dao.Mongo;
import help.Candle;
import help.Date;
import io.JSON;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import trade.indicator.base.BollingerBands;
import trade.indicator.base.Indicator;

/**
 *
 * @author omarlopezorendain
 */
public class Factory {
    private Mongo mongo;
    private Integer from;
    private IndicatorController ic;
    public Factory(Integer from){
        this.from = from;
        this.mongo = new Mongo().setDB("batch");
        this.ic = new IndicatorController();
        Indicator.setFrom(this.from);
    }
    
    public void newBollinger(String s, int p, int n) {
        String c = s + "_" + p + "_" +n;
        if (!this.exist(c)) {
            this.build(s, p, n);
        } else {
            System.out.println("No lo puedo crear, ya existe :(");
        }
    }
      
    private boolean exist(String c) {
        Set<String> l = this.mongo.getDB().getCollectionNames();
        Iterator i = l.iterator();
        while (i.hasNext()) {
            if (i.next().equals(c)) {
                return true;
            }
        }
        return false;
    }
    
    private void build(String s, int p, int n){
        Mongo data = new Mongo().setDB("data");
        String name = s + "_" + p + "_" + n;
        data.setCollection(s);
        DBCursor cursor = data.getRange(this.from, 20120801);
        BollingerBands b = new BollingerBands(s, p, n);
        Candle candle =  new Candle(p);
        System.out.println(b);
        while(cursor.hasNext()){
            DBObject o = cursor.next();
            Date.setTime(String.valueOf(o.get("DTYYYYMMDD")), String.valueOf(o.get("TIME")));
            if(candle.isNew(Date.getMinutes())){
                Double open = (Double)o.get("OPEN");
                b.refreshValues(open);
                HashMap<String, Object> r = new HashMap();
                r.put("date", Integer.parseInt(Date.getDate()));
                r.put("time", Integer.parseInt(Date.time()));
                r.put("down", b.getLowerBand());
                r.put("middle", b.getMiddleBand());
                r.put("up", b.getUpperBand());
                this.mongo.insert(name, JSON.HashToJson(r));
            }
        }
    }
}
