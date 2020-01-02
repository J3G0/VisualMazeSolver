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
    private VerticeType type;
    private Vertice parent;
    
    public Vertice(double positionX, double positionY) 
    {
        this.positionX = positionX;
        this.positionY = positionY;
        this.type = VerticeType.BASIC;
        this.parent = null;
    }
    
    
    public void setVerticeType(VerticeType type) { this.type = type; }
    public VerticeType getVerticeType() { return type; }
    
    public double getPositionX() { return positionX; }
    public double getPositionY() { return positionY; }
    
    public void setPositionX(double positionX) {this.positionX = positionX; }
    public void setPositionY(double positionY) {this.positionY = positionY; }
    
    public String getLocation() { return "x location: " + positionX + " y location: "  + positionY;}
}
