package nqueensproblem;

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
        String input = ""; // number of queens from user
        int num = 0;
        while(num <= 3){
            try{
                System.out.print("Please enter the number of "
                        + "queens greater than 3: ");
                input = kin.next();
                num = Integer.parseInt(input.trim());                
                
            if(num <=3) // if num was not high enough
                System.out.println("Number too low.");
            //end of if statement
            } catch(NumberFormatException err){
                System.out.println("Not a number.");
            } // end of try-catch statement
            
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
            queens[index].setColumn(index+1); // sets queens in rows 1-N
        }// end of for loop
    }// end of createQueenArray();
    
    /**
     * Method: solveNQueens
     * Purpose: is to be called by the user to start solving the assignment with
     *          size given to the numberofqueens variable
     * Parameters: None
     * Returns: Nothing
     */
    public static void solveNQueens(){
        bruteForceMethod();
    }// end of solveNQueens
    
    //========================Brute Force=======================================
    
    private static void bruteForceMethod(){
        moveQueen_BruteForce();
        
        String board_row = "";
        boolean piece_located = false;
        for(int row = 1; row <= numberofqueens; row++){
            for(int col = 1; col <= numberofqueens; col++){
                for(int index = 0; index < numberofqueens; index++){
                    if(queens[index].getColumn() == col
                            && queens[index].getRow() == row)

                        piece_located = true;
                }

                if(piece_located){
                    board_row += "1";
                    piece_located = false;
                }
                else
                    board_row += "0";
            }
            board_row += "\n";
        }

        System.out.println(board_row);
    }// end of bruteForceMethod
    
    private static void moveQueen_BruteForce(){
        int index = numberofqueens - 1;
        int END_OF_BOARD = numberofqueens+1;
        
        while(!isSolved&& !unsolvable)  {
            moveMinutePiece(index);
            
            if(checkBruteForcePieces()){
                isSolved = true;
            }
        }    
    }// end of moveQueen_BruteForce    
    
    private static void moveMinutePiece(int index){
        //System.out.println("moveMinutePiece method");
        if(index < 0){
            unsolvable = true;
            return;
        }
        
        int END_OF_BOARD = numberofqueens+1;
        queens[index].setColumn(queens[index].getColumn()+1);
        
        if(queens[index].getColumn() >= END_OF_BOARD){
            queens[index].setRow(queens[index].getRow()+1);
            queens[index].setColumn(1);
            
            while(queens[index].getRow() >= END_OF_BOARD){
                
                moveMinutePiece(index - 1);
                if(index != 0){
                    queens[index].setRow(queens[index - 1].getRow());
                    queens[index].setColumn(queens[index - 1].getColumn() + 1);
                    
                }
                if (queens[index].getColumn() >= END_OF_BOARD) {
    
                    queens[index].setRow(queens[index].getRow() + 1);
                    queens[index].setColumn(1);
                }
                }                
            }
        }
    
    //=========================Iterative Repair=================================
    
    
    //=======================Checking Algoritms=================================
    
        private static boolean checkBruteForcePieces(){
        for(int index = 0; index < queens.length-1; index++){
            for(int check = index + 1; check < queens.length; check++){
                if(queens[index].getRow() == queens[check].getRow())
                    return false;
                else if(queens[index].getColumn()== queens[check].getColumn())
                    return false;
                
                int row1 = queens[index].getRow();
                int row2 = queens[check].getRow();
                int col1 = queens[index].getColumn();
                int col2 = queens[check].getColumn();
                
                int q1 = row1-col1;
                int q2 = row2-col2;
                
                if(q1==q2){
                    return false;
                }
                
                q1 = row1+col1;
                q2 = row2+col2;
                
                if(q1==q2){
                    return false;
                }
            }
        }        
        return true;
    }

    /**
     * Method: checkQueens
     * Purpose: goes through the array of queens to find out if the problem has
     *          been solved
     * @param
     * @return true - if all queens are in a valid location
     *         false - if one or more queens have one or more conflicts with 
     *                  other queens
     */
    private static boolean checkQueens(){
        for(int index = 0; index < numberofqueens; index++){
            queens[index].setValidLocal(0);
            checkCurrentQueen(index); 
        }
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
    private static void checkCurrentQueen(int current){
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
    private static int checkQueenHor(int current){
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
    private static int checkQueenVer(int current){
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
    private static int checkQueenDia(int current){
        int conflicts = 0;
        
        for(int index = 0; index < numberofqueens; index++){
            if(index != current){
                int row1 = queens[current].getRow();
                int row2 = queens[index].getRow();
                int col1 = queens[current].getColumn();
                int col2 = queens[index].getColumn();
                
                int q1 = row1-col1;
                int q2 = row2-col2;
                
                if(q1==q2){
                    conflicts++;
                }
                
                q1 = row1+col1;
                q2 = row2+col2;
                
                if(q1==q2){
                    conflicts++;
                }
            }// end of if statement
        }// end of for loop
        
        return conflicts;
    }// end of checkQueenDia
    
    private static void printQueenLocations(){
        for(int index = 0; index < queens.length; index++){
            System.out.print("Queen #" + index + ": ");
            queens[index].printLocal();
        }
    }
}// end of Control Class
