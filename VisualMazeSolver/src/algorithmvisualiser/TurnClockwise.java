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
 * @author Jeffrey
 */

public class TurnClockwise extends AlgorithmModel
{  
    /**
     * Lijst van richtingen waarneer de Vertice kan bewegen
     */
    List<MovementDirection> possibleDirections = new ArrayList<>();
    
    /**
     * De huidige richting waar de Vertice naar toe gaat
     */
    MovementDirection currentDirection = null;
    
    /**
     * AlwaysGoRight constructor
     */
    public TurnClockwise()
    {
        this.algorithmName = "TurnClockwise";    
    }
    
    /**
     * AlwaysGoRight constructor met map parameter
     * @param map 2D array van Vertice met map data
     */
    public TurnClockwise(Vertice[][] map)
    {
        super(map);
        this.algorithmName = "TurnClockwise";  
    }    
    
    /**
     * Iterate functie van het model (1 iteratie van het model)
     */
    @Override
    public void iterate()
    { 
        if(getAlgorithmState() == AlgorithmState.SOLVED)
        {
            drawTakenPath();
        }
        
        if (getAlgorithmState() == AlgorithmState.SOLVING)
        {
            updateModelState();
            increaseIterations();
            //System.out.println("this is the turnclockwise algoritm");
            if(currentNode.getVerticeType() == VerticeType.BASIC)
            {
                currentNode.setVerticeType(VerticeType.HEAD);
                
                if(currentNode.getParent() != null && currentNode.getParent() != startNode)
                {
                   currentNode.getParent().setVerticeType(VerticeType.TRAVERSED); 
                }
            }
            neighbours = getNeighbourVertices(currentNode, false);
            
            //Deze crashed
            //possibleDirections = createDirectionList();
            
            possibleDirections = createDirectionList(currentNode);

            //System.out.println("/////");

            //Get first possible direction if not empty
            if(!possibleDirections.isEmpty())
            {
                currentDirection = possibleDirections.get(0);
                System.out.println("Chose direction: " + currentDirection);
            }
            //If possible directions is empty, set currentnode to null (so it goes to parent)
            else
            {
                currentDirection = null;
                System.out.println("NO DIRECTION");
            }   
            currentNode = getNodeAtDirection(currentDirection);

            //If currentnode is null that means currentNode is the startnode (startnode has no parent)   
        }
    }
    
    /**
     * Finish functie: roept iterate() op tot opgelost of vastgelopen
     */
    @Override
    public void finish()
    {
        while(getAlgorithmState() == AlgorithmState.SOLVING)
        {
            iterate();
        }
        drawTakenPath();
    }
    
     /**
     * Kijk van de currentNode in welke richting deze kan bewegen
     * @return List<MovementDirection> een lijst van mogelijke richtingen
     */    
    @Override
    public List<MovementDirection> createDirectionList(Vertice currentNode)
    {
        List<MovementDirection> possibleDirections = new ArrayList<>();
        System.out.println("CREATE");
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

            if(currentDirection == null)
            {
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
            
            // X-> |
            //    X|
            if(currentDirection == MovementDirection.RIGHT)
            {
                System.out.println("Direction right");
                //If there is a block on your path go down (clockwise)
                if(!rightNeighbour)
                {
                    possibleDirections.add(MovementDirection.DOWN);
                }
                //Cant go down? Go up
                else if(!bottomNeighbour)
                {
                    possibleDirections.add(MovementDirection.UP);
                }
                //Cant go up? Go back
                else if(!topNeighbour)
                {
                    possibleDirections.add(MovementDirection.LEFT);
                }
            }

            //      X
            //      ||
            //      \/
            // X <- __
            else if(currentDirection == MovementDirection.DOWN)
            {
                System.out.println("Direction down");
                //If there is a block on your path go left (clockwise)
                if(!bottomNeighbour)
                {
                    possibleDirections.add(MovementDirection.LEFT);
                }
                else if(!leftNeighbour)
                {
                    possibleDirections.add(MovementDirection.RIGHT);
                }
                else if(!rightNeighbour)
                {
                    possibleDirections.add(MovementDirection.UP);
                }
            }

            //      X
            //      /\
            //      ||
            //    |   <-  X
            else if(currentDirection == MovementDirection.LEFT)
            {
                System.out.println("Direction left");
                //If there is a block on your path go up (clockwise)
                if(!leftNeighbour)
                {
                    possibleDirections.add(MovementDirection.UP);
                }
                else if(!topNeighbour)
                {
                    possibleDirections.add(MovementDirection.DOWN);
                }
                else if(!bottomNeighbour)
                {
                    possibleDirections.add(MovementDirection.RIGHT);
                }
            }

            //  __
            //      -> X
            //  /\
            //  ||
            //  X
            else if(currentDirection == MovementDirection.UP)
            {
                System.out.println("Direction up");
                //If there is a block on your path go right(clockwise)
                if(!topNeighbour)
                {
                    possibleDirections.add(MovementDirection.RIGHT);
                }
                else if(!rightNeighbour)
                {
                    possibleDirections.add(MovementDirection.LEFT);
                }
                else if(!leftNeighbour)
                {
                    possibleDirections.add(MovementDirection.DOWN);
                }
            }
        }
        return possibleDirections;
    } 
}
