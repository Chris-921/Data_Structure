package ngordnet.ngrams;

import java.util.*;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {
    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        for (int i = startYear; i <= endYear; i++) {
            this.put(i, ts.get(i));
        }
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        List<Integer> yearsList = new ArrayList<>();
        Set<Integer> keys = this.keySet();
        for (Integer key : keys) {
            yearsList.add(key);
        }
        return yearsList;
    }


    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        List<Double> dataList = new ArrayList<>();
        Set<Integer> keys = this.keySet();
        for (Integer key : keys) {
            dataList.add(this.get(key));
        }
        return dataList;
    }

    /**
     * Returns the yearwise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries timeSer = new TimeSeries();
        List<Integer> yearsList1 = new ArrayList<>();
        Set<Integer> keys1 = this.keySet();
        List<Integer> yearsList2 = new ArrayList<>();
        Set<Integer> keys2 = ts.keySet();

        for (Integer key : keys1) {
            yearsList1.add(key);
        }
        for (Integer key : keys2) {
            yearsList2.add(key);
        }

        int i = 0;
        int j = 0;
        while (i < yearsList1.size() && j < yearsList2.size()) {
            int y1 = yearsList1.get(i);
            int y2 = yearsList2.get(j);
            if (y1 < y2) {
                timeSer.put(y1, this.get(y1));
                i++;
            } else if (y1 > y2) {
                timeSer.put(y2, ts.get(y2));
                j++;
            } else {
                double addedValue = this.get(y1) + ts.get(y2);
                timeSer.put(y1, addedValue);
                i++;
                j++;
            }
        }

        while (i < yearsList1.size()) {
            int y1 = yearsList1.get(i);
            timeSer.put(y1, this.get(y1));
            i++;
        }

        while (j < yearsList2.size()) {
            int y2 = yearsList2.get(j);
            timeSer.put(y2, ts.get(y2));
            j++;
        }

        return timeSer;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. If TS is missing a year that exists in this TimeSeries,
     * throw an IllegalArgumentException. If TS has a year that is not in this TimeSeries, ignore it.
     * Should return a new TimeSeries (does not modify this TimeSeries).
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries timeSer = new TimeSeries();
        List<Integer> yearsList1 = new ArrayList<>();
        Set<Integer> keys1 = this.keySet();

        for (Integer key : keys1) {
            yearsList1.add(key);
        }

        for (Integer i : yearsList1) {
            if (ts.get(i) == null) {
                throw new IllegalArgumentException("TimeSeries should not be null");
            } else {
                timeSer.put(i, this.get(i) / ts.get(i));
            }
        }
        return timeSer;
    }
}
