/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithmvisualiser;

import java.text.DecimalFormat;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author Sebastiaan
 */
public class AStarVerticeView extends Region
{
    final private AStarVertice node;
    final protected int SIZE = 35;
    final protected int OFFSET = 5;
    
    public AStarVerticeView(AStarVertice node) 
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
                
            default:
                kleur = Color.WHITE;
                break;
        }
        
        final double x = node.getPositionX() * SIZE + node.getPositionX() * OFFSET;
        final double y = node.getPositionY() * SIZE + node.getPositionY() * OFFSET;
        DecimalFormat df = new DecimalFormat("#.0");
        double cost = node.getFCost();
        Text t = new Text( x + OFFSET , y + (SIZE / 2), df.format(cost));
        t.setFill(Color.BLACK);
        t.setFont(new Font(15));
        Rectangle nodeRect = createRectangle(x, y, kleur);
        
        getChildren().add(nodeRect);
        getChildren().add(t);
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
