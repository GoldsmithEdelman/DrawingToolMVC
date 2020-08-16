package mydraw;

import java.awt.Color;
import java.awt.Graphics;

public class CommandRectangle implements Drawable
{
    private DrawGUI _cmdRecGUI;
    private DrawModel _cmdRecModel;
    private Color _cmdRecColor;
    private int _x1, _x0, _y1, _y0;

    /**
     * Constructor
     * @param itsGUI GUI reference 
     * @param itsColor Color reference 
     * @param itsModel Model reference 
     * @param x1 coordinate
     * @param y1 coordinate
     * @param x0 coordinate
     * @param y0 coordinate
     */
    public CommandRectangle(DrawGUI itsGUI, Color itsColor, DrawModel itsModel,
            int x1, int y1, int x0, int y0)
    {
        _cmdRecGUI = itsGUI;
        _x1 = x1;
        _x0 = x0;
        _y1 = y1;
        _y0 = y0;
        _cmdRecColor = itsColor;
        _cmdRecModel = itsModel;
        _cmdRecModel.addCommand(this);
        String commandColor = _cmdRecModel.hashkeyColor(itsColor);
        _cmdRecModel.addStringCommands("rectangle" + "+" + commandColor + "+"
                + x1 + "+" + y1 + "+" + x0 + "+" + y0);
        execute();
    }

    @Override
    public void execute()
    {
        Graphics g = _cmdRecGUI.model.getBufferGraphics();
        g.setPaintMode();
        g.setColor(_cmdRecColor);
        _cmdRecModel.doDraw(_x0, _y0, _x1, _y1, g);
    }
}
