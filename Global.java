
/**
 * Contient les variables globales
 *
 * @author Alexian Plancke
 * @version Finale
 */
public class Global
{
    private static boolean aDebug = false;

    /**
     * Constructeur d'objets de classe Global
     * @return Booléen qui indique si le mode debug est actif
     */
    public Global()
    {
    }

    public static boolean getDebug(){
        return aDebug;
    }
    
       /**
     * Renvoie la direction opposée
     * @param pDir String    La direction 
     * @return  La chaine contenant la direction opposée
     */
    public static String getOppositeDir(final String pDir){
        if (pDir.equals("north"))
            return "south";
        if (pDir.equals("south"))
            return "north";
        if (pDir.equals("east"))
            return "west";
        if (pDir.equals("west"))
            return "east";
        if (pDir.equals("up"))
            return "down";
        if (pDir.equals("down"))
            return "up";
        return "ERROR";
    }
    
    /**
     * Converti une erreur en message pour l'utilisateur
     * @param pCode String Code d'erreur
     * @return Message en clair
    */
    public static String getMessage(final String pCode){
        if (pCode.equals("OK-LOCKED"))
            return "You lock the door";
        if (pCode.equals("OK-UNLOCKED"))
            return "You unlock the door";
        if (pCode.equals("ERROR-NODOOR"))
            return "There is no door in this direction!";
        if (pCode.equals("ERROR-LOCKED"))
            return "Sorry, you can't go back further";
        if (pCode.equals("ERROR-NOMATCH"))
            return "Sorry, you don't have any key that fit this lock";
        if (pCode.equals("ERROR-BLOCKED"))
            return "Sorry, this door appears to be blocked from the other side";
        if (pCode.equals("ERROR-NOBACK"))
            return "Sorry, you can't go back further";
        if (pCode.equals("ERROR-NOITEM"))
            return "Sorry, you don't have the required item in your inventory";
        return "Sorry, something bad happened";
    }
    
    /**
     * Affiche un message de mise au point sur la console si le mode debug est activé
     * @param pMessage String Message à afficher
    */
    public static void debug(final String pMessage){
        if (aDebug)
            System.out.println(pMessage);
    }

}
