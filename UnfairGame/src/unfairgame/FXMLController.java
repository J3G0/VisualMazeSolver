package unfairgame;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class FXMLController implements Initializable
{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane spelView;
    
    private UnfairGameModel model;
    private UnfairGameView view;
    private UnfairGameTimer timer;
    
    public void setModel(UnfairGameModel model) 
    {
        this.model = model;
        view = new UnfairGameView(model);
        view.setOnKeyPressed(this::processInput);
        view.setFocusTraversable(true);
        spelView.getChildren().add(view);
        update();
        timer = new UnfairGameTimer(model, this);
        Timer t = new Timer(true);
        t.scheduleAtFixedRate(timer, 50, 50);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        //startKnop.setOnAction(evt -> herstart());
        //addKnop.setOnAction(evt -> addCirkel());
    }    
    
    public void herstart()
    {
        model = new UnfairGameModel();
        view = new UnfairGameView(model);
        spelView.getChildren().clear();
        spelView.getChildren().add(view);
    }
    
    public void addCirkel()
    {
        update();
    }
    
    public void update()
    {
        view.update();
    }
    
    private void processInput(KeyEvent keyEvent)
    {
        
        switch(keyEvent.getCode())
        {
            case UP:
                model.beweegNaarBoven();
                view.update();
                break;
            case DOWN:
                model.beweegNaarOnder();
                view.update();
                break;
                
            case LEFT:
                model.beweegNaarLinks();
                view.update();
                break;
                
            case RIGHT:
                model.beweegNaarRechts();
                view.update();
                break;
                
            default:
                view.update();
                break;
        }
    }
}
