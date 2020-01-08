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


// This class is a node (Node name is already taken by JavaFX)
// So this will be named Vertice :)
public class Vertice
{
    private double positionX;
    private double positionY;
    protected VerticeType type;
    protected VerticeType previousVerticeType;
    protected Vertice parent;
    
    //Distance to start node cost
    private double gCost;
    
    //Distance to end node cost
    private double hCost;
    
    //This is total cost (gCost + hCost)
    private double fCost;
    
    
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
    
    public void setParent(Vertice parent){this.parent = parent;}
    public Vertice getParent(){ return parent;}
            
    public void setVerticeType(VerticeType type) { this.type = type; }
    public VerticeType getVerticeType() { return type; }
    
    public void setPreviousVerticeType(VerticeType type) { this.previousVerticeType = previousVerticeType; }
    public VerticeType getPreviousVerticeType() { return previousVerticeType; }
    
    public double getPositionX() { return positionX; }
    public double getPositionY() { return positionY; }
    
    public void setPositionX(double positionX) {this.positionX = positionX; }
    public void setPositionY(double positionY) {this.positionY = positionY; }
    
    public String getLocation() { return "x location: " + positionX + " y location: "  + positionY;}
    
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
}
