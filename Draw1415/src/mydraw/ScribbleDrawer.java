package mydraw;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

/**
 * If this class is active, the mouse is interpreted as a pen
 */

class ScribbleDrawer extends ShapeDrawer
{
    int lastx, lasty;
    DrawGUI gui;

    public ScribbleDrawer(DrawGUI gui2)
    {
        gui = gui2;
    }

    public void mousePressed(MouseEvent e)
    {
        lastx = e.getX();
        lasty = e.getY();
    }

    public void mouseDragged(MouseEvent e)
    {
        Graphics g = gui.buffer.getGraphics();
        int x = e.getX(), y = e.getY();
        g.setColor(gui.color);
        g.setPaintMode();
        g.drawLine(lastx, lasty, x, y);
        lastx = x;
        lasty = y;
        gui.draw();
    }
}
