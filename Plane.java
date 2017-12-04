/**
 * Plane class that has a plane's flightnumber, runway, destination, and order
 */
public class Plane implements Comparable<Plane>{

    /**
     * Field declaration of String flightnumber
     */
    private String flightnumber;
    /**
     * Field declaration of runway as an int value
     */
    private int runway;
    /**
     * Field declaration of String destination
     */
    private String destination;
    /**
     * Field declaration of order as an int value
     */
    private int order;

    /**
     * Class constructor that initializes flight number to the f parameter, runway to the r parameter, and destination to the d parameter
     * 
     * @param f
     * @param r
     * @param d
     */
    public Plane(String f, int r, String d){
        flightnumber = f;
        runway = r;
        destination = d;
    }

    /**
     * Getter for the runway data field
     * 
     * @return runway
     */
    public int getRunway() {
        return runway;
    }

    /**
     * Setter for the runway data field
     * 
     * @param runway
     */
    public void setRunway(int runway) {
        this.runway = runway;
    }

    /**
     * Getter for the flightnumber data field
     * 
     * @return flightnumber
     */
    public String getFlightNumber() {

        return flightnumber;
    }

    /**
     * Setter for the order data field
     * 
     * @param order
     */
    public void setOrder(int order){
        this.order = order;
    }

    /**
     * Setter for the flightnumber data field
     * 
     * @param flightnumber
     */
    public void setFlightNumber(String flightnumber) {
        this.flightnumber = flightnumber;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString(){
        return flightnumber + " to " + destination;
    }

    /**
     * Getter for the destination data field
     * 
     * @return destination
     */
    public String getDestination(){
        return destination;
    }

    /**
     * Setter for the destination data field
     * 
     * @param d
     */
    public void setDestination(String d){
        destination = d;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Plane p){
        return p.getRunway() - runway;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o){
        if(o == this){
            return true;
        }

        if(!(o instanceof Plane)){
            return false;
        }

        Plane p = (Plane) o;
        if(runway == p.getRunway()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Getter for the order data field
     * 
     * @return order
     */
    public int getOrder() {
        return order;
    }
}