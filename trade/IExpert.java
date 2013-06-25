/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trade;

/**
 *
 * @author omar
 */
public interface IExpert {

    /**
     * Se llama una vez al inicializar el expert, usualmente es usado para
     * inicializar variables locales.
     */
    public void Init();

    /**
     * Evento de tick llamado al recibir un nuevo precio(Bid), es en donde todo
     * pasa.
     */
    public void onTick();

    /**
     * En caso de ser una prueba, se llama al finalizar la misma.
     */
    public void onDone();
}
