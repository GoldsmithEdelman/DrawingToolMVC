package mydraw;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Abstract class for shape drawer
 */

abstract class ShapeDrawer extends MouseAdapter implements MouseMotionListener
{
    public void mouseMoved(MouseEvent e)
    {
        /* ignore */ }
}
