/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithmvisualiser;

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
    private VerticeView view;
    
    public AlgorithmView(AlgorithmModel model) 
    {
        this.model = model;
        update();
    }
    
    public void update()
    {
        getChildren().clear();
        Vertice[][] nodeList = model.getNodes();
        
        for (int i = 0; i < nodeList.length; i++)
        {
            for (int j = 0; j < nodeList[i].length ; j++)
            {
                VerticeView vv = new VerticeView(nodeList[i][j]);
                getChildren().add(vv);
            }
        }
    }
}
