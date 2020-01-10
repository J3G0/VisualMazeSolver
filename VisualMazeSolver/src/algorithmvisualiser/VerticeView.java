/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithmvisualiser;

import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Sebastiaan
 */
public class VerticeView extends Region
{
    final private Vertice node;
    final protected int SIZE = 35;
    final protected int OFFSET = 5;
    
    public VerticeView(Vertice node) 
    {
        this.node = node;
        update();
    }

    public void update()
    {
        getChildren().clear();
        Paint kleur;
        
        switch(node.getVerticeType())
        {
            case SOLID:
                kleur = Color.BLACK;
                break;
                
            case START:
                kleur = Color.RED;
                break;
                
            case END:
                kleur = Color.CYAN;
                break;
                
            case BASIC:
                kleur = Color.CHOCOLATE;
                break;
                
            case TRAVERSED:
                kleur = Color.GREEN;
                break;
                
            case NEIGHBOUR:
                kleur = Color.PINK;
                break;
                
            case PARENT:
                kleur = Color.YELLOW;
                break;
                
            case HEAD:
                kleur = Color.PURPLE;
                break;
            
            default:
                kleur = Color.WHITE;
                break;
        }
        
        final double x = node.getPositionX() * SIZE + node.getPositionX() * OFFSET;
        final double y = node.getPositionY() * SIZE + node.getPositionY() * OFFSET;
        
        Rectangle nodeRect = createRectangle(x, y, kleur);
        
        getChildren().add(nodeRect);
    }
    
    public Vertice getModel()
    {
        return node;
    }
    
    public Rectangle createRectangle(double xCoord, double yCoord, Paint kleur)
    {
            
        Rectangle r = new Rectangle(xCoord, yCoord, SIZE, SIZE);
        r.setFill(kleur);
        
        return r;
    }
}
