/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unfairgame;

import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

/**
 *
 * @author Sebastiaan
 */
public class UnfairGameView extends Region
{
    private UnfairGameModel model;
    private Text info;
    private GerardView view;
    
    public UnfairGameView(UnfairGameModel spel) 
    {
        model = spel;
        update();
    }
    
        public void update()
        {
            getChildren().clear();
            Gerard c = model.getGerard();

            GerardView cv = new GerardView(c);
            getChildren().add(cv);
            view = cv;
        }
}
