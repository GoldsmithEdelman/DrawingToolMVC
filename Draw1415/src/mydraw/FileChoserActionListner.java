package mydraw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;

public class FileChoserActionListner implements ActionListener
{

    private DrawModel _actionListModel;
    private DrawGUI _actionListGUI;
    @SuppressWarnings("unused")
    private Draw _actionListDraw;

    /**
     * Constructor
     * @param itsDraw
     * @param itsModel
     * @param itsGUI
     */
    public FileChoserActionListner(Draw itsDraw, DrawModel itsModel,
            DrawGUI itsGUI)
    {
        _actionListDraw = itsDraw;
        _actionListModel = itsModel;
        _actionListGUI = itsGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == _actionListGUI.save)
        {
            int returnValue = _actionListGUI.filesave
                .showSaveDialog(_actionListGUI);
            if (returnValue == JFileChooser.APPROVE_OPTION)
            {
                File file = _actionListGUI.filesave.getSelectedFile();
                try
                {
                    _actionListModel.saveImageToFile(file);
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
            else
            {
                //do nothing
            }

        }
    }

}
