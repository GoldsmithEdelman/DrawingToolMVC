package mydraw;

import java.awt.Graphics;

/**
 * If this class is active an oval is drawn
 */

class OvalDrawer extends RectangleDrawer
{
    /**
     * Constructor
     * @param itsGUI
     */
    OvalDrawer(DrawGUI itsGUI)
    {
        super(itsGUI);
    }

    public void doDraw(int x0, int y0, int x1, int y1, Graphics g)
    {
        getRDGUI().model.doDraw2(x0, y0, x1, y1, g);
    }

    public void saveCommand(int x0, int y0, int x1, int y1, Graphics g)
    {
        @SuppressWarnings("unused")
        Drawable cmd = new CommandOval(getRDGUI(), getRDGUI().color,
                getRDGUI().model, x0, y0, x1, y1);
    }
}
