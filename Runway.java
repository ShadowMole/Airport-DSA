/**
 * Created by Mole on 11/8/2017.
 */
public class Runway {
    private ResizableArrayQueue<Plane> planes;
    private String name;

    public Runway(String n){
        planes = new ResizableArrayQueue<>();
        name = n;
    }

    public void addPlane(Plane p){
        planes.enqueue(p);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResizableArrayQueue<Plane> getPlanes() {
        return planes;
    }

    public Plane takeOff(){
        return planes.dequeue();
    }

    public boolean isEmpty(){
        return planes.isEmpty();
    }

    public Plane reassign(){
        return planes.dequeue();
    }

    public String toString(){
        String s = "";
        if(planes.isEmpty()){
            s += "Runway " + name + " is empty.";
        }else {
            s += "Runway " + name + ":\n";
            s += planes;
        }
        return s;
    }
}