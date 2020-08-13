package mydraw;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

/** 
 * If this class is active, rectangles are drawn
 */

class RectangleDrawer extends ShapeDrawer
{
    DrawGUI gui;

    public RectangleDrawer(DrawGUI gui2)
    {
        gui = gui2;
    }

    int pressx, pressy;
    int lastx = -1, lasty = -1;

    // mouse pressed => fix first corner of rectangle
    public void mousePressed(MouseEvent e)
    {
        pressx = e.getX();
        pressy = e.getY();
    }

    // mouse released => fix second corner of rectangle
    // and draw the resulting shape
    public void mouseReleased(MouseEvent e)
    {
        Graphics g = gui.buffer.getGraphics();
        if (lastx != -1)
        {
            // first undraw a rubber rect
            g.setColor(gui.colorBackground);
            g.setXORMode(gui.color);
            doDraw(pressx, pressy, lastx, lasty, g);
            lastx = -1;
            lasty = -1;
        }
        // these commands finish the rubberband mode
        g.setPaintMode();
        g.setColor(gui.color);
        // draw the finel rectangle
        doDraw(pressx, pressy, e.getX(), e.getY(), g);
        gui.draw();
    }

    // mouse released => temporarily set second corner of rectangle
    // draw the resulting shape in "rubber-band mode"
    public void mouseDragged(MouseEvent e)
    {
        Graphics g = gui.drawingArea.getGraphics();
        // these commands set the rubberband mode
        //        g.setXORMode(gui.color);
        //        g.setColor(gui.drawingArea.getBackground());
        g.setXORMode(gui.color);
        g.setColor(gui.colorBackground);
        if (lastx != -1)
        {
            // first undraw previous rubber rect
            doDraw(pressx, pressy, lastx, lasty, g);

        }
        lastx = e.getX();
        lasty = e.getY();
        // draw new rubber rect
        doDraw(pressx, pressy, lastx, lasty, g);
    }

    public void doDraw(int x0, int y0, int x1, int y1, Graphics g)
    {
        // calculate upperleft and width/height of rectangle
        int x = Math.min(x0, x1);
        int y = Math.min(y0, y1);
        int w = Math.abs(x1 - x0);
        int h = Math.abs(y1 - y0);
        // draw rectangle
        g.drawRect(x, y, w, h);
    }
}
