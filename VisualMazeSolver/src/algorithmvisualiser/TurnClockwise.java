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

    public TurnClockwise()
    {
        this.algorithmName = "TurnClockwise";
    }
    
    /**
     * AlwaysGoLeft constructor met map parameter
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
                //met de clock mee / linksom
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
     * getNodeAtDirection functie die Node teruggeeft aan de meegegeven richting
     * Als currentDirection null is betekent dit dat er geen mogelijke stappen meer
     * zijn voor de Vertice, en moet de ouder meegegeven worden als volgende Node.
     * Indien er wel een currentDirection mogelijk is, geeft node in die richting mee.
     * @param currentDirection de node in de richting die meegegeven is.
     * @return 
     */
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
    
    /**
     * Kijk van de currentNode in welke richting deze kan bewegen
     * @return List<MovementDirection> een lijst van mogelijke richtingen
     */    
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
            
            // X-> |
            //    X|
            if(currentDirection == MovementDirection.RIGHT)
            {
                //If there is a block on your path go down (clockwise)
                if(rightNeighbour)
                {
                    possibleDirections.add(MovementDirection.DOWN);
                }
                //Cant go down? Go up
                else if(bottomNeighbour)
                {
                    possibleDirections.add(MovementDirection.UP);
                }
                //Cant go up? Go back
                else if(topNeighbour)
                {
                    possibleDirections.add(MovementDirection.LEFT);
                }
            }
            
            //      X
            //      ||
            //      \/
            // X <- __
            if(currentDirection == MovementDirection.DOWN)
            {
                //If there is a block on your path go left (clockwise)
                if(bottomNeighbour)
                {
                    possibleDirections.add(MovementDirection.LEFT);
                }
                else if(leftNeighbour)
                {
                    possibleDirections.add(MovementDirection.RIGHT);
                }
                else if(rightNeighbour)
                {
                    possibleDirections.add(MovementDirection.UP);
                }
            }
            
            //      X
            //      /\
            //      ||
            //    |   <-  X
            if(currentDirection == MovementDirection.LEFT)
            {
                //If there is a block on your path go up (clockwise)
                if(leftNeighbour)
                {
                    possibleDirections.add(MovementDirection.UP);
                }
                else if(topNeighbour)
                {
                    possibleDirections.add(MovementDirection.DOWN);
                }
                else if(bottomNeighbour)
                {
                    possibleDirections.add(MovementDirection.RIGHT);
                }
            }
            
            //  __
            //      -> X
            //  /\
            //  ||
            //  X
            if(currentDirection == MovementDirection.UP)
            {
                //If there is a block on your path go right(clockwise)
                if(topNeighbour)
                {
                    possibleDirections.add(MovementDirection.RIGHT);
                }
                else if(rightNeighbour)
                {
                    possibleDirections.add(MovementDirection.LEFT);
                }
                else if(leftNeighbour)
                {
                    possibleDirections.add(MovementDirection.DOWN);
                }
            }
            
        }
        for(MovementDirection md : possibleDirections)
        {
            System.out.println(md);
        }
        return possibleDirections;
    }
}
