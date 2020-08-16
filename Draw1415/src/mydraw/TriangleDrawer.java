package mydraw;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class TriangleDrawer extends ShapeDrawer
{
    private int _x1, _y1;
    private int _x2, _y2;
    private int _z;
    public BufferedImage _initialBuffer;
    public DrawGUI trirDrawGUI;

    /**
     * Constructor
     * @param itsGUI
     */
    public TriangleDrawer(DrawGUI itsGUI)
    {
        trirDrawGUI = itsGUI;
        _z = 0;
    }

    @SuppressWarnings("unused")
    public void mousePressed(MouseEvent e)
    {
        if (_z == 0)
        {
            _x1 = e.getX();
            _y1 = e.getY();
            _initialBuffer = trirDrawGUI.model.getBufferedImage();
            _z++;
        }
        else if (_z == 1)
        {
            _x2 = e.getX();
            _y2 = e.getY();
            _z++;
        }
        else
        {
            _z = 0;
            Graphics g = trirDrawGUI.model.getBufferGraphics();
            g.setColor(trirDrawGUI.color);
            g.setPaintMode();
            Drawable cmd = new CommandTriangle(trirDrawGUI, trirDrawGUI.color,
                    trirDrawGUI.model, _x1, _y1, _x2, _y2, e.getX(), e.getY());

            trirDrawGUI.bufferToDrawingArea();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        BufferedImage copy = new BufferedImage(_initialBuffer.getWidth(),
                _initialBuffer.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = copy.getGraphics();
        g.drawImage(_initialBuffer, 0, 0, null);
        g.setColor(trirDrawGUI.color);
        g.setPaintMode();

        if (_z == 1)
        {
            g.drawLine(_x1, _y1, e.getX(), e.getY());
        }
        else if (_z == 2)
        {
            g.drawLine(_x1, _y1, e.getX(), e.getY());
            g.drawLine(_x1, _y1, _x2, _y2);
            g.drawLine(_x2, _y2, e.getX(), e.getY());
        }
        trirDrawGUI.specBufferToDrawingArea(copy);
    }

}
