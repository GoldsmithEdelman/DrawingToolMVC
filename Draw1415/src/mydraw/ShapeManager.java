package mydraw;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * This class determines how mouse events are to be interpreted depending on the shape more
 * currently set 
 */

public class ShapeManager implements ItemListener
{
    private DrawGUI gui;
    ScribbleDrawer scribbleDrawer;
    ShapeDrawer currentDrawer;
    RectangleDrawer rectDrawer;
    OvalDrawer ovalDrawer;

    public ShapeManager(DrawGUI itsGui)
    {

        gui = itsGui;
        scribbleDrawer = new ScribbleDrawer(gui);
        rectDrawer = new RectangleDrawer(gui);
        ovalDrawer = new OvalDrawer(gui);

        // default: scribble mode
        currentDrawer = scribbleDrawer;
        // activate scribble drawer
        gui.drawingArea.addMouseListener(currentDrawer);
        gui.drawingArea.addMouseMotionListener(currentDrawer);
    }

    // reset the shape drawer
    public void setCurrentDrawer(ShapeDrawer l)
    {
        if (currentDrawer == l) return;

        // deactivate previous drawer
        gui.drawingArea.removeMouseListener(currentDrawer);
        gui.drawingArea.removeMouseMotionListener(currentDrawer);
        // activate new drawer
        currentDrawer = l;
        gui.drawingArea.addMouseListener(currentDrawer);
        gui.drawingArea.addMouseMotionListener(currentDrawer);
    }

    // user selected new shape => reset the shape mode
    public void itemStateChanged(ItemEvent e)
    {
        if (e.getItem()
            .equals("Scribble"))
        {
            setCurrentDrawer(scribbleDrawer);
        }
        else if (e.getItem()
            .equals("Rectangle"))
        {
            setCurrentDrawer(rectDrawer);
        }
        else if (e.getItem()
            .equals("Oval"))
        {
            setCurrentDrawer(ovalDrawer);
        }
    }
}
