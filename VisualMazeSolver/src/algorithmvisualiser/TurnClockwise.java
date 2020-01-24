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
        this.algorithmName = "AlwaysGoRight";    
    }
    
    /**
     * AlwaysGoRight constructor met map parameter
     * @param map 2D array van Vertice met map data
     */
    public TurnClockwise(Vertice[][] map)
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

            possibleDirections = createDirectionList(currentNode);

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
}
