package mydraw;

/**
 *This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
 * Copyright (c) 1997 by David Flanagan
 * This example is provided WITHOUT ANY WARRANTY either expressed or implied.
 * You may study, use, modify, and distribute it for non-commercial purposes.
 * For any commercial use, see http://www.davidflanagan.com/javaexamples 
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * Main entry class that manages the drawing area of the applications
 *
 * @author Michal Zlotnik (primary author: Copyright (c) 1997 by David Flanagan)
 * 
 */

public class Draw
{
    protected DrawGUI _window;
    private DrawModel _model;

    /**
     * Main entry point. Just create an instance of this application class
     * @param args standard input
     */

    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new Draw();
            }
        });

    }

    /**
     * Application constructor: create an instance of our GUI class
     */

    public Draw()
    {
        _model = new DrawModel();
        _window = new DrawGUI(this, _model);

    }

    /**
     * Getter for the drawing color
     * @return current drawing color
     */
    public String getFGColor()
    {
        Color color = _window.color;
        if (_model.colorString.containsKey(color))
        {
            return _model.colorString.get(color);
        }
        return "Color is not available";
    }

    /**
     * Setter of the drawing color
     * @param newColor new color
     * @throws ColorException invalid color throws exception
     */
    public void setFGColor(String newColor) throws ColorException
    {
        if (_window.model.stringColor.containsKey(newColor))
        {
            _window.color = _window.model.stringColor.get(newColor);
        }
        else
        {
            throw new ColorException(newColor);
        }
    }

    /**
     * Getter of the width of the drawing area in pixels
     * @return width 
     */
    public int getWidth()
    {
        return _window.getDrawingArea()
            .getWidth();
    }

    /**
     * Getter if the height of the drawing area in pixels
     * @return height
     */
    public int getHeight()
    {
        return _window.getDrawingArea()
            .getHeight();
    }

    /**
     * Set the width of the drawing area
     * @param newWidth new width
     */
    public void setWidth(int newWidth)
    {
        int w = _window.getWidth();
        int b = _window.getDrawingArea()
            .getWidth();
        int d = w - b;
        _window.setSize((newWidth + d), getHeight());
        _window.getDrawingArea()
            .setSize((newWidth + d), getHeight());
    }

    /**
     * Setter for the height of the drawing area
     * @param height new height
     */
    public void setHeight(int height)
    {
        int w = _window.getHeight();
        int b = _window.getDrawingArea()
            .getHeight();
        int d = w - b;
        _window.setSize(_window.getWidth(), (height + d));
        _window.getDrawingArea()
            .setSize(_window.getWidth(), (height));
    }

    /**
     * Setter for the background color
     * @param newColor new color 
     * @throws ColorException invalid color throws exception
     */
    public void setBGColor(String newColor) throws ColorException
    {
        if (_model.stringColor.containsKey(newColor))
        {
            Color color = _model.stringColor.get(newColor);
            new CommandBackgroundColor(_window, color, _model);
            _window.redraw();
        }
        else
        {
            throw new ColorException(newColor);
        }

    }

    /**
     * Getter for the drawing area background color
     * @return String
     */
    public String getBGColor()
    {
        Color color = _model.getBackgroundCol();
        if (_model.colorString.containsKey(color))
        {
            return _model.colorString.get(color);
        }
        return "Background color is not available";
    }

    /**
     * Draw a rectangle based on upper-left and lower-right corners of the rectangle
     * @param upperLeft upper left corner
     * @param lowerRight lower right corner
     */
    public void drawRectangle(Point upperLeft, Point lowerRight)
    {
        new CommandRectangle(_window, _window.color, _model, upperLeft.x,
                upperLeft.y, lowerRight.x, lowerRight.y);
    }

    /**
     * Draw ab oval based on upper-left and lower-right corners
     * @param upperLeft upper left corner
     * @param lowerRight lower right corner
    
     */
    public void drawOval(Point upperLeft, Point lowerRight)
    {
        new CommandOval(_window, _window.color, _model, upperLeft.x,
                upperLeft.y, lowerRight.x, lowerRight.y);
    }

    /**
     * Draw a polyline based on a scribble mode
     * @param scribblePoints list of points
     */
    public void drawPolyLine(java.util.List<Point> scribblePoints)
    {
        new CommandScribble(_window, _window.color, _model, scribblePoints);

    }

    /**
     * Getter for the buffer image
     * @return Image
     */
    public Image getDrawing()
    {
        return _model.getBufferedImage();
    }

    /**
     * Clear the drawing area
     */
    public void clear()
    {
        _window.doCommand("clear");
    }

    /**
     * 
     * Save image
     * 
     * @param imageToSave Image to save 
     * @param fileName Name of the file
     * @throws IOException thrown exception
     */
    public void writeImage(Image imageToSave, String fileName)
            throws IOException
    {
        File file = new File(fileName + ".png");
        ImageIO.write((RenderedImage) imageToSave, "png", file);
    }

    /**
     * Load Image
     * @param fileName Name of the file
     * @return Image Image to be loaded
     * @throws IOException thrown exception
     */
    public Image readImage(String fileName) throws IOException
    {
        File file = new File(fileName);
        return ImageIO.read(file);
    }

    /**
     * Return the buffer image of the drawing area
     * @return BufferedImage
     */
    public BufferedImage getBufferImg()
    {
        return _model.getBufferedImage();
    }

    /**
     * Undo the latest drawing action
     */
    public void undo()
    {
        _window.undo();
        _window.redraw();
    }

    /**
     * Redo the latest drawing action
     */
    public void redo()
    {
        _window.redo();
        _window.redraw();
    }

    /**
     * Save text commands to file
     * @param fileName Name of the file 
     * @throws TextIOException Thrown exception when save fails
     */
    public void writeText(String fileName) throws TextIOException
    {
        File file = new File(fileName);
        _model.saveTextFile(file);
    }

    /**
     * Load text commands from file and drop them on the drawing area
     * @param name name of the file 
     * @throws TextIOException thrown exception when the load fails
     */
    public void readText(String name) throws TextIOException
    {
        File file = new File(name);
        _model.readTextFile(file);
        _model.loadCommandText(_window);
        _window.redraw();
    }

    /**
     * Draws triangle based on its three corners
     * @param pointone coordinate
     * @param pointtwo coordinate
     * @param pointthree coordinate
     */
    public void drawTrian(Point pointone, Point pointtwo, Point pointthree)
    {
        new CommandTriangle(_window, _window.color, _model, pointone.x,
                pointone.y, pointtwo.x, pointtwo.y, pointthree.x, pointthree.y);
    }

    /**
     * Draws a straight line
     * @param upper_left first coordinate
     * @param lower_right second coordinate
     */
    public void drawLine(Point upper_left, Point lower_right)
    {
        new CommandLine(_window, _window.color, _model, upper_left.x,
                upper_left.y, lower_right.x, lower_right.y);
    }

    /**
     * Draws a filled rectangle. Fill color is the current drawing color
     * @param upper_left upper left coordinate
     * @param lower_right lower left coordinate
     */
    public void drawFillRectangle(Point upper_left, Point lower_right)
    {
        new CommandFilledRectangle(_window, _window.color, _model, upper_left.x,
                upper_left.y, lower_right.x, lower_right.y);
    }

    /**
     * Test method for the API methods
     */
    public void autoDraw()
    {
        clear();
        Color initialDrawingColor = _window.color;

        ArrayList<Point> points = new ArrayList<Point>();

        Point leftBottom = new Point(300, 300);
        Point rightBottom = new Point(400, 300);
        Point leftTop = new Point(300, 200);
        Point rightTop = new Point(400, 200);
        Point topMiddle = new Point(350, 150);

        points.add(leftBottom);
        points.add(rightBottom);
        points.add(leftTop);
        points.add(rightTop);
        points.add(leftBottom);
        points.add(leftTop);
        points.add(topMiddle);
        points.add(rightTop);
        points.add(rightBottom);

        try
        {
            setBGColor("Black");
        }
        catch (ColorException e1)
        {
            e1.printStackTrace();
        }
        try
        {
            setFGColor("White");
        }
        catch (ColorException e)
        {
            e.printStackTrace();
        }
        drawRectangle(new Point(0, 0), new Point(150, 150));

        try
        {
            setFGColor("Red");
        }
        catch (ColorException e)
        {
            e.printStackTrace();
        }
        drawRectangle(new Point(140, 140), new Point(240, 240));

        try
        {
            setFGColor("Blue");
        }
        catch (ColorException e)
        {
            e.printStackTrace();
        }
        drawRectangle(new Point(10, 10), new Point(200, 200));

        try
        {
            setFGColor("Green");
        }
        catch (ColorException e)
        {
            e.printStackTrace();
        }
        drawOval(new Point(140, 140), new Point(240, 240));

        try
        {
            setFGColor("Blue");
        }
        catch (ColorException e)
        {
            e.printStackTrace();
        }
        drawRectangle(new Point(100, 100), new Point(150, 150));
        drawOval(new Point(100, 100), new Point(150, 150));
        drawPolyLine(points);
        points.clear();

        drawFillRectangle(new Point(120, 150), new Point(100, 130));
        drawLine(new Point(0, 0), new Point(200, 250));
        drawTrian(new Point(0, 0), new Point(200, 150), new Point(200, 250));
        _window.color = initialDrawingColor;
    }

}
