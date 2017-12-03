/**
 * Created by Mole on 11/8/2017.
 */
public class Runway {
    /**
     * Field declaration of order as an int value
     */
    private int order;
    /**
     * Field declaration of name as an int value
     */
    private String name;
    /**
     * Field declaration of active as a boolean value
     */
    private boolean active;

    /**
     * Class constructor that initialized order to the o parameter, name to the n parameter, and active to true
     * active is set to true because once a runway is opened in the driver it is then active, if the runway is closed then active is assigned to false
     * 
     * @param n
     * @param o
     */
    public Runway(String n, int o){
        order = o;
        name = n;
        active = true;
    }

    /**
     * Getter for the order data field
     * 
     * @return order
     */
    public int getOrder(){
        return order;
    }

    /**
     * Getter for the name data field
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Will set the active data field to false if the runway is no longer active
     */
    public void setInactive(){
        active = false;
    }

    /**
     * Returns the boolean value of active, true if the runway is active and false if the runway if inactive
     * 
     * @return boolean
     */
    public boolean getActive(){
        return active;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString(){
        return getName();
    }
}