package io.Exceptions;

/**
 *
 * @author omar
 */
public class SettingNotFound extends Exception{
    public SettingNotFound(String setting){
        super("No se opción en archivo de configuración: "+ setting);
    }
}
