import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.awt.image.*;
import javax.swing.JOptionPane;

/**
 * This class implements a simple graphical user interface with a text entry
 * area, a text output area and an optional image.
 * 
 * @author Michael Kolling
 * @version 1.0 (Jan 2003) DB edited (2019)
 */
public class UserInterface implements ActionListener
{
    private GameEngine aEngine;
    private JFrame     aMyFrame;
    private JTextField aEntryField;
    private JTextArea  aLog;
    private JLabel     aImage;
    private JTextField aTime = new JTextField();
    private JButton    aQuitButton;
    private JButton    aNorthButton;
    private JButton    aSouthButton;
    private JButton    aEastButton;
    private JButton    aWestButton;
    private JButton    aBackButton;
    private JButton    aUpButton;
    private JButton    aDownButton;
    private JButton    aInventoryButton;

    /**
     * Construct a UserInterface. As a parameter, a Game Engine
     * (an object processing and executing the game commands) is
     * needed.
     * 
     * @param pGameEngine GameEngine The GameEngine object implementing the game logic.
     */
    public UserInterface( final GameEngine pGameEngine )
    {
        this.aEngine = pGameEngine;
        this.createGUI();
    } // UserInterface(.)

    /**
     * Print out some text into the text area.
     * @param pText String Texte à afficher
     */
    public void print( final String pText )
    {
        this.aLog.append( pText );
        this.aLog.setCaretPosition( this.aLog.getDocument().getLength() );
    } // print(.)

    /**
     * Print out some text into the text area, followed by a line break.
     * @param pText String Le texte à afficher
     */
    public void println( final String pText )
    {
        this.print( pText + "\n" );
    } // println(.)

    /**
     * Show an image file in the interface.
     * @param pImageName String Nom de l'Image
     */
    public void showImage( final String pImageName )
    {
        // this.println("Working Directory = " + System.getProperty("user.dir"));
        String vImagePath = "img/" + pImageName; // to change the directory
        URL vImageURL = this.getClass().getClassLoader().getResource( vImagePath );
        if ( vImageURL == null ) 
            // System.out.println( "Image not found : " + vImagePath );
            this.println( "Image not found : " + vImagePath );
        else {
            ImageIcon vIcon = new ImageIcon( vImageURL );
            this.aImage.setIcon( vIcon );
            this.aMyFrame.pack();
        }
    } // showImage(.)

    /**
     * Enable or disable input in the input field.
     * @param pOnOff boolean Switch that indicate if the control should be enable or disable
     */
    public void enable( final boolean pOnOff )
    {
        this.aEntryField.setEditable( pOnOff ); // enable/disable
        if ( ! pOnOff ) { // disable
            this.aEntryField.getCaret().setBlinkRate( 0 ); // cursor won't blink
            this.aEntryField.removeActionListener( this ); // won't react to entry
        }
    } // enable(.)

    /**
     * Set up graphical user interface.
     */
    private void createGUI()
    {
        this.aMyFrame = new JFrame( "The crimson crime" ); // change the title
        this.aEntryField = new JTextField( 34 );

        this.aLog = new JTextArea();
        this.aLog.setEditable( false );
        JScrollPane vListScroller = new JScrollPane( this.aLog );
        vListScroller.setPreferredSize( new Dimension(200, 170) );
        vListScroller.setMinimumSize( new Dimension(100,100) );

        JPanel vPanel = new JPanel();
        JPanel vSubPanel = new JPanel();
        
        this.aImage = new JLabel();

        vPanel.setLayout( new BorderLayout() ); // ==> only five places
        vPanel.add( this.aImage, BorderLayout.NORTH );
        vPanel.add( vListScroller, BorderLayout.CENTER );
        vPanel.add( this.aEntryField, BorderLayout.SOUTH );

        this.aMyFrame.getContentPane().add( vPanel, BorderLayout.CENTER );
        
        //        vSubPanel.setLayout(new BoxLayout(vSubPanel, BoxLayout.Y_AXIS));
        vSubPanel.setLayout(new GridLayout(26,1));
                
        this.aInventoryButton = new JButton("  Inventory  ");
        this.aNorthButton = new JButton("  North  ");
        this.aSouthButton = new JButton("  South  ");
        this.aEastButton = new JButton("  East  ");
        this.aWestButton = new JButton("  West  ");
        this.aUpButton = new JButton("  Up  ");
        this.aDownButton = new JButton("  Down  ");
        this.aBackButton = new JButton("  Back  ");
        this.aQuitButton = new JButton("  Quit  ");
        
        Dimension d = this.aInventoryButton.getMaximumSize();
        d.setSize(d.getWidth()*1.2, d.getHeight()); 

        this.aNorthButton.setMinimumSize(new Dimension (d));
        this.aNorthButton.setMaximumSize(new Dimension (d));
        this.aSouthButton.setMinimumSize(new Dimension (d));
        this.aSouthButton.setMaximumSize(new Dimension (d));
        this.aSouthButton.setMinimumSize(new Dimension (d));
        this.aSouthButton.setMaximumSize(new Dimension (d));
        this.aEastButton.setMinimumSize(new Dimension (d));
        this.aEastButton.setMaximumSize(new Dimension (d));
        this.aWestButton.setMinimumSize(new Dimension (d));
        this.aWestButton.setMaximumSize(new Dimension (d));
        this.aBackButton.setMinimumSize(new Dimension (d));
        this.aBackButton.setMaximumSize(new Dimension (d));
        this.aQuitButton.setMinimumSize(new Dimension (d));
        this.aQuitButton.setMaximumSize(new Dimension (d));
        this.aInventoryButton.setMinimumSize(new Dimension (d));
        this.aInventoryButton.setMaximumSize(new Dimension (d));
        
        d.setSize(d.getWidth(), 30); 
        this.aTime.setPreferredSize(d);
        this.aTime.setMinimumSize(d);
        this.aTime.setMaximumSize(d);
        this.aTime.setHorizontalAlignment(JTextField.CENTER);
        
        // this.aMyFrame.getContentPane().add( this.aQuitButton, BorderLayout.EAST );
        this.aTime.setText("00:00:00");
        vSubPanel.add(this.aTime);
        vSubPanel.add(this.aNorthButton);
        vSubPanel.add(this.aSouthButton);
        vSubPanel.add(this.aEastButton);
        vSubPanel.add(this.aWestButton);
        vSubPanel.add(this.aUpButton);
        vSubPanel.add(this.aDownButton);
        vSubPanel.add(this.aBackButton);
        vSubPanel.add(this.aInventoryButton);
        vSubPanel.add(this.aQuitButton);

        this.aMyFrame.getContentPane().add( vSubPanel, BorderLayout.EAST );
        
        // add some event listeners to some components
        this.aEntryField.addActionListener( this );
        this.aInventoryButton.addActionListener( this );
        this.aQuitButton.addActionListener( this );
        this.aNorthButton.addActionListener( this );
        this.aSouthButton.addActionListener( this );
        this.aEastButton.addActionListener( this );
        this.aWestButton.addActionListener( this );
        this.aUpButton.addActionListener( this );
        this.aDownButton.addActionListener( this );
        this.aBackButton.addActionListener( this );

        this.aMyFrame.getContentPane().add( vSubPanel, BorderLayout.EAST );

        // to end program when window is closed
        this.aMyFrame.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent e) { System.exit(0); }
        } );
        
        this.aMyFrame.pack();
        this.aMyFrame.setVisible( true );
        this.aEntryField.requestFocus();
    } // createGUI()

    /**
     * Actionlistener interface for entry textfield.
     * @param pE ActionEvent Evènement à gérer
     */
    public void actionPerformed( final ActionEvent pE ) 
    {
        // Check if type of action is on Button:
        if (pE.getSource() == this.aQuitButton)
        {  
            this.aEntryField.setText( "quit" );        
        }
        else if (pE.getSource() == this.aBackButton)
        {
            this.aEntryField.setText( "back " );
        }
        else if (pE.getSource() == this.aNorthButton)
        {
            this.aEntryField.setText( "go north" );
        }
        else if (pE.getSource() == this.aSouthButton)
        {
            this.aEntryField.setText( "go south" );
        }
        else if (pE.getSource() == this.aEastButton)
        {
            this.aEntryField.setText( "go east" );
        }
        else if (pE.getSource() == this.aUpButton)
        {
            this.aEntryField.setText( "go up" );
        }
        else if (pE.getSource() == this.aDownButton)
        {
            this.aEntryField.setText( "go down" );
        }
        else if (pE.getSource() == this.aInventoryButton)
        {
            this.aEntryField.setText( "inventory" );
        }
        else if (pE.getSource() == this.aWestButton)
        {
            this.aEntryField.setText( "go west" );
        }//if
        
        // If no other type of action, the only one possible action is (text input) :
        boolean vGameOver = this.processCommand(); // never suppress this line

        // C'est ennervant d'avoir à cliquer dans le champs à chaque fois pour taper une commande
        this.aEntryField.requestFocus();
    } // actionPerformed(.)

    /**
     * Show an image file in the interface.
     * @param pSeconds int Nobre de secondes
     */
    public void showTime( final int pSeconds )
    {
        int vHour, vMin, vSec;
        vHour = (int) (pSeconds / 3600);
        vSec = pSeconds % 60;
        vMin = (int) ((pSeconds-(vHour*60)-vSec) / 60);
        this.aTime.setText( String.format("%02d:%02d:%02d", vHour, vMin, vSec));
    }
    
    /**
     * Display a Yes/No modal window a get the user choice
     * @param pTitle String The title of this Ok/Cancel modal
     * @param pQuery String The query the user must answer to
     * @return Returns true if the user hits Ok, false otherwise
     */
    public boolean okCancel( final String pTitle, final String pQuery )
    {
        int vResult = JOptionPane.showConfirmDialog(this.aMyFrame, pQuery, pTitle, JOptionPane.YES_NO_OPTION);
        return (vResult == JOptionPane.YES_OPTION);
    }
    
    /**
     * A command has been entered. Read the command and do whatever is 
     * necessary to process it.
     * @return Returns true if the game is over
     */
    private boolean processCommand()
    {
        String vInput = this.aEntryField.getText();
        this.println("> "+vInput);
        this.aEntryField.setText( "" );
        vInput = vInput.toLowerCase()+" *";

        // this.aEngine.interpretCommand( vInput );
        String[] vSplit = vInput.split(" ");
        Command vC = new Command(vSplit[0], vSplit[1]);
        return this.aEngine.processCommand(vC, false);
    } // processCommand()
} // UserInterface 
