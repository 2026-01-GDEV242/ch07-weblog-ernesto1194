import java.util.Calendar;

/**
 * The LogEntry class represents a single record from a web server log file.
 * Each entry stores a timestamp (year, month, day, hour, minute) and can be
 * compared with other entries based on time order.
 *
 * This class is used to analyze web traffic patterns over time.
 *
 * @author Ernesto Cuellar
 * @version 1.0
 */
public class LogEntry implements Comparable<LogEntry>
{
    private int[] dataValues;
    private Calendar when;

    private static final int YEAR = 0;
    private static final int MONTH = 1;
    private static final int DAY = 2;
    private static final int HOUR = 3;
    private static final int MINUTE = 4;

    private static final int NUMBER_OF_FIELDS = 5;

    /**
     * Creates a LogEntry by parsing a log line.
     *
     * @param logline a string containing log data in the format:
     *                year month day hour minute
     */
    public LogEntry(String logline)
    {
        dataValues = new int[NUMBER_OF_FIELDS];

        LoglineTokenizer tokenizer = new LoglineTokenizer();
        tokenizer.tokenize(logline, dataValues);

        setWhen();
    }

    /**
     * Creates a LogEntry from individual date and time components.
     *
     * @param year the year of the log entry
     * @param month the month (1–12)
     * @param day the day of the month (1–31)
     * @param hour the hour of the day (0–23)
     * @param minute the minute (0–59)
     */
    public LogEntry(int year, int month, int day, int hour, int minute)
    {
        dataValues = new int[NUMBER_OF_FIELDS];

        dataValues[YEAR] = year;
        dataValues[MONTH] = month;
        dataValues[DAY] = day;
        dataValues[HOUR] = hour;
        dataValues[MINUTE] = minute;

        setWhen();
    }

    /**
     * Gets the hour from this log entry.
     *
     * @return the hour (0–23)
     */
    public int getHour()
    {
        return dataValues[HOUR];
    }

    /**
     * Gets the minute from this log entry.
     *
     * @return the minute (0–59)
     */
    public int getMinute()
    {
        return dataValues[MINUTE];
    }

    /**
     * Gets the year from this log entry.
     *
     * @return the year
     */
    public int getYear()
    {
        return dataValues[YEAR];
    }

    /**
     * Gets the month from this log entry.
     *
     * @return the month (1–12)
     */
    public int getMonth()
    {
        return dataValues[MONTH];
    }

    /**
     * Gets the day from this log entry.
     *
     * @return the day of the month (1–31)
     */
    public int getDay()
    {
        return dataValues[DAY];
    }

    /**
     * Returns a formatted string representation of this log entry.
     *
     * @return a string containing all date/time values
     */
    public String toString()
    {
        StringBuilder buffer = new StringBuilder();

        for(int value : dataValues) {
            if(value < 10) {
                buffer.append("0");
            }
            buffer.append(value).append(" ");
        }

        return buffer.toString().trim();
    }

    /**
     * Compares this log entry with another based on date and time.
     *
     * @param otherEntry the other LogEntry to compare to
     * @return negative if this is earlier, positive if later, 0 if equal
     */
    public int compareTo(LogEntry otherEntry)
    {
        return when.compareTo(otherEntry.getWhen());
    }

    /**
     * Returns the Calendar object representing this entry's time.
     *
     * @return the Calendar object for this log entry
     */
    private Calendar getWhen()
    {
        return when;
    }

    /**
     * Converts stored data values into a Calendar object.
     */
    private void setWhen()
    {
        when = Calendar.getInstance();

        when.set(
            dataValues[YEAR],
            dataValues[MONTH] - 1,
            dataValues[DAY] - 1,
            dataValues[HOUR],
            dataValues[MINUTE]
        );
    }
}