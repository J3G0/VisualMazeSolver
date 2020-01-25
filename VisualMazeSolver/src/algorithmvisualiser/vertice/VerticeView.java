/*
 * @author Sebastiaan Vanspauwen
 * @author Jeffrey Gorissen
 * @teacher Kris Aerts
 */
package algorithmvisualiser.vertice;

import algorithmvisualiser.vertice.Vertice;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Klasse die zorgt voor de View van de Vertices (nodes)
 * Erft van Region.
 */
public class VerticeView extends Region
{
    /**
     * Een Vertice is een node.
     */
    final private Vertice node;
    /**
     * Size van een Vertice.
     */
    final protected int SIZE = 35;
    /**
     * Offset tot de volgende Vertice.
     */
    final protected int OFFSET = 5;
    
    /**
     * VerticeView Constructor, de view van de Vertice.
     * @param node, de meegegeven node van de Vertice
     */
    public VerticeView(Vertice node) 
    {
        this.node = node;
        update();
    }

    /**
     * Methode die update voorziet voor VerticeView.
     * De Vertice wordt geupdate navenant het type dat deze is.
     * BVB; START- en End Vertice zijn resp. rood en cyaan.
     * SOLID Vertices zijn zwart,...
     */
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
    
    /**
     * Methode die huidige Vertice (node) teruggeeft
     * @return node
     */
    public Vertice getModel()
    {
        return node;
    }
    
    /**
     * Methode die de rechthoekige Vertices aanmaakt en teruggeeft
     * @param xCoord, de x positie van de Vertice
     * @param yCoord, de y positie van de Vertice
     * @param kleur, de kleur van de Vertice.
     * @return r, de net aangemaakte rechthoek (Vertice)
     */
    public Rectangle createRectangle(double xCoord, double yCoord, Paint kleur)
    {
            
        Rectangle r = new Rectangle(xCoord, yCoord, SIZE, SIZE);
        r.setFill(kleur);
        
        return r;
    }
    
    /**
     * Methode die de grootte teruggeeft.
     * @return SIZE, de grootte
     */
    public int getSize() { return SIZE; }
    /**
     * Methode die de offset teruggeeft.
     * @return OFFSET, de offset.
     */
    public int getOffset() { return OFFSET; }
}
