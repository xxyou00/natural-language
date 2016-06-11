package nl.yannickl88.language.matcher.buildins;

import nl.yannickl88.language.EntityMatcherInterface;
import nl.yannickl88.language.matcher.EntityMatch;

/**
 * The static word EntityMatcher allows for matching static words. These
 * usually come from configured utterances rather then specialized matcher
 * configuration.
 */
public class StaticWord implements EntityMatcherInterface {
    public final String word;

    public StaticWord(String word) {
        this.word = word;
    }

    @Override
    public EntityMatch match(String message) {
        return new EntityMatch(message.contains(this.word) ? 1.0f : 0.0f);
    }

    @Override
    public int hashCode() {
        return word.hashCode();
    }
}
