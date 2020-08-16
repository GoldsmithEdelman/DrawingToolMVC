package mydraw;

import java.awt.Color;
import java.awt.Graphics;

public class CommandFilledRectangle implements Drawable
{
    private DrawGUI _cmdFRGUI;
    private Color _cmdFRColo;
    private DrawModel _cmdFRModel;
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
    public CommandFilledRectangle(DrawGUI itsGUI, Color itsColor,
            DrawModel itsModel, int x1, int y1, int x0, int y0)
    {

        _cmdFRGUI = itsGUI;
        _x0 = x0;
        _x1 = x1;
        _y0 = y0;
        _y1 = y1;
        _cmdFRColo = itsColor;
        _cmdFRModel = itsModel;
        _cmdFRModel.addCommand(this);
        String colorToCmd = _cmdFRModel.hashkeyColor(itsColor);
        _cmdFRModel.addStringCommands("rectangleFill" + "+" + colorToCmd + "+"
                + x1 + "+" + y1 + "+" + x0 + "+" + y0);

        execute();
    }

    @Override
    public void execute()
    {
        Graphics g = _cmdFRGUI.model.getBufferGraphics();
        g.setPaintMode();
        g.setColor(_cmdFRColo);
        int x = Math.min(_x0, _x1);
        int y = Math.min(_y0, _y1);
        int w = Math.abs(_x1 - _x0);
        int h = Math.abs(_y1 - _y0);
        g.fillRect(x, y, w, h);
    }
}
