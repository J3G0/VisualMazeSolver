package algorithmvisualiser;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class FXMLAlgorithmController implements Initializable
{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;
    
    private AlgorithmModel model;
    private AlgorithmView view;
    
    public void setModel(AlgorithmModel model) 
    {
        this.model = model;
        view = new AlgorithmView(model);
        anchorPane.getChildren().add(view);
        update();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        //startKnop.setOnAction(evt -> herstart());
        //addKnop.setOnAction(evt -> addCirkel());
    }    
    
    public void herstart()
    {
        model = new AlgorithmModel();
        view = new AlgorithmView(model);
        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(view);
    }
    
    public void addCirkel()
    {
        update();
    }
    
    public void update()
    {
        view.update();
    }
}
