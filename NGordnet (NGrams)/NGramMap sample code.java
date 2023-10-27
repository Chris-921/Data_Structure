package ngordnet.ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 * <p>
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {
    private HashMap<String, TimeSeries> dataWordAndNum;
    private TimeSeries dataTotalNum;
    private TimeSeries dataTotalPages;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        In wordFReader = new In(wordsFilename);
        dataWordAndNum = new HashMap<>();
        while (wordFReader.hasNextLine()) {
            String line = wordFReader.readLine();
            if (line == null) {
                continue;
            }
            String[] arr = line.split("\t");
            String word = arr[0];
            int year = Integer.parseInt(arr[1]);
            double count = Double.parseDouble(arr[2]);
            TimeSeries ts = dataWordAndNum.get(word);
            if (ts == null) {
                ts = new TimeSeries();
            }
            ts.put(year, count);
            dataWordAndNum.put(word, ts);
        }


        In countFReader = new In(countsFilename);
        dataTotalNum = new TimeSeries();
        dataTotalPages = new TimeSeries();
        while (countFReader.hasNextLine()) {
            String line = countFReader.readLine();
            if (line == null) {
                continue;
            }
            String[] arr = line.split(",");
            int year = Integer.parseInt(arr[0]);
            double totalNumber = Double.parseDouble(arr[1]);
            double totalPages = Double.parseDouble(arr[2]);
            dataTotalNum.put(year, totalNumber);
            dataTotalPages.put(year, totalPages);
        }

    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy,
     * not a link to this NGramMap's TimeSeries. In other words, changes made
     * to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word) {
        TimeSeries countHstry = new TimeSeries();
        List<Integer> years = dataWordAndNum.get(word).years();
        List<Double> data = dataWordAndNum.get(word).data();
        for (int i = 0; i < years.size(); i++) {
            countHstry.put(years.get(i), data.get(i));
        }
        return countHstry;
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other words,
     * changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries countHstry = new TimeSeries();
        for (int i = startYear; i <= endYear; i++) {
            if (dataWordAndNum.get(word).get(i) == null) {
                continue;
            } else {
                countHstry.put(i, dataWordAndNum.get(word).get(i));
            }
        }
        return countHstry;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        TimeSeries totalCountHistoryTs = new TimeSeries();
        List<Integer> years = dataTotalNum.years();
        List<Double> data = dataTotalNum.data();
        for (int i = 0; i < years.size(); i++) {
            totalCountHistoryTs.put(years.get(i), data.get(i));
        }
        return totalCountHistoryTs;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to
     * all words recorded in that year.
     */
    public TimeSeries weightHistory(String word) {
        TimeSeries portion = countHistory(word);
        TimeSeries totalW = totalCountHistory();
        portion = portion.dividedBy(totalW);
        return portion;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries portion = countHistory(word, startYear, endYear);
        TimeSeries totalW = totalCountHistory();
        portion = portion.dividedBy(totalW);
        return portion;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries sumWigt = new TimeSeries();
        Iterator<String> it = words.iterator();
        while (it.hasNext()) {
            String word = it.next();
            sumWigt = sumWigt.plus(weightHistory(word));

        }

        return sumWigt;
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS
     * between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     * this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries sumWigt = new TimeSeries();
        Iterator<String> it = words.iterator();
        while (it.hasNext()) {
            String word = it.next();
            sumWigt = sumWigt.plus(weightHistory(word, startYear, endYear));
        }
        return sumWigt;
    }


}
