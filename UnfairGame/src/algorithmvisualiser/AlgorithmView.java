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
    private AStarVerticeView view;
    
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
    
    
    public Point getCoordPointFromClick(int x, int y)
    {
        System.out.println("I should get circle under: " +  x + " , " + y);
        
        //Todo: make dynamic
        int nodeX = Math.min( (int) (x / (35 + 2.5)) , model.ROWS_X );
        int nodeY = Math.min( (int) (y / (35 + 2.5)) , model.ROWS_Y );
        
        System.out.println("Returning node at location:" + nodeX + " , " + nodeY);
        return new Point(nodeX, nodeY);
    }
}
