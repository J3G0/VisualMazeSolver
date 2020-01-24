/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithmvisualiser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Jeffrey
 */
public class DrunkModel extends AlgorithmModel
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
     * AlwaysGoLeft constructor
     */
    public DrunkModel()
    {
        this.algorithmName = "Drunk";    
    }
    
    /**
     * AlwaysGoLeft constructor met map parameter
     * @param map 2D array van Vertice met map data
     */
    public DrunkModel(Vertice[][] map)
    {
        super(map);
        this.algorithmName = "Drunk";  
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

            possibleDirections = createDirectionList(currentNode);
            
            System.out.println(Arrays.toString(possibleDirections.toArray()));

            //Get first possible direction if not empty
            if(!possibleDirections.isEmpty())
            {
                Random r = new Random();
                currentDirection = possibleDirections.get(r.nextInt(possibleDirections.size()));
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
