/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unfairgame;

import java.util.TimerTask;
import javafx.application.Platform;

/**
 *
 * @author Sebastiaan
 */
public class UnfairGameTimer extends TimerTask
{
    private UnfairGameModel model;
    private FXMLController controller;
    
    public UnfairGameTimer(UnfairGameModel model, FXMLController controller)
    {
        this.model = model;
        this.controller = controller;
    }
    
    @Override
    public void run() 
    {
        model.tick();
        System.out.println(model.getGerard().getYCoordinaat()); 
        Platform.runLater(controller::update);
    }
    
}
