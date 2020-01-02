/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithmvisualiser;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sebastiaan
 */

// Pseudo code from "https://en.wikipedia.org/wiki/A*_search_algorithm#Pseudocode" was used for thinking purposes
// Also the tutorial from Sebastian Lague helped me understand the basic concept of A*
// Source: https://github.com/SebLague/Pathfinding/blob/master/Episode%2003%20-%20astar/Assets/Scripts/Pathfinding.cs



//Base class of an Algorithm model

public class AlgorithmModel
{
    //Amount of nodes in x direction (same for each algorithm)
    final protected int ROWS_X = 25;
    
    //Amount of nodes in y direction (same for each algorithm)
    final protected int ROWS_Y = 15;
    
    
    //Startnode of the algorithm
    final protected Vertice startNode; 
    
    //Endnode of the algorithm
    final protected Vertice endNode;
    
    //current node that is looping
    final protected Vertice currentNode;
    
    //A list of nodes that was already considered
    final protected List<Vertice> closedSet = new ArrayList<>();
    
    //A list of nodes that was not considered
    final protected List<Vertice> openSet = new ArrayList<>();
    
    //Model has a list of nodes, each node representing a rectangle in the view.  
    final protected Vertice[][] nodes = new Vertice[ROWS_X][ROWS_Y];
    
    public AlgorithmModel()
    {
        closedSet.clear();
        openSet.clear();
        
        for (int i = 0; i < ROWS_X ; i++)
        {
            for (int j = 0; j < ROWS_Y ;j++)
            {
                nodes[i][j] = new Vertice(i,j);
            }
        } 
        startNode = nodes[0][0];
        nodes[0][0].setVerticeType(VerticeType.START);
        
        currentNode = startNode;
        
        endNode = nodes[5][5];
        nodes[5][5].setVerticeType(VerticeType.END);
    }
    
    protected Vertice[][] getNodes()
    {
        return nodes;
    }
    
}