package nqueensproblem;

/**
 * Queen Class handles all of the data needed for tracking position and all
 * intersections with other Queens
 * @author scha7843
 */
public class Queen {
    private int row; // stores row value of Queen
    private int collumn; // stores collumn value of Queen
    private boolean validlocal; //is a valid location boolean for Queen object
    
    /**
     * Getters and Setters for the private variables in the Queen class
     * 
     * Parameters for setters are integers and booleans
     * returns integers and booleans for getters
     */
    public int getRow(){ return row; }
    public void setRow(int num){ row = num; }
    public int getCollumn() { return collumn; }
    public void setCollumn(int num){ collumn = num; }
    public boolean getValidLocal(){ return validlocal; }
    public void setValidLocal(boolean bool) { validlocal = bool; }
} // end of Queen Class
