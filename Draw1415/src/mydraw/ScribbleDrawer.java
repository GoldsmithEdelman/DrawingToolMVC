package mydraw;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * If this class is active, the mouse is interpreted as a pen
 */
class ScribbleDrawer extends ShapeDrawer
{

    private DrawGUI _scrDraGUI;
    private List<Point> _scrDraPoints;
    private int _lastX, _lastY;

    /**
     * Constructor
     * @param itsGUI
     */
    ScribbleDrawer(DrawGUI itsGUI)
    {
        _scrDraGUI = itsGUI;
    }

    public void mousePressed(MouseEvent e)
    {
        _lastX = e.getX();
        _lastY = e.getY();
        _scrDraPoints = new ArrayList<Point>();
        Point punkt = new Point(_lastX, _lastY);
        _scrDraPoints.add(punkt);
    }

    public void mouseDragged(MouseEvent e)
    {
        Graphics g = _scrDraGUI.model.getBufferGraphics();
        g.setColor(_scrDraGUI.color);
        g.setPaintMode();

        g.drawLine(_lastX, _lastY, e.getX(), e.getY());
        Point punkt = new Point(e.getX(), e.getY());
        _scrDraPoints.add(punkt);

        _lastX = e.getX();
        _lastY = e.getY();
        _scrDraGUI.bufferToDrawingArea();
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        @SuppressWarnings("unused")
        CommandScribble cmd = new CommandScribble(_scrDraGUI, _scrDraGUI.color,
                _scrDraGUI.model, _scrDraPoints);
    }

}
