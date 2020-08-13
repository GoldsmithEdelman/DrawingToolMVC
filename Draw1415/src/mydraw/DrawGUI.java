package mydraw;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * 
 * This class implements the GUI for our application
 * 
 */

@SuppressWarnings("serial")
public class DrawGUI extends JFrame
{
    private Draw app; // A reference to the application, to send commands to.
    private DrawGUI gui = this;
    public Color color;

    //Minimal size of the window
    public int MIN_WINDOW_WIDTH;
    public int MIN_WINDOW_HEIGHT;

    public BufferedImage buffer;
    public JPanel drawingArea;
    private JPanel menu;
    private JPanel menuAreaTop;
    private JPanel menuAreaLower;
    private Container containerPane;
    public Color colorBackground;

    // Buttons
    private JButton clear;
    private JButton quit;
    private JButton auto;
    private JButton saveAuto;
    private JButton loadAuto;

    // selector for drawing modes
    private Choice shape_chooser;

    // selector for drawing colors
    private Choice color_chooser;

    // selector for background colors
    public Choice background_chooser;

    /**
     *
     *The GUI constructor does all the work of creating the GUI and setting
     *up event listeners.  Note the use of local and anonymous classes.
     * @param application reference
     */

    public DrawGUI(Draw application)
    {
        super("Draw"); // Create the window

        MIN_WINDOW_WIDTH = 600;
        MIN_WINDOW_HEIGHT = 400;

        app = application; // Remember the application reference
        color = Color.black; // the current drawing color
        colorBackground = Color.white; // the current background color

        drawingArea = new DrawingPanel(gui); // separate drawable area
        menu = new JPanel();
        menuAreaTop = new JPanel();
        menuAreaLower = new JPanel();

        containerPane = new Container();
        color_chooser = new Choice(); // selector for drawing colors
        shape_chooser = new Choice(); // selector for drawing modes
        background_chooser = new Choice(); // selector for background colors

        //Create buttons
        clear = new JButton("Clear");
        quit = new JButton("Quit");
        auto = new JButton("Auto");
        saveAuto = new JButton("SaveAuto");
        loadAuto = new JButton("LoadAuto");

        addShapesToSelector(); // add shapes to drop down shapes

        addColorsToDrawingSelector(); // add drawing colors to selector

        addColorsToBackgroundSelector(); // add background colors to selector

        connectButtonsToApp(); // define action listener adapters that connect the  buttons to the app

        setLayout(); // set a LayoutManager, and add the choosers and buttons to the window. 

        setWindowAndPopUp(); // finally, set the size of the window, and pop it up

        initializeBuffer();

        // Handle the window close request 
        this.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                gui.doCommand("quit");
            }
        });
    }

    /**
     * This is the application method that processes commands sent by the GUI 
     * @param command to be executed
     */

    public void doCommand(String command)
    {
        if (command.equals("clear"))
        {
            // clear the GUI window
            // It would be more modular to include this functionality in the GUI
            // class itself.  But for demonstration purposes, we do it here.
            gui.initializeBuffer();
            gui.draw();
        }
        else if (command.equals("quit"))
        { // quit the application
            gui.dispose(); // close the GUI
            System.exit(0); // and exit.
        }
        else if (command.equals("auto"))
        {
            app.autoDraw();
        }
        else if (command.equals("saveAuto"))
        {
            try
            {
                app.writeImage(app.getDrawing(), "test");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else if (command.equals("loadAuto"))
        {
            try
            {
                Image img = app.readImage("test.bmp");
                int height = gui.drawingArea.getHeight();
                int width = gui.drawingArea.getWidth();
                if (img.getWidth(null) > gui.drawingArea.getWidth())
                {

                    int deltaW = Math
                        .abs(img.getWidth(null) - gui.drawingArea.getWidth());
                    app.setWidth(width + deltaW);
                }
                if (img.getHeight(null) > gui.drawingArea.getHeight())
                {
                    int deltaH = Math
                        .abs(img.getHeight(null) - gui.drawingArea.getHeight());
                    app.setHeight(height + deltaH);
                }

                BufferedImage tmp = new BufferedImage(img.getWidth(null),
                        img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
                Graphics g = tmp.getGraphics();
                g.drawImage(img, 0, 0, null);
                g.dispose();

                img = makeColorTransparent(tmp, app.savedBackgroungColor);
                Graphics t = buffer.getGraphics();
                t.drawImage(img, 0, 0, null);
                t.dispose();

                gui.draw();

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Set the size of the window, and pop it up
     */
    private void setWindowAndPopUp()
    {
        this.setSize(800, 600);
        this.setMinimumSize(new Dimension(MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT));
        this.setVisible(true);
    }

    /**
     * Set a LayoutManager, and add the choosers and buttons to the window.
     */
    private void setLayout()
    {
        containerPane.setLayout(new BorderLayout());
        menu.setLayout(new GridLayout(3, 0));
        Border blackline = BorderFactory.createLineBorder(Color.black);
        menu.setBorder(blackline);
        menuAreaTop.setLayout(new GridLayout());
        menuAreaLower.setLayout(new GridLayout(2, 3));
        menu.add(menuAreaTop);
        menu.add(menuAreaLower);
        menuAreaLower.add(new JLabel("Shape:"));
        menuAreaLower.add(new JLabel("Color:"));
        menuAreaLower.add(new JLabel("Background:"));
        menuAreaLower.add(shape_chooser);
        menuAreaLower.add(color_chooser);
        menuAreaLower.add(background_chooser);
        menuAreaTop.add(clear);
        menuAreaTop.add(quit);
        menuAreaTop.add(auto);
        menuAreaTop.add(saveAuto);
        menuAreaTop.add(loadAuto);
        containerPane.add(menu, BorderLayout.NORTH);
        containerPane.add(drawingArea, BorderLayout.CENTER);
        gui.add(containerPane);
    }

    /**
     * Add shapes to drop down shapes
     */

    private void addShapesToSelector()
    {
        shape_chooser.add("Scribble");
        shape_chooser.add("Rectangle");
        shape_chooser.add("Oval");
    }

    /**
     * Define action listener adapters that connect the  buttons to the app
     */

    private void connectButtonsToApp()
    {
        clear.addActionListener(new DrawActionListener("clear", this));
        quit.addActionListener(new DrawActionListener("quit", this));
        auto.addActionListener(new DrawActionListener("auto", this));
        saveAuto.addActionListener(new DrawActionListener("saveAuto", this));
        loadAuto.addActionListener(new DrawActionListener("loadAuto", this));
        shape_chooser.addItemListener(new ShapeManager(this));
        color_chooser.addItemListener(new ColorItemListener(this));
        background_chooser.addItemListener(new BackgroundColorListener(this));
    }

    /**
     * Add drawing colors to selector
     */

    private void addColorsToDrawingSelector()
    {
        color_chooser.add("Black");
        color_chooser.add("White");
        color_chooser.add("Green");
        color_chooser.add("Red");
        color_chooser.add("Blue");
    }

    /**
     * Add background colors to selector
     */

    private void addColorsToBackgroundSelector()
    {
        background_chooser.add("White");
        background_chooser.add("Black");
        background_chooser.add("Green");
        background_chooser.add("Red");
        background_chooser.add("Blue");
    }

    /**
     * Draw buffer onto a drawing area 
     */
    public void draw()
    {
        Graphics g = gui.drawingArea.getGraphics();
        g.drawImage(gui.buffer, 0, 0, gui.colorBackground, null);
    }

    /**
     * Initialize buffer to be drawn onto
     */

    public void initializeBuffer()
    {
        buffer = new BufferedImage(gui.drawingArea.getWidth(),
                gui.drawingArea.getHeight(), BufferedImage.TYPE_INT_ARGB);
    }

    /**
     * 
     * Set a color transparent by editing its alpha channel
     * @param img image which one of colors needs to be transparent
     * @param color color to be set to be transparent
     * @return image with the chosen color set as transparent
     * @require img != null
     * @require color != null
     * @ensure imgToReturn != null
     * 
     */
    public Image makeColorTransparent(BufferedImage img, Color color)
    {
        Image imgToReturn = null;
        ImageFilter filter = new RGBImageFilter()
        {
            public int markerRGB = color.getRGB();

            public int filterRGB(final int x, final int y, final int rgb)
            {
                if ((rgb | 0xFF000000) == markerRGB)
                {
                    // Mark the alpha bits as zero - transparent
                    return 0x00FFFFFF & rgb;
                }
                else
                {
                    // nothing to do
                    return rgb;
                }
            }
        };

        ImageProducer ip = new FilteredImageSource(img.getSource(), filter);
        imgToReturn = Toolkit.getDefaultToolkit()
            .createImage(ip);
        return imgToReturn;
    }

    /**
     * Convert Image to BufferedImage
     * @param img image to be converted to buffer
     * @return buffered image
     */

    public BufferedImage convertToBuffered(Image img)
    {
        BufferedImage tmp = new BufferedImage(img.getWidth(null),
                img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics g = tmp.getGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        return tmp;
    }

}
