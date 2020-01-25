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
import java.util.ArrayList;
import java.util.List;

/**
 * Klasse voor het TurnClockwise algoritme.
 * Erft van AlgoritmModel.
 * Bevat alle methodes inherent aan TurnClockwise.
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
    
    boolean movedRight = false;
    boolean movedDown = false;
    boolean movedLeft = false;
    boolean movedUp = false;
    
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
        if(getAmountOfIterations() > 200)
        {
            setAlgorithmState(AlgorithmState.UNSOLVABLE);
            updateModelState();
        }
        
        if (getAlgorithmState() == AlgorithmState.SOLVING)
        {
            updateModelState();
            increaseIterations();           
            updateCurrentNode();
            neighbours = getNeighbourVertices(currentNode, false, true);
            possibleDirections = createDirectionList(currentNode);
            //Get first possible direction if not empty
            if (!possibleDirections.isEmpty())
            {
                createClockMovement(possibleDirections);
            }            
            //If possible directions is empty, set currentnode to null (so it goes to parent)
            else
            {
                currentDirection = null;
            }   
            currentNode = getNodeAtDirection(currentDirection);
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
    }
    
    /**
     * Methode die de mogelijke richtingen voor het algoritme berekend.
     * Indien een richting voldoet aan de voorwaarden wordt deze in de lijst gestoken.
     * @param possibleDirections, lijst met de mogelijke richtingen.
     */
    public void createClockMovement(List<MovementDirection> possibleDirections)
    {   
        if(!movedRight)
        {
            //Maintain right while possible
            if(possibleDirections.contains(MovementDirection.RIGHT))
            {
                currentDirection = MovementDirection.RIGHT;   
            }
            //Else finish the right movement step
            else
            {
                if( currentDirection == MovementDirection.RIGHT )
                {
                    movedRight = true;
                }
                else
                {
                    currentDirection = null;
                }
            }
        }

        if(!movedDown && movedRight)
        {
            //Maintain right while possible
            if(possibleDirections.contains(MovementDirection.DOWN))
            {
                currentDirection = MovementDirection.DOWN;   
            }
            //Else finish the right movement step
            else
            {
                if( currentDirection == MovementDirection.DOWN )
                {
                    movedDown = true;
                }
                else
                {
                    currentDirection = null;
                }
            }
        }  

        if(!movedLeft && movedDown && movedRight)
        {
            //Maintain right while possible
            if(possibleDirections.contains(MovementDirection.LEFT))
            {
                currentDirection = MovementDirection.LEFT;   
            }
            //Else finish the right movement step
            else
            {
                if( currentDirection == MovementDirection.LEFT )
                {
                    movedLeft = true;
                }
                else
                {
                    currentDirection = null;
                }
            }
        } 

        if(!movedUp && movedLeft && movedRight && movedDown)
        {
            //Maintain right while possible
            if(possibleDirections.contains(MovementDirection.UP))
            {
                currentDirection = MovementDirection.UP;   
            }
            //Else finish the right movement step
            else
            {
                if( currentDirection == MovementDirection.UP )
                {
                    movedUp = true;
                }
                else
                {
                    currentDirection = null;
                }
            }
        }
        else
        {
            if ((movedDown && movedUp && movedLeft && movedRight) || possibleDirections.isEmpty() )
            {
                currentDirection = null;
                movedRight = false;
                movedDown = false;
                movedLeft = false;
                movedUp = false;                
            }
        }
    }
}
