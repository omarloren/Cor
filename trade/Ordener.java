
package trade;

/**
 * Una ordén es una compra o venta de cierta moneda, debe de ser inicializada con
 * Con un Symbol, un tamaño de Lotes, un side y un precio. Se debe de implementar
 * el close.
 * 
 * @author omar
 */
public abstract class Ordener {
    /*
     * Precio en el que abrió la ordén.
     */
    private Double openPrice = 0.0;
    /*
     * Precio en el que cerró la ordén.
     */
    private Double closePrice = 0.0;
    /**
     * Stop - Loss.
     */
    private Double sl = 0.0;
    /*
     * Take - Profit.
     */
    private Double tp = 0.0;
    /*
     * Tamaño de la Orden.
     */
    private Double lotes;
    
    private Integer magicma;
    /*
     * Cruce de la orden 
     */
    private String symbol;
    /*
     * Moneda Base de la orden 
     * Ej. Sí simbol = EUR/USD la base sera EUR.
     */
    private String base;
    /**
     * TODO -
     *      Generar un ID para las ordenés.
     */
    private String id;
    /*
     * Si la orden es 1 será "Buy" y si es 2 sera "Sell".
     */
    private String sideStr;
    /*
     * Rason por la cuál una orden cerro.
     */
    private String reason;
    /**
     * 1 o 2 - Compra o Venta.
     */
    private Character side;
    /**
     * Contra parte de la operación.
     */
    private Character averse; //contrario a side
    /*
     * Si la orden esta activa(Abierta).
     */
    private Boolean active;

    public Ordener(String symbol, Double lotes, Character side, Double price) {
        this.symbol = symbol;
        this.lotes = lotes * 10000; // Añadir esto a archivo de CONFIG!
        this.openPrice = price;
        this.side = side;
        this.sideStr = this.side == '1' ? "Buy" : "sell";
        this.averse = this.side == '1' ? '2' : '1';
        this.active = true;
        this.base = this.symbol.substring(0, 3);
    }
    /**
     * Añade la razón por la que la operacion fué cerrada.
     * @param reason
     * @return 
     */
    public Ordener setReason(String reason){
        this.reason = reason;
        return this;
    }
    /**
     * Añade Stop loss y Take Profit.
     * @param sl
     * @param tp
     * @return 
     */
    public Ordener setStopAndTake(Double sl, Double tp) {
        this.sl = sl;
        this.tp = tp;
        return this;
    }
    
    public Ordener setOpenPrice(Double open){
        this.openPrice = open;
        return this;
    }
    
    /**
     * Añade Precio de cierre.
     * @param close
     * @return 
     */
    public Ordener setClosePrice(Double close){
        this.closePrice = close;
        this.active = false;
        return this;
    }
    
    public Ordener setActive(Boolean active){
        this.active = active;
        return this;
    }
    
    /**
     * Añade MAGICMA -> Ver documento de las cosas que nadie puede explicar.
     * @param magic
     * @return 
     */
    public Ordener setMagic(Integer magicma){
        this.magicma = magicma;
        return this;
    }
    public Ordener setId(Integer id){
        this.id = id.toString();
        return this;
    }
    /**
     * Si la Orden esta vigente o no.
     * @return 
     */
    public Boolean isActive(){
        return this.active;
    }
    
    public String getSymbol() {
        return this.symbol;
    }

    public Double getTp() {
        return this.tp;
    }

    public Double getSl() {
        return this.sl;
    }

    public Double getLotes() {
        return this.lotes;
    }

    public Character getAverse() {
        return this.averse;
    }

    public String getBase() {
        return this.base;
    }

    public String getSideStr() {
        return this.sideStr;
    }
    
    public Double getOpenPrice(){
        return this.openPrice;
    }
    
    public Double getClosePrice(){
        return this.closePrice;
    }
    
    public String getID(){
        return this.id;
    }
    
    public Character getSide(){
        return this.side;
    }
    
    public Integer getMagic(){
        return this.magicma;
    }
    
    public String getReason(){
        return this.reason;
    }
}
