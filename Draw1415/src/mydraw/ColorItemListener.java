package mydraw;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Managing colors selected by the user
 * @author Michael
 *
 */

public class ColorItemListener implements ItemListener
{

    DrawGUI gui;

    public ColorItemListener(DrawGUI itsGUI)
    {
        gui = itsGUI;
    }

    // user selected new color => store new color in DrawGUIs
    public void itemStateChanged(ItemEvent e)
    {
        if (e.getItem()
            .equals("Black"))
        {
            gui.color = Color.black;
        }
        else if (e.getItem()
            .equals("White"))
        {
            gui.color = Color.white;
        }
        else if (e.getItem()
            .equals("Green"))
        {
            gui.color = Color.green;
        }
        else if (e.getItem()
            .equals("Red"))
        {
            gui.color = Color.red;
        }
        else if (e.getItem()
            .equals("Blue"))
        {
            gui.color = Color.blue;
        }

    }
}
