
/**
 * Write a description of class AscendinglyOrderedStringList here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class AscendinglyOrderedList <T extends Comparable<T>> implements AscendinglyOrderedListInterface <T>{

    T[] items;
    int numItems;

    public AscendinglyOrderedList(){
        items = (T[]) new Comparable[3];
    }  // end default constructor

    public void add(T item){
        if (numItems == items.length){//fixes implementation error and programming style
            resize();
        }
        int index = search(item);
        if(index > numItems){
            index -= (2 * numItems);
        }
        for (int pos = numItems-1; pos >= index; pos--)  //textbook code modified to eliminate logic error causing ArrayIndexOutOfBoundsException
        {
            items[pos+1] = items[pos];
        } // end for
        // insert new item
        items[index] = item;
        numItems++;
    }

    public int search(T item){
        int low = 0;
        int high = numItems - 1;
        int index = 0;
        boolean end = false;
        boolean found = false;
        for(int mid = (low + high) / 2; !found && !end; mid = (low + high) / 2){
            if(item.equals(items[mid])){
                index = mid;
                found = true;
            }else if(low > high){
                index = low;
                end = true;
            }else if(item.compareTo(items[mid]) < 0){
                high = mid - 1;
            }else{
                low = mid + 1;
            }
        }
        if(found){
            return index;
        }else{
            return index + (2 * numItems);
        }
    }

    public boolean isEmpty(){
        return (numItems == 0);
    } // end isEmpty

    public int size(){
        return numItems;
    }  // end size

    public void clear(){
        // Creates a new array; marks old array for
        // garbage collection.
        items = (T[]) new Comparable[3];
        numItems = 0;
    } // end removeAll

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

    public T get(int index) throws ListIndexOutOfBoundsException{
        if (index >= 0 && index < numItems){
            return items[index];
        }
        else{
            // index out of range
            throw new ListIndexOutOfBoundsException(
                    "ListIndexOutOfBoundsException on remove");
        }  // end if
    }

    public String toString(){
        String info = "List of size " + numItems + " has the following items: \t";
        for(int i = 0; i < numItems; i++){
            info += items[i] + " ";
        }
        return info;
    }

    public void resize(){
        T[] newItems = (T[]) new Comparable[items.length * 2];
        for(int i = 0; i < items.length; i++){
            newItems[i] = items[i];
        }
        items = newItems;
    }
}