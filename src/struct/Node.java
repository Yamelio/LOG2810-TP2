package struct;

import java.util.*;

public class Node {

    private Map<Character,Node> voisins;
    private boolean isFinal;
    public static int cpt = 0;

    public Node(){
        voisins = new HashMap<>();
        cpt++;
    }

    public Node(boolean isFinal){
        this.isFinal = isFinal;
        voisins = new HashMap<>();
        cpt++;
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

    /***
     * Appel publique a la methode autocomplete.
     * Il est necessaire pour préparer la recherche de mots de parcourir le graphe jusqu'a l'etat correspondant au mot de départ.
     * Cette methode initialise cette recherche
     * @param start correspond au mot a completer
     * @return la liste des mots disponibles a partir du mot de depart
     */
    public List<String> autocomplete(String start) {
        try {
            return autocomplete(start, start);
        }
        catch (Exception e){
            return null;
        }
    }


    /***
     * Methode privee d'autocomplete.
     * Permet de se rendre a l'etat correspondant au mot de depart
     * @param start rappel de la chaine de depart necessaire pour l'appel a getWords(start)
     * @param s chaine correspondant au node actuellement visite
     * @return la liste de mots disponibles a partir du mot de depart (appel recursif jusqu'au bon node)
     */
    private List<String> autocomplete(String start, String s) {

        if(s.equals("")){
            return new ArrayList<>(getWords(start));
        }
        else{
            return voisins.get(s.charAt(0)).autocomplete(start, s.substring(1));
        }

    }


    /***
     * Renvoie recursivement la liste des mots disponibles a partir du mot de depart
     * On recupere les voisins directs, et on appelle recursivement sur les autres voisins
     * Le LinkedHashSet évite les doublons
     * @param depart Mot du node actuel a completer
     * @return La liste des mots completables avec les voisins.
     */
    private Set<String> getWords(String depart){
        Set<String> res = new LinkedHashSet<>();

        if(isFinal){
            res.add(depart);
        }

        for(char s : voisins.keySet()){

            if(voisins.get(s).isFinal){
                res.add(depart+s);
            }

            if(!voisins.get(s).getVoisins().isEmpty())
                res.addAll(voisins.get(s).getWords(depart+s));

        }

        return res;
    }

    public boolean contains(String s){
        if(s.length() != 0) {
            if (s.length() == 1)
                return voisins.keySet().contains(s.charAt(0)) && voisins.get(s.charAt(0)).isFinal();
            else if (voisins.keySet().contains(s.charAt(0)))
                return voisins.get(s.charAt(0)).contains(s.substring(1));
        }
        return false;
    }
}
