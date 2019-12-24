/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unfairgame;

import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Sebastiaan
 */
public class GerardView extends Region
{
    final private Gerard model;
    
    public GerardView(Gerard model) 
    {
        this.model = model;
        update();
    }
    
    public void update()
    {
        getChildren().clear();
        Paint kleur = Color.BROWN;
        Rectangle gerard = createRectangle(model.getXCoordinaat(), model.getYCoordinaat(),model.getGerardBreedte(), model.getGerardHoogte(), kleur);
        
        
        getChildren().add(gerard);
    }
    
    public Gerard getModel()
    {
        return model;
    }
    
    public Rectangle createRectangle(double xCoord, double yCoord, double breedte, double hoogte, Paint kleur)
    {
        Rectangle r = new Rectangle(xCoord, yCoord, breedte, hoogte);
        r.setFill(kleur);
        
        return r;
    }
}
