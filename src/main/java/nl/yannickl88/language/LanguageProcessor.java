package nl.yannickl88.language;

import nl.yannickl88.language.intent.Intent;
import nl.yannickl88.language.matcher.EntityMatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Language Processor guesses the intent of a message based on it's
 * configuration.
 *
 * @see IntentMatcherLoader
 */
public class LanguageProcessor implements LanguageProcessable, IntentMatcherLoadable {
    private HashMap<String, Classifier> classifiers;
    private HashSet<String> uniqueWords;
    private int totalUtterances;

    public LanguageProcessor() {
        classifiers = new HashMap<>();
        uniqueWords = new HashSet<>();
    }

    @Override
    public Intent getIntent(String message) {
        double score = 0.0;
        Intent best = new Intent("None");

        for (String key : classifiers.keySet()) {
            IntentBuilder builder = new IntentBuilder();
            builder.setAction(key);

            Classifier classifier = classifiers.get(key);
            double total = (classifier.getTotalUtterances() + 1) / (double) (totalUtterances + 1);

            for (String word : Classifier.getWords(message)) {
                total *= (double) (classifier.getWordCount(word) + 1) / (classifier.getTotalWords() + uniqueWords.size());
            }

            if (total > score) {
                score = total;

                for (EntityMatchable matcher : classifier.getMatchers()) {
                    EntityMatch match = matcher.match(message);

                    if (match.score > 0 && match.hasEntity()) {
                        builder.addEntity(match.getEntityType(), match.getEntity());
                    }
                }
                best = builder.createIntent();
            }
        }

        return best;
    }

    @Override
    public void addIntentMatcher(Classifier classifier) {
        classifiers.put(classifier.action, classifier);
        totalUtterances += classifier.getTotalUtterances();
        uniqueWords.addAll(classifier.getWords());
    }
}
