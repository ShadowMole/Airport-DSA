/**
 * Runway class that has a ResizableArrayQueue of planes.
 */
public class Runway {
    /**
     * Field declaration of the ResizableArrayQueue planes of type Plane
     */
    private ResizableArrayQueue<Plane> planes;
    /**
     * Field declaration of name as a String
     */
    private String name;

    /**
     * Class constructor that creates a new ResizableArrayQueue and assigns it to planes, and name is assigned the parameter n
     * @param n
     */
    public Runway(String n){
        planes = new ResizableArrayQueue<>();
        name = n;
    }

    /**
     * Adds a plane to the planes queue
     * 
     * @param p
     */
    public void addPlane(Plane p){
        planes.enqueue(p);
    }

    /**
     * Getter for the name field
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name field
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the ResizableArrayQueue<Plane> planes
     * 
     * @return planes
     */
    public ResizableArrayQueue<Plane> getPlanes() {
        return planes;
    }

    /**
     * Dequeues a plane from the planes queue
     * 
     * @return planes.dequq()
     */
    public Plane takeOff(){
        return planes.dequeue();
    }

    /**
     * Checks to see if the planes queue is empty or not
     * If empty the return would be true
     * 
     * @return planes.isEmpty() 
     */
    public boolean isEmpty(){
        return planes.isEmpty();
    }

    /**
     * Dequeues a plane from the planes queue that will be later reassigned
     * 
     * @return planes.dequeue()
     */
    public Plane reassign(){
        return planes.dequeue();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
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