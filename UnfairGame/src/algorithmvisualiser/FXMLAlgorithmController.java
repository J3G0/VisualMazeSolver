package algorithmvisualiser;

import java.awt.Point;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
        anchorPane.setOnMouseDragged(this::handleClick);
        view.setFocusTraversable(true);
        anchorPane.getChildren().add(view);
        update();
    }
    
    public void updateComboBox()
    {
        // Initialize own comboBox items
        // Source: https://stackoverflow.com/questions/35260061/combobox-items-via-scene-builder
        comboBox.getItems().removeAll(comboBox.getItems());
        comboBox.getItems().addAll("Start node", "End node", "Solid node");
        comboBox.getSelectionModel().select("Option B");      
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
        model.findPath();
        update();      
    }
    
    private void iterate(KeyEvent e) 
    {
        switch(e.getCode())
        {
            case UP:
                model.findPath();
                update();
                break;
        }
    }
    
    private void handleClick(MouseEvent e)
    {
        int clickedX = (int) e.getX();
        int clickedY = (int) e.getY();
        
        Point p = view.getCoordPointFromClick(clickedX,clickedY);
        model.getNodeAtLocation(p.x,p.y).setVerticeType(VerticeType.SOLID);
        update();
    }
}
