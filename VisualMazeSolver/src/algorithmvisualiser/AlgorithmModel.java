/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithmvisualiser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    protected Vertice startNode; 
    
    //Endnode of the algorithm
    protected Vertice endNode;
    
    //current node that is looping
    protected Vertice currentNode;
    
    //A list of nodes that was already considered
    protected List<Vertice> closedSet;
    
    //A list of nodes that was not considered
    protected List<Vertice> openSet;
    
    //A list of neighbour nodes surrounding the current node
    protected List<Vertice> neighbours;
    
    //Model has a list of nodes, each node representing a rectangle in the view.  
    final protected Vertice[][] nodes = new Vertice[ROWS_X][ROWS_Y];
    
    protected String algorithmName;
    
    public AlgorithmModel()
    {
        startNode = null;
        endNode = null;
        currentNode = null;
        algorithmName = "BASE_CLASS";
        //Bad practice to do new ArrayList? is .clear() better?
        
        closedSet = new ArrayList<>();
        openSet = new ArrayList<>();
        neighbours = new ArrayList<>();
        
        for (int i = 0; i < ROWS_X ; i++)
        {
            for (int j = 0; j < ROWS_Y ;j++)
            {
                nodes[i][j] = new Vertice(i,j);
            }
        } 
        nodes[0][0].setVerticeType(VerticeType.START);
        nodes[5][5].setVerticeType(VerticeType.END);
        updateSets();  
        currentNode = startNode;
    }
    
    public Vertice[][] getNodes()
    {
        return nodes;
    }
    
    //Returns the neighbours surrounding the current node (except the current node itself)
    public List<Vertice> getNeighbourVertices (Vertice currentNode, boolean allowDiagonal)
    {
        List<Vertice> neighbours = new ArrayList<>();
        
        //Offset coords surrounding the current  node
        
        double xCorrection = currentNode.getPositionX();
        double yCorrection = currentNode.getPositionY();
        
        for (int xCoord = -1; xCoord <= 1; xCoord++)
        {
            for (int yCoord = -1; yCoord <= 1; yCoord++)
            {
                Vertice neighbourNode = new Vertice(xCoord, yCoord);
                
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
                        
                        if ( (nodes[posX][posY].getVerticeType() == VerticeType.BASIC) || nodes[posX][posY]  == endNode)
                        {
                            neighbours.add(nodes[posX][posY]);
                        }
                    }

                }
            }       
        }   
        
        //Check for diagonals (not the cleanest code but it works)
        if(!allowDiagonal)
        {
            List<Vertice> neighboursToRemove = new ArrayList<>();
            int posX = (int)currentNode.getPositionX();
            int posY = (int)currentNode.getPositionY();
            for(Vertice n : neighbours)
            {
                int nX = (int) n.getPositionX();
                int nY = (int) n.getPositionY();
                
                //check top left
                boolean topLeftNeighbour = posX >= 1 && posY >= 1 && (posX - 1 == nX) && (posY - 1 == nY);
                //Check top right
                boolean topRightNeighbour = posX >= 1 && posY >= 1 && (posX + 1 == nX) && (posY - 1 == nY);
                //Check bottom right
                boolean bottomRightNeighbour = posX < ROWS_X && posY < ROWS_Y && (posX + 1 == nX) && (posY + 1 == nY);
                //Check bottom left
                boolean bottomLeftNeighbour = posX < ROWS_X && posY < ROWS_Y && (posX - 1 == nX) && (posY + 1 == nY);
                
                
                if(topLeftNeighbour || topRightNeighbour  || bottomRightNeighbour || bottomLeftNeighbour)
                {
                    neighboursToRemove.add(n);
                }    
            }
            // https://stackoverflow.com/questions/10431981/remove-elements-from-collection-while-iterating
            neighbours.removeAll(neighboursToRemove);
        }
        return neighbours;
    }   
    
    public boolean isNodeOnSameCoord(Vertice nodeA, Vertice nodeB)
    {
        return (nodeA.getPositionX() == nodeB.getPositionX() && nodeA.getPositionY() == nodeB.getPositionY());
    }  
    
    
    //Override this method in subclasses, this is what progresses algorithms.
    public void iterate()
    {
        
    }
    
    //Override in subclass to finish
    public void finish()
    {
        
    }
    
    public void updateSets()
    {
        List <Vertice> nodesList = Arrays.stream(nodes).flatMap(Arrays::stream).collect(Collectors.toList()); 
        for(int i = 0; i < nodesList.size(); i++)
        {
            switch(nodesList.get(i).getVerticeType())
            {
                case START:
                    //System.out.println("Setting start node");
                    startNode = nodesList.get(i);                                     
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
        
    public Vertice getNodeAtLocation(int x, int y)
    {
        x = Math.min(x, ROWS_X - 1);
        y = Math.min(y, ROWS_Y - 1);
        
        return nodes[x][y];
    }
    
    public Vertice getStartNode() { return startNode; }
    public Vertice getEndNode() { return endNode; }
}