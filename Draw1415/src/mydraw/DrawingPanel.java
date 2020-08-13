package mydraw;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawingPanel extends JPanel
{
    DrawGUI gui;
    BufferedImage tmp;

    public DrawingPanel(DrawGUI gui2)
    {
        gui = gui2;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (gui.getWidth() > gui.buffer.getWidth()
                || gui.getHeight() > gui.buffer.getHeight())
        {
            tmp = new BufferedImage(gui.drawingArea.getWidth(),
                    gui.drawingArea.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics t = tmp.getGraphics();
            t.drawImage(gui.buffer, 0, 0, null);
            g.drawImage(tmp, 0, 0, gui.colorBackground, null);
            gui.buffer = tmp;
        }
        else
        {
            g.drawImage(gui.buffer, 0, 0, gui.colorBackground, null);
        }

    }
}
