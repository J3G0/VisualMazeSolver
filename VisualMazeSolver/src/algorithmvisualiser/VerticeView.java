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
    final private int SIZE = 15;
    final private int OFFSET = 5;
    
    public VerticeView(Vertice node) 
    {
        this.node = node;
        update();
    }

    public void update()
    {
        getChildren().clear();
        Paint kleur;
        kleur = Color.CHOCOLATE;
        
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
