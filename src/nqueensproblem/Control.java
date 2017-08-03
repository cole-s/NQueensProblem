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
    private static int time_array_index = 0;
    private static long[] brute_force_times = new long[100];
    private static long[] iterative_repair_times = new long[100];
    
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
                input = kin.next().trim();  
                
                num = Integer.parseInt(input);                
                
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
//        for(time_array_index = 0; time_array_index < brute_force_times.length; time_array_index++){
            bruteForceMethod();
            is_solved = false;
//        }
        System.out.println("Iterative Repair: \n");
//        for(time_array_index = 0; time_array_index < iterative_repair_times.length; time_array_index++){
            iterativeRepairAlgorithm();
            is_solved = false;
//        }
        
        long brute_avg = 0;
        int iterative_avg = 0;
        
//        for(time_array_index = 0; time_array_index < 100; time_array_index++){
//            brute_avg += brute_force_times[time_array_index];
//            iterative_avg += iterative_repair_times[time_array_index];
//        }
        
        System.out.println("Brute force averge time: "+(brute_avg/100));
        System.out.println("Iterative Repair average time: "+(iterative_avg/100));
    }// end of solveNQueens
    
    private static void printBoard(){
        String board_row = "\n";
        boolean piece_located = false;
        for(int row = 1; row <= numberofqueens; row++){
            for(int col = 1; col <= numberofqueens; col++){
                for(int index = 0; index < numberofqueens; index++){
                    if(queens[index].getColumn() == col
                            && queens[index].getRow() == row)

                        piece_located = true;
                }

                if(piece_located){
                    board_row += "1 ";
                    piece_located = false;
                }
                else
                    board_row += "0 ";
            }
            board_row += "\n";
        }

        System.out.println(board_row);
    }
    
    //========================Brute Force=======================================
    private static void resetQueens(){
        for(int index=0; index < numberofqueens; index++){
            queens[index].setRow(1);             
            queens[index].setColumn(index+1); // sets queens in rows 1-N
        }// end of for loop
    }
    private static void bruteForceMethod(){
        resetQueens();
        long time = System.nanoTime();
        moveQueen_BruteForce();
        long est_time = System.nanoTime()-time;
//        brute_force_times[time_array_index] = est_time;
//        time_array_index++;
        System.out.println("Time for brute force: "+est_time+" nanoseconds");
        //printBoard();
    }// end of bruteForceMethod
    
    private static void moveQueen_BruteForce(){
        int index = numberofqueens - 1;
        
        while(!is_solved&& !unsolvable)  {
            moveMinutePiece(index);
            if(checkBruteForcePieces()){
                is_solved = true;
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
    
    /**
     * Method: setupQueens
     * Purpose: creates a random board setup for the queens on a nxn board, 
     *          where all the queens have different rows and columns
     */
    private static void setupQueens(){
        int rows[] = new int[numberofqueens];
        for(int index = 0; index < rows.length; index++){
            rows[index] = index+1;
        }// end of for loop;
        
        Random rand = new Random();
        // placing the queens
        for(int index = 0; index < queens.length; index++){
            queens[index].setColumn(index + 1);
            int rnd = -1;
            while(rnd == -1 || rows[rnd] == -1){
                rnd = rand.nextInt(rows.length);
            }// end of while loop
            queens[index].setRow(rows[rnd]);
            
            rows[rnd] = -1;
        }// end of for loop
    }// end of setupQueens
    
    /**
     * Method: iterativeRepairAlgorithm
     * Purpose: This method is the method called to execute
     */
    private static void iterativeRepairAlgorithm() throws IndexOutOfBoundsException{
        // reseting global booleans
        is_solved = false;
        unsolvable = false;
        
        // setting up board
        setupQueens();
        
        // setting up basic varibles for method
        int last_queen = -1;
        int err_queen = -1;
        int move_queen = 0;        
        
        // Timing variable
        long time = System.nanoTime();
        // solving the N Queens problem main loop
        while(!is_solved){
            move_queen = queenWithMostConflicts(last_queen, err_queen);
            if(!is_solved){
                if(move_queen == -1){
                   move_queen = last_queen != -1 ? last_queen 
                                      : (int)(Math.random()*numberofqueens + 1);
                }
                if(swapQueenRows(move_queen)){
                    last_queen = move_queen;
                    err_queen = -1;
                } else {
                    if(err_queen == -1)
                        err_queen = move_queen;
                    else{
                        setupQueens();
                        err_queen = -1;
                    }
                }
            }
        }// end of while loop

//        printBoard(); 
        long est_time = System.nanoTime()-time;
        
        System.out.println("Iterative Repair time: "+est_time+" nanoseconds");
//        iterative_repair_times[time_array_index] = est_time;
    }// end of iterativeRepairAlgorithm
    
    /**
     * 
     * @param last_queen
     * @param err_queen
     * @return
     */
    private static int queenWithMostConflicts(int last_queen, int err_queen){
        is_solved = checkQueens(); 
        
        int most_conflicts = 0;
        int move_queen = -1;
                
        if(!is_solved){     // only tries this when the board is not solved       
            for(int index = 0; index < queens.length; index++){
                   if((index != last_queen && index != err_queen)
                           && queens[index].getConflicts() > most_conflicts){
                       most_conflicts = queens[index].getConflicts();
                       move_queen = index;
                   } else if((index != last_queen && index != err_queen)
                           && queens[index].getConflicts() == most_conflicts){
                       int rnd = (int) Math.random()*2;
                       if(rnd == 1){
                           most_conflicts = queens[index].getConflicts();
                           move_queen = index;
                       }// end of if statement
                   }// end of if-else if statement
            }// end of for loop
        }// end of if statement
        
        return move_queen;
    }// end of queenWithMostConflicts()
    
    /**
     * 
     * @param int current - index of the current queen with the most conflicts in 
     *                  the array of queens
     * @return boolean
     */
    private static boolean swapQueenRows(int current){
        // setting base varibles for method
       int old_row = queens[current].getRow();
       int new_row = 0;
       int high_conflicts = queens[current].getConflicts();
       boolean changed = false;
        
       // finding square in column with least amount of conflicts
        for(int row = 1; row < numberofqueens; row++){
            queens[current].setRow(row);
            checkCurrentQueen(current);
            
            if(queens[current].getConflicts() < high_conflicts){
                high_conflicts = queens[current].getConflicts();
                new_row = row;
                changed = true;                
            }// end of if statement
            else if(queens[current].getConflicts() == high_conflicts){
                int rnd = (int) Math.random() * 2;
                if(rnd == 1){
                    high_conflicts = queens[current].getConflicts();
                    new_row = row;
                    changed = true;
                }
            }
        }// end of for loop
        
        if(changed)
            queens[current].setRow(new_row);
        else
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
            for(int check = index + 1; check < queens.length; check++){
                if(queens[index].getRow() == queens[check].getRow()) // checks horizontal
                    return false;
                else if(queens[index].getColumn()== queens[check].getColumn()) // checks verticle
                    return false;
                //end of if-elseif statements
                
                int row1 = queens[index].getRow();
                int row2 = queens[check].getRow();
                int col1 = queens[index].getColumn();
                int col2 = queens[check].getColumn();
                
                int q1 = row1-col1;
                int q2 = row2-col2;
                
                if(q1==q2){
                    return false;
                }// end of if statemement
                
                q1 = row1+col1;
                q2 = row2+col2;
                
                if(q1==q2){
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
        boolean solved = true;
        for(int index = 0; index < numberofqueens; index++){
            queens[index].setConflicts(0);
            checkCurrentQueen(index); 
            if(queens[index].getConflicts() > 0)
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
    
    //Debug method, not needed for solution
    private static void printQueenLocations(){
        for(int index = 0; index < queens.length; index++){
            System.out.print("Queen #" + index + ": ");
            queens[index].printLocal();
        }
    }
}// end of Control Class
