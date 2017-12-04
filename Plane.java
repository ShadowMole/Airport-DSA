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

    public void setOrder(int order){
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

    public int getOrder() {
        return order;
    }
}