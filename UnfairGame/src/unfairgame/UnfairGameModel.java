/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unfairgame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author Sebastiaan
 */
public class UnfairGameModel
{
    /** de lijst met cirkels */
    private Gerard gerard;
     
    /** Constructor voor het cirkelspel met 0 cirkels */
    public UnfairGameModel() 
    {
        this.gerard = new Gerard();   
    } 

    public Gerard getGerard()
    {
        return gerard;
    }
    
    public void beweegNaarLinks()
    {
        double gX = gerard.getXCoordinaat();
        gerard.setXCoordinaat(gX - 1);
    }
    
    public void beweegNaarRechts()
    {
        double gX = gerard.getXCoordinaat();
        gerard.setXCoordinaat(gX + 1);
    }

    public void beweegNaarBoven()
    {
        double gY = gerard.getYCoordinaat();
        gerard.setYCoordinaat(gY - 1);
    }

    public void beweegNaarOnder()
    {
        double gY = gerard.getYCoordinaat();
        gerard.setYCoordinaat(gY + 1);
    }
    
    public void tick()
    {
        Random rand = new Random();
        gerard.setYCoordinaat(rand.nextInt(100));
    }
    
}
