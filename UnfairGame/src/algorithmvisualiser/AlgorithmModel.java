/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithmvisualiser;

/**
 *
 * @author Sebastiaan
 */
public class AlgorithmModel
{
    final private int ROWS_X = 5;
    final private int ROWS_Y = 5;
    
    //Model has a list of nodes, each node representing a rectangle in the view.  
    private Vertice[][] nodes = new Vertice[ROWS_X][ROWS_Y];
     
    public AlgorithmModel() 
    {
        for (int i = 0; i < ROWS_X ; i++)
        {
            for (int j = 0; j < ROWS_Y ;j++)
            {
                nodes[i][j] = new Vertice(i,j, false);
            }
        }
        
        nodes[2][2].setSolid(true);
        nodes[2][3].setSolid(true);
        
    } 

    public Vertice[][] getNodes()
    {
        //return nodes[0][0];
        return nodes;
    }
    
}
