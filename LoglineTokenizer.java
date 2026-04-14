import java.util.Scanner;

/**
 * The LoglineTokenizer class breaks a single line of web server log data
 * into separate integer values.
 *
 * The expected format of the log line is:
 * year month day hour minute
 *
 * These values are stored in an integer array provided by the caller.
 *
 * @author Ernesto Cuellar
 * @version 1.0
 */
public class LoglineTokenizer
{
    /**
     * Creates a new LoglineTokenizer.
     */
    public LoglineTokenizer()
    {
        // No initialization required
    }

    /**
     * Tokenizes a log line and stores the values into an integer array.
     *
     * The input string must contain enough integer values to fill the array.
     *
     * @param logline the line of text containing log data
     * @param dataLine the array where parsed integer values are stored
     * @throws java.util.NoSuchElementException if the log line does not contain
     *         enough integer values
     */
    public void tokenize(String logline, int[] dataLine)
    {
        Scanner tokenizer = new Scanner(logline);

        for(int i = 0; i < dataLine.length; i++) {
            if(!tokenizer.hasNextInt()) {
                throw new java.util.NoSuchElementException(
                    "Insufficient data items on log line: " + logline
                );
            }

            dataLine[i] = tokenizer.nextInt();
        }
    }
}
