import javafx.util.Pair;
import struct.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class Main {

    private static Map<String, Integer> labels = new HashMap<>();
    private static List<String> history = new LinkedList<>();
    private static JFrame frame;
    private static JTextField input;
    private static JTextArea textarea;
    private static JTextArea historyarea;
    private static JScrollPane output;
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

        System.out.println(nodeinit);

        frame = new JFrame("Bonjour");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        input = new JTextField();
        input.setMaximumSize(new Dimension(500,50));
        textarea = new JTextArea();
        textarea.setEditable(false);
        output = new JScrollPane(textarea);
        historyarea = new JTextArea();
        historyarea.setEditable(false);
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
                String txt;
                if(content != null){
                    if(content.size() == 1 || nodeinit.contains(input.getText())){
                        if(content.size() == 1)
                            txt = content.get(0);
                        else
                            txt = input.getText();
                        if(!history.isEmpty()){
                            if(!history.get(history.size()-1).equals(txt)) {
                                if (history.size() >= 5) {
                                    history.remove(0);
                                }
                                history.remove(txt);
                                history.add(txt);
                                labels.put(txt, labels.get(txt) + 1);
                                updateHistoryArea();
                            }
                        }
                        else{
                            history.add(txt);
                            labels.put(txt, labels.get(txt) + 1);
                            updateHistoryArea();
                        }


                    }
                    txt="";
                    for (String s : content) {
                        txt += s +" | "+ labels.get(s) + "\n";
                    }
                    textarea.setText(txt);
                }
                else{
                    textarea.setText("Rien n'a ete trouve");
                }
            }
        });
        frame.add(input,BorderLayout.PAGE_START);
        frame.add(output,BorderLayout.CENTER);
        frame.add(historyarea,BorderLayout.PAGE_END);
        frame.setVisible(true);
    }

    private static void updateHistoryArea() {
        String res = "";
        for(String s : history){
            res += s + " x" + labels.get(s) + " / ";
        }
        historyarea.setText(res);
    }

    private static Node creerAutomate() throws FileNotFoundException{

        Scanner sc = new Scanner(new File(GRAPHPATH), "ISO-8859-1");
        Node res = new Node(false);
        String line = "";
        while(sc.hasNextLine()){
            line = sc.nextLine();
            labels.put(line,0);
            res.addVoisin(line);
        }
        return res;
    }

    private static List<String> autoComplete(String s){
        return nodeinit.autocomplete(s);
    }




}