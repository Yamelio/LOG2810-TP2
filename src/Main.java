import struct.Node;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    private static String GRAPHPATH = "lexique1.txt";
    private static Node nodeinit;
    //private static Scanner sc;

    public static void main(String[] args) {

        //sc = new Scanner(System.in);
        try {
            nodeinit = creerAutomate();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(nodeinit);
        System.out.println(nodeinit.getVoisins());

    }

    private static Node creerAutomate() throws FileNotFoundException{

        Scanner sc = new Scanner(new File(GRAPHPATH));
        System.out.println(sc.hasNext());

        Node res = new Node(false);
        String line = "";
        while(sc.hasNextLine()){
            line = sc.nextLine();
            res.addVoisin(line);
        }
        return res;
    }

}
