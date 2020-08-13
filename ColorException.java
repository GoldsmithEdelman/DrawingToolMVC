package mydraw;

/**
 * Handling color exceptions
 * @author Michael
 *
 */

@SuppressWarnings("serial")
public class ColorException extends Exception
{
    private String color;

    public ColorException(String color)
    {
        this.color = color;
    }

    public String toString()
    {
        return "Chosen color is not available: " + color;
    }

}
