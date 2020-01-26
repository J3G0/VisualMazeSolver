package algorithmvisualiser;

import algorithmvisualiser.vertice.VerticeType;
import algorithmvisualiser.vertice.Vertice;
import algorithmvisualiser.algorithmtype.TurnClockwise;
import algorithmvisualiser.algorithmtype.AlwaysGoRight;
import algorithmvisualiser.algorithmtype.Drunk;
import algorithmvisualiser.algorithmtype.AStar;
import java.awt.Point;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class FXMLAlgorithmController implements Initializable
{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private Button next;
    
    @FXML
    private Button rst;
    
    @FXML
    private Button restart;   
    
    @FXML
    private Button finish;
    
    @FXML
    private ComboBox<String> comboBox;
    
    @FXML
    private TextField iterateField;
    
    @FXML
    private ComboBox<String> algorithmComboBox;
    
    @FXML
    private Slider progressorSpeed;

    @FXML
    private Text speedIndicator;
    
    /**
     * Het model van het algoritme
     */
    private AlgorithmModel model;
    /**
     * De view van het algorithme
     */
    private AlgorithmView view;
    /**
     * De progressor van het algorithme (timer)
     */
    private AlgorithmProgressor progressor;
    /**
     * De timer van het algorithme
     */
    private Timer timer;
    /**
     * Een boolean (flag) die bijhoudt of de gebruiken aan het klikken is
     */
    private boolean isClicking;
    /**
     * Het punt waarop geklikt werd
     */
    private Point clickLocation;
    /**
     * De vorige node die geselecteerd is
     */
    private Vertice previousSelectedNode;
    /**
     * De huidige node die geselecteerd is
     */
    private Vertice selectedNode;
    
    /**
     * Functie die het model update naar het nieuwe ingestelde model
     * @param model het meegegeven model
     */
    public void setModel(AlgorithmModel model) 
    {
        this.model = model;
        view = new AlgorithmView(model);
        view.setFocusTraversable(true);
        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(view);
        view.setOnMouseDragged(this::handleMouseDragEvent);
        view.setOnMouseClicked(this::handleMouseClickEvent);
        // https://stackoverflow.com/questions/35372236/how-to-pass-paremeter-with-an-event-in-javafx
        view.setOnMousePressed(event -> {updateClickStatus(event, true); });
        view.setOnMouseReleased(event -> {updateClickStatus(event, false); });
        algorithmComboBox.setOnAction(this::processAlgorithmComboBox);
        progressorSpeed.setOnMouseReleased(this::handleSliderDragEvent);
        progressor = new AlgorithmProgressor(this.model, this);
        timer = new Timer(true);
        update();
    }
    
    /**
     * Functie die de klik status voorziet
     * @param event de event van het klikken
     * @param isClicking boolean die meegeeft de muis is ingedrukt of niet
     */
    public void updateClickStatus (MouseEvent event, boolean isClicking) 
    {
        //Vraag de kliklocatie op
        clickLocation = getPointFromMouseEvent(event);  
        //Update isClicking
        this.isClicking = isClicking;
        
        //Als er geen huidige klik bezig is en niks is geselecteerd
        if(!isClicking && selectedNode != null)
        {
            Point p = new Point( (int) selectedNode.getPositionX(), (int) selectedNode.getPositionY());
            
            //Als startnode is geselecteerd
            if(selectedNode.getVerticeType() == VerticeType.START)
            {              
                model.setStartNode(model.getNodeAtPoint(p)); 
            }
            //Als endnode is geselecteerd
            else if(selectedNode.getVerticeType() == VerticeType.END)
            {
                model.setEndNode(model.getNodeAtLocation(p.x, p.y)); 
            }    
            previousSelectedNode = null;
            selectedNode = null;
            clickLocation = null;
            model.updateSets();
            update();
        }      
    } 
    
    /**
     * Functie die wordt opgeroepen bij een RESTART klik
     * Neemt de waarde van de comboBox en herstart het model met huidige nodes
     * @param event ActionEvent de event van de ComboBox
     */
    public void processAlgorithmComboBox(ActionEvent event)
    {
       timer.cancel();
       switch(algorithmComboBox.getValue())
       {         
           case "Always right":
               setModel(new AlwaysGoRight(model.getNodes()));          
               break;
               
           case "Drunk":
               setModel(new Drunk(model.getNodes()));          
               break;
               
           case "Turn clockwise":
               setModel(new TurnClockwise(model.getNodes()));          
               break;
               
           case "A Star":
               setModel(new AStar(model.getNodes()));
               break;
       }
    }
    
    /**
     * Reset het huidige model (verwijder alle nodes)
     */
    public void resetCurrentModel()
    {
       switch(algorithmComboBox.getValue())
       {
              
           case "Always right":
               setModel(new AlwaysGoRight());          
               break;
               
           case "Drunk":
               setModel(new Drunk());          
               break;

           case "Turn clockwise":
               setModel(new TurnClockwise());          
               break;
               
           case "A Star":
               setModel(new AStar());
               break;
       }
    }
    
    /**
     * Functie die slider snelheid aanpast
     * Als het geslide wordt, en model is aan het lopen dan kan deze geüpdate worden
     * Maakt ook nieuwe timer aan
     * @param event Event van het sliden
     */
    public void handleSliderDragEvent(MouseEvent event)
    {
        if(model.getAmountOfIterations() > 1)
        {
            speedIndicator.setText("Speed: " + (int) progressorSpeed.getValue() + " ms");
            timer.cancel();
            progressor = new AlgorithmProgressor(this.model, this);
            timer = new Timer(true);
            timer.scheduleAtFixedRate(progressor, (int) progressorSpeed.getValue(), (int) progressorSpeed.getValue());
            update();
        }
    }
    
    /**
     * Initialisatie van de comboBox waardes
     */
    public void updateComboBox()
    {
        // Initialize own comboBox items
        // Source: https://stackoverflow.com/questions/35260061/combobox-items-via-scene-builder
        comboBox.getItems().removeAll(comboBox.getItems());
        comboBox.getItems().addAll("Solid node", "Basic node");
        comboBox.getSelectionModel().select(comboBox.getItems().get(0)); 
        
        algorithmComboBox.getItems().removeAll(algorithmComboBox.getItems());
        algorithmComboBox.getItems().addAll("Always right", "Drunk", "Turn clockwise", "A Star");
        algorithmComboBox.getSelectionModel().select("Always right"); 
    }
    
    /**
     * Functie die initialisatie van de buttons voorziet
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        updateComboBox();
        rst.setOnAction(evt -> changeModel());
        restart.setOnAction(evt -> processAlgorithmComboBox(new ActionEvent()));
        next.setOnAction(evt -> iterate());
        finish.setOnAction(evt -> finish());
    }    
    
    /**
     * Functie die wordt opgeroepen als FINISH knop wordt ingeduwd
     * roept model.finish() op
     */
    public void finish()
    {
        model.finish();
        update();
    }
    
    /**
     * Functie die wordt opgeroepen als NEXT knop wordt ingeduwd
     * Deze roept de progessor op die dan model.iterate() op een timer uitvoert.
     */
    public void iterate()
    {     
        if(model.getAlgorithmState() == AlgorithmState.SOLVING)
        {
            //Fixed timertask that calls model.iterate()
            try
            {
                timer.scheduleAtFixedRate(progressor, (int) progressorSpeed.getValue(), (int) progressorSpeed.getValue());
                update();
            }
            catch(Exception e)
            {
                System.out.println("Timer error: " + e.getCause());
            }
        }
    }
    
    /**
     * Functie die die interface update (aantal iteraties, snelheid, .. )
     */
    public void update()
    {
        view.update();
        iterateField.setText( ("STATE: " + model.getAlgorithmState().toString()) + " - ITERATIONS: " + model.getAmountOfIterations());
        speedIndicator.setText("Speed: " + (int) progressorSpeed.getValue() + " ms");
    }
    
    /**
     * Functie die het model verandert
     */
    public void changeModel()
    {
        //Cancel timer as a new
        timer.cancel();
        resetCurrentModel();
        update();
    }
    
    /**
     * Functie die het bewerken van de view behandelt.
     * Alsook het bewegen van START en END node
     * Hiermee kan de gebruiker SOLID of BASIC nodes tekenen in de map
     * @param event event van muis in view
     */
    private void handleMouseDragEvent(MouseEvent event )
    {
        //Punt van de mouseDrag
        Point eventPoint = getPointFromMouseEvent(event);
        //Als het model BASIC of SOLID is kan deze aangepast worden
        if ((model.getNodeAtPoint(eventPoint).getVerticeType() == VerticeType.BASIC 
            || model.getNodeAtPoint(eventPoint).getVerticeType() == VerticeType.SOLID)
            && model.getNodeAtPoint(clickLocation) != model.getStartNode() 
            && model.getNodeAtPoint(clickLocation) != model.getEndNode())
        {
            handleTileComboBox(eventPoint, comboBox.getValue());     
            update();
        }

        
        //Drag start node
        if(isClicking && model.getNodeAtPoint(clickLocation) == model.getStartNode())
        {
            selectedNode = model.getNodeAtLocation(eventPoint.x, eventPoint.y);
            if(selectedNode != previousSelectedNode)
            {
                //System.out.println(model.getNodeAtPoint(eventPoint).getVerticeType());
                if(model.getNodeAtPoint(eventPoint) != model.getEndNode() && model.getNodeAtPoint(eventPoint).getVerticeType() != VerticeType.SOLID)
                {
                    model.getNodeAtPoint(eventPoint).setVerticeType(VerticeType.START);                              
                    if(previousSelectedNode != null && previousSelectedNode != model.getEndNode() && previousSelectedNode.getVerticeType() != VerticeType.SOLID)
                    {
                        model.getNodeFromOther(previousSelectedNode).setVerticeType(VerticeType.BASIC);
                    }
                    previousSelectedNode = selectedNode;
                }
                update();
            }
        }
        
        //Drag end node
        if(isClicking && model.getNodeAtPoint(clickLocation) == model.getEndNode())
        {
            selectedNode = model.getNodeAtPoint(eventPoint);
            if(selectedNode != previousSelectedNode)
            {
                if(model.getNodeAtPoint(eventPoint) != model.getStartNode() && model.getNodeAtPoint(eventPoint).getVerticeType() != VerticeType.SOLID)
                {
                    model.getNodeAtPoint(eventPoint).setVerticeType(VerticeType.END);                             
                    if(previousSelectedNode != null && previousSelectedNode != model.getStartNode() && previousSelectedNode.getVerticeType() != VerticeType.SOLID)
                    {
                        model.getNodeFromOther(previousSelectedNode).setVerticeType(VerticeType.BASIC);
                    }
                    previousSelectedNode = selectedNode;
                }
                update();
            }
        }
    }
    
    /**
     * Functie die het klikken in de view behandelt
     * @param event de event van de muisklik
     */
    private void handleMouseClickEvent(MouseEvent event)
    {       
        Point p = getPointFromMouseEvent(event);
        handleTileComboBox(p, comboBox.getValue());     
        model.updateSets();
        update();

    }
     
    /**
     * Functie die van klikevent in reële coordinaten een x,y coordinaat terug krijgt
     * die overeenkomt in de nodes[x][y] map
     * @param event mouseEvent van de klik
     * @return het punt P(x,y) dat overeenstemt met een nodes[x][y]
     */
    public Point getPointFromMouseEvent(MouseEvent event)
    {
        int clickedX = (int) event.getX();
        int clickedY = (int) event.getY();
        Point p = view.getCoordPointFromClick(clickedX,clickedY);
        
        return p;
    }
    
    /**
     * Aan de hand van comboBox kan ingesteld worden welke Vertice getekend kan worden
     * @param p het punt van de te bewerking Vertice
     * @param comboBoxValue het type dat getekend moet worden (SOLID of BASIC)
     */
    public void handleTileComboBox(Point p, String comboBoxValue)
    {
        switch(comboBoxValue)
        {
            case "Solid node":
                Vertice s = model.getNodeAtLocation(p.x,p.y);
                //Don't overwrite start or end nodes
                
                if (!(s.getVerticeType() == VerticeType.START || s.getVerticeType() == VerticeType.END) )
                {
                    model.getNodeAtLocation(p.x,p.y).setPreviousVerticeType(VerticeType.BASIC);
                    model.getNodeAtLocation(p.x,p.y).setVerticeType(VerticeType.SOLID);
                }
                break;   
                
            case "Basic node":
                Vertice b = model.getNodeAtLocation(p.x,p.y);
                //Don't overwrite start or end nodes
                
                if (!(b.getVerticeType() == VerticeType.START || b.getVerticeType() == VerticeType.END) )
                {
                    model.getNodeAtLocation(p.x,p.y).setPreviousVerticeType(model.getNodeAtLocation(p.x,p.y).getVerticeType());
                    model.getNodeAtLocation(p.x,p.y).setVerticeType(VerticeType.BASIC);
                }
                break;                
        }  
        model.updateSets();
    }
}
