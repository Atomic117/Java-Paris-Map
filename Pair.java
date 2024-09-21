/*
Thomas Tran

A basic pair class that represents the x and y coordinate of the Adjacency matrix.
It represents an edge given two vertex

 */

public class Pair {

    //Declare instance variables
    int rowNum;
    int colNum;

    //Constructor
    public Pair(int i, int j){
        rowNum = i;
        colNum = j;
    }


    //Set methods
    public void setRowNum(int i){rowNum = i;}
    public void setColNum(int i){
        colNum = i;
    }
    public int getRowNum(){
        return rowNum;
    }

    //Get methods
    public int getColNum(){
        return colNum;
    }

    //toString method
    public String toString(){
        return(rowNum + ", " + colNum);
    }
}
