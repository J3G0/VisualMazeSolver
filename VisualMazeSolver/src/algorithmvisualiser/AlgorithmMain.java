/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithmvisualiser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Sebastiaan
 */
public class AlgorithmMain extends Application
{
    @Override
    public void start(Stage stage) throws Exception 
    {
        FXMLLoader lader = new FXMLLoader(getClass().getResource("FXMLAlgorithm.fxml"));
        Parent root = lader.load();
        FXMLAlgorithmController controller = lader.getController();
        
        AlgorithmModel model = new AlgorithmModel();
        controller.setModel(model);
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        launch(args);
    }
    
}
