package mydraw;

import java.awt.Color;
import java.awt.Graphics;

public class CommandOval implements Drawable
{
    private DrawGUI _cmdOvGUI;
    private DrawModel _cmdOVModel;
    private Color _cmdOVColor;
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
    public CommandOval(DrawGUI itsGUI, Color itsColor, DrawModel itsModel,
            int x1, int y1, int x0, int y0)
    {
        _cmdOvGUI = itsGUI;
        _x1 = x1;
        _x0 = x0;
        _y1 = y1;
        _y0 = y0;
        _cmdOVColor = itsColor;
        _cmdOVModel = itsModel;
        _cmdOVModel.addCommand(this);
        String colorCommand = _cmdOVModel.hashkeyColor(itsColor);
        _cmdOVModel.addStringCommands("oval" + "+" + colorCommand + "+" + x1
                + "+" + y1 + "+" + x0 + "+" + y0);
        execute();
    }

    @Override
    public void execute()
    {
        Graphics g = _cmdOvGUI.model.getBufferGraphics();
        g.setPaintMode();
        g.setColor(_cmdOVColor);
        _cmdOVModel.doDraw2(_x0, _y0, _x1, _y1, g);
    }
}
