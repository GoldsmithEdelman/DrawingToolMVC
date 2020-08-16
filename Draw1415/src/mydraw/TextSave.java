package mydraw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

public class TextSave implements ActionListener
{

    private DrawModel _textSaveModel;
    private DrawGUI _textSaveGUI;

    /**
     * Constructor
     * @param model
     * @param gui
     */
    public TextSave(DrawModel model, DrawGUI gui)
    {
        _textSaveModel = model;
        _textSaveGUI = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == _textSaveGUI.textSaveButton)
        {
            int returnVal = _textSaveGUI.textSaveFileChooser
                .showSaveDialog(_textSaveGUI);
            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                File file = _textSaveGUI.textSaveFileChooser.getSelectedFile();
                _textSaveModel.saveTextFile(file);
            }
            else
            {
                //nothing
            }

        }
        else if (e.getSource() == _textSaveGUI.textLoadButton)
        {
            int returnVal = _textSaveGUI.textLoadFileChooser
                .showOpenDialog(_textSaveGUI);
            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                File file = _textSaveGUI.textLoadFileChooser.getSelectedFile();
                _textSaveModel.readTextFile(file);
                _textSaveModel.loadCommandText(_textSaveGUI);

                _textSaveGUI.redraw();
            }
            else
            {

            }

        }
    }
}
