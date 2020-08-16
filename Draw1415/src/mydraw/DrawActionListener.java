package mydraw;

/**
 * Here's a the class used for action listeners for the buttons 
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class DrawActionListener implements ActionListener
{
    private DrawGUI _drawActionListenerGUI;
    private String _drawActionListenerCommand;

    /**
     * Constructor
     * @param itsCommand Command reference 
     * @param itsGUI GUI reference
     */
    public DrawActionListener(String itsCommand, DrawGUI itsGUI)
    {
        _drawActionListenerCommand = itsCommand;
        _drawActionListenerGUI = itsGUI;
    }

    public void actionPerformed(ActionEvent e)
    {
        _drawActionListenerGUI.doCommand(_drawActionListenerCommand);
    }
}