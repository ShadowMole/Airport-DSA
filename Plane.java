/**
 * Created by Mole on 11/8/2017.
 */
public class Plane implements Comparable<Plane>{

    private String flightnumber;
    private String runway;
    private String destination;

    public Plane(String f, String r, String d){
        flightnumber = f;
        runway = r;
        destination = d;
    }

    public String getRunway() {
        return runway;
    }

    public void setRunway(String runway) {
        this.runway = runway;
    }

    public String getFlightNumber() {

        return flightnumber;
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
        return flightnumber.compareTo(p.getFlightNumber());
    }

    public boolean equals(Plane p){
        return flightnumber.equals(p.getFlightNumber());
    }
}