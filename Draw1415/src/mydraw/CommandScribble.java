package mydraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

public class CommandScribble implements Drawable
{
    private DrawGUI _cmdScrGUI;
    private Color _cmdScrColor;
    private List<Point> _cmdPoints;
    private DrawModel _cmdScrModel;

    /**
     * Constructor
     * @param itsGUI GUI reference 
     * @param itsColor Color reference 
     * @param itsModel Model reference
     * @param itsPoints coordinates 
     */
    public CommandScribble(DrawGUI itsGUI, Color itsColor, DrawModel itsModel,
            List<Point> itsPoints)
    {
        _cmdScrGUI = itsGUI;
        _cmdPoints = itsPoints;
        _cmdScrColor = itsColor;
        _cmdScrModel = itsModel;
        _cmdScrModel.addCommand(this);
        String colorCommand = _cmdScrModel.hashkeyColor(itsColor);
        String prefixCommand = "scrLines" + "+" + colorCommand;
        String wholeCommand = "";
        for (int i = 0; i < itsPoints.size(); i++)
        {
            int xfirst = itsPoints.get(i).x;
            int yfirst = itsPoints.get(i).y;
            String str = "+" + xfirst + "+" + yfirst;
            wholeCommand = prefixCommand.concat(str);
            prefixCommand = wholeCommand;
        }
        _cmdScrModel.addStringCommands(wholeCommand);

        execute();
    }

    private void drawScribble(int xfirst, int yfirst, int xlast, int ylast)
    {
        Graphics g = _cmdScrGUI.model.getBufferGraphics();
        g.setPaintMode();
        g.setColor(_cmdScrColor);
        g.drawLine(xfirst, yfirst, xlast, ylast);
    }

    @Override
    public void execute()
    {
        for (int i = 0; i < _cmdPoints.size() - 1; i++)
        {
            int xfirst = _cmdPoints.get(i).x;
            int yfirst = _cmdPoints.get(i).y;
            int k = i + 1;
            int xlast = _cmdPoints.get(k).x;
            int ylast = _cmdPoints.get(k).y;
            drawScribble(xfirst, yfirst, xlast, ylast);
        }

    }

}
