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
    final private int ROWS_X = 25;
    final private int ROWS_Y = 15;
    
    //Model has a list of nodes, each node representing a rectangle in the view.  
    private AStarVertice[][] nodes = new AStarVertice[ROWS_X][ROWS_Y];
    private AStarVertice startNode;
    private AStarVertice endNode;
     
    public AlgorithmModel() 
    {
        for (int i = 0; i < ROWS_X ; i++)
        {
            for (int j = 0; j < ROWS_Y ;j++)
            {
                nodes[i][j] = new AStarVertice(i,j, false);
            }
        }
        nodes[0][0].setVerticeType(VerticeType.START);
        nodes[ROWS_X - 1][ROWS_Y - 1].setVerticeType(VerticeType.END);
        
        nodes[2][2].setVerticeType(VerticeType.SOLID);
        nodes[2][3].setVerticeType(VerticeType.SOLID);
        
    } 

    public AStarVertice[][] getNodes()
    {
        //return nodes[0][0];
        return nodes;
    }
    
    public AStarVertice getStartNode() { return startNode; }
    public AStarVertice getEndNode() { return endNode; }
    
}
