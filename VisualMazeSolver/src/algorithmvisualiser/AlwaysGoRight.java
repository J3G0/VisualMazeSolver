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
public class AlwaysGoRight extends AlgorithmModel
{  
    List<MovementDirection> possibleDirections = new ArrayList<>();
    MovementDirection currentDirection = null;
    public AlwaysGoRight()
    {
        this.algorithmName = "AlwaysGoRight";    
        System.out.println(algorithmName);
    }
     
    
    @Override
    public void iterate()
    {        
        increaseIterations();
        System.out.println(getAmountOfIterations());
        this.algorithmName = "AlwaysGoRight " + getAmountOfIterations(); 
        currentNode.setVerticeType(VerticeType.TRAVERSED);
        neighbours = getNeighbourVertices(currentNode, false);
        
        possibleDirections = createDirectionList();
        
        //System.out.println("/////");
        
        //Get first possible direction if not empty
        if(!possibleDirections.isEmpty())
        {
            currentDirection = possibleDirections.get(0);
            //System.out.println("Chose direction: " + currentDirection);
        }
        //If possible directions is empty, set currentnode to null (so it goes to parent)
        else
        {
            currentDirection = null;
        }   
        
        currentNode = getNodeAtDirection(currentDirection);
        
        //If currentnode is null that means currentNode is the startnode (startnode has no parent)
        if(currentNode == null)
        {
             setAlgorithmState(AlgorithmState.UNSOLVABLE);
        }
        
    }
    
    @Override
    public void finish()
    {
        while(!hasReachedEnd())
        {
            iterate();
        }
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
                    
                case LEFT:
                    nodeAtDirection = getNodeAtLocation(posX - 1, posY);
                    break;
                    
                case RIGHT:
                    nodeAtDirection = getNodeAtLocation(posX + 1, posY);
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

            if(topNeighbour)
            {
                possibleDirections.add(MovementDirection.UP);
            }
            else if(rightNeighbour)
            {
                possibleDirections.add(MovementDirection.RIGHT);
            }
            else if(bottomNeighbour)
            {
                possibleDirections.add(MovementDirection.DOWN);
            }
            else if(leftNeighbour)
            {
                possibleDirections.add(MovementDirection.LEFT);
            }
        }
        return possibleDirections;
    }
}
