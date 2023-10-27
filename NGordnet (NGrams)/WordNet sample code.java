package ngordnet.main;

import edu.princeton.cs.algs4.In;
import ngordnet.ngrams.NGramMap;

import java.util.*;

public class WordNet {
    private Graph graph;
    HashMap<Integer, ArrayList<String>> hashMapIdsWords = new HashMap<Integer, ArrayList<String>>();
    HashMap<Integer, ArrayList<Integer>> hashMapIdsRelationship = new HashMap<Integer, ArrayList<Integer>>();
    HashMap<String, ArrayList<Integer>> hashMapGetIds = new HashMap<String, ArrayList<Integer>>();
    //Wrapper for graph

    public WordNet(String synsetFile, String hyponymFile) {
        In synsetFReader = new In(synsetFile);
        while (synsetFReader.hasNextLine()) {
            String line = synsetFReader.readLine();
            if (line == null) {
                continue;
            }
            String[] arr = line.split(",");
            int wordsId = Integer.parseInt(arr[0]);
            String[] splitWords = arr[1].split(" ");
            ArrayList<String> words = new ArrayList<String>();
            for (String i : splitWords) {
                words.add(i);
                if (hashMapGetIds.containsKey(i)) {
                    ArrayList<Integer> value = hashMapGetIds.get(i);
                    value.add(wordsId);
                    hashMapGetIds.replace(i, value);
                } else {
                    ArrayList<Integer> value = new ArrayList<Integer>();
                    value.add(wordsId);
                    hashMapGetIds.put(i, value);
                }
            }
            hashMapIdsWords.put(wordsId, words);
        }

        In hyponymFReader = new In(hyponymFile);
        while (hyponymFReader.hasNextLine()) {
            String line = hyponymFReader.readLine();
            if (line == null) {
                continue;
            }
            String[] arr = line.split(",");
            int keyForHMIR = Integer.parseInt(arr[0]);
            ArrayList<Integer> valForHMIR = new ArrayList<Integer>();
            for (int i = 1; i < arr.length; i++) {
                valForHMIR.add(Integer.parseInt(arr[i]));
            }
            if (hashMapIdsRelationship.containsKey(keyForHMIR)) {
                ArrayList<Integer> value = hashMapIdsRelationship.get(keyForHMIR);
                value.addAll(valForHMIR);
                hashMapIdsRelationship.replace(keyForHMIR, value);
            } else {
                hashMapIdsRelationship.put(keyForHMIR, valForHMIR);
            }
        }
    }

    public Set<String> hyponyms(List<String> wordList) {
        Set<String> outputSet = new HashSet<String>();
        if (wordList == null) {
            outputSet = null;
        } else {
            outputSet = hyponymsMainHelper(wordList.get(0));
            for (int i = 1; i < wordList.size(); i++) {
                Set<String> compareSet = hyponymsMainHelper(wordList.get(i));
                outputSet.retainAll(compareSet);
            }
        }
        return outputSet;
    }

    private Set<String> hyponymsMainHelper(String wordToPrint) {
        Set<String> outputSet = new HashSet<String>();
        ArrayList<Integer> idValues = hashMapGetIds.get(wordToPrint);
        if (idValues == null) {
            return outputSet;
        }
        for (int i : idValues) {
            hyponymsHelper(i, outputSet);
        }

        TreeSet<String> myOutPutTreeSet = new TreeSet<String>();
        myOutPutTreeSet.addAll(outputSet);
        return myOutPutTreeSet;
    }

    private void hyponymsHelper(int id, Set<String> set) {
        ArrayList<String> value = hashMapIdsWords.get(id);
        for (String s : value) {
            set.add(s);
        }
        ArrayList<Integer> arr = hashMapIdsRelationship.get(id);
        if (arr != null) {
            for (int i : arr) {
                hyponymsHelper(i, set);
            }
        }
    }

    public Set<String> popularWord(List<String> wordList, int k, NGramMap nm, int startYear, int endYear) {
        if (wordList == null) {
            return null;
        }
        HashMap<Double, String> outputHashMap = new HashMap<Double, String>();
        Set<Double> outputKeySet = new HashSet<Double>();
        TreeSet<String> output = new TreeSet<String>();
        Set<String> hyponymsWords = hyponyms(wordList);

        for (String s : hyponymsWords) {
            List<Double> counts = nm.countHistory(s, startYear, endYear).data();
            double totalCount = counts.stream().mapToDouble(Double::intValue).sum();
            if (totalCount > 0) {
                outputHashMap.put(totalCount, s);
            }

        }

        outputKeySet = outputHashMap.keySet();
        TreeSet<Double> myRealOutputKeyset = new TreeSet<Double>();
        myRealOutputKeyset.addAll(outputKeySet);
        myRealOutputKeyset = (TreeSet) myRealOutputKeyset.descendingSet();

        Iterator iter = myRealOutputKeyset.iterator();
        if (!iter.hasNext()) {
            return output;
        }
        while (k > 0) {
            String s = "";
            if (iter.hasNext()) {
                s = outputHashMap.get(iter.next());
            }
            if (s != "") {
                output.add(s);
            }
            k--;
        }

        return output;
    }
}
