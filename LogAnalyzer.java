/**
 * Read web server data and analyze hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses
     * using the default file.
     */
    public LogAnalyzer()
    { 
        hourCounts = new int[24];
        reader = new LogfileReader();
    }

    /**
     * Create an object to analyze hourly web accesses
     * using a specified file.
     */
    public LogAnalyzer(String filename)
    {
        hourCounts = new int[24];
        reader = new LogfileReader(filename);
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }

    /**
     * Print the hourly counts.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
    /**
     * Print the raw data.
     */
    public void printData()
    {
        reader.printData();
    }

    /**
     * Returns the total number of accesses.
     */
    public int numberOfAccesses()
    {
        int total = 0;
        for (int count : hourCounts) {
            total += count;
        }
        return total;
    }
    
    /**
     * Returns the busiest hour (0-23) based on hourly counts.
     */
    public int busiestHour()
    {
        int maxCount = hourCounts[0];
        int busiestHour = 0;

        for (int hour = 1; hour < hourCounts.length; hour++) {
            if (hourCounts[hour] > maxCount) {
                maxCount = hourCounts[hour];
                busiestHour = hour;
            }
        }

        return busiestHour;
    }
    
    /**
 * Returns the least busy hour (0-23) based on hourly counts.
 */
public int quietestHour()
{
    int minCount = hourCounts[0];
    int quietestHour = 0;

    for (int hour = 1; hour < hourCounts.length; hour++) {
        if (hourCounts[hour] < minCount) {
            minCount = hourCounts[hour];
            quietestHour = hour;
        }
    }

    return quietestHour;
}

}
