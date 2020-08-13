package mydraw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Here's a local class used for action listeners for the buttons
 * @author Michael
 *
 */

public class DrawActionListener implements ActionListener
{

    private String command;
    private DrawGUI gui;

    public DrawActionListener(String cmd, DrawGUI drawGUI)
    {
        command = cmd;
        gui = drawGUI;
    }

    public void actionPerformed(ActionEvent e)
    {
        gui.doCommand(command);
    }

}
