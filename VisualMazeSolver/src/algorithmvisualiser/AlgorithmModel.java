/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithmvisualiser;

import java.awt.Point;
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
    /**
     * Het aantal rijen in de X-richting
     */
    final protected int ROWS_X = 25;
    
    /**
     * Het aantal rijen in de Y-richting
     */
    final protected int ROWS_Y = 15;
    
    /**
     * De node die aangegeven wordt als de start node
     */
    protected Vertice startNode; 
    
    /**
     * De node die aangegeven wordt als de end node
     */
    protected Vertice endNode;
    
    /**
     * De node die aangegeven wordt als huidige node (hoofd)
     */
    protected Vertice currentNode;
    
    /**
     * Arraylist die alle Nodes die niet meer beschouwd moeten worden bevat (SOLIDS, TRAVERSED)
     * Hangt van model af welke nodes hierin gestopt worden.
     */
    protected List<Vertice> closedSet;
    
    /**
     * Arraylist die alle mogelijke volgende stappen bevat
     */
    protected List<Vertice> openSet;
    
    /**
     * Arraylist die alle buren van currentNode bevat
     * @see currentNode
     */
    protected List<Vertice> neighbours;
    
    /**
     * 2D lijst van nodes die de map beschrijft met afmeting ROWS_X op ROWS_Y
     * @see ROWS_X
     * @see ROWS_Y
     */ 
    protected Vertice[][] nodes = new Vertice[ROWS_X][ROWS_Y];
    
    /**
     * De naam van het algorithme
     */
    protected String algorithmName;
    
    /**
     * De hoeveelheid van iteraties dat het algoritme al gedaan heeft
     */
    private int amountOfIterations;
    
    /**
     * Geeft de status van het algorithme aan (opgelost, bezig, niet oplosbaar)
     * @see AlgorithmState
     */
    private AlgorithmState algorithmState;
    
    
    /**
     * Construtor van het model
     */
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
        nodes[ROWS_X - 1][ROWS_Y - 1].setVerticeType(VerticeType.END);
        currentNode = startNode;
        updateSets();
        amountOfIterations = 0;
        algorithmState = AlgorithmState.SOLVING;
    }
    
    /**
     * Constructor van het model met map parameter
     * Op deze manier kan de map behouden worden bij een nieuw algoritme
     * @param map de huidige map in het spel
     */
    public AlgorithmModel(Vertice[][] map)
    {
        startNode = null;
        endNode = null;
        currentNode = null;
        algorithmName = "BASE_CLASS";
        closedSet = new ArrayList<>();
        openSet = new ArrayList<>();
        neighbours = new ArrayList<>();
        
        nodes = map;
        for (int i = 0; i < map.length ; i++)
        {
            for (int j = 0; j < map[i].length ;j++)
            {
                Vertice current = map[i][j];
                
                switch(current.getVerticeType())
                {
                    case SOLID:
                        nodes[i][j] = current;
                        break;
                        
                    case START:
                        startNode = current;
                        break;
                    case END:                      
                        endNode = current;
                        break;  
                        
                    case PARENT:
                    case NEIGHBOUR:
                    case HEAD:
                    case TRAVERSED:
                        current.setVerticeType(VerticeType.BASIC);
                        nodes[i][j] = current;
                        break;
                }
            }
        } 
        currentNode = startNode;
        amountOfIterations = 0;
        updateSets();
        algorithmState = AlgorithmState.SOLVING;
    }
    
    /**
     * Geeft alle nodes die in de map zijn terug
     * @return 2D array van nodes alle nodes in de map met afmeting ROWS_X op ROWS_Y
     */
    public Vertice[][] getNodes()
    {
        return nodes;
    }
    
    /**
     * Methode die alle buren geeft van de huidige node (currentNode)
     * @param currentNode de huidige node
     * @param allowDiagonal al dan niet diagonaal kijken naar buren
     * @return List van nodes die alle buren van currentNode bevat
     */
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
    
    /**
     * Kijken of een node dezelfde coördinaten heeft als een andere
     * @param nodeA de eerste node
     * @param nodeB de tweede node
     * @return true indien coördinaten gelijk zijn
     */
    public boolean isNodeOnSameCoord(Vertice nodeA, Vertice nodeB)
    {
        return (nodeA.getPositionX() == nodeB.getPositionX() && nodeA.getPositionY() == nodeB.getPositionY());
    }  
    
    
    /**
     * iterate methode die override wordt in subclasses
     */
    public void iterate()
    {
        
    }
    
    /**
     * finish methode die override wordt in subclasses
     */
    public void finish()
    {
        
    }
    
    /**
     * Methode die de sets update aan de hand van VerticeType van de nodes
     * @see VerticeType
     */
    public void updateSets()
    {
        List <Vertice> nodesList = Arrays.stream(nodes).flatMap(Arrays::stream).collect(Collectors.toList()); 
        for(int i = 0; i < nodesList.size(); i++)
        {
            switch(nodesList.get(i).getVerticeType())
            {
                case START:
                    if(startNode == null)
                    {
                        System.out.println("Setting start node");
                        startNode = nodesList.get(i);   
                    }
                    if(currentNode == null)
                    {
                        currentNode = startNode;
                    }
                    break;
                case END:
                    //System.out.println("Setting end node");
                    endNode = nodesList.get(i);
                    break;
                case SOLID:
                    //System.out.println("Setting solid node");
                    if(!closedSet.contains(nodesList.get(i)))
                    {
                        closedSet.add(nodesList.get(i));
                    }
                
                default:
                    break;
            }      
        }
        
        if(startNode == null || endNode == null)
        {
            throw new NullPointerException("NPE WARNING: Startnode or endnode is zero!");
        }
    }
        
    
    /**
     * Methode die node terug geeft op coordinaat (x,y)
     * @param x de x coördinaat
     * @param y de y coördinaat
     * @return de node die op dat coördinaat aanwezig is
     */
    public Vertice getNodeAtLocation(int x, int y)
    {
        x = Math.min(x, ROWS_X - 1);
        y = Math.min(y, ROWS_Y - 1);
        
        return nodes[x][y];
    }
    
    /**
     * Methode die node terug geeft op Point p
     * @param p het punt waarop gechecked wordt
     * @return de node die op dat punt aanwezig is
     * @see Point
     */
    public Vertice getNodeAtPoint(Point p)
    {
         int x = Math.min(p.x, ROWS_X - 1);
         int y = Math.min(p.y, ROWS_Y - 1);
        
        return nodes[x][y];       
    }
    
    /**
     * Methode die startnode teruggeeft
     * @return startNode de node waar het algoritme begint
     */
    public Vertice getStartNode() 
    { 
        return startNode; 
    }
    
    /**
     * Methode voor het setten van de startNode
     * @param startNode de node waarop het algorithme moet beginnen
     */
    public void setStartNode(Vertice startNode) 
    { 
        this.startNode = startNode; 
    }
    
    /**
     * Methode die endNode teruggeeft
     * @return endNode de node waar het algorithme eindigt
     */
    public Vertice getEndNode() 
    { 
        return endNode; 
    }
    
    /**
     * Methode voor het setten van de endNode
     * @param endNode de node waar het algorithme moet eindigen
     */
    public void setEndNode(Vertice endNode) 
    { 
        this.endNode = endNode; 
    }
    
    /**
     * Methode die aantal iteraties terug geeft
     * @return amountOfIterations het aantal iteraties
     */
    public int getAmountOfIterations() 
    { 
        return amountOfIterations; 
    }
    
    /**
     * Methode die het aantal iteraties zet
     * @param amountOfIterations de hoeveelheid van iteraties dat al gebeurd zijn
     */
    public void setAmountOfIterations(int amountOfIterations) 
    { 
        this.amountOfIterations = amountOfIterations; 
    }
    
    /**
     * Methode die het aantal iteraties met 1 verhoogd.
     */
    public void increaseIterations() 
    { 
        amountOfIterations++; 
    }
    
    /**
     * Methode die de status van het algorithme opvraagt
     * @return algorithmState de status van het algorithme
     * @see AlgorithmState
     */
    public AlgorithmState getAlgorithmState()  
    { 
        return algorithmState; 
    }
    
    /**
     * Methode die de status van  het algorithme kan setten
     * @param algorithmState die nieuwe status van het algorithme
     */
    public void setAlgorithmState(AlgorithmState algorithmState) 
    { 
        this.algorithmState = algorithmState; 
    }
    
    /**
     * Methode die kijkt of currentNode gelijk is aan endNode,
     * indien dit het geval is is het algorithm klaar en wordt AlgorithmState solved.
     * 
     * Indien currentNode gelijk is aan startNode dan is het algorithme vastgelopen
     * en wordt het op unsolveable gezet.
     */
    public void updateModelState()
    {
        if(currentNode == endNode)
        {
            System.out.println("Setting state to finished");
            algorithmState = AlgorithmState.FINISHED;
        }
        
        else if(currentNode == startNode && amountOfIterations > 1)
        {
            System.out.println("Setting state to unsolvable");
            algorithmState = AlgorithmState.UNSOLVABLE;
        }    
    }
    
    /**
     * Teken het genomen pad door het algorithme
     * Dit gebeurt door van elke currentNode de ouder (parent)
     * op te vragen en zo terug naar start te gaan.
     */
    public void drawTakenPath()
    {
        while(currentNode != startNode)
        {
            System.out.println(currentNode.getLocation());
            
            if(currentNode.getVerticeType() == VerticeType.TRAVERSED && currentNode != startNode && currentNode != endNode)
            {
                currentNode.setVerticeType(VerticeType.PARENT);
            }
            
            currentNode = currentNode.getParent();
            
            if (currentNode == null)
            {
                return;
            }
        }
    }
    
    /**
     * Methode die Vertice (node) teruggeeft aan de hand van andere Vertice
     * @param node de Vertice die gechecked moet worden
     * @return node in nodes[][] die overeenkomt met de meegegeven node
     */
    public Vertice getNodeFromOther(Vertice node)
    {
        int vX = (int) node.getPositionX();
        int vY = (int) node.getPositionY();
        
        return nodes[vX][vY];
    }
    
    /**
     * @deprecated
     * Methode die kijkt of een Vertice al dan niet de VerticeType heeft vande meegegeven List
     * @param node de node om te controleren
     * @param typesToCheck lijst van VerticeTypes
     * @return true indien node de VerticeType bevat
     */
    public boolean containsVerticeType(Vertice node, List<VerticeType> typesToCheck)
    {
        //Netbeans suggested this function over for(Type t : x){}
        if (typesToCheck.stream().anyMatch((verticeType) -> (node.getVerticeType() == verticeType))) 
        {
            return true;
        }
        return false;
    }
}