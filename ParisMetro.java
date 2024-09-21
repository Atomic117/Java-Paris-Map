/* THOMAS TRAN
Class representing the Paris Metro system; with its respected graph representation as a variable
Contains the function specified by the assignment
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ParisMetro {

    //DECLARES VARIABLES
    Graph metro;
    final int stationNumbers = 376;

    //Constructor
    public ParisMetro(String filename){
        metro = new Graph(stationNumbers);
        File file = new File(filename);
        Scanner data;
        String[] stations = new String[stationNumbers];
        String line;


        //Read the file
        try {
            Scanner in = new Scanner(file);
            int count = 0;
            ArrayList<String> lines;

            in.nextLine();


            //Obtains the stationNames
            while (in.hasNextLine() && count < stationNumbers) {
                data = new Scanner(in.nextLine());
                lines = new ArrayList<String>();
                while (data.hasNext()) {
                    lines.add(data.next() + " ");
                }

                line = "";
                for (int i = 1; i < lines.size(); i++){
                    line += lines.get(i);
                }

                stations[count] = line;
                count++;
            }

            metro.setStationNames(stations);
            in.nextLine();


            //Obtains the weight/distance between each stations
            int a;
            int b;
            int c;
            while (in.hasNext()) {
                a = Integer.parseInt(in.next());
                b = Integer.parseInt(in.next());
                c = Integer.parseInt(in.next());

                if (!(a == 0 && b == 0 && c == 0)) {
                    metro.addEdge(a, b, c);
                }
            }

            in.close();


        } catch (FileNotFoundException e){
            System.out.println("Error finding file");
        }

    }

    //Question 2; Part 1
    private ArrayList<Integer>  ParisMetro1(int stationStart){
        return metro.findMetroLine(stationStart, true);
    }

    //Question 2; Part 2
    private ArrayList<Integer> ParisMetro2 (int stationStart, int stationEnd){
        return metro.findShortestPath(stationStart, stationEnd);
    }

    //Question 3; Part 3
    private ArrayList<Integer> ParisMetro3(int stationStart, int stationEnd, int obstruction){
        return metro.findShortestPath2(stationStart, stationEnd, obstruction);
    }


    public static void main(String[] args) {
        ParisMetro metro = new ParisMetro("metro.txt");
        int stationStart;
        int stationEnd;
        int stationDestroyed;
        int argsLength = args.length;
        switch(argsLength){
            case 3:
                stationStart = Integer.parseInt(args[0]);
                stationEnd = Integer.parseInt(args[1]);
                stationDestroyed = Integer.parseInt(args[2]);
                System.out.println("-------------------------------------------------------");
                System.out.println("Inputs: " + stationStart + " " + stationEnd + " " + stationDestroyed);
                metro.ParisMetro3(stationStart, stationEnd, stationDestroyed);
                System.out.println("-------------------------------------------------------");
                break;
            case 2:
                stationStart = Integer.parseInt(args[0]);
                stationEnd = Integer.parseInt(args[1]);
                System.out.println("-------------------------------------------------------");
                System.out.println("Inputs: " + stationStart + " " + stationEnd);
                metro.ParisMetro2(stationStart, stationEnd);
                System.out.println("-------------------------------------------------------");
                break;
            case 1:
                stationStart = Integer.parseInt(args[0]);
                System.out.println("-------------------------------------------------------");
                System.out.println("Input: " + stationStart);
                metro.ParisMetro1(stationStart);
                System.out.println("-------------------------------------------------------");
                break;
            default:
                System.out.println("-------------------------------------------------------");
                System.out.println("Invalid arguments");
                System.out.println("-------------------------------------------------------");
                break;
        }
    }
}
