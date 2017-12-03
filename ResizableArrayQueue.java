
/**
 * Queue implementation of an array
 *
 */
public class ResizableArrayQueue<T> implements QueueInterface<T>{

    /**
     * Field declaration of the array items
     */
    protected T[] items;
    /**
     * Field declaration of numItems as an int value
     */
    protected int numItems;
    /**
     * Field declaration of back as an int value
     */
    protected int front;
    /**
     * Field declaration of back as an int value
     */
    protected int back;

    /**
     * Class constructor that initializes items to size 3, front to value 0, back to value 0, and numItems to value 0
     */
    public ResizableArrayQueue(){
        items = (T[]) new Object[3];
        front = 0;
        back = 0;
        numItems = 0;
    }

    /* (non-Javadoc)
     * @see QueueInterface#isEmpty()
     */
    public boolean isEmpty(){
        return numItems == 0;
    }

    /* (non-Javadoc)
     * @see QueueInterface#enqueue(java.lang.Object)
     */
    public void enqueue(T newItem) throws QueueException{
        if(numItems == items.length){
            resize();
        }
        items[back] = newItem;
        back = (back + 1) % items.length;
        numItems++;
    }

    /* (non-Javadoc)
     * @see QueueInterface#dequeue()
     */
    public T dequeue() throws QueueException{
        if(numItems == 0){
            throw new QueueException("Is Empty");
        }
        T returnItem = items[front];
        items[front] = null;
        front = (front + 1) % items.length;
        numItems--;
        return returnItem;
    }

    /* (non-Javadoc)
     * @see QueueInterface#dequeueAll()
     */
    public void dequeueAll(){
        items = (T[]) new Object[3];
        front = 0;
        back = 0;
        numItems = 0;
    }

    /* (non-Javadoc)
     * @see QueueInterface#peek()
     */
    public T peek() throws QueueException{
        if(numItems == 0){
            throw new QueueException("Is Empty");
        }
        return items[front];
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString(){
        if(numItems == 0){
            throw new QueueException("Is Empty");
        }
        String s = "";
        int index = front;
        for(int i = 0; i < numItems; i++){
            s += items[index] + " ";
            index = (index + 1) % items.length;
        }
        return s;
    }

    /**
     * Resizes the fixed sized array items
     */
    public void resize(){
        T[] newItems = (T[]) new Object[items.length * 2];
        int index = front;
        for(int i = 0; i < numItems; i++){
            newItems[i] = items[index];
            index = (index + 1) % items.length;
        }
        front = 0;
        back = numItems;
        items = newItems;
    }
}