package nl.yannickl88.language;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Classifier {
    public final String action;
    private HashMap<String, Integer> wordCount;
    private ArrayList<EntityMatchable> matchers;
    private int totalUtterances = 0, totalWords = 0;

    public static ArrayList<String> getWords(String message) {
        Pattern p = Pattern.compile("([\\w\\a]+)");
        Matcher m = p.matcher(message);
        ArrayList<String> words = new ArrayList<>();

        while (m.find()) {
            words.add(m.group().toLowerCase());
        }

        return words;
    }

    Classifier(String action) {
        this.action = action;
        wordCount = new HashMap<>();
        matchers = new ArrayList<>();
    }

    int getWordCount(String word) {
        if (!wordCount.containsKey(word)) {
            return 0;
        }

        return wordCount.get(word);
    }

    List<String> getWords() {
        return new ArrayList<>(wordCount.keySet());
    }

    void addUtterance(List<String> words) {
        totalUtterances++;
        totalWords += words.size();

        for (String word : words) {
            wordCount.put(word, getWordCount(word) + 1);
        }
    }

    int getTotalUtterances() {
        return totalUtterances;
    }

    int getTotalWords() {
        return totalWords;
    }

    public void addMatcher(EntityMatchable matcher) {
        matchers.add(matcher);
    }

    public List<EntityMatchable> getMatchers() {
        return matchers;
    }
}
