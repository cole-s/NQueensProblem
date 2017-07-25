package nqueensproblem;

/**
 * Queen Class handles all of the data needed for tracking position and all
 * intersections with other Queens
 * @author scha7843
 */
public class Queen {
    private int row; // stores row value of Queen
    private int column; // stores collumn value of Queen
    private int validlocal; //is a valid location boolean for Queen object
                            // 0 for yes and anything else is # of conflicts
                            // with placement
    
    public Queen(){
        row = 0;
        column = 1;
        validlocal = 0;
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
    public int getValidLocal(){ return validlocal; }
    public void setValidLocal(int conflicts) { validlocal = conflicts; }

} // end of Queen Class
