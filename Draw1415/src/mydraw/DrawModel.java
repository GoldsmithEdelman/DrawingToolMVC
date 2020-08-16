package mydraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * Class based on the MVC design pattern to remove redundacies and file load and save management.
 */
public class DrawModel
{
    public Hashtable<String, Color> stringColor;
    public Hashtable<Color, String> colorString;
    public BufferedImage buffer;
    public List<Drawable> commandList;
    public List<String> textCommandList;
    public int index;
    public Color backgroundCol;

    /**
     * Constructor
     */
    public DrawModel()
    {
        index = 0;
        commandList = new ArrayList<Drawable>();
        textCommandList = new ArrayList<String>();
        hashTableColors();
    }

    /**
     * Initialize buffered image to given width and height
     * @param width width
     * @param height height
     */
    public void initilizeBufferedImage(int width, int height)
    {
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    public void doDraw(int x0, int y0, int x1, int y1, Graphics g)
    {
        // calculate upperleft and width/height of rectangle
        int x = Math.min(x0, x1);
        int y = Math.min(y0, y1);
        int w = Math.abs(x1 - x0);
        int h = Math.abs(y1 - y0);
        // draw rectangle
        g.drawRect(x, y, w, h);
    }

    public void doDraw2(int x0, int y0, int x1, int y1, Graphics g)
    {
        int x = Math.min(x0, x1);
        int y = Math.min(y0, y1);
        int w = Math.abs(x1 - x0);
        int h = Math.abs(y1 - y0);
        // draw oval instead of rectangle
        g.drawOval(x, y, w, h);
    }

    /**
     * Redraw by taking into account the background color
     */
    public void redraw()
    {
        //find the background color
        for (int i = 0; i < index; i++)
        {
            if (commandList.get(i) instanceof CommandBackgroundColor)
            {
                commandList.get(i)
                    .execute();
            }
        }
        setBackgroundBufferCol(backgroundCol);
        for (int i = 0; i < index; i++)
        {
            if (commandList.get(i) instanceof CommandBackgroundColor)
            {

            }
            else
            {
                commandList.get(i)
                    .execute();
            }
        }
    }

    /**
     * Add command object to the command list
     * @param commandToSave Command to be added
     */
    public void addCommand(Drawable commandToSave)
    {
        int size = commandList.size();
        if (size == index)
        {
            commandList.add(commandToSave);
            index++;
        }
        else
        {
            for (int i = size; i > index; i--)
            {
                commandList.remove(i - 1);
            }
            commandList.add(commandToSave);
            index++;
        }

    }

    /**
     * Undo the last action and redraw
     */
    public void undo()
    {
        if (index == 0)
        {

        }
        else
        {
            index--;
            redraw();
        }
    }

    /**
     * Redo the last action and redraw
     */
    public void redo()
    {
        if (index < commandList.size())
        {
            index++;
            redraw();
        }
    }

    /**
     * Get buffered image
     * @return buffer buffered image 
     */
    public BufferedImage getBufferedImage()
    {
        return buffer;
    }

    /**
     * Get graphics context of the buffered image
     * @return graphics context of the buffered image
     */
    public Graphics getBufferGraphics()
    {
        return buffer.getGraphics();
    }

    /**
     * Resize the buffer and paint its background with a input color
     * @param width width
     * @param height height
     * @param backgroundNewCol new background color
     */
    public void setBufferSizeColor(int width, int height,
            Color backgroundNewCol)
    {
        BufferedImage temp = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics t = temp.getGraphics();
        t.setPaintMode();
        t.setColor(backgroundNewCol);
        t.fillRect(0, 0, width, height);
        t.drawImage(buffer, 0, 0, null);
        buffer = temp;

    }

    /**
     * Clear background by painting its background 
     * @param backgroundNewCol color to be drawn
     */
    public void clearBufferedImg(Color backgroundNewCol)
    {
        Graphics g = buffer.getGraphics();
        g.setPaintMode();
        g.setColor(backgroundNewCol);
        g.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
    }

    /**
     * Draw buffer to input graphics context
     * @param g graphics context
     */
    public void drawBufferToGraphics(Graphics g)
    {
        g.drawImage(buffer, 0, 0, null);
    }

    /**
     * Add string to list of commands
     */
    public void addStringCommands(String str)
    {
        textCommandList.add(str);
    }

    /**
     * Returns string corresponding to color-type input
     * @param Color
     * @return String
     */
    public String hashkeyColor(Color inputColor)
    {
        return colorString.get(inputColor);
    }

    /**
     * Returns color-type corresponding to string-format color
     * @param colorAsString
     * @return Color
     */
    public Color hashkeyString(String colorAsString)
    {
        return stringColor.get(colorAsString);
    }

    /**
     * Save image to file as bmp
     * @param file
     * @throws IOException
     */
    public void saveImageToFile(File file) throws IOException
    {
        String f = file.getAbsolutePath() + ".bmp";
        ImageIO.write((RenderedImage) buffer, "bmp", new File(f));

    }

    /**
     * Setter for the background color
     * @param colorToSet
     */
    public void setModelBackgroundColor(Color colorToSet)
    {
        backgroundCol = colorToSet;
    }

    /**
     * Getter of the background color
     * @return Color
     */
    public Color getBackgroundCol()
    {
        return backgroundCol;
    }

    /**
     * Draw background color in the buffered image
     * @param colorToDraw
     */
    public void setBackgroundBufferCol(Color colorToDraw)
    {
        Graphics g = buffer.getGraphics();
        g.setPaintMode();
        g.setColor(colorToDraw);
        g.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
    }

    /**
     * Helper function that divides long strings into substrings. "+" here is a separator
     * @param stringToSplit
     * @return new list with separated strings
     */
    public List<String> splitString(String stringToSplit)
    {
        List<String> list = new ArrayList<String>();
        char separator = '+';
        int helperIncr = 0;
        for (int i = 0; i < stringToSplit.length(); i++)
        {
            if (stringToSplit.charAt(i) == separator)
            {
                list.add(stringToSplit.substring(helperIncr, (i)));
                helperIncr = i + 1;
            }
        }
        list.add(stringToSplit.substring(helperIncr, (stringToSplit.length())));
        return list;
    }

    /**
     * Saves text command list as a text file
     * @param file
     */
    public void saveTextFile(File file)
    {
        try
        {
            FileWriter fw = new FileWriter(file.getAbsolutePath());
            PrintWriter pw = new PrintWriter(fw);

            for (int i = 0; i < textCommandList.size(); i++)
            {
                pw.println(textCommandList.get(i));
            }
            pw.close();
        }
        catch (IOException e)
        {
            System.out.println("Save not succesfull");
            e.printStackTrace();
        }
    }

    /**
     * Load text file containing list of commands
     * @param file
     */
    public void readTextFile(File file)
    {
        try
        {
            FileReader fr = new FileReader(file.getAbsolutePath());
            BufferedReader br = new BufferedReader(fr);
            String str;

            try
            {
                textCommandList = new ArrayList<String>();
                while ((str = br.readLine()) != null)
                {
                    textCommandList.add(str);
                }
            }
            catch (IOException e)
            {
                System.out.println("Load not successful");
                e.printStackTrace();
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

    /**
     * Converts list of strings to command objects
     * @param itsGUI
     * @param str
     */
    @SuppressWarnings("unused")
    public void stringsToCommands(DrawGUI itsGUI, String str)
    {
        List<String> list = splitString(str);
        if (list.get(0)
            .equals("oval"))
        {
            int x1 = Integer.parseInt(list.get(2));
            int y1 = Integer.parseInt(list.get(3));
            int x2 = Integer.parseInt(list.get(4));
            int y2 = Integer.parseInt(list.get(5));
            Color color = hashkeyString(list.get(1));
            CommandOval cmd = new CommandOval(itsGUI, color, this, x1, y1, x2,
                    y2);
        }
        if (list.get(0)
            .equals("clear"))
        {
            CommandClear cmd = new CommandClear(this, itsGUI);
        }
        if (list.get(0)
            .equals("bacCol"))
        {
            Color color = hashkeyString(list.get(1));
            CommandBackgroundColor cmd = new CommandBackgroundColor(itsGUI,
                    color, this);
        }
        if (list.get(0)
            .equals("rectangle"))
        {
            int x1 = Integer.parseInt(list.get(2));
            int y1 = Integer.parseInt(list.get(3));
            int x2 = Integer.parseInt(list.get(4));
            int y2 = Integer.parseInt(list.get(5));
            Color color = hashkeyString(list.get(1));
            CommandRectangle cmd = new CommandRectangle(itsGUI, color, this, x1,
                    y1, x2, y2);
        }
        if (list.get(0)
            .equals("triangle"))
        {
            int x1 = Integer.parseInt(list.get(2));
            int y1 = Integer.parseInt(list.get(3));
            int x2 = Integer.parseInt(list.get(4));
            int y2 = Integer.parseInt(list.get(5));
            int x3 = Integer.parseInt(list.get(6));
            int y3 = Integer.parseInt(list.get(7));
            Color color = hashkeyString(list.get(1));
            CommandTriangle cmd = new CommandTriangle(itsGUI, color, this, x1,
                    y1, x2, y2, x3, y3);
        }
        if (list.get(0)
            .equals("rectangleFill"))
        {
            int x1 = Integer.parseInt(list.get(2));
            int y1 = Integer.parseInt(list.get(3));
            int x2 = Integer.parseInt(list.get(4));
            int y2 = Integer.parseInt(list.get(5));
            Color color = hashkeyString(list.get(1));
            CommandFilledRectangle cmd = new CommandFilledRectangle(itsGUI,
                    color, this, x1, y1, x2, y2);
        }
        if (list.get(0)
            .equals("line"))
        {
            int x1 = Integer.parseInt(list.get(2));
            int y1 = Integer.parseInt(list.get(3));
            int x2 = Integer.parseInt(list.get(4));
            int y2 = Integer.parseInt(list.get(5));
            Color color = hashkeyString(list.get(1));
            CommandLine cmd = new CommandLine(itsGUI, color, this, x1, y1, x2,
                    y2);
        }

        if (list.get(0)
            .equals("scrLines"))
        {
            Color color = hashkeyString(list.get(1));
            java.util.List<Point> punkte = new ArrayList<Point>();
            for (int i = 2; i < list.size(); i = i + 2)
            {
                int x = Integer.parseInt(list.get(i));
                int k = i + 1;
                int y = Integer.parseInt(list.get(k));
                Point punkt = new Point(x, y);
                punkte.add(punkt);
            }
            CommandScribble cmd = new CommandScribble(itsGUI, color, this,
                    punkte);
        }

    }

    public void loadCommandText(DrawGUI itsGUI)
    {
        String str = null;
        int listSize = textCommandList.size();

        List<String> copy = new ArrayList<String>();
        for (int i = 0; i < listSize; i++)
        {
            str = textCommandList.get(i);
            copy.add(str);
        }

        for (int i = 0; i < listSize; i++)
        {
            stringsToCommands(itsGUI, textCommandList.get(i));
        }

        textCommandList = copy;
    }

    private void hashTableColors()
    {
        colorString = new Hashtable<Color, String>();

        colorString.put(Color.BLACK, "Schwarz");
        colorString.put(Color.WHITE, "Weiss");
        colorString.put(Color.RED, "Rot");
        colorString.put(Color.GREEN, "Gruen");
        colorString.put(Color.BLUE, "Blau");
        colorString.put(Color.LIGHT_GRAY, "Grau");
        colorString.put(Color.YELLOW, "Gelb");
        colorString.put(Color.ORANGE, "Orange");

        stringColor = new Hashtable<String, Color>();

        stringColor.put("red", Color.RED);
        stringColor.put("Red", Color.RED);
        stringColor.put("rot", Color.RED);
        stringColor.put("Rot", Color.RED);

        stringColor.put("blue", Color.BLUE);
        stringColor.put("Blue", Color.BLUE);
        stringColor.put("blau", Color.BLUE);
        stringColor.put("Blau", Color.BLUE);

        stringColor.put("green", Color.GREEN);
        stringColor.put("Green", Color.GREEN);
        stringColor.put("gruen", Color.GREEN);
        stringColor.put("Gruen", Color.GREEN);

        stringColor.put("black", Color.BLACK);
        stringColor.put("Black", Color.BLACK);
        stringColor.put("schwarz", Color.BLACK);
        stringColor.put("Schwarz", Color.BLACK);

        stringColor.put("white", Color.WHITE);
        stringColor.put("White", Color.WHITE);
        stringColor.put("weiss", Color.WHITE);
        stringColor.put("Weiss", Color.WHITE);

        stringColor.put("Gray", Color.LIGHT_GRAY);
        stringColor.put("gray", Color.LIGHT_GRAY);
        stringColor.put("Grau", Color.LIGHT_GRAY);
        stringColor.put("grau", Color.LIGHT_GRAY);

        stringColor.put("Orange", Color.ORANGE);
        stringColor.put("orange", Color.ORANGE);

        stringColor.put("Yellow", Color.YELLOW);
        stringColor.put("yellow", Color.YELLOW);
        stringColor.put("Gelb", Color.YELLOW);
        stringColor.put("gelb", Color.YELLOW);
    }

}
