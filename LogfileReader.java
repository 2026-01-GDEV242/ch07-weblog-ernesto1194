import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

/**
 * The LogfileReader class reads log data from a file and provides
 * access to LogEntry objects one at a time using an iterator.
 * If the file cannot be found, simulated data is generated instead.
 *
 * The log data is expected in the format:
 * year month day hour minute
 *
 * @author Ernesto Cuellar
 * @version 1.0
 */
public class LogfileReader implements Iterator<LogEntry>
{
    private String format;
    private ArrayList<LogEntry> entries;
    private Iterator<LogEntry> dataIterator;

    /**
     * Creates a LogfileReader using the default log file.
     * The default file is "demoData.txt".
     */
    public LogfileReader()
    {
        this("demoData.txt");
    }

    /**
     * Creates a LogfileReader that reads data from the given file.
     *
     * @param filename the name of the log file to read
     */
    public LogfileReader(String filename)
    {
        format = "Year Month Day Hour Minute";
        entries = new ArrayList<>();

        boolean dataRead = false;

        try {
            URL fileURL = getClass().getClassLoader().getResource(filename);

            if(fileURL == null) {
                throw new FileNotFoundException(filename);
            }

            Scanner logfile = new Scanner(new File(fileURL.toURI()));

            while(logfile.hasNextLine()) {
                String line = logfile.nextLine();
                LogEntry entry = new LogEntry(line);
                entries.add(entry);
            }

            logfile.close();
            dataRead = true;
        }
        catch(FileNotFoundException | URISyntaxException e) {
            System.out.println("Problem reading file: " + e);
        }

        if(!dataRead) {
            System.out.println("Using simulated data instead.");
            createSimulatedData(entries);
        }

        Collections.sort(entries);
        reset();
    }

    /**
     * Checks if there are more log entries available.
     *
     * @return true if more entries exist, false otherwise
     */
    @Override
    public boolean hasNext()
    {
        return dataIterator.hasNext();
    }

    /**
     * Returns the next LogEntry from the dataset.
     *
     * @return the next LogEntry object
     */
    @Override
    public LogEntry next()
    {
        return dataIterator.next();
    }

    /**
     * Reset is not supported for removing entries.
     */
    @Override
    public void remove()
    {
        System.err.println("Remove operation not supported.");
    }

    /**
     * Gets the expected format of the log file.
     *
     * @return a string describing the log format
     */
    public String getFormat()
    {
        return format;
    }

    /**
     * Resets the iterator so the log data can be read again.
     */
    public void reset()
    {
        dataIterator = entries.iterator();
    }

    /**
     * Prints all log entries to the console.
     */
    public void printData()
    {
        for(LogEntry entry : entries) {
            System.out.println(entry);
        }
    }

    /**
     * Creates simulated log data if file reading fails.
     *
     * @param data the list where generated entries are stored
     */
    private void createSimulatedData(ArrayList<LogEntry> data)
    {
        LogfileCreator creator = new LogfileCreator();

        int numEntries = 100;

        for(int i = 0; i < numEntries; i++) {
            data.add(creator.createEntry());
        }
    }
}
