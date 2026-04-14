import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/**
 * The LogfileCreator class generates simulated web server log data.
 * It can write randomly generated LogEntry objects to a file, or
 * create individual entries for testing purposes.
 *
 * This is mainly used when real log data is unavailable.
 *
 * @author Ernesto Cuellar
 * @version 1.0
 */
public class LogfileCreator
{
    private Random rand;

    /**
     * Creates a new LogfileCreator with a random number generator.
     */
    public LogfileCreator()
    {
        rand = new Random();
    }

    /**
     * Creates a log file containing randomly generated log entries.
     *
     * @param filename the name of the file to create
     * @param numEntries the number of log entries to generate
     * @return true if the file was created successfully, false otherwise
     */
    public boolean createFile(String filename, int numEntries)
    {
        if(numEntries <= 0) {
            return false;
        }

        try (FileWriter writer = new FileWriter(filename)) {

            LogEntry[] entries = new LogEntry[numEntries];

            for(int i = 0; i < numEntries; i++) {
                entries[i] = createEntry();
            }

            Arrays.sort(entries);

            for(LogEntry entry : entries) {
                writer.write(entry.toString());
                writer.write("\n");
            }

            return true;
        }
        catch(IOException e) {
            System.err.println("Error writing file: " + filename);
            return false;
        }
    }

    /**
     * Creates a single randomly generated log entry.
     *
     * @return a new LogEntry with random date and time values
     */
    public LogEntry createEntry()
    {
        int year = 2016;
        int month = 1 + rand.nextInt(12);
        int day = 1 + rand.nextInt(28);
        int hour = rand.nextInt(24);
        int minute = rand.nextInt(60);

        return new LogEntry(year, month, day, hour, minute);
    }
}
