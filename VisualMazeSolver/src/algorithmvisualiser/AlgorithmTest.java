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
public class AlgorithmTest extends AlgorithmModel
{
        
    @Override 
    public Vertice[][] getNodes() { return nodes; }
    
    public AlgorithmTest()
    {
        for(int i = 0; i < nodes.length ; i++)
        {
            for(int j = 0; j < nodes[i].length; j++)
            {
                nodes[i][j].setVerticeType(VerticeType.START);
                
            }
        }   
    }
}
