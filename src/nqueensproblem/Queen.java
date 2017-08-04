package nqueensproblem;

/**
 * Queen Class handles all of the data needed for tracking position and all
 * intersections with other Queens
 * @author scha7843
 */
public class Queen {
    private int row; // stores row value of Queen
    private int column; // stores collumn value of Queen
    private int conflicts; //is a valid location boolean for Queen object
                            // 0 for yes and anything else is # of conflicts
                            // with placement
    
    /**
     * Method: Queen
     * Purpose: Constructor for the Queen class and assigns default values
     */
    public Queen(){
        row = 1;
        column = 0;
        conflicts = 0;
    }// end of Constructor
    
    /**
     * Getters and Setters for the private variables in the Queen class
     * 
     * Parameters for setters are integers and booleans
     * returns integers and booleans for getters
     */
    public int getRow(){ return row; }
    public void setRow(int num){ row = num; }
    public int getColumn() { return column; }
    public void setColumn(int num){ column = num; }
    public int getConflicts(){ return conflicts; }
    public void setConflicts(int con) { conflicts = con; }
    
    /**
     * Method: printLocal
     * Purpose: prints the queen's current position and number of conflicts 
     *          for easier debugging
     */
    public void printLocal(){
        System.out.println("[" + row + ", " + column + "]" 
                + "Conflicts: " + conflicts);
    }// end of printLocal

} // end of Queen Class
