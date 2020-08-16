package mydraw;

import java.awt.Color;
import java.awt.Graphics;

public class CommandLine implements Drawable
{
    private DrawGUI _cmdLineGUI;
    private Color _cmdLineColor;
    private DrawModel _cmdLineModel;
    private int _x0, _x1, _y0, _y1;

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
    public CommandLine(DrawGUI itsGUI, Color itsColor, DrawModel itsModel,
            int x1, int y1, int x0, int y0)
    {

        _cmdLineGUI = itsGUI;
        _x1 = x1;
        _x0 = x0;
        _y1 = y1;
        _y0 = y0;
        _cmdLineColor = itsColor;
        _cmdLineModel = itsModel;
        _cmdLineModel.addCommand(this);
        String colorCommand = _cmdLineModel.hashkeyColor(itsColor);
        _cmdLineModel.addStringCommands("line" + "+" + colorCommand + "+" + x1
                + "+" + y1 + "+" + x0 + "+" + y0);
        execute();
    }

    @Override
    public void execute()
    {
        Graphics g = _cmdLineGUI.model.getBufferGraphics();
        g.setPaintMode();
        g.setColor(_cmdLineColor);
        g.drawLine(_x1, _y1, _x0, _y0);

    }
}
