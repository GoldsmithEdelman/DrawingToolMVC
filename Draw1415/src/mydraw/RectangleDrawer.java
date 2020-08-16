package mydraw;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * If this class is active, rectangles are drawn
 */
class RectangleDrawer extends ShapeDrawer
{
    private int _pressX, _pressY;
    private DrawGUI _rdGUI;
    public BufferedImage _initialBuffer;

    /**
     * Constructor
     * @param itsGUI
     */
    RectangleDrawer(DrawGUI itsGUI)
    {
        _rdGUI = itsGUI;
    }

    // mouse pressed => fix first corner of rectangle
    public void mousePressed(MouseEvent e)
    {
        _pressX = e.getX();
        _pressY = e.getY();
        _initialBuffer = _rdGUI.model.getBufferedImage();
    }

    // mouse released => fix second corner of rectangle
    // and draw the resulting shape
    public void mouseReleased(MouseEvent e)
    {
        Graphics g = _rdGUI.model.getBufferGraphics();
        g.setColor(_rdGUI.color);
        g.setPaintMode();
        saveCommand(_pressX, _pressY, e.getX(), e.getY(), g);

        _rdGUI.bufferToDrawingArea();
    }

    public void mouseDragged(MouseEvent e)
    {
        BufferedImage copy = new BufferedImage(_initialBuffer.getWidth(),
                _initialBuffer.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = copy.getGraphics();
        g.drawImage(_initialBuffer, 0, 0, null);
        g.setColor(_rdGUI.color);
        g.setPaintMode();
        doDraw(_pressX, _pressY, e.getX(), e.getY(), g);
        _rdGUI.specBufferToDrawingArea(copy);

    }

    public void doDraw(int x0, int y0, int x1, int y1, Graphics g)
    {
        // calculate upperleft and width/height of rectangle
        _rdGUI.model.doDraw(x0, y0, x1, y1, g);
    }

    public void saveCommand(int x0, int y0, int x1, int y1, Graphics g)
    {
        @SuppressWarnings("unused")
        Drawable cmd = new CommandRectangle(_rdGUI, _rdGUI.color, _rdGUI.model,
                x0, y0, x1, y1);
    }

    public DrawGUI getRDGUI()
    {
        return _rdGUI;
    }
}
