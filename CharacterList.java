import java.util.HashMap;

/**
 * Décrivez votre classe ItemList ici.
 *
 * @author Alexian Plancke
 * @version Finale
 */
public class CharacterList
{
    private HashMap<String, Character> aList = new HashMap<String, Character>();

    /**
     * Constructeur d'objets de classe CharacterList
     */
    public CharacterList()
    {
    }

    /**
     * Ajoute un personnage à la liste 
     *
     * @param       pName   Nom de l'item à ajouter
     * @param       pItem       Objet item à ajouter
     */
    public void add(String pName, Character pCharacter)
    {
        this.aList.put(pName, pCharacter);
    }
        
    /**
     * Supprime un personnage de la liste 
     * @param       pName   Nom de l'item à ajouter
     * @return      Objet item retiré de la liste
     */
    public Character remove(String pName)
    {
        return this.aList.remove(pName);
    }

    /**
     * Recherche un personnage de la liste
     * @param   pName String  Nom du personnage 
     * @return  True si présent dans la liste
     */
    public boolean search(String pName)
    {
        return this.aList.containsKey(pName);
    }

    /**
     * Recherche un personnage de la liste 
     * @param   pName String  Nom de l'item 
     * @return  l'objet s'il est présent dans la liste
     */
    public Character find(String pName)
    {
        Global.debug("find("+pName+") => "+this.aList.get(pName));
        return this.aList.get(pName);
    }
        
    /**
     * Renvoie la liste des personnages sous forme de string
     * @return  La chaine contenant la description de la liste de personnages
     */
    public String liste() {
        String vListe = "";

        for (HashMap.Entry<String, Character> entry : aList.entrySet()) {
            Character value = entry.getValue();
            vListe += value.getDescription() + ", ";
        }
        
        if (vListe.length() > 2)
            return vListe.substring(0, vListe.length()-2);
        return vListe;
    }
        
    /**
     * Renvoie un message lors de la 1ère rencontre
     * @return  La chaine contenant le message
     */
    public String greetings() {
        String vListe = "", vTmp;

        for (HashMap.Entry<String, Character> entry : aList.entrySet()) {
            Character value = entry.getValue();
            vTmp = value.getSpeech("hello");
            if ((vTmp != null)&&(! vTmp.equals("")))
                vListe += value.getDescription() + ": " + vTmp + "\n";
        }
        return vListe;
    }
        
    /**
     * Réaction des personnages présent dans la pièce à ce que dit le personnage
     * @return  La chaine contenant le/les réponses
     */
    public String say(final String vSomething) {
        String vListe = "", vTmp;

        if (aList.isEmpty())
            return "There is nobody here to hear what you say!";
        for (HashMap.Entry<String, Character> entry : this.aList.entrySet()) {
            Character value = entry.getValue();
            vTmp = value.getSpeech(vSomething);
            if ((vTmp != null)&&(! vTmp.equals("")))
                vListe += value.getDescription() + ": " + vTmp + "\n";
        }
        return vListe;
    }
    
}
