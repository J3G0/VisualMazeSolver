/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithmvisualiser;
import java.util.TimerTask;
import javafx.application.Platform;

/**
 * Klasse die ervoor zorgt dat iteratie per tijdsinterval opgeroepen wordt
 * Om de x seconden 1 iteratie van het algoritme
 * @author Sebastiaan
 */
public class AlgorithmProgressor extends TimerTask
{
    private AlgorithmModel model;
    private FXMLAlgorithmController controller;
    
    public AlgorithmProgressor(AlgorithmModel model, FXMLAlgorithmController controller) 
    {
        this.model = model;
        this.controller = controller;
    }
    
    @Override
    public void run()
    {
        model.iterate();
        Platform.runLater(controller::update);
    }
}
