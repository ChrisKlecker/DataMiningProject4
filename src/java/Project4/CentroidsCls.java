/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project4;

import java.awt.Point;

/**
 *
 * @author David Klecker
 */
public class CentroidsCls {
    
    //For a trapazoid use A1, A2 for the top, B1, B2 for the base and H for the height;
    //For a triangle use A1, B1, and C1;
    //For a rectangle use A1, A2 for the top, B1, B2 for the base. 
    
    public Point A1;
    public Point A2;
    public Point B1;
    public Point B2;
    public Point C1;
    public double H;
    public Point Centroid;
    
    public CentroidsCls(){
        
        this.A1 = null;
        this.A2 = null;
        this.B1 = null;
        this.B2 = null;
        this.C1 = null;
        this.H = 0.0;
        this.Centroid = null;
    }
    
    public void GetCentroidOfTrapizoid(){
        
        double a = A2.x - A1.x;
        double b = B2.x - B1.x;
        H = A1.y - B1.y;
        
        Centroid.setLocation(((b+(2.0*a)) / (3.0*(a+b))) * H, (H/2)+B1.y);
    }
    
    public void GetCentroidOfTriangle(){
        
        double X = (A1.x + B1.x + C1.x)/3;
        double Y = (A1.y + B1.y + C1.y)/3;
        Centroid.setLocation(X, Y);
    }
}
