package nqueensproblem;

import java.util.Random;
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
    private static boolean is_solved = false; // is the problem solved
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
        String input = ""; // number of queens from user taken as a string
        int num = 0; // integer representation of input
        while(num <= 3){ // while the number inputed is too small
            try{
                System.out.print("Please enter the number of "
                        + "queens greater than 3: ");
                input = kin.next().trim(); // takes the input from the user 
                
                num = Integer.parseInt(input);// is the input a acutal number              
                
            if(num <=3) // if num was not high enough
                System.out.println("Number too low.");
            //end of if statement
            } catch(NumberFormatException err){ // user inputs a non-number
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
        bruteForceMethod(); // starts brute force method
        System.out.println("Iterative Repair: \n");
        iterativeRepairAlgorithm(); // starts iterative repair method
    }// end of solveNQueens
    
    /**
     * Method: printBoard
     * Purpose: prints the entire board with empty spaces represented as 0 and 
     *          positions where queens are located with 1's
     */
    private static void printBoard(){
        String board_row = "\n"; // holds the value for the complete board setup
        boolean piece_located = false; // has a position of a queen been located
        for(int row = 1; row <= numberofqueens; row++){
        // go through all rows on the board
            for(int col = 1; col <= numberofqueens; col++){
            // go through all columns on the board
                for(int index = 0; index < numberofqueens; index++){
                // go through all the queens in the queens array
                    if(queens[index].getColumn() == col
                            && queens[index].getRow() == row)
                    // if there is a piece located at row and col
                        piece_located = true;
                }// end of for loop

                if(piece_located){ // piece is at current indexes
                    board_row += "1 ";
                    piece_located = false;
                }
                else
                    board_row += "0 ";
                //end of if-else statements
            }// end of for loop
            board_row += "\n"; // new row
        }// end of for loop

        System.out.println(board_row); // output current board
    }// end of printBoard
    
    //========================Brute Force=======================================
    
    /**
     * Method: bruteForceMethod
     * Purpose: To time and start the brute force algorithm to solve the 
     *          N queens problem
     */
    private static void bruteForceMethod(){
        long time = System.nanoTime(); // starting time for algorithm
        
        moveQueen_BruteForce(); // starts the brute force algorithm
        
        long est_time = System.nanoTime()-time; // time algorithm took
        
        System.out.println("Time for brute force: "+est_time+" nanoseconds");
        //prints out time the algorithm took
        
        printBoard(); // print current board solution
    }// end of bruteForceMethod
    
    /**
     * Method: moveQueen_BruteForce
     * Purpose: is called to start the recursive algorithm and continue to run
     *          the recursive algorithm until the problem has been resolved
     */
    private static void moveQueen_BruteForce(){
        int index = numberofqueens - 1; // index of the last queen in the array
        
        while(!is_solved&& !unsolvable){
        // do while still solvable and not solved
            moveMinutePiece(index); // move the last piece in the queens array
            if(checkBruteForcePieces()){ // is the problem solved
                is_solved = true;
            }// end of if statement
        }// end of while loop
    }// end of moveQueen_BruteForce    
    
    /**
     * Method: moveMinutePiece
     * Purpose: A recursive method that moves the pieces on the board one at a
     *          time in a clock like fashion moving one piece to the end before
     *          moving the next piece in the array of queens
     * @param index - the current queen to be moved
     */
    private static void moveMinutePiece(int index){
        if(index < 0){
        // if index is less than the minimum index fail this amount of queens
            unsolvable = true;
            return;
        }// end of if statement
        
        int END_OF_BOARD = numberofqueens+1; // the end of the board
        queens[index].setColumn(queens[index].getColumn()+1);
        
        if(queens[index].getColumn() >= END_OF_BOARD){
        // if the queen is off of the board in current row
            queens[index].setRow(queens[index].getRow()+1); // switch rows
            queens[index].setColumn(1);
            
            while(queens[index].getRow() >= END_OF_BOARD){
            // while the current queen is off of the board
            
                moveMinutePiece(index - 1); 
                // move the previous queen in the array
                
                if(index != 0){
                // if the index is currently at 0
                    queens[index].setRow(queens[index - 1].getRow());
                    queens[index].setColumn(queens[index - 1].getColumn() + 1);                    
                }// end of if statement
                
                if (queens[index].getColumn() >= END_OF_BOARD) {
                // if the current queen is still off the board
                    queens[index].setRow(queens[index].getRow() + 1);
                    // switch rows                    
                    queens[index].setColumn(1);
                }// end of if statement
            }// end of while loop           
        }// end of if statement
    }// end of moveMinutePiece
    
    //=========================Iterative Repair=================================
    
    /**
     * Method: setupQueens
     * Purpose: creates a random board setup for the queens on a nxn board, 
     *          where all the queens have different rows and columns
     */
    private static void setupQueens(){
        int rows[] = new int[numberofqueens]; 
        // holds the row values for all posible placement for the queens
        for(int index = 0; index < rows.length; index++){ 
        // goes through rows array
            rows[index] = index+1;
        }// end of for loop;
        
        Random rand = new Random(); // creates a random number
        
        // placing the queens
        for(int index = 0; index < queens.length; index++){
        // goes through the entire array of queens
            queens[index].setColumn(index + 1); // set fixed column placements
            int rnd = -1;
            while(rnd == -1 || rows[rnd] == -1){
            // while the random row is not valid
                rnd = rand.nextInt(rows.length);
            }// end of while loop
            
            queens[index].setRow(rows[rnd]);
            //sets current queen with random row position
            
            rows[rnd] = -1; // empty current index
        }// end of for loop
    }// end of setupQueens
    
    /**
     * Method: iterativeRepairAlgorithm
     * Purpose: This method is the method called to execute and continues to 
     *           execute the iterative repair algorithm until the problem is 
     *           solved
     */
    private static void iterativeRepairAlgorithm(){
        // reseting global booleans
        is_solved = false;
        unsolvable = false;
        
        // setting up board
        setupQueens();
        
        // setting up basic varibles for method
        int last_queen = -1; // index of last moved queen
        int err_queen = -1; 
            // index of queen unable to be moved to a better position
        int move_queen = 0; // index of queen to be moved     
        
        // Timing variable
        long time = System.nanoTime();
        // solving the N Queens problem main loop
        while(!is_solved){
        // continue to run while the problem has not been solved
            move_queen = queenWithMostConflicts(last_queen, err_queen);
            if(!is_solved){ // is the problem solved yet?
                if(move_queen == -1){
                // if no queen was found to have the most conflicts
                // move the last moved queen or randomly select a new queen
                   move_queen = last_queen != -1 ? last_queen 
                                      : (int)(Math.random()*numberofqueens);
                } // end of if statement
                if(swapQueenRows(move_queen)){ // if the queen changes rows
                    last_queen = move_queen;
                    err_queen = -1;
                } else { // if swaping queen's row didn't work
                    if(err_queen == -1) // if err_queen doesn't exist yet
                        err_queen = move_queen;
                    else{ // err_queen exists
                        setupQueens();
                        err_queen = -1;
                    }// end of if-else statements
                }// end of if-else statements
            }// end of if statement
        }// end of while loop
        printBoard(); // prints the current board
        long est_time = System.nanoTime()-time; // total time for algorithm        
        System.out.println("Iterative Repair time: "+est_time+" nanoseconds");
    }// end of iterativeRepairAlgorithm
    
    /**
     * Method: queenWithMostConflicts
     * Purpose: finds the queen with the most conflicts in the array of queens
     * @param last_queen - the last moved queen
     * @param err_queen - queen has to possibility to be moved to a location
     *                      with less conflicts
     * @return int index - the index in the array with the queen with the most
     *                      conflicts
     */
    private static int queenWithMostConflicts(int last_queen, int err_queen){
        is_solved = checkQueens(); // is the default position a solution
        
        int most_conflicts = 0; 
        // holds the highest number of conflicts from a queen
        int move_queen = -1; // holds the index of the queen to move
                
        if(!is_solved){// only tries this when the board is not solved       
            for(int index = 0; index < queens.length; index++){
                   if((index != last_queen && index != err_queen)
                           && queens[index].getConflicts() > most_conflicts){
                       //if the queen has more conflicts than the stored value
                       most_conflicts = queens[index].getConflicts();
                       move_queen = index;
                   } else if((index != last_queen && index != err_queen)
                           && queens[index].getConflicts() == most_conflicts){
                     // if valid index and has same number of conflicts
                       int rnd = (int) Math.random()*2;
                       if(rnd == 1){
                       // randomly decides to switch other queen or not
                           most_conflicts = queens[index].getConflicts();
                           move_queen = index;
                       }// end of if statement
                   }// end of if-else if statement
            }// end of for loop
        }// end of if statement
        
        return move_queen;
    }// end of queenWithMostConflicts()
    
    /**
     * Method: swapQueenRows
     * Purpose: To change the current queen's position to one with less conflicts
     * @param int current - index of the current queen with the most conflicts in 
     *                  the array of queens
     * @return boolean 
     *          -true: swap was successful
     *          -false: swap was unsuccessful
     */
    private static boolean swapQueenRows(int current){
        // setting base varibles for method
       int old_row = queens[current].getRow(); // queen's current position
       int new_row = 0; // position with the least number of conflicts
       int high_conflicts = queens[current].getConflicts(); 
            // current number of conflicts with queen's current position
       boolean changed = false; // did the change in row happen
        
       // finding square in column with least amount of conflicts
        for(int row = 1; row < numberofqueens; row++){
            queens[current].setRow(row); // sets potential new position
            checkCurrentQueen(current); // checks new position
            
            if(queens[current].getConflicts() < high_conflicts){
                // if the current queen placement has less conflicts 
                // than other position
                high_conflicts = queens[current].getConflicts();
                new_row = row;
                changed = true;                
            }// end of if statement
            else if(queens[current].getConflicts() == high_conflicts){
                // current queen matches another queen's total number of conflicts
                int rnd = (int) Math.random() * 2; // random number
                if(rnd == 1){ // to randomly select a piece to move
                    high_conflicts = queens[current].getConflicts();
                    new_row = row;
                    changed = true;
                } // end of if statement
            } // end of if-else if statements
        }// end of for loop
        
        if(changed) // if the row was changed
            queens[current].setRow(new_row);
        else // row wasn't changed
            queens[current].setRow(old_row);
        
        return changed;
    }// end of swapQueenColumns
    
    //=======================Checking Algoritms=================================
    
    /**
     * Method: checkBruteForcePieces
     * Purpose: to check the brute force algorithm current placement of pieces
     *          is a solution to the N Queens problem
     * @return true if solve
     * @return false if not solved
     */
    private static boolean checkBruteForcePieces(){
        for(int index = 0; index < queens.length-1; index++){
            // from start of array to the 2nd to last index
            for(int check = index + 1; check < queens.length; check++){
                // from the current index +1 to the last queen in the array
                if(queens[index].getRow() == queens[check].getRow())
                    // checks horizontal
                    return false;
                else if(queens[index].getColumn()== queens[check].getColumn())
                    // checks verticle
                    return false;
                //end of if-elseif statements
                
                int row1 = queens[index].getRow(); // row 1 of queen 1
                int row2 = queens[check].getRow(); // row 2 of queen 2
                int col1 = queens[index].getColumn(); // column 1 of queen 1
                int col2 = queens[check].getColumn(); // column 2 of queen 2
                
                //q1 and q2 hold the values of the sums and differences between
                //row and column one and two respectivly
                int q1 = row1-col1;
                int q2 = row2-col2;
                
                if(q1==q2){ // do the differences
                    return false;
                }// end of if statemement
                
                q1 = row1+col1;
                q2 = row2+col2;
                
                if(q1==q2){ // do the sums equal
                    return false;
                } // end of if statement
            }// end of for loop
        }// end of for loop        
        return true;
    }// end of checkBruteForcePieces

    /**
     * Method: checkQueens
     * Purpose: goes through the array of queens to find out if the problem has
     *          been solved
     */
    private static boolean checkQueens(){
        boolean solved = true; // return variable
        for(int index = 0; index < numberofqueens; index++){ // check all queens
            queens[index].setConflicts(0); // resets conflicts of queen
            checkCurrentQueen(index); //checks queen and index
            if(queens[index].getConflicts() > 0) // if there is a conflict
                solved = false;
        }// end of for loop
        
        return solved;
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
        queens[current].setConflicts(checkQueenHor(current)
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
        int conflicts = 0; // number of conflicts
        
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
     * Method: checkQueenDia
     * Purpose: checks the queens array for any queen that has other queens in 
     *          the same diagonals
     * @param current
     *          -index of the current queen in the array
     * @return conflicts
     *          -the number of other queens
     */
    private static int checkQueenDia(int current){
        int conflicts = 0; // number of conflicts
        
        for(int index = 0; index < numberofqueens; index++){
            if(index != current){ // if not checking itself
                int row1 = queens[current].getRow(); // row of queen 1
                int row2 = queens[index].getRow(); // row of queen 2
                int col1 = queens[current].getColumn(); // column of queen 1
                int col2 = queens[index].getColumn(); // column of queen 2
                
                //q1 and q2 hold the values of the sums and differences between
                //row and column one and two respectivly
                int q1 = row1-col1; 
                int q2 = row2-col2;
                
                if(q1==q2){ // do the differences equal
                    conflicts++;
                }
                
                q1 = row1+col1;
                q2 = row2+col2;
                
                if(q1==q2){ // do the sums equal
                    conflicts++;
                }
            }// end of if statement
        }// end of for loop
        
        return conflicts;
    }// end of checkQueenDia
}// end of Control Class
