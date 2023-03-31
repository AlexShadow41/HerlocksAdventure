/**
 * Ce sont les Items que le joueur peut rammasser dans une pièce
 *
 * @author Alexian Plancke
 * @version Finale
 */
public class Item
{
    private String aDescription = "";
    private String aLongDescription = "";
    private String aType = "Item";
    private String aCharacteristic = "";
    private int aWeight = 0;
    private int aCount = 0;
    private int aMaxCount = 1;
    
    public Item()
    {
    }
    
    /**
     * 
     * @param pD String La description
     * @param pW int Le poids
     */
    public Item(String pD, int pW)
    {
        this.aDescription = pD;
        this.aWeight = pW;
    }
    
    /**
     * 
     * @param pD String La description
     * @param pW int Le poids
     * @param pType String Le type de l'objet (Key)
     * @param pValue String Caractéristique de l'objet
     */
    public Item(String pD, int pW, String pType, String pValue)
    {
        this.aDescription = pD;
        this.aWeight = pW;
        this.aType = pType;
        this.aCharacteristic = pValue;
    }
    
    /**
     * Initialise la "Description"
     * @param pD String La description
     */
    public void setDescription(final String pD)
    {
        this.aDescription = pD;
    }
    
    /**
     * Initialise la "Description précise"
     * @param pLD String La description plus précise de l'Item
     */
    public void setLongDescription(final String pLD)
    {
        this.aDescription = pLD;
    }

    /**
     * Initialise la charactéristique d'un objet 
     * @param pType String Le type de l'Item
     * @param pLD String La caractéristique (pour une clef, le code qui correspond à la serrure qu'elle ouvre)
     */
    public void setCharacteristic(final String pType, final String pValue)
    {
        this.aType = pType;
        this.aCharacteristic = pValue;
    }
    
    /**
     * Initialise le "poids"
     * @param pW int Le poids
     */
    public void setWeight(int pW)
    {
        this.aWeight = pW;
    }
    
    /**
     * Permet d'obtenir la description d'un Item
     * @return     Description de l'item
    */
    public String getDescription()
    {
        return this.aDescription;
    }
    
    /**
     * Permet d'obtenir la description précise d'un Item
     * @return     Description détaillée de l'item
    */
    public String getLongDescription()
    {
        return this.aLongDescription;
    }
    
    /**
     * Permet d'obtenir le poids d'un item
     * @return     Poids de l'item
    */
    public int getWeight()
    {
        return this.aWeight;
    }

    /**
     * Permet d'obtenir le nombre d'items
     * @return     Nombre d'occurence(s) de l'item
    */
    public int getCount()
    {
        return this.aCount;
    }

    /**
     * Permet de consulter le type d'un Item
     * @return  Le type
    */
    public String getType()
    {
        return this.aType;
    }

    /**
     * Permet de consulter la caractéristique d'un objet
     * @return  La valeur caractéristique
    */
    public String getCharacteristic()
    {
        return this.aCharacteristic;
    }

    /**
     * Permet de consulter ce qui compose un objet (pour debug)
     * @return  La chaine contenant les informations qui font l'Item
    */
    public String toString()
    {
        return this.aDescription + " type:"+ this.aType + " char:" + this.aCharacteristic + " W:"+this.aWeight;
    }

}
