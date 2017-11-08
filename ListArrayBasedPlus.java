/**
 * Purpose: Data Structure and Algorithms Lab 2 Problem 1
 * Status: Complete and thoroughly tested
 * Last update: 09/13/17
 * Submitted:  09/19/17
 * Comment: test suite and sample run attached
 * @author: Steven Bruman
 * @version: 2017.09.13
 */
public class ListArrayBasedPlus<T> extends ListArrayBased<T>{

    /**
     * Constructor for objects of class ListArrayBasedPlus
     */
    public ListArrayBasedPlus(){
        super();
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y
     */
    public void add(int index, T item)throws  ListIndexOutOfBoundsException{
        if (numItems == items.length)//fixes implementation error and programming style
        {
            resize();
        }
        super.add(index, item);
    }

    public String toString(){
        String info = "List of size " + numItems + " has the following items: \t";
        for(int i = 0; i < numItems; i++){
            info += items[i] + " ";
        }
        return info;
    }

    public void resize(){
        T[] newItems = (T[]) new Object[items.length * 2];
        for(int i = 0; i < items.length; i++){
            newItems[i] = items[i];
        }
        items = newItems;
    }
}