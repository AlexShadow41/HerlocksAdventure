import java.util.Stack;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Gère les actions et les données relative à un joueur
 *
 * @author Alexian Plancke
 * @version Finale
 */
public class Player
{
    private String aCurrentRoom;
    // private Stack <String> aPreviousRooms = new Stack<String>();
    private String aPlayerName;
    private ItemList aInventory = new ItemList();
    private int aStrength;
    private int aAgility;
    private int aWisdom;
    private int aIntelligence;
    private float aCarriedWeight;
    private float aExtraWeigth;
    private Stack <String> aGoBack = new Stack<String>();

    /**
     * Constructeur d'objets de classe Player
     */
    public Player()
    {
        this.aPlayerName = "Player 1";
        this.aCurrentRoom = "Road1";
        this.aStrength = 10 + (int) (Math.random() * 10)  ;
        this.aAgility = 5 + (int) (Math.random() * 15);
        this.aWisdom = 5 + (int) (Math.random() * 15);
        this.aIntelligence = 10 + (int) (Math.random() * 10);
        this.aExtraWeigth = this.aCarriedWeight = 0;
    }
    
    /**
     * Enregistre le nom du joueur
     * @param pPlayerName String Nom du joueur
     */
    public void setName(final String pPlayerName){
        this.aPlayerName = pPlayerName;
    }
    
    /**
     * Retourne le nom du joueur
     * @return       Le nom du joueur
     */
    public String getName(){
        return(this.aPlayerName);
    }
    
    /**
     * Permet d'accéder à la pièce où se trouve le joueur
     * @return       Renvoie l'objet Room de la salle dans laquelle se trouve le joueur
     */
    public Room getCurrentRoom(){
        return Room.get(this.aCurrentRoom);
    }

    /**
     * Permet d'accéder au code de la pièce où se trouve le joueur
     * @return       Renvoie l'objet Room de la salle dans laquelle se trouve le joueur
     */
    public String getCurrentRoomCode(){
        return this.aCurrentRoom;
    }

    /**
     * Permet de remmettre l'historique à zéro
     */
    public void flushBack(){
        this.aGoBack.clear();
    }
    
    /**
     * Initialise la position initiale du joueur
     * @param pRoomName String Nom de la Salle
     */
    public void setCurrentRoom(final String pRoomName){
        this.aCurrentRoom = pRoomName;
    }
    
    /**
     * 
     * @param pDirection String La direction
     * @return Renvoie le nom de la nouvelle salle courante
     */
    public String go(final String pDirection)
    {
        // Vérification que le back est possible
        String vDirection = pDirection;
        if (pDirection.equals("back")) {
            if (this.aGoBack.isEmpty())
                return "ERROR-NOBACK";
            vDirection = this.aGoBack.pop();
        }//if
        
        // Récupère la porte qui se trouve dans cette direction 
        Door vDoor = Room.get(this.aCurrentRoom).getExit(vDirection);
        
        // Pas de porte dans cette direction
        if (vDoor == null)
            return "ERROR-NODOOR";
        Global.debug(vDoor.toString());
            
        // La porte est femée
        if (vDoor.islocked())
            return "ERROR-LOCKED";
            
        // Si la salle est une "téléport room", il faut effacer l'historique
        boolean vIsTeleportRoom = Room.get(this.aCurrentRoom).getTeleportRoom();
        
        // Tout est ok, on peut aller dans la pièce suivante
        this.aCurrentRoom = vDoor.getExit(vDirection);
        Global.debug("[DEBUG] player.go() Door: "+vDoor.toString());

        // Dans quelle direction aller pour revenir en arrière?
        if (vIsTeleportRoom) {
            this.flushBack();
        }
        else if (! pDirection.equals("back")) {
            this.aGoBack.push(Global.getOppositeDir(pDirection));
        }//if
        return(this.aCurrentRoom);
    }
    
    /**
     * Fonction permettant dau joueur de prendre un item
     * @param   pItemName String Nom de l'item à ajouter à l'iventaire du joueur
     * @return  Renvoie le message à afficher pour informer le joueur du résultat de l'action
     */
    public String take(final String pItemName) {
        // Can the requested item be found in the current room?
        Room vRoom = Room.get(this.aCurrentRoom);
        Item vItem = vRoom.takeItem(pItemName);
        if (vItem == null)
            return "There's no such item in this room";
        
        // Can the player carry all the weight?
        double vCanCarry = this.aStrength * 2.5 * 1000.0 + this.aExtraWeigth;
        double vToCarry = this.aCarriedWeight + vItem.getWeight();
        if (vToCarry > vCanCarry) {
            vRoom.addItem(pItemName, vItem);
            return "You are not strong enough to carry this, maybe you should consider droping some stuff";
        }//if
        // Add the item to player's inventory
        this.aInventory.addItem(pItemName, vItem);
        this.aCarriedWeight += vItem.getWeight();
        return "The "+pItemName+" has been added to your inventory";
    }

    /**
     * Méthode permettant au joueur d'inspecter un objet (A améliorer plus tard avec la loupe)
     * @param pItemName String L'item à inspecter
     * @return  Renvoie le message à afficher pour informer le joueur du résultat de l'action
     */
    public String inspect(final String pItemName) {
        // Can the requested item be found in the current room?
        Item vItem = Room.get(this.aCurrentRoom).findItem(pItemName);
        if (vItem == null)
            return "There's no such item in this room";
        return vItem.getDescription();
        //return vItem.getLongDescription();
    }

    
    /**
     * Méthode permettant au joueur de manger
     * @param pItemName String L'item à manger
     * @return  Renvoie le message à afficher pour informer le joueur du résultat de l'action
     */
    public String eat(final String pItemName) {
        // Check if in your inventory
        if (! this.aInventory.searchItem(pItemName))
            return "You don't have any "+pItemName+" in your inventory";

        // check if you can eat it
        if (! pItemName.equals("Cookie"))
            return "Oh no, this doesn't look tasty, try it yourself!";

        // Remove it from your inventory
        Item vItem = this.aInventory.removeItem(pItemName);
        this.aCarriedWeight -= vItem.getWeight();
        
        // Apply magic effect
        this.aExtraWeigth = 60*1000; 
        return "Oh my god, I now feel so strong";
    }

    /**
     * Méthode permettant au joueur de reposer un objet
     * @param pItemName String L'item à déposer
     * @return  Renvoie le message à afficher pour informer le joueur du résultat de l'action
     */
    public String drop(final String pItemName) {
        if (! this.aInventory.searchItem(pItemName))
            return "You don't have any "+pItemName+" in your inventory";
        Item vItem = this.aInventory.removeItem(pItemName);
        this.aCarriedWeight -= vItem.getWeight();

        // Put the dropped Item in the current room
        Room.get(this.aCurrentRoom).addItem(pItemName, vItem);
        return "The "+pItemName+" has been dropped";
    }
    
    /**
     * Renvoie l'inventaire du joueur sous forme de String
     * @return  La chaine contenant la description de l'inventaire
     */
    public String inventory() {
        String vInventory = this.aInventory.listeItems();
        if (vInventory.equals(""))
            return "Empty";
        return vInventory;
    }
    
    /**
     * Verrouille ou dévérrouille une porte
     * @param pLock boolean     True pour verrouiller, false pour déverouiller
     * @param pDirection String Dans quelle direction se trouve la porte
     * @return  Error code or OK
     */
    public String lockOrUnlockDoor(final boolean pLock, final String pDirection) {
        // Rechercher la porte à ouvrir
        Door vDoor = Room.get(this.aCurrentRoom).getExit(pDirection);
        if (vDoor == null)
            return "ERROR-NODOOR";

        // Recherche les clés dans l'inventaire
        LinkedList<Item> vAllKeys = this.aInventory.getItemsByType("KEY");
        
        // Essayer toutes les clés
        Iterator<Item> iterator = vAllKeys.iterator();
        while(iterator.hasNext()){
           Item vItem = iterator.next();
           // System.out.println(vItem.toString());
           if (pLock) {
               if (vDoor.lock(vItem.getCharacteristic()))
                   return "OK-LOCKED";
           }
           else {
               if (vDoor.unlock(vItem.getCharacteristic()))
                   return "OK-UNLOCKED";
           }//if
        }//while
        return "ERROR-NOMATCH";
    
    }
    
    /**
     * Recherche un item dans l'inventaire du joueur
     * @param pItem String Nom de l'item recherche
     * @return Item Item recherché ou NULL
     */
    public Item getItem(final String pItemName) {
        // Recherche l'item demandé dans l'inventaire
        return this.aInventory.findItem(pItemName);
    }
    
    /**
     * Renvoie des informations de debug sous forme de String
     * @return  La chaine contenant ces informations
     */
    public String toString() {
        return "aGoBack: "+this.aGoBack.toString();
    }

}
