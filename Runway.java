/**
 * Created by Mole on 11/8/2017.
 */
public class Runway {
    private int order;
    private String name;
    private boolean active;

    public Runway(String n, int o){
        order = o;
        name = n;
        active = true;
    }

    public int getOrder(){
        return order;
    }

    public String getName() {
        return name;
    }

    public void setInactive(){
        active = false;
    }

    public boolean getActive(){
        return active;
    }

    public String toString(){
        return getName();
    }
}