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
import javafx.scene.control.TextField;
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
    private Button finish;
    
    @FXML
    private ComboBox<String> comboBox;
    
    @FXML
    private TextField iterateField;
    
    @FXML
    private ComboBox<String> comboBox2;
    
    private AlgorithmModel model;
    private AlgorithmView view;
    private AlgorithmProgressor progressor;
    private Timer timer;
    
    public void setModel(AlgorithmModel model) 
    {
        this.model = model;
        view = new AlgorithmView(model);
        view.setFocusTraversable(true);
        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(view);
        view.setOnMouseDragged(this::handleMouseEvent);
        view.setOnMouseClicked(this::handleMouseEvent);
        progressor = new AlgorithmProgressor(this.model, this);
        timer = new Timer(true);
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
    
    public void updateComboBox2()
    {
        // Initialize own comboBox items
        // Source: https://stackoverflow.com/questions/35260061/combobox-items-via-scene-builder
        comboBox.getItems().removeAll(comboBox2.getItems());
        comboBox.getItems().addAll("Always Right", "AStar", "Vertice");
        comboBox.getSelectionModel().select("Always Right");      
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        updateComboBox();
        rst.setOnAction(evt -> changeModel());
        next.setOnAction(evt -> iterate());
        finish.setOnAction(evt -> finish());
    }    
    
    public void finish()
    {
        if(model.getAlgorithmState() != AlgorithmState.UNSOLVABLE)
        {
            model.finish();
            update();
        }
    }
    
    public void iterate()
    {
        timer.scheduleAtFixedRate(progressor, 50, 50);
        //model.iterate();
        update();
    }
    
    public void update()
    {
        view.update();
        iterateField.setText(Integer.toString(model.getAmountOfIterations()));
    }
    
    public void changeModel()
    {
        //Cancel timer as a new
        timer.cancel();
        setModel(new AlwaysGoRight());
        update();
    }
    
    private void handleMouseEvent(MouseEvent e)
    {
        int clickedX = (int) e.getX();
        int clickedY = (int) e.getY();
        
        Point p = view.getCoordPointFromClick(clickedX,clickedY);
        
        switch(comboBox.getValue())
        {
            //Todo: rewrite this to fit better (function?)
            case "Start node":
                Vertice previousStart = model.getStartNode();
                
                previousStart.setPreviousVerticeType(VerticeType.START);
                previousStart.setVerticeType(VerticeType.BASIC);
                
                model.getNodeAtLocation(p.x,p.y).setVerticeType(VerticeType.START);
                model.getNodeAtLocation(p.x,p.y).setPreviousVerticeType(VerticeType.BASIC);
                model.setStartNode(model.getNodeAtLocation(p.x,p.y));
                break;
                
            case "End node":
                Vertice previousEnd = model.getEndNode();
                
                previousEnd.setPreviousVerticeType(VerticeType.END);

                
                previousEnd.setVerticeType(VerticeType.BASIC);               
                model.getNodeAtLocation(p.x,p.y).setVerticeType(VerticeType.END);
                model.getNodeAtLocation(p.x,p.y).setPreviousVerticeType(VerticeType.BASIC);
                model.setEndNode(model.getNodeAtLocation(p.x,p.y));
                break;      
                
            case "Solid node":
                Vertice n = model.getNodeAtLocation(p.x,p.y);
                //Don't overwrite start or end nodes
                
                if (!(n.getVerticeType() == VerticeType.START || n.getVerticeType() == VerticeType.END) )
                {
                    model.getNodeAtLocation(p.x,p.y).setPreviousVerticeType(VerticeType.BASIC);
                    model.getNodeAtLocation(p.x,p.y).setVerticeType(VerticeType.SOLID);
                }
                break;   
        }
        
        model.updateSets();
        update();
    }

    private void randomize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
