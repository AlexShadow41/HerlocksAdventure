import java.util.HashMap;
import java.util.Set;
import java.util.Random;
import java.util.ArrayList; 

/**
 * Classe qui sert représenter chacunes des pièces que le joueur peut visiter dans le jeu
 *
 * @author Alexian Plancke
 * @version Finale
 * 
 */
public class Room
{
    private String aDescription;
    private HashMap<String, Door> aExits;
    private String aImageName;
    private ItemList aItems;
    private CharacterList aCharacters;
    protected boolean aIsTeleportRoom;
    private static HashMap<String, Room> aRooms = new HashMap<String, Room>();
    private static HashMap<String, HashMap<String, Door>> aDoors = new HashMap<String, HashMap<String, Door>>();
    private static ArrayList<String> aTeleportTo = new ArrayList<String>(); 
    private static String aTeleportRoom = "";
    
    /**
     * Constructeur naturel
     * @param pName String Nom de la salle
     * @param pD String Description de la salle
     * @param pI String Nom de l'image
    */
    public Room (final String pName, final String pD, final String pI, final boolean pTeleport)
    {
        this.aDescription = pD;
        this.aExits = new HashMap<String, Door>();
        this.aCharacters = new CharacterList();
        this.aItems = new ItemList();
        this.aImageName = pI;
        this.aIsTeleportRoom = false;
        Room.aRooms.put(pName, this);
        if (pTeleport)
            // Cette pièce est une destination possible pour la téléportation aléatoire
            this.aTeleportTo.add(pName);
    } //Room
    
    /**
     * Retrouve une pièce connaissant son nom
     * @param pName String Nom de la salle
     * @return      La pièce
    */
    public static Room get(final String pName)
    {
        return Room.aRooms.get(pName);
    } //getDescription
    
    /**
     * Retourne la description d'une pièce
     * @return      La description de la pièce
    */
    public String getDescription()
    {
        return this.aDescription;
    } //getDescription

    /**
     * Permet d'ajouter un Item (Avec description précise)
     * @param pObjName String Nom de l'Item
     * @param pObjD String Description de l'Item
     * @param pObjW int Poids de l'Item
     * @param pObjLD String Description précise de l'Item
     * @return  Le nouvel item
    */
    public Item addItem(final String pObjName, final String pObjD, final int pObjW, final String pObjLD)
    {
        Item It = new Item(pObjD, pObjW);
        It.setLongDescription(pObjLD);
        this.aItems.addItem(pObjName, It);
        return It;
    }
    
    /**
     * Permet d'ajouter un Item
     * @param pObjName String Nom de l'Item
     * @param pObjD String Description de l'Item
     * @param pObjW int Poids de l'Item
     * @return  Le nouvel item
    */
    public Item addItem(final String pObjName, final String pObjD, final int pObjW)
    {
        Item vItem = new Item(pObjD, pObjW);
        this.aItems.addItem(pObjName, vItem);
        return vItem;
    }

    /**
     * Permet d'ajouter un Item
     * @param pObjName String Nom de l'Item
     * @param pItem Item Objet à ajouter
    */
    public void addItem(final String pObjName, final Item pItem)
    {
        this.aItems.addItem(pObjName, pItem);
    }
    
    /**
     * Retourne la liste des items d'une pièce
     * @return      La liste des objets présent dans la pièce
    */
    public String getItemList()
    {
        String vS = this.aItems.listeItems();
        if (vS != "")
            return "You can see some items: " + vS + "\n";
        return "";
    } //getDescription

    /**
     * Supprime un objet d'une salle et le renvoie pour être ajouté à l'inventaire du joueur
     * Retourne null si l'article est introuvable dans la pièce
     * @param       pItemName   Nom de l'item 
     * @return      L'objet Item pris dans la pièce
    */
    public Item takeItem(final String pItemName)
    {
        return this.aItems.removeItem(pItemName);
    }

    /**
     * Rechercher un élément dans une pièce 
     * Retourne null si l'article est introuvable dans la pièce
     * @param       pItemName   Nom de l'item 
     * @return      L'objet Item pris dans la pièce
    */
    public Item findItem(final String pItemName)
    {
        return this.aItems.findItem(pItemName);
    }
        
    /**
     * Permet d'ajouter un personnage
     * @param pName String Nom du personnage
     * @return  Le nouveau personnage
    */
    public Character addCharacter(final String pName, final String pDescription)
    {
        Character vCharacter = new Character(pName, pDescription);
        this.aCharacters.add(pName, vCharacter);
        return vCharacter;
    }

    /**
     * Retourne la description détaillée d'une pièce
     * @return      La description détaillée de la pièce
    */
    public String getLongDescription()
    {
        return "You are " + this.aDescription + ".\n" + this.getLocationInfo();
    } //getDescription

    /**
     * Retourne la chemin vers l'image d'une pièce
     * @return      Le chemin d'accès à l'image
    */
    public String getImageName()
    {
        return this.aImageName;
    }
    
    /**
     * Retourne un booléen qui indique si la pièce est une salle de téléportation    
     * @return      Vrai si c'est une salle de téleportation
    */
    public boolean getTeleportRoom()
    {
        return this.aIsTeleportRoom;
    }
    
    /**
     * Définis les sorties
     * @param pNorthExit Room Sortie Nord
     * @param pSouthExit Room Sortie Sud
     * @param pEastExit Room Sortie Est
     * @param pWestExit Room Sortie Ouest
    */
    public static void setExits(final String pFromRoom, final String pNorthExit,final String pSouthExit, final String pEastExit, final String pWestExit)
    {
        if (pNorthExit != "")
            Room.setExit(pFromRoom, "north", pNorthExit, "", false);
        if (pSouthExit != "")
            Room.setExit(pFromRoom, "south", pSouthExit, "", false);
        if (pEastExit != "")
            Room.setExit(pFromRoom, "east", pEastExit, "", false);
        if (pWestExit != "")
            Room.setExit(pFromRoom, "west", pWestExit, "", false);
    }
    
    /**
     * Ajoute une sortie
     * @param pDirection String La direction à rajouter
     * @param pExit Room Room La pièce qui se trouve dans cette direction  
    */
    public static void setExit(final String pFromRoom, final String pDirection, final String pExit)
    {
        Room.setExit(pFromRoom, pDirection, pExit, "", true);
    }
    
    /**
     * Ajoute une sortie
     * @param pFromRoom String  A partir de quelle pièce
     * @param pDirection String La direction à rajouter
     * @param pExit Room Room La pièce qui se trouve dans cette direction  
     * @param pLockCode String Le code de la serrure
     * @param pLocked boolean La serrure serrure est-elle verrouillée?
    */
    public static void setExit(final String pFromRoom, final String pDirection, final String pExit, final String pLockCode, final boolean pLocked)
    {
        Room vRoom1 = Room.get(pFromRoom), vRoom2 = Room.get(pExit);
        
        // verifie si une porte existe déjà
        if (vRoom1.aExits.containsKey(pDirection)) 
            return;
        if (vRoom2.aExits.containsKey(Global.getOppositeDir(pDirection))) 
            return;

        // Ajoute une porte
        Door vDoor = new Door(pLockCode, pLocked);
        vDoor.setExit(pDirection, pExit);
        vRoom1.aExits.put(pDirection, vDoor);
        vDoor.setExit(Global.getOppositeDir(pDirection), pFromRoom);
        vRoom2.aExits.put(Global.getOppositeDir(pDirection), vDoor);
    }
        
    /**
     * Ajoute une sortie piégée
     * @param pFromRoom String  A partir de quelle pièce
     * @param pDirection String La direction à rajouter
     * @param pExit Room Room La pièce qui se trouve dans cette direction  
     * @param pError String Le code du message à renvoyer si on tente d'ouvrir la porte
    */
    public static void setTrappedExit(final String pFromRoom, final String pDirection, final String pExit, final String pError)
    {
        Room vRoom1 = Room.get(pFromRoom), vRoom2 = Room.get(pExit);
        
        // verifie si une porte existe déjà
        if (vRoom1.aExits.containsKey(pDirection))
            return;
        if (vRoom2.aExits.containsKey(Global.getOppositeDir(pDirection)))
            return;

        // Ajoute une porte
        Door vDoor = new Door();
        vDoor.setExit(pDirection, pExit);
        vRoom1.aExits.put(pDirection, vDoor);
        vDoor.setTrappedExit(Global.getOppositeDir(pDirection), pError);
        vRoom2.aExits.put(Global.getOppositeDir(pDirection), vDoor);

        Global.debug("From: "+pFromRoom+" To: "+pExit);
        Global.debug("Dir: "+pDirection);
        Global.debug("Opp Dir: "+Global.getOppositeDir(pDirection));
        Global.debug(vDoor.toString());
    }
    
    /**
     * Accesseur sur une sortie d'une salle en fonction de la direction fournie
     * @param pDirection String La direction fournie
     * @return La porte de sortie dans cette direction
    */
    public Door getExit(String pDirection)
    {
        Door vDoor = this.aExits.get(pDirection);
        
        // Si on est dans une "teleport room", alors la porte doit mener vers une destination sélectionnée au hasard
        if ((vDoor != null) && this.aIsTeleportRoom) {
            if (Room.aTeleportRoom.equals("")) {
                Random random = new Random();
                int i = random.nextInt(this.aTeleportTo.size());
                String vRoom = this.aTeleportTo.get(i);
                vDoor.setExit(pDirection, vRoom);
            }
            else {
                // This is for test purpose, you need to help the hasard
                vDoor.setExit(pDirection, Room.aTeleportRoom);
                Room.aTeleportRoom = "";
            }//if
        }//if
        return this.aExits.get(pDirection);
    }   
    
    /**
     * Pour le mode test uniquement, spécifie la prochaine pièce pour une "télépoter room"
     * @param pRoomName String     Le nom de la prochaine pièce pour se téléporter 
    */
    public static void setTeleportRoom(final String pRoomName)
    {
        Room.aTeleportRoom = pRoomName;
    } //setExit
   
    /**
     * Renvoie la liste des sorties d'une salle sous forme d'une chaine de caractère
     * @return      Retourne la liste des sorties d'une pièce
    */
    public String getLocationInfo()
    {
        String ReturnString = "Exits: [";
        Set<String> keys = this.aExits.keySet();
        for(String exit : keys) {
            ReturnString += " " + exit;
        }
        ReturnString += "]";
        return(ReturnString);
   
    }
        
    /**
     * Retourne la liste des personnages d'une pièce
     * @return      La liste des objets présent dans la pièce
    */
    public String getCharactersList()
    {
        String vS = this.aCharacters.liste(), vRst = "";
        if (vS != "") 
            vRst = "You are not alone, there's also " + vS + "\n";
        // Any greetings?
        vS = this.aCharacters.greetings();
        if (vS != "") 
            vRst +=  vS;
        return vRst;
    } //getCharactersList
        
    /**
     * Retourne la liste des personnages d'une pièce
     * @return      La liste des objets présent dans la pièce
    */
    public String saySomething(final String pSomething)
    {
        return this.aCharacters.say(pSomething);
    } //saySomething

    
}// Room