/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithmvisualiser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author jeffr
 */

// naar rechts gaan -> naar omhooh
// naar omhoog gaan -> naar links
// naar links gaan -> naar onder
// naar onder gaan -> naar rechts
public class AlwaysGoLeft extends AlgorithmModel
{  
    List<MovementDirection> possibleDirections = new ArrayList<>();
    MovementDirection currentDirection = null;
    public AlwaysGoLeft()
    {
        this.algorithmName = "AlwaysGoLeft";    
    }
    
    public AlwaysGoLeft(Vertice[][] map)
    {
        super(map);
    }
    
    @Override
    public void iterate()
    { 
        if(getAlgorithmState() == AlgorithmState.FINISHED)
        {
            drawTakenPath();
        }
        
        if (getAlgorithmState() == AlgorithmState.SOLVING)
        {
            updateModelState();
            increaseIterations();
            if(currentNode.getVerticeType() == VerticeType.BASIC)
            {
                currentNode.setVerticeType(VerticeType.HEAD);
                
                if(currentNode.getParent() != null && currentNode.getParent() != startNode)
                {
                   currentNode.getParent().setVerticeType(VerticeType.TRAVERSED); 
                }
            }
            neighbours = getNeighbourVertices(currentNode, false);

            possibleDirections = createDirectionList();
            
            System.out.println(Arrays.toString(possibleDirections.toArray()));

            //Get first possible direction if not empty
            if(!possibleDirections.isEmpty())
            {
                if(possibleDirections.contains(MovementDirection.LEFT))
                {
                    System.out.println("Contains left!");
                    currentDirection = MovementDirection.LEFT;
                }
                else
                {
                    currentDirection = possibleDirections.get(0);
                }
                //System.out.println("Chose direction: " + currentDirection);
            }
            //If possible directions is empty, set currentnode to null (so it goes to parent)
            else
            {
                currentDirection = null;
            }   

            currentNode = getNodeAtDirection(currentDirection);

            //If currentnode is null that means currentNode is the startnode (startnode has no parent)   
        }
    }
    
    @Override
    public void finish()
    {
        while(getAlgorithmState() == AlgorithmState.SOLVING)
        {
            iterate();
        }
        drawTakenPath();
    }
    
    public Vertice getNodeAtDirection(MovementDirection currentDirection)
    {
        Vertice nodeAtDirection = null;
        
        if(currentDirection == null)
        {
            Vertice parentNode = currentNode.getParent();
            
            if(parentNode == null)
            {
                return null;
            }
            else
            {
                return parentNode;
            }
        }
        else
        {
            int posX = (int) currentNode.getPositionX();
            int posY = (int) currentNode.getPositionY();
            
            switch(currentDirection)
            {
                case UP:
                    nodeAtDirection = getNodeAtLocation(posX, posY - 1);
                    break;
                    
                case DOWN:
                    nodeAtDirection = getNodeAtLocation(posX, posY + 1);
                    break;
                    
                case RIGHT:
                    nodeAtDirection = getNodeAtLocation(posX + 1, posY);
                    break;
                    
                case LEFT:
                    nodeAtDirection = getNodeAtLocation(posX - 1, posY);
                    break;       
                    
            }
        }
        nodeAtDirection.setParent(currentNode);
        return nodeAtDirection;
    }
    
    public List<MovementDirection> createDirectionList()
    {
        possibleDirections.clear();
        int posX = (int) currentNode.getPositionX();
        int posY = (int) currentNode.getPositionY();

        for(Vertice n : neighbours)
        {
            int nX = (int) n.getPositionX();
            int nY = (int) n.getPositionY();
            
            //System.out.println("Checking neighbour at : " + n.getLocation() );

            //check top left
            boolean topNeighbour = (posX == nX) && (posY - 1 == nY);
            //Check top right
            boolean rightNeighbour =  (posX + 1 == nX) && (posY == nY);
            //Check bottom right
            boolean bottomNeighbour =  (posX == nX) && (posY + 1 == nY);
            //Check bottom left
            boolean leftNeighbour = (posX - 1 == nX) && (posY == nY);

            if(leftNeighbour)
            {
                possibleDirections.add(MovementDirection.LEFT);
            }
            else if(bottomNeighbour)
            {
                possibleDirections.add(MovementDirection.DOWN);
            }
            else if(topNeighbour)
            {
                possibleDirections.add(MovementDirection.UP);
            }
            else if(rightNeighbour)
            {
               possibleDirections.add(MovementDirection.RIGHT);
            }
            
        }
        for(MovementDirection md : possibleDirections)
        {
            System.out.println(md);
        }
        return possibleDirections;
    }
}
