/**
 * Created by Mole on 11/8/2017.
 */
public class Runway {
    private int order;
    private String name;

    public Runway(String n, int o){
        order = o;
        name = n;
    }

    public int getOrder(){
        return order;
    }

    public String getName() {
        return name;
    }

    public String toString(){
        return getName();
    }
}