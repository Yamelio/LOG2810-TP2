package struct;

import java.util.HashMap;
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

    public Node addVoisin(String word){

        char firstChar = word.charAt(0);

        if(word.length() == 1){
            Node toAdd = new Node(true);
            return voisins.put(firstChar,toAdd);
        }
        else if(voisins.keySet().contains(firstChar)){
            return voisins.get(firstChar).addVoisin(word.substring(1));
        }
        else{
            Node toAdd = new Node(false);
            toAdd.addVoisin(word.substring(1));
            return voisins.put(firstChar,toAdd);
        }
    }

    @Override
    public String toString() {
        return printPadding(0);
    }

    private String printPadding(int padding){
        String res = "";
        for(char s : voisins.keySet()){
            for(int i=0;i<padding;i++)
                res += '\t';
            res+= s + "\n";
            res += voisins.get(s).printPadding(padding+1);
            res+= "\n";
        }
        return res;
    }
}
