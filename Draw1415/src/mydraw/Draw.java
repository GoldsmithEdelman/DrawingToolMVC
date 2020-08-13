package mydraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;

/** 
 * 
 * The application class.  Processes high-level commands sent by GUI 
 * 
 * @author (co-author) Michael Zlotnik
 * 
 * PTP 2020
 * 
 * */
public class Draw
{
    protected static DrawGUI window;

    private Color initialDrawingColor;

    // Drawing area coordinates (DAC)
    private int daWidth, daHeight, bufferWidth, bufferHeight;
    private Point daUpperLeft, daUpperMid, daMidRight, daBottomRight,
            daBottomMid, daMidLeft;

    // Buffer coordinates (i.d.R. abs((int) DAC - 1))
    private Point bufferUpperLeft, bufferUpperMid, bufferMidRight,
            bufferBottomRight, bufferBottomMid, bufferMidLeft;

    private List<Point> points;
    public Color savedBackgroungColor;

    /**
     * Main entry point. Just create an instance of this application class
     * @param args arguments
     */

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new Draw();
            }
        });
    }

    /** 
     * Application constructor:  create an instance of our GUI class 
     */

    public Draw()
    {
        window = new DrawGUI(this);
        savedBackgroungColor = window.colorBackground;
    }

    /**
     * API method: get height of the drawing area
     * @return height of the drawing area
     * @ensure height != null
     */

    public int getHeight()
    {
        int height = window.drawingArea.getHeight();
        return height;
    }

    /**
     * API method: set height of the drawing area
     * 
     * @param height of the drawing area
     * @require windowMinHeight(height)
     * 
     */

    public void setHeight(int height)
    {
        assert windowMinHeight(
                height) : "Vorbedingung verletzt: windowMinHeight(height)";

        int delta = Math
            .abs(window.getHeight() - window.drawingArea.getHeight());
        window.setSize(window.getWidth(), height + delta);
        window.drawingArea.setSize(window.drawingArea.getWidth(), height);
        resizeTheBuffer();
    }

    /**
     * API method: get width of the drawing area
     * @return width of the drawing area
     * @ensure width != null
     */

    public int getWidth()
    {
        int width = window.drawingArea.getWidth();
        return width;
    }

    /**
     * API method: set width of the drawing area
     * 
     * @param width of the drawing area 
     * @require windowMinWidth(width)
     * @ensure width == getWidth()
     * @ensure windowMinWidth(width)
     */

    public void setWidth(int width)
    {
        assert windowMinWidth(
                width) : "Vorbedingung verletzt: windowMinWidth(width)";

        int delta = Math.abs(window.getWidth() - window.drawingArea.getWidth());
        window.setSize(width + delta, window.getHeight());
        window.drawingArea.setSize(width, window.drawingArea.getHeight());
        resizeTheBuffer();
    }

    /**
     * API method: set drawing color
     * @param new_color new drawing color
     * @throws ColorException input color not available
     */

    public void setFGColor(String new_color) throws ColorException
    {
        if (new_color.equalsIgnoreCase("Black"))
        {
            window.color = Color.black;
        }
        else if (new_color.equalsIgnoreCase("Green"))
        {
            window.color = Color.green;
        }
        else if (new_color.equalsIgnoreCase("Red"))
        {
            window.color = Color.red;
        }
        else if (new_color.equalsIgnoreCase("Blue"))
        {
            window.color = Color.blue;
        }
        else if (new_color.equalsIgnoreCase("White"))
        {
            window.color = Color.white;
        }
        else
        {
            throw new ColorException(new_color);
        }
    }

    /**
     * API method: get current drawing color
     * @return current drawing color
     * @ensure str != null
     */
    public String getFGColor()
    {
        String str = null;
        if (window.color.equals(Color.black))
        {
            str = "Black";
        }
        else if (window.color.equals(Color.white))
        {
            str = "White";
        }
        else if (window.color.equals(Color.green))
        {
            str = "Green";
        }
        else if (window.color.equals(Color.red))
        {
            str = "Red";
        }
        else if (window.color.equals(Color.blue))
        {
            str = "Blue";
        }
        return str;
    }

    /**
     * API method: set background color
     * @param new_color new color for the background color of the drawing area
     * @throws ColorException input color not available
     */

    public void setBGColor(String new_color) throws ColorException
    {
        if (new_color.equalsIgnoreCase("Black"))
        {
            window.colorBackground = Color.black;
        }
        else if (new_color.equalsIgnoreCase("Green"))
        {
            window.colorBackground = Color.green;
        }
        else if (new_color.equalsIgnoreCase("Red"))
        {
            window.colorBackground = Color.red;
        }
        else if (new_color.equalsIgnoreCase("Blue"))
        {
            window.colorBackground = Color.blue;
        }
        else if (new_color.equalsIgnoreCase("White"))
        {
            window.colorBackground = Color.white;
        }
        else
        {
            throw new ColorException(new_color);
        }
        window.draw();
    }

    /**
     * API method: get background color of the drawing area
     * @return String color of the background drawing area
     * @ensure str != null
     */

    public String getBGColor()
    {
        String str = null;
        if (window.colorBackground.equals(Color.black))
        {
            str = "Black";
        }
        else if (window.colorBackground.equals(Color.green))
        {
            str = "Green";
        }
        else if (window.colorBackground.equals(Color.red))
        {
            str = "Red";
        }
        else if (window.colorBackground.equals(Color.blue))
        {
            str = "Blue";
        }
        else if (window.colorBackground.equals(Color.white))
        {
            str = "White";
        }
        return str;
    }

    /**
     * API method: get drawing 
     * @return drawing area saved in the buffer
     */
    public Image getDrawing()
    {
        BufferedImage theOneToSave = new BufferedImage(window.buffer.getWidth(),
                window.buffer.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = theOneToSave.getGraphics();
        g.drawImage(window.buffer, 0, 0, window.colorBackground, null);
        return theOneToSave;
    }

    /**
     * API method: write image to file 
     * @param img to be written to file
     * @param filename name of the file to be written to file 
     * @throws IOException issue when writing to file
     * @require filename != null
     * @require img != null
     */

    public void writeImage(Image img, String filename) throws IOException
    {
        assert filename != null : "Vorbedingung verletzt: filename ist null";
        assert img != null : "Vorbedingung verletzt: img ist null";

        MyBMPFile.write(filename + ".bmp", getDrawing());
        savedBackgroungColor = window.colorBackground;
    }

    /**
     * API method: reads image saved by write image
     * @param filename name of the file to be read
     * @return image to be loaded on the drawing area
     * @throws IOException file not found or error reading
     * @require filename != null
     */

    public Image readImage(String filename) throws IOException
    {
        assert filename != null : "Vorbedingung verletzt: filename ist null";

        Image theonetoload = MyBMPFile.read(filename);
        return theonetoload;
    }

    /** 
     * API method: clear the drawing area and the image save to buffer
     * */

    public void clear()
    {
        window.initializeBuffer();
        window.draw();
    }

    /**
     * 
     * Tests methods that draw
     * 
     */

    public void autoDraw()
    {
        setWidth(600);
        setHeight(600);

        initialDrawingColor = window.color;
        intializePointsAuto();
        points = new ArrayList<Point>();

        try
        {
            setBGColor("Green");
        }
        catch (ColorException e1)
        {
            e1.printStackTrace();
        }

        try
        {
            setFGColor("BlUe");
        }
        catch (ColorException e)
        {
            e.printStackTrace();
        }

        drawRectangle(daUpperLeft, daBottomRight);

        points.add(daMidLeft);
        points.add(daMidRight);
        points.add(daUpperMid);
        points.add(daBottomMid);
        drawPolyLine(points);
        points.clear();

        try
        {
            setFGColor("red");
        }
        catch (ColorException e)
        {
            e.printStackTrace();
        }

        points.add(bufferMidLeft);
        points.add(bufferMidRight);
        points.add(bufferUpperMid);
        points.add(bufferBottomMid);
        drawPolyLine(points);
        points.clear();

        try
        {
            setFGColor("black");
        }
        catch (ColorException e)
        {
            e.printStackTrace();
        }

        points.add(new Point(50, 50));
        points.add(new Point(74, 87));
        points.add(new Point(50, 50));
        points.add(new Point(10, 400));
        points.add(new Point(354, 412));
        points.add(new Point(250, 45));
        drawPolyLine(points);
        points.clear();

        drawRectangle(bufferUpperLeft, bufferBottomRight);

        try
        {
            setFGColor("whitE");
        }
        catch (ColorException e)
        {
            e.printStackTrace();
        }
        drawOval(new Point(100, 100), new Point(200, 200));
        drawOval(new Point(110, 110), new Point(200, 200));

        try
        {
            writeImage(getDrawing(), "test31");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        window.color = initialDrawingColor;
        window.background_chooser.select(getBGColor());
    }

    /**
     * 
     * API: pain a rectangle 
     * 
     * @param upper_left corner of the rectangle
     * @param lower_right corner of the rectangle
     * @require upper_left != null 
     * @require lower_right != null
     * 
     */

    public void drawRectangle(Point upper_left, Point lower_right)
    {
        assert upper_left != null : "Vorbedingung verletzt: upper_left ist null";
        assert lower_right != null : "Vorbedingung verletzt: lower_right ist null";

        Graphics g = window.buffer.getGraphics();
        g.setColor(window.color);
        g.setPaintMode();
        g.drawRect(upper_left.x, upper_left.y,
                Math.abs(upper_left.x - lower_right.x),
                Math.abs(upper_left.y - lower_right.y));
        window.draw();
    }

    /**
     * 
     * API: paint an oval
     * 
     * @param upper_left point of the oval
     * @param lower_right point of the oval
     * @require upper_left != null
     * @require lower_right != null
     *   
     */

    public void drawOval(Point upper_left, Point lower_right)
    {
        assert upper_left != null : "Vorbedingung verletzt: upper_left ist null";
        assert lower_right != null : "Vorbedingung verletzt: lower_right ist null";

        Graphics g = window.buffer.getGraphics();
        g.setColor(window.color);
        g.setPaintMode();
        g.drawOval(upper_left.x, upper_left.y,
                Math.abs(upper_left.x - lower_right.x),
                Math.abs(upper_left.y - lower_right.y));
        window.draw();
    }

    /**
     * 
     * API: paint a polyline/scribble
     * 
     * @param points of the polyline to be drawn
     * @require points != null 
     * 
     */

    public void drawPolyLine(List<Point> points)
    {
        assert points != null : "Vorbedingung verletzt: points ist null";

        int x1, x2, y1, y2;
        Graphics g = window.buffer.getGraphics();
        g.setColor(window.color);
        g.setPaintMode();
        for (int i = 0; i < points.size() - 1;)
        {
            x1 = points.get(i).x;
            y1 = points.get(i).y;
            if (i < points.size() - 1)
            {
                i++;
                x2 = points.get(i).x;
                y2 = points.get(i).y;
                g.drawLine(x1, y1, x2, y2);
            }
        }
        window.draw();
    }

    /**
     * Resizing a buffer to match current size of the drawing area
     */

    private void resizeTheBuffer()
    {
        BufferedImage tmp = new BufferedImage(window.drawingArea.getWidth(),
                window.drawingArea.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = tmp.getGraphics();
        g.drawImage(window.buffer, 0, 0, null);
        window.buffer = tmp;
    }

    /**
     * Initializers for coordinate helpers
     */

    private void intializePointsAuto()
    {
        daWidth = getWidth() - 1;
        daHeight = getHeight() - 1;
        bufferWidth = window.buffer.getWidth() - 2;
        bufferHeight = window.buffer.getHeight() - 2;

        daUpperLeft = new Point(0, 0);
        daUpperMid = new Point(daWidth / 2, 0);
        new Point(daWidth, 0);
        daMidRight = new Point(daWidth, daHeight / 2);
        daBottomRight = new Point(daWidth, daHeight);
        daBottomMid = new Point(daWidth / 2, daHeight);
        new Point(0, daHeight);
        daMidLeft = new Point(0, daHeight / 2);
        new Point(daWidth / 2, daHeight / 2);

        bufferUpperLeft = new Point(1, 1);
        bufferUpperMid = new Point((bufferWidth / 2) + 1, 1);
        new Point(bufferWidth, 1);
        bufferMidRight = new Point(bufferWidth, (bufferHeight / 2));
        bufferBottomRight = new Point(bufferWidth, bufferHeight);
        bufferBottomMid = new Point((bufferWidth / 2) + 1, bufferHeight);
        new Point(1, bufferHeight);
        bufferMidLeft = new Point(1, (bufferHeight / 2));
        new Point((bufferWidth / 2), (bufferHeight / 2));
    }

    /**
     * 
     * Check if the width is larger than the required minimum width  
     * 
     * @param width
     * @return true, when the width is equal or larger than the minimum window width, false otherwise
     * 
     */

    private boolean windowMinWidth(int width)
    {
        boolean result;
        if (width >= window.MIN_WINDOW_WIDTH)
        {
            result = true;
        }
        else
        {
            result = false;
        }
        return result;
    }

    /**
     * 
     * Check if the height is larger than the required minimum height
     * 
     * @param height
     * @return true, when the height is equal or larger than the minimum window height, false otherwise
     * 
     */

    private boolean windowMinHeight(int height)
    {
        boolean result;
        if (height >= window.MIN_WINDOW_HEIGHT)
        {
            result = true;
        }
        else
        {
            result = false;
        }
        return result;
    }

}
