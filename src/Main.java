import struct.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static JFrame frame;
    private static JTextField input;
    private static JTextArea output;
    private static String GRAPHPATH = "lexique6.txt";
    private static Node nodeinit;
    //private static Scanner sc;

    public static void main(String[] args) {

        //sc = new Scanner(System.in);
        try {
            nodeinit = creerAutomate();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //System.out.println(nodeinit);
        List<String> autocompleteTest = autoComplete("syl");
        for(String s : autocompleteTest){
            System.out.println(s);
        }

        frame = new JFrame("Bonjour");
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.PAGE_AXIS));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        input = new JTextField();
        output = new JTextArea();
        output.setEditable(false);
        input.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                List<String> content = autoComplete(input.getText());
                String txt = "";
                if(content != null){
                    for(String s : content){
                        txt += s + "\n";
                    }
                    output.setText(txt);
                }
                else{
                    output.setText("Rien n'a ete trouve");
                }
            }
        });
        frame.add(input);
        frame.add(output);
        frame.setVisible(true);

    }

    private static Node creerAutomate() throws FileNotFoundException{

        Scanner sc = new Scanner(new File(GRAPHPATH), "ISO-8859-1");
        System.out.println(sc.hasNext());

        Node res = new Node(false);
        String line = "";
        while(sc.hasNextLine()){
            line = sc.nextLine();
            res.addVoisin(line);
        }
        return res;
    }

    private static List<String> autoComplete(String s){
        return nodeinit.autocomplete(s);
    }

}
