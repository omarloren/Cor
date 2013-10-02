
package io;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.simple.JSONObject;

/**
 * Wrapper para manejar objectos JSON usando simple JSON's, pero aún más fácil.
 * @author omar
 */
public class JSON {
    public static String HashToJson( HashMap<String, Object> hm){
        JSONObject r = new JSONObject();
        Iterator i = hm.entrySet().iterator();
        while(i.hasNext()) {
            Map.Entry e = (Map.Entry)i.next();
            r.put(e.getKey(), e.getValue());
        }
        return r.toJSONString();
    }
}
