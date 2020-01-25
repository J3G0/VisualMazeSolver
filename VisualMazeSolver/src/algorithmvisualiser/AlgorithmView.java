/*
 * @author Sebastiaan Vanspauwen
 * @author Jeffrey Gorissen
 * @teacher Kris Aerts
 */
package algorithmvisualiser;

import algorithmvisualiser.vertice.VerticeView;
import algorithmvisualiser.vertice.Vertice;
import java.awt.Point;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Klasse die zorgt voor de View van het Algorithme
 * @author Sebastiaan
 */
public class AlgorithmView extends Region
{
    /**
     * Het model van het algoritme
     */
    private AlgorithmModel model;
    
    /**
     * AlgorithmView Constructor, de view van het model
     * @param model het meegegeven model van het algoritme
     */
    public AlgorithmView(AlgorithmModel model) 
    {
        this.model = model;
        update();
    }
    
    /**
     * Functie die de update voorziet voor de AlgorithmView
     */
    public void update()
    {
        getChildren().clear();
        Vertice[][] nodeList = model.getNodes();
        Text t =  new Text(820,0, "Current model: " + model.algorithmName);
        t.setFill(Color.BLACK);
        t.setFont(new Font(15));      
        
        for (int i = 0; i < nodeList.length; i++)
        {
            for (int j = 0; j < nodeList[i].length ; j++)
            {
                VerticeView vv = new VerticeView(nodeList[i][j]);
                getChildren().add(vv);
            }
        }
        getChildren().add(t);
    }
    
    /**
     * Functie die coordinaat terug geeft wanneer er geklikt wordt in de view
     * Alleen de view weet de exacte coordinaten waar de Vertices staan (met OFFSET en SIZE)
     * Het model weet alleen hoeveel Vertice in de X en Y richting zitten, dus de view moet een coordinaat terug geven
     * @param clickedAtX coordinaat van de x klik
     * @param clickedAtY coordinaat van de y klik
     * @return een coordinaat binnen nodes[x][y]
     */
    public Point getCoordPointFromClick(int clickedAtX, int clickedAtY)
    {   
            
        //Make a view to call for getCoordPointFromClick()
        VerticeView view = new VerticeView(new Vertice(-50,-50));
        // Todo: make dynamic
        // Take a click on (200.0, 78.0)
        // We know each node has view.OFFSET offset from eachother, and the size is view.SIZE;
        
        double rasterFormula = view.getOffset() + view.getSize();
        
        int nodeX = Math.min( (int) (clickedAtX / rasterFormula) , model.ROWS_X - 1 );
        int nodeY = Math.min( (int) (clickedAtY / rasterFormula), model.ROWS_Y - 1);
        
        //System.out.println("Returning node at location:" + nodeX + " , " + nodeY);
        return new Point(nodeX, nodeY);
    }
}
