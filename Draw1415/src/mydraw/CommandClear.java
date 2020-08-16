package mydraw;

public class CommandClear implements Drawable
{
    private DrawModel _model;
    private DrawGUI _gui;

    /**
     * Constructor
     * @param itsModel Model reference
     * @param itsGUI GUI reference
     */
    public CommandClear(DrawModel itsModel, DrawGUI itsGUI)
    {

        _model = itsModel;
        _gui = itsGUI;
        _model.addCommand(this);
        _model.addStringCommands("clear");

        execute();
    }

    @Override
    public void execute()
    {
        _model.clearBufferedImg(_gui.backgroundColorGUI);
        _gui.bufferToDrawingArea();
    }

}
