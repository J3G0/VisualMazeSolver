/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithmvisualiser;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

/**
 *
 * @author Sebastiaan
 */
public class AlgorithmView extends Region
{
    private AlgorithmModel model;
    
    public AlgorithmView(AlgorithmModel model) 
    {
        this.model = model;
        update();
    }
    
    public void update()
    {
        getChildren().clear();
        AStarVertice[][] nodeList = model.getNodes();
        
        for (int i = 0; i < nodeList.length; i++)
        {
            for (int j = 0; j < nodeList[i].length ; j++)
            {
                AStarVerticeView vv = new AStarVerticeView(nodeList[i][j]);
                getChildren().add(vv);
            }
        }
    }
    
    
    public Point getCoordPointFromClick(int clickedAtX, int clickedAtY)
    {   
            
        //Make a view to call for getCoordPointFromClick()
        AStarVerticeView view = new AStarVerticeView(new AStarVertice(-50,-50));
        // Todo: make dynamic
        // Take a click on (200.0, 78.0)
        // We know each node has view.OFFSET offset from eachother, and the size is view.SIZE;
        
        double rasterFormula = view.OFFSET + view.SIZE;
        
        int nodeX = Math.min( (int) (clickedAtX / rasterFormula) , model.ROWS_X - 1 );
        int nodeY = Math.min( (int) (clickedAtY / rasterFormula), model.ROWS_Y - 1);
        
        //System.out.println("Returning node at location:" + nodeX + " , " + nodeY);
        return new Point(nodeX, nodeY);
    }
}
