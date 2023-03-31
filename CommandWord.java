
/**
 * Décrivez votre classe CommandWord ici.
 *
 * @author Alexian Plancke
 * @version Finale
 */

public enum CommandWord
{
    // GO, QUIT, BACK, HELP, LOOK, TAKE, DROP, SMOKE, ITEMS, TEST, INSPECT, EAT, UNKNOWN;
    LOOK("look"), GO("go"),BACK("back"), QUIT("quit"), HELP("help"), TAKE("take"), DROP("drop"), SMOKE("smoke"), ITEMS("items"), TEST("test"), 
        INVENTORY("inventory"), INSPECT("inspect"), EAT("eat"), LOCK("lock"), UNLOCK("unlock"), UNKNOWN("***"), CHARGE("charge"), FIRE("fire"), 
        ALEA("alea"), SAY("say");
    
    private String commandString;
    
    /**
     * Command word initialization
     * @param commandWord The command as expected from user
     */
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }
    
    /**
     * @return The command word as a string.
     */
    public String toString()
    {
        return commandString;
    }
}


