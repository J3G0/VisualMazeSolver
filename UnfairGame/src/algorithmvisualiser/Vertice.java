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

public class Vertice
{
    private double positionX;
    private double positionY;
    private boolean solid;
    
    public Vertice(double positionX, double positionY, boolean solid) 
    {
        this.solid = solid;
        this.positionX = positionX;
        this.positionY = positionY;
    }
    
    public double getPositionX() { return positionX; }
    public double getPositionY() { return positionY; }
    
    public boolean isSolid() { return solid; }
    public void setSolid(boolean solid) { this.solid = solid; }
}
