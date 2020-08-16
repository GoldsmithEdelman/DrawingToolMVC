package mydraw;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class WindowListner implements ComponentListener
{
    private DrawGUI _windowListenerGUI;

    /**
     * Constructor
     * @param itsGUI
     */
    public WindowListner(DrawGUI itsGUI)
    {
        _windowListenerGUI = itsGUI;
    }

    @Override
    public void componentHidden(ComponentEvent e)
    {
    }

    @Override
    public void componentMoved(ComponentEvent e)
    {
    }

    @Override
    public void componentResized(ComponentEvent e)
    {
        _windowListenerGUI.model.setBufferSizeColor(
                _windowListenerGUI.getDrawingArea()
                    .getWidth(),
                _windowListenerGUI.getDrawingArea()
                    .getHeight(),
                _windowListenerGUI.getDrawingArea()
                    .getBackground());
        _windowListenerGUI.redraw();
    }

    @Override
    public void componentShown(ComponentEvent e)
    {

    }

}
