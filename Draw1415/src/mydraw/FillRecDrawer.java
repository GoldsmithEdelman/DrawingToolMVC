package mydraw;

import java.awt.Graphics;

/**
 * If this class is active filled rectangle are drawn. Fill color is the current drawing color. 
 */

public class FillRecDrawer extends RectangleDrawer
{
    /**
     * Constructor
     * @param itsGUI
     */
    FillRecDrawer(DrawGUI itsGUI)
    {
        super(itsGUI);

    }

    public void doDraw(int x0, int y0, int x1, int y1, Graphics g)
    {
        // calculate upperleft and width/height of rectangle
        getRDGUI().model.doDraw(x0, y0, x1, y1, g);
    }

    public void saveCommand(int x0, int y0, int x1, int y1, Graphics g)
    {
        @SuppressWarnings("unused")
        Drawable cmd = new CommandFilledRectangle(getRDGUI(), getRDGUI().color,
                getRDGUI().model, x0, y0, x1, y1);
    }
}
