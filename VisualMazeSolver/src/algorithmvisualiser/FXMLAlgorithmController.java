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
    
    private AlgorithmModel model;
    private AlgorithmView view;
    
    public void setModel(AlgorithmModel model) 
    {
        this.model = model;
        view = new AlgorithmView(model);
        view.setFocusTraversable(true);
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
        rst.setOnAction(evt -> changeModel());
        next.setOnAction(evt -> iterate());
    }    
    
    public void iterate()
    {
        model.iterate();
        update();
    }
    
    public void update()
    {
        view.update();
    }
    
    public void changeModel()
    {
        System.out.println("test");
        setModel(new AlwaysGoRight());
        update();
    }
}
