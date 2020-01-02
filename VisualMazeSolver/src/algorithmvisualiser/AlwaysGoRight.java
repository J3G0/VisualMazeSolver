/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithmvisualiser;

import java.util.Set;

/**
 *
 * @author Sebastiaan
 */
public class AlwaysGoRight extends AlgorithmModel
{  
    
    @Override 
    public Vertice[][] getNodes() { return nodes; }
    
    public AlwaysGoRight()
    {
        this.algorithmName = "AlwaysGoRight";    
        System.out.println(algorithmName);
    }
     
    
    @Override
    public void iterate()
    {
        neighbours = getNeighbourVertices(currentNode);
        
    }
}
