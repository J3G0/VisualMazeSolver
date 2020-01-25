/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithmvisualiser.algorithmtype;

import algorithmvisualiser.AlgorithmModel;
import algorithmvisualiser.AlgorithmModel;
import algorithmvisualiser.AlgorithmState;
import algorithmvisualiser.vertice.MovementDirection;
import algorithmvisualiser.vertice.Vertice;
import algorithmvisualiser.vertice.VerticeType;
import algorithmvisualiser.vertice.VerticeType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sebastiaan
 */

// Pseudo code from "https://en.wikipedia.org/wiki/A*_search_algorithm#Pseudocode" was used for thinking purposes
// Also the tutorial from Sebastian Lague helped me understand the basic concept of A*
// Source: https://github.com/SebLague/Pathfinding/blob/master/Episode%2003%20-%20astar/Assets/Scripts/Pathfinding.cs
// Source: https://www.youtube.com/watch?v=-L-WgKMFuhE&list=PLFt_AvWsXl0cq5Umv3pMC9SPnKjfp9eGW&index=1

public class AStar extends AlgorithmModel
{
    /**
     *
     * AStarModel constructor
     */
    public AStar() 
    {
        this.algorithmName = "A Star";  
        openSet.add(currentNode);
        
    } 
  
    /**
     * AStarModel constructor with map parameter
     * @param map 2D Vertice map that contains the Vertice data
     */
    public AStar(Vertice[][] map) 
    {
        super(map);
        this.algorithmName = "A Star";  
        openSet.add(currentNode);     
    } 
    
    /**
     * Iterate function that iterates the algorithm once
     */
    @Override
    public void iterate()
    {
        //If algorithm is solving, update state
        if(getAlgorithmState() == AlgorithmState.SOLVING)
        {
            updateModelState();
        }
        
        //If algorithm has completed, draw the taken path
        if(getAlgorithmState() == AlgorithmState.SOLVED)
        {
            drawTakenPath();
        }      
        
        // If there are no more Vertices in openSet it means that the algorithm has found
        // no possible solution (it is stuck)
        if (openSet.isEmpty() && getAlgorithmState() == AlgorithmState.SOLVING)
        {
            System.out.println("Setting state to unsolveable");
            setAlgorithmState(AlgorithmState.UNSOLVABLE);
        }
        //If openSet contains a Vertice
        else if (getAlgorithmState() == AlgorithmState.SOLVING)
        {          
            increaseIterations();
            //set currentNode as the first Vertice in openset
            currentNode = openSet.get(0);
            
            //Set currentNode parent to traversed
            if(currentNode.getVerticeType() == VerticeType.BASIC)
            {             
                if(currentNode.getParent() != null && currentNode.getParent() != startNode)
                {
                   currentNode.getParent().setVerticeType(VerticeType.TRAVERSED); 
                }
            }
            //Clear neighbour list to prepare for new iterations
            neighbours.clear();
            
            //Loop the corresponding Vertice in openSet and choose best option based on fCost and hCost
            // @see Vertice
            for(int i = 1; i < openSet.size(); i++)
            {
                if (openSet.get(i).getFCost() < currentNode.getFCost() || openSet.get(i).getFCost() == currentNode.getFCost())
                {
                    if (openSet.get(i).getHCost() < currentNode.getHCost())
                    {
                        currentNode = openSet.get(i);
                    }
                }               
            }
            //remove currentNode from openSet as it is already looked at and add it to closedSet
            openSet.remove(currentNode);
            closedSet.add(currentNode);

            for(Vertice v : closedSet)
            {
                if(v.getVerticeType() == VerticeType.BASIC || v.getVerticeType() == VerticeType.NEIGHBOUR)
                {
                    v.setVerticeType(VerticeType.TRAVERSED);
                }
            }

            //Get neighbours from currentNode and allow diagonal
            neighbours = getNeighbourVertices(currentNode, true, false);
            
            //Loop over neighbours to set each cost
            for (Vertice n : neighbours)
            {
                if(n.getVerticeType() == VerticeType.BASIC)
                {
                     n.setVerticeType(VerticeType.NEIGHBOUR);
                }            
                if (!closedSet.contains(n))
                {
                    double cost = getTravelCost(n, currentNode);
                    double currentNodeCost = currentNode.getGCost();
                    double newCost = cost + currentNodeCost;
                    if (newCost < n.getGCost() || !openSet.contains(n))
                    {
                        n.setGCost(currentNodeCost + cost);
                        n.setHCost(getTravelCost(n, endNode)); 
                        n.setFCost(n.getGCost() + n.getHCost());
                        n.setParent(currentNode);
                    }

                    if(!openSet.contains(n))
                    {
                        openSet.add(n);
                    }
                }
            }    
        }
    }
    
    /**
     * Method that calls iterate() untill solved or unsolveable state is reached
     * if solved it calls drawTakenPath
     */
    @Override
    public void finish()
    {
        while(getAlgorithmState() == AlgorithmState.SOLVING)
        {
            iterate();
        }
        if(getAlgorithmState() == AlgorithmState.SOLVED)
        {
            drawTakenPath();
        }
    }
    
    /**
     * Gets the travelCost based on distance from startNode and endNode
     *  Say nodeA is at (0,0) and nodeB is at (2,2);
     *  NodeA has to travel two diameters of distance (Pythagoras) or 2 horizontal and two vertical
     *  let one distance be 1, a vertical distance would be sqtr(1^2 + 1^2) = 1.41
     *  The lowest cost distance is either 2 * 1.41 = 2.82 or 4 * 1 = 4. (2.82)
     *   
     *  What if the endnode is on the same line as the startNode?
     *  NodeA (1,0) , nodeB(4,0)
     *  There is no need for Pythagoras here (no x or y difference), the cost would be 3 * 1 = 1.
     *  
     *   We need to calculate the absolute difference between x,y of the two nodes (nodeA, nodeB)
     * 
     * @param nodeA the first Vertice (Vertice of which the cost is calculated)
     * @param nodeB the secone Vertice (startNode or endNode)
     * @return travelCost the weights (cost) of the travel distance
     */
    public double getTravelCost(Vertice nodeA, Vertice nodeB)
    {
        double travelCost = 0;
        final double tileTravelCost = 10;
        
        double absoluteDifferenceX = Math.abs(nodeA.getPositionX() - nodeB.getPositionX() );
        double absoluteDifferenceY = Math.abs(nodeA.getPositionY() - nodeB.getPositionY() );
        
        double xBigger = (Math.sqrt(Math.pow(tileTravelCost, 2) + Math.pow(tileTravelCost, 2)) * absoluteDifferenceY  + tileTravelCost * (absoluteDifferenceX - absoluteDifferenceY));
        double yBigger = (Math.sqrt(Math.pow(tileTravelCost, 2) + Math.pow(tileTravelCost, 2)) * absoluteDifferenceX  + tileTravelCost * (absoluteDifferenceY - absoluteDifferenceX));
        
        travelCost = absoluteDifferenceX > absoluteDifferenceY ? xBigger : yBigger;
        return travelCost;
        
    }
}