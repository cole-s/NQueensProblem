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
     * Method: checkQueens
     * Purpose: goes through the array of queens to find out if the problem has
     *          been solved
     * @param
     * @return true - if all queens are in a valid location
     *         false - if one or more queens have one or more conflicts with 
     *                  other queens
     */
    private boolean checkQueens(){
        for(int index = 0; index < numberofqueens; index++)
            checkCurrentQueen(index); 
        // end of for loop
        for(int index = 0; index < numberofqueens; index++){
            if(queens[index].getValidLocal() > 0)
                return false;
            // end of if statement
        } // end of for loop
        
        return true;
    }// end of checkQueens
    
    /**
     * Method: checkCurrentQueen
     * Purpose: is the currently moved queen in a position that is free of conflicts
     *              -only need to check queens that are moved because by default
     *                  all queens are in a position that is not valid
     *              -Method will be broken up into three parts to check
     *                  a) rows
     *                  b) columns
     *                  c) diagonals
     * @param
     * Returns: Nothing
     */
    private void checkCurrentQueen(int current){
        queens[current].setValidLocal(checkQueenHor(current)
                                        +checkQueenVer(current)
                                        +checkQueenDia(current));
    }// end of checkCurrentQueen
    
    /**
     * Method: checkQueenHor
     * Purpose: To check the current queen's row to see if other queens are in
     *          the same row
     * @param current
     *          -index of the current queen in the array
     * @return  conflicts
     *          -the number of other queens on the same row as the current queen
     */
    private int checkQueenHor(int current){
        int conflicts = 0;
        
        for(int index = 0; index < numberofqueens; index++){
            if(index != current){
                int row1 = queens[current].getRow();
                int row2 = queens[index].getRow();
                
                if(row1 == row2)
                    conflicts++;
            }// end of if statement
        }// end of for loop
        
        return conflicts;
    }// end of checkQueenHor
    
    /**
     * Method: checkQueenvar
     * Purpose: checks the column of the current queen and returns the number of
     *          other queens in the same column
     * @param current
     *          -index of the current queen in the array
     * @return conflicts
     *          -the number of other queens in the same column as the current
     *              queen
     */
    private int checkQueenVer(int current){
        int conflicts = 0;
        
        for(int index = 0; index < numberofqueens; index++){
            if(index != current){
                int col1 = queens[current].getColumn();
                int col2 = queens[index].getColumn();
                
                if(col1 == col2)
                    conflicts++;
            }// end of if statement
        }// end of for loop
        
        return conflicts;
    }// end of checkQueenVer
    
    /**
     * Method: checkQueenDia
     * Purpose: checks the queens array for any queen that has other queens in 
     *          the same diagonals
     * @param current
     *          -index of the current queen in the array
     * @return conflicts
     *          -the number of other queens
     */
    private int checkQueenDia(int current){
        int conflicts = 0;
        
        for(int index = 0; index < numberofqueens; index++){
            if(index != current){
                int row1 = queens[current].getRow();
                int row2 = queens[index].getRow();
                int col1 = queens[current].getColumn();
                int col2 = queens[index].getColumn();
                
                double slope = (double)(row1-row2)/(double)(col1-col2);
                
                if(Math.abs(slope) == 1)
                    conflicts++;
            }// end of if statement
        }// end of for loop
        
        return conflicts;
    }// end of checkQueenDia
}// end of Control Class
