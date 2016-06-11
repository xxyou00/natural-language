package nl.yannickl88.language;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

class IntentMatcher {
    final String action;
    private HashSet<EntityMatcherInterface> words;

    IntentMatcher(String action) {
        this.action = action;
        words = new HashSet<>();
    }

    void addMatcher(EntityMatcherInterface word) {
        words.add(word);
    }

    List<EntityMatcherInterface> getWords() {
        return new ArrayList<>(words);
    }
}
