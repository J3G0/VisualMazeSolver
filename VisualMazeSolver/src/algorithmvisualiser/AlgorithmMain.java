/*
 * @author Sebastiaan Vanspauwen
 * @author Jeffrey Gorissen
 * @teacher Kris Aerts
 */
package algorithmvisualiser;
import algorithmvisualiser.algorithmtype.AlwaysGoRight;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Basis klasse Algorithm Main
 * Erft van Application
 */
public class AlgorithmMain extends Application
{
    @Override
    public void start(Stage stage) throws Exception 
    {
        FXMLLoader lader = new FXMLLoader(getClass().getResource("FXMLAlgorithm.fxml"));
        Parent root = lader.load();
        FXMLAlgorithmController controller = lader.getController();
        
        AlwaysGoRight model = new AlwaysGoRight();
        controller.setModel(model);
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Algorithm Visualiser by Sebastiaan & Jeffrey");
        // https://exceptionshub.com/javafx-application-icon.html
        stage.getIcons().add(new Image(AlgorithmMain.class.getResourceAsStream("./resources/img/Test-icon.png")));
        stage.show();
    }

    /**
     * @param args de command line argumenten
     */
    public static void main(String[] args) 
    {
        launch(args);
    }
    
}
