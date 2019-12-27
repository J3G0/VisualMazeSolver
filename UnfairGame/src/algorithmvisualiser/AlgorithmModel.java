/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithmvisualiser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Sebastiaan
 */

//Pseudo code from https://en.wikipedia.org/wiki/A*_search_algorithm#Pseudocode was used for thinking purposes

public class AlgorithmModel
{
    //Amount of nodes in x direction
    final private int ROWS_X = 25;
    
    //Amount of nodes in y direction
    final private int ROWS_Y = 15;
    
    //Model has a list of nodes, each node representing a rectangle in the view.  
    private AStarVertice[][] nodes = new AStarVertice[ROWS_X][ROWS_Y];
    
    //Nodes that are still open to discover
    private List<AStarVertice> openSet = new ArrayList<>();
    
    //Nodes that are no longer a valid option
    private List<AStarVertice> closedSet = new ArrayList<>();
    
    //Nodes that are a neightbour to the current node
    private List<AStarVertice>neighbourNodes = new ArrayList<>();
    
    //Nodes that are the path that the algorithm chose
    private List<AStarVertice> calculatedPath = new ArrayList<>();
    
    //Startnode (begin point)
    private AStarVertice startNode;
    
    //Endnode (end point)
    private AStarVertice endNode;
     
    public AlgorithmModel() 
    {
        for (int i = 0; i < ROWS_X ; i++)
        {
            for (int j = 0; j < ROWS_Y ;j++)
            {
                nodes[i][j] = new AStarVertice(i,j);
            }
        }
        nodes[5][5].setVerticeType(VerticeType.START);
        nodes[ROWS_X - 1][ROWS_Y - 1].setVerticeType(VerticeType.END);
        
        nodes[2][2].setVerticeType(VerticeType.SOLID);
        nodes[2][3].setVerticeType(VerticeType.SOLID);
        
        startNode = null;
        endNode = null;
        updateBeginPoints();
        findPath(startNode, endNode);
        
    } 

    public AStarVertice[][] getNodes()
    {
        //return nodes[0][0];
        return nodes;
    }
    
    public AStarVertice getStartNode() { return startNode; }
    public AStarVertice getEndNode() { return endNode; }
    
    // Function to determine who is start node and endnode
    // If no found, throw error?
    public void updateBeginPoints()
    {
        List <AStarVertice> nodesList = Arrays.stream(nodes).flatMap(Arrays::stream).collect(Collectors.toList()); 
        for(int i = 0; i < nodesList.size(); i++)
        {
            switch(nodesList.get(i).getVerticeType())
            {
                case START:
                    startNode = nodesList.get(i);
                    break;
                case END:
                    endNode = nodesList.get(i);
                    break;
                default:
                    break;
            }      
        }
        
        if(startNode == null || endNode == null)
        {
            throw new NullPointerException("NPE WARNING: Startnode or endnode is zero!");
        }
    }
    
    
    public void findPath(AStarVertice startNode, AStarVertice endNode)
    {
        AStarVertice start = startNode;
        AStarVertice end = endNode;
        openSet.clear();
        closedSet.clear();
        calculatedPath.clear();
        
        openSet.add(start);
        
        neighbourNodes = getNeighbourVertices(start);
        AStarVertice currentNode = openSet.get(0);
        //Loop over neighbours to set each cost
        for (AStarVertice n : neighbourNodes)
        {
            n.setVerticeType(VerticeType.SOLID);
            
            if (!closedSet.contains(neighbourNodes))
            {
                getTravelCost(n, currentNode);
            }
                
        }
        
        //Get costs from all neighbours
        
        
        //Set lowest cost node as currentNode
        
    }
    
    //Returns the neighbours surrounding the current node (except the current node itself)
    public List<AStarVertice> getNeighbourVertices (AStarVertice currentNode)
    {
        List<AStarVertice> neighbours = new ArrayList<>();
        
        //Offset coords surrounding the current  node
        
        double xCorrection = currentNode.getPositionX();
        double yCorrection = currentNode.getPositionY();
        
        for (int xCoord = -1; xCoord <= 1; xCoord++)
        {
            for (int yCoord = -1; yCoord <= 1; yCoord++)
            {
                AStarVertice neighbourNode = new AStarVertice(xCoord, yCoord);
                
                //Correction needed for currentNode offset loop
                neighbourNode.setPositionX(xCorrection - xCoord);
                neighbourNode.setPositionY(yCorrection - yCoord);
                //System.out.println(neighbourNode.getPositionX() + " , " + neighbourNode.getPositionY());
                
                if (!isNodeOnSameCoord(currentNode, neighbourNode))
                {

                    if(neighbourNode.getPositionX() >= 0 && neighbourNode.getPositionY() >= 0)
                    {
                        //Idea: Add actual node instead of neighbournode?
                        // neighbours.add(neighbourNode);
                        int posX = (int)neighbourNode.getPositionX();
                        int posY = (int)neighbourNode.getPositionY();
                        neighbours.add(nodes[posX][posY]);
                    }

                }
            }
            
        }
        
        return neighbours;
    }
    
    public boolean isNodeOnSameCoord(AStarVertice nodeA, AStarVertice nodeB)
    {
        return (nodeA.getPositionX() == nodeB.getPositionX() && nodeA.getPositionY() == nodeB.getPositionY());
    }
    
    
    public double getTravelCost(AStarVertice nodeA, AStarVertice nodeB)
    {
        double travelCost = 0;
        final double tileTravelCost = 1;
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
        
        if(absoluteDifferenceX > 0 && absoluteDifferenceY > 0)
        {
            double diagonalCost = absoluteDifferenceY * Math.sqrt(Math.pow(tileTravelCost, 2) + Math.pow(tileTravelCost, 2));
            double horizontalCost = (Math.abs (absoluteDifferenceX - absoluteDifferenceY)) * 1; 
            travelCost = diagonalCost + horizontalCost;
        }
        
        if(absoluteDifferenceX == 0)
        {
            travelCost = absoluteDifferenceY *  tileTravelCost;
        }
        
        if(absoluteDifferenceY == 0)
        {
            travelCost = absoluteDifferenceX *  tileTravelCost;
        }
        
        System.out.println(travelCost);
        return travelCost;
        
    }
}
