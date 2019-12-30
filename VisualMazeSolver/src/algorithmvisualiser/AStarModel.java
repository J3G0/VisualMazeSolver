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
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 *
 * @author Sebastiaan
 */

// Pseudo code from "https://en.wikipedia.org/wiki/A*_search_algorithm#Pseudocode" was used for thinking purposes
// Also the tutorial from Sebastian Lague helped me understand the basic concept of A*
// Source: https://github.com/SebLague/Pathfinding/blob/master/Episode%2003%20-%20astar/Assets/Scripts/Pathfinding.cs

public class AStarModel
{
    //Amount of nodes in x direction
    final protected int ROWS_X = 25;
    
    //Amount of nodes in y direction
    final protected int ROWS_Y = 15;
    
    //Model has a list of nodes, each node representing a rectangle in the view.  
    final private AStarVertice[][] nodes = new AStarVertice[ROWS_X][ROWS_Y];
    
    //Nodes that are still open to discover
    final private List<AStarVertice> openSet = new ArrayList<>();
    
    //Nodes that are no longer a valid option
    final private List<AStarVertice> closedSet = new ArrayList<>();
    
    //Nodes that are a neightbour to the current node
    private List<AStarVertice>neighbourNodes = new ArrayList<>();
    
    //Nodes that are the path that the algorithm chose
    final private List<AStarVertice> calculatedPath = new ArrayList<>();
    
    //Startnode (begin point)
    private AStarVertice startNode;
    
    //Endnode (end point)
    private AStarVertice endNode;
    
    //Node that is currently being calculated
    private AStarVertice currentNode;
    
    private boolean endNodeReached;
    
    protected ArrayList<AStarVertice> edittedNodes = new ArrayList<>();
     
    public AStarModel() 
    {
        edittedNodes.clear();
        openSet.clear();
        closedSet.clear();
        calculatedPath.clear();
        for (int i = 0; i < ROWS_X ; i++)
        {
            for (int j = 0; j < ROWS_Y ;j++)
            {
                nodes[i][j] = new AStarVertice(i,j);
            }
        }
        nodes[0][0].setVerticeType(VerticeType.START);
        nodes[5][5].setVerticeType(VerticeType.END);
        
        startNode = null;
        endNode = null;
        endNodeReached = false;
        updateSets();  
    } 

    public AStarVertice[][] getNodes()
    {
        //return nodes[0][0];
        return nodes;
    }
    
    public AStarVertice getNodeAtLocation(int x, int y)
    {
        return nodes[x][y];
    }
    
    public AStarVertice getStartNode() { return startNode; }
    public AStarVertice getEndNode() { return endNode; }
    
    // Function to determine who is start node and endnode
    // If no found, throw error?
    public void updateSets()
    {
        List <AStarVertice> nodesList = Arrays.stream(nodes).flatMap(Arrays::stream).collect(Collectors.toList()); 
        for(int i = 0; i < nodesList.size(); i++)
        {
            switch(nodesList.get(i).getVerticeType())
            {
                case START:
                    System.out.println("Setting start node");
                    startNode = nodesList.get(i);                      
                    if(startNode != null)
                    {
                        openSet.clear();
                        openSet.add(startNode);
                    }
                    
                    break;
                case END:
                    endNode = nodesList.get(i);
                    break;
                case SOLID:
                    closedSet.add(nodesList.get(i));
                
                default:
                    break;
            }      
        }
        
        if(startNode == null || endNode == null)
        {
            throw new NullPointerException("NPE WARNING: Startnode or endnode is zero!");
        }
    }
    
    
    
    public void findPath()
    {
        //todo: needs a better way to detect final node reached
        if(currentNode == endNode || endNodeReached)
        {
            endNodeReached = true;
            drawTakenPath();
            System.out.println("Do something here");
        }
        
        else
        {
            System.out.println("ey");
            neighbourNodes.clear();

            currentNode = openSet.get(0);

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

            for(AStarVertice v : closedSet)
            {
                if(v.getVerticeType() != VerticeType.SOLID)
                {
                    v.setVerticeType(VerticeType.TRAVERSED);
                }
            }

            neighbourNodes = getNeighbourVertices(currentNode);
            //Loop over neighbours to set each cost
            for (AStarVertice n : neighbourNodes)
            {
                //n.setVerticeType(VerticeType.NEIGHBOUR);
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
                neighbourNode.setPositionX(Math.min( xCorrection - xCoord, ROWS_X- 1));
                neighbourNode.setPositionY(Math.min( yCorrection - yCoord, ROWS_Y- 1));
                //System.out.println(neighbourNode.getPositionX() + " , " + neighbourNode.getPositionY());
                
                if (!isNodeOnSameCoord(currentNode, neighbourNode))
                {

                    if(neighbourNode.getPositionX() >= 0 && neighbourNode.getPositionY() >= 0)
                    {
                        //Idea: Add actual node instead of neighbournode?
                        // neighbours.add(neighbourNode);
                        int posX = (int)neighbourNode.getPositionX();
                        int posY = (int)neighbourNode.getPositionY();
                        if((nodes[posX][posY].getVerticeType() != VerticeType.SOLID))
                        {
                            neighbours.add(nodes[posX][posY]);
                        }
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
    
    public AStarVertice getCurrentNode()
    {
        return currentNode;
    }
    
    
    public void drawTakenPath()
    {
        while(currentNode != startNode)
        {
            currentNode.setVerticeType(VerticeType.PARENT);
            currentNode = currentNode.getParent();
        }
        currentNode.setVerticeType(VerticeType.PARENT);
    }
}