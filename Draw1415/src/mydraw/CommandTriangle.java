package mydraw;

import java.awt.Color;
import java.awt.Graphics;

public class CommandTriangle implements Drawable
{
    private DrawGUI _cmdTriGUI;
    private DrawModel _cmdTriModel;
    private Color _cmdTriColor;
    private int _x1, _x0, _y1, _y0, _x2, _y2;

    /**
     * Constructor
     * @param itsGUI GUI reference 
     * @param itsColor Color reference 
     * @param itsModel Model reference 
     * @param x1 coordinate
     * @param y1 coordinate
     * @param x0 coordinate
     * @param y0 coordinate
     * @param x2 coordinate
     * @param y2 coordinate
     */
    public CommandTriangle(DrawGUI itsGUI, Color itsColor, DrawModel itsModel,
            int x1, int y1, int x0, int y0, int x2, int y2)
    {

        _cmdTriGUI = itsGUI;
        _x1 = x1;
        _x0 = x0;
        _y1 = y1;
        _y0 = y0;
        _x2 = x2;
        _y2 = y2;
        _cmdTriColor = itsColor;
        _cmdTriModel = itsModel;
        _cmdTriModel.addCommand(this);
        String colorToCommand = _cmdTriModel.hashkeyColor(_cmdTriColor);
        _cmdTriModel.addStringCommands("triangle" + "+" + colorToCommand + "+"
                + x1 + "+" + y1 + "+" + x0 + "+" + y0 + "+" + x2 + "+" + y2);

        execute();
    }

    @Override
    public void execute()
    {
        Graphics g = _cmdTriGUI.model.getBufferGraphics();
        g.setPaintMode();
        g.setColor(_cmdTriColor);
        g.drawLine(_x1, _y1, _x0, _y0);
        g.drawLine(_x1, _y1, _x2, _y2);
        g.drawLine(_x2, _y2, _x0, _y0);
    }
}
