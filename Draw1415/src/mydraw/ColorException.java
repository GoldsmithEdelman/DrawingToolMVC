package mydraw;

import static org.junit.Assert.fail;

@SuppressWarnings({"serial", "unused"})
public class ColorException extends Exception
{
    /**
     * Default constructor
     */
    public ColorException()
    {
        super("Color is not available");
    }

    /**
     * Constructor with the parameter color as string-type
     * @param color Color that triggers exception
     */
    public ColorException(String color)
    {
        super("Chosen color " + color + " is not available");

    }
}
