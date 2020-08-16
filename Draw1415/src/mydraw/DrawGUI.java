package mydraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class DrawGUI extends JFrame
{
    public Color color;
    public Color backgroundColorGUI;
    public DrawModel model;

    private DrawGUI _gui = this;
    private Draw _app; // A reference to the application, to send commands to.
    private BackgroundManager _backgroundManager;

    //Swing objects
    private JPanel _drawingArea;
    private JPanel _topMenu;
    private JPanel _topMenuTop;
    private JPanel _topMenuBottom;

    //Drop-down Selectors
    private JComboBox<String> _shape_chooser;
    private JComboBox<String> _color_chooser;
    private JComboBox<String> _background_chooser;

    //Buttons
    private JButton _clear;
    private JButton _quit;
    private JButton _draw;
    private JButton _undo;
    private JButton _redo;
    private Container _content;
    public JButton save;
    public JFileChooser filesave;
    public JFileChooser textSaveFileChooser;
    public JFileChooser textLoadFileChooser;
    public JButton textSaveButton;
    public JButton textLoadButton;

    /**
     * The GUI constructor does all the work of creating the GUI and setting up
     * event listeners. Note the use of local and anonymous classes.
     * @param itsApplication reference
     * @param itsModel reference
     */

    @SuppressWarnings("unused")
    public DrawGUI(Draw itsApplication, DrawModel itsModel)
    {
        _app = itsApplication; // Remember the application reference
        color = Color.black; // the current drawing color
        model = itsModel;

        // Create Swing Objects
        setSwingObjects();

        // Create Layout
        setLayout();

        //add Listeners to Objects
        addListeners();

        // Finally, set the size of the window, and pop it up
        setWindowAndPop();

        // Initialize the buffered image with dimensions of the drawing area
        model.initilizeBufferedImage(this._drawingArea.getWidth(),
                this._drawingArea.getHeight());
        backgroundColorGUI = _drawingArea.getBackground();

        CommandBackgroundColor cmd = new CommandBackgroundColor(this,
                _drawingArea.getBackground(), model);
        this.addComponentListener(new WindowListner(this));

    }

    private void setWindowAndPop()
    {
        this.setTitle("Draw Aufgabe 1.4-1.5");
        this.setSize(700, 400);
        this.setLocationRelativeTo(null);
        this.setBackground(Color.white);
        this.setMinimumSize(new Dimension(700, 300));
        this.setVisible(true);

    }

    private void setLayout()
    {
        _content = this.getContentPane();

        _drawingArea = new DrawingPanel(model, this);
        _drawingArea.setBackground(Color.WHITE);
        _topMenu = new JPanel();
        _topMenu.setLayout(new GridLayout(2, 2));

        _content.add(_topMenu, BorderLayout.NORTH);
        _content.add(_drawingArea, BorderLayout.CENTER);

        _topMenuTop = new JPanel();
        _topMenuBottom = new JPanel();

        _topMenu.add(_topMenuTop);
        _topMenu.add(_topMenuBottom);

        _topMenuTop.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        _topMenuBottom.add(new JLabel("Shape:"));
        _topMenuBottom.add(_shape_chooser);
        _topMenuBottom.add(new JLabel("Color:"));
        _topMenuBottom.add(_color_chooser);
        _topMenuBottom.add(_clear);
        _topMenuBottom.add(_draw);
        _topMenuBottom.add(_quit);

        _topMenuBottom.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        _topMenuTop.add(new JLabel("BackgroundColor:"));
        _topMenuTop.add(_background_chooser);
        _topMenuTop.add(_undo);
        _topMenuTop.add(_redo);
        _topMenuTop.add(save);
        _topMenuTop.add(textSaveButton);
        _topMenuTop.add(textLoadButton);

        this.setVisible(true);
    }

    private void setSwingObjects()
    {
        // Create selector for drawing modes
        createShapeChooser();

        // Create selector for drawing colors
        createDrawingColorChooser();

        // Create selector for background colors
        createBackgroundColChooser();

        // Create file chooser for save
        createimageToSaveWindow();

        createTextToSaveWindow();

        // Create buttons
        createJButtons();
    }

    private void createBackgroundColChooser()
    {
        _background_chooser = new JComboBox<String>();
        _background_chooser.addItem("White");
        _background_chooser.addItem("Black");
        _background_chooser.addItem("Green");
        _background_chooser.addItem("Blue");
        _background_chooser.addItem("Orange");
        _background_chooser.addItem("Yellow");
    }

    private void createimageToSaveWindow()
    {
        filesave = new JFileChooser();
        filesave.setCurrentDirectory(null);
        filesave.setDialogTitle("Save image as bmp file");
        filesave.setVisible(true);
    }

    private void createTextToSaveWindow()
    {
        textSaveFileChooser = new JFileChooser();
        textSaveFileChooser.setCurrentDirectory(null);
        textSaveFileChooser.setDialogTitle("Save Data");
        textSaveFileChooser.setVisible(true);

        textLoadFileChooser = new JFileChooser();
        textLoadFileChooser.setCurrentDirectory(null);
        textLoadFileChooser.setDialogTitle("Load Data");
        textLoadFileChooser.setVisible(true);
    }

    private void createShapeChooser()
    {
        _shape_chooser = new JComboBox<String>();
        _shape_chooser.addItem("Scribble");
        _shape_chooser.addItem("Rectangle");
        _shape_chooser.addItem("Oval");
        _shape_chooser.addItem("Triangle");
        _shape_chooser.addItem("Line");
        _shape_chooser.addItem("FilledRectangle");

    }

    private void createDrawingColorChooser()
    {
        _color_chooser = new JComboBox<String>();
        _color_chooser.addItem("Black");
        _color_chooser.addItem("Green");
        _color_chooser.addItem("Red");
        _color_chooser.addItem("Blue");
        _color_chooser.addItem("Orange");
        _color_chooser.addItem("White");
        _color_chooser.addItem("Yellow");
    }

    private void createJButtons()
    {
        _clear = new JButton("Clear");
        _quit = new JButton("Quit");
        _draw = new JButton("Draw");
        _undo = new JButton("Undo");
        _redo = new JButton("Redo");
        save = new JButton("Save");
        textLoadButton = new JButton("Load cmds");
        textSaveButton = new JButton("Save cmds");
    }

    private void addListeners()
    {
        _clear.addActionListener(new DrawActionListener("clear", this));
        _quit.addActionListener(new DrawActionListener("quit", this));
        _draw.addActionListener(new DrawActionListener("draw", this));
        _undo.addActionListener(new DrawActionListener("undo", this));
        _redo.addActionListener(new DrawActionListener("redo", this));

        save.addActionListener(new FileChoserActionListner(_app, model, this));
        textSaveButton.addActionListener(new TextSave(model, this));
        textLoadButton.addActionListener(new TextSave(model, this));

        _shape_chooser.addItemListener(new ShapeManager(this));

        _color_chooser.addItemListener(new ColorItemListener(this));
        _backgroundManager = new BackgroundManager(this);
        _background_chooser.addItemListener(_backgroundManager);

        // Handle the window close request similarly
        this.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                _gui.doCommand("quit");
            }
        });
    }

    public void undo()
    {
        model.undo();
        model.drawBufferToGraphics(this._drawingArea.getGraphics());
    }

    public void redo()
    {
        model.redo();
        model.drawBufferToGraphics(this._drawingArea.getGraphics());
    }

    public void selectColorBackground(Color color)
    {
        String s = model.hashkeyColor(color);
        _background_chooser.removeItemListener(_backgroundManager);
        _background_chooser.setSelectedItem(s);
        _background_chooser.addItemListener(_backgroundManager);

    }

    /**
     * Draw buffered image onto the drawing area
     */
    public void bufferToDrawingArea()
    {
        Graphics t = this._drawingArea.getGraphics();
        model.drawBufferToGraphics(t);
    }

    /**
     * Draw input specified buffer onto drawing area
     * @param bufferToDraw buffer to be drawn onto drawing area
     */

    public void specBufferToDrawingArea(BufferedImage bufferToDraw)
    {
        Graphics g = this._drawingArea.getGraphics();
        g.drawImage(bufferToDraw, 0, 0, null);
    }

    public void redraw()
    {
        model.redraw();
        model.drawBufferToGraphics(this._drawingArea.getGraphics());
    }

    /**
     * Function previously included in the Draw class 
     * @param command command passed
     */
    @SuppressWarnings("unused")
    public void doCommand(String command)
    {
        if (command.equals("clear"))
        {
            // clear the GUI window
            CommandClear cmd = new CommandClear(model, this);
        }
        else if (command.equals("quit"))
        {
            this.dispose(); // close the GUI
            System.exit(0); // and exit.
        }
        else if (command.equals("draw"))
        {
            _app.autoDraw();
            Graphics t = this._drawingArea.getGraphics();
            t.drawImage(model.getBufferedImage(), 0, 0, null);
        }
        else if (command.equals("redraw"))
        {
            this.redraw();
        }
        else if (command.equals("undo"))
        {
            this.undo();
        }
        else if (command.equals("redo"))
        {
            this.redo();
        }
    }

    /**
     * Getter for the drawing area
     * @return reference to the drawing area
     */
    public JPanel getDrawingArea()
    {
        return this._drawingArea;
    }
}