/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project4;

import Jama.Matrix;

/**
 *
 * @author David Klecker
 */
public class TwoPolynomialEquation {
    
    public double[] Equation1;
    public double[] Equation2;
    public double X;
    public double Y;
    
    public TwoPolynomialEquation(double[] FirstEquation, double[] SecondEquation){
        
        if(FirstEquation.length==3 && SecondEquation.length == 3){
            Equation1 = FirstEquation;
            Equation2 = SecondEquation; 
        }
        else{
            Equation1 = null;
            Equation2 = null;
        }
        X = 0.0;
        Y = 0.0;
    }
    
    public void Solve(){
        
        if(Equation1 != null && Equation2 != null){
            double[][] ary = {Equation1, Equation2};
            Matrix FullMatrix = new Matrix(ary);
            int i[] = {0, 1};
            Matrix MatrixLeft = FullMatrix.getMatrix(0, 1, i);
            int j[] = {2};
            Matrix MatrixRight = FullMatrix.getMatrix(0, 1, j);
        
            Matrix m1Inverse = MatrixLeft.inverse();
            Matrix Solved = m1Inverse.times(MatrixRight);
            X = Solved.get(0, 0);
            Y = Solved.get(1, 0);        
        }
    }

}
