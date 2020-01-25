/*
 * @author Sebastiaan Vanspauwen
 * @author Jeffrey Gorissen
 * @teacher Kris Aerts
 */
package algorithmvisualiser;
import java.util.TimerTask;
import javafx.application.Platform;

/**
 * Klasse die ervoor zorgt dat iteratie per tijdsinterval opgeroepen wordt
 * Om de x seconden 1 iteratie van het algoritme
 */
public class AlgorithmProgressor extends TimerTask
{
    private AlgorithmModel model;
    private FXMLAlgorithmController controller;
    
    /**
     * AlgorithmProcessor Constructor
     * @param model, geeft het gebruikte model mee.
     * @param controller, geef de controllor mee. 
     */
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
