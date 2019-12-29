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
    
    public Vertice(double positionX, double positionY) 
    {
        this.positionX = positionX;
        this.positionY = positionY;
    }
    
    public double getPositionX() { return positionX; }
    public double getPositionY() { return positionY; }
    
    public void setPositionX(double positionX) {this.positionX = positionX; }
    public void setPositionY(double positionY) {this.positionY = positionY; }
}
