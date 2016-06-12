package nl.yannickl88.language;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

class IntentMatcher {
    final String action;
    private TreeMap<Integer, EntityMatchable> words;

    IntentMatcher(String action) {
        this.action = action;
        words = new TreeMap<>();
    }

    void addMatcher(EntityMatchable word) {
        words.put(words.size(), word);
    }

    List<EntityMatchable> getWords() {
        return new ArrayList<>(words.values());
    }
}
