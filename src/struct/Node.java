package struct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node {

    private Map<Character,Node> voisins;
    private boolean isFinal;

    public Node(){
        voisins = new HashMap<>();
    }

    public Node(boolean isFinal){
        this.isFinal = isFinal;
        voisins = new HashMap<>();
    }

    public Map<Character, Node> getVoisins() {
        return voisins;
    }

    public void setVoisins(Map<Character, Node> voisins) {
        this.voisins = voisins;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    public void addVoisin(String word){

        char firstChar = word.charAt(0);

        if(word.length() == 1){
            Node toAdd = new Node(true);
            voisins.put(firstChar,toAdd);
        }
        else if(voisins.keySet().contains(firstChar)){
            voisins.get(firstChar).addVoisin(word.substring(1));
        }
        else{
            Node toAdd = new Node(false);
            toAdd.addVoisin(word.substring(1));
            voisins.put(firstChar,toAdd);
        }
    }

    @Override
    public String toString() {
        return printPadding(0);
    }

    private String printPadding(int padding){
        String res = "";
        if(isFinal){
            res += " //out";
        }
        for(char s : voisins.keySet()){
            res+= "\n";
            for(int i=0;i<padding;i++)
                res += '-';
            res+= s ;

            res += voisins.get(s).printPadding(padding+1);
        }
        return res;
    }

    public List<String> autocomplete(String start) {
        try {
            return autocomplete(start, start);
        }
        catch (Exception e){
            return null;
        }
    }


    private List<String> autocomplete(String start, String s) {

        if(s.equals("")){
            return getWords(start);
        }
        else{
            return voisins.get(s.charAt(0)).autocomplete(start, s.substring(1));
        }

    }


    private List<String> getWords(String depart){
        List<String> res = new ArrayList<>();

        for(char s : voisins.keySet()){
            if(voisins.get(s).isFinal){
                res.add(depart+s);
            }
            if(!voisins.get(s).getVoisins().isEmpty())
                res.addAll(voisins.get(s).getWords(depart+s));

        }

        return res;
    }
}
