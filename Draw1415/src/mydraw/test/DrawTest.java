package mydraw.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import mydraw.ColorException;
import mydraw.Draw;
import mydraw.DrawGUI;

/**
 * 
 * @author Michal Zlotnik
 * PTP2020
 */
public class DrawTest
{
    private DrawGUI gui;
    private Draw test;
    private int minWidth;
    private int minHeight;
    private int height;
    private int width;

    @Before
    public void setUp()
    {
        test = new Draw();
        gui = new DrawGUI(test);

        minWidth = gui.MIN_WINDOW_WIDTH;
        minHeight = gui.MIN_WINDOW_HEIGHT;
        gui.setSize(minWidth, minHeight);
        height = gui.MIN_WINDOW_HEIGHT + 1;
        width = gui.MIN_WINDOW_WIDTH + 1;
    }

    /**
     * Test method for {@link mydraw.Draw#getHeight()}.
     */

    @Test
    public void testSetGetHeight()
    {
        test.setHeight(height);
        assertTrue(minHeight <= test.getHeight());
        assertFalse(minHeight > test.getHeight());

        test.setHeight(height + 100);
        assertEquals(height + 100, test.getHeight());

        assertNotNull(test.getHeight());
    }

    /**
     * Test method for {@link mydraw.Draw#getWidth()}.
     */
    @Test
    public void testSetGetWidth()
    {
        test.setWidth(width);
        assertTrue(minWidth <= test.getWidth());
        assertFalse(minWidth > test.getWidth());

        test.setWidth(width + 100);
        assertEquals(width + 100, test.getWidth());

        assertNotNull(test.getWidth());
    }

    /**
     * Test method for {@link mydraw.Draw#setFGColor(java.lang.String)}.
     */
    @Test
    public void testSetGetFGColor()
    {
        try
        {
            test.setFGColor("blAck");
        }
        catch (ColorException e)
        {
            e.printStackTrace();
        }
        assertTrue(test.getFGColor() == "Black");
        assertFalse(test.getFGColor() == "White");
        assertEquals("Black", test.getFGColor());
        assertNotNull(test.getFGColor());

        try
        {
            test.setFGColor("white");
        }
        catch (ColorException e)
        {
            e.printStackTrace();
        }
        assertTrue(test.getFGColor() == "White");
        assertFalse(test.getFGColor() == "Black");
        assertEquals("White", test.getFGColor());
        assertNotNull(test.getFGColor());

        try
        {
            test.setFGColor("reD");
        }
        catch (ColorException e)
        {
            e.printStackTrace();
        }
        assertTrue(test.getFGColor() == "Red");
        assertFalse(test.getFGColor() == "Black");
        assertEquals("Red", test.getFGColor());
        assertNotNull(test.getFGColor());

        try
        {
            test.setFGColor(test.getFGColor());
        }
        catch (ColorException e)
        {
            e.printStackTrace();
        }
        assertTrue(test.getFGColor() == "Red");
        assertFalse(test.getFGColor() == "Black");
        assertEquals("Red", test.getFGColor());
        assertNotNull(test.getFGColor());
    }

    /**
     * Test method for {@link mydraw.Draw#setBGColor(java.lang.String)}.
     */
    @Test
    public void testSetGetBGColor()
    {
        try
        {
            test.setBGColor("blacK");
        }
        catch (ColorException e)
        {
            e.printStackTrace();
        }
        assertTrue(test.getBGColor() == "Black");
        assertFalse(test.getBGColor() == "White");
        assertEquals("Black", test.getBGColor());
        assertNotNull(test.getBGColor());

        try
        {
            test.setBGColor("gReen");
        }
        catch (ColorException e)
        {
            e.printStackTrace();
        }
        assertTrue(test.getBGColor() == "Green");
        assertFalse(test.getBGColor() == "White");
        assertEquals("Green", test.getBGColor());
        assertNotNull(test.getBGColor());

        try
        {
            test.setBGColor("ReD");
        }
        catch (ColorException e)
        {
            e.printStackTrace();
        }
        assertTrue(test.getBGColor() == "Red");
        assertFalse(test.getBGColor() == "White");
        assertEquals("Red", test.getBGColor());
        assertNotNull(test.getBGColor());

        try
        {
            test.setBGColor("BLue");
        }
        catch (ColorException e)
        {
            e.printStackTrace();
        }
        assertTrue(test.getBGColor() == "Blue");
        assertFalse(test.getBGColor() == "White");
        assertEquals("Blue", test.getBGColor());
        assertNotNull(test.getBGColor());

        try
        {
            test.setBGColor("whiTE");
        }
        catch (ColorException e)
        {
            e.printStackTrace();
        }
        assertTrue(test.getBGColor() == "White");
        assertFalse(test.getBGColor() == "Black");
        assertEquals("White", test.getBGColor());
        assertNotNull(test.getBGColor());
    }

    /**
     * Test method for {@link mydraw.Draw#writeImage(java.awt.Image, java.lang.String)}.
     */
    @Test
    public void testWriteReadAutoClear()
    {
        test.autoDraw();
        Image save = test.getDrawing();
        BufferedImage saveBuff = gui.convertToBuffered(save);
        Image load = null;
        BufferedImage loadBuff;

        try
        {
            test.writeImage(save, "testAuto");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            load = test.readImage("testAuto.bmp");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        loadBuff = gui.convertToBuffered(load);

        assertTrue(saveBuff.getWidth() == loadBuff.getWidth());
        assertTrue(saveBuff.getHeight() == loadBuff.getHeight());

        for (int i = 0; i < saveBuff.getWidth(); i++)
        {
            for (int j = 0; j < saveBuff.getHeight(); j++)
            {
                assertTrue(saveBuff.getRGB(i, j) == loadBuff.getRGB(i, j));
                assertFalse(saveBuff.getRGB(i, j) != loadBuff.getRGB(i, j));
            }
        }
        for (int i = 0; i < loadBuff.getHeight(); i++)
        {
            for (int j = 0; j < loadBuff.getWidth(); j++)
            {
                assertTrue(saveBuff.getRGB(i, j) == loadBuff.getRGB(i, j));
                assertFalse(saveBuff.getRGB(i, j) != loadBuff.getRGB(i, j));
            }
        }
        for (int i = 0; i < saveBuff.getWidth(); i++)
        {
            for (int j = 0; j < saveBuff.getHeight(); j++)
            {
                // pink : 255192203 
                saveBuff.setRGB(i, j, 255192203);
                assertTrue(saveBuff.getRGB(i, j) != loadBuff.getRGB(i, j));
                assertFalse(saveBuff.getRGB(i, j) == loadBuff.getRGB(i, j));
            }
        }

        test.clear();
        try
        {
            test.writeImage(test.getDrawing(), "testClear");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //    /**
    //     * Test method for {@link mydraw.Draw#drawRectangle(java.awt.Point, java.awt.Point)}.
    //     */
    //    @Test
    //    void testDrawRectangle()
    //    {
    //    }
    //
    //    /**
    //     * Test method for {@link mydraw.Draw#drawOval(java.awt.Point, java.awt.Point)}.
    //     */
    //    @Test
    //    void testDrawOval()
    //    {
    //    }
    //
    //    /**
    //     * Test method for {@link mydraw.Draw#drawPolyLine(java.util.List)}.
    //     */
    //    @Test
    //    void testDrawPolyLine()
    //    {
    //    }

}
