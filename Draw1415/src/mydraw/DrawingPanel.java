package mydraw;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawingPanel extends JPanel
{
    private DrawModel _drawingPanelModel;

    /**
     * Constructor
     * @param itsModel Model reference
     * @param itsGUI GUI reference
     */
    public DrawingPanel(DrawModel itsModel, DrawGUI itsGUI)
    {
        _drawingPanelModel = itsModel;
    }

    @Override
    protected void paintComponent(final Graphics g)
    {
        super.paintComponent(g);
        BufferedImage bild = _drawingPanelModel.getBufferedImage();
        g.drawImage(bild, 0, 0, null);

    }
}
