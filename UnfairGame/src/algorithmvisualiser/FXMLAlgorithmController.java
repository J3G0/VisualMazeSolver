package algorithmvisualiser;

import java.awt.Point;
import java.net.URL;
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
    private ComboBox<String> comboBox;
    
    private AlgorithmModel model;
    private AlgorithmView view;
    
    public void setModel(AlgorithmModel model) 
    {
        this.model = model;
        view = new AlgorithmView(model);
        view.setOnKeyPressed(this::iterate);
        view.setFocusTraversable(true);
        view.setOnMouseDragged(this::handleMouseEvent);
        view.setOnMouseClicked(this::handleMouseEvent);
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
    }    
    
    public void herstart()
    {
        model = new AlgorithmModel();
        view = new AlgorithmView(model);
        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(view);
    }
    
    public void update()
    {
        view.update();
    }
    
    private void iterate()
    {
        for(int i = 0 ; i < 5 ; i++)
        {
            model.findPath();
            update();   
        }   
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
                if( e.isControlDown()  && model.solidNodes.size() > 0)
                {
                    AStarVertice lastSolid = model.solidNodes.get(model.solidNodes.size() - 1);
                    model.solidNodes.remove(lastSolid);
                    
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
            case "Start node":
                AStarVertice previousStart= model.getStartNode();
                previousStart.setVerticeType(VerticeType.BASIC);
                model.getNodeAtLocation(p.x,p.y).setVerticeType(VerticeType.START);
                break;
                
            case "End node":
                AStarVertice previousEnd = model.getEndNode();
                previousEnd.setVerticeType(VerticeType.BASIC);
                model.getNodeAtLocation(p.x,p.y).setVerticeType(VerticeType.END);
                break;      
                
            case "Solid node":
                AStarVertice n = model.getNodeAtLocation(p.x,p.y);
                //Don't overwrite start or end nodes
                if (!(n.getVerticeType() == VerticeType.START || n.getVerticeType() == VerticeType.END) )
                {
                    model.getNodeAtLocation(p.x,p.y).setVerticeType(VerticeType.SOLID);
                    model.solidNodes.add(model.getNodeAtLocation(p.x,p.y));
                }
                break;   
        }
        
        model.updateSets();
        update();
    }
}
