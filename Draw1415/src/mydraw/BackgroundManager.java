package mydraw;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class BackgroundManager implements ItemListener
{

    private DrawGUI _bmGUI;

    /**
     * Constructor
     * @param itsGUI GUI reference
     */
    public BackgroundManager(DrawGUI itsGUI)
    {
        _bmGUI = itsGUI;
    }

    @SuppressWarnings("unused")
    @Override
    public void itemStateChanged(ItemEvent e)
    {
        String str = (String) e.getItem();
        Color convertedColor = _bmGUI.model.hashkeyString(str);
        CommandBackgroundColor command = new CommandBackgroundColor(_bmGUI,
                convertedColor, _bmGUI.model);
        _bmGUI.redraw();
    }

}
