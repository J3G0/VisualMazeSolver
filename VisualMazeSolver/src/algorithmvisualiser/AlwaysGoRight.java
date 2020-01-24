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
    public AlwaysGoRight()
    {
        this.algorithmName = "AlwaysGoRight";    
    }
    
    /**
     * AlwaysGoRight constructor met map parameter
     * @param map 2D array van Vertice met map data
     */
    public AlwaysGoRight(Vertice[][] map)
    {
        super(map);
        this.algorithmName = "AlwaysGoRight";  
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
            
            if(currentNode.getVerticeType() == VerticeType.BASIC || currentNode.getVerticeType() == VerticeType.TRAVERSED || currentNode.getVerticeType() == VerticeType.HEAD)
            {
                currentNode.setVerticeType(VerticeType.HEAD);
                System.out.println("Before: " + currentNode.getVerticeType() + " " + currentNode.getParent().getVerticeType());
                if(currentNode.getParent() != null && currentNode.getParent() != startNode)
                {
                    switch(currentNode.getVerticeType())
                    {
                        case HEAD:
                            currentNode.getParent().setVerticeType(VerticeType.TRAVERSED); 
                            break;

                    }
                    System.out.println("After: " + currentNode.getVerticeType() + " " + currentNode.getParent().getVerticeType());
                }
            }
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

            if(topNeighbour)
            {
                possibleDirections.add(MovementDirection.UP);
                System.out.println("Movement up added");
            }
            else if(rightNeighbour)
            {
                possibleDirections.add(MovementDirection.RIGHT);
                System.out.println("Movement right added");
            }
            else if(bottomNeighbour)
            {
                possibleDirections.add(MovementDirection.DOWN);
                System.out.println("Movement down added");
            }
            else if(leftNeighbour)
            {
                possibleDirections.add(MovementDirection.LEFT);
                System.out.println("Movement up added");
            }
        }
        return possibleDirections;
    }
}
