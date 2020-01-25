/*
 * @author Sebastiaan Vanspauwen
 * @author Jeffrey Gorissen
 * @teacher Kris Aerts
 */
package algorithmvisualiser.algorithmtype;

import algorithmvisualiser.AlgorithmModel;
import algorithmvisualiser.AlgorithmState;
import algorithmvisualiser.vertice.MovementDirection;
import algorithmvisualiser.vertice.Vertice;
import algorithmvisualiser.vertice.VerticeType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Klasse voor het AlwaysGoLeft algoritme.
 * Erft van AlgoritmModel.
 * Bevat alle methodes inherent aan AlwaysGoLeft.
 */
public class AlwaysGoLeft extends AlgorithmModel
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
    public AlwaysGoLeft()
    {
        this.algorithmName = "AlwaysGoLeft";    
    }
    
    /**
     * AlwaysGoLeft constructor met map parameter
     * @param map 2D array van Vertice met map data
     */
    public AlwaysGoLeft(Vertice[][] map)
    {
        super(map);
        this.algorithmName = "AlwaysGoLeft";  
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
            neighbours = getNeighbourVertices(currentNode, false, false);

            possibleDirections = createDirectionList(currentNode);
            
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
    /**
     * Methode die iterate() oproept totdat de state 'solved' of 'unsolvable' bereikt is.
     * Als hij 'solved' is dan roept deze drawTakenPath() op.
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
