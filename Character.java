import java.util.HashMap;
/**
 * Ce sont les personnages que l'ont peut rencontrer dans le jeu
 *
 * @author Alexian Plancke
 * @version Finale
 */
public class Character
{
    private String aName;
    private String aDescription;
    private boolean aFirst = true;
    private static HashMap<String, Character> aCharacters = new HashMap<String, Character>();
    HashMap<String, String> aSpeeches = new HashMap<String, String>();
    
    public Character(final String vName, final String vDescription)
    {
        this.aName = vName;
        this.aDescription = vDescription;
        Character.aCharacters.put(vName, this);
    }
 
    /**
     * Accesseur de la description personnage
     * @return La description du personnage
     */
    public String getDescription()
    {
        return this.aDescription;
    }
    
    /**
     * Ajoute une interaction avec le personnage
     * @param pKeyWord String Le mot clé
     * @param pAnswer String La réponse que fait ce personnage
     */
    public void setSpeech(final String pKeyWord, final String pAnswer)
    {
        this.aSpeeches.put(pKeyWord, pAnswer);
    }
 
    /**
     * Une interaction avec le personnage
     * @param pKeyWord String Le mot clé
     * @return La réponse que fait ce personnage
     */
    public String getSpeech(final String pKeyWord)
    {
        if (pKeyWord.equals("hello")) {
            if (! this.aFirst)
                return "";
            this.aFirst = false;
        }
        return this.aSpeeches.get(pKeyWord);
    }
 
}