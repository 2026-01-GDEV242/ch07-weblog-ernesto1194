/**
 * The LogAnalyzer class reads and analyzes web server log data.
 * It processes log entries and provides statistics such as busiest hours,
 * days, and months of activity.
 *
 * @author Ernesto Cuellar
 * @version 1.0
 */
public class LogAnalyzer {

    private int[] hourCounts = new int[24];
    private int[] dayCounts = new int[28];
    private int[] monthCounts = new int[12];
    private LogfileReader reader;

    /**
     * Creates a LogAnalyzer using the default log file.
     */
    public LogAnalyzer() {
        reader = new LogfileReader();
    }

    /**
     * Creates a LogAnalyzer using a specified log file.
     *
     * @param filename the name of the log file to read
     */
    public LogAnalyzer(String filename) {
        reader = new LogfileReader(filename);
    }

    /**
     * Reads all log entries and updates hourly, daily, and monthly counts.
     */
    public void analyzeData() {
        for (int i = 0; i < 24; i++) hourCounts[i] = 0;
        for (int i = 0; i < 28; i++) dayCounts[i] = 0;
        for (int i = 0; i < 12; i++) monthCounts[i] = 0;

        while (reader.hasNext()) {
            LogEntry entry = reader.next();
            hourCounts[entry.getHour()]++;
            dayCounts[entry.getDay() - 1]++;
            monthCounts[entry.getMonth() - 1]++;
        }
    }

    /**
     * Calculates the total number of log entries processed.
     *
     * @return total number of accesses
     */
    public int numberOfAccesses() {
        int total = 0;
        for (int h : hourCounts) {
            total += h;
        }
        return total;
    }

    /**
     * Finds the hour with the highest number of accesses.
     *
     * @return busiest hour (0–23)
     */
    public int busiestHour() {
        int max = hourCounts[0];
        int h = 0;

        for (int i = 1; i < 24; i++) {
            if (hourCounts[i] > max) {
                max = hourCounts[i];
                h = i;
            }
        }
        return h;
    }

    /**
     * Finds the hour with the lowest number of accesses.
     *
     * @return quietest hour (0–23)
     */
    public int quietestHour() {
        int min = hourCounts[0];
        int h = 0;

        for (int i = 1; i < 24; i++) {
            if (hourCounts[i] < min) {
                min = hourCounts[i];
                h = i;
            }
        }
        return h;
    }

    /**
     * Finds the busiest consecutive two-hour period.
     *
     * @return starting hour of busiest two-hour block
     */
    public int busiestTwoHourPeriod() {
        int maxSum = hourCounts[0] + hourCounts[1];
        int h = 0;

        for (int i = 1; i < 23; i++) {
            int sum = hourCounts[i] + hourCounts[i + 1];
            if (sum > maxSum) {
                maxSum = sum;
                h = i;
            }
        }
        return h;
    }

    /**
     * Finds the quietest day of the month.
     *
     * @return day number (1–28)
     */
    public int quietestDay() {
        int min = dayCounts[0];
        int d = 1;

        for (int i = 1; i < 28; i++) {
            if (dayCounts[i] < min) {
                min = dayCounts[i];
                d = i + 1;
            }
        }
        return d;
    }

    /**
     * Finds the busiest day of the month.
     *
     * @return day number (1–28)
     */
    public int busiestDay() {
        int max = dayCounts[0];
        int d = 1;

        for (int i = 1; i < 28; i++) {
            if (dayCounts[i] > max) {
                max = dayCounts[i];
                d = i + 1;
            }
        }
        return d;
    }

    /**
     * Gets the total number of accesses for a given month.
     *
     * @param month the month (1–12)
     * @return total number of accesses in that month
     */
    public int totalAccessesForMonth(int month) {
        if (month < 1 || month > 12) {
            return 0;
        }
        return monthCounts[month - 1];
    }

    /**
     * Finds the quietest month.
     *
     * @return month number (1–12)
     */
    public int quietestMonth() {
        int min = monthCounts[0];
        int m = 1;

        for (int i = 1; i < 12; i++) {
            if (monthCounts[i] < min) {
                min = monthCounts[i];
                m = i + 1;
            }
        }
        return m;
    }

    /**
     * Finds the busiest month.
     *
     * @return month number (1–12)
     */
    public int busiestMonth() {
        int max = monthCounts[0];
        int m = 1;

        for (int i = 1; i < 12; i++) {
            if (monthCounts[i] > max) {
                max = monthCounts[i];
                m = i + 1;
            }
        }
        return m;
    }

    /**
     * Calculates the average number of accesses per month.
     *
     * @return average monthly accesses
     */
    public double averageAccessesPerMonth() {
        int sum = 0;

        for (int c : monthCounts) {
            sum += c;
        }

        return sum / 12.0;
    }

    /**
     * Prints the number of accesses per hour.
     */
    public void printHourlyCounts() {
        System.out.println("Hour : Count");
        for (int i = 0; i < 24; i++) {
            System.out.println(i + ": " + hourCounts[i]);
        }
    }

    /**
     * Prints the number of accesses per day.
     */
    public void printDailyCounts() {
        System.out.println("Day : Count");
        for (int i = 0; i < 28; i++) {
            System.out.println((i + 1) + ": " + dayCounts[i]);
        }
    }

    /**
     * Prints the number of accesses per month.
     */
    public void printMonthlyCounts() {
        System.out.println("Month : Count");
        for (int i = 0; i < 12; i++) {
            System.out.println((i + 1) + ": " + monthCounts[i]);
        }
    }
}