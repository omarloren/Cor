package io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
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
    private String host = "";
    private int port = -1;
    public Messenger(String host, int port){
        this.host = host;
        this.port = port;
    }
    /**
     * Añadimos ID de la aplicación.
     * @param id
     * @return 
     */
    public Messenger setID(String id){
        this.id = id;
        return this;
    }
    
    @Override
    public void run(){
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
        this.send(json);
    }
    
    /**
     * Escribimos mensajes a node un objecto json.
     * @param json 
     */
    public void send(JSONObject json) {
        try {
            //Escribmos a el stream los bytes de la cadena.
            this.outNode.write(json.toJSONString().getBytes()); 
        } catch (IOException ex) {
            System.out.println(ex);
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
