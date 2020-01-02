package algorithmvisualiser;

import java.awt.Point;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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
    private Button finnish;
    
    @FXML
    private ComboBox<String> comboBox;
    
    private AStarModel model;
    private AStarView view;
    
    public void setModel(AStarModel model) 
    {
        this.model = model;
        view = new AStarView(model);
        view.setOnKeyPressed(this::iterate);
        view.setFocusTraversable(true);
        view.setOnMouseDragged(this::handleMouseEvent);
        view.setOnMouseClicked(this::handleMouseEvent);
        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(view);
        update();
    }
    
    public void updateComboBox()
    {
        // Initialize own comboBox items
        // Source: https://stackoverflow.com/questions/35260061/combobox-items-via-scene-builder
        comboBox.getItems().removeAll(comboBox.getItems());
        comboBox.getItems().addAll("Start node", "End node", "Solid node");
        comboBox.getSelectionModel().select("Start node");      
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        updateComboBox();
        next.setOnAction(evt -> iterate());
        rst.setOnAction(evt -> randomize());
        finnish.setOnAction(evt -> finnish());
    }    
    
    public void randomize()
    {
        Random r = new Random();
        this.model = new AStarModel(r.nextInt(150));
        setModel(model);
        System.out.println("Start: " + model.getStartNode().getLocation());
        System.out.println("End: " + model.getEndNode().getLocation());
    }
    
    public void herstart()
    {
        model = new AStarModel();
        view = new AStarView(model);
        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(view);
    }
    
    public void update()
    {
        view.update();
    }
    
    private void iterate()
    {
        model.findPath();
        update();     
    }
    
    private void finnish()
    {
        model.fastPath();
        update();
    }
    
    private void iterate(KeyEvent e) 
    {
        //ISSUE: KEYEVENTS FOCUS ON BUTTONS
        System.out.println(e.getCode());
        switch(e.getCode())
        {
            case UP:
                model.findPath();
                break;
                
            case Z:
                System.out.println("Z pressed");
                if( e.isControlDown()  && model.edittedNodes.size() > 0)
                {
                    AStarVertice lastSolid = model.edittedNodes.get(model.edittedNodes.size() - 1);
                    model.edittedNodes.remove(lastSolid);
                    lastSolid.setVerticeType(null);
                    
                    int lastSolidX = (int) lastSolid.getPositionX();
                    int lastSolidY = (int) lastSolid.getPositionY();
                    
                    model.getNodeAtLocation(lastSolidX, lastSolidY).setVerticeType(VerticeType.BASIC);
                    
                }
                break;
        }
        update();
    }
    
    private void handleMouseEvent(MouseEvent e)
    {
        int clickedX = (int) e.getX();
        int clickedY = (int) e.getY();
        
        Point p = view.getCoordPointFromClick(clickedX,clickedY);
        System.out.println(comboBox.getValue());
        
        switch(comboBox.getValue())
        {
            //Todo: rewrite this to fit better (function?)
            case "Start node":
                AStarVertice previousStart= model.getStartNode();
                
                previousStart.setPreviousVerticeType(VerticeType.START);
                model.edittedNodes.add(previousStart);
                
                previousStart.setVerticeType(VerticeType.BASIC);
                model.getNodeAtLocation(p.x,p.y).setVerticeType(VerticeType.START);
                model.getNodeAtLocation(p.x,p.y).setPreviousVerticeType(VerticeType.BASIC);
                model.edittedNodes.add(model.getNodeAtLocation(p.x,p.y));
                break;
                
            case "End node":
                AStarVertice previousEnd = model.getEndNode();
                
                previousEnd.setPreviousVerticeType(VerticeType.END);
                
                model.edittedNodes.add(previousEnd);
                
                previousEnd.setVerticeType(VerticeType.BASIC);               
                model.getNodeAtLocation(p.x,p.y).setVerticeType(VerticeType.END);
                model.getNodeAtLocation(p.x,p.y).setPreviousVerticeType(VerticeType.BASIC);
                model.edittedNodes.add(model.getNodeAtLocation(p.x,p.y));
                break;      
                
            case "Solid node":
                AStarVertice n = model.getNodeAtLocation(p.x,p.y);
                //Don't overwrite start or end nodes
                if (!(n.getVerticeType() == VerticeType.START || n.getVerticeType() == VerticeType.END) )
                {
                    model.edittedNodes.add(model.getNodeAtLocation(p.x,p.y));
                    model.getNodeAtLocation(p.x,p.y).setPreviousVerticeType(VerticeType.BASIC);
                    model.getNodeAtLocation(p.x,p.y).setVerticeType(VerticeType.SOLID);
                }
                break;   
        }
        
        model.updateSets();
        update();
    }
}
