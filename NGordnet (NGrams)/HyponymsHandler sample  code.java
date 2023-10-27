package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;

import java.util.List;
import java.util.Set;


public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNet wn;
    private NGramMap nm;

    public HyponymsHandler(WordNet wn, NGramMap nm) {
        this.wn = wn;
        this.nm = nm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();


        if (words == null || words.size() == 0) {
            return "[]";
        }
        
        Set<String> iterSet = wn.hyponyms(words);
        if (k > 0) {
            iterSet = wn.popularWord(words, k, nm, startYear, endYear);
        }

        String out = iterSet.toString();
        return out;
    }
}
