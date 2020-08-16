package mydraw;

import java.awt.Color;

public class CommandBackgroundColor implements Drawable
{
    private DrawGUI _cmdBackColorGUI;
    private Color _cmdBackColorColor;
    private DrawModel _cmdBackColoModel;

    /**
     * Constructor
     * @param itsGUI GUI reference
     * @param itsColor Color reference
     * @param itsModel Model reference
     */
    public CommandBackgroundColor(DrawGUI itsGUI, Color itsColor,
            DrawModel itsModel)
    {
        _cmdBackColorGUI = itsGUI;
        _cmdBackColorColor = itsColor;
        _cmdBackColoModel = itsModel;
        _cmdBackColoModel.addCommand(this);
        String colorCommands = _cmdBackColoModel
            .hashkeyColor(_cmdBackColorColor);
        _cmdBackColoModel.addStringCommands("bacCol" + "+" + colorCommands);
        _cmdBackColorGUI.selectColorBackground(_cmdBackColorColor);
        execute();

    }

    @Override
    public void execute()
    {
        _cmdBackColoModel.setModelBackgroundColor(_cmdBackColorColor);
        _cmdBackColoModel.setBackgroundBufferCol(_cmdBackColorColor);
        _cmdBackColorGUI.backgroundColorGUI = _cmdBackColorColor;

    }
}
