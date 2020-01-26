/*
 * @author Sebastiaan Vanspauwen
 * @author Jeffrey Gorissen
 * @teacher Kris Aerts
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

// Pseudo code from "https://en.wikipedia.org/wiki/A*_search_algorithm#Pseudocode" was used for thinking purposes
// Also the tutorial from Sebastian Lague helped me understand the basic concept of A*
// Source: https://github.com/SebLague/Pathfinding/blob/master/Episode%2003%20-%20astar/Assets/Scripts/Pathfinding.cs
// Source: https://www.youtube.com/watch?v=-L-WgKMFuhE&list=PLFt_AvWsXl0cq5Umv3pMC9SPnKjfp9eGW&index=1

/**
 * Klasse voor het AStar algoritme.
 * Erft van AlgoritmModel.
 * Bevat alle methodes inherent aan AStar.
 */
public class AStar extends AlgorithmModel
{
    /**
     * AStarModel constructor
     */
    public AStar() 
    {
        this.algorithmName = "A Star";
        openSet.clear();
        openSet.add(currentNode);
    } 
  
    /**
     * AStarModel constructor met map parameter
     * @param map 2D Vertice map die de Vertice data bevat.
     */
    public AStar(Vertice[][] map) 
    {
        super(map);
        this.algorithmName = "A Star";  
        openSet.clear();
        openSet.add(currentNode);     
    } 
    
    /**
     * iterate Functie die één keer over het algoritme itereert.
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
     * Methode die iterate() oproept totdat de state 'solved' of 'unsolvable' bereikt is.
     * Als hij 'solved' is dan roept deze drawTakenPath() op.
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
     * Geeft de travelcost (reiskost) terug op basis van de afstand tot de startNode en de endNode.
     *  Stel nodeA ligt op (0,0) en nodeB ligt op (2,2);
     *  NodeA moet 2 maal schuin gaan (Pythagoras) of 2 keer horizontaal and twee keer verticaal
     *  Neem de afstand 1, de verticale afstand zou sqtr(1^2 + 1^2) = 1.41 zijn.
     *  De laagste kost afstand is 2 * 1.41 = 2.82 of 4 * 1 = 4. (2.82)
     *  
     * Wat als de endNode op dezelfde lijn ligt als de startNode?
     *  NodeA (1,0) , nodeB(4,0)
     *  Er is geen nood voor Pythagoras nu (geen x of y verschil), de kost is dan 3 * 1 = 1.
     *  
     *  We moeten het absolute verschil tussen x en y van de 2 nodes (nodeA, nodeB) berekenen.
     * 
     * @param nodeA De eerste Vertice (Vertice van wie de kost wordt berekend)
     * @param nodeB De tweede Vertice (startNode of endNode)
     * @return travelCost, het gewicht of de kost van de reisafstand.
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