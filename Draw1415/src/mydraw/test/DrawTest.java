package mydraw.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import mydraw.ColorException;
import mydraw.Draw;
import mydraw.TextIOException;

public class DrawTest
{
    Draw test;
    BufferedImage bufferedImagePositive;
    BufferedImage bufferedImageNegative;
    Image img;
    Image img2;
    boolean pixel;
    boolean except;

    @Before
    public void setUp()
    {
        test = new Draw();
        bufferedImagePositive = new BufferedImage(test.getWidth(),
                test.getHeight(), BufferedImage.TYPE_INT_RGB);
        bufferedImageNegative = new BufferedImage(test.getWidth(),
                test.getHeight(), BufferedImage.TYPE_INT_RGB);
        pixel = true;
        except = false;
    }

    @Test
    public void drawtest()
    {
        //Positive Test
        img = null;
        test.autoDraw();
        try
        {
            test.writeImage(test.getBufferImg(), "testImage");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            img = test.readImage("testImage.png");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        bufferedImagePositive = (BufferedImage) img;

        assertEquals(test.getBufferImg()
            .getHeight(), bufferedImagePositive.getHeight());
        assertEquals(test.getBufferImg()
            .getWidth(), bufferedImagePositive.getWidth());

        pixel = true;
        for (int currentHeight = 0; currentHeight < bufferedImagePositive
            .getHeight(); currentHeight++)
        {
            for (int currentWidth = 0; currentWidth < bufferedImagePositive
                .getWidth(); currentWidth++)
            {
                // Compare the pixels for equality
                if (test.getBufferImg()
                    .getRGB(currentWidth,
                            currentHeight) != bufferedImagePositive
                                .getRGB(currentWidth, currentHeight))
                {
                    pixel = false;
                }
            }
        }
        assertTrue(pixel);

        //Negative Test

        img2 = null;
        test.autoDraw();

        try
        {
            test.setFGColor("Schwarz");
        }
        catch (ColorException e)
        {
            e.printStackTrace();
        }
        test.drawOval(new Point(10, 10), new Point(120, 133));
        try
        {
            test.writeImage(test.getBufferImg(), "secondTestImage");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            img2 = test.readImage("secondTestImage.png");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        bufferedImageNegative = (BufferedImage) img2;
        test.autoDraw();

        assertEquals(bufferedImagePositive.getHeight(),
                bufferedImageNegative.getHeight());
        assertEquals(bufferedImagePositive.getWidth(),
                bufferedImageNegative.getWidth());

        boolean pixel2 = false;
        for (int y = 0; y < bufferedImagePositive.getHeight(); y++)
        {
            for (int x = 0; x < bufferedImagePositive.getWidth(); x++)
            {
                // Compare the pixels for equality.
                if (bufferedImageNegative.getRGB(x, y) != bufferedImagePositive
                    .getRGB(x, y))
                {
                    pixel2 = true;
                }
            }
        }
        assertTrue(pixel2);
    }

    @Test
    public void colortest()
    {
        test = new Draw();

        // Negative Tests
        assertFalse(test.getFGColor()
            .equals("Color is not available"));
        assertNotNull(test.getFGColor());

        // Negative Exception Test
        try
        {
            test.setFGColor("Gelb");
        }
        catch (ColorException e)
        {
            System.out.println(e);
        }

        //Positive Tests
        try
        {
            test.setFGColor("Blau");
        }
        catch (ColorException e)
        {
            fail();
        }
        assertEquals("Blau", test.getFGColor());

        try
        {
            test.setFGColor("gruen");
        }
        catch (ColorException e)
        {
            fail();
        }
        assertEquals("Gruen", test.getFGColor());

        try
        {
            test.setFGColor("Rot");
        }
        catch (ColorException e)
        {
            fail();
        }
        assertEquals("Rot", test.getFGColor());

        try
        {
            test.setFGColor("Schwarz");
        }
        catch (ColorException e)
        {
            fail();
        }
        assertEquals("Schwarz", test.getFGColor());

    }

    @Test
    public void backgroundColorTest()
    {
        test = new Draw();
        // Negative Tests
        assertNotNull(test.getBGColor());
        assertFalse(test.getBGColor()
            .equals("Color is not available"));

        // Negative exception test
        except = false;
        try
        {
            test.setBGColor("Background");
            except = false;
        }
        catch (ColorException e)
        {
            System.out.println(e);
            except = true;
        }
        assertTrue(except);

        //Positive Tests
        assertEquals("Weiss", test.getBGColor());

        try
        {
            test.setBGColor("Blau");
        }
        catch (ColorException e)
        {
            fail();
        }
        assertEquals("Blau", test.getBGColor());

        try
        {
            test.setBGColor("Gruen");
        }
        catch (ColorException e)
        {
            fail();
        }
        assertEquals("Gruen", test.getBGColor());

        try
        {
            test.setBGColor("Rot");
        }
        catch (ColorException e)
        {
            fail();
        }
        assertEquals("Rot", test.getBGColor());

        try
        {
            test.setBGColor("Schwarz");
        }
        catch (ColorException e)
        {
            fail();
        }
        assertEquals("Schwarz", test.getBGColor());
    }

    @Test
    public void sizetest()
    {
        test = new Draw();

        //Positive Tests
        test.setHeight(700);
        assertTrue(700 == test.getHeight());
        test.setWidth(700);
        assertTrue(700 == test.getWidth());

        //Negative Tests
        test.setHeight(600);
        assertFalse(200 == test.getHeight());
        test.setWidth(300);
        assertFalse(200 == test.getWidth());

    }

    @Test
    public void undoredo()
    {
        test = new Draw();
        test.autoDraw();
        BufferedImage p1 = (BufferedImage) test.getDrawing();
        try
        {
            test.writeImage(p1, "p1");
        }
        catch (IOException e1)
        {
            fail();
        }
        test.undo();
        BufferedImage p2 = (BufferedImage) test.getDrawing();
        try
        {
            test.writeImage(p2, "p2");
        }
        catch (IOException e1)
        {
            fail();
        }
        BufferedImage p11 = null;
        BufferedImage p22 = null;
        try
        {
            p11 = (BufferedImage) test.readImage("p1.png");
            p22 = (BufferedImage) test.readImage("p2.png");
        }
        catch (IOException e)
        {
            fail();
        }

        pixel = false;
        for (int y = 0; y < p11.getHeight(); y++)
        {
            for (int x = 0; x < p1.getWidth(); x++)
            {
                // Compare the pixels for equality.
                if (p11.getRGB(x, y) != p22.getRGB(x, y))
                {
                    pixel = true;
                }
            }
        }
        assertEquals(p1.getHeight(), p2.getHeight());
        assertEquals(p1.getWidth(), p2.getWidth());
        //Images should not be equal after one undo action
        assertTrue(pixel);

        test.redo();
        BufferedImage p3 = (BufferedImage) test.getDrawing();
        pixel = true;
        for (int y = 0; y < p1.getHeight(); y++)
        {
            for (int x = 0; x < p1.getWidth(); x++)
            {
                // Compare the pixels for equality.
                if (p3.getRGB(x, y) != p11.getRGB(x, y))
                {
                    pixel = false;
                }
            }
        }
        //p1 and p3 should be equal after undo and redo actions
        assertTrue(pixel);
    }

    @Test
    public void writereadcommand() throws TextIOException
    {
        String t = "CommandTest";
        Draw testWrite = new Draw();
        Draw testRead = new Draw();
        testWrite.autoDraw();

        testWrite.writeText(t);
        testRead.readText(t);

        BufferedImage bild = testRead.getBufferImg();

        BufferedImage autodraw = null;
        try
        {
            autodraw = (BufferedImage) testWrite.readImage("testImage.png");
        }
        catch (IOException e)
        {
            fail();
        }

        pixel = true;
        for (int y = 0; y < bild.getHeight(); y++)
        {
            for (int x = 0; x < bild.getWidth(); x++)
            {
                // Compare the pixels for equality.
                if (autodraw.getRGB(x, y) != bild.getRGB(x, y))
                {
                    pixel = false;
                }
            }
        }
        assertTrue(pixel);

    }

}
