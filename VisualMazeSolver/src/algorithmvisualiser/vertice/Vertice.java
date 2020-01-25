/*
 * @author Sebastiaan Vanspauwen
 * @author Jeffrey Gorissen
 * @teacher Kris Aerts
 */
package algorithmvisualiser.vertice;

/**
 * Basis klasse Vertice.
 * Het bevat alle methodes die nodig zijn voor de Vertices in de visualizer.
 * Deze klasse is een node, maar die naam is reeds in gebruik door JavaFX.
 * Daarom is gekozen voor de naam Vertice.
 */
public class Vertice
{
    /**
     * De x positie van de Vertice
     */
    private double positionX;
    /**
     * De y positie van de Vertice
     */
    private double positionY;
    /**
     * Het type van de Vertice
     */
    protected VerticeType type;
    /**
     * Het vorige type van de Vertice
     */
    protected VerticeType previousVerticeType;
    /**
     * De ouder van de Vertice
     */
    protected Vertice parent;
    
    /**
     * Houd de afstand 'kost' tot de start Vertice (node) bij.
     */
    private double gCost;
    
    /**
     * Houd de afstand 'kost' tot de end Vertice (node) bij.
     */
    private double hCost;
    
    /**
     * Houd de kost bij. (gCost + hCost)
     */
    private double fCost;
    
    /**
     * Vertice constructor om de positie van een Vertice te setten.
     * @param positionX meegeven positie x
     * @param positionY meegeven positie y
     */
    public Vertice(double positionX, double positionY) 
    {
        this.positionX = positionX;
        this.positionY = positionY;
        this.type = VerticeType.BASIC;
        this.parent = null;
        this.previousVerticeType = null;
        this.gCost = 0;
        this.hCost = 0;
        this.fCost = 0;
    }
    
    /**
     * Methode die type op parent zet.
     * @param parent, type vertice.
     */
    public void setParent(Vertice parent){this.parent = parent;}
    /**
     * Methode die parent returned
     * @return parent, type vertice.
     */
    public Vertice getParent(){ return parent;}
            
    /**
     * Methode die type zet.
     * @param type, type vertice.
     */    
    public void setVerticeType(VerticeType type) { this.type = type; }
    /**
     * Methode die het type returned
     * @return type.
     */
    public VerticeType getVerticeType() { return type; }
    
    /**
     * Methode die het vorige type van de huidige Vertice kan zetten
     * @param previousVerticeType, type vorige Vertice.
     */
    public void setPreviousVerticeType(VerticeType previousVerticeType) { this.previousVerticeType = previousVerticeType; }
    /**
     * Methode die de vorige VerticeType opvraagt.
     * @return previousVerticeType, de vorige VerticeType.
     */
    public VerticeType getPreviousVerticeType() { return previousVerticeType; }
    
    /**
     * Methode die de X positie opvraagt.
     * @return positionX de x positie van de Vertice
     */
    public double getPositionX() { return positionX; }
    /**
     * Methode die de Y positie opvraagt.
     * @return positionY de y positie van de Vertice
     */
    public double getPositionY() { return positionY; }
    
    /**
     * Methode die de X positie kan zetten.
     * @param positionX , de X positie
     */
    public void setPositionX(double positionX) {this.positionX = positionX; }
     /**
     * Methode die de Y positie kan zetten.
     * @param positionY , de Y positie
     */
    public void setPositionY(double positionY) {this.positionY = positionY; }
    
    /**
     * Methode die een string met de X en Y positie van een Vertice teruggeeft. (DEBUG)
     * @return String x location: X, y location: Y
     */
    public String getLocation() { return "x location: " + positionX + " y location: "  + positionY;}
    
    /**
     * Methode die de totale kost berekend aan de hand van gCost en hCost.
     * @return FCost, som van gCost en hCost.
     */
    public double getFCost()
    {
        return gCost + hCost;
    }
    
    /**
     * Methode die de totale kost zet.
     * @param fCost, de totale kost.
     */
    public void setFCost(double fCost)
    {
        this.fCost = fCost;
    }
    
    /**
     * Methode die de kost tot aan de end Vertice teruggeeft.
     * @return hCost, de kost tot de end Vertice.
     */
    public double getHCost() { return hCost; }
    /**
     * Methode die de kost tot aan de start Vertice teruggeeft.
     * @return gCost, de kost tot aan de start Vertice.
     */
    public double getGCost() { return gCost; }

    /**
     * Methode die de kost tot aan de end Vertice zet.
     * @param hCost, de kost tot aan de end Vertice.
     */
    public void setHCost(double hCost) {this.hCost = hCost; }
    /**
     * Methode die de kost tot aan de start vertice zet.
     * @param gCost, de kost tot aan de start Vertice.
     */
    public void setGCost(double gCost) {this.gCost = gCost; }
}
