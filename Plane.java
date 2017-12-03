/**
 * Created by Mole on 11/8/2017.
 */
public class Plane implements Comparable<Plane>{

    private String flightnumber;
    private int runway;
    private String destination;
    private int order;

    public Plane(String f, int r, String d){
        flightnumber = f;
        runway = r;
        destination = d;
    }

    public int getRunway() {
        return runway;
    }

    public void setRunway(int runway) {
        this.runway = runway;
    }

    public String getFlightNumber() {

        return flightnumber;
    }

    public int setOrder(int order){
        this.order = order;
    }

    public void setFlightNumber(String flightnumber) {
        this.flightnumber = flightnumber;
    }

    public String toString(){
        return flightnumber + " to " + destination;
    }

    public String getDestination(){
        return destination;
    }

    public void setDestination(String d){
        destination = d;
    }

    @Override
    public int compareTo(Plane p){
        return p.getRunway() - runway;
    }

    public boolean equals(Plane p){
        return runway == p.getRunway();
    }

    public int getOrder() {
        return order;
    }
}