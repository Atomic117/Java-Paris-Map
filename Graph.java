/*
Thomas Tran

Graph implementation using a basic adjacent matrix.

Uses DFT traversal to find the complete line given a station, able to use Dijkstra's Algorithm

 */


//Basic imports
import java.util.ArrayList;
import java.util.Stack;

public class Graph {

    /*
    Instance variables
     */

    //Graph representation
    private int adjMatrix[][];

    //List containing the station names
    private String[] stationNames;

    //Variable to hold number of vertexes/stations
    private int numVertex;

    private final int WALK_DISTANCE = 90;


    /*
    Constructor:
    Creates an empty graph given the number of vertexes, an empty list containing station names and stores the number of vertexes
     */
    public Graph(int numVertex){
        adjMatrix = new int[numVertex][numVertex];
        stationNames = new String[numVertex];
        this.numVertex = numVertex;
    }

    //Method to add in an edge between two vertexes; adds weight as well
    public void addEdge(int i, int j, int time){
        adjMatrix[i][j] = time;
    }

    //Method to remove an edge
    public void removeEdge(int i, int j){
        adjMatrix[i][j] = 0;
    }

    //Returns the weight of an edge given the endpoints
    public int getEdge(int i, int j){
        return adjMatrix[i][j];
    }

    //Storing an array of names as station names in an array
    public void setStationNames(String[] names){
        stationNames = names;
    }

    //Checks if an edge exist AND the vertex was not already discovered
    private boolean colIsValid(ArrayList<Integer> list, int row, int col) {
        if (list.contains(col) || adjMatrix[row][col] <= 0) {
            return false;
        }
        return true;
    }

    //Prints the line; and distance is provided
    private void displayLine(ArrayList<Integer> list, int distance){
        System.out.print("Station path: ");
        for (int i = 0; i < list.size(); i++){
            System.out.print(list.get(i) + " ");
        }
        System.out.println("");

        if (distance != -1){
            System.out.println("Total travel time: " + distance);
        }
    }

    //Given a list of integer representing station numbers, returns a list that represents the station names
    public ArrayList<String> getStationNames(ArrayList<Integer> list){
        ArrayList<String> names = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++){
            names.add(stationNames[list.get(i)]);
        }

        return names;
    }
    /*
    Returns an ArrayList that represents the line that contains a station/vertex

    The core part of the method is that it uses Depth-First-Search. As such, the data structure used is a stack.
     */
    public ArrayList<Integer> findMetroLine(int stationNum, boolean print){
        //DECLARING LOCAL VARIABLES
        int currentStation;
        Stack<Pair> stack = new Stack<Pair>();
        ArrayList<Integer> visited = new ArrayList<Integer>();


        //ADD THE FIRST FEW EDGES TO DEQUEUE
        currentStation = stationNum;
        visited.add(currentStation);
        //Checks if the edge exist and is not already traversed
        for (int i = 0; i < numVertex; i++){
            if (colIsValid(visited, currentStation, i)){
                stack.push(new Pair(currentStation, i));
            }
        }

        //TRAVERSING THE GRAPH
        while(!stack.isEmpty()) {
            //Gets an edge and the endpoint vertex
            Pair current = stack.pop();
            currentStation = current.getColNum();
            //Adds the vertex to the stack
            visited.add(currentStation);
            //Iterates through the matrix row
            for (int i = 0; i < numVertex; i++) {
                //Checks if the edge exist and is not already traversed
                if (colIsValid(visited, currentStation, i)) {
                    //Pushes the vertexes that can be visited
                    stack.push(new Pair(currentStation, i));
                }
            }
        }

        //Prints the line
        if (print) {
            displayLine(visited, -1);
        }

        return visited;
    }

    /*
    Returns an arrayList containing the shortest path when inputted a starting and ending station.
    Based on what is taught in class, it uses Dijkstra's Algorithm, using the Breath-First-Search technique
    and a custom-made priority queue.
     */
    public ArrayList<Integer> findShortestPath(int stationStart, int stationEnd){
        //Declaring variables
        TriplePriorityQueue undiscoveredVertex = new TriplePriorityQueue();
        Triple currentStation;
        int currentVertex;
        int currentDistance;
        int edgeDistance;
        ArrayList<Integer> currentPath;
        ArrayList<Integer> attemptedPath = new ArrayList<>();
        boolean traversed;

        //Fills the priority queue with every vertex found in the graph, setting the key as max value, and assigning it an empty path
        for (int i = 0; i < numVertex; i++){
            undiscoveredVertex.add(new Triple(i, Integer.MAX_VALUE, new ArrayList<Integer>()));
        }

        //Changes the key of the starting station to 0
        undiscoveredVertex.updateQueue(stationStart, 0, new ArrayList<Integer>());

        //While the priority queue is not empty
        while(!undiscoveredVertex.isEmpty()){
            //Retrieves the vertex with the smallest distance (at this point, it is the best distance possible for that vertex)
            //Stores the vertex, the distance and the path that leads to the verex
            currentStation = undiscoveredVertex.poll();
            currentVertex = currentStation.getVertex();
            currentDistance = currentStation.getDistance();
            currentPath = currentStation.getPath();

            //Searches through the adjacency matrix
            for (int i = 0; i < numVertex; i++){
                traversed = false;
                //Gets the weight of the edge
                edgeDistance = getEdge(currentVertex, i);
                //Checks if the new vertex is already traversed and exist
                if (!currentPath.contains(i) && edgeDistance != 0){
                    //Add the vertex to the current path
                    attemptedPath = new ArrayList<>(currentPath);
                    attemptedPath.add(currentVertex);

                    //Checks if the weight is -1; reassigning the value to 90 if true
                    if (edgeDistance == -1){
                        edgeDistance = WALK_DISTANCE;
                    }

                    //Checks if the newly discovered path is indeed better than the existing path that leads to vertex
                    //Done by comparing the key (distance)
                    //If true, replaces the distance/key and the path assigned to the vertex
                    if (currentDistance + edgeDistance < undiscoveredVertex.getDistance(i)){
                        undiscoveredVertex.updateQueue(i, currentDistance + edgeDistance, attemptedPath);
                        traversed = true;
                    }

                }

                //If we had indeed made a traversal, and the new edge leads to our end point, then
                //Displays the new line and returns the list that contains the shortest path
                if (traversed && i == stationEnd){
                    attemptedPath.add(stationEnd);
                    displayLine(attemptedPath, currentDistance+edgeDistance);
                    return attemptedPath;
                }
            }
        }

        return null;
    }
    /*
    Returns an arrayList containing the shortest path when inputted a starting and ending station.
    Based on what is taught in class, it uses Dijkstra's Algorithm, using the Breath-First-Search technique
    and a custom-made priority queue.

    Additionally, when creating the priority queue, the assigned path will contain the "destroyed" line; telling
    the algorithm to not traversed to it
 */
    public ArrayList<Integer> findShortestPath2(int stationStart, int stationEnd, int stationDestroyed){
        //Declaring variables
        TriplePriorityQueue undiscoveredVertex = new TriplePriorityQueue();
        Triple currentStation;
        int currentVertex;
        int currentDistance;
        int edgeDistance;
        ArrayList<Integer> currentPath;
        ArrayList<Integer> attemptedPath = new ArrayList<>();
        boolean traversed;

        //Declaring the "empty path" to be added in the queue. It contains the destroyed line.
        //Call function implemented by 2A to find the line that we can't use
        ArrayList<Integer> destroyedLine = findMetroLine(stationDestroyed, false);
        ArrayList<Integer> list = destroyedLine;

        //Checks if the starting station is part of the destroyed line, and if it can walk to a working line
        if (list.contains(stationStart)){
            boolean stuck = true;
            for (int i = 0; i < numVertex; i++){
                if (getEdge(stationStart, i) == -1){
                    stuck = false;
                }
            }

            if (stuck){
                return null;
            }
        }
        //Filling the priority queue
        for (int i = 0; i < numVertex; i++){
            undiscoveredVertex.add(new Triple(i, Integer.MAX_VALUE, list));
        }

        //Changing the starting station key to 0
        undiscoveredVertex.updateQueue(stationStart, 0, list);

        //While the priority queue is not empty
        while(!undiscoveredVertex.isEmpty()){
            //Retrieves the vertex with the smallest distance (at this point, it is the best distance possible for that vertex)
            //Stores the vertex, the distance and the path that leads to the verex
            currentStation = undiscoveredVertex.poll();
            currentVertex = currentStation.getVertex();
            currentDistance = currentStation.getDistance();
            currentPath = currentStation.getPath();

            //Searches through the adjacency matrix
            for (int i = 0; i < numVertex; i++){
                traversed = false;
                //Gets the weight of the edge
                edgeDistance = getEdge(currentVertex, i);
                //Checks if the new vertex is already traversed and exist
                if (!currentPath.contains(i) && edgeDistance != 0){
                    //Add the vertex to the current path
                    attemptedPath = new ArrayList<>(currentPath);
                    attemptedPath.add(currentVertex);

                    //Checks if the weight is -1; reassigning the value to 90 if true
                    if (edgeDistance == -1){
                        edgeDistance = WALK_DISTANCE;
                    }

                    //Checks if the newly discovered path is indeed better than the existing path that leads to vertex
                    //Done by comparing the key (distance)
                    //If true, replaces the distance/key and the path assigned to the vertex
                    if (currentDistance + edgeDistance < undiscoveredVertex.getDistance(i)){
                        undiscoveredVertex.updateQueue(i, currentDistance + edgeDistance, attemptedPath);
                        traversed = true;
                    }

                }

                //If we had indeed made a traversal, and the new edge leads to our end point, then
                //Displays the new line and returns the list that contains the shortest path
                if (traversed && i == stationEnd){
                    //Removes the destroyed line
                    for (int j = 0; j < destroyedLine.size(); j++) {
                        attemptedPath.remove(0);
                    }
                    //Prints the line and returns it
                    attemptedPath.add(stationEnd);
                    displayLine(attemptedPath, currentDistance+edgeDistance);
                    return attemptedPath;
                }
            }
        }

        return null;
    }
}