/*
Thomas Tran

A customized priority queue made to handle the Triple class.
Did not used Priority Queue provided by Java due to the need of being able to update/access the keys as
we traverse the graph. The imported Priority Queue does not provide this functionality.

This priority queue is based on an unsorted-array-list; using an iterator to return the smallest element.
It compares the distance provided by the Triple class.

 */

//Imports
import java.util.ArrayList;

public class TriplePriorityQueue {

    //Instance variables
    ArrayList<Triple> queue;

    //Constructor
    public TriplePriorityQueue(){
        queue = new ArrayList<Triple>();
    }

    /*


     */

    //Standard methods provided by the Priority Queue
    public void add(Triple o){
        queue.add(o);
    }

    //Iterates through the arraylist to find the smallest element, and returns it.
    public Triple poll() {
        int i = 10000000;
        int smallIndex = 0;
        for (int j = 0; j < size(); j++){
            if (queue.get(j).getDistance() < i){
                i = queue.get(j).getDistance();
                smallIndex = j;
            }
        }
        Triple point = queue.get(smallIndex);
        queue.remove(smallIndex);
        return point;
    }

    public int size(){
        return queue.size();
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }

    //New methods that are implemented
    //Given a vertex (the index), distance and path, updates the values at the specified vertex
    public void updateQueue(int a, int b, ArrayList<Integer> c){
        for (int i = 0; i < size(); i++){
            Triple point = queue.get(i);
            if (point.getVertex() == a){
                point.setDistance(b);
                point.setPath(c);
                return;
            }
        }
    }

    //Given a vertex/index, returns the distance at that vertex
    public int getDistance(int a){
        for (int i = 0; i < size(); i++){
            Triple point = queue.get(i);
            if (point.getVertex() == a){
                return point.getDistance();
            }
        }
        return -2;
    }
}
