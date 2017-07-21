package nqueensproblem;

import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * Control Class handles all user interface interactions. Makes calls to Queens
 * Class. Handles all interaction checking and to see if the problem has been
 * solved.
 * 
 * @author scha7843
 */
public class Control {
    private static Queen queens[]; // array of n queens on nxn board
    private static boolean isSolved = false; // is the problem solved
    private static boolean unsolvable = false; // is the problem solvable
    private static int numberofqueens; // total number of queens and board size
    
    /**
     * Method: getNQueens
     * Purpose: gets the number of queens and size of the board from the user
     * Parameters: none
     * Returns: Nothing
     */
    public static void getNQueens(){
        Scanner kin = new Scanner(System.in); // takes in user input
        int num = 0; // number of queens from user
        
        while(num <= 3){
            try{
                System.out.print("Please enter the number of "
                        + "queens greater than 3: ");
                num = kin.nextInt();
            } catch(InputMismatchException err){
                System.out.println("Not a number.");
            } // end of try-catch statement
            
            if(num <=3) // if num was not high enough
                System.out.println("Number too low.");
            //end of if statement
        }// end of while loop
        numberofqueens = num;
        createQueensArray(); // creates array of queens
    }// end of getNQueens
    
    /**
     * Method: createQueensArray()
     * Purpose: creates an array of queens for assignment
     * Parameters: none
     * Returns: nothing
     */
    private static void createQueensArray(){
        queens = new Queen[numberofqueens]; // creates array of queens
        
        for(int index=0; index < numberofqueens; index++){
            queens[index] = new Queen(); // creates queen at index            
            queens[index].setRow(index+1); // sets queens in rows 1-N
        }// end of for loop
    }// end of createQueenArray();
    
    /**
     * Method: solveNQueens
     * Purpose: is to be called by the user to start solving the assignment with
     *          size given to the numberofqueens variable
     * Parameters: None
     * Returns: Nothing
     */
    public void solveNQueens(){
        
    }// end of solveNQueens
    
    /**
     * Method: checkCurrentQueen
     * Purpose: is the currently moved queen in a position that is safe
     *              -only need to check queens that are moved because by default
     *                  all queens are in a position that is not valid
     *              -Method will be broken up into three parts to check
     *                  a) rows
     *                  b) columns
     *                  c) diagonals
     * @param piece 
     *          -is the current moving queen in the iteration method being 
     *              used
     * Returns: Nothing
     */
    private void checkCurrentQueen(Queen piece){
        
    }
}// end of Control Class
