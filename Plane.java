	/**
     * The plane sets the flightnumber, runway, and destination of each plane object that is added to the planes list in the driver
     */
public class Plane implements Comparable<Plane>{

    /**
     * Field declaration of the String flightnumber
     */
    private String flightnumber;
    /**
     * Field declaration of runway as an int value
     */
    private int runway;
    /**
     * Field declaration of the String destination
     */
    private String destination;
    /**
     * Field declaration of order as an int value
     */
    private int order;

    /**
     * Class constructor that takes in 3 parameters. It also initializes flightnumer to the f parameter, runway to the r parameter, and destination to the d parameter
     * 
     * @param f flight number
     * @param r ruwnay name
     * @param d destination name
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
     * @param runway
     */
    public void setRunway(int runway) {
        this.runway = runway;
    }

    /**
     * Getter for the flightnumber data field
     * 
     * @return
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
     * @return
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

    /**
     * Checks to see if the runway data field matches to the runway the plane is assigned
     * 
     * @param p
     * @return boolean
     */
    public boolean equals(Plane p){
        return runway == p.getRunway();
    }

    /**
     * Getter for the order data field
     * 
     * @return
     */
    public int getOrder() {
        return order;
    }
}