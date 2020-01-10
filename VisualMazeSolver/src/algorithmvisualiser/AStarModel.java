/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithmvisualiser;

import algorithmvisualiser.AStar.*;
import algorithmvisualiser.AlgorithmModel;
import algorithmvisualiser.VerticeType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 *
 * @author Sebastiaan
 */

// Pseudo code from "https://en.wikipedia.org/wiki/A*_search_algorithm#Pseudocode" was used for thinking purposes
// Also the tutorial from Sebastian Lague helped me understand the basic concept of A*
// Source: https://github.com/SebLague/Pathfinding/blob/master/Episode%2003%20-%20astar/Assets/Scripts/Pathfinding.cs

public class AStarModel extends AlgorithmModel
{
    public AStarModel() 
    {
        this.algorithmName = "A Star";  
        openSet.add(currentNode);
        
    } 
  
    public AStarModel(Vertice[][] map) 
    {
        super(map);
        this.algorithmName = "A Star";  
        openSet.add(currentNode);     
    } 
    
    public void iterate()
    {
        if(getAlgorithmState() == AlgorithmState.SOLVING)
        {
            updateModelState();
        }
        
        //todo: needs a better way to detect final node reached
        if(getAlgorithmState() == AlgorithmState.FINISHED)
        {
            drawTakenPath();
        }      
        
        if (openSet.isEmpty() && getAlgorithmState() == AlgorithmState.SOLVING)
        {
            System.out.println("Setting state to unsolveable");
            setAlgorithmState(AlgorithmState.UNSOLVABLE);
        }
        else if (getAlgorithmState() == AlgorithmState.SOLVING)
        {          
            increaseIterations();
            currentNode = openSet.get(0);
            
            if(currentNode.getVerticeType() == VerticeType.BASIC)
            {
                currentNode.setVerticeType(VerticeType.HEAD);
                
                if(currentNode.getParent() != null && currentNode.getParent() != startNode)
                {
                   currentNode.getParent().setVerticeType(VerticeType.TRAVERSED); 
                }
            }
            
            neighbours.clear();
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
               openSet.remove(currentNode);
               closedSet.add(currentNode);

               for(Vertice v : closedSet)
               {
                   if(v.getVerticeType() == VerticeType.BASIC || v.getVerticeType() == VerticeType.NEIGHBOUR)
                   {
                       v.setVerticeType(VerticeType.TRAVERSED);
                   }
               }

               neighbours = getNeighbourVertices(currentNode, true);
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

    public double getTravelCost(Vertice nodeA, Vertice nodeB)
    {
        double travelCost = 0;
        final double tileTravelCost = 10;
        // Say nodeA is at (0,0) and nodeB is at (2,2);
        // NodeA has to travel two diameters of distance (Pythagoras) or 2 horizontal and two vertical
        // let one distance be 1, a vertical distance would be sqtr(1^2 + 1^2) = 1.41
        // The lowest cost distance is either 2 * 1.41 = 2.82 or 4 * 1 = 4. (2.82)
        
        // What if the endnode is on the same line as the startNode?
        // NodeA (1,0) , nodeB(4,0)
        // There is no need for Pythagoras here (no x or y difference), the cost would be 3 * 1 = 1.
        
        // We need to calculate the absolute difference between x,y of the two nodes (nodeA, nodeB)
        
        double absoluteDifferenceX = Math.abs(nodeA.getPositionX() - nodeB.getPositionX() );
        double absoluteDifferenceY = Math.abs(nodeA.getPositionY() - nodeB.getPositionY() );
        
        double xBigger = (Math.sqrt(Math.pow(tileTravelCost, 2) + Math.pow(tileTravelCost, 2)) * absoluteDifferenceY  + tileTravelCost * (absoluteDifferenceX - absoluteDifferenceY));
        double yBigger = (Math.sqrt(Math.pow(tileTravelCost, 2) + Math.pow(tileTravelCost, 2)) * absoluteDifferenceX  + tileTravelCost * (absoluteDifferenceY - absoluteDifferenceX));
        
        travelCost = absoluteDifferenceX > absoluteDifferenceY ? xBigger : yBigger;
        return travelCost;
        
    }
}