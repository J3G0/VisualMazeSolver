/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithmvisualiser;

/**
 *
 * @author Sebastiaan
 */
//AStarVertice is a vertice with parent node, hCost, gCost and enum Type

public class AStarVertice extends Vertice
{
    //Distance to start node cost
    private double gCost;
    
    //Distance to end node cost
    private double hCost;
    
    //This is total cost (gCost + hCost)
    private double fCost;
    
    //What was the previous node
    private AStarVertice parent;
    
    //What type is this AStarVertice
    private VerticeType type;
    
    //What type was is previously? used for control + z
    private VerticeType previousType;
    
    public AStarVertice(int positionX, int positionY)
    {
        super(positionX, positionY);
        this.parent = null;
        this.type = VerticeType.BASIC;
        this.previousType = null;
        this.gCost = 0;
        this.hCost = 0;
        this.fCost = 0;
    }
    
    public VerticeType getVerticeType() { return type; }
    public void setVerticeType(VerticeType type) { this.type = type;}
    
    public VerticeType getPreviousVerticeType() { return previousType; }
    public void setPreviousVerticeType(VerticeType previousType) { this.previousType = previousType; }
    
    public double getFCost()
    {
        return gCost + hCost;
    }
    
    public void setFCost(double fCost)
    {
        this.fCost = fCost;
    }
    
    public double getHCost() { return hCost; }
    public double getGCost() { return gCost; }

    public void setHCost(double hCost) {this.hCost = hCost; }
    public void setGCost(double gCost) {this.gCost = gCost; }
    
    public AStarVertice getParent() { return parent; }
    public void setParent(AStarVertice parent) { this.parent = parent; }
}
