package mydraw;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

class ColorItemListener implements ItemListener
{
    private DrawGUI _colorItemGUI;

    /**
     * Constructor
     * @param itsGUI GUI reference
     */
    ColorItemListener(DrawGUI itsGUI)
    {
        _colorItemGUI = itsGUI;
    }

    /**
     * If user selected new color then store new color in DrawGUI
     */
    public void itemStateChanged(ItemEvent e)
    {
        _colorItemGUI.color = _colorItemGUI.model.stringColor.get(e.getItem());
    }

}
