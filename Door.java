import java.util.HashMap;

/**
 * Classe qui sert représenter chacunes des portes que le joueur peut visiter dans le jeu
 *
 * @author Alexian Plancke
 * @version Finale
*/
public class Door
{
    private boolean aOpened = false;
    private boolean aLocked = false;
    private String  aKey;
    HashMap<String, String> aToRoom = new HashMap<String, String>();
    HashMap<String, String> aTrapped = new HashMap<String, String>();

    /**
     * Constructeur d'objets de classe Door simple, sans serrure 
     */
    public Door()
    {
        this.aKey = "";
    }

    /**
     * Constructeur d'objets de classe Door simple, avec serrure 
     * @param pKey String       La référence de la serrure 
     * @param pLocked boolean   L'état de la serrure: vérrouillée ou non
     */
    public Door(final String pKey, final boolean pLocked)
    {
        this.aKey = pKey;
        this.aLocked = pLocked;
    }

    /**
     * La porte est-elle verrouillée?
     * @return boolean  Vrai si la porte est verrouillée
    */
    public boolean islocked()
    {
        return this.aLocked;
    } //islocked
        
    /**
     * Initialise une porte avec sa direction
     * @param pDir String     La direction de la porte 
     * @param pRoom String   La salle qui se trouve dans cette direction
    */
    public void setExit(final String pDir, final String pRoom)
    {
        this.aToRoom.put(pDir, pRoom);
    } //setExit

    /**
     * Initialise une porte avec sa direction
     * @param pDir String     La direction de la porte 
     * @param pError String   Le code du message
    */
    public void setTrappedExit(final String pDir, final String pError)
    {
        this.aTrapped.put(pDir, pError);
    } //setExit

    /**
     * Accède à la pièce sur laquelle ouvre une porte
     * @param pDir String   La direction où aller
     * @return Room         La salle à laquelle mène cette porte
    */
    public String getExit(final String pDir)
    {
        if (this.aToRoom.containsKey(pDir))
            return this.aToRoom.get(pDir);
        if (this.aTrapped.containsKey(pDir))
            return this.aTrapped.get(pDir);
        return null;
    } //lock

    /**
     * Verrouille une porte si la clé correspond à sa serrure
     * @param pKey String       La référence de la serrure 
     * @return boolean  Vrai si la porte est verrouillée
    */
    public boolean lock(final String pKey)
    {
        // Pas possible de verrouiller une porte sans serrure
        if (this.aKey.equals(""))
            return false;
        // Pas possible de verrouiller une porte déjà verrouillée
        if (this.aLocked)
            return false;
        // Si la clé correspond à la serrure, on ferme
        if (this.aKey.equals(pKey)) {
            this.aLocked = true;
            return true;
        }
        return false;
    } //lock
    
    /**
     * Deverrouille une porte si la clé correspond à sa serrure
     * @param pKey String  La référence de la serrure 
     * @return boolean  Vrai si la porte a été verrouillée
    */
    public boolean unlock(final String pKey)
    {
        // Pas possible de verrouiller une porte sans serrure
        if (this.aKey.equals(""))
            return false;
        // Pas possible de verrouiller une porte déjà verrouillée
        if (! this.aLocked)
            return false;
        // Si la clé correspond à la serrure, on ouvre
        if (this.aKey.equals(pKey)) {
            this.aLocked = false;
            return true;
        }
        return false;
    } //unlock
    
    /**
     * Renvoie des informations de debug sous forme de String
     * @return  La chaine contenant ces informations
     */
    public String toString(){
        String vString;
        vString = "aExits: "+aToRoom.toString();
        vString += "\naTrapped: "+aTrapped.toString();
        return vString;
    }
}
