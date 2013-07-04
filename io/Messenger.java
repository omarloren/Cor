package io;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Clase qué sirve como mensajero entre una instancia de Node.js y Java. 
 * Es un Thread aparte ya que de otra forma se bloquearia el programa al 
 * estar escuchando de un puerto de forma permanente.
 * @author omar
 */
public abstract class Messenger extends Thread{
    private Socket socket;
    private DataOutputStream outNode;
    private String id;
    private static String host;
    private static int port = -1;
    
    public static void build(String h, int p){
        host = h;
        port = p;
    }
    /**
     * Añade ID de la aplicación.
     * @param id
     * @return 
     */
    public Messenger setID(String id){
        this.id = id;
        return this;
    }
    
    @Override
    public void run() {
        JSONObject root;
        JSONObject json; 
        
        try {
            this.socket = new Socket(this.host, this.port);
            this.outNode = new DataOutputStream(socket.getOutputStream());
            BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
            InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");            
            if (!this.id.isEmpty()) { //Si ya hay ID mandamos el login.
                this.handShake(); 
            }
            StringBuffer in = new StringBuffer();
            int c;
            while (isr.read() > 0) {
                while ((c = isr.read()) != 10) { //Hasta encontrar nueva linea.
                    in.append((char) c);
                }
                /**
                 * Convertimos a JSON la cadena entrante, si hay un error en el
                 */
                root = (JSONObject) new JSONParser().parse("{" + in);
                json = (JSONObject) root.get("msj");
                this.inHandler(json);
                //Limpiamos buffer...
                in.delete(0, in.length());
            }
        } catch (UnknownHostException ex) { //Por si no existe el host.
            System.err.println(ex); 
        } catch (IOException ex) { //Por si el socket no existe-
            System.err.println(ex);
        } catch (ParseException ex) { //Por si el mensaje entrante no es json.
            System.err.println(ex);
        }
    }
    
    /**
     * Handshake al server.
     * @param profile 
     */
    public void handShake(){    
        JSONObject json = new JSONObject();
        json.put("type", "handshake");
        json.put("name", this.id);
        this.nodeStream(json);
    }
    
    /**
     * Escribe mensajes a node un objecto json.
     * @param json 
     */
    public void nodeStream(JSONObject json) {
        try {
            //Escribmos a el stream los bytes de la cadena.
            this.outNode.write(json.toJSONString().getBytes()); 
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    /**
     * Escribe mensajes a node apartir de un string, este debe de estar bien
     * formateado si no lanzará una excepcion al parsearlo.
     * @param s 
     */
    public void nodeStream(String s){
        try {
            JSONObject o = (JSONObject)new JSONParser().parse(s);
            this.nodeStream(o);
        } catch (ParseException ex) {
            System.err.println(ex);
        }
    }
    /**
     * Debe de ser implementado con un switch.
     * ----switch ((String) json.get("type")) {...}
     * haciendo un case por cada type que se quiera implemetar.
     * @param msj 
     */
    public abstract void inHandler(JSONObject msj);
}
