/**
 * Classe qui permet d'analyser et de manipuler les commandes entrées par l'utilisateur
 *
 * @author Alexian Plancke
 * @version Finale
 */
public class Command
{
    private CommandWord aCommandWord;
    private String aSecondWord;
    // private static final String validCommands[] = {"go", "quit", "help", "look", "smoke", "back", "test", "inspect", "inventory", "items", "eat"};
    
    /**
     * Constructeur
     * @param pCw La chaine entrée par le joueur
    */
    public Command(final String pCw)
    {
       this.aCommandWord = CommandWords.isCommand(pCw);
       this.aSecondWord = "";
    }
    
    
    /**
     * Constructeur naturel
     * @param pCw La commande principale (action)
     * @param pSw Le complément de la commande (second mot)
    */
    public Command(final String pCw, final String pSw)
    {
        this.aCommandWord = CommandWords.isCommand(pCw);
        this.aSecondWord = pSw;
    }
    
    /**
     * Accesseur qui permet de savoir quel est la commande
    */
    public CommandWord getCommandWord()
    {
        return this.aCommandWord;
    }
    
    /**
     * Accesseur qui permet de connaître le complément de commande
    */
    public String getSecondWord()
    {
        return this.aSecondWord;
    }
    
    /**
     * Renvoi null si il n'y a pas de complément de commande
    */
    public boolean hasSecondWord()
    {
        return this.aSecondWord != null;
    }
    
    /**
     * Renvoi null si la commande tapée par l'utilisateur n'est pas reconnue
    */
    public boolean isUnknown()
    {
        return this.aCommandWord == null;
    }
} // Command
