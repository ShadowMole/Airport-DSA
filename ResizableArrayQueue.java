
/**
 * Write a description of class ResizableArrayQueue here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ResizableArrayQueue<T> implements QueueInterface<T>{

    protected T[] items;
    protected int numItems;
    protected int front;
    protected int back;

    public ResizableArrayQueue(){
        items = (T[]) new Object[3];
        front = 0;
        back = 0;
        numItems = 0;
    }

    public boolean isEmpty(){
        return numItems == 0;
    }

    public void enqueue(T newItem) throws QueueException{
        if(numItems == items.length){
            resize();
        }
        items[back] = newItem;
        back = (back + 1) % items.length;
        numItems++;
    }

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

    public void dequeueAll(){
        items = (T[]) new Object[3];
        front = 0;
        back = 0;
        numItems = 0;
    }

    public T peek() throws QueueException{
        if(numItems == 0){
            throw new QueueException("Is Empty");
        }
        return items[front];
    }

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

    public void resize(){
        T[] newItems = (T[]) new Object[items.length * 2];
        int index = front;
        for(int i = 0; i < numItems; i++){
            newItems[i] = items[index];
            index++;
            index = (index + 1) % items.length;
        }
        front = 0;
        back = numItems;
        items = newItems;
    }
}