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
    private int gCost;
    private int hCost;
    private AStarVertice parent;
    private VerticeType type;
    
    public AStarVertice(int positionX, int positionY, boolean solid)
    {
        super(positionX, positionY);
        this.parent = null;
        this.type = VerticeType.BASIC;
    }
    
    public VerticeType getVerticeType() { return type; }
    public void setVerticeType(VerticeType type) { this.type = type;}
    
}
