package nqueensproblem;

/**
 * Main Class for use of User Interface calls to Control to run both brute force
 * and iterative repair algorithms
 * @author scha7843
 */
public class NQueensProblem {

    /**
     * @param args the command line arguments
     * @author scha7843
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Control.getNQueens(); // get input from user
        Control.solveNQueens(); // solve the problem using the 2 algorithms
    }// end of main    
}// end of NQueensProblem
