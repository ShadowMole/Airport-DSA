/**
 * Purpose: Data Structure and Algorithms Lab 2 Problem 1
 * Status: Complete and thoroughly tested
 * Last update: 09/13/17
 * Submitted:  09/19/17
 * Comment: test suite and sample run attached
 * @author: Steven Bruman
 * @version: 2017.09.13
 */
// ********************************************************
// Array-based implementation of the ADT list.
// *********************************************************
public class ListArrayBased<T> implements ListInterface<T>{

    /**
     * Field declaration of the max size of the items array, assigned to the value 3
     */
    private static final int MAX_LIST = 3;
    /**
     * Field declaration of the generic array items
     */
    protected T[] items;  // an array of list items
    /**
     * Field declaration of numItems as an int value
     */
    protected int numItems;  // number of items in list

    /**
     * Class constructor, that initializes items to size 3, and numItems to the value 0
     */
    public ListArrayBased(){
        items = (T[]) new Object[3];
        numItems = 0;
    }  // end default constructor

    /* (non-Javadoc)
     * @see ListInterface#isEmpty()
     */
    public boolean isEmpty(){
        return (numItems == 0);
    } // end isEmpty

    /* (non-Javadoc)
     * @see ListInterface#size()
     */
    public int size(){
        return numItems;
    }  // end size

    /* (non-Javadoc)
     * @see ListInterface#removeAll()
     */
    public void removeAll(){
        // Creates a new array; marks old array for
        // garbage collection.
        items = (T[]) new Object[3];
        numItems = 0;
    } // end removeAll

    /* (non-Javadoc)
     * @see ListInterface#add(int, java.lang.Object)
     */
    public void add(int index, T item)throws  ListIndexOutOfBoundsException{
        if (numItems == items.length)//fixes implementation error and programming style
        {
            throw new ListException("ListException on add");
        }  // end if
        if (index >= 0 && index <= numItems)
        {
            // make room for new element by shifting all items at
            // positions >= index toward the end of the
            // list (no shift if index == numItems+1)
            for (int pos = numItems-1; pos >= index; pos--)  //textbook code modified to eliminate logic error causing ArrayIndexOutOfBoundsException
            {
                items[pos+1] = items[pos];
            } // end for
            // insert new item
            items[index] = item;
            numItems++;
        }
        else{
            // index out of range
            throw new ListIndexOutOfBoundsException("ListIndexOutOfBoundsException on add");
        }  // end if
    } //end add

    /* (non-Javadoc)
     * @see ListInterface#get(int)
     */
    public T get(int index)throws ListIndexOutOfBoundsException{
        if (index >= 0 && index < numItems){
            return items[index];
        }else{
            // index out of range
            throw new ListIndexOutOfBoundsException(
                    "ListIndexOutOfBoundsException on get");
        }  // end if
    } // end get

    /* (non-Javadoc)
     * @see ListInterface#remove(int)
     */
    public void remove(int index)throws ListIndexOutOfBoundsException{
        if (index >= 0 && index < numItems){
            // delete item by shifting all items at
            // positions > index toward the beginning of the list
            // (no shift if index == size)
            for (int pos = index+1; pos < numItems; pos++) //textbook code modified to eliminate logic error causing ArrayIndexOutOfBoundsException
            {
                items[pos-1] = items[pos];
            }  // end for
            items[numItems - 1] = null; //Fixes Memory Leak
            numItems--;
        }
        else{
            // index out of range
            throw new ListIndexOutOfBoundsException(
                    "ListIndexOutOfBoundsException on remove");
        }  // end if
    } //end remove
}