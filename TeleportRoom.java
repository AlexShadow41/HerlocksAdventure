/**
 * Classe qui sert représenter chacunes des pièces que le joueur peut visiter dans le jeu
 *
 * @author Alexian Plancke
 * @version Finale
 * 
 */
public class TeleportRoom extends Room
{
    /**
     * Constructeur d'objets de classe teleportRoom
     */
    public TeleportRoom(final String pName, final String pD, final String pI, final boolean pTeleport)
    {
        super(pName, pD, pI, pTeleport);
        // initialisation des variables d'instance
        super.aIsTeleportRoom = true;
    }

}
