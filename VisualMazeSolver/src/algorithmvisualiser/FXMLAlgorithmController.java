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
    
    private AlgorithmModel model;
    private AlgorithmView view;
    private AlgorithmProgressor progressor;
    private Timer timer;
    private boolean isClicking;
    private Point clickLocation;
    private Vertice previousSelectedNode;
    private Vertice selectedNode;
    
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
    
    public void updateClickStatus (MouseEvent event, boolean isClicking) 
    {
        clickLocation = getPointFromMouseEvent(event);
        
        this.isClicking = isClicking;
        //System.out.println("click " + isClicking);
        if(!isClicking && selectedNode != null)
        {
            Point p = new Point( (int) selectedNode.getPositionX(), (int) selectedNode.getPositionY());
            
            if(selectedNode.getVerticeType() == VerticeType.START)
            {              
                model.setStartNode(model.getNodeAtPoint(p)); 
            }
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        updateComboBox();
        rst.setOnAction(evt -> changeModel());
        restart.setOnAction(evt -> processAlgorithmComboBox(new ActionEvent()));
        next.setOnAction(evt -> iterate());
        finish.setOnAction(evt -> finish());
    }    
    
    public void finish()
    {
        model.finish();
        update();
    }
    
    public void iterate()
    {     
        if(model.getAlgorithmState() == AlgorithmState.SOLVING)
        {
            //Fixed timertask that calls model.iterate()
            timer.scheduleAtFixedRate(progressor, (int) progressorSpeed.getValue(), (int) progressorSpeed.getValue());
            update();
        }
    }
    
    public void update()
    {
        view.update();
        iterateField.setText( ("STATE: " + model.getAlgorithmState().toString()) + " - ITERATIONS: " + model.getAmountOfIterations());
        speedIndicator.setText("Speed: " + (int) progressorSpeed.getValue() + " ms");
    }
    
    public void changeModel()
    {
        //Cancel timer as a new
        timer.cancel();
        resetCurrentModel();
        update();
    }
    
    private void handleMouseDragEvent(MouseEvent event )
    {
        Point eventPoint = getPointFromMouseEvent(event);
        

        if(model.getNodeAtPoint(eventPoint).getVerticeType() == VerticeType.BASIC && model.getNodeAtPoint(clickLocation) != model.getStartNode() && model.getNodeAtPoint(clickLocation) != model.getEndNode())
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
                }
                
                if(previousSelectedNode != null && previousSelectedNode != model.getEndNode() && previousSelectedNode.getVerticeType() != VerticeType.SOLID)
                {
                    model.getNodeFromOther(previousSelectedNode).setVerticeType(VerticeType.BASIC);
                }
                previousSelectedNode = selectedNode;
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
                }
                
                if(previousSelectedNode != null && previousSelectedNode != model.getStartNode() && previousSelectedNode.getVerticeType() != VerticeType.SOLID)
                {
                    model.getNodeFromOther(previousSelectedNode).setVerticeType(VerticeType.BASIC);
                }
                previousSelectedNode = selectedNode;
                update();
            }
        }
    }
    
    
    private void handleMouseClickEvent(MouseEvent event)
    {
        //System.out.println("Clicked");        
        Point p = getPointFromMouseEvent(event);
        handleTileComboBox(p, comboBox.getValue());     
        model.updateSets();
        update();

    }
     
    public Point getPointFromMouseEvent(MouseEvent event)
    {
        int clickedX = (int) event.getX();
        int clickedY = (int) event.getY();
        Point p = view.getCoordPointFromClick(clickedX,clickedY);
        
        return p;
    }
    
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
