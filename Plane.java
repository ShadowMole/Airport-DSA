/**
 * Created by Mole on 11/8/2017.
 */
public class Plane {

    private int flightnumber;
    private String runway;

    public Plane(int f, String r){
        flightnumber = f;
        runway = r;
    }

    public String getRunway() {
        return runway;
    }

    public void setRunway(String runway) {
        this.runway = runway;
    }

    public int getFlightNumber() {

        return flightnumber;
    }

    public void setFlightNumber(int flightnumber) {
        this.flightnumber = flightnumber;
    }

    public String toString(){
        return Integer.toString(flightnumber);
    }
}