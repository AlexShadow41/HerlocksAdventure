import java.util.List;
import java.util.Stack;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Décrivez votre classe GameEngine ici.
 *
 * @author Alexian Plancke
 * @version Finale
 */
public class GameEngine
{
    // private Room aCurrentRoom;
    // private Stack <Room> aPreviousRooms = new Stack<Room>();
    public final static int ONE_SECOND = 1000;
    private Parser aParser;
    private UserInterface aGui;
    private Player aPlayer;
    private Timer aTimer;
    private static int aElapsed;
    
    /**
     * Constructeur par défaut
    */
    public GameEngine ()
    {
        this.aParser = new Parser();
        
        // Initialize the rooms of the game
        createRooms();
        
        // Create a time that will count the time spent in the game
        GameEngine.aElapsed = 0;
        this.aTimer = new Timer("Timer");
    }

    /**
     * Méthode qui permet de démarrer une session du jeu
     * @param pUserInterface UserInterface L'interface graphique
    */
    public void setGUI( final UserInterface pUserInterface )
    {
        this.aGui = pUserInterface;
        this.aPlayer = new Player();
        this.printWelcome();
        // Start the count
        this.aTimer.schedule(new TimerTask() {
            public void run() {
               GameEngine.aElapsed += 1;
               aGui.showTime( GameEngine.aElapsed );
            }
        }, 0, ONE_SECOND);
    }
    
    /**
     * Affiche le message d'accueil du jeu
    */
    private void printWelcome()
    {
        String vName = javax.swing.JOptionPane.showInputDialog( "Quel est ton prenom ?" );
        if (! vName.equals(""))
            this.aPlayer.setName(vName);
        this.aGui.println("Dear "+this.aPlayer.getName()+", welcome to 'The Crimson Alibi'");
        this.aGui.println("You are in London during the 19th century");
        this.aGui.println("");
        this.aGui.println("Type 'map' if you need the map.");
        printLocationInfo(this.aPlayer.getCurrentRoom());
        this.aGui.println("");
    }
    
    /**
     * Permet de créer l'ensemble des salles auquel le joueur a accès
    */
    private void createRooms()
    {
         //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
         new Room("Road1", "Outside the main entrance of your apartment","Road1.jpg", true);
         new Room("MainEntry221B", "In the entry of your apartment","BakerStreet.jpg", true);
         new Room("lounge221B", "In the lounge of your apartment","221BSalon.jpg", true);
         new Room("OfficeSH221B", "In Sholmes's Room","BureauHerlock.jpg", true);
         new Room("OfficeJW221B", "In Tatswon's Room","BureauTattswon.jpg", true);
         new Room("Road2", "Outside the main entrance of the crime scene","Road2.jpg", false);
         new Room("Bar", "In the nearest bar","Pub.jpg", false);
         new Room("Garden", "In the garden","Garden.jpg", false);
         new Room("CrimeRoom", "Scene of the crime","CrimeRoom.jpg", false);
         new Room("Cellar", "Cellar","Cellar.jpg", false);
         new TeleportRoom("Cellar2", "Cellar2","Cellar2.jpg", false);
         new Room("Irregulars", "Irregulars","Irregulars.png", true);

         this.addItem("lounge221B", "Pipe", "Smoking Pipe", 250, "This is Herlock's smocking pipe.\nWithout it, Herlock can't think properly");
         this.addItem("lounge221B", "Magnifier", "Magnifier", 520, "This is Herlock's Magnifier.\nWithout it, Herlock can't find clues");
         this.addItem("OfficeSH221B", "Luminol", "Luminol  flask", 350, "This is a product invented by Herlock.\nWith it, you can discover blood spatter");
         this.addItem("CrimeRoom", "Ring", "Wedding Ring", 80, "A strange Ring found in the crime scene.\nIt's a German brand");
         this.addItem("OfficeSH221B", "Cane", "Dagger-Cane", 1500, "This is Herlock's cane.\nUse it if you have to fight");
         this.addItem("OfficeJW221B", "Revolver", "Revolver", 1200, "This is Tattswon's revolver.\nUse it if you have to fight");
         this.addItem("Road1", "Card", "Business Card", 5, "The Business Card of a Detective called Herlock Sholmes");
         this.addItem("Road1", "Anvil", "Anvil", 80*1000, "Probably opportuniscally forgoten here by some blacksmith");
         this.addItem("lounge221B", "Cookie", "Magic cookie", 40, "Something cooked by Miss Deshons, a real fairy, isn't she?");
         this.addItem("Irregulars", "Key", "Key", 40, "Small key").setCharacteristic("KEY", "JB007");
         this.addItem("Cellar", "Beamer", "Beamer", 900, "A device to teleport you in the memorized place").setCharacteristic("BEAMER", "OfficeSH221B");
         
         Character vW = this.addCharacter("OfficeJW221B", "Tatswon", "Dr Tatswon");
         vW.setSpeech("hello", "Good evening, my dear Sholmes");
         vW.setSpeech("bye", "Bye");
         Character vH = this.addCharacter("lounge221B", "Shedons", "Ms Dustons");
         vH.setSpeech("hello", "Good evening, Mr Sholmes");
         vH.setSpeech("bye", "Bye");
         Character vL = this.addCharacter("CrimeRoom", "Lestrade", "Inspector Lestrade");
         vL.setSpeech("hello", "Sholmes! What do you do in this area?!");
         vL.setSpeech("bye", "Go to hell!");
         
         Room.setExits("Irregulars", "Road1", "", "", "");
         Room.setExits("Road1", "MainEntry221B", "Irregulars", "", "Road2");
         Room.setExits("MainEntry221B", "", "Road1", "", "lounge221B");
         Room.setExits("lounge221B", "OfficeSH221B", "OfficeJW221B", "MainEntry221B", "");
         Room.setExits("OfficeSH221B", "", "lounge221B", "", "");
         Room.setExits("OfficeJW221B", "lounge221B", "", "", "");
         Room.setExits("Road2", "Garden", "Bar", "Road1", "");
         Room.setExits("Bar", "Road2", "", "", "");
         Room.setExits("Garden", "CrimeRoom", "Road2", "", "");
         Room.setExits("CrimeRoom", "", "Garden", "", "");
         Room.setExits("Cellar2", "", "Cellar", "", "");
         // this.setExit("MainEntry221B", "Cellar", "down", "up", "JB007");
         this.setTrappedExit("MainEntry221B", "Cellar", "down", "ERROR-BLOCKED");
         //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }// createRooms
    
    /**
     * Ajoute un nouvel Item dans une salle
     * @param pRoomName String Nom de la salle
     * @param pObjName String Nom de l'objet
     * @param pObjD String Description de l'objet
     * @param pObjW int Poids de l'objet
     * @param pObjLD String Description plus précise de l'objet (avec la loupe)
     * @return  Le nouvel item
    */
    private Item addItem(final String pRoomName, final String pObjName, final String pObjD, final int pObjW, final String pObjLD)
    {
        Room vRoom = Room.get(pRoomName);
        Item vItem = vRoom.addItem(pObjName.toLowerCase(), pObjD, pObjW, pObjLD);
        return vItem;
    }
        
    /**
     * Ajoute un nouveau personnage dans une salle
     * @param pRoomName String Nom de la salle
     * @param pName String Nom du personnage
     * @return  Le nouveau personnage
    */
    private Character addCharacter(final String pRoomName, final String pName, final String pDescription)
    {
        Room vRoom = Room.get(pRoomName);
        Character vCharacter = vRoom.addCharacter(pName.toLowerCase(), pDescription);
        return vCharacter;
    }

    /**
     * Connecte deux salles
     * @param pRoomA String La première salle
     * @param pRoomB String La seconde salle
     * @param pDirA String Direction A vers B
     * @param pDirB String Direction B vers A
     * @param pKey  String qui vaut "" si pas de serrure
     */
    private void setExit(final String pRoomA, final String pRoomB, final String pDirA, final String pDirB, final String pKeyCode)
    {
        Room.setExit(pRoomA, pDirA, pRoomB, pKeyCode, ! pKeyCode.equals(""));
        Room.setExit(pRoomB, pDirB, pRoomA, pKeyCode, ! pKeyCode.equals(""));
    }

    /**
     * Connecte deux salles, à sens unique
     * @param pRoomA String La première salle
     * @param pDirA String Direction A vers B
     * @param pRoomB String La seconde salle
     * @param pError Expliquer pourquoi il n'est pas possible de revenir en arrière
     */
    private void setTrappedExit(final String pRoomA, final String pRoomB, final String pDirA, final String pError)
    {
        Room.setTrappedExit(pRoomA, pDirA, pRoomB, pError);
    }
    
    /**
     * Permet au joueur de quitter le jeu
     * @param pC Command La commande à interpréter
     * @return Retourne true si on quitte
    */
    private boolean quit(Command pC)
    {
        if (this.aGui.okCancel("Before you go...", "Do you really want to quit?"))
            System.exit(0);
        return false;
    }
    
    /**
     * Affiche l'aide du jeu
    */
    private void printHelp()
    {
        this.aGui.println("");
        this.aGui.println("You wander around London.");
        this.aGui.println("Your command words are:");
        this.aGui.print(CommandWords.getCommandList());
    }
    
    /**
     * affiche les informations relatives à une pièce
     * @param pRoom Room La pièce en question
    */
    private void printLocationInfo(final Room pRoom)
    {
        this.aGui.println(pRoom.getLongDescription());
        String vString = pRoom.getItemList();
        if (! vString.equals(""))
            this.aGui.println(vString);
        vString = pRoom.getCharactersList();
        if (! vString.equals(""))
            this.aGui.println(vString);
        this.aGui.showImage( pRoom.getImageName() );
    }
    
    /**
     * Permet de récupérer une commande tapée par l'utilisateur et de la traiter
     * @param pC Command Commande à traiter
     * @return   Renvoie false
    */
    public boolean processCommand(final Command pC, final boolean pIsTest)
    {
        String vRst;
        Item vItem;
        CommandWord vCmd = pC.getCommandWord();
        
        // Il faut résoudre le mystère en moins de 5 minutes
        if (GameEngine.aElapsed > 300) {
            if (vCmd == CommandWord.QUIT)
                return(this.quit(pC));
            this.aGui.println("You couldn't solve the mystery fast enough and the murderer managed to escape.");
            this.aGui.println("Try again and hurry up!");
            return true;
        }//if
                
        switch(vCmd) {
            case BACK:
            case GO:
                goRoom(pC);
                return(false);
            case HELP:
                printHelp();
                return(false);
            case QUIT:
                return(this.quit(pC));
            case SMOKE:
                this.smoke();
                return(false);
            case EAT:
                if (checkSecondWord(pC, "Please indicate which object to be dropped")){
                this.aGui.println(this.aPlayer.eat(pC.getSecondWord()));
                }
                return(false);
            case TAKE:
                if (checkSecondWord(pC, "Please indicate which object to be taken")){
                this.aGui.println(this.aPlayer.take(pC.getSecondWord()));
                }
                return(false);
            case DROP:
                if (checkSecondWord(pC, "Please indicate which object to be dropped")){
                this.aGui.println(this.aPlayer.drop(pC.getSecondWord()));
                }
                return(false);
            case INVENTORY:
            case ITEMS:
                this.aGui.println("Inventory: "+ this.aPlayer.inventory());
                return(false);
            case LOOK:
                this.look(); 
                return(false);
            case LOCK:
                this.aGui.println(Global.getMessage(this.aPlayer.lockOrUnlockDoor(true, pC.getSecondWord())));
                return(false);
            case UNLOCK:
                this.aGui.println(Global.getMessage(this.aPlayer.lockOrUnlockDoor(false, pC.getSecondWord())));
                return(false);
            case CHARGE:
                // Vérifie si le joueur a le Beamer dans son inventaire
                vItem = this.aPlayer.getItem("beamer");
                if (vItem == null)
                {
                    this.aGui.println(Global.getMessage("ERROR-NOITEM"));
                    return(false);
                }
                // Memorise la position de la salle actuelle dans le beamer
                vItem.setCharacteristic("BEAMER",this.aPlayer.getCurrentRoomCode());
                return(false); 
            case FIRE:
                // Vérifie si le joueur a le Beamer dans son inventaire
                vItem = this.aPlayer.getItem("beamer");
                if (vItem == null)
                {
                    this.aGui.println(Global.getMessage("ERROR-NOITEM"));
                    return(false);
                }
                // Change la salle actuelle par celle mémorisée dans le beamer
                aPlayer.flushBack();
                aPlayer.setCurrentRoom(vItem.getCharacteristic());
                printLocationInfo(this.aPlayer.getCurrentRoom());
                return(false);
            case TEST:
                // Read file
                try
                {
                    Scanner vScanner = new Scanner(new File(pC.getSecondWord()+".txt"));
                    while (vScanner.hasNextLine()) {
                        String vInput = vScanner.nextLine();
                        this.aGui.println(vInput);
                        vInput += " *";
                        String[] vSplit = vInput.split(" ");
                        Command vC = new Command(vSplit[0], vSplit[1]);
                        this.processCommand(vC, true);
                    }// while
                }
                catch (java.io.FileNotFoundException ff)
                {
                    this.aGui.println("File not found");
                    return(false);
                }
                return(false);
            case ALEA:
                if (! pIsTest)
                    // Tricheur!
                    return(false);
                Room.setTeleportRoom(pC.getSecondWord());
                return(false);
            case INSPECT:
                if (checkSecondWord(pC, "Please indicate which object to be inspected")){
                    this.aGui.println(this.aPlayer.inspect(pC.getSecondWord()));
                }
                return(false);
            case SAY:
                if (checkSecondWord(pC, "Sorry, whay do you want to say")){
                    this.aGui.println(this.aPlayer.getCurrentRoom().saySomething(pC.getSecondWord()));
                }
                return(false);
            case UNKNOWN: 
            default:
                this.aGui.println("This command is unknown");
                return(false);
        }
    }
    
    /**
     * Check if the player has specified a second word when the command requires one
     * Display an error message if not the case
     * @param pCmd Command Commande qui contient l'instruction à tester
     * @return Renvoie true si le joueur a entré unsecond terme
    */
    private boolean checkSecondWord(final Command pCmd, final String pErrorMsg)
    {
        if (pCmd.hasSecondWord()) {
            return true;
        }
        this.aGui.println(pErrorMsg);
        return false;
    }
    
    /**
     * Se déplacer dans une pièce
     * @param pCmd Command Commande qui contient l'instruction, c'est à dire la direction dans laquelle aller
    */
    private void goRoom(final Command pCmd)
    {

        CommandWord vCmd = pCmd.getCommandWord();
        String vNextRoom, vDirection = pCmd.getSecondWord();
        if (vCmd == CommandWord.BACK) {
            vNextRoom = this.aPlayer.go("back");
        }
        else {
            if (! checkSecondWord(pCmd, "Go where?")) {
                return;
            }
            vNextRoom = this.aPlayer.go(vDirection);
        }//if
        
        Global.debug("[DEBUG] aPlayer.go("+ vDirection + ") => returned " + vNextRoom );
        Global.debug("[DEBUG] Current Room =>" + this.aPlayer.getCurrentRoom() );

        // Si le joueur rencontre un BLOCKED lors d'un BACK, l'historique ne sert plus à rien, il ne peut plus revenir en arrière
        if ((vCmd == CommandWord.BACK) && (vNextRoom.equals("ERROR-ROOM"))) {
            this.aPlayer.flushBack();
        }//if
        

        if (vNextRoom.startsWith("ERROR-"))
        {
            this.aGui.println(Global.getMessage(vNextRoom));
            return;
        } 
        printLocationInfo(this.aPlayer.getCurrentRoom());
        
    }
    
    /**
     * Execute la commande look
    */
    private void look(){
        this.aGui.println(this.aPlayer.getCurrentRoom().getLongDescription());
        this.aGui.println(this.aPlayer.getCurrentRoom().getItemList());
    }
    
    /**
     * Execute la commande smoke
    */
    private void smoke(){
        this.aGui.println("You take your pipe out of your pocket, some tobacco, and look at some clues while smoking");
    }
    
} // Game

