/**
 * Read web server data and analyze hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29
 */
public class LogAnalyzer {
    private int[] hourCounts = new int[24];
    private int[] dayCounts = new int[28];   // Days 1-28
    private int[] monthCounts = new int[12]; // Months 1-12
    private LogfileReader reader;

    // Default constructor
    public LogAnalyzer() {
        reader = new LogfileReader();
    }

    // Constructor with file name
    public LogAnalyzer(String filename) {
        reader = new LogfileReader(filename);
    }

    // Analyze all log entries
    public void analyzeData() {
        // reset counts
        for(int i=0;i<24;i++) hourCounts[i]=0;
        for(int i=0;i<28;i++) dayCounts[i]=0;
        for(int i=0;i<12;i++) monthCounts[i]=0;

        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            hourCounts[entry.getHour()]++;
            dayCounts[entry.getDay()-1]++;
            monthCounts[entry.getMonth()-1]++;
        }
    }

    // Total number of accesses
    public int numberOfAccesses() {
        int total = 0;
        for(int h : hourCounts) total += h;
        return total;
    }

    // Busiest hour
    public int busiestHour() {
        int max = hourCounts[0], h = 0;
        for(int i=1;i<24;i++) if(hourCounts[i]>max){ max=hourCounts[i]; h=i; }
        return h;
    }

    // Quietest hour
    public int quietestHour() {
        int min = hourCounts[0], h = 0;
        for(int i=1;i<24;i++) if(hourCounts[i]<min){ min=hourCounts[i]; h=i; }
        return h;
    }

    // Busiest two-hour period
    public int busiestTwoHourPeriod() {
        int maxSum = hourCounts[0]+hourCounts[1], h=0;
        for(int i=1;i<23;i++){
            int sum = hourCounts[i]+hourCounts[i+1];
            if(sum>maxSum){ maxSum=sum; h=i; }
        }
        return h;
    }

    // Quietest day (1-28)
    public int quietestDay() {
        int min = dayCounts[0], d=1;
        for(int i=1;i<28;i++) if(dayCounts[i]<min){ min=dayCounts[i]; d=i+1; }
        return d;
    }

    // Busiest day (1-28)
    public int busiestDay() {
        int max = dayCounts[0], d=1;
        for(int i=1;i<28;i++) if(dayCounts[i]>max){ max=dayCounts[i]; d=i+1; }
        return d;
    }

    // Total accesses for a month (1-12)
    public int totalAccessesForMonth(int month) {
        if(month<1||month>12) return 0;
        return monthCounts[month-1];
    }

    // Quietest month
    public int quietestMonth() {
        int min=monthCounts[0], m=1;
        for(int i=1;i<12;i++) if(monthCounts[i]<min){ min=monthCounts[i]; m=i+1; }
        return m;
    }

    // Busiest month
    public int busiestMonth() {
        int max=monthCounts[0], m=1;
        for(int i=1;i<12;i++) if(monthCounts[i]>max){ max=monthCounts[i]; m=i+1; }
        return m;
    }

    // Average accesses per month
    public double averageAccessesPerMonth() {
        int sum=0;
        for(int c: monthCounts) sum+=c;
        return sum/12.0;
    }
    
    // Print counts (optional)
    public void printHourlyCounts() {
        System.out.println("Hour : Count");
        for(int i=0;i<24;i++) System.out.println(i+": "+hourCounts[i]);
    }
    
    public void printDailyCounts() {
        System.out.println("Day : Count");
        for(int i=0;i<28;i++) System.out.println((i+1)+": "+dayCounts[i]);
    }

    public void printMonthlyCounts() {
        System.out.println("Month : Count");
        for(int i=0;i<12;i++) System.out.println((i+1)+": "+monthCounts[i]);
    }
}