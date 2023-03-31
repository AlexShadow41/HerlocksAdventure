import java.util.HashMap;
import java.util.Set;

/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration table of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kolling and David J. Barnes + D.Bureau
 * @version 2008.03.30 + 2019.09.25
 */
public class CommandWords
{
    // a HashMap that will hold all valid command words
    private static HashMap<String, CommandWord> aValidCommands = new HashMap<String, CommandWord>();
     
    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        for(CommandWord command : CommandWord.values()) {
            if(command != CommandWord.UNKNOWN) {
                aValidCommands.put(command.toString(), command);
            }
        }
    } // CommandWords()
    
    /**
     * Check whether a given String is a valid command word. 
     * @return true if a given string is a valid command,
     * false if it isn't.
     * @param pString String La commande à analyser
     */
    public static CommandWord isCommand( final String pString )
    {
        CommandWord vCommand = aValidCommands.get(pString);
        if (vCommand != null){
            return vCommand;
        }//if
        // if we get here, the string was not found in the commands :
        return CommandWord.UNKNOWN;
    } // isCommand()
    
    /**
     * Affiche l'ensemble des commandes valides du jeu
     * @return La chaine qui conrient les commandes du jeu
    */
    public static String getCommandList()
    {
        String vS = "";
        Set<String> keys = CommandWords.aValidCommands.keySet();
        
        for(String key: keys){
            vS += key + " ";
        }//for
        return(vS);
    }//getCommandList()
} // CommandWords

