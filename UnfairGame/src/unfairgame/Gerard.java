/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unfairgame;

/**
 *
 * @author Sebastiaan
 */
public class Gerard 
{
    final private double gerardBreedte;
    final private double gerardHoogte;
    private double xCoordinaat;
    private double yCoordinaat;
    
    
    public Gerard() 
    {
        this.gerardBreedte = 50;
        this.gerardHoogte = 50;
        this.xCoordinaat = 0;
        this.yCoordinaat = 0;
    }
    
    // Getters
    
    public double getGerardBreedte() 
    { 
        return gerardBreedte; 
    }
    public double getGerardHoogte() 
    { 
        return gerardHoogte; 
    }
    
    public double getXCoordinaat()
    {
        return xCoordinaat;
    }
    
    public double getYCoordinaat()
    {
        return yCoordinaat;
    }
    
    
    // Setters
    
    public void setXCoordinaat(double xCoordinaat)
    {
        this.xCoordinaat = xCoordinaat;
    }
    
    public void setYCoordinaat(double yCoordinaat)
    {
        this.yCoordinaat = yCoordinaat;
    }
    

}
