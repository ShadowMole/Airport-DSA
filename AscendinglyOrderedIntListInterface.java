public interface AscendinglyOrderedIntListInterface
{
    boolean isEmpty();
    int size();
    void add(int item) throws ListIndexOutOfBoundsException;
    int get(int index) throws ListIndexOutOfBoundsException;
    void remove(int index) throws ListIndexOutOfBoundsException;
    int search(int item);
    void clear();
} 