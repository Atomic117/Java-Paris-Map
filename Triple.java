/*
Thomas Tran

A basic class that represents three variables in one. It represents
- A vertex
- The shortest distance to that vertex
- The shortest path to that vertex

 */


//IMPORTS
import java.util.ArrayList;

public class Triple{

    //Instance variables
    int vertex;
    int distance;
    ArrayList<Integer> path;

    //Constructor
    public Triple(int a, int b, ArrayList<Integer> c){
        vertex = a;
        distance = b;
        path = c;
    }

    //Get methods

    public int getVertex(){
        return vertex;
    }

    public int getDistance(){
        return distance;
    }

    public ArrayList<Integer> getPath(){
        return path;
    }

    //Set methods

    public void setVertex(int a){
        vertex = a;
    }
    public void setPath(ArrayList<Integer> a){
        path = a;
    }
    public void setDistance(int a){
        distance = a;
    }
}

