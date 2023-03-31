import java.util.HashMap;
import java.util.LinkedList;

/**
 * Décrivez votre classe ItemList ici.
 *
 * @author Alexian Plancke
 * @version Finale
 */
public class ItemList
{
    // variables d'instance - remplacez l'exemple qui suit par le vôtre
    private HashMap<String, Item> aList = new HashMap<String, Item>();

    /**
     * Constructeur d'objets de classe ItemList
     */
    public ItemList()
    {
    }

    /**
     * Ajoute un item à la liste des items
     *
     * @param       pItemName   Nom de l'item à ajouter
     * @param       pItem       Objet item à ajouter
     */
    public void addItem(String pItemName, Item pItem)
    {
        this.aList.put(pItemName, pItem);
    }

    /**
     * Supprime un item de la liste des items
     *
     * @param       pItemName   Nom de l'item à supprimer
     * @return      Objet item retiré de la liste
     */
    public Item removeItem(String pItemName)
    {
        return this.aList.remove(pItemName);
    }

    /**
     * Recherche un item de la liste des items
     *
     * @param   pItemName String  Nom de l'item 
     * @return  True si l'objet est présent dans la liste
     */
    public boolean searchItem(String pItemName)
    {
        return this.aList.containsKey(pItemName);
    }

    /**
     * Recherche un item de la liste des items
     *
     * @param   pItemName String  Nom de l'item 
     * @return  l'objet s'il est présent dans la liste
     */
    public Item findItem(String pItemName)
    {
        Global.debug("findItem("+pItemName+") => "+this.aList.get(pItemName));
        return this.aList.get(pItemName);
    }
    
    /**
     * Renvoie la liste des Items sous forme de string
     * @return  La chaine contenant la description de la liste d'items
     */
    public String listeItems() {
        String vListe = "";

        for(String vItemName: this.aList.keySet())
            vListe += vItemName+ ", ";
        if (vListe.length() > 2)
            return vListe.substring(0, vListe.length()-2);
        return vListe;
    }
    
    /**
     * Renvoie la liste des Items d'un type donné
     * @param   pType String  Le type des Items recherchés
     * @return  La liste d'items demandée
     */
    public LinkedList<Item> getItemsByType(final String pType) {
       LinkedList<Item> vResult =new LinkedList<Item>();
        
       for(HashMap.Entry<String, Item> vItem: this.aList.entrySet()) {
           if (vItem.getValue().getType().equals("KEY")) {
               vResult.add(vItem.getValue());
           }//if
       }//for
        return vResult;
    }

}
