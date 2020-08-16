package mydraw;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * If this class is active a straight line is drawn 
 */
public class LineDrawer extends ShapeDrawer
{
    private DrawGUI _ldGUI;
    private BufferedImage _initilaBuffer;
    private int _x, _y;

    /**
     * Constructor
     * @param itsGUI
     */
    LineDrawer(DrawGUI itsGUI)
    {
        _ldGUI = itsGUI;
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        _x = e.getX();
        _y = e.getY();
        _initilaBuffer = _ldGUI.model.getBufferedImage();
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        Graphics g = _ldGUI.model.getBufferGraphics();
        g.setColor(_ldGUI.color);
        g.setPaintMode();
        @SuppressWarnings("unused")
        Drawable cmd = new CommandLine(_ldGUI, _ldGUI.color, _ldGUI.model,
                e.getX(), e.getY(), _x, _y);
        _ldGUI.bufferToDrawingArea();

    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        BufferedImage copy = new BufferedImage(_initilaBuffer.getWidth(),
                _initilaBuffer.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = copy.getGraphics();
        g.drawImage(_initilaBuffer, 0, 0, null);
        g.setColor(_ldGUI.color);
        g.setPaintMode();
        g.drawLine(_x, _y, e.getX(), e.getY());
        _ldGUI.specBufferToDrawingArea(copy);
    }

}
