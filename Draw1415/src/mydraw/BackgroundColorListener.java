package mydraw;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Managing background color of the drawing 
 * @author Michael
 *
 */

public class BackgroundColorListener implements ItemListener
{

    DrawGUI gui;

    public BackgroundColorListener(DrawGUI itsGUI)
    {
        gui = itsGUI;
    }

    @Override
    public void itemStateChanged(ItemEvent e)
    {
        if (e.getItem()
            .equals("Black"))
        {
            gui.colorBackground = Color.black;
        }
        else if (e.getItem()
            .equals("Green"))
        {
            gui.colorBackground = Color.green;
        }
        else if (e.getItem()
            .equals("Red"))
        {
            gui.colorBackground = Color.red;
        }
        else if (e.getItem()
            .equals("Blue"))
        {
            gui.colorBackground = Color.blue;
        }
        else if (e.getItem()
            .equals("White"))
        {
            gui.colorBackground = Color.white;
        }
        gui.draw();
    }

}
