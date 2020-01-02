/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithmvisualiser;

import java.util.List;

/**
 *
 * @author Sebastiaan
 */
public class AlwaysGoRight extends AlgorithmModel
{  
    
    public AlwaysGoRight()
    {
        this.algorithmName = "AlwaysGoRight";    
        System.out.println(algorithmName);
    }
     
    
    @Override
    public void iterate()
    {
        neighbours = getNeighbourVertices(currentNode, false);
        
        for(Vertice n : neighbours)
        {
            n.setVerticeType(VerticeType.NEIGHBOUR);
        }
        
        //Get possible movement directions from neighbours, if nothing possible, set currentnode to parent
        
        //createDirectionList(neighbours);
        
        //Move to next direction
        
        //Repeat
        
    }
}
