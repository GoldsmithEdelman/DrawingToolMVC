package mydraw;

@SuppressWarnings("serial")
public class TextIOException extends Exception
{
    /**
     * Default constructor
     */
    public TextIOException()
    {
        super("Command does not exist");
    }

    /**
     * Alternative constructor with string-type command
     * @param command
     */
    public TextIOException(String command)
    {
        super("Following command does not exist: " + command);

    }
}
